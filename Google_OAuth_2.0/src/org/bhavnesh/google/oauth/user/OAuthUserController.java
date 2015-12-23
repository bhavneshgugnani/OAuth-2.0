package org.bhavnesh.google.oauth.user;

import java.net.URLEncoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bhavnesh.google.db.DBConnectionManager;
import org.bhavnesh.google.db.DBQueryManager;
import org.bhavnesh.google.form.UserLoginForm;
import org.bhavnesh.google.oauth.security.AESSecurityProvider;
import org.bhavnesh.google.oauth.security.Constants;
import org.bhavnesh.google.vo.ClientVO;
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

	@RequestMapping(method = RequestMethod.GET)
	public String manageOAuthAccount(HttpServletRequest request, ModelMap model) {
		// get all clients linked to oauth for user account
		try {
			String email = (String) request.getSession().getAttribute(Constants.EMAIL);
			// get ids of all clients linked
			StringBuilder query = DBQueryManager.createOAuthLinkedAccountQuery(email);
			ResultSet rs = dbConnectionManager.executeQuery(query);
			List<String> clientIds = new ArrayList<>();
			while (rs.next()) {
				clientIds.add(rs.getString("ClientId"));
			}

			if (!clientIds.isEmpty()) {
				// get names of clients
				query = DBQueryManager.createGetClientQuery(clientIds);
				rs = dbConnectionManager.executeQuery(query);
				ClientVO client = null;
				List<ClientVO> clients = new ArrayList<>();
				while (rs.next()) {
					client = new ClientVO();
					client.setClientId(rs.getString("Id"));
					client.setClientName(rs.getString("ClientName"));
					clients.add(client);
				}
				if (!clients.isEmpty()) {
					model.addAttribute(Constants.OAUTH_CLIENTS, clients);
				}
			}
			return "useroauthclients";
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		return "google";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/remove/{clientId}")
	public void removeOAuthClient(@PathVariable("clientId") String clientId, HttpServletRequest request,
			ModelMap model) {
		try {
			String email = (String) request.getSession().getAttribute(Constants.EMAIL);
			StringBuilder query = DBQueryManager.createRemoveOAuthClientQuery(clientId, email);
			int rs = dbConnectionManager.executeUpdate(query);
			if (rs == 1) {
				// successful
				model.addAttribute(Constants.DISPLAY_MESSAGE, "OAuth Client unlinked successfully!");
			} else if (rs == 0) {
				// error, failed to delete, wrong params
				model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to unlink account. Please try again.");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to unlink account. Unexpected error.");
		}
		// page reloaded by client
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{clientId}")
	public String oAuthUser(@PathVariable("clientId") String clientId,
			@RequestParam(value = "responseUrl", required = false) String responseUrl, ModelMap model,
			HttpServletRequest request) {
		model.addAttribute("clientId", clientId);
		model.addAttribute("responseUrl", responseUrl);
		// verify clientid and redirect to login page
		StringBuilder query = DBQueryManager.createClientInfoQuery(clientId);
		ResultSet rs = dbConnectionManager.executeQuery(query);
		try {
			if (rs.next()) {
				if (request.getSession() != null && request.getSession().getAttribute(Constants.AUTHENTICATED) != null
						&& (boolean) request.getSession().getAttribute(Constants.AUTHENTICATED) == true) {
					// user already in session, redirect to access granting page
					model.addAttribute("clientName", rs.getString("ClientName"));
					return "oauthusergrantingpage";
				} else {
					// redirect user to login page
					return "oauthuserloginpage";
				}
			} else {
				model.addAttribute(Constants.DISPLAY_MESSAGE,
						"Warning! Unknown third party user trying to access your private information.");
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Failure to load information for third party requester");
		}
		return "unknownclientpage";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{clientId}/userlogin")
	public String oAuthUserLogin(@ModelAttribute("oauthuserloginform") UserLoginForm form,
			@PathVariable("clientId") String clientId, ModelMap model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		StringBuilder query = DBQueryManager.createClientInfoQuery(clientId);
		ResultSet rs = dbConnectionManager.executeQuery(query);
		try {
			if (rs.next()) {
				// save client info
				String clientName = rs.getString("ClientName");
				model.addAttribute("clientName", clientName);
				request.getSession().setAttribute("clientName", clientName);
			} else {
				model.addAttribute(Constants.DISPLAY_MESSAGE,
						"Warning! Unknown third party user trying to access your private information.");
				return "unknownclientpage";
			}
			String responseUrl = form.getResponseUrl();
			model.addAttribute("clientId", clientId);
			session.setAttribute(Constants.CLIENT_ID, clientId);
			session.setAttribute(Constants.RESPONSE_URL, responseUrl);

			// do user authentication
			query = DBQueryManager.createUserLoginQuery(form);
			rs = dbConnectionManager.executeQuery(query);
			if (rs.next()) {
				// user login success
				String firstName = rs.getString("FirstName");

				session.setAttribute(Constants.AUTHENTICATED, true);

				session.setAttribute(Constants.EMAIL, rs.getString("EMail"));
				session.setAttribute(Constants.FIRST_NAME, firstName);
				session.setAttribute(Constants.LAST_NAME, rs.getString("LastName"));

				// model.addAttribute(Constants.USER_SESSION_INFO, userInfo);
				model.addAttribute(Constants.AUTHENTICATED, true);
				model.addAttribute("displayname", firstName);

				return "oauthusergrantingpage";
			} else {

				model.addAttribute("responseUrl", responseUrl);
				model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to Login! Please try Again.");
				return "oauthuserloginpage";
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}

		model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to Login! Please try Again.");
		return "oauthuserlogin";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{clientId}/userlogout")
	public String oAuthUserLogout(@PathVariable("clientId") String clientId, ModelMap model, HttpServletRequest request,
			HttpServletResponse response) {
		return "";
	}

	@RequestMapping(method = RequestMethod.POST, value = "/{clientId}/permission")
	public String grantPermission(@PathVariable("clientId") String clientId,
			@RequestParam(value = "permissionGrant", required = true) boolean permissionGrant, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			HttpSession session = request.getSession();
			AESSecurityProvider aesSecurityProvider = null;
			if (session != null && session.getAttribute(Constants.AUTHENTICATED) != null
					&& Boolean.parseBoolean(session.getAttribute(Constants.AUTHENTICATED).toString()) == true) {
				String responseUrl = (String) session.getAttribute(Constants.RESPONSE_URL);
				if (!permissionGrant) {
					responseUrl += "?" + Constants.ACCESS + "=false";
				} else {
					// generate temporary authToken and store in db
					// token has timeout of 5 min.
					// extract client secret for encryption of temp token
					StringBuilder query = DBQueryManager.createClientSecretQuery(clientId);
					ResultSet rs = dbConnectionManager.executeQuery(query);
					if (rs.next()) {
						UUID uuid = UUID.randomUUID();
						aesSecurityProvider = new AESSecurityProvider(rs.getString("ClientSecret"));
						String encryptedToken = aesSecurityProvider.encrypt(uuid.toString().trim());
						query = DBQueryManager.createTempTokenQuery(clientId,
								(String) session.getAttribute(Constants.EMAIL), encryptedToken,
								Long.toString((Calendar.getInstance().getTimeInMillis()) + (5 * 60 * 1000)));
						int res = dbConnectionManager.executeUpdate(query);
						if (res == 1) {
							// success
							responseUrl += "?" + Constants.ACCESS + "=true&" + Constants.SUCCESS + "=true&"
									+ Constants.TOKEN + "=" + URLEncoder.encode(uuid.toString().trim(), "UTF-8");
							System.out.println("GOOGLE : Temp Token = *******" + encryptedToken.trim() + "********");
						} else if (res == 0) {// rs == 0
							// failure
							responseUrl += "?" + Constants.ACCESS + "=true&" + Constants.SUCCESS + "=false";
						} else if (res == -1) {
							// duplicate entry in db, invalid case
							responseUrl += "?" + Constants.ACCESS + "=false&" + Constants.SUCCESS + "=false";
						}
					} else {
						// client secret missing in db
						responseUrl += "?" + Constants.ACCESS + "=false&" + Constants.SUCCESS + "=false";
					}

				}
				// logout user from google account for security
				// automatically
				// session.invalidate();

				// return "redirect:" + responseUrl;
				model.addAttribute("redirectUrl", responseUrl);
				model.addAttribute(Constants.DISPLAY_MESSAGE,
						"Authorization request complete. Logging out user from current session. Redirecting back to host website. Please wait...");
				return "oauthredirectpage";

			} else {
				model.addAttribute("clientId", clientId);
				model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to Login! Please try Again.");
				return "oauthuserlogin";
			}

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			model.addAttribute(Constants.DISPLAY_MESSAGE, "Something went wroing! Please try again later.");
		}
		model.addAttribute(Constants.DISPLAY_MESSAGE, "Failed to grant permission! Please try again.");
		model.addAttribute("clientName", request.getSession().getAttribute("clientName"));
		model.addAttribute("clientId", clientId);
		return "oauthusergrantingpage";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/{clientId}/requestoauthtoken")
	public void getOAuthToken(@PathVariable("clientId") String clientId, @RequestParam("token") String token,
			ModelMap model, HttpServletRequest request, HttpServletResponse response) {
		// below parameters are set as header fields in response
		String oauthToken = "";
		String message = "";
		boolean success = false;

		try {
			// find client secret from db
			StringBuilder query = DBQueryManager.createClientSecretQuery(clientId);
			ResultSet rs = dbConnectionManager.executeQuery(query);
			if (rs.next()) {
				// valid clientId, get secret key for client form db for
				// encryption/decryption of token
				String clientSecret = rs.getString("ClientSecret");
				AESSecurityProvider aesSecurityProvider = new AESSecurityProvider(clientSecret);

				// encrypt token to see if valid
				String encryptedToken = aesSecurityProvider.encrypt(token);
				query = DBQueryManager.createTokenTimeoutQuery(encryptedToken, clientId);
				rs = dbConnectionManager.executeQuery(query);
				if (rs.next()) {
					// token valid, check expiry
					String timeout = rs.getString("Timeout");
					String email = rs.getString("EMail");
					Calendar now = Calendar.getInstance();
					Calendar tokenExpiry = Calendar.getInstance();
					tokenExpiry.setTimeInMillis(Long.valueOf(timeout).longValue());
					if (!tokenExpiry.before(now)) {
						// valid token, generate oauth token
						UUID uuid = UUID.randomUUID();
						String encryptedOAuthToken = aesSecurityProvider.encrypt(uuid.toString().trim());
						query = DBQueryManager.createNewOAuthTokenQuery(clientId, email, encryptedOAuthToken);
						int result = dbConnectionManager.executeUpdate(query);
						if (result == 1) {
							// delete temp token
							query = DBQueryManager.createDeleteTempTokenQuery(encryptedToken, clientId);
							dbConnectionManager.executeUpdate(query);

							oauthToken = URLEncoder.encode(uuid.toString().trim(), "UTF-8");
							success = true;
							System.out.println("GOOGLE : OAuth Token = *******" + uuid.toString().trim() + "********");
						} else if (result == 0) {
							// no row affected in table
							success = false;
							message = "OAuth token generation failed. Please try again.";
						}
					} else {
						// expired token
						success = false;
						message = "Expired token.";
					}
				} else {
					// invalid token
					success = false;
					message = "Invalid Token";
				}
			} else {
				// invalid client id
				success = false;
				message = "Invalid client id.";
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			message = "Something went wrong. Please try again later.";
		} finally {
			// add final results to response header
			response.setHeader(Constants.TOKEN, oauthToken);
			response.setHeader(Constants.SUCCESS, String.valueOf(success));
			response.setHeader(Constants.DISPLAY_MESSAGE, message);
		}
	}
}
