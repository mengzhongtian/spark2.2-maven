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
    * 4.打包
    * (1)把依赖打入jar包中提交上去
    * 要添加的jar：
    * flume-ng-sdk-1.6.0.jar
    * spark-streaming-flume_2.11-2.2.0.jar
    * 打包：
    * artifacts -> extracted directory 选择这两个jar包。
    *
    * 打包遇到的问题，如果全打进去，会报错：找不到主类：原因是要把linux环境中已经存在的依赖从打的jar包中移除
    *
    * (2)把依赖拷贝到linux上然后通过--jars a.jar,b.jar
    * 逗号分隔的方式
    *
    * 5.提交：spark-submit --class FlumeKafka2Streaming.FlumePush2Streaming mooc.jar
    *
    * 6.提交顺序：
    * (1)先提交spark-submit
    * spark-submit --class FlumeKafka2Streaming.FlumePush2Streaming mooc.jar
    * (2)然后启动flume
    * bin/flume-ng agent  \
    * --name simple-agent   \
    * --conf conf    \
    * --conf-file conf/flume_push_streaming.conf  \
    * -Dflume.root.logger=INFO,console
    *
    * (3)然后启动telnet：
    * telnet localhost 4444
    *
    * 注意：----------------------------------------------------------------------------
    * 区分telnet和netcat：nc -lk 4444
    * telnet localhost 4444
    *
    *
    */

  /**
    * flume配置文件：
    *
    *flume_push_streaming.conf
    * simple-agent.sources = netcat-source
    * simple-agent.sinks = avro-sink
    * simple-agent.channels = memory-channel
    *
    * simple-agent.sources.netcat-source.type = netcat
    * simple-agent.sources.netcat-source.bind = localhost
    * simple-agent.sources.netcat-source.port = 4444
    * simple-agent.sinks.avro-sink.type = avro
    * simple-agent.sinks.avro-sink.hostname = localhost
    * simple-agent.sinks.avro-sink.port = 41414
    * *
    * simple-agent.channels.memory-channel.type = memory
    * *
    * simple-agent.sources.netcat-source.channels = memory-channel
    * simple-agent.sinks.avro-sink.channel = memory-channel
    *
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
