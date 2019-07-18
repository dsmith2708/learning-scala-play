package models

import play.api.data._
import play.api.data.Forms._
import scala.collection.mutable.ListBuffer

case class People(name: String, age: Int)

object People{

  val peopleFormConstraints = Form(
    mapping(
      "name" -> nonEmptyText,
      "age" -> number(min = 0, max = 100)
    )(People.apply)(People.unapply)
  )

  val listItems: ListBuffer[People] = ListBuffer(
    new People("John Waters", 15),
    new People("James Wick", 25),
    new People("Jimmy West", 35),
    new People("John Wick", 45)
  )

}