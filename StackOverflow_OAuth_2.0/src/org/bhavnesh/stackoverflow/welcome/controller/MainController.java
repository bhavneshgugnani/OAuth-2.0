package org.bhavnesh.stackoverflow.welcome.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

	@RequestMapping(method = RequestMethod.GET)
	public String google(ModelMap model) {
		
		return "stackoverflow";
	}
}
