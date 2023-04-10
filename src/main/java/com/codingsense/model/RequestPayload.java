package com.codingsense.model;

public class RequestPayload {
	private String username;
	private String password;
	private String msisdn;
	private String cli;
	private String message;
	private String clienttransid;
	private String rn_code;
	private String type;
	private String longSMS;
	private String dataCoding;
	private Boolean isUnicode;
	private String unicode;
	private Boolean isFlash;
	private String flash;
	private Boolean isLongSMS;

	public RequestPayload() {
	}

	public RequestPayload(String username, String password, String msisdn, String cli, String message,
			String clienttransid, String rn_code, String type, String longSMS, String dataCoding, Boolean isUnicode,
			String unicode, Boolean isFlash, String flash, Boolean isLongSMS) {
		super();
		this.username = username;
		this.password = password;
		this.msisdn = msisdn;
		this.cli = cli;
		this.message = message;
		this.clienttransid = clienttransid;
		this.rn_code = rn_code;
		this.type = type;
		this.longSMS = longSMS;
		this.dataCoding = dataCoding;
		this.isUnicode = isUnicode;
		this.unicode = unicode;
		this.isFlash = isFlash;
		this.flash = flash;
		this.isLongSMS = isLongSMS;
	}

	public Boolean getIsUnicode() {
		return isUnicode;
	}

	public void setIsUnicode(Boolean isUnicode) {
		this.isUnicode = isUnicode;
	}

	public Boolean getIsFlash() {
		return isFlash;
	}

	public void setIsFlash(Boolean isFlash) {
		this.isFlash = isFlash;
	}

	public Boolean getIsLongSMS() {
		return isLongSMS;
	}

	public void setIslongSMS(Boolean isLongSMS) {
		this.isLongSMS = isLongSMS;
	}

	public boolean isIsLongSMS() {
		return isLongSMS;
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

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getCli() {
		return cli;
	}

	public void setCli(String cli) {
		this.cli = cli;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getClienttransid() {
		return clienttransid;
	}

	public void setClienttransid(String clienttransid) {
		this.clienttransid = clienttransid;
	}

	public String getRn_code() {
		return rn_code;
	}

	public void setRn_code(String rn_code) {
		this.rn_code = rn_code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLongSMS() {
		return longSMS;
	}

	public void setLongSMS(String longSMS) {
		this.longSMS = longSMS;
	}

	public String getDataCoding() {
		return dataCoding;
	}

	public void setDataCoding(String dataCoding) {
		this.dataCoding = dataCoding;
	}

	public boolean isUnicode() {
		return isUnicode;
	}

	public String getUnicode() {
		return unicode;
	}

	public void setUnicode(String unicode) {
		this.unicode = unicode;
	}

	public boolean isFlash() {
		return isFlash;
	}

	public String getFlash() {
		return flash;
	}

	public void setFlash(String flash) {
		this.flash = flash;
	}

	@Override
	public String toString() {
		return "RequestPayload [username=" + username + ", password=" + password + ", msisdn=" + msisdn + ", cli=" + cli
				+ ", message=" + message + ", clienttransid=" + clienttransid + ", rn_code=" + rn_code + ", type="
				+ type + ", longSMS=" + longSMS + ", dataCoding=" + dataCoding + ", isUnicode=" + isUnicode
				+ ", unicode=" + unicode + ", isFlash=" + isFlash + ", flash=" + flash + ", isLongSMS=" + isLongSMS
				+ "]";
	}

}