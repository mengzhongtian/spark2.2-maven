package FlumeKafka2Streaming

import kafka.serializer.StringDecoder
import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.streaming.kafka._

/**
  * spark-submit --class FlumeKafka2Streaming.KafkaDirect2Streaming --packages org.apache.spark:spark-streaming-kafka-0-8_2.11:2.2.0 test.jar
  *
  * 1.direct vs reciver
  * Receiver方式是通过zookeeper来连接kafka队列，Direct方式是直接连接到kafka的节点上获取数据了。
  *
  * 、基于Direct的方式
  * 这种新的不基于Receiver的直接方式，是在Spark 1.3中引入的，从而能够确保更加健壮的机制。替代掉使用Receiver来接收数据后，这种方式会周期性地查询Kafka，来获得每个topic+partition的最新的offset，从而定义每个batch的offset的范围。当处理数据的job启动时，就会使用Kafka的简单consumer api来获取Kafka指定offset范围的数据。
  * *
  * 这种方式有如下优点：
  * 1、简化并行读取：如果要读取多个partition，不需要创建多个输入DStream然后对它们进行union操作。Spark会创建跟Kafka partition一样多的RDD partition，并且会并行从Kafka中读取数据。所以在Kafka partition和RDD partition之间，有一个一对一的映射关系。
  * *
  * 2、高性能：如果要保证零数据丢失，在基于receiver的方式中，需要开启WAL机制。这种方式其实效率低下，因为数据实际上被复制了两份，Kafka自己本身就有高可靠的机制，会对数据复制一份，而这里又会复制一份到WAL中。而基于direct的方式，不依赖Receiver，不需要开启WAL机制，只要Kafka中作了数据的复制，那么就可以通过Kafka的副本进行恢复。
  * *
  * 3、一次且仅一次的事务机制：
  * 基于receiver的方式，是使用Kafka的高阶API来在ZooKeeper中保存消费过的offset的。这是消费Kafka数据的传统方式。这种方式配合着WAL机制可以保证数据零丢失的高可靠性，但是却无法保证数据被处理一次且仅一次，可能会处理两次。因为Spark和ZooKeeper之间可能是不同步的。
  * 基于direct的方式，使用kafka的简单api，Spark Streaming自己就负责追踪消费的offset，并保存在checkpoint中。Spark自己一定是同步的，因此可以保证数据是消费一次且仅消费一次。
  *
  */
object KafkaDirect2Streaming {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
    val ssc = new StreamingContext(conf, Seconds(5))
    /**
      * param:broker不是zookeeper
      * 返回类型是map
      */
    val param = Map[String, String]("metadata.broker.list" -> "localhost:9092")
    /**
      * 话题，用逗号分割
      * 返回类型是set
      */
    val topic = "testtt"
    val to = topic.split(",").toSet

    val dStream = KafkaUtils.createDirectStream[String, String, StringDecoder, StringDecoder](ssc, param, to)

    dStream.map(_._2).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).print()

    /**
      * 如果dStream直接打印。key值是什么？(Kafka message key, Kafka message value)
      * dStream.print()
      * (null,a b c d)
      *
      */
    ssc.start()
    ssc.awaitTermination()
  }

}
