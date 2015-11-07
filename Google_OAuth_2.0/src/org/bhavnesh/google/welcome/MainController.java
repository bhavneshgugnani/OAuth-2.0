package org.bhavnesh.google.welcome;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class MainController {

	@RequestMapping(method = RequestMethod.GET)
	public String google(ModelMap model) {
		return "google";
	}
	
	@RequestMapping(value="search", method = RequestMethod.GET)
	public String search(ModelMap model) {
		return "search";
	}
}
