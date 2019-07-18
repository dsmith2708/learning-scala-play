package models

import play.api.data.Form
import play.api.data.Forms._

case class LoginUserPass(username: String, password: String)

object LoginUserPass {
  val createUserPassForm = Form {
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(LoginUserPass.apply)(LoginUserPass.unapply)
  }
}
