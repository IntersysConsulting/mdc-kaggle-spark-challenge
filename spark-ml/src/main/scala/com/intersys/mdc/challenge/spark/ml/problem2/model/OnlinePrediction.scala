package com.intersys.mdc.challenge.spark.ml.problem2.model

import io.circe.Decoder
import io.circe.generic.semiauto.deriveDecoder
import io.circe.parser.decode

final case class OnlinePrediction(id: String, result: OnlinePrediction.Result) {
  def flatten: OnlinePrediction.Flat = OnlinePrediction.Flat(id, result.categ_label, result.fail, result.success)
}

object OnlinePrediction {
  import Result._
  final case class Result(categ_label: String, pmmlprediction: String, prediction: Double, fail: Double, success: Double)
  object Result {
    implicit val decodeResult: Decoder[Result] = deriveDecoder
  }

  final case class Flat(id: String, online_prediction: String, online_prediction_fail: Double, online_prediction_success: Double)

  implicit val decodeOnlinePrediction: Decoder[OnlinePrediction] = deriveDecoder

  def fromString(string: String): Option[OnlinePrediction]= {
    val opString = string.replace("probability", "")
      .replace("(", "")
      .replace(")", "")
    decode[OnlinePrediction](opString) match {
      case Right(op)  => Some(op)
      case Left(e)    => None
    }
  }
}
