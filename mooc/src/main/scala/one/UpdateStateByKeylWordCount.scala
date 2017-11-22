package one

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object UpdateStateByKeylWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[3]").setAppName("StatefulWordCount")
    val ssc = new StreamingContext(conf, Seconds(3))

    /**
      * spark指定linux文件绝对路径：
      */
    ssc.checkpoint("file:///root/mike")

    val lines = ssc.socketTextStream("localhost", 4444)
    val result = lines.flatMap(_.split(" ")).map(x => (x, 1))
    val up = result.updateStateByKey[Int](updateFunction _)
    up.print()
    ssc.start()
    ssc.awaitTermination()


  }


  def updateFunction(curV: Seq[Int], preV: Option[Int]): Option[Int] = {
    val current = curV.sum
    val pre = preV.getOrElse(0)
    Some(pre + current)

  }
}
