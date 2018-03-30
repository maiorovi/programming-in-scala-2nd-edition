package chapter9

object FileMatcher {
  import java.io.File

  private def filesHere = (new File(".").listFiles())

  def filesEnding(query: String) = filesMatching(query, _.endsWith(query))

  def filesContaining(query: String) = filesMatching(query, _.contains(query))

  def filesRegex(query: String) = filesMatching(query, _.matches(query))

  def filesMatching(query: String, matcher: String => Boolean):Array[File] =
    for( file <- filesHere; if matcher(file.getName))
      yield file

}
