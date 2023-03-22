package com.codingsense.model;

public class ApiResponse {
	private String status;
	private String messageId;
	private String destination;

	public ApiResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	@Override
	public String toString() {
		return "ApiResponse [status=" + status + ", messageId=" + messageId + ", destination=" + destination + "]";
	}

}
