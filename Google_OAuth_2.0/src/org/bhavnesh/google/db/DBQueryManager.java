package org.bhavnesh.google.db;

import java.util.List;
import java.util.UUID;

import org.bhavnesh.google.form.ClientLoginForm;
import org.bhavnesh.google.form.ClientRegistrationForm;
import org.bhavnesh.google.form.UserLoginForm;
import org.bhavnesh.google.form.UserRegistrationForm;

public class DBQueryManager {

	public static StringBuilder createLoginQuery(ClientLoginForm form) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"SELECT Id, ClientName, ClientUsername, ClientEmail, ClientAddress FROM oauth2_0.google_client WHERE ClientUsername='");
		sb.append(form.getUsername());
		sb.append("' AND ClientPassword='");
		sb.append(form.getPassword());
		sb.append("';");
		return sb;
	}

	public static StringBuilder createRegistrationQuery(ClientRegistrationForm form, String secretKey) {
		// generate unique id for client
		UUID uuid = UUID.randomUUID();

		StringBuilder sb = new StringBuilder();
		sb.append(
				"INSERT INTO oauth2_0.google_client (Id, ClientName, ClientUsername, ClientPassword, ClientAddress, ClientEmail, ClientSecret) VALUES ('");
		sb.append(uuid.toString());
		sb.append("', '");
		sb.append(form.getClientName());
		sb.append("', '");
		sb.append(form.getClientUsername());
		sb.append("', '");
		sb.append(form.getClientPassword());
		sb.append("', '");
		sb.append(form.getClientAddress());
		sb.append("', '");
		sb.append(form.getClientEmail());
		sb.append("', '");
		sb.append(secretKey);
		sb.append("');");
		return sb;
	}

	public static StringBuilder createIdRequestQuery(String clientUsername) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT Id FROM oauth2_0.google_client WHERE ClientUsername='");
		sb.append(clientUsername);
		sb.append("';");
		return sb;
	}

	public static StringBuilder createClientInfoQuery(String clientId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ClientName FROM oauth2_0.google_client WHERE Id='");
		sb.append(clientId);
		sb.append("';");
		return sb;
	}

	public static StringBuilder createUserLoginQuery(UserLoginForm form) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT FirstName, LastName , EMail FROM oauth2_0.google_user WHERE EMail='");
		sb.append(form.getEmail());
		sb.append("' AND Password='");
		sb.append(form.getPassword());
		sb.append("';");
		return sb;
	}

	public static StringBuilder createTempTokenQuery(String clientId, String eMail, String token, String timeout) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO oauth2_0.google_temp_token (Id, ClientId, EMail, Token, Timeout) VALUES ('0', '");
		sb.append(clientId);
		sb.append("' ,'");
		sb.append(eMail);
		sb.append("' ,'");
		sb.append(token);
		sb.append("' ,'");
		sb.append(timeout);
		sb.append("');");
		return sb;
	}

	public static StringBuilder createClientSecretQuery(String clientId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ClientSecret FROM oauth2_0.google_client where Id='");
		sb.append(clientId);
		sb.append("';");
		return sb;
	}

	public static StringBuilder createTokenTimeoutQuery(String token, String clientId) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT Timeout FROM oauth2_0.google_temp_token WHERE Token='");
		sb.append(token);
		sb.append("' AND ClientId='");
		sb.append(clientId);
		sb.append("';");
		return sb;
	}

	public static StringBuilder createNewOAuthTokenQuery(String clientId, String eMail, String token) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO oauth2_0.google_oauth_token (Id, ClientId, EMail, OAuthToken) VALUES ('0', '");
		sb.append(clientId);
		sb.append("' ,'");
		sb.append(eMail);
		sb.append("' ,'");
		sb.append(token);
		sb.append("');");
		return sb;
	}

	public static StringBuilder createValidateOAuthTokenQuery(String token) {
		StringBuilder sb = new StringBuilder();

		return sb;
	}

	public static StringBuilder createQuery(UserRegistrationForm form) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"INSERT INTO oauth2_0.google_user (Id, FirstName, LastName, EMail, Password, Age, Gender, Address, Phone) VALUES('0', '");
		sb.append(form.getFirstname());
		sb.append("', '");
		sb.append(form.getLastname());
		sb.append("', '");
		sb.append(form.getUsername() + "@gmail.com");
		sb.append("', '");
		sb.append(form.getPassword());
		sb.append("', ");
		sb.append(form.getAge());
		sb.append(", '");
		sb.append(form.getGender());
		sb.append("', '");
		sb.append(form.getAddress());
		sb.append("', '");
		sb.append(form.getPhone());
		sb.append("');");
		System.out.println(sb.toString());
		return sb;
	}

	public static StringBuilder createUsernameQuery(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT FirstName, LastName FROM oauth2_0.google_user WHERE EMail='");
		sb.append(email);
		sb.append("';");
		return sb;
	}

	public static StringBuilder createLoginQuery(String email, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT FirstName, LastName FROM oauth2_0.google_user WHERE EMail='");
		sb.append(email);
		sb.append("' AND Password='");
		sb.append(password);
		sb.append("';");
		return sb;
	}
	
	public static final StringBuilder createOAuthLinkedAccountQuery(String email){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ClientId FROM oauth2_0.google_oauth_token WHERE EMail='");
		sb.append(email);
		sb.append("';");
		return sb;
	}
	
	public static final StringBuilder createGetClientQuery(List<String> clientIds){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT Id, ClientName FROM oauth2_0.google_client WHERE Id IN (");
		for(String id : clientIds)
			sb.append("'" + id + "'");
		sb.append(");");
		return sb;
	}
	
	public static final StringBuilder createRemoveOAuthClientQuery(String clientId){
		StringBuilder sb = new StringBuilder();
		sb.append("");
		return sb;
	}
}
