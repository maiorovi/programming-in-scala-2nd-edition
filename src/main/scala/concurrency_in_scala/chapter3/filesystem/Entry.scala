package concurrency_in_scala.chapter3.filesystem

import java.util.concurrent.atomic.AtomicReference


class Entry(val isDir: Boolean) {
  val state = new AtomicReference[State](new Idle)
}
