package controllers

import models.People
import play.api._
import play.api.mvc._

import scala.collection.mutable.ListBuffer

class Application extends Controller {

  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }

  def form = Action {
    Ok(views.html.form())
  }

  def list = Action {
    val listItems: ListBuffer[People] = ListBuffer(
      new People("John Waters", 15),
      new People("James Wick", 25),
      new People("Jimmy West", 35),
      new People("John Wick", 45)
    )

    Ok(views.html.listDisplay(listItems))
  }

}