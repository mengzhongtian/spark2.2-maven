package FlumeKafka2Streaming

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.flume._

/**
  * 提交任务：
  * spark-submit --packages org.apache.spark:spark-streaming-flume-sink_2.11:2.2.0,org.apache.spark:spark-streaming-flume_2.11:2.2.0 flumepullstreaming.jar
  *pull相比push的优点是：具有高容错性，不会丢失数据。
  *
  */

object FlumePull2Streaming {

  val conf = new SparkConf().setMaster("local[2]").setAppName("FlumePush2Streaming")
  val ssc = new StreamingContext(conf, Seconds(5))
  val flumeStream = FlumeUtils.createPollingStream(ssc, "localhost", 41414)
  flumeStream.map(x => new String(x.event.getBody.array()).trim).flatMap(_.split(" ")).map(x => (x, 1)).reduceByKey(_ + _).print()
  ssc.start()
  ssc.awaitTermination()

}
