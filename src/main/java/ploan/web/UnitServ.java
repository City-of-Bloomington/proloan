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

@WebServlet(urlPatterns = {"/Unit"})
public class UnitServ extends TopServlet{

    String bgcolor = LoanServ.bgcolor;
    static Logger logger = LogManager.getLogger(UnitServ.class);
    //

    
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	doPost(req,res);
    }

    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException {
    
	String pid="",oid="", address="",
	    notes="", occupancy="", subsidy="", rent_total="",
	    rent_assist="",
	    owner_type="", act_type="",chdo="",district="";
	
	int bedrooms = 0,tenure_type = 0, ptype=0, ami=0, race=0,hh_size=0,
	    hh_type=0;

	String est_const_year="", action="", message="";

	boolean userFoundFlag = false;
	boolean success = true;

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String name, value;
	String nhoodArr[] = null;
	Enumeration values = req.getParameterNames();
	String [] vals;
	Unit unit = new Unit();
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("pid")){
		pid = value;
		unit.setPid(pid);
	    }
	    else if (name.equals("oid")){
		oid = value;
		unit.setOid(value);
	    }
	    else if (name.equals("ami")) {
		if(!value.equals("")){
		    unit.setAmi(value);
		    try{
			ami = Integer.parseInt(value);
		    }catch(Exception ex){
		    }
		}
	    }
	    else if (name.equals("hh_size")) {
		if(!value.equals("")){
		    unit.setHh_size(value);
		    try{
			hh_size = Integer.parseInt(value);
		    }catch(Exception ex){
		    }
		}
	    }
	    else if (name.equals("hh_type")) {
		if(!value.equals("")){
		    unit.setHh_type(value);		    		    
		    try{
			hh_type = Integer.parseInt(value);
		    }catch(Exception ex){
		    }
		}
	    }
	    else if (name.equals("address")) {
		address =value.toUpperCase();
		unit.setNhood(value);
	    }
	    else if (name.equals("rent_total")) {
		unit.setRent_total(value);
		rent_total =value;
	    }
	    else if (name.equals("subsidy")) {
		unit.setSubsidy(value);
		subsidy =value;
	    }
	    else if (name.equals("rent_assist")) {
		unit.setRent_assist(value);
		rent_assist =value;
	    }
	    else if (name.equals("occupancy")) {
		unit.setOccupancy(value);
		occupancy =value;
	    }
	    else if (name.equals("bedrooms")) {
		if(!value.equals("")){
		    unit.setBedrooms(value);
		    try{
			bedrooms = Integer.parseInt(value);
			// unit.setBedrooms(bedrooms);		
		    }catch(Exception ex){};
		}
	    }
	    else if (name.equals("race")) {
		if(!value.equals("")){
		    unit.setRace(value);
		    try{
			race = Integer.parseInt(value);
		    }catch(Exception ex){};
		}
	    }
	    else if (name.equals("notes")){
		unit.setNotes(value);
		notes = value;  
	    }
	    else if (name.equals("action")){ 
		action = value;  
		if(action.startsWith("Add")) action="";
	    }
	}
	//
	if(action.equals("Save")){
	    message = unit.doSave();
	    if(message.isEmpty()){
		oid = unit.getOid();
		message = "Saved successfully";
	    }
	}
    	else if(action.equals("Update")){
	    //
	    message = unit.doUpdate();
	    if(message.isEmpty()){
		message = "Updated successfully";
	    }
	}
    	else if(action.equals("Delete")){
	    //
	    message = unit.doDelete();
	    if(message.isEmpty()){
		message = "Deleted successfully";
	    }	    
	    ptype=0; occupancy="";subsidy="";
	    bedrooms = 0;tenure_type = 0; race=0; hh_size=0;
	    rent_assist="";hh_type=0;
	}
	else if(!oid.isEmpty()){	
	    //
	    //  System.err.println("zoom");
	    //
	    message = unit.doSelect();
	    if(message.isEmpty()){
		address = unit.getAddress();
	    
		bedrooms = unit.getBedrooms();
		occupancy = unit.getOccupancy();
		
		subsidy =unit.getSubsidy();
		rent_total = unit.getRent_total();
		ami = unit.getAmi();
		race = unit.getRace();

		hh_size = unit.getHh_size();
		hh_type = unit.getHh_type();
		rent_assist = unit.getRent_assist();
		notes = unit.getNotes();
	    }
	}
	//
	out.println("<html><head><title>ProLoan</title>");
	Helper.writeWebCss(out, url);
	out.println("<script language=Javascript1.2>");
	out.println("  function validateForm(){		                ");
	out.println("  	 if ((document.myForm.empid.value.length < 2)){ ");
	out.println("     alert(\"'User Name' field should be at least 2 letters.\");	    ");
	out.println("     return false;				       	");
	out.println("	}						");
	out.println("  	if ((document.myForm.problem.value.length > 500)){ "); 
	out.println("     alert(\"You have entered \" + document.myForm.problem.value.length + \" characters in Description of Problem field. Maximum characters allowed are 500\");		");
	out.println("  	document.myForm.problem.value = document.myForm.problem.value.substring(0,500);         ");
	out.println("    return false;					    ");
	out.println("	}						    ");
	out.println("  	if ((document.myForm.assigned_to.options[document.myForm.assigned_to.selectedIndex].text.length == 0)) {	    	"); 
	out.println("      alert(\"'Assigned to' field is empty\");	    ");
	out.println("      return false;				    ");
	out.println(" 	}						    ");
	out.println(" var a1 = document.myForm.assigned_to.selectedIndex;");
	out.println(" var a2 = document.myForm.assigned_to_2.selectedIndex;");
	out.println(" var a3 = document.myForm.assigned_to_3.selectedIndex;");
	out.println(" if((a2 > 0 && a1==a2) || ");
	out.println("    (a3 > 0 && a1==a3) || ");
	out.println("    (a2 > 0 && a3 > 0 && a2==a3)){ ");
	out.println("      alert(\"Same request assigned to the same person twice\");	    ");
	out.println("      return false;				    ");
	out.println(" 	}						    ");
	out.println("  	if ((document.myForm.resolved.options[document.myForm.resolved.selectedIndex].text == \"Solved\")){		"); 
	out.println("   if (!(document.myForm.usetodaysdate.checked)){	    ");
	out.println("  	   if ((document.myForm.resolved_month.options[document.myForm.resolved_month.selectedIndex].text.length == 0) || ");		
	out.println("	    (document.myForm.resolved_day.options[document.myForm.resolved_day.selectedIndex].text.length == 0) ||	  ");
	out.println("	   (document.myForm.resolved_year.options[document.myForm.resolved_year.selectedIndex].text.length == 0)) {	  ");
	out.println("	      alert(\"Resolved Date is not specified\");    ");
	out.println("         return false;			            ");
	out.println("	    }					            ");
	out.println("  	  }					            ");
	out.println("	}					            ");
	out.println("  	if ((document.myForm.resolved_notes.value.length > 500)){ "); 
	out.println("      alert(\"You have entered \" + document.myForm.resolved_notes.value.length + \" characters in 'Resolved Notes' field. Maximum characters allowed are 500\");		");
	out.println("  	document.myForm.resolved_notes.value = document.myForm.resolved_notes.value.substring(0,500); ");
	out.println("     return false;				      	 ");
	out.println("	        }					 ");
	out.println("  var j = document.myForm.assigned_to.selectedIndex; ");
	out.println("  var j2 = document.myForm.assigned_to_2.selectedIndex;");
	out.println("  var j3 = document.myForm.assigned_to_3.selectedIndex;");
	out.println("  if((j > 0 && j == j2) ||    "); 
	out.println("     (j > 0 && j == j3) ||    "); 
	out.println("     (j2 > 0 && j2 == j3)){   "); 
	out.println(" alert(\"Same request assigned to the same user "+
		    "twice\");       ");
	out.println("     return false;				      	 ");
	out.println("	        }					 ");
	//
	out.println(" if(document.myForm.resolved.selectedIndex == 1){ "); 
	out.println("  if(document.myForm.resolved_by.options[document.myForm.resolved_by.selectedIndex].length == 0){"); 
	out.println("    alert(\"Resolved by is not specified \");  ");
	out.println("    document.myForm.resolved_by.focus();       "); 
	out.println("     return false;   }}			    ");
	out.println("  if(document.myForm.resolved_by.selectedIndex > 0){"); 
	out.println("    if(document.myForm.resolved.selectedIndex < 1){ "); 
	out.println("    alert(\"Resolved status is not set to Solved \");  ");
	out.println("    document.myForm.resolved.focus();          "); 
	out.println("     return false;   }}			    ");
	out.println("     return true;				    ");
	out.println("	}	         			    ");
	//
	out.println("  function validateDelete(){	            ");
	out.println("   var x = false;                              ");
	out.println("   x = confirm(\"Are you sure you want to delete this record\");");
	out.println("  return x;                                    ");
	out.println("	}			        	    ");
	out.println("  function validateForm2(){	            ");
	out.println("  	 if ((document.myForm.empid.value.length < 2)){ ");
	out.println("     alert(\"'User Name' field should not be less than two letters \");         ");
	out.println("     return false;				       	 ");
	out.println("	}						 ");
	out.println("	return true;  }	     			          ");
	out.println("  function firstFocus(){                             ");
	out.println("   if(document.myForm.empid.type == \"text\"){       ");
	out.println("     document.myForm.empid.focus();                  ");
	out.println("	}}			       		          ");
	//
	out.println("  function fillSubList(selectCtrl, itemArray){     ");
	//out.println("      alert(\"In cat list \"+itemArray[0]);  ");
	out.println(" var i, j=0, prompt; ");
	out.println("  if( selectCtrl.options.length > 0){ ");
	out.println("  for (i = selectCtrl.options.length; i >= 0; i--){ ");
	out.println("     selectCtrl.options[i] = null;   "); 
	out.println("   }}                                ");
	out.println("   if (itemArray != null) {          ");
	out.println(" // add new items                    ");
	out.println(" for (i = 0; i < itemArray.length; i++) { ");
	out.println(" selectCtrl.options[j] = new Option(itemArray[i]);");
	out.println("    j++;      ");
	out.println("   }          ");
	out.println("   // select first item (prompt) for sub list ");
	out.println("  selectCtrl.options[0].selected = true; ");
	out.println("   }                       ");
       	out.println("  }                       ");
	out.println(" function fromListToText(){        "); 
	out.println(" if(document.myForm.userlist && document.myForm.empid){");
	out.println("    var xx = document.myForm.userlist.options[document.myForm.userlist.selectedIndex].text;  ");
	out.println(" if (xx.length > 0) {     ");
	out.println(" document.myForm.empid.value = xx;  ");
	out.println("  }}}                       ");
	out.println(" </script>				        ");
    	out.println(" </head><body onload=\"firstFocus()\" >");
	Helper.writeTopMenu(out, url);
	//
    	// delete startNew
	if(action.equals("") || action.startsWith("New") ||
	   action.equals("Delete")){
	    out.println("<center><h2>New Occupant"+
			"</h2>");
	    if(action.equals("Delete")){
		if(success)
		    out.println("<h3><font color=green>Record Deleted "+
				"Successfully</font></h3>");
		else
		    out.println("<h3><font color=red>Error with Deleting "+
				"the record</font></h3>");
	    }
	}
    	else { // zoom, update, add
	    out.println("<center><h2>Check/Update Occupant</h2>");
	    //
	    if(action.equals("Save")){
		if(success)
		    out.println("<h3><font color=green>Record saved "+
				"successfully</font></h3>");
		else{
		    out.println("<h3><font color=red> "+message+
				"</font></h3>");
		}
	    }
	    else if(action.equals("Update")){
		if(success)
		    out.println("<h3><font color=green>Record updated "+
				"Successfully </font></h3>");
		else{
		    out.println("<h3><font color=red> "+message+
				"</font></h3>");
		}
	    }
	    else if(action.equals("zoom")){
		if(!success){
		    out.println("<h3><font color=red> "+message+
				"</font></h3>");
		}
	    }
	}
    	//
	//
	out.println("<table border width=80%>");
	out.println("<tr><td align=center bgcolor="+bgcolor+">");
	//
	// Add/Edit record
	//
	out.println("<table width=100%>");
	//the real table
	out.println("<form name=myForm method=post "+
		    "onSubmit=\"return validateForm2()\">");
	out.println("<tr><td><b>Unit ID:</b>"+oid);
	out.println("&nbsp;&nbsp; <b>Property ID:</b>"+pid+"</td></tr>");
	if(!oid.equals(""))
	    out.println("<input type=hidden name=oid value=\""+oid+
			"\">");
	out.println("<input type=hidden name=pid value=\""+pid+
		    "\">");
	//
	// address
	out.println("<tr><td><b>Address:</b>");
	out.println("<input type=text name=address maxlength=30 "+
		    "size=30  value=\"" +address +
		    "\"></td></tr>");
	//
	// Bedrooms
	out.println("<tr><td><b>Bedrooms: </b>");
	out.println("<select name=bedrooms>");
	for(int i=0;i<LoanServ.BEDROOMS_ARR.length; ++i){
	    if(i == bedrooms)
		out.println("<option selected value="+i+">"+
			    LoanServ.BEDROOMS_ARR[i]);
	    else
		out.println("<option value="+i+">"+
			    LoanServ.BEDROOMS_ARR[i]);
	}
	out.println("</select>&nbsp;&nbsp;<b>Occupancy</b>");
	out.println("<select name=occupancy>");
	for(int i=0;i<LoanServ.OCCUPANCY_ARR.length; ++i){
	    if(occupancy.equals(LoanServ.OCCUPANCY_ARR[i]))
		out.println("<option selected>"+
			    LoanServ.OCCUPANCY_ARR[i]);
	    else
		out.println("<option>"+
			    LoanServ.OCCUPANCY_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
    	// Subsidy
	out.println("<tr><td><b>Subsidy: </b>");
	out.println("<input type=text name=subsidy size=8 "+
		    "value=\""+subsidy+
		    "\" maxlength=8> &nbsp;&nbsp;");
	//
	// Total rent
	out.println("<b>Total rent: </b>");
	out.println("<input type=text name=rent_total size=8 "+
		    "value=\""+rent_total+
		    "\" maxlength=8> </td></tr>"); 
	//
    	// AMI
	out.println("<tr><td><b>Area Median Income: </b><select name=ami>");
	for(int i=0;i<LoanServ.AMI_ARR.length; ++i){
	    if(ami == i)
		out.println("<option selected value="+i+">"+
			    LoanServ.AMI_ARR[i]);
	    else
		out.println("<option value="+i+">"+LoanServ.AMI_ARR[i]);
	}
	out.println("</select>&nbsp;&nbsp;");
	//
	// Race
	out.println("<b> Race:</b>");
	out.println("<select name=race size=1>");
	for(int i=0; i<LoanServ.RACE_ARR.length; ++i){
	    if(i == race)
		out.println("<option selected value=\""+i+"\">" +
			    LoanServ.RACE_ARR[i]);
	    else
		out.println("<option value=\""+i+"\">" +
			    LoanServ.RACE_ARR[i]);		
	}
	out.println("</select></td></tr>");
	//
    	// hh_size
	out.println("<tr><td><b>Household size: </b>");
	out.println("<input type=text name=hh_size size=4 "+
		    "value=\""+hh_size+
		    "\" maxlength=4>&nbsp;&nbsp;");
	//
    	out.println("<b>Household type: </b>");
	out.println("<select name=hh_type>");
	for(int i=0;i<LoanServ.HOUSEHOLD_TYPE_ARR.length; ++i){
	    if(i == hh_type)
		out.println("<option selected value="+i+">"+
			    LoanServ.HOUSEHOLD_TYPE_ARR[i]);
	    else
		out.println("<option value="+i+">"+
			    LoanServ.HOUSEHOLD_TYPE_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
    	// Rental assistance
	out.println("<tr><td><b>Rental Assistance: </b>");
	out.println("<select name=rent_assist>");
	for(int i=0;i<LoanServ.ASSIST_ARR.length; ++i){
	    if(rent_assist.equals(LoanServ.ASSIST_ARR[i]))
		out.println("<option selected>"+
			    LoanServ.ASSIST_ARR[i]);
	    else
		out.println("<option>"+
			    LoanServ.ASSIST_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
    	// Notes
	out.println("<tr><td>"+
		    "<b>Notes</b>"+
		    "<Font color=green size=1> "+
		    "Up to 250 characters</Font><br>" +
		    "<textarea rows=3 cols=50 wrap "+
		    "name=notes>"+
		    notes +
		    "</textarea>" +
		    "</td></tr>");
	//
	// submit
	if(action.equals("") || 
	   action.equals("Delete")){
	    out.println("<tr><td align=right> "+
			"<input type=submit "+
			"name=action "+
			"value=Save>&nbsp;&nbsp;&nbsp;"+
			"</td></tr>"); 
	    out.println("</form>");
	}
    	else{ // save, update
	    out.println("<tr><td valign=top align=right>"+
			"<table><tr><td valign=top><input "+
			"type=submit name=action "+
			"value=Update>&nbsp;&nbsp;</td>");
	    // New 
	    out.println("<td valign=top><input "+
			"type=submit name=action "+
			"value=\"Add New Occupant\">&nbsp;&nbsp;");
	    //
	    out.println("</form></td><td>");
	    out.println("<form name=myform onSubmit=\"return "+
			"validateDelete();\">");
	    out.println("<input type=hidden name=oid value="+oid+">");
	    out.println("<input type=hidden name=pid value="+pid+">");
	    out.println("<input type=submit name=action "+
			"value=Delete>");
	    out.println("</form>");
	    out.println("</td></tr></table></cent4er></td></tr>");
	}
    
	out.println("</table>");
	out.println("</td></tr></table>");

    	out.println("<br />");
	if(true){
	    //
	    // find units in this property
	    // ToDo need unit list
	    List<Unit> units = null;
            if(!oid.equals("")){
		UnitList ul = new UnitList();
		ul.setOid(oid);
	       String back = ul.find();
		if(back.isEmpty()){
		    List<Unit> ones = ul.getUnits();
		    if(ones != null || ones.size() > 0){
			units = ones;
		    }
		}
		if(units != null){
		    out.println("<b>Units in this property"+
				"</b><br>");
		    out.println("<table border><tr><th>Unit ID</th>"+
				"<th>Address</th><th>Occupancy</th></tr>");
		    for(Unit one:units){
			String str = one.getOid();
			String str2 = one.getPid();
			out.println("<tr><td>");
			if(str != null && !str.equals("0")){
			    out.println("<li><a href="+url+"Unit?"+
					"oid="+str+
					"&pid="+str2+
					">"+str+"</a>");
			    out.println("</td><td>");
			    str = one.getAddress();
			    if(str == null) str = "&nbsp;";
			    out.println(str);
			    out.println("</td><td>");
			    str = one.getOccupancy();
			    if(str == null) str = "&nbsp;";
			    out.println(str);
			    out.println("</td></tr>");
			}
		    }
		    out.println("</table>");
		}
		else{
		    out.println("<h3>No unit belong to this "+
				" property yet.</h3>");
		}
	    }
	}
	out.println("<br><br><a href="+url+"Property?pid="+pid+"&action=zoom"+
		    ">Back to related property</a>");
	out.println("<br><br><a href="+url+"Logout target=_top>Log Out</a>");
	out.flush(); 
	//
	out.print("</body></html>");
	out.flush();
	out.close();
    }

}






















































