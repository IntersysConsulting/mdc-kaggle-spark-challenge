package com.intersys.mdc.challenge.spark.ml.problem2.model

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpMethods, HttpRequest}
import akka.http.scaladsl.unmarshalling.Unmarshal
import io.circe.Encoder
import io.circe.generic.semiauto.deriveEncoder
import io.circe.syntax._
import akka.stream.Materializer
import com.intersys.mdc.challenge.spark.ml.config.Settings
import com.intersys.mdc.challenge.spark.ml.problem2.model

import scala.concurrent.{Await, ExecutionContext, Future}
import scala.concurrent.duration.Duration

final case class OfflinePrediction(
                              expected_predicted_label: String,
                              categ_label: String,
                              main_category: String,
                              currency: String,
                              goal: Double,
                              pledged: Double,
                              backers: Double,
                              country: String,
                              date_diff: Double
                            ) {
  def withId: OfflinePrediction.WithId = model.OfflinePrediction.WithId(
    java.util.UUID.randomUUID().toString, expected_predicted_label, categ_label,
    main_category, currency, goal, pledged, backers, country, date_diff)
}

object OfflinePrediction {
  implicit val encodeOfflinePrediction: Encoder[OfflinePrediction] = deriveEncoder[OfflinePrediction]

  final case class WithId(
                           offlineId: String,
                           expected_predicted_label: String,
                           categ_label: String,
                           main_category: String,
                           currency: String,
                           goal: Double,
                           pledged: Double,
                           backers: Double,
                           country: String,
                           date_diff: Double
                         ) {
    val url: String = Settings.Problem2.Openscoring.url
    def sendAsync(
                   implicit actorSystem: ActorSystem,
                   executionContext: ExecutionContext,
                   materializer: Materializer): Future[Option[OnlinePrediction]] = for {
      httpResponse <- Http(actorSystem).singleRequest(HttpRequest(
        HttpMethods.POST, url).withEntity(ContentTypes.`application/json`,
        OfflinePrediction.Request.fromOfflinePredictionWithId(this).toJson))
      stringContent <- Unmarshal(httpResponse.entity).to[String]
    } yield if (httpResponse.status.isSuccess()) OnlinePrediction.fromString(stringContent) else None

    def send(
              implicit  actorSystem: ActorSystem,
              executionContext: ExecutionContext,
              materializer: Materializer): Option[OnlinePrediction] = Await.ready(
      sendAsync, Duration.Inf).value.flatMap(_.toOption).flatten
  }

  final case class Request(id: String, arguments: Request.Arguments) {
    def toJson: String = Request.toJson(this)
  }

  object Request {
    //import Arguments._
    implicit val encodeRequest: Encoder[Request] = deriveEncoder
    final case class Arguments(main_category: String, currency: String, goal: Double, pledged: Double, backers: Double, country: String, date_diff: Double)
    object Arguments {
      implicit val encodeArguments: Encoder[Arguments] = deriveEncoder
    }
    def fromOfflinePredictionWithId(op: OfflinePrediction.WithId): Request = Request(
      op.offlineId, Arguments(op.main_category, op.currency, op.goal, op.pledged, op.backers, op.country, op.date_diff))
    def toJson(req: Request): String = req.asJson.toString
  }

  def toJson(op: OfflinePrediction): String = op.asJson.toString
}
