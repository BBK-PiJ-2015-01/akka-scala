package integral

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala

object Integral extends App {

  runIntegral(workers = 4, samples = 100000, iterations = 100000)

  def runIntegral(workers: Int, samples: Int, iterations: Int) {

    // Create the system
    val system = ActorSystem("IntegralSystem")

    // create the finaliser, which will print the result and shutdown the system
    val finalizer = system.actorOf(Props[Finalizer], name = "finalizer")

    // create the master
    val initializer = system.actorOf(Props(new Initialiser(workers, samples, iterations, finalizer)),
      name = "initializer")

    // start the calculation
    initializer ! Execute
  }
}