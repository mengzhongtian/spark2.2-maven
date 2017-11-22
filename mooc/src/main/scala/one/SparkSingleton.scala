package one

import org.apache.spark.{SparkConf, sql}
import org.apache.spark.sql.SparkSession

object SparkSingleton {

  /**
    * 这里的下划线=null
    */
  private var spark:SparkSession=_


  def getSpark(conf:SparkConf)={
    if(spark==null){

      spark = SparkSession.builder().config(conf).getOrCreate()
    }
    spark



  }

}
