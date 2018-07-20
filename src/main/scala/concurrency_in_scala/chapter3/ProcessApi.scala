package concurrency_in_scala.chapter3

import concurrency_in_scala.chapter3.ProcessAsync.lsProcess

import scala.sys.process._

object ProcessApi {


}

object ProcessRun extends App {
  val command = "ls"
  val exitcode = command.!
  log(s"command exited with status $exitcode")

  def lineCount(filename: String): Int = {
    val output = s"wc $filename".!!
    output.trim.split(" ").head.toInt
  }

}


object ProcessAsync extends App {
  val lsProcess = "ls -R /".run()
  Thread.sleep(1000)
  log("Timeout - killing ls!")
  lsProcess.destroy()
}

object MyProcess extends App {
  val myProcess = "echo \"Hello World\"".run()
  Thread.sleep(1000)
  log("Timeout - killing ls!")
  myProcess.destroy()
}
