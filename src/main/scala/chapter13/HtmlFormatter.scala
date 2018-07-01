package chapter13


class Anchor(val value : String) extends AnyVal
class Style(val value: String) extends AnyVal
class Text(val value: String) extends AnyVal
class Html(val value: String) extends AnyVal

class HtmlFormatter {
  def title(anchor: Anchor, style: Style, text: Text): Html = new Html(
    s"<div id='${anchor.value}'><h1 class='${style.value}'>" +
      s"${text.value}" +
      s"</h1></div>"
  );
}

object Gogo {

  def main(args: Array[String]): Unit = {
    val formatter = new HtmlFormatter()

    val html = formatter.title(new Anchor("container"), new Style("header"), new Text("Content here"))

    println(html.value)
  }

}
