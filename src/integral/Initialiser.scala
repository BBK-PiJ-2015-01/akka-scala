package integral

import scala.concurrent.duration.DurationLong
import akka.actor._
import akka.routing._
import scala.math._

class Initialiser(workers: Int, samples: Int, iterations: Int, finalizer: ActorRef) extends Actor {

  var results: Int = _


  // Create a pool of workers of type Integrator
  val workerRouter = context.actorOf(Props[Integrator].withRouter(RoundRobinPool(workers)), name = "workerRouter")

  def receive = {
    case Execute ⇒
      for (i ← 0 until samples) workerRouter ! Integrate(iterations, d => pow(d,2))

    case Result(value) ⇒
     
      finalizer ! ResultAccumulation(value )
      results += 1
      
      if (results == samples) {
        // Finalize
        finalizer ! Finalize
        // Stops this actor and all its supervised children
        context stop self
      }
  }

}