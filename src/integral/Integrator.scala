package integral

import akka.actor._
import scala.util._
import scala.math.pow

class Integrator extends Actor{
  
    // calculatePiFor ...

  def receive = {
    case Integrate(iterations, f) ⇒
      sender ! Result(iterate(iterations, f)) // perform the work
  }
  
  def iterate( iterations: Int, f:Double => Double): Double = {
    
    var accumulation: Double = 0.0
    val r = Random
    
//    iterations.until(0,  -1).foreach(i =>  accumulation += pow(r.nextDouble(),3))
    iterations.until(0,  -1).foreach(i =>  accumulation += f(r.nextDouble()))
        
    accumulation / iterations
  }
}