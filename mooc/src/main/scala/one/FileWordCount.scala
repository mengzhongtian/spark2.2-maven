package one

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.streaming.{Seconds, StreamingContext}

object FileWordCount {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setMaster("local[2]").setAppName("FileWordCount")
    val ssc = new StreamingContext(conf, Seconds(3))
//    val sc = new SparkContext(conf)
//    val value = sc.textFile("file:////c:/mydata")
//    value.foreach(println)

    /**
      * 这样应该是正确的路径写法吧，但是还是没成功，有可能不能用来读取windows文件。
      */
    val lines = ssc.textFileStream("file:////c:/mydata/")
//    file:////C://list.txt

//    val word = lines.flatMap(x => x.split(" "))
//    word.print()
//    lines.print()
//    ssc.start()
//    ssc.awaitTermination()
    lines.print()

//    val result = lines.flatMap(_.split(" ")).map((_,1)).reduceByKey(_+_)
//    result.print()

    ssc.start()
    ssc.awaitTermination()


  }

}
