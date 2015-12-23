package org.bhavnesh.stackoverflow.signup;

import javax.servlet.http.HttpServletRequest;

import org.bhavnesh.stackoverflow.db.DBConnectionManager;
import org.bhavnesh.stackoverflow.db.DBQueryManager;
import org.bhavnesh.stackoverflow.form.UserRegistrationForm;
import org.bhavnesh.stackoverflow.oauth.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signup")
public class SignUpController {
	private DBConnectionManager dbConnectionManager = null;

	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	@Autowired
	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getSignUp(ModelMap model) {
		return "signuppage";
	}

	@RequestMapping(method = RequestMethod.POST)
	public String signUp(@ModelAttribute("userregistrationform") UserRegistrationForm form, ModelMap model,
			HttpServletRequest request) {
		try {
			// save credentials and login if successfull
			StringBuilder sb = DBQueryManager.createSignUpQuery(form);
			int rs = dbConnectionManager.executeUpdate(sb);
			if (rs == 1) {
				model.addAttribute("username", form.getUsername());
				model.addAttribute(Constants.AUTHENTICATED, true);
				request.getSession().setAttribute(Constants.AUTHENTICATED, true);
				return "stackoverflow";
			} else if (rs == 0) {
				model.addAttribute(Constants.DISPLAY_MESSAGE,
						"User with same information exists in database. Please try different credentials.");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Something went wrong. Please try again.");
		}
		return "signuppage";
	}
}
