package org.bhavnesh.stackoverflow.signup;

import org.bhavnesh.stackoverflow.oauth.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/signup")
public class SignUpController {

	@RequestMapping(method = RequestMethod.GET)
	public String getSignUp(ModelMap model) {
		return "signuppage";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String signUp(ModelMap model) {
		
		return "stackoverflow";
	}
}
