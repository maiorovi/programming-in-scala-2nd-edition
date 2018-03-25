package chapter8

import java.io.File

import scala.io.Source
import scala.io.Source._

class LongLines {

  def processFile(file: File, width: Int): List[String] = (for (line <- fromFile(file).getLines() if line.length > width) yield line) toList

}
