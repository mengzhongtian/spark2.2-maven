package one

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

case class Word(word: String)

object Sql2Streaming {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("Sql2Streaming")
    val ssc = new StreamingContext(conf, Seconds(5))
    val lines = ssc.socketTextStream("localhost", 4444)
    val words = lines.flatMap(_.split(" "))
    words.foreachRDD { rdd =>

      // Get the singleton instance of SparkSession
      val spark = SparkSingleton.getSpark(conf)
      import spark.implicits._

      val value = rdd.map(word => Word(word))
      val df1 = value.toDF()
      df1.createOrReplaceTempView("words")


      val wordCountsDataFrame =
        spark.sql("select word, count(*) as total from words group by word")
      wordCountsDataFrame.show()
    }

    ssc.start()
    ssc.awaitTermination()

  }
}
