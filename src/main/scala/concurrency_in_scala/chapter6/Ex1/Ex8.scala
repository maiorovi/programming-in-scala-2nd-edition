package concurrency_in_scala.chapter6.Ex1

import java.io.{File, FileInputStream, FileOutputStream}

import concurrency_in_scala.chapter6._
import rx.lang.scala.{Observable, Subscription}

import scala.concurrent.duration._

object Ex8 extends App {

  def copyFile(src:String, dest: String): Observable[Double] = {
    return Observable.apply[Double] { s =>
//      new Thread( () => {
      val file = new File((src))
      val srcFileStream = new FileInputStream(file)
      val outFileStream = new FileOutputStream(new File(dest))
      val b = new Array[Byte](5)

      val size = file.length()
      log("inner")
      var counter: Int = 0
      var readed: Int = 0
      while (readed != -1) {
        readed = srcFileStream.read(b)

//        Thread.sleep(10)
        if (readed != -1) {
          outFileStream.write(b, 0, readed)
          counter = counter + readed
          val percent = Math.round(counter.toDouble / size.toDouble * 100)
          //          log(s"copied - $percent%")
          s.onNext(percent)
        }
      }
//      }}).start()
        Subscription()
    }.sample(10.millisecond)
  }


  copyFile("/app/Projects/Programming-In-Scala/src/main/resources/more_input.txt",
  "/app/Projects/Programming-In-Scala/src/main/scala/concurrency_in_scala/chapter6/Ex1/tytyty.txt")
    .subscribe(q => log(q))
  log("here")
  Thread.sleep(10000)
}

object TestA extends App {


  Observable.interval(100.millisecond)
    .subscribe(i => log(i))

  Thread.sleep(10000)
}
