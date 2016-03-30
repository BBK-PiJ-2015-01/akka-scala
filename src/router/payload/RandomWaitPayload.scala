package router.payload

import scala.util.Random

case class RandomWaitPayload(val payload: Int = 1000) extends Payload {

  val ratio = 3
  val minSleepTime = 50
  val r = new Random()

  override def perform = {
    var sleepTime = r.nextInt(payload)
    sleepTime = if (sleepTime % ratio == 0) minSleepTime else sleepTime
    Thread.sleep(r.nextInt(sleepTime))
  }
}