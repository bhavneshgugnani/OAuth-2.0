package org.bhavnesh.google.client;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.bhavnesh.google.db.DBConnectionManager;
import org.bhavnesh.google.form.ClientLoginForm;
import org.bhavnesh.google.oauth.security.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/client")
public class ClientLoginController {
	private DBConnectionManager dbConnectionManager = null;

	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String getClientLoginPage(ModelMap model) {
		return "googleclientloginpage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("clientloginform") ClientLoginForm form, ModelMap model, HttpServletRequest request) {
		try {
			StringBuilder query = createLoginQuery(form);
			ResultSet rs = dbConnectionManager.executeQuery(query);
			if (rs.next()) {
				request.getSession().setAttribute("authenticated", true);
				String clientId = rs.getString("Id");
				model.addAttribute("clientname", rs.getString("ClientName"));
				model.addAttribute("clientusername", rs.getString("ClientUsername"));
				model.addAttribute("clientaddress", rs.getString("ClientAddress"));
				model.addAttribute("clientemail", rs.getString("ClientEmail"));
				model.addAttribute("clientid", clientId);
				model.addAttribute("clientoauthurl", Constants.O_AUTH_2_0_URL_PREFIX + clientId);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("message", "Invalid Credentials! Please try Again.");
		return "googleclientloginpage";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model, HttpServletRequest request) {
		request.getSession().invalidate();
		return "googleclientloginpage";
	}

	private StringBuilder createLoginQuery(ClientLoginForm form) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT Id, ClientName, ClientUsername, ClientEmail, ClientAddress FROM oauth2_0.google_client WHERE ClientUsername='");// ,
																																			// ClientPassword,
																																			// ClientAddress,
																																			// ClientEmail,
																																			// ClientSecret)
																																			// VALUES('0',
																																			// '");
		sb.append(form.getUsername());
		sb.append("' AND ClientPassword='");
		sb.append(form.getPassword());
		sb.append("';");
		return sb;
	}
}
