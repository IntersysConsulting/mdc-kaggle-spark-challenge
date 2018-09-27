package com.intersys.mdc.challenge.spark.ml.problem1.model.classification

import com.intersys.mdc.challenge.spark.ml.config.Settings
import org.apache.spark.ml.Pipeline
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.feature._
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.tuning.ParamGridBuilder

class RandomForest(basePipeline: Pipeline, processing: Pipeline, labelConverter: IndexToString) {
  import Settings.Problem1.Spark.Model.Classification.RandomForest._
  private val rf: RandomForestClassifier = new RandomForestClassifier()
    .setLabelCol("label")
    .setFeaturesCol("features")
    .setMaxBins(400)

  val paramGrid: Array[ParamMap] = new ParamGridBuilder()
    .baseOn(basePipeline.stages -> (processing.getStages :+ rf :+ labelConverter))
    .addGrid(rf.numTrees, numTrees)
    .addGrid(rf.maxDepth, maxDepth)
    .build()
}
