package traits

class BMW extends Car with run {
  override val brand: String = "BMW"

  override def run(): Unit = println("I can RUN")
}
