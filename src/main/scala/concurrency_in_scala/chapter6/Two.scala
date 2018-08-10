package concurrency_in_scala.chapter6

import rx.lang.scala.Observable

import scala.collection.parallel.mutable
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

object Two1 extends App {
  val odds = Observable.interval(0.5.seconds)
    .filter(_ % 2 == 1).map(n => s"num $n").take(5)

  odds.subscribe(
    z => log(z), e => log(s"unexpected $e"), () => log("no more odds"))
  Thread.sleep(4000)

}


object Three1 extends App {
  def fetchQuote(): Future[String] = Future {
      val url = "http://quotes.stormconsultancy.co.uk/random.json" + "?show_permalink=false&show_source=false"
      Source.fromURL(url).getLines.mkString
  }

  def fetchQuotesObservable(): Observable[String] = {
    log("fetching quotes!")
    Observable.from(fetchQuote())
  }

  def quotes: Observable[Observable[String]] = Observable.interval(0.5.seconds).take(4).map {
    n => fetchQuotesObservable().map(txt => s"$n) $txt")
  }

  var values = Set.empty[Int]

  def countAverage(): Double = values.sum / values.size

//  val avg = quotes.flatMap(q => {
//    q.map(s => {
//      values += s.length
//      countAverage()
//    })
//  })

//  avg.subscribe(avg => println(s">>>> avg is ${avg}"))

  val avg = quotes
    .flatten
    .scan((0L, 0)) {
      case (p, q) => (p._1 + q.length, p._2 + 1)
    }
      .tail
      .map(p => p._1 / p._2)
      .subscribe(avg => println(s">>>> avg is ${avg}"))


  log(s"Using concat")
  quotes.concat.subscribe(x => log(x))
  Thread.sleep(6000)
  log(s"Now using flatten")
  quotes.flatten.subscribe(x => log(x))
  Thread.sleep(6000)
}

object CompositionErrors extends App {
  val status = Observable.items("ok", "still ok") ++ Observable.error(new Exception)
  val fixedStatus =
    status.onErrorReturn(e => "exception occurred.")
  fixedStatus.subscribe(log(_))
  val continuedStatus =
    status.onErrorResumeNext(e => Observable.items("better", "much better"))
  continuedStatus.subscribe(log(_))
}
