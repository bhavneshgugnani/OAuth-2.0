package org.bhavnesh.stackoverflow.db;

import org.bhavnesh.stackoverflow.form.UserRegistrationForm;
import org.bhavnesh.stackoverflow.form.UserSignInForm;

public class DBQueryManager {

	public static final StringBuilder createSignUpQuery(UserRegistrationForm form) {
		StringBuilder sb = new StringBuilder();
		sb.append(
				"INSERT INTO oauth2_0.stackoverflow_user (ID, FirstName, LastName, Username, Password, Age, Gender) VALUES ('0', '");
		sb.append(form.getFirstname());
		sb.append("', '");
		sb.append(form.getLastname());
		sb.append("', '");
		sb.append(form.getUsername());
		sb.append("', '");
		sb.append(form.getPassword());
		sb.append("', '");
		sb.append(form.getAge());
		sb.append("', '");
		sb.append(form.getGender());
		sb.append("');");
		return sb;
	}

	public static final StringBuilder createSignInQuery(UserSignInForm form) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT * FROM oauth2_0.stackoverflow_user WHERE Username='");
		sb.append(form.getUsername());
		sb.append("' AND Password='");
		sb.append(form.getPassword());
		sb.append("';");
		return sb;
	}

	public static final StringBuilder createAddOAuthTokenQuery(String oauthToken, String username) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO oauth2_0.stackoverflow_user_oauth (ID, Username, OAuthToken) VALUES ('0', '");
		sb.append(username);
		sb.append("', '");
		sb.append(oauthToken);
		sb.append("');");
		return sb;
	}

}
