package com.mike

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object ReadAccesslog {
  def main(args: Array[String]): Unit = {
    //    val conf = new SparkConf().setAppName("ReadAccessLog").setMaster("local[2]")
    val spark = SparkSession.builder().appName("ReadAccessLog").master("local[2]").getOrCreate()
    val ds1 = spark.read.textFile("hdfs:/flume/access_log")
    val str = ds1.first()
    ds1.show()
  }

}
