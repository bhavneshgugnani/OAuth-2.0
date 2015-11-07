package org.bhavnesh.google.form;

import java.util.List;

public class ClientRegistrationForm {

	private String clientName = null;
	private String clientUsername = null;
	private String clientPassword = null;
	private String clientAddress = null;
	private String clientEmail = null;
	private List<String> infoAccessNames = null;

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getClientUsername() {
		return clientUsername;
	}

	public void setClientUsername(String clientUsername) {
		this.clientUsername = clientUsername;
	}

	public String getClientPassword() {
		return clientPassword;
	}

	public void setClientPassword(String clientPassword) {
		this.clientPassword = clientPassword;
	}

	public String getClientAddress() {
		return clientAddress;
	}

	public void setClientAddress(String clientAddress) {
		this.clientAddress = clientAddress;
	}

	public String getClientEmail() {
		return clientEmail;
	}

	public void setClientEmail(String clientEmail) {
		this.clientEmail = clientEmail;
	}
}
