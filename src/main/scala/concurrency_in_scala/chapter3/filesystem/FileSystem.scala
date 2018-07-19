package concurrency_in_scala.chapter3.filesystem

import java.io.File
import java.util.concurrent.ConcurrentHashMap

import scala.collection.JavaConverters._
import scala.collection.concurrent;

class FileSystem(val root: String) {
    val rootDir = new File(root)
    val files: concurrent.Map[String, Entry] = new ConcurrentHashMap[String, Entry].asScala



  def prepareForDelete(e: Entry): Boolean = {
    val state = e.state.get
    state match {
      case i: Idle =>
        if (e.state.compareAndSet(state, new Deleting)) true
        else prepareForDelete(e)
      case c: Creating => false
      case c: Copying => false
      case c: Deleting => false
    }
  }

}
