package traits.test

import traits.traitsImpl.CacheImpl

object CacheTest {
  def main(args: Array[String]): Unit = {
    val impl = new CacheImpl
    impl.put(3,"abc")
    impl.delete(3)
    val str = impl.get(3)
    println(str)

  }

}
