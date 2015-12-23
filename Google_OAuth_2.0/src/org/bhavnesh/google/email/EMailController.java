package org.bhavnesh.google.email;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.bhavnesh.google.oauth.security.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/user/email")
public class EMailController {

	@RequestMapping(method = RequestMethod.GET)
	public String getAllEMails(HttpServletRequest request, ModelMap model){
		HttpSession session = request.getSession();
		if(session != null && session.getAttribute(Constants.AUTHENTICATED) != null && Boolean.parseBoolean((String) session.getAttribute(Constants.AUTHENTICATED)) == true){
			model.addAttribute(Constants.DISPLAY_MESSAGE, "You can see your emails below.");
			return "emailpage";
		} else {
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Please login to access email");
			return "google";
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/oauth")
	public void getAllEMailsByOAuth(){
		
	}
}
