package traits.traitsImpl

import traits.Cache

class CacheImpl extends Cache[Int,String]{
  override def get(key: Int): String = key.toString

  override def put(key: Int, value: String): Unit = println("put it")

  override def delete(key: Int): Unit = println("delete it")
}
