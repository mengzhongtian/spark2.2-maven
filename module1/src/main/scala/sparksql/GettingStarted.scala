package sparksql

object GettingStarted {

  import org.apache.spark.sql.SparkSession

  val spark = SparkSession
    .builder()
    .appName("Spark SQL basic example")
    .config("spark.some.config.option", "some-value")
    .master("local[2]")
    .getOrCreate()

  // For implicit conversions like converting RDDs to DataFrames
  import spark.implicits._

  /**
    * 创建DataFrame
    */
  val df = spark.read.json("examples/src/main/resources/people.json")

  // Displays the content of the DataFrame to stdout
  df.show()
  // +----+-------+
  // | age|   name|
  // +----+-------+
  // |null|Michael|
  // |  30|   Andy|
  // |  19| Justin|
  // +----+-------+


}
