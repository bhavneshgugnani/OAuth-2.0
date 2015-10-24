package org.bhavnesh.google.login.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signin")
public class SignInController {
	
	
	@RequestMapping(value="/username", method = RequestMethod.GET)
	public String username(ModelMap model) {
		// Process Username page
		
		return "signinusernamepage";
	}
	
	@RequestMapping(value="/password", method = RequestMethod.GET)
	public String password(ModelMap model) {
		// Store username, Process password page
		return "signinpage";
	}
}
