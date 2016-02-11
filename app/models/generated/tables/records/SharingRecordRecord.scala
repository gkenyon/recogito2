/**
 * This class is generated by jOOQ
 */
package models.generated.tables.records


import java.lang.Boolean
import java.lang.Integer
import java.lang.String
import java.time.OffsetDateTime

import javax.annotation.Generated

import models.generated.tables.SharingRecord

import org.jooq.Field
import org.jooq.Record1
import org.jooq.Record8
import org.jooq.Row8
import org.jooq.impl.UpdatableRecordImpl


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = Array(
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	),
	comments = "This class is generated by jOOQ"
)
class SharingRecordRecord extends UpdatableRecordImpl[SharingRecordRecord](SharingRecord.SHARING_RECORD) with Record8[Integer, Integer, Integer, String, String, OffsetDateTime, Boolean, Boolean] {

	/**
	 * Setter for <code>sharing_record.id</code>.
	 */
	def setId(value : Integer) : Unit = {
		setValue(0, value)
	}

	/**
	 * Getter for <code>sharing_record.id</code>.
	 */
	def getId : Integer = {
		val r = getValue(0)
		if (r == null) null else r.asInstanceOf[Integer]
	}

	/**
	 * Setter for <code>sharing_record.folder_id</code>.
	 */
	def setFolderId(value : Integer) : Unit = {
		setValue(1, value)
	}

	/**
	 * Getter for <code>sharing_record.folder_id</code>.
	 */
	def getFolderId : Integer = {
		val r = getValue(1)
		if (r == null) null else r.asInstanceOf[Integer]
	}

	/**
	 * Setter for <code>sharing_record.document_id</code>.
	 */
	def setDocumentId(value : Integer) : Unit = {
		setValue(2, value)
	}

	/**
	 * Getter for <code>sharing_record.document_id</code>.
	 */
	def getDocumentId : Integer = {
		val r = getValue(2)
		if (r == null) null else r.asInstanceOf[Integer]
	}

	/**
	 * Setter for <code>sharing_record.shared_by</code>.
	 */
	def setSharedBy(value : String) : Unit = {
		setValue(3, value)
	}

	/**
	 * Getter for <code>sharing_record.shared_by</code>.
	 */
	def getSharedBy : String = {
		val r = getValue(3)
		if (r == null) null else r.asInstanceOf[String]
	}

	/**
	 * Setter for <code>sharing_record.shared_with</code>.
	 */
	def setSharedWith(value : String) : Unit = {
		setValue(4, value)
	}

	/**
	 * Getter for <code>sharing_record.shared_with</code>.
	 */
	def getSharedWith : String = {
		val r = getValue(4)
		if (r == null) null else r.asInstanceOf[String]
	}

	/**
	 * Setter for <code>sharing_record.shared_at</code>.
	 */
	def setSharedAt(value : OffsetDateTime) : Unit = {
		setValue(5, value)
	}

	/**
	 * Getter for <code>sharing_record.shared_at</code>.
	 */
	def getSharedAt : OffsetDateTime = {
		val r = getValue(5)
		if (r == null) null else r.asInstanceOf[OffsetDateTime]
	}

	/**
	 * Setter for <code>sharing_record.accepted</code>.
	 */
	def setAccepted(value : Boolean) : Unit = {
		setValue(6, value)
	}

	/**
	 * Getter for <code>sharing_record.accepted</code>.
	 */
	def getAccepted : Boolean = {
		val r = getValue(6)
		if (r == null) null else r.asInstanceOf[Boolean]
	}

	/**
	 * Setter for <code>sharing_record.revoked</code>.
	 */
	def setRevoked(value : Boolean) : Unit = {
		setValue(7, value)
	}

	/**
	 * Getter for <code>sharing_record.revoked</code>.
	 */
	def getRevoked : Boolean = {
		val r = getValue(7)
		if (r == null) null else r.asInstanceOf[Boolean]
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------
	override def key() : Record1[Integer] = {
		return super.key.asInstanceOf[ Record1[Integer] ]
	}

	// -------------------------------------------------------------------------
	// Record8 type implementation
	// -------------------------------------------------------------------------

	override def fieldsRow : Row8[Integer, Integer, Integer, String, String, OffsetDateTime, Boolean, Boolean] = {
		super.fieldsRow.asInstanceOf[ Row8[Integer, Integer, Integer, String, String, OffsetDateTime, Boolean, Boolean] ]
	}

	override def valuesRow : Row8[Integer, Integer, Integer, String, String, OffsetDateTime, Boolean, Boolean] = {
		super.valuesRow.asInstanceOf[ Row8[Integer, Integer, Integer, String, String, OffsetDateTime, Boolean, Boolean] ]
	}
	override def field1 : Field[Integer] = SharingRecord.SHARING_RECORD.ID
	override def field2 : Field[Integer] = SharingRecord.SHARING_RECORD.FOLDER_ID
	override def field3 : Field[Integer] = SharingRecord.SHARING_RECORD.DOCUMENT_ID
	override def field4 : Field[String] = SharingRecord.SHARING_RECORD.SHARED_BY
	override def field5 : Field[String] = SharingRecord.SHARING_RECORD.SHARED_WITH
	override def field6 : Field[OffsetDateTime] = SharingRecord.SHARING_RECORD.SHARED_AT
	override def field7 : Field[Boolean] = SharingRecord.SHARING_RECORD.ACCEPTED
	override def field8 : Field[Boolean] = SharingRecord.SHARING_RECORD.REVOKED
	override def value1 : Integer = getId
	override def value2 : Integer = getFolderId
	override def value3 : Integer = getDocumentId
	override def value4 : String = getSharedBy
	override def value5 : String = getSharedWith
	override def value6 : OffsetDateTime = getSharedAt
	override def value7 : Boolean = getAccepted
	override def value8 : Boolean = getRevoked

	override def value1(value : Integer) : SharingRecordRecord = {
		setId(value)
		this
	}

	override def value2(value : Integer) : SharingRecordRecord = {
		setFolderId(value)
		this
	}

	override def value3(value : Integer) : SharingRecordRecord = {
		setDocumentId(value)
		this
	}

	override def value4(value : String) : SharingRecordRecord = {
		setSharedBy(value)
		this
	}

	override def value5(value : String) : SharingRecordRecord = {
		setSharedWith(value)
		this
	}

	override def value6(value : OffsetDateTime) : SharingRecordRecord = {
		setSharedAt(value)
		this
	}

	override def value7(value : Boolean) : SharingRecordRecord = {
		setAccepted(value)
		this
	}

	override def value8(value : Boolean) : SharingRecordRecord = {
		setRevoked(value)
		this
	}

	override def values(value1 : Integer, value2 : Integer, value3 : Integer, value4 : String, value5 : String, value6 : OffsetDateTime, value7 : Boolean, value8 : Boolean) : SharingRecordRecord = {
		this.value1(value1)
		this.value2(value2)
		this.value3(value3)
		this.value4(value4)
		this.value5(value5)
		this.value6(value6)
		this.value7(value7)
		this.value8(value8)
		this
	}

	/**
	 * Create a detached, initialised SharingRecordRecord
	 */
	def this(id : Integer, folderId : Integer, documentId : Integer, sharedBy : String, sharedWith : String, sharedAt : OffsetDateTime, accepted : Boolean, revoked : Boolean) = {
		this()

		setValue(0, id)
		setValue(1, folderId)
		setValue(2, documentId)
		setValue(3, sharedBy)
		setValue(4, sharedWith)
		setValue(5, sharedAt)
		setValue(6, accepted)
		setValue(7, revoked)
	}
}
