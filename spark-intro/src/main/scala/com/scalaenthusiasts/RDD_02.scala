package com.scalaenthusiasts

import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.{SparkConf, SparkContext}

import scala.util.Random

object RDD_02 extends LazyLogging {

  /**
    * RDD Overview
    *  - primary Spark abstraction
    *  - distributed, parallel, partitioned, immutable, type-safe arrays
    *  - potentially very large
    *    - usually in-memory, can spill to disk
    *  - works like a Scala collection
    *  - one task per partition
    *  - computations opaque to Spark
    *    - not easily optimized
    *  - data unoptimized by Spark
    *  - no support for SQL operations
    *  - Dataset and Dataframe are the new hotness that support SQL
    *  - nicely support general distributed / fault-tolerant computation
    */

  val conf: SparkConf = new SparkConf()
      .setAppName("Spark_02")
      .setMaster("local[*]")
      .set("spark.executor.memory","2g")

  // The entry point to programming Spark with the RDD API.
  val sc: SparkContext = new SparkContext(conf)

  /** Main function */
  def main(args: Array[String]): Unit = {
    val ints = Array.ofDim[Int](1000 * 1000 * 10)

    val initialRdd = sc.parallelize(ints)

    val rand = new Random()

    val filledRdd = initialRdd.map { i:Int =>
      rand.nextInt()
    }

    val sortedRdd = filledRdd.sortBy(identity)

    logger.warn(s"Sorted values: ${sortedRdd.take(10).mkString(", ")}")

    //sc.stop()
  }
}
