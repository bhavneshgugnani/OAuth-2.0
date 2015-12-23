package org.bhavnesh.stackoverflow.signin;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bhavnesh.stackoverflow.db.DBConnectionManager;
import org.bhavnesh.stackoverflow.db.DBQueryManager;
import org.bhavnesh.stackoverflow.form.UserSignInForm;
import org.bhavnesh.stackoverflow.oauth.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signin")
public class SignInController {
	private DBConnectionManager dbConnectionManager = null;

	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	@Autowired
	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getForm(ModelMap model) {
		// Provide login form
		return "signinpage";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String signIn(@ModelAttribute("usersigninform") UserSignInForm form, ModelMap model,
			HttpServletRequest request) {
		// Process login credentials
		try {
			StringBuilder query = DBQueryManager.createSignInQuery(form);
			ResultSet rs = dbConnectionManager.executeQuery(query);
			if (rs.next()) {
				String username = rs.getString("Username");
				boolean linked = Boolean.parseBoolean(rs.getString("Linked"));
				HttpSession session = request.getSession();
				session.setAttribute(Constants.AUTHENTICATED, true);
				session.setAttribute(Constants.USERNAME, username);
				session.setAttribute(Constants.LINKED, linked);

				model.addAttribute(Constants.AUTHENTICATED, true);
				model.addAttribute(Constants.USERNAME, username);
				model.addAttribute(Constants.LINKED, linked);
			} else {
				model.addAttribute(Constants.DISPLAY_MESSAGE, "Invalid credentials! Please try again.");
				return "signinpage";
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Something went wrong. Please try again.");
			return "signinpage";
		}
		return "stackoverflow";
	}
}
