import org.junit.runner.RunWith
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import scala.concurrent.Await


/**
 * Denis Anfertev
 * 10/21/13 6:08 PM
 */
@RunWith(classOf[JUnitRunner])
class SortedSpec extends Specification {
  import scala.concurrent.duration._
  val duration = Duration(30, "sec")

  import Sorted._
  "Array of Int" should {

    "be sorted ascending" in {
      Await.result(isSorted[Int](Array(1, 2, 3, 3, 5), _ - _ <= 0), duration)
    }
    "not be sorted ascending" in {
      !Await.result(isSorted[Int](Array(1, 2, 2, 5, 4), _ - _ <= 0), duration)
    }
    "be sorted descending" in {
      Await.result(isSorted[Int](Array(5, 3, 3, 2, 1), _ - _ >= 0), duration)
    }
    "not be sorted descending" in {
      !Await.result(isSorted[Int](Array(3, 5, 3, 2, 1), _ - _ >= 0), duration)
    }
    "with no element should be sorted" in {
      Await.result(isSorted[Int](Array[Int](), _ - _ >= 0), duration)
    }
    "with one element should be sorted" in {
      Await.result(isSorted[Int](Array(1), _ - _ < 0), duration)
    }
    "with 500k elements should be sorted ascending" in {
      Await.result(isSorted[Int]((1 to 500000).toArray, _ - _ < 0), duration)
    }
  }
}
