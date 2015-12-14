package org.bhavnesh.google.user;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bhavnesh.google.db.DBConnectionManager;
import org.bhavnesh.google.form.SingleValueForm;
import org.bhavnesh.google.form.UserLoginForm;
import org.bhavnesh.google.oauth.security.Constants;
import org.bhavnesh.google.vo.UserSessinInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class UserLoginController {
	private DBConnectionManager dbConnectionManager = null;


	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	@Autowired
	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	@RequestMapping(value = "/username", method = RequestMethod.GET)
	public String username(ModelMap model) {
		// Process Username page
		model.addAttribute("authenticated", false);
		return "signinusernamepage";
	}

	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public String password(@ModelAttribute("singlevalueform") SingleValueForm form, ModelMap model, HttpServletRequest request) {
		// Store username, Process password page
		String email = form.getValue();
		StringBuilder query = createUsernameQuery(email);
		String firstName = null, lastName = null;
		ResultSet rs = dbConnectionManager.executeQuery(query);
		try {
			if(rs.next()) {
				firstName = rs.getString("FirstName");
				lastName = rs.getString("LastName");
				
				UserSessinInfoVo userSessionInfo = new UserSessinInfoVo(firstName, lastName, email);
				HttpSession session = request.getSession();
				//session.setAttribute("email", email);
				session.setAttribute("authenticated", false);
				session.setAttribute(Constants.USER_SESSION_INFO, userSessionInfo);
				
				//model.addAttribute("firstName", firstName);
				//model.addAttribute("lastName", lastName);
				//model.addAttribute("email", email);
				model.addAttribute(Constants.USER_SESSION_INFO, userSessionInfo);
				return "signinpasswordpage";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("message", "Invalid Username! Please try again with a valid email.");
		return "signinusernamepage";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(@ModelAttribute("userloginform") UserLoginForm form, ModelMap model, HttpServletRequest request) {
		// Validate Password, Process Main page
		StringBuilder query = createLoginQuery(form.getEmail(), form.getPassword());
		ResultSet rs = dbConnectionManager.executeQuery(query);
		String firstName = null, lastName = null;
		try {
			if(rs.next()) {
				firstName = rs.getString("FirstName");
				lastName = rs.getString("LastName");
				
				HttpSession session = request.getSession();
				session.setAttribute("authenticated", true);
				session.setAttribute("firstname", firstName);
				session.setAttribute("lastname", lastName);
				
				model.addAttribute("authenticated", true);
				model.addAttribute("firstName", firstName);
				model.addAttribute("lastName", lastName);
				model.addAttribute("email", session.getAttribute("email"));
				return "signinpasswordpage";
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("message", "Invalid Password! Please try again.");
		return "signinusernamepage";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(ModelMap model,HttpServletRequest request) {
		model.addAttribute("authenticated", false);
		request.getSession().invalidate();
		return "google";
	}

	private StringBuilder createUsernameQuery(String email) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT FirstName, LastName FROM oauth2_0.google_user WHERE EMail='");
		sb.append(email);
		sb.append("';");
		return sb;
	}

	private StringBuilder createLoginQuery(String email, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT FirstName, LastName FROM oauth2_0.google_user WHERE EMail='");
		sb.append(email);
		sb.append("' AND Password='");
		sb.append(password);
		sb.append("';");
		return sb;
	}
}
