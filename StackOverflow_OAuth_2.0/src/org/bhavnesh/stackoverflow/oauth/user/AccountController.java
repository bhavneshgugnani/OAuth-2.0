package org.bhavnesh.stackoverflow.oauth.user;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bhavnesh.stackoverflow.oauth.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class AccountController {

	@RequestMapping(method = RequestMethod.GET, value = "/account")
	public String manageAccount(ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		model.addAttribute(Constants.AUTHENTICATED, session.getAttribute(Constants.AUTHENTICATED));
		model.addAttribute(Constants.LINKED, session.getAttribute(Constants.LINKED));
		return "manageaccount";
	}

}
