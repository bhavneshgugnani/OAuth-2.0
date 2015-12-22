package org.bhavnesh.stackoverflow.oauth.user;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bhavnesh.stackoverflow.oauth.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/oauth/redirect")
public class OAuthRedirectController {

	@RequestMapping(method = RequestMethod.GET)
	public String redirectToGoogle(ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
		String redirectUrl = Constants.OAUTH_REDIRECT_URL + "?responseUrl=" + Constants.RESPONSE_URL;
		model.addAttribute("redirectUrl", redirectUrl);
		model.addAttribute(Constants.DISPLAY_MESSAGE, "Redirecting to Google page for Authorization. Please wait...");
		return "oauthredirectpage";
	}
}
