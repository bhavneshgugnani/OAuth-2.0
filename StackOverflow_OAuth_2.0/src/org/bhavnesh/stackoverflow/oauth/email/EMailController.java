package org.bhavnesh.stackoverflow.oauth.email;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.ResultSet;
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

@Controller
@RequestMapping("/user/email")
public class EMailController {
	private DBConnectionManager dbConnectionManager = null;

	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	@Autowired
	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String viewEMails(HttpServletRequest request, ModelMap model) {
		try {
			HttpURLConnection conn = null;
			// request email access from google based on auth token
			String username = (String) request.getSession().getAttribute(Constants.USERNAME);
			StringBuilder query = DBQueryManager.createGetOAuthTokenForUser(username);
			ResultSet rs = dbConnectionManager.executeQuery(query);
			if (rs.next()) {
				String oauthToken = rs.getString("OAuthToken");
				
				// request google for email access
				URL url = new URL(Constants.GOOGLE_EMAIL_URL + URLEncoder.encode(Constants.CLIENT_ID.trim(), "UTF-8") + "&token=" + URLEncoder.encode(oauthToken.trim(), "UTF-8")); 
				conn = (HttpURLConnection) url.openConnection();
				
				Map<String, List<String>> headersFields = conn.getHeaderFields();
				String success = headersFields.get(Constants.SUCCESS).get(0);
				if(success == null || Boolean.parseBoolean(success) == false){
					//access denied
					model.addAttribute(Constants.DISPLAY_MESSAGE, "Access Denied!");
				} else if(Boolean.parseBoolean(success) == true){
					model.addAttribute(Constants.DISPLAY_MESSAGE, "Access Allowed. You can view your Google emails here.");
				}
			} else {
				// no google account linked
				model.addAttribute(Constants.DISPLAY_MESSAGE, "No Google account linked to this account.");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Something went wrong. Please try again later.");
		}
		HttpSession session = request.getSession();
		model.addAttribute(Constants.AUTHENTICATED, session.getAttribute(Constants.AUTHENTICATED));
		model.addAttribute(Constants.LINKED, session.getAttribute(Constants.LINKED));
		return "emailpage";
	}
}
