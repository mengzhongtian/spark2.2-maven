package one

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object NetworkWordCount {
  /**
    * 事先需要在节点上启动一个：nc -lk 4444
    * @param args
    */
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val ssc = new StreamingContext(sparkConf, Seconds(1))

    val lines = ssc.socketTextStream("localhost", 4444)
    lines.flatMap(_.split(" "))
    val dStream1 = lines.flatMap(x => x.split(" "))
    val result = dStream1.map(x => (x, 1)).reduceByKey(_ + _)
    result.print()
    ssc.start()
    ssc.awaitTermination()


  }

}
