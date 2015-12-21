package org.bhavnesh.stackoverflow.oauth.user;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bhavnesh.stackoverflow.oauth.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth")
public class OAuthUserController {

	@RequestMapping(method = RequestMethod.GET, value = "/callback")
	public String receiveTempToken(@RequestParam(value = Constants.ACCESS, required = true) String access,
			@RequestParam(value = Constants.SUCCESS, required = true) String success,
			@RequestParam(value = Constants.TOKEN, required = false) String token, ModelMap model,
			HttpServletRequest request) {
		try {
			if (success == null || success.length() <= 0 || Boolean.parseBoolean(success) == false) {
				// display error on main page
				model.addAttribute(Constants.MESSAGE, "Authorization failed! Something went wrong. Please try again.");
			} else {
				if (access == null || access.length() <= 0 || Boolean.parseBoolean(access) == false) {
					// display access fail page
					model.addAttribute(Constants.MESSAGE,
							"Authorization Failed! Failed to load user information. Please try again or create a new account");
				} else if (Boolean.parseBoolean(access) == true) {
					if (token == null || token.length() <= 0) {
						// no token in request, failed request
						model.addAttribute(Constants.MESSAGE,
								"No Token received. Authorization failure. Please try again to generate new token.");
					} else {
						// token received
						HttpSession session = request.getSession();
						session.setAttribute(Constants.TOKEN, URLDecoder.decode(token.trim(), "UTF-8"));
						System.out.println("STACKOVERFLOW : Temp Token = *******" + URLDecoder.decode(token.trim(), "UTF-8") + "********");
						model.addAttribute(Constants.MESSAGE,
								"Authorization Token Received. Please wait while OAuth Token is being generated for this request.");
						return "oauthtokenreceivedpage";
					}
				}
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "stackoverflow";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/requestoauthtoken")
	public String requestOAuthToken(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute(Constants.TOKEN);
		// Request google server for oauth token in exchange of temp token
		// provided by google before token timeout
		StringBuilder result = new StringBuilder();
		BufferedReader rd = null;
		try {
			URL url = new URL(Constants.OAUTH_REQUEST_URL + "?token=" + URLEncoder.encode(token.trim(), "UTF-8"));// java.net.URLEncoder.encode(Constants.OAUTH_REQUEST_URL
																											// +
																											// "?token="
																											// +
																											// token,
																											// "UTF-8"));
			// String url = Constants.OAUTH_REQUEST_URL + "?token=" + token;
			// String encodedURL=java.net.URLEncoder.encode(url,"UTF-8");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.addRequestProperty(Constants.TOKEN, token);
			conn.setRequestMethod("GET");
			rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			System.out.println(line);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		} finally {
			try {
				rd.close();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}

		System.out.println(result.toString());
		return "oauthtokenreceivedpage";
	}
}
