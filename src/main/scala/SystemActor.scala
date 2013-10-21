import akka.actor.Actor

/**
 * Denis Anfertev
 * 10/21/13 12:58 PM
 */
class SystemActor extends Actor {
  def receive = {
    case messages.StopSystem => {
      println("System is going to shutdown.")
      context.system.shutdown()
    }
  }
}
