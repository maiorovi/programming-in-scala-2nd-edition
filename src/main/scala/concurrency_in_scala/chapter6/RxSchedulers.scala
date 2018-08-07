package concurrency_in_scala.chapter6

import java.util.concurrent

import org.apache.commons.io.input.ObservableInputStream.Observer
import rx.lang.scala.{Observable, Scheduler, Subscription}
import rx.lang.scala.schedulers._


import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.io.Source

object RxSchedulers extends App {

  val scheduler = ComputationScheduler()

  val numbers = Observable.from(0 until 20)

  numbers.subscribe(n => log(s"num $n"))
  numbers.observeOn(scheduler).subscribe(n => log(s"num $n"))
  Thread.sleep(5000)
}


import scala.swing._
import scala.swing.event._


object SchedulerSwing extends SimpleSwingApplication {


  override def top = new MainFrame {
    title = "Swing Observables"

    val button = new Button {
      text = "Click"
    }

    contents = button

    val buttonClicks = Observable.apply[Button] { obs =>
      button.reactions += {
        case ButtonClicked(_) => obs.onNext(button)
      }
      Subscription()
    }

    buttonClicks.subscribe(btn => log("button clicked"))
  }
}


abstract class BrowserFrame extends MainFrame {
  import java.util.concurrent.Executor
  import rx.schedulers.Schedulers.{from => fromExecutor}
  import javax.swing.SwingUtilities.invokeLater
  title = "MiniBrowser"
  val specUrl = ""
  val urlfield = new TextField(specUrl)
  val pagefield = new TextArea
//  val swingScheduler = new Scheduler {
//    val asJavaScheduler = fromExecutor((r: Runnable) => invokeLater(r))
//  }


  val button = new Button {
    text = "Feeling Lucky"
  }

  contents = new BorderPanel {
    import  BorderPanel.Position._
    layout(new BorderPanel {
      layout(new Label("URL:")) = West
      layout(urlfield) = Center
      layout(button) = East
    }) = North
    layout(pagefield) = Center
  }

  size = new Dimension(1024, 768)

  implicit class ButtonOps(val self: Button) {
    def clicks = Observable.apply[Unit] { obs =>
      self.reactions += {
        case ButtonClicked(_) => obs.onNext()
      }

      Subscription()
    }
  }

  implicit class TextFieldOps(val self: TextField) {
    def texts = Observable.apply[String] { obs =>
      self.reactions += {
        case ValueChanged(_) => obs.onNext(self.text)
      }

    }
  }
}

import scala.concurrent.duration._

trait BrowserLogic {
  self: BrowserFrame =>

  def suggestRequest(term: String): Observable[String] = {
    val url = s"http://suggestqueries.google.com/complete/search?client=firefox&q=$term"
    val request = Future {
      Source.fromURL(url).mkString
    }

    Observable.from(request)
      .timeout(0.5.seconds)
      .onErrorReturn(e => s"Could not load page: $e")
  }

  def pageRequest(url: String): Observable[String] = {
    val request = Future {
      Source.fromURL(url).mkString
    }

    Observable.from(request)
      .timeout(4.seconds)
      .onErrorReturn(e => s"Could not load page: $e")
  }

  urlfield.texts.map(suggestRequest).concat
//    .observeOn(swingScheduler)
    .subscribe(response => pagefield.text = response)

  button.clicks.map(_ => pageRequest(urlfield.text))
//    .observeOn(swingScheduler)
    .concat
    .subscribe(response => pagefield.text = response)
}

object SchedulersBrowser extends SimpleSwingApplication {
  override def top: Frame = new BrowserFrame with BrowserLogic
}