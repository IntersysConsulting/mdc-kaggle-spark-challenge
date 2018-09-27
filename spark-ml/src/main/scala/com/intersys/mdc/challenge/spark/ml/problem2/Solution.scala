package com.intersys.mdc.challenge.spark.ml.problem2

import com.intersys.mdc.challenge.spark.ml.config._
import com.intersys.mdc.challenge.spark.ml.problem2.model.{OfflinePrediction, OnlinePrediction}
import org.apache.spark.sql._
import org.apache.spark.sql.types.StructType

object Solution extends Problem2Context {
  import spark.implicits._

  val schema: StructType = Encoders.product[OfflinePrediction].schema

  val offline: Dataset[OfflinePrediction.WithId] =
    spark.read.option("header", value=true).schema(schema).csv(Settings.Data.prediction + "/*.csv")
      .sample(withReplacement=false, fraction=0.05)
      .as[OfflinePrediction]
      .map(_.withId)
      .cache()

  val online: Dataset[OnlinePrediction.Flat] =
    offline.map(_.send.get.flatten).cache()

  def main(args: Array[String]): Unit = {
    val data = offline.join(online).where(
      offline.col("offlineId") === online.col("id"))
    data.coalesce(1).write.option("header", value=true).csv(Settings.Data.onlinePrediction)
    spark.stop()
  }
}
