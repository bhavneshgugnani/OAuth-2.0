package org.bhavnesh.stackoverflow.oauth;

public class Constants {
	//production server of google, provided by google on client registration/info page
	public static final String OAUTH_REDIRECT_URL = "http://localhost:8080/google/user/oauth/232cd4cd-9f22-430d-8f03-945ec387c1f0";

	//production server of stackoverflow
	public static final String RESPONSE_URL = "http://localhost:8080/stackoverflow/oauth/callback";
	
	public static final String ACCESS = "access";
	public static final String TOKEN = "token";
	public static final String SUCCESS = "success";
}
