package org.bhavnesh.google.user;

import javax.servlet.http.HttpServletRequest;

import org.bhavnesh.google.db.DBConnectionManager;
import org.bhavnesh.google.db.DBQueryManager;
import org.bhavnesh.google.form.UserRegistrationForm;
import org.bhavnesh.google.oauth.security.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserRegistrationController {
	private DBConnectionManager dbConnectionManager = null;

	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	@Autowired
	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String getSignUp(ModelMap model) {
		model.addAttribute("authenticated", false);
		return "newuserregistrationpage";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postSignUp(@ModelAttribute("userregistrationform") UserRegistrationForm form, ModelMap model,
			HttpServletRequest request) {
		try {
			// save credentials and login if successfull
			StringBuilder sb = DBQueryManager.createQuery(form);
			int rs = dbConnectionManager.executeUpdate(sb);
			if (rs == 1) {
				model.addAttribute("username", form.getUsername());
				model.addAttribute(Constants.AUTHENTICATED, true);
				request.getSession().setAttribute(Constants.AUTHENTICATED, true);
				return "google";
			} else if (rs == 0) {
				model.addAttribute(Constants.DISPLAY_MESSAGE,
						"User with same information exists in database. Please try different credentials.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Something went wrong. Please try again.");
		}
		return "newuserregistrationpage";
	}

}
