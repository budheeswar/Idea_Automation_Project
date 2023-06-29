package com.ll.idea.model;

public class IdeaDocumentType {
	public String documentTypeId = null;
	public String documentTypeName = null;
	public String imagePageNumber = null;
	public String documentPageNumber = null;
	
	public String getImagePageNumber() {
		return imagePageNumber;
	}

	public void setImagePageNumber(String imagePageNumber) {
		this.imagePageNumber = imagePageNumber;
	}

	public String getDocumentPageNumber() {
		return documentPageNumber;
	}

	public void setDocumentPageNumber(String documentPageNumber) {
		this.documentPageNumber = documentPageNumber;
	}

	public String getDocumentTypeId() {
		return documentTypeId;
	}

	public void setDocumentTypeId(String documentTypeId) {
		this.documentTypeId = documentTypeId;
	}

			
	public String getDocumentTypeName() {
		return documentTypeName;
	}

	public void setDocumentTypeName(String documentTypeName) {
		this.documentTypeName = documentTypeName;
	}

	public IdeaDocumentType() {
		//Empty constructor to instantiate an object
	}

}
