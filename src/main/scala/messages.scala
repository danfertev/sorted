/**
 * Denis Anfertev
 * 10/21/13 12:55 PM
 */
object messages {

  trait Message

  case class Partition(xs: Vector[Int]) extends Message

  case object StopSystem extends Message

  case class IsSorted(n: Int, xs: Vector[Int], ord: (Int, Int) => Boolean)

  case object FalseResult extends Message

  case class TrueResult(n: Int, xs: Vector[Int]) extends Message

  case class CompleteResult(b:Boolean) extends Message
}
