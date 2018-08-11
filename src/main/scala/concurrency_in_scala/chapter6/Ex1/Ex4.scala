package concurrency_in_scala.chapter6.Ex1

import concurrency_in_scala.chapter6.Ex1.Ex4.{RCell, Signal}
import rx.lang.scala.{Observable, Subject}


object Ex4 extends App {

  implicit class ObservableAdditional[T](val self: Observable[T]) {
    def toSignal: Signal[T] = new Signal[T](self)
  }

  class Signal[T] {
    protected var lastEvent: Option[T] = None
    protected var observable: Observable[T] = _

    def this(obs: Observable[T]) {
      this()
      setObservable(obs)
    }

    def this(obs: Observable[T], initialValue: T) {
      this(obs)
      lastEvent = Option(initialValue)
    }

    protected def setObservable(obs: Observable[T]) = {
      observable = obs
      obs.subscribe(t => lastEvent = Some(t))
    }

    def apply(): T = lastEvent.get

    def map[S](f: T => S): Signal[S] = lastEvent match {
      case Some(x) => new Signal[S](observable.map(t => f(t)), f(x))
      case _ => new Signal[S](observable.map(t => f(t)))
    }

    def zip[S](that: Signal[S]): Signal[(T, S)] = (lastEvent, that.lastEvent) match {
      case (Some(t), Some(s)) => new Signal[(T, S)](observable.zip(that.observable), (t, s))
      case _ => new Signal[(T, S)](observable.zip(that.observable))
    }

    def scan[S](z: S)(f: (S, T) => S): Signal[S] = new Signal[S](observable.scan(z)(f))

  }

  class RCell[T] extends Signal[T] {
    private[this] val subject = Subject[T]()
    setObservable(subject)

    def :=(x:T): Unit = {
      subject.onNext(x)
    }

  }

  val sub1 = Subject[Int]()
  val sig1 = sub1.toSignal
  sub1.onNext(1)
  assert(sig1() == 1)
  sub1.onNext(2)
  assert(sig1() == 2)

  val sub2 = Subject[Int]()
  val sig2 = sub2.toSignal
  sub2.onNext(1)
  val increment = sig2.map(_ + 1)
  assert(increment() == 2)
  sub2.onNext(2)
  assert(increment() == 3)

  val sub31 = Subject[Int]()
  val sub32 = Subject[String]()
  val sig31 = sub31.toSignal
  val sig32 = sub32.toSignal
  sub31.onNext(1)
  sub32.onNext("a")
  val zipped = sig31.zip(sig32)
  assert(zipped() == (1, "a"))
  sub31.onNext(2)
  sub32.onNext("b")
  assert(zipped() == (2, "b"))

  val sub4 = Subject[Int]()
  val sig4 = sub4.toSignal
  sub4.onNext(1)
  val sum = sig4.scan(10)(_ + _)
  assert(sum() == 10)
  sub4.onNext(2)
  assert(sum() == 12)
  sub4.onNext(3)
  assert(sum() == 15)



}

object Ex5 extends App {
    val rc1 = new RCell[Int]()
    rc1 := 1
    assert(rc1() == 1)

    val rc2 = new RCell[Int]()
    rc2 := 1
    val increment = rc2.map(_ + 1)
    assert(increment() == 2)
    rc2 := 2
    assert(increment() == 3)

    val rc31 = new RCell[Int]()
    val rc32 = new RCell[String]()
    rc31 := 1
    rc32 := "a"
    val zipped = rc31.zip(rc32)
    assert(zipped() == (1, "a"))
    rc31 := 2
    rc32 := "b"
    assert(zipped() == (2, "b"))

    val rc4 = new RCell[Int]()
    rc4 := 1
    val sum = rc4.scan(10)(_ + _)
    assert(sum() == 10)
    rc4 := 2
    assert(sum() == 12)
    rc4 := 3
    assert(sum() == 15)


}
