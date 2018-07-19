package concurrency_in_scala.chapter2.exercise

import scala.collection.parallel.mutable

object Ex7 {
  import scala.collection.concurrent.Map;
  import scala.collection.mutable;

  class SyncConcurrentMap[K,V] extends Map[K,V] {

    private val m = mutable.Map.empty[K, V]

    override def putIfAbsent(k: K, v: V): Option[V] = m.synchronized {
      m.get(k) match {
        case optv@Some(_) => optv
        case None => m.put(k, v)
      }
    }

    override def remove(k: K, v: V): Boolean = m.synchronized {
      m.remove(k) match {
        case Some(_) => true
        case None => false
      }
    }

    override def replace(k: K, oldvalue: V, newvalue: V): Boolean = m.synchronized {
      m.get(k) match {
        case Some(v) if v == oldvalue => m.put(k,newvalue); true
        case _ => false
      }
    }

    override def replace(k: K, v: V): Option[V] = m.synchronized {
      m.get(k) match {
        case Some(oldValue) => m.put(k, v);Some(oldValue);
        case None => None
      }
    }

    override def +=(kv: (K, V)): SyncConcurrentMap.this.type = m.synchronized {
      m.put(kv._1, kv._2)
      this
    }

    override def -=(key: K): SyncConcurrentMap.this.type = m.synchronized {
      m.remove(key)
      this
    }

    override def get(key: K): Option[V] = m.synchronized {
      m.get(key)
    }

    override def iterator: Iterator[(K, V)] = m.synchronized {
      m.iterator
    }
  }
}
