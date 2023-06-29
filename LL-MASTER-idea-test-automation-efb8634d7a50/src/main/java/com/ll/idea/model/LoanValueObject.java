package com.ll.idea.model;

public class LoanValueObject {
	String loanState;
	String loanId;
	String loanNumber;
	String loanBatchId;
	String loanStatus;
	String relativeConfidenceValue;
	String calculatedConfidenceValue;
	String seperationalConfidenceValue;
	String mlDocumentTypeValue;
	
	public String getRelativeConfidenceValue() {
		return relativeConfidenceValue;
	}
	public void setRelativeConfidenceValue(String relativeConfidenceValue) {
		this.relativeConfidenceValue = relativeConfidenceValue;
	}
	public String getCalculatedConfidenceValue() {
		return calculatedConfidenceValue;
	}
	public void setCalculatedConfidenceValue(String calculatedConfidenceValue) {
		this.calculatedConfidenceValue = calculatedConfidenceValue;
	}
	public String getSeperationalConfidenceValue() {
		return seperationalConfidenceValue;
	}
	public void setSeperationalConfidenceValue(String seperationalConfidenceValue) {
		this.seperationalConfidenceValue = seperationalConfidenceValue;
	}
	public String getMlDocumentTypeValue() {
		return mlDocumentTypeValue;
	}
	public void setMlDocumentTypeValue(String mlDocumentTypeValue) {
		this.mlDocumentTypeValue = mlDocumentTypeValue;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getLoanState() {
		return loanState;
	}
	public void setLoanState(String loanState) {
		this.loanState = loanState;
	}
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanNumber() {
		return loanNumber;
	}
	public void setLoanNumber(String loanNumber) {
		this.loanNumber = loanNumber;
	}
	public String getLoanBatchId() {
		return loanBatchId;
	}
	public void setLoanBatchId(String loanBatchId) {
		this.loanBatchId = loanBatchId;
	}
	
}
