import akka.actor.Actor
import scala.collection.mutable

/**
 * Denis Anfertev
 * 10/21/13 1:20 PM
 */
class GatherActor extends Actor {
  var map = new mutable.HashMap[Int, Vector[Int]]()

  def receive = {
    case messages.FalseResult => {
      println("Some node is not sorted. Is sorted: false.")
      run.systemActor ! messages.StopSystem
    }
    case messages.TrueResult(n, xs) => {
      map += (n -> xs)
      if (map.values.foldLeft(0)(_ + _.size) == run.xs.size) {
        val keys = map.keys.toIndexedSeq.sortWith(_ < _)
        val merge = for (k <- keys) yield {
          if (k < keys.size - 1) globals.Ordering(map(k).last, map(k + 1).head) else true

        }
        println(s"Is sorted: ${merge.forall(x => x)}")
        run.systemActor ! messages.StopSystem
      }
    }
  }

}
