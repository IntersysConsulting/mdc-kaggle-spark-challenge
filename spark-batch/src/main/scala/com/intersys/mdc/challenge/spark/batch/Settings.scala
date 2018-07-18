package com.intersys.mdc.challenge.spark.batch

import java.io.File

import com.typesafe.config.{Config, ConfigFactory}

object Settings {
  private val app: Config = ConfigFactory.load().getConfig("application")

  object Spark {
    private val spark: Config = app.getConfig("spark")
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

  object Http {
    private val http: Config = app.getConfig("http")
    val host: String = http.getString("host")
    val port: Int    = http.getInt("port")
  }

  object Data {
    private val data: Config = app.getConfig("data")
    val dirPath: java.nio.file.Path = java.nio.file.Paths.get(System.getProperty("user.dir"), data.getString("dir"))
    val file: Option[File] = new File(dirPath.toString)
      .listFiles().headOption.flatMap(file => if (file.toString.endsWith(".csv")) Some(file) else None)
  }
}
