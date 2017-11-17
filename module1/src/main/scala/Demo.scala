import org.apache.spark.sql.SparkSession

object Demo {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
        .master("local[2]")
      .appName("Spark SQL basic example")
      .getOrCreate()

    // Create an RDD
//    val peopleRDD = spark.sparkContext.textFile("file:////C://list.txt")
val peopleRDD = spark.sparkContext.textFile("list.txt")
    println("hello world")
    println(peopleRDD.count())
    peopleRDD.foreach(x=>println(x))
  }
}
