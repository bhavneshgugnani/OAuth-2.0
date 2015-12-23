package org.bhavnesh.stackoverflow.oauth.user;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bhavnesh.stackoverflow.db.DBConnectionManager;
import org.bhavnesh.stackoverflow.db.DBQueryManager;
import org.bhavnesh.stackoverflow.oauth.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth")
public class OAuthUserController {
	private DBConnectionManager dbConnectionManager = null;

	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	@Autowired
	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/callback")
	public String receiveTempToken(@RequestParam(value = Constants.ACCESS, required = true) String access,
			@RequestParam(value = Constants.SUCCESS, required = true) String success,
			@RequestParam(value = Constants.TOKEN, required = false) String token, ModelMap model,
			HttpServletRequest request) {
		try {
			if (success == null || success.length() <= 0 || Boolean.parseBoolean(success) == false) {
				// display error on main page
				model.addAttribute(Constants.DISPLAY_MESSAGE,
						"Authorization failed! Something went wrong. Please try again.");
			} else {
				if (access == null || access.length() <= 0 || Boolean.parseBoolean(access) == false) {
					// display access fail page
					model.addAttribute(Constants.DISPLAY_MESSAGE,
							"Authorization Failed! Failed to load user information. Please try again or create a new account");
				} else if (Boolean.parseBoolean(access) == true) {
					if (token == null || token.length() <= 0) {
						// no token in request, failed request
						model.addAttribute(Constants.DISPLAY_MESSAGE,
								"No Token received. Authorization failure. Please try again to generate new token.");
					} else {
						// token received
						HttpSession session = request.getSession();
						session.setAttribute(Constants.TOKEN, URLDecoder.decode(token.trim(), "UTF-8"));
						System.out.println("STACKOVERFLOW : Temp Token = *******"
								+ URLDecoder.decode(token.trim(), "UTF-8") + "********");
						model.addAttribute(Constants.DISPLAY_MESSAGE,
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

		HttpURLConnection conn = null;
		try {
			URL url = new URL(Constants.OAUTH_REQUEST_URL + "?token=" + URLEncoder.encode(token.trim(), "UTF-8"));// java.net.URLEncoder.encode(Constants.OAUTH_REQUEST_URL
			// +
			// "?token="
			// +
			// token,
			// "UTF-8"));
			// String url = Constants.OAUTH_REQUEST_URL + "?token=" + token;
			// String encodedURL=java.net.URLEncoder.encode(url,"UTF-8");
			conn = (HttpURLConnection) url.openConnection();

			// get header fields for result
			Map<String, List<String>> headerFields = conn.getHeaderFields();
			boolean success = Boolean.parseBoolean(headerFields.get(Constants.SUCCESS).get(0));
			if (success) {
				// save token for future access
				String oauthToken = headerFields.get(Constants.TOKEN).get(0);
				String username = (String) session.getAttribute(Constants.USERNAME);
				StringBuilder query = DBQueryManager.createAddOAuthTokenQuery(oauthToken, username);
				int rs = dbConnectionManager.executeUpdate(query);
				if(rs == 1){
					// update state if user
					query = DBQueryManager.createUpdateLinkedStateOfUser(username, String.valueOf(true));
					rs = dbConnectionManager.executeUpdate(query);
					if(rs == 1){
						model.addAttribute(Constants.DISPLAY_MESSAGE, "Account linked successfully!");
						session.setAttribute(Constants.LINKED, true);
					} else if(rs == 0){
						model.addAttribute(Constants.DISPLAY_MESSAGE, "Something went wrong. Please try again");
					}
					
				} else {
					model.addAttribute(Constants.DISPLAY_MESSAGE, "Something went wrong. Please try again");
				}
				// remove temp token form session
				session.removeAttribute(Constants.TOKEN);
			} else {
				// failure, display message on UI
				model.addAttribute(Constants.DISPLAY_MESSAGE, headerFields.get(Constants.DISPLAY_MESSAGE).get(0));
				model.addAttribute(Constants.SUCCESS, "false");
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "manageaccount";
	}
}
