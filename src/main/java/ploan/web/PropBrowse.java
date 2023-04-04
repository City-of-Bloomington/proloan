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

@WebServlet(urlPatterns = {"/PropSearch","/PropBrowse"})
public class PropBrowse extends TopServlet {

    int maxlimit = 100; // limit on records
    String bgcolor = LoanServ.bgcolor;
    Touple [] nhoodArr = null;
    static Logger logger = LogManager.getLogger(PropBrowse.class);
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

	String name, value;
	String action="", message="";
	String pid="";
	int category = 0, resolved_month=0;
	boolean success = true;
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
	UserList proj_managers = new UserList(debug);
	if(true){
	    proj_managers.isProjectManager();
	    String back = proj_managers.find();
	    if(!back.equals("")){
		message += back;
	    }
	}		
	// 
	if(nhoodArr == null){
	    nhoodArr = Property.getAllNhood();
	}
       	out.println("<html><head><title>Property Search</title>");
	Helper.writeWebCss(out, url);
	out.println("<script language=javascript>");
	out.println("                            ");

	out.println(" function moveToNext(item, nextItem, e){ ");
	out.println("  var keyCode = \" \";  ");
	out.println(" keyCode = (window.Event) ? e.which: e.keyCode;  ");
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
	out.println("  }                                        ");
	out.println("     return false;				");
	out.println("  }                                        ");
	out.println("  function validateMonth(mm){	        ");
	out.println(" var len = mm.length;                      ");
	out.println(" if(len == 1){                             ");
	out.println("     if(!validateInteger(mm)){             ");
	out.println("     alert(\"Invalid mm -1 \");	        ");
	out.println("        return false;			");
	out.println("      }                                    ");
	out.println("     if(mm == \"0\"){                      ");
	out.println("     alert(\"Invalid mm 0 \");	        ");
	out.println("       return false;		       	");
	out.println("     }                                     ");
	out.println("  }else{                                   ");
	out.println("     if(!validateInteger(mm.charAt(0)) && !validateInteger(mm.charAt(1))){     ");
	out.println("        return false;		       	");
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
	out.println("  }else{                        ");
	out.println("    if(!validateInteger(dd.charAt(0)) || !validateInteger(dd.charAt(1))){   "); 
	out.println("     return false;				");
	out.println("     }                                     ");
	out.println("    if(dd > 31 ){    ");
	out.println("     return false;				");
	out.println("    }}			");
	out.println("     return true;				");
	out.println("  }                             ");
	//
	// validate form 
	out.println("  function validateForm(){		              ");
	out.println("     return true;				       	");
	out.println("	}               ");
	out.println(" </script>				                ");
    	out.println("              </head><body>                        ");
	//
	Helper.writeTopMenu(out, url);	
	out.println("<h2><center>Property Search</center></h2>");
	if(!success){
	    out.println("<p><font color=red>"+message+"</font></p>");
	}
	out.println("<center><table align=center border>");
	out.println("<tr><td bgcolor="+bgcolor+">"); // #e0e0e0 light gray
	out.println("<table>");
	// 
	out.println("<form name=myForm method=post onSubmit=\"return validateForm()\">");
	// id
	out.println("<tr><td><b>Property ID:</b> ");
	out.println("<input type=text name=pid maxlength=8 size=8" +
		    " value=\""+pid+"\" ></td></tr>");
	out.println("<tr><td><b>Property type:</b>");
	out.println("<select name=ptype>");
	out.println("<option value=\"\">All</option>");
	for(int i=0;i<LoanServ.PROP_TYPE_ARR.length;i++){
	    out.println("<option value="+i+
			">"+LoanServ.PROP_TYPE_ARR[i]);
	}
	out.println("</select>&nbsp;</td></tr>");
	// 
	// Project Manager
	out.println("<tr><td><b>Project Managed by:</b></b>");
	out.println("<select name=proj_manager>");
	out.println("<option value=\"\"></option>");		
	if(proj_managers.size() > 0){
	    for(User one:proj_managers){
		String selected = "";
		out.println("<option "+selected+" value=\""+one.getId()+"\">"+one+"</option>");
	    }
	}
		
	out.println("</select></td></tr>");
	//
	// Address
	out.println("<tr><td>Address</td></tr>");
	//
	// Street num
	out.println("<tr><td><b>Street num:</b>");
	out.println("<input type=text name=street_num maxlength=8 "+
		    "size=8> <b>dir:</b>");
	//
	// street dir 
	out.println(" <select name=street_dir size=1>");
	out.println("<option value=\"\">All</option>");
	for(int i=0; i<LoanServ.DIR_ARR.length; ++i){
	    out.println("<option>" +
			LoanServ.DIR_ARR[i]);		
	}
	out.println("</select> <b> name:</b>");
	out.println("<input type=text name=street_name maxlength=8 "+
		    "size=8> <b>type:</b>");
	//
	// street type 
	out.println(" <select name=street_type>");
	out.println("<option value=\"\">All</option>");
	for(int i=0; i<LoanServ.STREET_KEY_ARR.length; ++i){
	    out.println("<option value=\"" + LoanServ.STREET_KEY_ARR[i]+
			"\">" + LoanServ.STREET_TYPE_ARR[i]);		
	}
	out.println("</select><b></td></tr>");
	// 
	out.println("<tr><td><b>Post dir:</b>");
	//
	// Post dir 
	//
	out.println(" <select name=post_dir>");
	out.println("<option value=\"\">All</option>");
	for(int i=0; i<LoanServ.DIR_ARR.length; ++i){
	    out.println("<option>" +
			LoanServ.DIR_ARR[i]);		
	}
	out.println("</select>");
	//
	// sud type
	//
	out.println(" <b>SUD type:</b>");
	out.println(" <select name=sud_type>");
	out.println("<option value=\"\">All</option>");
	for(int i=0; i<LoanServ.SUD_KEY_ARR.length; ++i){
	    out.println("<option value=\"" + LoanServ.SUD_KEY_ARR[i]+
			"\">" + LoanServ.SUD_TYPE_ARR[i]);		
	}
	// sud num
	out.println("</select> <b>SUD num:</b>");
	out.println("<input type=text name=sud_num maxlength=8 "+
		    "size=8  "+
		    "></td></tr>");
	//
	// contractor
	out.println("<tr><td><b>Contractor: </b>");
	out.println("<input type=text name=contract size=40 "+
		    " maxlength=40></td></tr>");
	//
	// contractor address
	out.println("<tr><td><b>Contractor Address: </b>");
	out.println("<input type=text name=cont_addr size=40 "+
		    " maxlength=50></td></tr>");
	//
	//
	// Activity type
	out.println("<tr><td><b>Activity type:</b>");
	out.println("<select name=act_type>");
	for(int i=0;i<LoanServ.ACTIVITY_TYPE_ARR.length; ++i){
	    out.println("<option>"+
			LoanServ.ACTIVITY_TYPE_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
	//
	// Insurance expires
	out.println("<tr><td><table><tr><td>&nbsp;</td>");
	out.println("<td>From (mm/dd/yyyy)</td>");
	out.println("<td>To (mm/dd/yyyy)</td></tr>");
	out.println("<tr><td><b>Insurance Expire Date </b></td>");			
	out.println("<td><input type=text name=exp_from value=\""+
		    "\" size=10 maxlength=10 /></td>");
	out.println("<td><input type=text name=exp_to value=\""+
		    "\" size=10 maxlength=10 /></td></tr>");
	out.println("<tr><td><b>Air Leakage Test Date </b></td>");			
	out.println("<td><input type=text name=leak_from value=\""+
		    "\" size=10 maxlength=10 /></td>");
	out.println("<td><input type=text name=leak_to value=\""+
		    "\" size=10 maxlength=10 /></td></tr>");				
	out.println("</table>");
	out.println("</td></tr>");
	//
	out.println("<tr><td>");
	out.println("<b>Sort by: </b>");
	out.println("<select name=sortby><option value=DESC "+
		    "selected>New first");
	out.println("<option value=Address>Address");
	out.println("<option value=ASC>Old first");

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
	out.println("<br />");
    
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
	String titles[] ={
	    "ID",
	    "Address",
	    "Contractor",
	    "Contractor address",
	    "Insurance Expires ", 
	    "Project Manager"
	};  
	//
	// fields to be shown

	PrintWriter out = res.getWriter();			  
	String name, value;
	String action="", message="";
	Enumeration values = req.getParameterNames();
	String [] vals;
	PropertyList pl = new PropertyList();
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = Helper.escapeIt(vals[vals.length-1].trim());
	    if (name.equals("exp_from")) {
		pl.setExp_from(value);
	    }
	    else if (name.equals("exp_to")) {
		pl.setExp_to(value);
	    }
	    else if (name.equals("leak_to")) {
		pl.setLeak_to(value);
	    }
	    else if (name.equals("leak_from")) {
		pl.setLeak_from(value);
	    }
	    else if (name.equals("pid")) {
		pl.setPid ( value);
	    }
	    else if (name.equals("sortby")) {
		pl.setSortby ( value);
	    }
	    else if (name.equals("ptype")) {
		pl.setPtype ( value);
	    }
	    else if (name.equals("act_type")) {
		pl.setAct_type ( value);
	    }
	    else if (name.equals("street_num")) {
		pl.setStreet_num (value);
	    }
	    else if (name.equals("street_dir")) {
		pl.setStreet_dir (value);
	    }
	    else if (name.equals("street_name")) {
		pl.setStreet_name (value.toUpperCase());
	    }
	    else if (name.equals("street_type")) {
		pl.setStreet_type (value);
	    }
	    else if (name.equals("post_dir")) {
		pl.setPost_dir (value);
	    }
	    else if (name.equals("sud_num")) {
		pl.setSud_num (value);
	    }
	    else if (name.equals("sud_type")) {
		pl.setSud_type (value);
	    }
	    else if (name.equals("contract")){ 
		pl.setContract ( value.toUpperCase());  
	    }
	    else if (name.equals("proj_manager")){ 
		pl.setProj_manager ( value);  
	    }
	    else if (name.equals("cont_addr")){ 
		pl.setCont_addr ( value.toUpperCase());  
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
	List<Property> properties = null;
	    
	message = pl.find();
	if(message.isEmpty()){
	    properties = pl.getProperties();
	}
	//
	out.println("<html><head><title>Property Search</title>");
	Helper.writeWebCss(out, url);
	out.println("</head><body>");
	Helper.writeTopMenu(out, url);
	//
	if(!message.isEmpty()){
	    out.println("<p><font color=red>"+message+"</font></p>");
	}
	if(properties == null || properties.size() == 0){
	    out.println("<center><p>No match found</p></center>");
	}	
	// 
	// we need this if the user wants to sort the output
	//
	out.println(" <br />");
	if(properties != null && properties.size() > 0){
	    out.println("<h4>Total " + properties.size() + 
			" records.</h4>");
	    out.println("<table border>");
	    int row = 0;
	    for(Property one:properties){
		if(row%20 == 0){
		    out.println("<tr>");
		    for (int c = 0; c < titles.length; c++){
			out.println("<th><font size=-1>"+titles[c]+
				    "</font></th>");
			
		    }
		    out.println("</tr>");			
		}
		//
		if(row%2 == 0)
		    out.println("<tr bgcolor=#CDC9A3>");
		else
		    out.println("<tr>");
		row++;
		out.println("<td><a href=" +url+
			    "Property?pid=" + one.getPid() + 
			    ">" + one.getPid() + "</a></td>");
		out.println("<td>"+one.getAddress()+"</td>");
		out.println("<td>"+one.getContract()+"</td>");
		out.println("<td>"+one.getCont_addr()+"</td>");
		out.println("<td>"+one.getInsur_expire()+"</td>");
		out.println("<td>"+one.getProj_manager_name()+"</td>");
		out.println("</tr>");
	    }
	    out.println("</table>");
	}
	out.println("</body>");
	out.println("</html>");
    }

}






















































