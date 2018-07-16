package com.intersys.mdc.challenge.spark.batch

import org.apache.spark.sql.SparkSession

object Test {

  def main(args: Array[String]): Unit = {
    val testFile = "/mnt/c/Users/rhdzm/Documents/Github/intersys/mdc-spark-challenge/spark-batch/data/ks-projects-201801.csv"
    val spark = SparkSession.builder.appName("Simple Application").getOrCreate()
    val data = spark.read.format("csv").option("header", "true").load(testFile).cache()

    println(data.show())
    spark.stop()
  }
}
