package org.bhvanesh.google.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class TestController {

	@RequestMapping(method = RequestMethod.GET, value="/{clientId}")
	public void test(){
		
	}
}
