package concurrency_in_scala.chapter6

import java.io.File

import org.apache.commons.io.monitor.{FileAlterationListenerAdaptor, FileAlterationMonitor, FileAlterationObserver}
import rx.lang.scala._

import scala.concurrent.Future
import scala.concurrent.duration._

object One extends App {
  val o = Observable.items("Pascal", "Java", "Scala")

  o.subscribe(name => log(s"learned the $name language"))
  o.subscribe(name => log(s"forgot the $name language"))
}

object Two extends App {
  val o = Observable.timer(1.second)

  o.subscribe(_ => log("Timeout!"))
  o.subscribe(_ => log("Another Timeout!"))

  Thread.sleep(2000)
}


object Three extends App {
  val exc = new RuntimeException

  val o = Observable.items(1,2) ++ Observable.error(exc)

  o.subscribe(
    x => log(s"number $x"),
    t => log(s"an error occured: $t")
  )
}

object Four extends App {
  val vms = Observable.apply[String] { obs =>
    obs.onNext("JVM")

    obs.onNext("DartVm")

    obs.onNext("V8")

    obs.onCompleted()

    Subscription()
  }

  vms.subscribe( x => log(x), e => log(s"ooops - $e"), () => log("Done!"))
  vms.subscribe( x => log(x), e => log(s"ooops - $e"), () => log("Done!"))
}

object Five extends App {
  import scala.concurrent.ExecutionContext.Implicits.global

  val f = Future {
    "future is here"
  }

  val vms = Observable.apply[String] { obs =>
    f.foreach(value => { obs.onNext(value); obs.onCompleted()})
    f.failed foreach (t => obs.onError(t))
  }
}

object Six extends App {

  def modified(directory: String): Observable[String] = {
    println(new File(directory).getAbsolutePath)
    Observable.apply { obs =>
      val fileMonitor = new FileAlterationMonitor(1000)
      val fileObs = new FileAlterationObserver(directory)
      val fileLis = new FileAlterationListenerAdaptor {
        override def onFileChange(file: File): Unit = {
          obs.onNext(file.getName)
        }
      }

      fileObs.addListener(fileLis)
      fileMonitor.addObserver(fileObs)

      fileMonitor.start()

      Subscription { fileMonitor.stop() }
    }
  }

  log(s"starting to monitor files")
  val sub = modified(".").subscribe(n => s"$n modified!")
  log(s"please modify and save a file")
  Thread.sleep(10000)
  sub.unsubscribe()
  log(s"monitoring done")
}