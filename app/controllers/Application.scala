package controllers

import javax.inject.Inject
import models.{CD, People}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc._
import models.CD._

class Application @Inject()(val messagesApi: MessagesApi, environment: play.api.Environment) extends Controller with I18nSupport {

  def index = Action {
    Ok(views.html.index("Your new application is ready.")).withSession(("user", "user@gmail.com"))
  }
//
//  def formFunction = Action {
//    Ok(views.html.form(People.peopleFormConstraints))
//  }
//
  def list = Action {
    Ok(views.html.listDisplay(CD.createCDForm, CD.cds, "List view page"))
  }


  def createCD() = Action { implicit request =>
    val formValidationResult = CD.createCDForm.bindFromRequest
    formValidationResult.fold({ formWithErrors =>
      println(request.session.get("user").getOrElse("TitleNotFound"))
      BadRequest(views.html.listDisplay(CD.createCDForm, CD.cds, request.session.get("user").getOrElse("TitleNotFound")))
    }, { cd =>
      println(request.session.get("user").getOrElse("TitleNotFound"))
      CD.cds.append(cd)
      Redirect(routes.Application.list())
    })
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