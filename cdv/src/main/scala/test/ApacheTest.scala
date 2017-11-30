package test

import util.DateUtil

object ApacheTest {
  def main(args: Array[String]): Unit = {

    val t="[01/Nov/2017:11:59:31 +0800]"
    val d = DateUtil.parseDateField(t)
    println(d)
//    Some(Wed Nov 01 11:59:31 CST 2017)
  }

}
