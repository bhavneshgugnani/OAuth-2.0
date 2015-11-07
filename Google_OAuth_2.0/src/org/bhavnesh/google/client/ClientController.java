package org.bhavnesh.google.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/client")
public class ClientController {

	@RequestMapping(method = RequestMethod.GET)
	public String getClientLandingPage(ModelMap model) {
		return "googleclient";
	}
}
