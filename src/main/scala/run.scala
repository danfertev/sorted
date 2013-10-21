import akka.actor.{Props, ActorSystem}

/**
 * Denis Anfertev
 * 10/21/13 1:02 PM
 */
object run extends App {
  val system = ActorSystem()
  val systemActor = system.actorOf(Props[SystemActor])
  val xs = (0 until 1000000).toVector.map(x => if(x == 999888) 1000000 else x)
  system.actorOf(Props[PartitionActor]) ! messages.Partition(xs)
  val gatherActor = system.actorOf(Props[GatherActor])
}
