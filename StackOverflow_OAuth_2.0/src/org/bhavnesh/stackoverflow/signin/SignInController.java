package org.bhavnesh.stackoverflow.signin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signin")
public class SignInController {

	@RequestMapping(method = RequestMethod.GET)
	public String getForm(ModelMap model) {
		// Provide login form
		return "signinpage";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String signIn(ModelMap model) {
		// Process login credentials
		return "stackoverflow";
	}
}
