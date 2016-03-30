package router

import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.actorRef2Scala
import akka.routing.SmallestMailboxPool
import router.actor.FinalizerActor
import router.actor.RouterActor
import akka.routing._


object RouterApp extends App {

  runApp(workers = 12, payload =1000, iterations = 500)

  def runApp(workers: Int, payload:Int, iterations: Int) {

    // Create the system
    val system = ActorSystem("RouterSystem")

    // create the finaliser, which will print the result and shutdown the system
    val finalizer = system.actorOf(Props[FinalizerActor], name = "finalizer")

    // Create a pool of workers 
    // RoundRobinPool
    // BalancingPool
    // SmallestMailboxPool
    val routerPool = BalancingPool(workers)

    // create the router actor
    val router = system.actorOf(Props(new RouterActor(iterations, payload, routerPool, finalizer)),
      name = "router")

    // start the calculation
    router ! Initialize
  }

}