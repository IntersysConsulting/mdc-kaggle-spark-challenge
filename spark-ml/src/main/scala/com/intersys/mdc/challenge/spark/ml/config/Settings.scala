package com.intersys.mdc.challenge.spark.ml.config

import com.typesafe.config.{Config, ConfigFactory}
import collection.JavaConversions._

object Settings {
  private val app: Config = ConfigFactory.load().getConfig("application")
  object Problem1 {
    private val problem1: Config = app.getConfig("problem1")
    object Spark {
      private val spark: Config = problem1.getConfig("spark")
      val name: String = spark.getString("name")
      object Model {
        private val model: Config = spark.getConfig("model")
        val file: String      = model.getString("file")
        val cvFold: Int       = model.getInt("cvFold")
        val parallelism: Int  = model.getInt("parallelism")
        object Classification {
          private val classification: Config = model.getConfig("classification")
          object RandomForest {
            private val randomForest: Config = classification.getConfig("randomForest")
            val numTrees: Array[Int] = randomForest.getIntList("numTrees").toList.map(_.toInt).toArray
            val maxDepth: Array[Int] = randomForest.getIntList("maxDepth").toList.map(_.toInt).toArray
          }
          object LogisticReg {
        private val logisticReg: Config = classification.getConfig("logisticReg")
        val regParam: Array[Double] = logisticReg.getDoubleList("regParam").toList.map(_.toDouble).toArray
      }
    }
  }
  object Label {
    private val label: Config = spark.getConfig("label")
    val master: String = label.getString("master")
      }
      object Value {
        private val value: Config = spark.getConfig("value")
        val master: String = value.getString("master")
      }
    }
  }
  object Problem2 {
    private val problem2: Config = app.getConfig("problem2")
    object Openscoring {
      private val openscoring: Config = problem2.getConfig("openscoring")
      val address: String = openscoring.getString("address")
      val port: String = openscoring.getString("port")
      val name: String = openscoring.getString("name")
      val url: String = s"http://$address:$port/openscoring/model/$name"
    }

    object Spark {
      private val spark: Config = problem2.getConfig("spark")
      val name: String = spark.getString("name")
      object Label {
        private val label: Config = spark.getConfig("label")
        val master: String = label.getString("master")
      }
      object Value {
        private val value: Config = spark.getConfig("value")
        val master: String = value.getString("master")
      }
    }
  }
  object Data {
    private val data: Config  = app.getConfig("data")
    val rawData: String       = data.getString("rawData")
    val prediction: String    = data.getString("prediction")
    val onlinePrediction: String = data.getString("onlinePrediction")
  }
}
