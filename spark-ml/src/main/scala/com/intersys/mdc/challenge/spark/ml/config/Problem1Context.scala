package com.intersys.mdc.challenge.spark.ml.config

import org.apache.spark.sql.SparkSession

trait Problem1Context {
  val spark: SparkSession = SparkSession.builder
    .appName(Settings.Problem1.Spark.name)
    .config(Settings.Problem1.Spark.Label.master, Settings.Problem1.Spark.Value.master)
    .getOrCreate()
}
