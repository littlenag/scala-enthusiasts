package com.scalaenthusiasts

import java.io.File

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

case class WikipediaArticle(title: String, text: String) {
  /**
    * @return Whether the text of this article mentions `lang` or not
    * @param lang Language to look for (e.g. "Scala")
    */
  def mentionsLanguage(lang: String): Boolean = text.split(' ').contains(lang)
}

object RDD_03 {

  /**
    * Actions vs Transformations
    *  - transformation:
    *    - creates a "new" rdd from an existing one, e.g. map
    *    - lazily evaluated
    *  - action:
    *    - returns a value to the driver program after running all transformations, e.g. collect
    *  - intermediate RDD's are not cached unless Spark is told to do so
    *  - Spark keeps track of the DAG of RDD dependencies
    *  - actions:
    *    - count
    *    - collect
    *    - reduce
    *  - transformations:
    *    - map
    *    - filter
    *    - join
    *  - key/value RDDs
    *    - sortByKey
    *    - groupByKey
    *    - reduceByKey
    *  - moving code (JVM)
    *  - moving data (shuffling)
    *
    * Links
    *  - http://spark.apache.org/docs/latest/rdd-programming-guide.html
    */

  val conf: SparkConf = new SparkConf()
      .setAppName("Spark_03")
      .setMaster("local[*]")
      .set("spark.executor.memory","4g")

  // The entry point to programming Spark with the RDD API.
  val sc: SparkContext = new SparkContext(conf)

  val langs = List(
    "JavaScript", "Java", "PHP", "Python", "C#", "C++", "Ruby", "CSS",
    "Objective-C", "Perl", "Scala", "Haskell", "MATLAB", "Clojure", "Groovy"
  )

  val wikiRdd: RDD[WikipediaArticle] = {

    def filePath = {
      val resource = this.getClass.getClassLoader.getResource("wikipedia/wikipedia.dat")
      if (resource == null) sys.error("Please download the dataset as explained in the assignment instructions")
      new File(resource.toURI).getPath
    }

    def parse(line: String): WikipediaArticle = {
      val subs = "</title><text>"
      val i = line.indexOf(subs)
      // Skip over "<page><title>
      val title = line.substring(14, i)
      // Then extract skipping over </title><text> and up to </text></page>",
      val text  = line.substring(i + subs.length, line.length-16)
      WikipediaArticle(title, text)
    }

    // Load and parse the data using our helpers
    sc.textFile(filePath).map(parse)
  }

  /**
    * Returns the number of articles on which the language `lang` occurs.
    */
  def occurrencesOfLang(lang: String, rdd: RDD[WikipediaArticle]): Int = {
    rdd.filter(_.mentionsLanguage(lang)).map((_,1)).aggregate(0)(
      (acc, value) => acc + value._2,
      (acc1, acc2) => acc1 + acc2
    )
  }

  /* (1) Use `occurrencesOfLang` to compute the ranking of the languages
   *     (`val langs`) by determining the number of Wikipedia articles that
   *     mention each language at least once. Don't forget to sort the
   *     languages by their occurrence, in decreasing order!
   *
   *   Note: this operation is long-running. It can potentially run for
   *   several seconds.
   */
  def rankLangs(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)] = {
    val countLangs = langs.map(lang => (lang, occurrencesOfLang(lang, rdd)))

    val sorted = countLangs.sortBy { case (lang, count) => count } (Ordering[Int].reverse)

    sorted
  }

  /* Compute an inverted index of the set of articles, mapping each language
   * to the Wikipedia pages in which it occurs.
   */
  def makeIndex(langs: List[String], rdd: RDD[WikipediaArticle]): RDD[(String, Iterable[WikipediaArticle])] = {
    rdd.flatMap { article =>
      langs.filter(lang => article.mentionsLanguage(lang)).map(lang => (lang, article))
    }.groupByKey()
  }

  /* (2) Compute the language ranking again, but now using the inverted index. Can you notice
   *     a performance improvement?
   *
   *   Note: this operation is long-running. It can potentially run for
   *   several seconds.
   */
  def rankLangsUsingIndex(index: RDD[(String, Iterable[WikipediaArticle])]): List[(String, Int)] = {
    index
        .map { case (lang, articles) =>
          (lang,articles.size)
        }
        .sortBy(_._2, ascending = false)
        .collect()
        .toList
  }

  /* (3) Use `reduceByKey` so that the computation of the index and the ranking are combined.
   *     Can you notice an improvement in performance compared to measuring *both* the computation of the index
   *     and the computation of the ranking? If so, can you think of a reason?
   *
   *   Note: this operation is long-running. It can potentially run for
   *   several seconds.
   */
  def rankLangsReduceByKey(langs: List[String], rdd: RDD[WikipediaArticle]): List[(String, Int)] = {
    rdd
        .flatMap { article =>
          val mentionedLangs = langs.filter { lang => article.mentionsLanguage(lang) }

          val a = mentionedLangs.map { lang =>
            (lang, 1)
          }

          a
        }
        .reduceByKey(_ + _)
        .sortBy(_._2, ascending = false)
        .collect()
        .toList
  }

  /** Main function */
  def main(args: Array[String]): Unit = {

    /* Languages ranked according to (1) */
    val langsRanked: List[(String, Int)] = timed("Part 1: naive ranking", rankLangs(langs, wikiRdd))

    /* An inverted index mapping languages to wikipedia pages on which they appear */
    def index: RDD[(String, Iterable[WikipediaArticle])] = makeIndex(langs, wikiRdd)

    /* Languages ranked according to (2), using the inverted index */
    val langsRanked2: List[(String, Int)] = timed("Part 2: ranking using inverted index", rankLangsUsingIndex(index))

    /* Languages ranked according to (3) */
    val langsRanked3: List[(String, Int)] = timed("Part 3: ranking using reduceByKey", rankLangsReduceByKey(langs, wikiRdd))

    /* Output the speed of each ranking */
    println(timing)
    sc.stop()
  }

  val timing = new StringBuffer
  def timed[T](label: String, code: => T): T = {
    val start = System.currentTimeMillis()
    val result = code
    val stop = System.currentTimeMillis()
    timing.append(s"Processing $label took ${stop - start} ms.\n")
    result
  }


}
