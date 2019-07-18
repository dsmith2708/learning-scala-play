package controllers

import javax.inject.Inject
import models.{CD, LoginUserPass, People}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import models.CD._

class Application @Inject()(val messagesApi: MessagesApi, environment: play.api.Environment) extends Controller with I18nSupport {

  def index = Action { implicit request =>
    Ok(views.html.index("Your new application is ready.", request.session.get("isLoggedIn").getOrElse("false").toBoolean)).withSession(("user", "user@gmail.com"))
  }

//
  def formFunction = Action {implicit request =>
    Ok(views.html.form( request.session.get("isLoggedIn").getOrElse("false").toBoolean) )
  }




//
  def list = Action { implicit request =>
    Ok(views.html.listDisplay(CD.createCDForm, CD.cds, "List view page", request.session.get("isLoggedIn").getOrElse("false").toBoolean))
  }


  def createCD() = Action { implicit request =>
    val formValidationResult = CD.createCDForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      println(request.session.get("user").getOrElse("TitleNotFound"))
      BadRequest(views.html.listDisplay(CD.createCDForm, CD.cds, request.session.get("user").getOrElse("TitleNotFound"), request.session.get("isLoggedIn").getOrElse("false").toBoolean))
    }, { cd =>
      println(request.session.get("user").getOrElse("TitleNotFound"))
      CD.cds.append(cd)
      Redirect(routes.Application.list())
    })
  }

  def doLogin() = Action { implicit request =>
    val formValidationResult = LoginUserPass.createUserPassForm.bindFromRequest
    var validated: Boolean = false
    formValidationResult.fold({ formWithErrors =>
      println(request.session.get("username").getOrElse("username not found"))
      println(request.session.get("password").getOrElse("password not found"))
      BadRequest(views.html.login(formWithErrors))
    }, { userCredentials =>
      println(userCredentials.username)
      println(userCredentials.password)
      if(userCredentials.username == "admin" && userCredentials.password == "admin") {
        validated = true
      } else validated = false
      println(validated.toString)
      Redirect(routes.Application.afterLogin()).withSession(("isLoggedIn", validated.toString))
    })
  }

  def afterLogin() = Action { implicit request =>
    Ok(views.html.isLoggedIn(request.session.get("isLoggedIn").getOrElse("false").toBoolean))
  }

  def getLoginForm() = Action { implicit request =>
    Ok(views.html.login(LoginUserPass.createUserPassForm))
  }


//  def sayHello() = Action { implicit request =>
//    val formData = People.peopleFormConstraints.bindFromRequest
//    formData.fold(
//      {
//        formWithErrors => BadRequest(views.html.listDisplay())
//      },
//      {
//        formData =>
//        Ok(views.html.main(s"posted with x.name: ${formData.name}, x.age: ${formData.age}"))
//      }
//    )
//   }

}