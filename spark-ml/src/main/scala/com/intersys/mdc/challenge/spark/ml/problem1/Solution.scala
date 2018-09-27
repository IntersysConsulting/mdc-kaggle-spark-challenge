package com.intersys.mdc.challenge.spark.ml.problem1

import com.intersys.mdc.challenge.spark.ml.config._
import com.intersys.mdc.challenge.spark.ml.problem1.data.DataProcessing
import com.intersys.mdc.challenge.spark.ml.problem1.model.Model
import com.typesafe.scalalogging.LazyLogging
import org.apache.spark.ml.PipelineModel
import org.apache.spark.sql._

object Solution extends Problem1Context with LazyLogging {
  import Settings.Problem1.Spark.Model._
  import Model.Implicits._

  // Get best model using grid-search and cross-validation
  val model: PipelineModel =
    Model.Classification.GridSearch(DataProcessing.pipeline, DataProcessing.Labels.labelConverter, cvFold, parallelism)
      .fit(DataProcessing.training)

  // Generate predictions for the test dataset
  val predictions: Dataset[Row] = model.transform(DataProcessing.test)
  val verificationData: Dataset[Row] = predictions
    .select("predicted_label", "categ_label",
      "main_category", "currency", "goal", "pledged", "backers", "country", "date_diff")
    .withColumnRenamed("predicted_label", "expected_predicted_label")

  def main(args: Array[String]): Unit = {

    // Save predictions
    verificationData
      .coalesce(1).write.option("header", value=true).csv(Settings.Data.prediction)

    // Get metric: area under the ROC curve
    val areaUnderROC = Model.Classification.evaluator.setMetricName("areaUnderROC") evaluate predictions
    logger.info("[Problem 2][Task 1] Best model performance: {}", areaUnderROC)

    // Save model with the PMML format
    val exportedStatus = model.exportPMML(
      DataProcessing.rawData.schema, areaUnderROC,
      Some(verificationData.sample(withReplacement = false, fraction = 0.01)))
    logger.info("[Problem 2][Task 1] Model PMML exporting procedure", if (exportedStatus) "SUCCESS" else "FAILURE")
    if (!exportedStatus) logger.warn("[Problem 2][Task 1] Model couldn't be exported to PMML.")

    spark.stop()
  }

}