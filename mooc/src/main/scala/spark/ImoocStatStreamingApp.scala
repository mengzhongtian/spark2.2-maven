package spark

import domain.ClickLog
import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka.KafkaUtils
import utils.DateUtils

/**
  * /opt/spark-2.2.0-bin-hadoop2.6/bin/spark-submit --class FlumeKafka2Streaming.KafkaDirect2Streaming --packages org.apache.spark:spark-streaming-kafka-0-8_2.11:2.2.0 test.jar
  */


object ImoocStatStreamingApp {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    //    conf.setAppName("kafka").setMaster("local[2]")

    val ssc = new StreamingContext(conf, Seconds(60))
    /**
      * param:broker不是zookeeper
      * 返回类型是map
      */
    val param = Map[String, String]("metadata.broker.list" -> "localhost:9092")
    /**
      * 话题，用逗号分割
      * 返回类型是set
      */
    val topic = "mooc"
    val to = topic.split(",").toSet

    val dStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, param, to)

    //    dStream.map(_._2).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()

    /**
      * 如果dStream直接打印。key值是什么？(Kafka message key, Kafka message value)
      * dStream.print()
      * (null,a b c d)
      *
      */

    /**
      * 数据清洗：
      */
    val logs = dStream.map(_._2)
    var courseId = 0

    val cleanData =
      logs
        .map(line => {
          val info = line.split("\t")
          val url = info(2).split(" ")(1)
          if (url.startsWith("/class")) {
            val courseIdHTML = url.split("/")(2)
            courseId = courseIdHTML.substring(0, courseIdHTML.lastIndexOf(".")).toInt
          }

          /**
            * 这里创建了一个对象：返回的是一个对象
            */
          ClickLog(info(0), DateUtils.parseToMinute(info(1)), courseId, info(3).toInt, info(4))

        })
        .filter(courseId => courseId != 0)
    //      .filter(x => {
    //        x.courseId != 0
    //      })


    cleanData
      .map(x => {
        (x.time.substring(0, 8) + "_" + x.courseId, 1)
      })
      .reduceByKey(_ + _)
      .foreachRDD(rdd=>{
        rdd.foreachPartition(partitionRecords=>{





        })

      })


    ssc.start()
    ssc.awaitTermination()
  }

}
