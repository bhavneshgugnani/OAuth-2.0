package org.bhavnesh.google.user;

import org.bhavnesh.google.db.DBConnectionManager;
import org.bhavnesh.google.form.UserRegistrationForm;
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

	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String getSignUp(ModelMap model) {
		model.addAttribute("authenticated", false);
		return "newuserregistrationpage";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String postSignUp(@ModelAttribute("userregistrationform") UserRegistrationForm form, ModelMap model) {
		// save credentials
		StringBuilder sb = createQuery(form);
		dbConnectionManager.executeUpdate(sb);
		model.addAttribute("username", form.getUsername());
		model.addAttribute("authenticated", true);
		return "google";
	}

	private StringBuilder createQuery(UserRegistrationForm form) {
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO oauth2_0.google_user (Id, FirstName, LastName, EMail, Password, Age, Gender, Address, Phone) VALUES('0', '");
		sb.append(form.getFirstname());
		sb.append("', '");
		sb.append(form.getLastname());
		sb.append("', '");
		sb.append(form.getUsername() + "@gmail.com");
		sb.append("', '");
		sb.append(form.getPassword());
		sb.append("', ");
		sb.append(form.getAge());
		sb.append(", '");
		sb.append(form.getGender());
		sb.append("', '");
		sb.append(form.getAddress());
		sb.append("', '");
		sb.append(form.getPhone());
		sb.append("');");
		System.out.println(sb.toString());
		return sb;
	}
}
