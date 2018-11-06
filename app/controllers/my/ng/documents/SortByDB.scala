package controllers.my.ng.documents

import play.api.libs.json.Json
import play.api.mvc.{AnyContent, Request}
import scala.concurrent.{ExecutionContext, Future}
import services.{Page, SortOrder}
import services.document.DocumentService
import services.generated.tables.records.DocumentRecord

trait SortByDB { self: DocumentInfoController =>

  /** Boilerplate to fetch documents sorted via a DB property */
  protected def documentsByDB[T <: Product](
    config: Option[PresentationConfig],
    fn: () => Future[Page[T]]
  )(implicit ctx: ExecutionContext) = for {
    documents <- fn()
    indexProperties <- config match {
      case Some(c) => 
        val ids = documents.items.map(_.productElement(0).asInstanceOf[DocumentRecord].getId)
        fetchIndexProperties(ids, c).map(Some(_))

      case None => Future.successful(None)
    }
  } yield (documents, indexProperties)

  /** My Documents, sorted by a DB property **/
  protected def getMyDocumentsSortedByDB(
    username: String,
    offset: Int, 
    size: Int, 
    config: Option[PresentationConfig]
  )(implicit request: Request[AnyContent]) = {
    documentsByDB(
      config, 
      () => documents.findByOwnerWithParts(
              username, offset, size,
              config.flatMap(_.sort.map(_.sortBy)),
              config.flatMap(_.sort.map(_.order)))
    ).map { case (documents, indexProperties) =>
      val interleaved = ConfiguredPresentation.forMyDocument(documents, indexProperties.map(_.toMap), config.map(_.columns))
      jsonOk(Json.toJson(interleaved))
    }
  }

  /** 'Shared with me' documents, sorted by a DB property **/
  protected def getSharedDocumentsSortedByDB(
    username: String,
    offset: Int, 
    size: Int, 
    config: Option[PresentationConfig]
  )(implicit request: Request[AnyContent]) = {   
    documentsByDB(
      config, 
      () => documents.findSharedWithPart(
              username, offset, size,
              config.flatMap(_.sort.map(_.sortBy)),
              config.flatMap(_.sort.map(_.order)))
    ).map { case (documents, indexProperties) =>
      val interleaved = ConfiguredPresentation.forSharedDocument(documents, indexProperties.map(_.toMap), config.map(_.columns))
      jsonOk(Json.toJson(interleaved))
    }
  }

  protected def getAccessibleDocumentsSortedByDB(
    owner: String, 
    loggedIn: Option[String],
    offset: Int,
    size: Int,
    config: Option[PresentationConfig]
  )(implicit request: Request[AnyContent]) = {
    documentsByDB(
      config,
      () => documents.findAccessibleDocumentsWithParts(
              owner, loggedIn, offset, size,
              config.flatMap(_.sort.map(_.sortBy)),
              config.flatMap(_.sort.map(_.order)))
    ).map { case (documents, indexProperties) =>
      // TODO fetch with sharing permissions, if any
      val interleaved = ConfiguredPresentation.forMyDocument(documents, indexProperties.map(_.toMap), config.map(_.columns))
      jsonOk(Json.toJson(interleaved))
    }
  }

}