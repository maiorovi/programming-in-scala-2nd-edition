import org.apache.commons.lang3.StringUtils

package object chapter13 {
  def concat(strings:String*): String = strings.foldLeft(new StringBuffer())( (b, s) => b append s) toString

}
