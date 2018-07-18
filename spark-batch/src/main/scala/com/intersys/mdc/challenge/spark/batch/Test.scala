package com.intersys.mdc.challenge.spark.batch

import org.apache.spark.sql.{DataFrame, SparkSession}

object Test {

  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder
      .appName(Settings.Spark.name)
      .config(Settings.Spark.Label.master, Settings.Spark.Value.master)
      .getOrCreate()

    Settings.Data.file match {
      case None       =>
        println("Data missing.")
      case Some(file) =>
        println(file.toString)
        val data: DataFrame = spark.read.option("header", "true").csv(file.toString).cache()
        println(data.show())
    }
    //val data = spark.read.format("csv").option("header", "true").load(testFile).cache()

    //println(data.show())
    spark.stop()
  }
}
