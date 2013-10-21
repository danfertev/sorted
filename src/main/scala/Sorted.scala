import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Denis Anfertev
 * 10/21/13 4:26 PM
 */
object Sorted {
  def isSorted[A](xs: Seq[A], ord: (A, A) => Boolean): Future[Boolean] = {
    type Indexes = (Int, Int)
    def slice: Seq[Indexes] = {
      val factor = xs.size / 16
      if (factor < 2) Seq((0, xs.size - 1))
      else {
        val ix = 0 until xs.size - 1 by factor
        (if (ix.last != xs.size - 1) ix :+ (xs.size - 1) else ix).sliding(2).toSeq.map(i => (i(0), i(1)))
      }
    }

    def isSliceSorted(ix: Indexes) = {
      val (start, end) = ix
      val n = end - start
      def step(k: Int): Boolean = (k == 0) || (ord(xs(k - 1), xs(k)) && step(k - 1))
      (n < 2) || step(n)
    }

    def par(indexes: Seq[Indexes]): Seq[Future[Boolean]] = {
      indexes map {
        ix => Future(isSliceSorted(ix))
      }
    }

    val result = par(slice)

    def track(fs: Seq[Future[Boolean]]): Future[Boolean] = {
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
