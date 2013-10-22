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
      Await.result(isSortedPar(Array(1, 2, 3, 3, 5), _ - _ <= 0), duration)
    }
    "not be sorted ascending" in {
      !Await.result(isSortedPar(Array(1, 2, 2, 5, 4), _ - _ <= 0), duration)
    }
    "be sorted descending" in {
      Await.result(isSortedPar(Array(5, 3, 3, 2, 1), _ - _ >= 0), duration)
    }
    "not be sorted descending" in {
      !Await.result(isSortedPar(Array(3, 5, 3, 2, 1), _ - _ >= 0), duration)
    }
    "with no element should be sorted" in {
      Await.result(isSortedPar(Array[Int](), _ - _ >= 0), duration)
    }
    "with one element should be sorted" in {
      Await.result(isSortedPar(Array(1), _ - _ < 0), duration)
    }
    "with 500k elements should be sorted ascending" in {
      Await.result(isSortedPar((1 to 500000).toArray, _ - _ < 0), duration)
    }
    "with 10kk elements should not be sorted ascending" in {
      !Await.result(isSortedPar((0 to 9999999).map(x => if (x == 625000) 0 else x).toArray, _ - _ < 0), duration)
    }
  }
}
