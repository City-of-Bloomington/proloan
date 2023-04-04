package ploan.web;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.sql.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
import ploan.list.*;
import ploan.utils.*;

@WebServlet(urlPatterns = {"/Search","/Browse"})
public class Browse extends TopServlet {

    int maxlimit = 100; // limit on records
    String bgcolor = LoanServ.bgcolor;
    Touple nhoodArr[] = null;
    static Logger logger = LogManager.getLogger(Browse.class);
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

	String name, value, message="";
	boolean loan = true, client=false, property=false, all = false;
	String ch_loan="checked",ch_client="",ch_property="", ch_all="";
	Enumeration values = req.getParameterNames();
	boolean success = true;
		
	String [] vals;
	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	

	    if(name.equals("ch_loan")){
		ch_loan = "checked";
		loan = true;
	    }
	    else if(name.equals("ch_client")){
		ch_client = "checked";
		client = true;
	    }
	    else if(name.equals("ch_property")){
		ch_property = "checked";
		property = true;
	    }
	    else if(name.equals("ch_all")){
		ch_all = "checked";
		all = true;
	    }
	}
	// 
	//
	// get the list of clients and props
	//

	//
	out.println("<html><head><title>help Desk</title>");
	Helper.writeWebCss(out, url);
	out.println("<script language=javascript>");
	out.println("                            ");
	out.println(" function moveToNext(item, nextItem, e){ ");
	out.println("  var keyCode = \" \";  ");
	out.println(" keyCode = (window.Event) ? e.which: e.keyCode;  ");
	out.println("  if(keyCode > 47 && keyCode < 58){  "); // only numbers
	out.println("  if(item.value.length > 1){               ");
	out.println("  eval(nextItem.focus());                  ");
	out.println("  }}}                                      ");
	out.println("  function checkDate(dd){		        ");
	out.println("   if(dd.length == 0) return true;         "); 
	out.println("   var dar = dd.split(\"/\");              "); 
	out.println(" if(dar.length < 3)                        ");
	out.println("      return false;                        ");
	out.println("   var m = dar[0];                         ");
	out.println("   var d = dar[1];                         ");
	out.println("   var y = dar[2];                         ");
	out.println("   if(isNaN(m) || isNaN(d) || isNaN(y)){   ");
	out.println(" alert(\"Not a valid date: \"+dd);         ");
	out.println("      return false; }                      ");
	out.println("   if( !((m > 0 && m < 13) && (d > 0 && d <32) && ");
	out.println("    (y > 1970 && y < 2099))){              "); 
	out.println(" alert(\"Not a valid date: \"+dd);         ");
	out.println("      return false; }                      ");
	out.println("    return true;                           ");
	out.println("    }                                      ");
	//
	out.println("  function validateForm(){		        ");
	out.println("if(document.myForm.dir_amount_from.value.length>0){");
	out.println("  if(isNaN(document.myForm.dir_amount_from.value)){");
	out.println(" alert(\"Direct loan amount is not a valid number.\");     ");
	out.println("     document.myForm.dir_amount_from.focus();      ");
	out.println("     return false;			            ");
	out.println("	}}					    ");
	out.println("if(document.myForm.dir_amount_to.value.length>0){");
	out.println("  if(isNaN(document.myForm.dir_amount_to.value)){");
	out.println(" alert(\"Direct loan amount is not a valid number.\");     ");
	out.println("     document.myForm.dir_amount_to.focus();        ");
	out.println("     return false;			            ");
	out.println("	}}					    ");
	out.println("if(document.myForm.defer_amount_from.value.length>0){");
	out.println("if(isNaN(document.myForm.defer_amount_from.value)){");
	out.println("alert(\"Defered loan amount is not a valid number.\");");
	out.println("     document.myForm.defer_amount_from.focus();    ");
	out.println("     return false;			            ");
	out.println("	}}					    ");
	out.println("if(document.myForm.defer_amount_to.value.length>0){");
	out.println("  if(isNaN(document.myForm.defer_amount_to.value)){");
	out.println(" alert(\"Defered loan amount is not a valid number.\");     ");
	out.println("     document.myForm.defer_amount_to.focus();      ");
	out.println("     return false;			            ");
	out.println("	}}					    ");
	out.println("if(document.myForm.cond_amount_from.value.length>0){");
	out.println(" if(isNaN(document.myForm.cond_amount_from.value)){");
	out.println(" alert(\"Conditional loan amount is not a valid number.\");     ");
	out.println("     document.myForm.cond_amount_from.focus();   ");
	out.println("     return false;			          ");
	out.println("	}}					  ");
	out.println("if(document.myForm.cond_amount_to.value.length>0){");
	out.println("  if(isNaN(document.myForm.cond_amount_to.value)){");
	out.println(" alert(\"Conditional loan amount is not a valid number.\");     ");
	out.println("     document.myForm.cond_amount_to.focus();     ");
	out.println("     return false;			          ");
	out.println("	}}					  ");

	// checking dates
	out.println("  with(document.myForm){ ");
	out.println("if(!checkDate(contract_date_from.value))return false;");
	out.println("if(!checkDate(contract_date_to.value))return false;");
	out.println("if(!checkDate(cond_set_date_from.value))return false;");
	out.println("if(!checkDate(cond_set_date_to.value))return false;");
	out.println("if(!checkDate(startDate_from.value))return false; ");
	out.println("if(!checkDate(startDate_to.value))return false;   ");
	out.println("if(!checkDate(dir_set_date_from.value))return false;");
	out.println("if(!checkDate(dir_set_date_to.value))return false;");
	out.println("if(!checkDate(appl_date_from.value))return false; ");
	out.println("if(!checkDate(appl_date_to.value))return false;   ");
	out.println("if(!checkDate(draw_date_from.value))return false; ");
	out.println("if(!checkDate(draw_date_to.value))return false;   ");
	out.println("	}                                          ");
	//
	// end loan items
	out.println("     return true;				       	");
	out.println("	}                                               ");
	out.println(" </script>				                ");
	out.println("              </head><body>                        ");
	Helper.writeTopMenu(out, url);
	//
	out.println("<h2><center>Loan Search</center></h2>");
	if(!success){
	    out.println("<p><font color=red>"+message+"</p>");
	}
	// 
	// selection form
	//
	// the main form
	out.println("<form name=myForm method=POST "+
		    "onSubmit=\"return validateForm()\">");
	out.println("<table align=center border width=70%>");
	out.println("<tr><td bgcolor="+bgcolor+">"); 
	out.println("<table width=100%>");
	// lid
	out.println("<tr><td><b>Loan ID:</b>");
	out.println("<input type=text name=lid maxlength=8 size=8" +
		    "> &nbsp;&nbsp;&nbsp;");
	out.println("<b>File ID:</b>");
	out.println("<input type=text name=fileId maxlength=20 size=8" +
		    "> &nbsp;&nbsp;&nbsp;");
	out.println("<b>Loan type:</b>");
       	out.println(" <select name=ltype size=1>");
	//
	for(int i=0; i<LoanServ.LOAN_TYPE_ARR.length; ++i){
	    out.println("<option>" +
			LoanServ.LOAN_TYPE_ARR[i]);		
	}
	out.println("</select></td></tr>");
	//
	//
	// from to table
	out.println("<tr><td><table border>"+
		    "<tr><td></td><td>from</td><td>to</td></tr> ");
	//
	// Contract date
	out.println("<tr><td><b>Contract date: </b></td><td>");
	out.println("<input name=contract_date_from size=10 "+
		    " maxlength=10></td><td>");
	out.println("<input name=contract_date_to size=10 "+
		    " maxlength=10></td></tr>");
	//
	// Direct loan amount
	out.println("<tr><td><b>Direct loan amount:</b></td><td>");
	out.println("<input type=text name=dir_amount_from size=8 "+
		    " maxlength=8></td><td>");
	out.println("<input type=text name=dir_amount_to size=8 "+
		    " maxlength=8></td></tr>");
	//
	//
	// Deferred loan amount
	out.println("<tr><td><b>Deferred loan amount: </b></td><td>");
	out.println("<input type=text name=defer_amount_from size=8 "+
		    " maxlength=8></td><td>");
	out.println("<input type=text name=defer_amount_to size=8 "+
		    " maxlength=8></td></tr>");
	//
	// Conditional loan amount
	out.println("<tr><td><b>Conditional loan amount:</td><td>");
	out.println("<input type=text name=cond_amount_from size=8 "+
		    " maxlength=8></td><td>");
	out.println("<input type=text name=cond_amount_to size=8 "+
		    " maxlength=8></td></tr>");
	//
	//
	out.println("<tr><td><b>Grant amount:</b></td><td>");
	out.println("<input type=text name=grant_from maxlength=6 "+
		    "size=6> </td><td>");
	out.println("<input type=text name=grant_to maxlength=6 "+
		    "size=6> </td></tr>");
	//
	//
	// Conditional loan settlement date 
	out.println("<tr><td><b>Conditional loan settlement date:"+
		    " </b></td><td>");
	out.println("<input type=text name=cond_set_date_from "+
		    "maxlength=10 size=10></td><td>");
	out.println("<input type=text name=cond_set_date_to maxlength=10 "+
		    "size=10></td></tr>");
	//
	//
	// Application date 
	out.println("<tr><td><b>Application date: "+
		    " </b></td><td>");
	out.println("<input type=text name=appl_date_from "+
		    "maxlength=10 size=10></td><td>");
	out.println("<input type=text name=appl_date_to maxlength=10 "+
		    "size=10></td></tr>");
	//
	out.println("<tr><td><b>Status:</b></td><td>");
	out.println("<input type=\"radio\" name=\"inactive\"  "+
		    "value=\"\" checked=\"checked\""  +
		    " />All</td><td> ");
	out.println("<input type=\"radio\" name=\"inactive\"  "+
		    "value=\"n\" "  +
		    " />Active Only ");						
	out.println("<input type=\"radio\" name=\"inactive\"  "+
		    "value=\"y\" "  +
		    " />Inactive Only </td></tr>");		
	out.println("</td></tr></table>"); // end from-to table
	//
	out.println("</select>&nbsp;&nbsp;<b>Sort by: </b>");
	out.println("<select name=sortby><option value=DESC "+
		    "selected>New records first");
	out.println("<option value=ASC>Old records first");
	out.println("</select></td></tr>");
	out.println("<tr><td align=right><input type=submit "+
		    "name=browse "+
		    "value=Submit></td></tr>");	
	//
	out.println("</table></td></tr>");
	// 
	out.println("</td></tr></table></td></tr>");
	out.println("</form></table>");
	//
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
	    "Loan ID",
	    "Client ID",
	    "Property ID",
	    "Loan type",  
	    "Contract date",
	    
	    "Direct loan amount", 
	    "Interest rate",
	    "Deferred loan amount",
	    "Conditional loan amount", 
	    "Conditional loan terms",
	    
	    "Conditional settlement date",
	    "Direct loan settlement date",
	    "Appraised value",
	    "Sale price"
	};  
	//
	PrintWriter out = res.getWriter();			  
	String name, value, message="";
	LoanList ll = new LoanList();
	Enumeration values = req.getParameterNames();
	String [] vals;
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = Helper.escapeIt(vals[vals.length-1].trim());
	    if (name.equals("sortby")) {
		ll.setSortby(value);
	    }
	    else if (name.equals("lid")) {
		ll.setLid(value);
	    }
	    else if (name.equals("inactive")) {
		ll.setStatus(value);
	    }						
	    else if (name.equals("fileId")) {
		ll.setFileId(value);
	    }
	    else if (name.equals("cid")) {
		if(!value.equals("0"))
		    ll.setCid(value);
	    }
	    else if (name.equals("pid")) {
		if(!value.equals("0"))
		    ll.setPid(value);
	    }
	    else if (name.equals("ltype")) {
		ll.setLtype(value);  // loan type
	    }
	    else if (name.equals("contract_date_from")){
		ll.setContract_date_from(value);  
	    }
	    else if (name.equals("contract_date_to")){
		ll.setContract_date_to(value);  
	    }
	    else if (name.equals("dir_amount_from")){
		ll.setDir_amount_from(value);  
	    }
	    else if (name.equals("dir_amount_to")) {
		ll.setDir_amount_to(value);  
	    }
	    else if (name.equals("grant_from")){
		ll.setGrant_from(value);  
	    }
	    else if (name.equals("grant_to")) {
		ll.setGrant_to(value);  
	    }
	    else if (name.equals("defer_amount_from")) {
		ll.setDefer_amount_from(value);  
	    }
	    else if (name.equals("defer_amount_to")) {
		ll.setDefer_amount_to(value);  
	    }
	    else if (name.equals("cond_amount_from")) {
		ll.setCond_amount_from(value);  
	    }
	    else if (name.equals("cond_amount_to")) {
		ll.setCond_amount_to(value);  
	    }
	    else if (name.equals("cond_set_date_from")) {
		ll.setCond_set_date_from(value);  
	    }
	    else if (name.equals("cond_set_date_to")) {
		ll.setCond_set_date_to(value);  
	    }
	    else if (name.equals("appl_date_from")) {
		ll.setAppl_date_from(value);  
	    }
	    else if (name.equals("appl_date_to")) {
		ll.setAppl_date_to(value);  
	    }
	}
	List<Loan> loans = null;
	message = ll.find();
	if(message.isEmpty()){
	    loans = ll.getLoans();
	}
	//
	//

	//
	out.println("<html><head><title>ProLoan</title>");
	Helper.writeWebCss(out, url);	
	out.println("<script language=Javascript>            ");
	out.println("</script>");
	out.println("</head><body>");
	Helper.writeTopMenu(out, url);
	if(!message.isEmpty()){
	    out.println("<p><font color=red>"+message+"</font></p>");
	}
	//
	if(loans != null &&  loans.size() > 0){
	    out.println("<table border>");
	    int row = 0;
	    for(Loan one:loans){
		if(row%20 == 0){
		    out.println("<tr>");
		    for (int c = 0; c < titles.length; c++){
			out.println("<th>"+titles[c]+
				    "</th>");
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
			    "Loan?lid=" + one.getLid() + 
			    ">" + 
			    one.getLid() + "</a></td>");
		out.println("<td><a href=" +url+
			    "Client?lid=" + one.getCid() + 
			    ">" + 
			    one.getLid() + "</a></td>");		
		out.println("<td><a href=" +url+
			    "Property?pid=" + one.getPid() + 
			    ">" + 
			    one.getLid() + "</a></td>");
		out.println("<td>"+one.getPtype()+"</td>");
		out.println("<td>"+one.getContract_date()+"</td>");
		out.println("<td>"+one.getDir_amount()+"</td>");
		out.println("<td>"+one.getDir_rate()+"</td>");
		out.println("<td>"+one.getDefer_amount()+"</td>");
		out.println("<td>"+one.getCond_amount()+"</td>");
		out.println("<td>"+one.getCond_term()+"</td>");
		out.println("<td>"+one.getCond_set_date()+"</td>");
		out.println("<td>"+one.getDir_set_date()+"</td>");
		out.println("<td>"+one.getAppraised_val()+"</td>");
		out.println("<td>"+one.getSale_price()+"</td>");
		out.println("</tr>");
	    }
	}
	out.println("<br />");
	out.println("</body>");
	out.println("</html>");
    }

}






















































