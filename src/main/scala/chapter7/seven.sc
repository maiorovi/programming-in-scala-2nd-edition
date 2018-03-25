import java.io.{File, FileNotFoundException, FileReader, IOException}
import java.net.{MalformedURLException, URL}

import scala.io.Source

val filesHere = (new File(".")).listFiles()

for (file <- filesHere)
  println(file)


val filesHere1 = new File(".").listFiles()

for(file <- filesHere1 if  file.getName.endsWith("dll"))
  println(file)


def grep(pattern: String, files: List[File]) = for {
  file <- files
  if file.getName.endsWith(".scala")
  line <- lines(file)
  trimmed = line.trim
  if trimmed.matches(pattern)
} println(file + ": " + trimmed)

def lines(file: File): List[String] = Source.fromFile(file).getLines.toList


val l = List(1,2,3,4,5,6)


for(a <- l if a % 2 == 0)
  yield a


try {
  val f = new FileReader("input.txt")
  // Use and close file
} catch {
  case ex: FileNotFoundException => // Handle missing file
  case ex: IOException => // Handle other I/O error
}


def urlForPath(path: String): URL = {
  try {
    new URL(path)
  } catch {
    case e: MalformedURLException => new URL("http://www.scala-lang.org")
  }
}

val firstArg = "gdfg"