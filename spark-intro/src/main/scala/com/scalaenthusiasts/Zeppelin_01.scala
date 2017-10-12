package com.scalaenthusiasts

import com.scalaenthusiasts.RDD_01.sc
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SQLContext
import org.apache.spark.{SparkConf, SparkContext}

/**
  * @author Mark Kegel (mkegel@vast.com)
  */
object Zeppelin_01 {
  import org.apache.commons.io.IOUtils
  import java.net.URL
  import java.nio.charset.Charset

  // Zeppelin creates and injects sc (SparkContext) and sqlContext (HiveContext or SqlContext)
  // So you don't need create them manually

  /*
  val conf: SparkConf = new SparkConf()
      .setAppName("Spark_01")
      .setMaster("local[*]")
      .set("spark.executor.memory","4g")

  // The entry point to programming Spark with the RDD API.
  val sc: SparkContext = new SparkContext(conf)
  */

  import org.apache.spark.sql._
  import org.apache.spark.sql.types._
  import org.apache.spark.sql.SparkSession

  val sparkSession: SparkSession =
    SparkSession
        .builder()
        .appName("Zeppelin_01")
        .master("local[*]")
        .getOrCreate()

  val sc = sparkSession.sparkContext
  val sqlContext = sparkSession.sqlContext

  import sparkSession.implicits._

  // load bank data
  val bankText: RDD[String] = sc.parallelize(
    IOUtils.toString(
      new URL("https://s3.amazonaws.com/apache-zeppelin/tutorial/bank/bank.csv"),
      Charset.forName("utf8")).split("\n"))

  case class Bank(age: Integer, job: String, marital: String, education: String, balance: Integer)

  val bankRdd: RDD[Bank] = bankText
      .map(s => s.split(";"))
      .filter(s => s(0) != "\"age\"")
      // map has a ClassTag context bound, which is how the column names appear
      .map(
        s => Bank(s(0).toInt,
          s(1).replaceAll("\"", ""),
          s(2).replaceAll("\"", ""),
          s(3).replaceAll("\"", ""),
          s(5).replaceAll("\"", "").toInt
        )
      )

  val bank = bankRdd.toDF()

  bank.createOrReplaceTempView("bank")

  def main(args: Array[String]): Unit = {
    // initialize the app
    // do stuff with spark
    // stop spark once finished
    sc.stop()
  }
}
