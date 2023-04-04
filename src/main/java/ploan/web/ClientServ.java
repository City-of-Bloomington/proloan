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

@WebServlet(urlPatterns = {"/Client"})
public class ClientServ extends TopServlet{

    static Logger logger = LogManager.getLogger(Client.class);
    String bgcolor="silver";
    // when Loan is done change the following to link to the 
    // ones in Loan
    static final String[] YES_NO_ARR ={"","Y","N"};
    static final String[] ETHENIC_ARR ={
	"",
	"Hispanic",
	"Non Hispanic"};
    String[] RACE_ARR = LoanServ.RACE_ARR;

    final static String [] SUD_TYPE_ARR = {
	" ",
	"Apartment",
	"Building",
	"Dept",
	"Floor",
	"Lot",
	"Room",
	"Suite",
	"Unit"};
    final static String [] SUD_KEY_ARR = {
	"",
	"A",
	"B",
	"D",
	"F",
	"L",
	"R",
	"S",
	"U"};
    final static String STREET_KEY_ARR[] = {"\"\"",
	"AVE", "BND", "BLVD", "CIR", "CT", 
	"CTR", "DR",  "EXPY", "LN", "PIKE",
	"PKY", "PL",  "RD",    "ST", "TER", 
	"TPKE", "TURN"};
    final static String STREET_TYPE_ARR[] = {"",
	"Avenue","Bend", "Boulevard", "Circle", "Court",
	"Center", "Drive", "Expressway", "Lane", "Pike" ,
	"Parkway" ,"Place" ,"Road" , "Street", "Terrace",
	"Turnpike","Turn"}; 
    final static String DIR_ARR[] = {
	"",
	"N",
	"S",
	"E",
	"W"};
    String AMI_ARR[] = LoanServ.AMI_ARR;

    //
    // old database connect string (Oracle 7.1)
    //

    public static String[] allmonths = {"\n","JAN","FEB","MAR",
	"APR","MAY","JUN",
	"JUL","AUG","SEP",
	"OCT","NOV","DEC"};

    static final String MONTH_SELECT = 
	"<option value=0>\n" + 
	"<option value=1>JAN\n" + 
	"<option value=2>FEB\n" + 
	"<option value=3>MAR\n" + 
	"<option value=4>APR\n" + 
	"<option value=5>MAY\n" + 
	"<option value=6>JUN\n" + 
	"<option value=7>JUL\n" + 
	"<option value=8>AUG\n" + 
	"<option value=9>SEP\n" + 
	"<option value=10>OCT\n" + 
	"<option value=11>NOV\n" + 
	"<option value=12>DEC\n" + 
	"</select>";

    static final String PAY_STR = "<option>\n"+
	"<option>Unpaid"+
	"<option>Billed"+
	"<option>Paid"+
	"</select>";
   
    
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
    
	String email = "", cid="", l_name="",f_name="", f_name2="",
	    l_name2="",company="", w_phone="",h_phone="",notes="", lid="",
	    street_num="",street_dir="",street_name="",street_type="",
	    post_dir="",sud_type="",sud_num="",city="",state="",zip="",
	    hh_size="",female_hhh="",entry_date="", hh_type="", pid="",
	    pobox = "", ben_type="", house_ratio="";

	int race=0, ami=0, ethnicity=0;
	boolean retval = false;  
	boolean userFoundFlag = false;
	boolean wizard=false;;

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String name, value;
	String action="", message="";
	boolean success = true;

	Enumeration values = req.getParameterNames();
	String [] vals;
	Client client = new Client();
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	


	    if (name.equals("race")) {
		if(!value.equals("")){
		    client.setRace(value);
		    try{
			race = Integer.parseInt(value);
		    }catch(Exception ex){
			System.err.println(ex);
		    }
		}
	    }
	    else if (name.equals("ethnicity")) {
		if(!value.equals("")){
		    client.setEthnicity(value);
		    try{
			ethnicity = Integer.parseInt(value);
		    }catch(Exception ex){
			System.err.println(ex);
		    }
		}
	    }
	    else if (name.equals("ami")) {
		client.setAmi(value);
		if(!value.equals("")){
		    try{
			ami = Integer.parseInt(value);
		    }catch(Exception ex){
			System.err.println(ex);
		    }
		}
	    }
	    else if (name.equals("email")) {
		client.setEmail(value);
		email = value;
	    }
	    else if (name.equals("cid")) {
		client.setCid(value);
		cid = value;
	    }
	    else if (name.equals("pid")) {
		client.setPid(value);
		pid = value;
	    }
	    else if (name.equals("pobox")) {
		client.setPobox(value);
		pobox = value;
	    }
	    else if (name.equals("house_ratio")) {
		client.setHouse_ratio(value);
		house_ratio = value;
	    }
	    else if (name.equals("lid")) {
		client.setLid(value);
		lid = value;
	    }
	    else if (name.equals("wizard")) {
		if(!value.equals(""))
		    wizard = true;
	    }
	    else if (name.equals("hh_type")) {
		client.setHh_type(value);
		hh_type = value;
	    }
	    else if (name.equals("ben_type")) { // beneficiery
		client.setBen_type(value);
		ben_type = value;
	    }
	    else if (name.equals("company")) {
		client.setCompany(value);
		company = value.toUpperCase();
	    }
	    else if (name.equals("l_name")) {
		client.setL_name(value);
		l_name =value.toUpperCase();
	    }
	    else if (name.equals("f_name")) {
		client.setF_name(value);
		f_name =value.toUpperCase();
	    }
	    else if (name.equals("l_name2")) {
		client.setL_name2(value);		
		l_name2 = value.toUpperCase();
	    }
	    else if (name.equals("f_name2")) {
		client.setF_name2(value);
		f_name2 = value.toUpperCase();
	    }
	    else if (name.equals("w_phone")) {
		client.setW_phone(value);
		w_phone = value;
	    }
	    else if (name.equals("h_phone")) {
		client.setH_phone(value);
		h_phone = value;
	    }
	    else if (name.equals("notes")) {
		client.setNotes(value);
		notes = value;
	    }
	    else if (name.equals("street_num")) {
		client.setStreet_num(value);
		street_num = value;
	    }
	    else if (name.equals("street_type")) {
		client.setStreet_type(value);
		street_type = value;
	    }
	    else if (name.equals("street_name")) {
		client.setStreet_name(value);
		street_name = value.toUpperCase();
	    }
	    else if (name.equals("street_dir")) {
		client.setStreet_dir(value);
		street_dir = value;
	    }
	    else if (name.equals("post_dir")) {
		client.setPost_dir(value);
		post_dir = value;
	    }
	    else if (name.equals("sud_type")) {
		client.setSud_type(value);
		sud_type = value;
	    }
	    else if (name.equals("sud_num")) {
		client.setSud_num(value);
		sud_num = value;
	    }
	    else if (name.equals("city")) {
		client.setCity(value);
		city = value.toUpperCase();
	    }
	    else if (name.equals("state")) {
		client.setState(value);
		state = value.toUpperCase();
	    }
	    else if (name.equals("zip")) {
		client.setZip(value);
		zip = value;
	    }
	    else if (name.equals("hh_size")) {
		client.setHh_size(value);
		hh_size = value;
	    }
	    else if (name.equals("female_hhh")) {
		client.setFemale_hhh(value);
		female_hhh = value;
	    }
	    else if (name.equals("entry_date")) {
		client.setEntry_date(value);
		entry_date = value;
	    }
	    else if (name.equals("action")){ 
		// Get, Save, zoom, edit, delete, New, Refresh
		action = value;  
	    }
	    else if (name.equals("action2")){ 
		if(!value.equals(""))
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
	    if(user.canEdit()){
		String back = client.doSave();
		if(!back.isEmpty()){
		    message += " Error saving data "+back;
		}
		else{
		    cid = client.getCid();
		    message = "Saved successfully";
		}
	    }
	}
	else if(action.equals("Update")){
	    if(user.canEdit()){
		String back = client.doUpdate();
		if(!back.isEmpty()){
		    message += " Error saving data "+back;
		}
		else{
		    message = "Updated successfully";
		}
	    }
	}
	else if(!cid.isEmpty()){
	    String back = client.doSelect();
	    if(back.isEmpty()){
		entry_date = client.getEntry_date();
		email  = client.getEmail();
		l_name = client.getL_name();
		f_name = client.getF_name();
		l_name2 = client.getL_name2();
		f_name2 = client.getF_name2();
		company = client.getCompany();
		w_phone = client.getW_phone();
		h_phone = client.getH_phone();
		notes = client.getNotes();
		lid = client.getLid();
		street_num = client.getStreet_num();
		street_dir = client.getStreet_dir();
		street_name = client.getStreet_name();
		street_type = client.getStreet_type();
		post_dir = client.getPost_dir();
		sud_type = client.getSud_type();
		sud_num = client.getSud_num();
		city = client.getCity();
		state = client.getState();
		zip = client.getZip();
		hh_size = client.getHh_size();
		female_hhh = client.getFemaile_hhh();
		entry_date = client.getEntry_date();
		hh_type = client.getHh_type();
		pid = client.getPid();
		pobox = client.getPobox();
		ben_type = client.getBen_type();
		house_ratio = client.getHouse_ratio();
		race = client.getRace();
		ami = client.getAmi();
		ethnicity = client.getEthnicity();
	    }
	    else{
		message += " Error retreiving data "+back;
	    }
	}
	//
	//
	out.println("<html><head><title>ProLoan</title>");
	Helper.writeWebCss(out, url);
	out.println("<script language=Javascript>");
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
	out.println("  function validateDelete(){	            ");
	out.println("   var x = false;                              ");
	out.println("   x = confirm(\"Are you sure you want to delete this record\");");
	out.println(" if(x){                                        ");
	out.println("    document.myForm.action2.value='Delete';   ");
	out.println("    document.myForm.submit();   ");
	out.println("	}			        	    ");				
	out.println("  return x;                                    ");
	out.println("	}			        	    ");
	out.println("  function firstFocus(){                          ");
	out.println("     document.myForm.f_name.focus();              ");
	out.println("	}			       		       ");
	out.println(" </script>				               ");
	out.println(" </head><body onload=\"firstFocus()\" >           ");
	out.println("<center>");
	Helper.writeTopMenu(out, url);	
	out.println("<form name=myForm method=post "+
		    "onSubmit=\"return validateForm()\">");
	if(!cid.equals("")){
	    out.println("<input type=\"hidden\" name=\"cid\" value=\"" +cid+"\" />");
	    out.println("<input type=\"hidden\" name=\"entry_date\" value=\"" +entry_date+"\" />");						
						
	    out.println("<input type=\"hidden\" name=\"action2\" value=\"\" />");
	}
				
	if(wizard){
	    out.println("<input type=hidden name=wizard value=y>");
	    if(!pid.equals("")){
		out.println("<input type=hidden name=pid value="+pid+">");
	    }
	    if(!lid.equals("")){
		out.println("<input type=hidden name=lid value="+lid+">");
	    }
	}
	// Delete startNew
	if(action.equals("Get")){
	    out.println("<h2>Continue with New Client"+
			"</h2>");
	}
	else if(cid.equals("")){
	    out.println("<h2>New Client </h2>");
	}
	else { // zoom = client.get update, add
	    out.println("<h2>Edit Client</h2>");
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
	out.println("<tr><td align=\"center\" bgcolor=\""+bgcolor+"\">");
	//
	// Add/Edit record
	//
	out.println("<table width=\"100%\">");
	out.println("<tr><td>");
	//the real table
	if(!cid.equals("")){
	    // Client id
	    out.println("<tr><td><b>Client ID:</b>");
	    out.println(cid);
	    out.println("</td></tr>");						
	}
	// first last name
	out.println("<tr><td><b>Client first name:</b>");
	out.println("<input type=text name=f_name maxlength=30 size=20" +
		    " value=\""+f_name+"\">");
	out.println(" <b> last name:</b>");
	out.println("<input type=text name=l_name maxlength=30 size=20" +
		    " value=\""+l_name+"\"></td></tr>");
	// Spouse
	out.println("<tr><td><b>Spouse first name:</b>");
	out.println("<input type=text name=f_name2 maxlength=30 size=20" +
		    " value=\""+f_name2+"\">");
	out.println(" <b> last name:</b>");
	out.println("<input type=text name=l_name2 maxlength=30 size=20" +
		    " value=\""+l_name2+"\"></td></tr>");
	// Company
	out.println("<tr><td><b>Company:</b>");
	out.println("<input type=text name=company maxlength=40 size=40" +
		    " value=\""+company+"\"></td></tr>");
	// phone
	out.println("<tr><td><b>Work phone:</b>");
	out.println("<input type=text name=w_phone maxlength=15 "+
		    "size=15  value=\"" + w_phone +
		    "\">&nbsp;<b>Home/Cell phone:</b>");
	out.println("<input type=text name=h_phone maxlength=15 "+
		    "size=15  value=\"" + h_phone +"\"></td></tr>");
	//
	// email
	out.println("<tr><td><b>Email:</b>");
	out.println("<input type=text name=email maxlength=40 "+
		    "size=40  value=\"" + email +
		    "\"></td></tr>");
	// 
	out.println("<tr><td>Address</td></tr>");
	//
	// Street num
	out.println("<tr><td><b>Street num:</b>");
	out.println("<input type=text name=street_num maxlength=8 "+
		    "size=8  value=\"" +street_num +
		    "\"><b>dir:</b>");
	//
	// street dir 
	out.println(" <select name=street_dir size=1>");
	for(int i=0; i<DIR_ARR.length; ++i){
	    if(street_dir.equals( DIR_ARR[i]))
		out.println("<option selected>" +
			    DIR_ARR[i]);
	    else
		out.println("<option>" +
			    DIR_ARR[i]);		
	}
	out.println("</select><b>Street name:</b>");
	out.println("<input type=text name=street_name maxlength=30 "+
		    "size=10  value=\"" +street_name +"\"><b>type:</b>");
	//
	// street type 
	out.println(" <select name=street_type>");
	for(int i=0; i<STREET_KEY_ARR.length; ++i){
	    if(street_type.equals(STREET_KEY_ARR[i]))
		out.println("<option selected value=\"" +
			    street_type + "\">"+STREET_TYPE_ARR[i]);
	    else
		out.println("<option value=\"" + STREET_KEY_ARR[i]+
			    "\">" + STREET_TYPE_ARR[i]);		
	}
	out.println("</select><b></td></tr>");
	out.println("<tr><td><b>Post dir:</b>");
	//
	// post dir 
	//
	out.println(" <select name=post_dir>");
	for(int i=0; i<DIR_ARR.length; ++i){
	    if(post_dir.equals(DIR_ARR[i]))
		out.println("<option selected>" +
			    DIR_ARR[i]);
	    else
		out.println("<option>" +
			    DIR_ARR[i]);		
	}
	out.println("</select>");
	//
	// Sud type
	//
	out.println("<b>SUD type:</b>");
	out.println(" <select name=sud_type>");
	for(int i=0; i<SUD_KEY_ARR.length; ++i){
	    if(sud_type.equals(SUD_KEY_ARR[i]))
		out.println("<option selected value=\"" +
			    sud_type + "\">"+SUD_TYPE_ARR[i]);
	    else
		out.println("<option value=\"" + SUD_KEY_ARR[i]+
			    "\">" + SUD_TYPE_ARR[i]);		
	}
	// sud num
	out.println("</select><b>SUD num:</b>");
	out.println("<input type=text name=sud_num maxlength=8 "+
		    "size=8  value=\"" +sud_num +
		    "\"> ");
	// PO box
	out.println("<b>P.O. Box:</b>");
	out.println("<input type=text name=pobox maxlength=8 "+
		    "size=8  value=\"" +pobox +
		    "\"></td></tr>");
	// City
	out.println("<tr><td><b>City:</b>");
	out.println("<input type=text name=city maxlength=30 "+
		    "size=30  value=\"" +city+
		    "\"><b>State:</b>");
	// state
	out.println("<input type=text name=state maxlength=2 "+
		    "size=2  value=\"" +state+
		    "\"><b>Zip:</b>");
	// zip
	out.println("<input type=text name=zip maxlength=12 "+
		    "size=12  value=\"" +zip+
		    "\"></td></tr>");
	//
	// House hold size
	out.println("<tr><td><b>House hold size:</b>");
	out.println("<input type=text name=hh_size maxlength=2 "+
		    "size=2  value=\"" +hh_size+
		    "\"><b>Ethnicity:</b>");
	//
	out.println("<select name=ethnicity size=1>");
	for(int i=0; i<ETHENIC_ARR.length; ++i){
	    if(i == ethnicity)
		out.println("<option selected value=\""+i+"\">" +
			    ETHENIC_ARR[i]);
	    else
		out.println("<option value=\""+i+"\">" +
			    ETHENIC_ARR[i]);
	}
	out.println("</select><b> Race:</b>");
	// 
	out.println("<select name=race size=1>");
	for(int i=0; i<RACE_ARR.length; ++i){
	    if(i == race)
		out.println("<option selected value=\""+i+"\">" +
			    RACE_ARR[i]);
	    else
		out.println("<option value=\""+i+"\">" +
			    RACE_ARR[i]);		
	}
	out.println("</select></td></tr>");
	//
	out.println("<tr><td><b>Beneficiary type: </b>");
	out.println("<select name=ben_type>");
	for(int i=0;i<LoanServ.BENIFICIARY_TYPE_ARR.length; ++i){
	    if(LoanServ.BENIFICIARY_TYPE_ARR[i].equals(ben_type))
		out.println("<option selected>"+
			    LoanServ.BENIFICIARY_TYPE_ARR[i]);
	    else
		out.println("<option>"+LoanServ.BENIFICIARY_TYPE_ARR[i]);
	}
	out.println("</select>&nbsp;");
	//
	// Household type
	out.println("<b>House hold type: </b>");
	out.println("<select name=hh_type>");
	for(int i=0;i<LoanServ.HOUSEHOLD_TYPE_ARR.length; ++i){
	    if(LoanServ.HOUSEHOLD_TYPE_ARR[i].equals(hh_type))
		out.println("<option selected>"+
			    LoanServ.HOUSEHOLD_TYPE_ARR[i]);
	    else
		out.println("<option>"+LoanServ.HOUSEHOLD_TYPE_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
	// Female head household
	out.println("<tr><td><b>Female head of house hold: </b>");
	out.println("<select name=female_hhh size=1>");
	for(int i=0;i<YES_NO_ARR.length; ++i){
	    if(YES_NO_ARR[i].equals(female_hhh))
		out.println("<option selected>"+
			    YES_NO_ARR[i]);
	    else
		out.println("<option>"+YES_NO_ARR[i]);
	}
	out.println("</select> <b>Area median income:</b>");
	// 
	out.println("<select name=ami>");
	for(int i=0;i<AMI_ARR.length; ++i){
	    if(ami == i)
		out.println("<option selected value="+i+">"+
			    AMI_ARR[i]);
	    else
		out.println("<option value="+i+">"+AMI_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
	out.println("<tr><td><b>Housing Ratio: </b>");
	out.println("<input name=house_ratio value=\""+house_ratio+
		    "\" size=6 maxlength=6>%</td></tr>");
	//
	// notes
	out.println("<tr><td><b>Notes</b>"+
		    "<Font color=green size=1> "+
		    "Maximum number of characters is 250</Font><br>" +
		    "<textarea rows=3 cols=50 wrap name=notes>" + notes +
		    "</textarea>" +
		    "</td></tr>");
	//
	// Submit
	if(cid.equals("")){
	    out.println("<tr><td align=right><input type=submit "+
			"name=action "+
			"value=Save></td></tr>");
	    out.println("</table></td></tr>");
	}
	else{ 
	    out.println("</table></td></tr>");
	    out.println("<tr><td align=\"center\">"+
			"<table width=\"80%\"><tr>");						
	    if(user.canEdit()){	
		out.println("<td valign=top align=\"center\"><input "+
			    "type=submit name=action "+
			    "value=Update></td>");
	    }
	    if(wizard){
		out.println("<td valign=\"top\"><input type=\"button\" onclick=\"document.location='"+url+"Property?pid="+pid+"&cid="+cid+"&lid="+lid+"&action=zoom&wizard=y';return false;\" value=\"Next >> Property Page\" /></td>");								
	    }
	    //
	    out.println("<td valign=\"top\"><input type=\"button\" onclick=\"document.location='"+url+"MediaUploadServ?obj_id="+cid+"&obj_type=Client';return false;\" value=\"Attachment\" /></td>");
	    if(user.canDelete()){
		out.println("<td valign=\"top\"><input type=\"button\" onclick=\"return validateDelete();\" value=\"Delete\" /></td>");

	    }
	    out.println("</tr></table></td></tr>");
	}
	out.println("</table>");
	out.println("</form>");								

	out.println("<br />");
	if(!cid.equals("")){
	    boolean found = false;
	    if(client.hasLoan()){
		lid = client.getLid();
		out.println(" <a href="+url+"Loan?"+
			    "&lid="+lid+
			    "&action=zoom>Related Loan "+lid+"</a>");
	    }
	    else{
		out.println("<b>No Loan is  associated with this "+
			    " client.</b><br>");
	    }
	}
	if(!cid.equals("")){
	    MediaFileList mfl = new MediaFileList(debug, cid, "Client");
	    String back = mfl.find();
	    if(back.equals("")){
		List<MediaFile> ones = mfl.getMediaFiles();
		Helper.writeMedia(ones, out, url);
	    }								
	}
	out.flush(); 
	//
	out.print("</body></html>");
	out.close();
    }
}






















































