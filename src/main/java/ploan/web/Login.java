package ploan.web;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.jasig.cas.client.authentication.AttributePrincipal;
import java.net.URL;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
import ploan.utils.*;


@WebServlet(urlPatterns = {"/Login","/login"}, loadOnStartup = 1)
public class Login extends TopServlet{

    //
    static Logger logger = LogManager.getLogger(Login.class);
    /**
     * Generates the login form for all users.
     *
     * @param req the request 
     * @param res the response
     */
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	String ipAddress = "", message="", id="";
	boolean found = false;
	
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	HttpSession session = null;
	String userid = null;
	AttributePrincipal principal = null;				
	if (req.getUserPrincipal() != null) {
	    principal = (AttributePrincipal) req.getUserPrincipal();
	    userid = principal.getName();
	}
	if(userid == null || userid.isEmpty()){
	    userid = req.getRemoteUser();
	}
	if(userid != null){
	    session = req.getSession(false);			
	    // setCookie(req, res);
	    User user = getUser(userid);
	    if(user != null && user.userExists() && session != null){
		session.setAttribute("user",user);
		out.println("<head><title></title><META HTTP-EQUIV=\""+
			    "refresh\" CONTENT=\"0; URL=" + url +
			    "IntroPage?\"></head>");
		out.println("<body>");
		out.println("</body>");
		out.println("</html>");
		out.flush();
		return;
	    }
	    else{
		message = " Unauthorized access ";
	    }
	}
	else{
	    message += " You can not access this system, check with IT or try again later";
	}
	// if we got here, the user is not authorized 
	out.println("<head><title></title><body>");
	out.println("<p><font color=red>");
	out.println(message);
	out.println("</p>");
	out.println("</body>");
	out.println("</html>");
	out.flush();	
    }
	
    void setCookie(HttpServletRequest req, 
		   HttpServletResponse res){ 
	Cookie cookie = null;
	boolean found = false;
	Cookie[] cookies = req.getCookies();
	for(int i=0;i<cookies.length;i++){
	    String name = cookies[i].getName();
	    if(name.equals(cookieName)){
		found = true;
	    }
	}
	//
	// if not found create one with 0 time to live;
	//
	if(!found){
	    cookie = new Cookie(cookieName,cookieValue);
	    res.addCookie(cookie);
	}
    }
    /**
     * get user object given username.
     * 
     * @param username
     */		
    User getUser(String username){

	User user = null;
	User usr = new User(username);
	String back = usr.doSelect();
	if(!back.equals("")){
	    logger.error(back);
	    // message += back;
	}
	else{
	    user = usr;
	}
	return user;
	//
    }

}






















































