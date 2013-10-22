import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Denis Anfertev
 * 10/21/13 4:26 PM
 */
object Sorted {
  def isSortedPar(xs: Array[Int], ord: (Int, Int) => Boolean): Future[Boolean] = {
    val size = xs.size
    type Index = (Int, Int)
    def slice: Array[Index] = {
      val factor = size / 32
      if (factor < 2) Array((0, size - 2))
      else {
        val ix = 0 until size - 1 by factor
        (if (ix.last != size - 2 || ix.last == size - 1) ix :+ (size - 2) else ix).sliding(2).toArray.map(i => (i(0), i(1)))
      }
    }

    def isSliceSorted(ix: Index) = {
      val (start, end) = ix
      val n = end - start

      def step(k: Int): Boolean = (k == 0) || (ord(xs(k + start - 1), xs(k + start)) && step(k - 1))

      (n < 2) || step(n + 1)
    }

    def par(indexes: Array[Index]): Array[Future[Boolean]] = {
      indexes map {
        ix => Future(isSliceSorted(ix))
      }
    }

    val result = par(slice)

    def track(fs: Array[Future[Boolean]]): Future[Boolean] = {
      if (fs.isEmpty) Future(true)
      else {
        val first = Future.firstCompletedOf(result)
        first.flatMap {
          r => if (r) track(fs.filter(f => first == f)) else Future(false)
        }
      }
    }
    track(result)
  }
}
