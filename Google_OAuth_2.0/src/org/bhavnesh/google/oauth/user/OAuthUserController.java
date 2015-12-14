package org.bhavnesh.google.oauth.user;

import java.sql.ResultSet;
import java.util.Calendar;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bhavnesh.google.db.DBConnectionManager;
import org.bhavnesh.google.form.UserLoginForm;
import org.bhavnesh.google.oauth.security.Constants;
import org.bhavnesh.google.vo.UserSessinInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/user/oauth")
public class OAuthUserController {
	private DBConnectionManager dbConnectionManager = null;


	public DBConnectionManager getDbConnectionManager() {
		return dbConnectionManager;
	}

	@Autowired
	public void setDbConnectionManager(DBConnectionManager dbConnectionManager) {
		this.dbConnectionManager = dbConnectionManager;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{clientId}")
	public String oAuthUser(@PathVariable("clientId") String clientId, @RequestParam(value = "responseUrl", required = false) String responseUrl, ModelMap model, HttpServletRequest request) {
		model.addAttribute("clientId", clientId);
		model.addAttribute("responseUrl", responseUrl);
		// verify clientid and redirect to login page
		StringBuilder query = createClientInfoQuery(clientId);
		ResultSet rs = dbConnectionManager.executeQuery(query);
		try{
			if(rs.next()){
				if(request.getSession() != null && request.getSession().getAttribute(Constants.AUTHENTICATED) != null && (boolean) request.getSession().getAttribute(Constants.AUTHENTICATED) == true){
					//user already in session, redirect to access granting page
					model.addAttribute("clientName", rs.getString("ClientName"));
					return "oauthusergrantingpage";
				} else {
					//redirect user to login page 
					return "oauthuserloginpage";
				}
			} else {
				model.addAttribute(Constants.DISPLAY_MESSAGE, "Warning! Unknown third party user trying to access your private information.");
			}
		} catch(Exception ex){
			System.out.println(ex.getMessage());
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Failure to load information for third party requester");
		}
		return "unknownclientpage";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/{clientId}/userlogin")
	public String oAuthUserLogin(@ModelAttribute("oauthuserloginform") UserLoginForm form, @PathVariable("clientId") String clientId, ModelMap model, HttpServletRequest request){
		StringBuilder query = createClientInfoQuery(clientId);
		ResultSet rs = dbConnectionManager.executeQuery(query);
		try{
			if(rs.next()){
				//save client info 
				String clientName = rs.getString("ClientName");
				model.addAttribute("clientName", clientName);
				request.getSession().setAttribute("clientName", clientName);
			} else {
				model.addAttribute(Constants.DISPLAY_MESSAGE, "Warning! Unknown third party user trying to access your private information.");
				return "unknownclientpage";
			}
			//do user authentication
			query = createUserLoginQuery(form);
			rs = dbConnectionManager.executeQuery(query);
			if(rs.next()){
				//user login success
				UserSessinInfoVo userInfo = new UserSessinInfoVo();
				userInfo.setFirstName(rs.getString("FirstName"));
				userInfo.setLastName(rs.getString("LastName"));
				userInfo.setEmail(rs.getString("EMail"));
				
				HttpSession session = request.getSession();
				session.setAttribute(Constants.AUTHENTICATED, true);
				session.setAttribute("clientId", clientId);
				session.setAttribute(Constants.USER_SESSION_INFO, userInfo);
				
				model.addAttribute(Constants.USER_SESSION_INFO, userInfo);
				model.addAttribute(Constants.AUTHENTICATED, true);
				model.addAttribute("displayname", userInfo.getFirstName());
				
				return "oauthusergrantingpage";
			} else {
				model.addAttribute("clientId", clientId);
				model.addAttribute("responseUrl", form.getResponseUrl());
				model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to Login! Please try Again.");
				return "oauthuserloginpage";
			}
		} catch(Exception ex){
			System.out.println(ex.getMessage());
		}
		
		model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to Login! Please try Again.");
		return "oauthuserlogin";
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/{clientId}/userlogout")
	public String oAuthUserLogout(@PathVariable("clientId") String clientId, ModelMap model, HttpServletRequest request, HttpServletResponse response){
		return "";
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/{clientId}/permission")
	public String grantPermission(@PathVariable("clientId") String clientId, @RequestParam(value = "permissionGrant", required = false) boolean permissionGrant, ModelMap model, HttpServletRequest request, HttpServletResponse response){
		try {
			HttpSession session = request.getSession();
			if (session != null && session.getAttribute(Constants.AUTHENTICATED) != null
					&& Boolean.parseBoolean(session.getAttribute(Constants.AUTHENTICATED).toString()) == true) {
				String responseUrl = (String) session.getAttribute("responseUrl");
				if (!permissionGrant) {
					responseUrl += "?access=false";
				} else {
					// generate temporary authToken and store in db
					// token has timeout of 5 min.
					
					UUID uuid = UUID.randomUUID();
					UserSessinInfoVo userInfo = (UserSessinInfoVo) session.getAttribute(Constants.USER_SESSION_INFO);
					StringBuilder query = createTempTokenQuery(clientId, userInfo.getEmail(), uuid.toString(), Long.toString((Calendar.getInstance().getTimeInMillis())+(5*60*1000)));
					int rs = dbConnectionManager.executeUpdate(query);
					if(rs == 1){
						// success
						responseUrl += "?access=true&success=true&token=" + uuid;
					} else if(rs == 0) {//rs == 0
						// failure
						responseUrl += "?access=true&success=false";
					} else if(rs == -1) {
						//duplicate entry in db, invalid case
						responseUrl += "?access=false&success=false";
					}
				}
				response.sendRedirect(responseUrl);
				return "";
			} else {
				model.addAttribute("clientId", clientId);
				model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to Login! Please try Again.");
				return "oauthuserlogin";
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to grant permission! Please try again.");
		model.addAttribute("clientName", request.getSession().getAttribute("clientName"));
		model.addAttribute("clientId", clientId);
		return "oauthusergrantingpage";
	}
	
	
	private StringBuilder createClientInfoQuery(String clientId){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ClientName FROM oauth2_0.google_client WHERE Id='");
		sb.append(clientId);
		sb.append("';");
		return sb;
	}
	
	private StringBuilder createUserLoginQuery(UserLoginForm form){
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT FirstName, LastName , EMail FROM oauth2_0.google_user WHERE EMail='");
		sb.append(form.getEmail());
		sb.append("' AND Password='");
		sb.append(form.getPassword());
		sb.append("';");
		return sb;
	}
	
	private StringBuilder createTempTokenQuery(String clientId, String eMail, String token, String timeout){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO oauth2_0.google_temp_token (Id, ClientId, EMail, Token, Timeout) VALUES ('0', '");
		sb.append(clientId);
		sb.append("' ,'");
		sb.append(eMail);
		sb.append("' ,'");
		sb.append(token);
		sb.append("' ,'");
		sb.append(timeout);
		sb.append("');");
		return sb;
	}
	
	private StringBuilder createNewOAuthTokenQuery(String clientId, String eMail, String token){
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO oauth2_0.google_oauth_token (Id, ClientId, EMail, OAuthToken) VALUES ('0', '");
		sb.append(clientId);
		sb.append("' ,'");
		sb.append(eMail);
		sb.append("' ,'");
		sb.append(token);
		sb.append("');");
		return sb;
	}
	
	private StringBuilder createValidateOAuthTokenQuery(){
		StringBuilder sb = new StringBuilder();
		
		return sb;
	}
}
