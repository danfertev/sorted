import akka.actor.Actor

/**
 * Denis Anfertev
 * 10/21/13 1:11 PM
 */
class IsSortedActor extends Actor {
  def receive = {
    case messages.IsSorted(n, xs, ord) => {
      val l = xs.length
      def step(k: Int): Boolean = {
        if (k == 0) true
        else if (ord(xs(k - 1), xs(k))) step(k - 1) else false
      }
      val result = if(l < 2) messages.TrueResult(n, xs) else if (step(l - 1)) messages.TrueResult(n, xs) else messages.FalseResult
      run.gatherActor ! result
    }
  }
}
