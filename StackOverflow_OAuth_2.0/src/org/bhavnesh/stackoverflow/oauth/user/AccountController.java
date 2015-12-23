package org.bhavnesh.stackoverflow.oauth.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user")
public class AccountController {

	@RequestMapping(method = RequestMethod.GET)
	public String manageAccount(ModelMap model, HttpServletRequest request){
		return "manageaccount";
	}
	
	
}
