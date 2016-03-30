package router.actor

import router.Finalize
import akka.actor.Actor

class FinalizerActor extends Actor {

  def receive = {

    case Finalize ⇒ context.system.shutdown()
  }
}