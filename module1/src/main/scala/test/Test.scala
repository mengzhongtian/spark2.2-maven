package test

import abst.abst1
import abstImpl.Abst1Impl
import traits.BMW

object Test {
  def main(args: Array[String]): Unit = {
    //    val student = new Student
    //    val name=student.name
    //    val age=student.age
    //    student.getName
    //    val add = student.ageAdd(3)
    //    println(add)
//    val car = new Car("passat")
//    car.printBrand
//    car.setBrand("benz")
//    car.printBrand

//    val impl = new Abst1Impl
//    val name = impl.getName()
//    val num = impl.getNumber(3)
//    println(name)
//    println(num)

    /**
      * trait
      */

val bmw = new BMW
    bmw.run()
    println(bmw.brand)


  }

}
