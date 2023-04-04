package ploan.web;
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;

@WebServlet(urlPatterns = {"/Logout"})
public class Logout extends TopServlet{


    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException{

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	Enumeration values = req.getParameterNames();
	String name= "";
	String value = "";
	HttpSession session = null;
	session = req.getSession(false);
	if(session != null){
	    session.invalidate();
	}
	res.sendRedirect(url);
	return;

    }

}






















































