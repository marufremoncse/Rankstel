package com.codingsense.model;

import java.util.Arrays;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class ApiPayload {
	private long id;
	private String username;
	private String password;
	private String sender;
	private String text;
	private String type;
	private String dataCoding;
	private String validityPeriod;
	private String[] gsm;

	public ApiPayload() {
		super();
		Random r = new Random();
		this.id = (long) (r.nextDouble() * 60 * 60 * 24 * 365);
	}

	public ApiPayload(String username, String password, String sender, String text, String type, String dataCoding,
			String validityPeriod, String[] gsm) {
		super();
		Random r = new Random();
		this.id = (long) (r.nextDouble() * 60 * 60 * 24 * 365);
		this.username = username;
		this.password = password;
		this.sender = sender;
		this.text = text;
		this.type = type;
		this.dataCoding = dataCoding;
		this.validityPeriod = validityPeriod;
		this.gsm = gsm;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String[] getGsm() {
		return gsm;
	}

	public void setGsm(String[] gsm) {
		this.gsm = gsm;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDataCoding() {
		return dataCoding;
	}

	public void setDataCoding(String dataCoding) {
		this.dataCoding = dataCoding;
	}

	public String getValidityPeriod() {
		return validityPeriod;
	}

	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}

	@Override
	public String toString() {
		return "ApiPayload [id=" + id + ", username=" + username + ", password=" + password + ", sender=" + sender
				+ ", text=" + text + ", type=" + type + ", dataCoding=" + dataCoding + ", validityPeriod="
				+ validityPeriod + ", gsm=" + Arrays.toString(gsm) + "]";
	}

}
