package models.annotation

import com.sksamuel.elastic4s.{ HitAs, RichSearchHit }
import com.sksamuel.elastic4s.ElasticDsl._
import com.sksamuel.elastic4s.source.Indexable
import java.util.UUID
import models.HasDate
import org.elasticsearch.search.sort.SortOrder
import org.joda.time.DateTime
import play.api.Logger
import play.api.libs.json.Json
import scala.concurrent.{ ExecutionContext, Future }
import storage.ES

trait AnnotationHistoryService extends HasAnnotationIndexing with HasDate {

  private val ANNOTATION_HISTORY = "annotation_history"

  /** Inserts a new version into the history index **/
  def insertVersion(annotation: Annotation)(implicit context: ExecutionContext): Future[Boolean] =
    ES.client execute {
      index into ES.IDX_RECOGITO / ANNOTATION_HISTORY source annotation
    } map {
      _.isCreated
    } recover { case t: Throwable =>
      Logger.error("Error storing annotation version")
      Logger.error(t.toString)
      t.printStackTrace
      false
    }

  /** Retrieves a version by annotationId and versionId **/
  def findVersionById(annotationId: UUID, versionId: UUID)(implicit context: ExecutionContext): Future[Option[Annotation]] =
    ES.client execute {
      search in ES.IDX_RECOGITO / ANNOTATION_HISTORY query {
        bool {
          must (
            termQuery("annotation_id" -> annotationId.toString),
            termQuery("version_id" -> versionId.toString)
          )
        }
      }
    } map { _.response.as[(Annotation, Long)].toSeq.headOption.map(_._1) }

  /** Retrieves the latest version stored in the history for a given annotation ID **/
  def findLatestVersion(annotationId: UUID)(implicit context: ExecutionContext): Future[Option[Annotation]] =
    ES.client execute {
      search in ES.IDX_RECOGITO / ANNOTATION_HISTORY query (
        termQuery("annotation_id" -> annotationId.toString)
      ) sort (
        field sort "last_modified_at" order SortOrder.DESC
      ) limit 1
    } map { _.as[(Annotation, Long)].toSeq.headOption.map(_._1) }

  /** Deletes all annotation versions in the history for a document, after a given timestamp **/
  def deleteVersionsAfter(docId: String, after: DateTime)(implicit context: ExecutionContext): Future[Boolean] = {

    // Retrieves all versions on the document after the the given timestamp
    def findVersionsAfter() = ES.client execute {
      search in ES.IDX_RECOGITO / ANNOTATION_HISTORY query filteredQuery query {
        nestedQuery("annotates").query(termQuery("annotates.document_id" -> docId))
      } postFilter {
        rangeFilter("last_modified_at").gt(formatDate(after))
      } limit Int.MaxValue
    } map { _.getHits.getHits }

    findVersionsAfter().flatMap { hits =>
      if (hits.size > 0) {
        ES.client execute {
          bulk ( hits.map(h => delete id h.getId from ES.IDX_RECOGITO / ANNOTATION_HISTORY) )
        } map {
          !_.hasFailures
        } recover { case t: Throwable =>
          t.printStackTrace()
          false
        }
      } else {
        // Nothing to delete
        Future.successful(true)
      }
    }
  }

}
