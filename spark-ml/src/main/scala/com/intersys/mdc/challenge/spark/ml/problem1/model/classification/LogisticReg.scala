package com.intersys.mdc.challenge.spark.ml.problem1.model.classification

import com.intersys.mdc.challenge.spark.ml.config.Settings
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.LogisticRegression
import org.apache.spark.ml.feature.IndexToString
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.tuning.ParamGridBuilder

class LogisticReg(basePipeline: Pipeline, processing: Pipeline, labelConverter: IndexToString) {
  import Settings.Problem1.Spark.Model.Classification.LogisticReg._

  private val lr: LogisticRegression = new LogisticRegression()
    .setLabelCol("label")
    .setFeaturesCol("features")

  val paramGrid: Array[ParamMap] = new ParamGridBuilder()
    .baseOn(basePipeline.stages -> (processing.getStages :+ lr :+ labelConverter))
    .addGrid(lr.regParam, regParam)
    .build()
}
