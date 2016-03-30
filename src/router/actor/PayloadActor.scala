package router.actor

import router.payload.Payload
import akka.actor.Actor
import router.Perform
import router.Complete

case class PayloadActor(val payload: Payload) extends Actor {

  def receive = {
    case Perform ⇒ {
      payload.perform // perform the work
      sender ! Complete
    }
  }
}