package one

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object BlackList {
  /**
    * spark-submit --class one.BlackList test.jar
    * @param args
    */
  def main(args: Array[String]): Unit = {
    val blackList=List("zs","ls")
    val conf = new SparkConf().setAppName("BlackList").setMaster("local[2]")
    val ssc = new StreamingContext(conf,Seconds(5))
    val lines = ssc.socketTextStream("localhost",4444)
    val blackRDD = ssc.sparkContext.parallelize(blackList).map(x=>(x,true))


    val result = lines.map(x => (x, x.split(" ")(1))).transform(rdd => {
      rdd.leftOuterJoin(blackRDD).filter(x => {
        x._2._2.getOrElse(false) == false
      }).map(x => x._2._1)
    })
    result.print()
    ssc.start()
    ssc.awaitTermination()


  }

}
