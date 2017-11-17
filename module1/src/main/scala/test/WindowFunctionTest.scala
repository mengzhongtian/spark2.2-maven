package test


import org.apache.spark.sql.SparkSession

object WindowFunctionTest {

  case class TheList(name: String, num: Long)

  case class Person(name: String, age: Int)


  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("Spark SQL basic example")
      //    .config("spark.some.config.option", "some-value")
      .master("local[2]")
      .getOrCreate()
    import spark.implicits._
    val dataFrame = spark.sparkContext
      .textFile("list.txt")
      .map(_.split(" "))
      .map(attributes => TheList(attributes(0), attributes(1).trim.toLong))
      .toDF()


    dataFrame.createOrReplaceTempView("list")

//    val sqlDF = spark.sql("SELECT * FROM list WHERE num BETWEEN 13 AND 30")
    val windowSql = "SELECT " +
      "name," +
      "num, " +
      "row_number() OVER (PARTITION BY name ORDER BY num DESC ) rank" +
      " FROM list"

    val sqlDF = spark.sql(windowSql)


//    sqlDF.map(name => "Name: " + name(0) + " : " + name(1)).show()

    sqlDF.show()

  }

  private def runInferSchemaExample(spark: SparkSession): Unit = {
    // $example on:schema_inferring$
    // For implicit conversions from RDDs to DataFrames
    import spark.implicits._

    // Create an RDD of Person objects from a text file, convert it to a Dataframe
    val peopleDF = spark.sparkContext
      .textFile("examples/src/main/resources/people.txt")
      .map(_.split(","))
      .map(attributes => Person(attributes(0), attributes(1).trim.toInt))
      .toDF()
    // Register the DataFrame as a temporary view
    peopleDF.createOrReplaceTempView("people")

    // SQL statements can be run by using the sql methods provided by Spark
    val teenagersDF = spark.sql("SELECT name, age FROM people WHERE age BETWEEN 13 AND 19")

    // The columns of a row in the result can be accessed by field index
    teenagersDF.map(teenager => "Name: " + teenager(0)).show()
    // +------------+
    // |       value|
    // +------------+
    // |Name: Justin|
    // +------------+

    // or by field name
    teenagersDF.map(teenager => "Name: " + teenager.getAs[String]("name")).show()
    // +------------+
    // |       value|
    // +------------+
    // |Name: Justin|
    // +------------+

    // No pre-defined encoders for Dataset[Map[K,V]], define explicitly
    implicit val mapEncoder = org.apache.spark.sql.Encoders.kryo[Map[String, Any]]
    // Primitive types and case classes can be also defined as
    // implicit val stringIntMapEncoder: Encoder[Map[String, Any]] = ExpressionEncoder()

    // row.getValuesMap[T] retrieves multiple columns at once into a Map[String, T]
    teenagersDF.map(teenager => teenager.getValuesMap[Any](List("name", "age"))).collect()
    // Array(Map("name" -> "Justin", "age" -> 19))
    // $example off:schema_inferring$
  }

}
