package services.folder.create

import java.util.{Date, UUID}
import java.sql.Timestamp
import org.jooq.DSLContext
import services.SharingLevel
import services.folder.FolderService
import services.generated.Tables.{FOLDER_ASSOCIATION, SHARING_POLICY}
import services.generated.tables.records.{FolderAssociationRecord, SharingPolicyRecord}

trait CreateOps { self: FolderService => 

  private def insertAssociation(documentId: String, folderId: UUID, sql: DSLContext) = {
    val association = new FolderAssociationRecord(folderId, documentId)
    sql.insertInto(FOLDER_ASSOCIATION).set(association).execute()
    association
  }

  /** Adds a document to a folder **/
  def addDocumentToFolder(documentId: String, folderId: UUID) = 
    db.withTransaction { sql => insertAssociation(documentId, folderId, sql) }

  /** Same as addToFolder, but removes existing association if any **/
  def moveDocumentToFolder(documentId: String, folderId: UUID) =
    db.withTransaction { sql => 
      sql.deleteFrom(FOLDER_ASSOCIATION)
         .where(FOLDER_ASSOCIATION.DOCUMENT_ID.equal(documentId))
         .execute

      insertAssociation(documentId, folderId, sql)
    }

  def addCollaborator(folderId: UUID, sharedBy: String, sharedWith: String, level: SharingLevel) = 
    db.query { sql => 
      val existing = sql.selectFrom(SHARING_POLICY)
        .where(SHARING_POLICY.FOLDER_ID.equal(folderId)
          .and(SHARING_POLICY.SHARED_WITH.equal(sharedWith))).fetchOne 

      val policy = Option(existing) match {
        case Some(policy) =>
          policy.setSharedBy(sharedBy)
          policy.setSharedAt(new Timestamp(new Date().getTime))
          policy.setAccessLevel(level.toString)
          policy

        case None => 
          val policy = new SharingPolicyRecord(
            null, // auto-inc id
            folderId,
            null, // document_id
            sharedBy,
            sharedWith,
            new Timestamp(new Date().getTime),
            level.toString)

          policy.changed(SHARING_POLICY.ID, false)     
          sql.attach(policy)
          policy
      }
      
      policy.store() == 1
    }

}