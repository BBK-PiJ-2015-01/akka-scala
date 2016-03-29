package integral

import akka.actor._
import scala.concurrent.duration._

class Finalizer extends Actor {

  var results: Int = _
  var accumulator: Double = _
  val startTime: Long = System.currentTimeMillis

  def receive = {
    case ResultAccumulation(value) ⇒
      accumulator += value
      results += 1
    case Finalize ⇒

      println("\n\tIntegration result: \t%s\n\tCalculation time: \t%s"
        .format(accumulator / results, (System.currentTimeMillis - startTime) millis))
      
      context.system.shutdown()
  }

}