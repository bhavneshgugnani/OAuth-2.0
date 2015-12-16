package org.bhavnesh.stackoverflow.oauth;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/oauth")
public class OAuthUserController {

	@RequestMapping(method = RequestMethod.GET, value = "/callback")
	public String receiveTempToken(@RequestParam(value = Constants.ACCESS, required = true) String access, @RequestParam(value = Constants.SUCCESS, required = true) String success, @RequestParam(value = Constants.TOKEN, required = false) String token, ModelMap model) {
		if(success == null || success.length() <= 0){
			//display error page
		} else {
			if(access == null || access.length() <= 0){
				//display access fail page
			} else if(Boolean.getBoolean(access) == false){
				//display access fail page
			} else if(Boolean.getBoolean(access) == true){
				if(token == null || token.length() <= 0){
					//no token in request, failed request
				} else {
					//token received
				}
			}
		}
		return "";
	}
}
