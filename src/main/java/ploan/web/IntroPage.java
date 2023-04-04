package ploan.web;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import ploan.model.*;
import ploan.utils.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = {"/IntroPage"})
public class IntroPage extends TopServlet{

    static Logger logger = LogManager.getLogger(IntroPage.class);

    public void doPost(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException{

	doPost(req, res);
    }
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException{
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	
	User user = null;
	HttpSession	session = req.getSession(false);
	if(session != null){
	    user = (User)session.getAttribute("user");
	}
	if(user == null){
	    String str = url+"Login";
	    res.sendRedirect(str);
	    return;
	}
	out.println("<html><head><title>ProLoan</title>");
	Helper.writeWebCss(out, url);
	out.println("</head><body>");
	Helper.writeTopMenu(out, url);
	out.println("<h3>Welcome to Promt </h3>");	
	out.println(" Select one of the options in the top menu. ");
	out.println("<ul>");
    	out.println("<li> To enter a new client, select 'New Client'</li>");
	out.println("<li> To enter a new loan, select 'New Loan'</li>");
	out.println("<li> To enter a new property, select 'New Property'</li>");
	out.println("<li> To search for loans, select 'Loans'</li>");
	out.println("<li> To search for clients, select 'Clients'</li>");
	out.println("<li> To search for properties, select 'Properties'</li>");
	out.println("<li> To run reports, click on 'Reports'</li>");
	out.println("</ul>");
	out.println("</center>");
	out.println("</body>");
	out.println("</html>");
	out.close();
    }
}
