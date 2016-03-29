package integral

import scala.concurrent.duration._

sealed trait IntegralMessage
case object Execute extends IntegralMessage
case object Finalize extends IntegralMessage
case class Integrate(iterations: Int, f:Double => Double) extends IntegralMessage
case class Result(value: Double) extends IntegralMessage

case class ResultAccumulation(value: Double)