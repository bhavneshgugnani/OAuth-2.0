package org.bhavnesh.google.oauth.login;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/oauth")
public class OAuthUserLogin {

	@RequestMapping(method = RequestMethod.GET, value="/{clientId}/userlogin")
	public String getOAuthUserLoginForm(@PathVariable("clientId") String clientId, ModelMap model) {
		// extract org information from url and redirect to login page
		
		return "oauthuserlogin";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/{clientId}/userlogin")
	public String postOAuthUserLoginForm(@PathVariable("clientId") String clientId, ModelMap model) {
		// extract user information and redirect to permission page
		
		return "oauthuserpermissionpage";
	}
	
	
}
