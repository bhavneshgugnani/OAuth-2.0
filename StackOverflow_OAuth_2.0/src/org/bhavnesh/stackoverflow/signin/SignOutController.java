package org.bhavnesh.stackoverflow.signin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bhavnesh.stackoverflow.db.DBConnectionManager;
import org.bhavnesh.stackoverflow.oauth.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signout")
public class SignOutController {
	private DBConnectionManager dbConnectionManager = null;

	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	@Autowired
	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String signout(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		session.removeAttribute(Constants.AUTHENTICATED);
		session.removeAttribute(Constants.USERNAME);
		session.removeAttribute(Constants.LINKED);
		session.invalidate();
		return "stackoverflow";
	}
}
