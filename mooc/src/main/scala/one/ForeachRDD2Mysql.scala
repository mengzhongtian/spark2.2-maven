package one

import java.sql.DriverManager

import org.apache.spark.SparkConf
import org.apache.spark.streaming.{Seconds, StreamingContext}

object ForeachRDD2Mysql {
  /**
    * spark-submit --class one.ForeachRDDApp --jars mysql-connector-java-5.1.6.jar test.jar提交的命令
    *
    *添加了如下jdbc的maven依赖：
    * <dependency>
    * <groupId>mysql</groupId>
    * <artifactId>mysql-connector-java</artifactId>
    * <version>5.1.6</version>
    * </dependency>
    *
    * @param args
    */

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("ForeachRDDApp").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(5))
    val lines = ssc.socketTextStream("localhost", 4444).flatMap(_.split(" ")).map(x => (x, 1)).reduceByKey(_ + _)


    lines.foreachRDD(rdd => {
      rdd.foreachPartition(partitionRDD => {


        partitionRDD.foreach(record => {
          val connection = createConnection
          val sql = "insert into wordcount(word, wordcount) values('" + record._1 + "'," + record._2 + ")"
          val statement = connection.prepareStatement(sql)
          statement.execute()


        })


      })


    })

    ssc.start()
    ssc.awaitTermination()


  }

  def createConnection = {
    Class.forName("com.mysql.jdbc.Driver")
    DriverManager.getConnection("jdbc:mysql://localhost:3306/mike", "root", "cdv123098")


  }

}
