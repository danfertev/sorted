import akka.actor.{Props, Actor}

/**
 * Denis Anfertev
 * 10/21/13 12:54 PM
 */


class PartitionActor extends Actor {
  def receive = {
    case messages.Partition(xs) => {
      if (xs.length <= globals.NodeLength) {
        println("Vector length is to small.")
        run.systemActor ! messages.StopSystem
      } else {
        val sizeOfNode = xs.size / (xs.size / globals.NodeLength + 1)
        println(s"The size of node is $sizeOfNode")
        val nodes = xs.sliding(sizeOfNode,sizeOfNode)
        nodes.foldLeft(0)((z,vs) => {
          context.actorOf(Props[IsSortedActor]) ! messages.IsSorted(z,vs, globals.Ordering)
          z + 1
        })
      }
    }
  }
}

