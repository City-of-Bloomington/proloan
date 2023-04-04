package ploan.web;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import ploan.utils.*;
import ploan.model.*;

@WebServlet(urlPatterns = {"/Wizard"})
public class Wizard extends TopServlet{

    String bgcolor = LoanServ.bgcolor;

    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	doPost(req,res);
    }
    /**
     * @link #doGetost
     */

    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException {
    
	String id="";

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String name, value;
	
	Enumeration values = req.getParameterNames();
	String [] vals;
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("id")){
		id = value;
	    }
	}
	User user = null;
	HttpSession session = null;
	session = req.getSession();
	if(session != null){
	    user = (User)session.getAttribute("user");
	}
	if(user == null){
	    res.sendRedirect(url+"Login");
	    return;
	}		
	//
	//
	out.println("<html><head><title>ProLoan</title>");
	Helper.writeWebCss(out, url);
	out.println("<script language=javascript>                   ");
	out.println("  function validateForm(){		                   ");
	out.println("	return true;		      	                   ");
	out.println("	}			       	                   ");
	out.println(" </script>				                   ");
    	out.println(" </head><body>                ");
	out.println("<center>");
	Helper.writeTopMenu(out, url);
	out.println("<h2>Loan Wizard </h2>");
	out.println("<table border width=80%><tr><td align=center>");
	out.println("<table width=95%>");
	out.println("<tr><td><br>");
	out.println("<form name=myForm method=post action=\""+url+
		    "Client\">");
	out.println("<input type=hidden name=wizard value=y>");
	//
	// Owner 
	out.println(" This wizard will guide you through three "+
		    "steps of adding a complete loan record to the <br>"+
		    " Loan database. <br>The first step starts by entering "+
		    " the Client information,<br> The second step the "+
		    " property information <br>"+
		    " and the final step is adding the  loan "+
		    " information. <br>You can go back and forth between the "+
		    " steps as needed. <br>"+
		    " The wizard is intended to link the client, property and"+
		    " loan info together. Which will reduce the time for "+
		    " the user to link them by himself/herself in the loan "+
		    " page. <br> <br>To start click on the 'Next' button.");
	out.println("</td></tr>");
	out.println("<tr><td>&nbsp;&nbsp;</td></tr>");
	//
	// Next
	out.println("<tr><td align=right>");
	out.println("<input type=submit "+
		    "value=\"&nbsp;&nbsp;Next&nbsp;&nbsp;\"><br>");
	out.println("</td></tr></table>");
	out.println("</td></tr></table>");

    	out.println("<br>");
	out.println("<a href="+url+"Logout target=_top>Log Out</a>");
	out.flush();
	//
	out.print("</body></html>");
	out.flush();
	out.close();

    }

}






















































