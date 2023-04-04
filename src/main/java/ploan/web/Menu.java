package ploan.web;
import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import ploan.web.*;

public class Menu extends TopServlet{

    String WEBserver = "";
    String url3="", url2="";
    String bgcolor = LoanServ.bgcolor;

    PrintWriter os = null;
    /**
     * Generates the menu form.
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException{

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();

	Enumeration values = req.getParameterNames();
	String name, value, id="";
	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    value = (req.getParameter(name)).trim();
	    if(name.equals("id")){
		id = value;
	    }
	}
	boolean allowed = false;
	if(url.equals("")){
	    url  = getServletContext().getInitParameter("url");
	    url2 = getServletContext().getInitParameter("url2");
	    url3 = getServletContext().getInitParameter("url3");
	    String debug2 = getServletContext().getInitParameter("debug");
	    if(debug2.equals("true")) debug = true;
	}
	//
	// check for the user
	//
	out.println("<HTML><HEAD><TITLE>User Menu </TITLE>  ");
	out.println("<script language=Javascript1.2>        ");
	out.println("  function movepic(img_name,img_src) { ");
	out.println("  document[img_name].src=img_src;      ");
	out.println("      }                                ");   
	out.println("</script>");  
	//
	out.println("</HEAD><BODY><br>");
	out.println("<h3><font color=blue><center>"+
		    "Menu</font></h3>");
	out.println("<table><tr><td bgcolor=silver valign=top>");
	out.println("<table cellpadding=0 cellspacing=0>"+
		    "<tr><td width=100 height=20>");
	//
	// New Client
	out.println("<a href="+url+"Client?"+
		    " target=rightFrame ");
	out.println(" onmouseover=\"movepic('client','"+url3+
		    "client2.jpg');\" " ); 
	out.println(" onmouseout=\"movepic('client','"+url3+
		    "client.jpg');\">");
	out.println("<IMG src=\""+url3+"client.jpg\" name=client "+
		    " border=0 alt=\"New Client\"></a>");
	out.println("</td></tr>");
	//
	// New Property
	out.println("<tr><td>");
	out.println("<a href="+url+"Property?"+
		    " target=rightFrame ");
	out.println(" onmouseover=\"movepic('property','"+url3+
		    "property2.jpg');\" " ); 
	out.println(" onmouseout=\"movepic('property','"+url3+
		    "property.jpg');\">");
	out.println("<IMG src=\""+url3+"property.jpg\" name=property "+
		    " border=0 alt=\"New Property\"></a>");
	out.println("</td></tr>");
	//
	// New Loan
	out.println("<tr><td>");
	out.println("<a href="+url+"Loan?"+
		    " target=rightFrame ");
	out.println(" onmouseover=\"movepic('loan','"+url3+
		    "loan2.jpg');\" " ); 
	out.println(" onmouseout=\"movepic('loan','"+url3+
		    "loan.jpg');\">");
	out.println("<IMG src=\""+url3+"loan.jpg\" name=loan "+
		    " border=0 alt=\"New Loan\"></a>");
	out.println("</td></tr>");
	//
	// Wizard
	out.println("<tr><td>");
	out.println("<a href="+url+"Wizard?"+
		    " target=rightFrame ");
	out.println(" onmouseover=\"movepic('wiz','"+url3+
		    "wizard2.jpg');\" " ); 
	out.println(" onmouseout=\"movepic('wiz','"+url3+
		    "wizard.jpg');\">");
	out.println("<IMG src=\""+url3+"wizard.jpg\" name=wiz "+
		    " border=0 alt=\"Loan Wizard\"></a>");
	out.println("</td></tr>");
	//
	// Search Loans
	out.println("<tr><td width=100 height=20>");
	out.println("<a href="+url+"Browse?"+
		    " target=rightFrame ");
	out.println(" onmouseover=\"movepic('search','"+url3+
		    "s_loan2.jpg');\" " ); 
	out.println(" onmouseout=\"movepic('search','"+url3+
		    "s_loan.jpg');\">");
	out.println("<IMG src=\""+url3+"s_loan.jpg\" name=search "+
		    " border=0 alt=\"Search Loans\"></a>");
	out.println("</td></tr>");
	//
	// Search Client
	out.println("<tr><td width=100 height=20>");
	out.println("<a href="+url+"ClientBrowse?"+
		    " target=rightFrame ");
	out.println(" onmouseover=\"movepic('search2','"+url3+
		    "s_client2.jpg');\" " ); 
	out.println(" onmouseout=\"movepic('search2','"+url3+
		    "s_client.jpg');\">");
	out.println("<IMG src=\""+url3+"s_client.jpg\" name=search2 "+
		    " border=0 alt=\"Search clients\"></a>");
	out.println("</td></tr>");
	//
	// Search Properties
	out.println("<tr><td width=100 height=20>");
	out.println("<a href="+url+"PropBrowse?"+
		    " target=rightFrame ");
	out.println(" onmouseover=\"movepic('search3','"+url3+
		    "s_property2.jpg');\" " ); 
	out.println(" onmouseout=\"movepic('search3','"+url3+
		    "s_property.jpg');\">");
	out.println("<IMG src=\""+url3+"s_property.jpg\" name=search3 "+
		    " border=0 alt=\"Search properties\"></a>");
	out.println("</td></tr>");
	//
	// periodic report
	out.println("<tr><td valign=bottom>");
	out.println("<a href="+url+"Reporter?"+
		    " target=rightFrame ");
	out.println(" onmouseover=\"movepic('report','"+url3+
		    "report2.jpg');\" " ); 
	out.println(" onmouseout=\"movepic('report','"+url3+
		    "report.jpg');\">");
	out.println("<IMG src=\""+url3+"report.jpg\" name=report "+
		    " border=0 alt=\"Periodic Report\"></a>");
	out.println("</td></tr>");
	// 
	// logout
	out.println("<tr><td valign=bottom>");
	out.println("<a href="+url+
		    "Logout?username target=_top " );
	out.println(" onmouseover=\"movepic('logout','"+url3+
		    "logout2.jpg');\" " ); 
	out.println(" onmouseout=\"movepic('logout','"+url3+
		    "logout.jpg');\">");
	out.println("<IMG src=\""+url3+"logout.jpg\" name=logout "+
		    " border=0 alt=\"Logout\"></a>");
	out.println("</td></tr>");
	out.println("</table></td></tr></table>");
	out.println("</BODY></HTML>");
	out.close();
	//
    }				   
    /**
     * @link #doGet
     */		  
    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException{
	doGet(req, res);
    }

}

