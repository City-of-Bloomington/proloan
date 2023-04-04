package ploan.web;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
import ploan.list.*;
import ploan.utils.*;

@WebServlet(urlPatterns = {"/LeakTestServ"})
public class LeakTestServ extends TopServlet{

    static Logger logger = LogManager.getLogger(LeakTestServ.class);
    String bgcolor="silver";

    static final String[] TEST_EVENT_OPTS ={"Before","After"};
    static final String[] FLOW_RING_OPTS ={"A","B","C"};
	
    /**
     * Generates the Client form and processes view, add, update and delete
     * operations.
     * @param req
     * @param res
     */
    
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
    
	String id="", pid="";

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String name, value;
	String action="", message="";
	boolean success = true;

	Enumeration values = req.getParameterNames();
	String [] vals;
	LeakTest lt = new LeakTest(debug);
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("blowRead")) {
		if(!value.equals("")){
		    try{
			Integer.parseInt(value);
			lt.setBlowRead(value);
		    }catch(Exception ex){
			System.err.println(ex);
		    }
		}
	    }
	    else if (name.equals("flowRing")) {
		lt.setFlowRing(value);
	    }
	    else if (name.equals("id")) {
		lt.setId(value);
		id = value;
	    }
	    else if (name.equals("pid")) {
		lt.setPid(value);
		pid = value;
	    }
	    else if (name.equals("testDate")) {
		lt.setDate(value);
	    }
	    else if (name.equals("flowRing")) {
		lt.setFlowRing(value);
	    }
	    else if (name.equals("testDate")) {
		lt.setDate(value);
	    }
	    else if (name.equals("testEvent")) {
		lt.setTestEvent(value);
	    }	
	    else if (name.equals("notes")) {
		lt.setNotes(value);
	    }
	    else if (name.equals("action")){ 
		action = value;  
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
	if(action.equals("Save")){
	    //
	    if(lt.isValid()){
		String back = lt.doSave();
		if(back.equals("")){
		    message = "Saved Successfully";
		    id = lt.getId();					
		}
		else{
		    message += back;
		    success = false;
		}
	    }
	    else{
		message = " All fields are required ";
		success = false;
	    }
	}
	else if(action.equals("Update")){
	    //
	    String back = lt.doUpdate();
	    if(back.equals("")){
		message = " Updated Successfully ";
	    }
	    else{
		message += back;
		success = false;
	    }
	}
	else if(action.equals("Delete")){
	    String back = lt.doDelete();
	    if(back.equals("")){
		message = " Deleted Successfully ";
		id = "";
	    }
	    else{
		message += back;
		success = false;
	    }
	}
	else if(!id.isEmpty()){	
	    String back = lt.doSelect();
	    if(back.equals("")){
		id = lt.getId();
		pid = lt.getPid();
	    }
	    else{
		message += back;
		success = false;
	    }
	}
	//
	// System.err.println(allITSList);
	//
	out.println("<html><head><title>Air Leak Test</title>");

	Helper.writeWebCss(out, url);
	out.println("<script language=javascript>");	
	out.println("  function validateForm(){		                ");
	out.println("  	 if ((document.myForm.l_name.value.length==0) && ");
	out.println("  	     (document.myForm.company.value.length==0)){ ");
	out.println("  alert(\"Company or 'Last Name' field is required.\");");
	out.println("  	   document.myForm.l_name.focus();             ");
	out.println("     return false;				       ");
	out.println("	}					       ");
	out.println("  	 if (document.myForm.house_ratio.value.length >0){ ");
	out.println("  	 if (isNaN(document.myForm.house_ratio.value)){ ");
	out.println("  alert(\"House ratio not a valid number.\");");
	out.println("  	   document.myForm.house_ratio.focus();             ");
	out.println("     return false;	}}			       ");
	out.println("     return true;				       ");
	out.println("	}	         			       ");
	//
	out.println("  function validateDelete(){	               ");
	out.println("   var x = false;                                 ");
	out.println("   x = confirm(\"Are you sure you want to delete this record\");");
	out.println("  return x;                                       ");
	out.println("	}			        	       ");
	out.println("  function firstFocus(){                          ");
	out.println("     document.myForm.f_name.focus();              ");
	out.println("	}			       		       ");
	out.println(" </script>				               ");
    	out.println(" </head><body onload=\"firstFocus()\" >           ");
	Helper.writeTopMenu(out, url);
	//
    	// Delete startNew
	if(id.equals("")){
	    out.println("<center><h2>Air Leakage Test </h2>");
	}
	else { // zoom, update, add
	    out.println("<center><h2>Edit Air Leakage Test</h2>");
	    //
	}
	if(!message.equals("")){
	    if(success)
		out.println("<h3>"+message+"</h3>");
	    else
		out.println("<p><font color=red>"+message+"</font></p>");
	}
	//
	out.println("<table border width=95%>");
	out.println("<tr><td align=center bgcolor="+bgcolor+">");
	//
	// Add/Edit record
	//
	out.println("<table>");
	out.println("<tr><td>");
	//the real table
	out.println("<form name=myForm method=post "+
		    "onsubmit=\"return validateForm()\">");
	out.println("<input type=\"hidden\" name=\"pid\" value=\"" +pid+"\" />");
	if(!id.equals("")){
	    out.println("<input type=\"hidden\" name=\"id\" value=\"" +id+"\" />");
	}		
	out.println("<tr><td align=right><b>Property:</b></td><td>");
	out.println("<a href=\""+url+"Property?pid="+pid+"&action=zoom\">"+pid+"</a>");
	out.println("</td></tr>");		

	// first last name
	out.println("<tr><td align=right><b>Test Performed:</b></td><td>");
	out.println("<select name=\"testEvent\">");
	for(String str:TEST_EVENT_OPTS){
	    String opted = "";
	    if(str.equals(lt.getTestEvent())) opted="selected=\"selected\"";
	    out.println("<option value=\""+str+"\" "+opted+">"+str+"</option>");
	}
	out.println("</select> Rehab</td></tr>");
			
	out.println("<tr><td align=right><b>Blower Door Reading:</b></td><td>");
	out.println("<input type=text name=blowRead maxlength=4 size=4" +
		    " value=\""+lt.getBlowRead()+"\">");
	out.println("cfm @ 50 pascals </td></tr>");
	out.println("<tr><td align=right><b>Flow Ring :</b></td><td>");
	out.println("<select name=\"flowRing\">");
	for(String str:FLOW_RING_OPTS){
	    String opted = "";
	    if(str.equals(lt.getFlowRing())) opted="selected=\"selected\"";
	    out.println("<option value=\""+str+"\" "+opted+">"+str+"</option>");
	}
	out.println("</select></td></tr>");
		
	// 
	out.println("<tr><td align=right><b>Test Date:</b></td><td>");
	out.println("<input name=testDate maxlength=10 size=10" +
		    " value=\""+lt.getDate()+"\">");
	out.println("(mm/dd/yyyy) </td></tr>");
	//
	out.println("<tr><td colspan=2><b>Notes:</b></td></tr>");
	out.println("<tr><td colspan=2>");
	out.println("<textarea name=\"notes\" wrap rows=5 cols=70>");
	out.println(lt.getNotes());
	out.println("</textarea></td></tr>");
		
	// Submit
	if(id.equals("")){
	    if(user.canEdit()){
		out.println("<tr><td align=right colspan=2><input type=submit "+
			    "name=action "+
			    "value=Save>&nbsp;&nbsp;&nbsp;&nbsp;</td></tr>");
	    }
	}
	else{ // save, update
	    if(user.canEdit()){	
		out.println("<tr><td valign=top align=right>"+
			    "<input type=submit name=action "+
			    "value=Update></td>");
		out.println("</form>");				
		if(user.canDelete()){
		    out.println("<td>");
		    out.println("<form name=\"myform\" onsubmit=\"return "+
				"validateDelete();\">");
		    out.println("<input type=hidden name=id value="+id+" />");
		    out.println("<input type=hidden name=pid value="+pid+" />");
		    out.println("<input type=submit name=action "+
				"value=\"Delete\" />");
		    out.println("</form>");
		    out.println("</td>");	
		}
	    }
	    out.println("</tr>");
	}
	out.println("</table>");
	out.println("</td></tr></table>");
	LeakTestList ltl = new LeakTestList(debug, pid);
	message = ltl.lookFor();
	if(message.equals("")){
	    if(ltl.size() > 0){
		out.println("<table border>"+
			    "<caption>Previous Leakage Tests</caption>"+
			    "<tr>&nbsp;<th></th><th>Date</th>"+
			    "<th>Before?After Rehab</th>"+
			    "<th>Blower Door Reading</th>"+
			    "<th>Flow Ring</th>"+
			    "<th>Notes</th></tr>");
		for(LeakTest tt:ltl){
		    out.println("<tr>"+
				"<td><a href="+url+"LeakTestServ?id="+tt.getId()+"&pid="+pid+"&action=zoom>Edit</a>"+
				"</td><td>"+tt.getDate()+
				"</td><td>"+tt.getTestEvent()+
				"</td><td>"+tt.getBlowRead()+
				"</td><td>"+tt.getFlowRing()+
				"</td><td>"+tt.getNotes()+"</td></tr>");
		}
		out.println("</table>");
	    }
	}
    	out.println("<br>");
	out.flush(); 
	//
	out.print("</body></html>");
	out.close();
    }

}






















































