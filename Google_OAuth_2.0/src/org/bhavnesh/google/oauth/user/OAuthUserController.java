//package org.bhavnesh.google.oauth.user;
//
//import java.sql.ResultSet;
//
//import javax.servlet.http.HttpServletRequest;
//
//import org.bhavnesh.google.db.DBConnectionManager;
//import org.bhavnesh.google.form.UserLoginForm;
//import org.bhavnesh.google.oauth.security.Constants;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.ModelMap;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//@Controller
//@RequestMapping("/oauth/user")
//public class OAuthUserController {
//	private DBConnectionManager dbConnectionManager = null;
//
//
//	public DBConnectionManager getDbConnectionManager() {
//		return dbConnectionManager;
//	}
//
//	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
//		this.dbConnectionManager = dbConnectionManager;
//	}
//	
//	@RequestMapping(method = RequestMethod.GET, value="/{clientId}")
//	public String oAuthUser(@PathVariable("clientId") String clientId, @RequestParam(value = "redirectUrl", required = false) String redirectUrl, ModelMap model, HttpServletRequest request) {
//		if(request.getSession().getAttribute(Constants.AUTHENTICATED) != null && (boolean) request.getSession().getAttribute(Constants.AUTHENTICATED) == true){
//			//user already in session, redirect to access granting page
//			accessGrantingPage(clientId, model);
//		} else {
//			//redirect user to login page 
//			userLoginPage(clientId, model);
//		}
//		
//		// extract org information from url and redirect to login page
//		StringBuilder query = createClientInfoQuery(clientId);
//		ResultSet rs = dbConnectionManager.executeQuery(query);
//		
//		try{
//			if(rs.next()){
//				model.addAttribute("clientname", rs.getString(""));
//				
//			}
//		} catch(Exception ex){
//			
//		}
//		
//		return "oauthuserlogin";
//	}
//	
//	@RequestMapping(method = RequestMethod.GET, value="/{clientId}/userlogin")
//	public String oAuthUserLogin(@ModelAttribute("userloginform") UserLoginForm form, ModelMap model, HttpServletRequest request){
//		StringBuilder query = createUserLoginQuery(form);
//		ResultSet rs = dbConnectionManager.executeQuery(query);
//		try{
//			if(rs.next()){
//				model.addAttribute("clientname", rs.getString(""));
//			}
//		} catch(Exception ex){
//			ex.printStackTrace();
//		}
//		model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to Login! Please try Again.");
//		return "oauthuserlogin";
//	}
//	
//	private String userLoginPage(String clientId, ModelMap model){
//		model.addAttribute("clientid", clientId);
//		return "oauthuserloginpage";
//	}
//	
//	private String accessGrantingPage(String clientId, ModelMap model){
//		model.addAttribute("clientid", clientId);
//		
//		return "oauthusergrantingpage";
//	}
//	
//	private StringBuilder createClientInfoQuery(String clientId){
//		StringBuilder sb = new StringBuilder();
//		sb.append("");
//		return sb;
//	}
//	
//	private StringBuilder createUserLoginQuery(UserLoginForm form){
//		StringBuilder sb = new StringBuilder();
//		sb.append("");
//		return sb;
//	}
//}
