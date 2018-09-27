package com.intersys.mdc.challenge.spark.ml.config

import org.apache.spark.sql.SparkSession
import akka.actor.ActorSystem
import akka.stream.{ActorMaterializer, Materializer}

import scala.concurrent.ExecutionContext

trait Problem2Context {
  val spark: SparkSession = SparkSession.builder
    .appName(Settings.Problem2.Spark.name)
    .config(Settings.Problem2.Spark.Label.master, Settings.Problem2.Spark.Value.master)
    .getOrCreate()
  implicit val actorSystem: ActorSystem = ActorSystem()
  implicit val executionContext: ExecutionContext = actorSystem.dispatcher
  implicit val materializer: Materializer = ActorMaterializer()
}
