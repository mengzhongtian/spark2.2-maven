package FlumeKafka2Streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume.FlumeUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FlumePush2Streaming {
  /**
    *0.官方文档：https://spark.apache.org/docs/latest/streaming-flume-integration.html
    *
    *
    *1. 添加依赖：
    * groupId = org.apache.spark
    * artifactId = spark-streaming-flume_2.11
    * version = 2.2.0
    *
    *3.flumeStream.map(x => new String(x.event.getBody.array()).trim)
    * 理解这部操作
    *
    */
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("FlumePush2Streaming")
    val ssc = new StreamingContext(conf, Seconds(5))
    val flumeStream = FlumeUtils.createStream(ssc, "localhost", 41414)
    flumeStream.map(x => new String(x.event.getBody.array()).trim).flatMap(_.split(" ")).map(x => (x, 1)).reduceByKey(_ + _).print()
    ssc.start()
    ssc.awaitTermination()


  }

}
