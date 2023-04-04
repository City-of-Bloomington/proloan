package ploan.web;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
import ploan.list.*;
import ploan.utils.*;

@WebServlet(urlPatterns = {"/ClientSearch","/ClientBrowse"})
public class ClientBrowse extends TopServlet {

    static Logger logger = LogManager.getLogger(ClientBrowse.class);

    int maxlimit = 100; // limit on records
    String email = "", dept="", fullname="", phone="";
    String bgcolor = LoanServ.bgcolor;
    /**
     * Generates the form for the search engine.
     * @param req
     * @param res
     */
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();

	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

	String name, value, message="";
	boolean success = true;
	if(url.equals("")){
	    url  = getServletContext().getInitParameter("url");
	    String debug2 = getServletContext().getInitParameter("debug");
	    if(debug2.equals("true")) debug = true;
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
	out.println("<html><head><title>Client Search</title>");
	Helper.writeWebCss(out, url);
	out.println("<script language=javascript>");
	out.println("                            ");
	out.println(" function moveToNext(item, nextItem, e){ ");
	out.println("  var keyCode = \" \";  ");
	out.println(" keyCode = (window.Event) ? e.which: e.keyCode;  ");
	//out.println("  alert(\" keycode = \"+keyCode);  ");
	out.println("  if(keyCode > 47 && keyCode < 58){  "); // only numbers
	out.println("  if(item.value.length > 1){         ");
	out.println("  eval(nextItem.focus());      ");
	out.println("  }}}      ");
	out.println("  function validateInteger(mm){	             ");
	out.println(" if(mm == \"0\" ||  mm == \"1\" || mm == \"2\" || ");
	out.println("   mm == \"3\" ||  mm == \"4\" || mm == \"5\"  || ");
	out.println("   mm == \"6\" ||  mm == \"7\" || mm == \"8\"  || ");
	out.println("   mm == \"9\"){ ");
	out.println("     return true;				");
	out.println("  }                             ");
	out.println("     return false;				");
	out.println("  }                             ");
	out.println("  function validateMonth(mm){	             ");
	out.println(" var len = mm.length;   ");
	out.println(" if(len == 1){                        ");
	out.println("     if(!validateInteger(mm)){     ");
	out.println("     alert(\"Invalid mm -1 \");	    ");
	out.println("        return false;				");
	out.println("      }                             ");
	out.println("     if(mm == \"0\"){                  ");
	out.println("     alert(\"Invalid mm 0 \");	    ");
	out.println("       return false;				");
	out.println("     }                             ");
	out.println("  }else{                        ");
	out.println("     if(!validateInteger(mm.charAt(0)) && !validateInteger(mm.charAt(1))){     ");
	out.println("        return false;		      	");
	out.println("      }                                    ");
	out.println("    if(mm == \"00\"){                      ");
	out.println("     return false;				");
	out.println("    } else if(mm > 12){                    ");
	out.println("     return false;				");
	out.println("  }}                                       ");
	out.println("     return true;				");
	out.println("  }                                        ");
	out.println("  function validateYear(yy){	        ");
	out.println(" var len = yy.length;                      ");
	out.println(" if(len < 2){                              ");
	out.println("     return false;				");
	out.println("  }else{                                   ");
	out.println("    if(!validateInteger(yy.charAt(0)) ||   ");
	out.println("       !validateInteger(yy.charAt(1))){    ");
	out.println("     return false;				");
	out.println("  }}                                       ");
	out.println("     return true;				");
	out.println("  }                                        ");
	out.println("  function validateDay(dd){                ");
	out.println(" var len = dd.length;                      ");
	out.println(" if(len == 1){                             ");
	out.println("    if(!validateInteger(dd))               "); 
	out.println("     return false;			        ");
	out.println("  }else{                                   ");
	out.println("    if(!validateInteger(dd.charAt(0)) || !validateInteger(dd.charAt(1))){   "); 
	out.println("     return false;				");
	out.println("     }                                     ");
	out.println("    if(dd > 31 ){                          ");
	out.println("     return false;				");
	out.println("    }}			                ");
	out.println("     return true;				");
	out.println("  }                                        ");
	//
	// validate form 
	out.println("  function validateForm(){		        ");
	out.println("     return true;			      	");
	out.println("	}                                       ");
	out.println(" </script>				        ");
    	out.println("              </head><body>");
	Helper.writeTopMenu(out, url);
	//

	out.println("<h2><center>Client Search</center></h2>");
	out.println("<center><table align=center border>");
	out.println("<tr><td bgcolor="+bgcolor+">"); // #e0e0e0 light gray
	out.println("<table>");

	out.println("<form name=myForm method=POST onSubmit=\"return validateForm()\">");
	// id
	out.println("<tr><td><b>Client ID:</b>");
	out.println("<input type=text name=cid maxlength=8 size=8" +
		    " > &nbsp;&nbsp;&nbsp;");
	out.println("</td></tr>");
	//
	// First name
	out.println("<tr><td><b>First name:</b>");

	out.println("<input type=text name=f_name maxlength=30 size=20"+
		    "> &nbsp;");
	// last name
	out.println("<b>Last:</b>");

	out.println("<input type=text name=l_name maxlength=30 size=20" +
		    "></td></tr>");
	//
	// company
	out.println("<tr><td><b>Company:</b>");
	out.println("<input type=text name=company size=20 maxlength=30>");
	out.println("</td></tr>");
	//
	// phone
	out.println("<tr><td><b>Phone:</b>");
	out.println("<input type=text name=phone size=12 maxlength=12>");
	out.println("&nbsp;&nbsp;");
	//
	out.println("</td></tr>");
	//
	// Address
	out.println("<tr><td><b>Street num:</b>");
	out.println("<input type=text name=street_num maxlength=8 "+
		    "size=8 "+
		    "><b>dir:</b>");
	//
	// street dir 
	out.println(" <select name=street_dir size=1>");
	for(int i=0; i<LoanServ.DIR_ARR.length; ++i){
	    out.println("<option>" +
			LoanServ.DIR_ARR[i]);		
	}
	out.println("</select><b> name:</b>");
	out.println("<input type=text name=street_name maxlength=8 "+
		    "size=8  "+
		    "><b>type:</b>");
	//
	// street type 
	out.println(" <select name=street_type>");
	for(int i=0; i<LoanServ.STREET_KEY_ARR.length; ++i){
	    out.println("<option value=\"" + LoanServ.STREET_KEY_ARR[i]+
			"\">" + LoanServ.STREET_TYPE_ARR[i]);		
	}
	out.println("</select><b></td></tr>");
	// 
	out.println("<tr><td><b>Post dir:</b>");
	//
	// post dir 
	//
	out.println(" <select name=post_dir>");
	for(int i=0; i<LoanServ.DIR_ARR.length; ++i){
	    out.println("<option>" +
			LoanServ.DIR_ARR[i]);		
	}
	out.println("</select>");
	//
	// Sud type
	//
	out.println("<b>SUD type:</b>");
	out.println(" <select name=sud_type>");
	for(int i=0; i<LoanServ.SUD_KEY_ARR.length; ++i){
	    out.println("<option value=\"" + LoanServ.SUD_KEY_ARR[i]+
			"\">" + LoanServ.SUD_TYPE_ARR[i]);		
	}
	// sud num
	out.println("</select><b>SUD num:</b>");
	out.println("<input type=text name=sud_num maxlength=8 "+
		    "size=8  "+
		    ">");
	out.println("<b>P.O. Box:</b>");
	out.println("<input type=text name=pobox maxlength=8 "+
		    "size=8  "+
		    "></td></tr>");
	//
	//
	out.println("<tr><td>");
	out.println("<b>Sort by: </b>");
	out.println("<select name=sortby><option value=DESC "+
		    "selected>New clients first");
	out.println("<option value=ASC>Old clients first");
	out.println("</select></td></tr>");
	//
	out.println("<tr><td><hr></td></tr>");
	out.println("<tr><td align=right><input type=submit "+
		    "name=browse "+
		    "value=Submit></td></tr>");
	out.println("</table></td></tr>");
	// 
	out.println("</td></tr></table></td></tr>");
	out.println("</form></table>");

	out.println("<a href="+url+"Logout target=_top>Log Out</a>");
	out.println("<br>");
    
	out.print("</body></html>");
	out.close();
    }
    /**
     * Processes the search request and arranges the output in a table.
     * @param req
     * @param res
     */
    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException{
	res.setContentType("text/html");
	String titles[] ={"Client ID",
	    "Name",
	    "Company",
	    "Address", 

	    "Phones",
	    "Email"//
	};
	//
	PrintWriter out = res.getWriter();			  
	String [] vals;
	String name="", value="";
	ClientList cl = new ClientList();
	Enumeration values = req.getParameterNames();	
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = Helper.escapeIt(vals[vals.length-1].trim());
	    if (name.equals("cid")) {
		cl.setCid(value);
	    }
	    else if (name.equals("l_name")) {
		cl.setL_name(value);
	    }
	    else if (name.equals("f_name")) {
		cl.setF_name(value);
	    }
	    else if (name.equals("company")) {
		cl.setCompany(value);
	    }
	    else if (name.equals("phone")) {
		cl.setPhone(value);
	    }
	    else if (name.equals("street_num")) {
		cl.setStreet_num(value);
	    }
	    else if (name.equals("street_name")) {
		cl.setStreet_name(value);
	    }
	    else if (name.equals("street_type")) {
		cl.setStreet_type(value);
	    }
	    else if (name.equals("street_dir")) {
		cl.setStreet_dir(value);
	    }
	    else if (name.equals("post_dir")) {
		cl.setPost_dir(value);
	    }
	    else if (name.equals("sud_num")) {
		cl.setSud_num(value);
	    }
	    else if (name.equals("sud_type")) {
		cl.setSud_type(value);
	    }
	    else if (name.equals("pobox")) {
		cl.setPobox(value);
	    }
	    else if (name.equals("sortby")) {
		cl.setSortby(value);
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
	List<Client> clients = null;
	String message = cl.find();
	if(message.isEmpty()){
	    clients = cl.getClients();
	}
	//
	out.println("<html><head><title>ProLoan</title>");
	Helper.writeWebCss(out, url);	
	out.println("<script language=javascript>");
	out.println("</script>");
	out.println("</head><body>");
	Helper.writeTopMenu(out, url);
	if(!message.isEmpty()){
	    out.println("<p><font color=red>"+message+"</font></p>");
	}
	if(clients == null || clients.size() == 0){
	    out.println("<center><p>No match found</p></center>");
	}
	// 
	// we need this if the user wants to sort the output
	//
	out.println(" <br />");
	if(clients != null && clients.size() > 0){
	    out.println("<h4>Total " + clients.size() + " record.</h4>");
	    out.println("<table border>");
	    out.println("<tr>");
	    for (int c = 0; c < titles.length; c++){
		out.println("<th><font size=-1>"+titles[c]+
			    "</font></th>");
	    }
	    out.println("</tr>");
	    int row = 0;
	    //
	    for(Client one:clients){	    
		if(row%2 == 0)
		    out.println("<tr bgcolor=#CDC9A3>");
		else
		    out.println("<tr>");
		row++;

		out.println("<td><a href="+url+
		    "Client?cid=" + one.getCid() +">"+ 
			    one.getCid() + "</a></td>");
		out.println("<td>"+one.getNames()+"</td>");
		out.println("<td>"+one.getCompany()+"</td>");
		out.println("<td>"+one.getAddress()+"</td>");		
		out.println("<td>"+one.getPhones()+"</td>");
		out.println("<td>"+one.getEmail()+"</td>");
		out.println("</tr>");
	    }
	    out.println("</table>");
	}
	out.println("<br><center>");
	out.println("</body>");
	out.println("</html>");
    }

}






















































