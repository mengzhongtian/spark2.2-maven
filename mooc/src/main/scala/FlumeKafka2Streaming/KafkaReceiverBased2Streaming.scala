package FlumeKafka2Streaming

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka._

/**
  * spark-submit --class FlumeKafka2Streaming.ReceiverBased2Streaming --packages org.apache.spark:spark-streaming-kafka-0-8_2.11:2.2.0 test.jar localhost:2181 test testtt 1
  */

object KafkaReceiverBased2Streaming {
  def main(args: Array[String]): Unit = {
    if (args.length != 4) {
      System.err.println("参数不对")
    }
    val conf = new SparkConf().setMaster("local[2]").setAppName("FlumePush2Streaming")
    val ssc = new StreamingContext(conf, Seconds(5))

    val Array(zk, groupid, topic, numThreads) = args

    val topicMap = topic.split(",").map((_, numThreads.toInt)).toMap[String, Int]


    val kafkaDstream = KafkaUtils.createStream(ssc, zk, groupid, topicMap)
    kafkaDstream.map(_._2).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print

    ssc.start()
    ssc.awaitTermination()


  }

}
