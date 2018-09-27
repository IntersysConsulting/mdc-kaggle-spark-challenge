package com.intersys.mdc.challenge.spark.ml.problem1.model

import java.io.BufferedWriter
import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths, StandardOpenOption}

import com.intersys.mdc.challenge.spark.ml.config.Settings
import javax.xml.transform.stream.StreamResult
import org.apache.spark.ml.evaluation.BinaryClassificationEvaluator
import org.apache.spark.ml.feature.IndexToString
import org.apache.spark.ml.param.ParamMap
import org.apache.spark.ml.tuning.CrossValidator
import org.apache.spark.ml.{Pipeline, PipelineModel}
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.{Dataset, Row}
import org.dmg.pmml.PMML
import org.jpmml.model.JAXBUtil
import org.jpmml.sparkml.PMMLBuilder

import scala.util.Try

object Model {

  object Implicits {
    implicit class PMMLModel(pipelineModel: PipelineModel) {
      val precision = 0.01
      val zeroThreshold = 1e-5
      def toPMML(schema: StructType, verificationData: Option[Dataset[Row]] = None): PMML = {
        val pmmlBuilder = new PMMLBuilder(schema, pipelineModel)
        if (verificationData.nonEmpty) pmmlBuilder.verify(verificationData.get, precision, zeroThreshold).build()
        else pmmlBuilder.build()
        pmmlBuilder.build()
      }
      def exportPMML(schema: StructType, score: Double, verificationData: Option[Dataset[Row]] = None): Boolean = {
        val modelPath: Path = Paths.get("resources", "output","model")
        if (!Files.exists(modelPath)) Files.createDirectory(modelPath)
        val pmmlFile: Path = modelPath.resolve(
          Settings.Problem1.Spark.Model.file.replaceAll("score", (100 * score).toInt.toString))
        val modelWriter: BufferedWriter = Files.newBufferedWriter(pmmlFile, StandardCharsets.UTF_8,
          StandardOpenOption.WRITE,
          StandardOpenOption.CREATE,
          StandardOpenOption.TRUNCATE_EXISTING)
        val pmml: PMML = pipelineModel.toPMML(schema, verificationData)
        pmml.addExtensions()
        val writeOp = Try(JAXBUtil.marshalPMML(pmml, new StreamResult(modelWriter))).toOption
        modelWriter.close()
        writeOp.nonEmpty
      }
    }
  }

  object Classification {
    val evaluator: BinaryClassificationEvaluator = new BinaryClassificationEvaluator()
      .setLabelCol("label")

    case class GridSearch(
                           processing: Pipeline,
                           labelConverter: IndexToString,
                           crossValidationFolds: Int = 1,
                           parallelism: Int = 1
                         ) {
      val basePipeline = new Pipeline()

      // Machine-learning Classification models
      val randomForest: classification.RandomForest =
        new classification.RandomForest(basePipeline, processing, labelConverter)
      val logisticReg:  classification.LogisticReg  =
        new classification.LogisticReg(basePipeline, processing, labelConverter)

      // Parameter grid for random search
      val paramGrid: Array[ParamMap] =
        randomForest.paramGrid ++ logisticReg.paramGrid

      // Cross validation technique
      val crossValidator: CrossValidator = new CrossValidator()
        .setEstimator(basePipeline)
        .setEvaluator(Model.Classification.evaluator)
        .setEstimatorParamMaps(paramGrid)
        .setNumFolds(crossValidationFolds)
        .setParallelism(parallelism)

      def fit(training: Dataset[Row]): PipelineModel =
        crossValidator.fit(training).bestModel.asInstanceOf[PipelineModel]
    }
  }
}
