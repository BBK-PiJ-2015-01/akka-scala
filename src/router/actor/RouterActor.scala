package router.actor

import akka.actor.Actor
import akka.actor.Props
import akka.routing.RoundRobinPool
import router.payload.RandomWaitPayload
import router.Initialize
import integral.Integrate
import router.Perform
import router.Complete
import akka.actor.ActorRef
import router.Finalize
import akka.routing.BalancingPool
import akka.routing.SmallestMailboxPool
import akka.routing.Pool

class RouterActor(iterations: Int, payload:Int,routerPool:Pool, finalizer: ActorRef) extends Actor {

  // count of Complete messages
  var completed: Int = _
  var startTime:Long = System.currentTimeMillis()


  val payloadRouter = context.actorOf(Props(classOf[PayloadActor], new RandomWaitPayload(payload))
    .withRouter(routerPool), name = "payloadRouter")

  def receive = {
    
    case Initialize ⇒ 
      for (i ← 0 until iterations) payloadRouter ! Perform
      println(s"Starting ${iterations} iterations")
    
    case Complete ⇒
      completed += 1
//      println(s"Completed: ${completed}")
      if (completed == iterations) {

        println(s"Elapsed time ${System.currentTimeMillis() - startTime} (ms)")
        
        finalizer ! Finalize
        // Stops this actor and all its supervised children
        context stop self
      }
  }

}