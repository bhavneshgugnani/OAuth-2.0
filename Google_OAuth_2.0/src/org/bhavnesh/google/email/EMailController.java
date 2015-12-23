package org.bhavnesh.google.email;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bhavnesh.google.db.DBConnectionManager;
import org.bhavnesh.google.db.DBQueryManager;
import org.bhavnesh.google.oauth.security.AESSecurityProvider;
import org.bhavnesh.google.oauth.security.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
	public String getAllEMails(HttpServletRequest request, ModelMap model) {
		HttpSession session = request.getSession();
		if (session != null && session.getAttribute(Constants.AUTHENTICATED) != null
				&& Boolean.parseBoolean((String) session.getAttribute(Constants.AUTHENTICATED)) == true) {
			model.addAttribute(Constants.DISPLAY_MESSAGE, "You can see your emails below.");
			return "emailpage";
		} else {
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Please login to access email");
			return "google";
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/oauth")
	public void getAllEMailsByOAuth(@RequestParam(value = "token", required = true) String oauthToken,
			@RequestParam(value = "clientid", required = true) String clientId, HttpServletResponse response) {
		try {
			AESSecurityProvider aesSecurityProvider = new AESSecurityProvider(clientId);
			String encryptedToken = aesSecurityProvider.encrypt(oauthToken); 
			StringBuilder query = DBQueryManager.createAccessUsingOAuthToken(encryptedToken, clientId);
			ResultSet rs = dbConnectionManager.executeQuery(query);
			if (rs.next()) {
				String email = rs.getString("EMail");
				//access emails and send in response with a flag
				response.setHeader(Constants.SUCCESS, String.valueOf(true));
			} else {
				response.setHeader(Constants.SUCCESS, String.valueOf(false));
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
}
