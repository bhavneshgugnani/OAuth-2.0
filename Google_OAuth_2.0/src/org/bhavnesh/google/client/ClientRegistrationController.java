package org.bhavnesh.google.client;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.bhavnesh.google.db.DBConnectionManager;
import org.bhavnesh.google.db.DBQueryManager;
import org.bhavnesh.google.form.ClientRegistrationForm;
import org.bhavnesh.google.oauth.security.AESSecurityProvider;
import org.bhavnesh.google.oauth.security.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/client/registration")
public class ClientRegistrationController {
	private DBConnectionManager dbConnectionManager = null;
	private AESSecurityProvider aesSecurityProvider = null;

	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	@Autowired
	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	public AESSecurityProvider getAesSecurityProvider() {
		return aesSecurityProvider;
	}

	@Autowired
	public void setAesSecurityProvider(AESSecurityProvider aesSecurityProvider) {
		this.aesSecurityProvider = aesSecurityProvider;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getRegistrationForm(ModelMap model) {
		return "oauthclientregistrationpage";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String postRegistrationForm(@ModelAttribute("clientregistrationform") ClientRegistrationForm form,
			ModelMap model, HttpServletRequest request) {
		try {
			// store info and provide resource server address
			// new instance of security provider
			this.aesSecurityProvider = new AESSecurityProvider();
			String encodedKey = this.aesSecurityProvider.getEncodedKey();
			StringBuilder query = DBQueryManager.createRegistrationQuery(form, encodedKey);
			// make entry in db
			this.dbConnectionManager.executeUpdate(query);
			// if entry successful, retrieve id of client to create unique oauth
			// url for user authentication
			query = DBQueryManager.createIdRequestQuery(form.getClientUsername());
			ResultSet rs = this.dbConnectionManager.executeQuery(query);
			if (rs.next()) {
				request.getSession().setAttribute("authenticated", true);
				String clientId = rs.getString("Id");
				model.addAttribute("clientname", form.getClientName());
				model.addAttribute("clientusername", form.getClientUsername());
				model.addAttribute("clientemail", form.getClientEmail());
				model.addAttribute("clientaddress", form.getClientAddress());
				model.addAttribute("clientid", clientId);
				model.addAttribute("clientoauthurl", Constants.O_AUTH_2_0_URL_PREFIX + clientId);
				model.addAttribute("message", "Registration Successfull!");
				return "oauthclientinfopage";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("message", "Unable to Register! Please try with different client username.");
		return "oauthclientregistrationpage";
	}

}
