package spark

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Demo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Demo_CDV")
      .master("local[2]")
      .getOrCreate()

    val df = spark.read.text("list.txt")


    import spark.implicits._

    df.printSchema()
    df.show()


//    df.createOrReplaceTempView("log")
    //    val sqlDF = spark.sql("SELECT * FROM log WHERE value LIKE %moid%")
    //    val sqlDF = spark.sql("SELECT * FROM log where value LIKE %moid%")
    //    val row = sqlDF.first()
    //    println(row)
//    val df2=df.filter(df("value") contains "moid")
  }

}
