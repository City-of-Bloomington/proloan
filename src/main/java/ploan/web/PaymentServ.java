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

@WebServlet(urlPatterns = {"/Payment"})
public class PaymentServ extends TopServlet{

    static Logger logger = LogManager.getLogger(PaymentServ.class);
    String bgcolor = LoanServ.bgcolor;
    
    //
    /**
     * Generates the Mortgage form and processes view, add, update and delete
     * operations.
     * @param req
     * @param res
     //
     //                     Mortgage Formula
     //                     ----------------
     // Payment calculation 
     //  P = A/D    
     //            where P = payment per month
     //                  A = Loan amount
     //                  D = discount factor
     //
     //  D = [(1+j)^n - 1] / [j(1+j)^n] 
     //
     //                  n = number of payments (months)
     //                  j = interest rate per pay period (month)
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
    

	String lid="", address="";      

	String lateFee ="", owner="", 
	    startDate="", deposit_acc="", cityStateZip="";
	String action="", dir_amount="", dir_rate="";
	int pay_count = 0, startMonth=0, startYear=0, yearReq=0, prevYear=0,
	    nextYear=0,periods=0,terms=0,id=0;
	double payment=0;
	String amount_paid="0",late_fee="0",receipt="", paid_date="",
	    principal_bal="", fund_type="", interest2="";

	boolean success = true, 
	    newMortgage=true;
	String message="";
	double prevBalance = 0.0;
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String name, value;
	String nhoodArr[] = null;
	String [] dueDateArr=null, paidDateArr=null,receiptArr=null,idArr=null;
	double [] amntPaidArr=null, interestArr=null,pricipalArr=null,
	    principalBalArr=null,
	    lateFeeArr=null, principalArr=null;
	
	Enumeration values = req.getParameterNames();
	String [] vals;
	Payment pp = new Payment();
	Loan loan = null;
	List<Payment> payments = null;
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("lid")){
		lid = value;
		pp.setLid(value);
	    }
	    else if (name.equals("id")){
		pp.setId(value);
		try{
		    id = Integer.parseInt(value);
		}catch(Exception ex){}
	    }
	    else if (name.equals("amount_paid")) {
		pp.setAmount_paid(value);
		amount_paid =value;
		if(amount_paid.equals(""))amount_paid="0";
	    }
	    else if (name.equals("prevBal")){
		try{
		    prevBalance = Double.parseDouble(value);
		    pp.setPrevBalance(prevBalance);		    
		}catch(Exception ex){}
	    }			
	    else if (name.equals("paid_date")) {
		pp.setPaid_date(value);
		paid_date =value;
	    }
	    else if (name.equals("receipt")) {
		pp.setReceipt(value);
		receipt =value;
	    }
	    else if (name.equals("principal_bal")) {
		pp.setPrincipal_bal(value);
		principal_bal = value;
		if(principal_bal.equals(""))principal_bal="0";
	    }
	    else if (name.equals("principal")) {
		pp.setPrincipal(value);
	    }	    
	    else if (name.equals("late_fee")) {
		pp.setLate_fee(value);
		late_fee =value;
	    }
	    else if (name.equals("interest")) {
		pp.setInterest(value);
		interest2 =value;
	    }
	    else if (name.equals("yearReq")) {
		pp.setYearReq(value);
		try{
		    yearReq = Integer.parseInt(value);
		}catch(Exception ex){};
	    }
	    else if (name.equals("action")){ 
		action = value;  
		if(action.startsWith("Add")) action="";
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

	if(action.equals("Update")){
	    message = pp.doUpdate();
	    if(message.isEmpty()){
		message = "Updated successfully";
	    }
	    message = pp.doSelect();
	}
	else if(id >= 0 && !lid.isEmpty()){
	    message = pp.doSelect();
	    if(!message.isEmpty()){
		message = "Error get record info";
	    }	    
	}

	// 
	// get the basic info from the ploan record 
	//
	double rate=0;  // dir_rate as percentage
	double principal=0;
	loan = pp.getLoan();
	if(loan != null){
	    terms = loan.getTerms();
	    periods = loan.getPeriods();
	    dir_amount = ""+loan.getDir_amount();
	    dir_rate = ""+loan.getDir_rate();
	    pay_count = terms * periods;
	    startDate = loan.getStartDate();
	    deposit_acc = loan.getDeposit_acc();
	    lateFee = loan.getLateFee();
	    if(loan.getClient() != null){
		address = loan.getClient().getAddress();
		cityStateZip = loan.getClient().getCityStateZip();
		owner = loan.getClient().getOwner();
	    }
	    cityStateZip = loan.getCityStateZip();
	    String str = loan.getPayment();
	    if(str != null && !str.isEmpty()){
		try{
		    payment = Double.parseDouble(str);
		}catch(Exception ex){}
	    }	    
	    List<Payment> all_payments = loan.getPayments();
	    if(payments == null){
		payments = new ArrayList<>();
	    }
	    for(Payment one:all_payments){
		int id2 = one.getId(); // we need only three max
		if(id2 == id-1 || id2 == id || id2 == id+1){
		    payments.add(one);
		}
	    }

	    try{
		principal = Double.valueOf(dir_amount).doubleValue();
		rate = Double.valueOf(dir_rate).doubleValue();
	    }
	    catch(Exception ex){}
	    if(rate > 0){
		rate = rate /(100.* periods );
	    }
	    if(id == 0){
		if(pp.getPrincipal_bal() == 0){
		    pp.setPrincipal_bal(""+principal);
		}
	    }
	}
    
	//
	// from the given info calculate the monthly payment
	// interest, principal, according to the formaul 
	// mentioned above set the payment dates 
	//
	out.println("<html><head><title>ProLoan</title>");
	Helper.writeWebCss(out, url);
	out.println("<script language=Javascript>                       ");
	out.println("  function validateForm(){		                       ");
	out.println("  if (document.myForm.amount_paid.value.length>0){    ");
	out.println("  if (isNaN(document.myForm.amount_paid.value)){      ");
	out.println(" alert(\"Amount Paid is not a valid number.\");       ");
	out.println("  	 document.myForm.amount_paid.focus();              ");
	out.println("     return false;			                           ");
	out.println("	}}					           ");
	out.println("  if (document.myForm.late_fee.value.length>0){       ");
	out.println("  if (isNaN(document.myForm.late_fee.value)){         ");
	out.println(" alert(\"Late fees is not a valid number.\");         ");
	out.println("  	 document.myForm.late_fee.focus();                 ");
	out.println("     return false;			                           ");
	out.println("	}}					                               ");
	out.println("  if (document.myForm.principal.value.length>0){      ");
	out.println("  if (isNaN(document.myForm.principal.value)){        ");
	out.println(" alert(\"Principal is not a valid number.\");         ");
	out.println("  	 document.myForm.principal.focus();                ");
	out.println("     return false;			                           ");
	out.println("	}}					                               ");
	out.println("  if (document.myForm.interest.value.length>0){       ");
	out.println("  if (isNaN(document.myForm.interest.value)){         ");
	out.println(" alert(\"Interest is not a valid number.\");          ");
	out.println("  	 document.myForm.interest.focus();                 ");
	out.println("     return false;			                           ");
	out.println("	}}					                               ");
	out.println("  if (document.myForm.principal_bal.value.length>0){  ");
	out.println("  if (isNaN(document.myForm.principal_bal.value)){    ");
	out.println(" alert(\"Principal balance is not a valid number.\"); ");
	out.println("  	 document.myForm.principal_bal.focus();            ");
	out.println("     return false;			                   ");
	out.println("	}}					                       ");
	//
	// checking dates
	out.println(" if(!checkDate(document.myForm.paid_date.value)) return false;	");
	out.println("     return true;				           ");
	out.println("	}	         			               ");
	out.println("  function checkDate(dt){		           ");
	out.println("  var dd = dt;		                       ");
	out.println("   if(dd.length == 0) return true;                    ");
	out.println("   var dar = dd.split(\"/\");                         "); 
	out.println(" if(dar.length < 3)                                   ");
	out.println("      return false;                                   ");
	out.println("   var m = dar[0];                                    ");
	out.println("   var d = dar[1];                                    ");
	out.println("   var y = dar[2];                                    ");
	out.println("   if(isNaN(m) || isNaN(d) || isNaN(y)){              ");
	out.println(" alert(\"Not a valid date: \"+dd);                    ");
	out.println("      return false; }                                 ");
	out.println("   if( !((m > 0 && m < 13) && (d > 0 && d <32) &&     ");
	out.println("    (y > 1970 && y < 2099))){                         "); 
	out.println(" alert(\"Not a valid date: \"+dd);                    ");
	out.println("      return false; }                                 ");
	out.println("    return true;                                      ");
	out.println("    }                                                 ");
	out.println("  function validateDelete(){	                       ");
	out.println("   var x = false;                                     ");
	out.println("   x = confirm(\"Are you sure you want to delete this record\");");
	out.println("  return x;                                           ");
	out.println("	}			                                       ");
	out.println("  function reSum(){                                   ");
	out.println("  var rate = document.myForm.rate.value;	       	   ");
	out.println("  var prevBal = document.myForm.prevBal.value;   	   ");
	out.println("  var pay = document.myForm.amount_paid.value;   	   ");
	out.println("  var interest = 0, bal=0;                            ");
	out.println("  if(rate > 0) {			       	                   ");
	out.println("     interest = rate*prevBal;		       	           ");
	out.println("     interest = Math.round(interest*100);             ");
	out.println("     interest = interest/100.;                        ");
	out.println("	}			       	                               ");
	out.println("  var princ = pay - interest;                         ");
	out.println("  bal = prevBal - princ;                              ");
	out.println("  document.myForm.interest.value=interest;            ");
	out.println("  document.myForm.principal.value=princ;              ");
	out.println("  document.myForm.principal_bal.value=bal;            ");
	out.println("	}			       	                               ");
	out.println("  function firstFocus(){                              ");
	out.println("     document.myForm.paid_date.focus();               ");
	out.println("	}			       	                               ");
	out.println(" </script>				                               ");
    	out.println(" </head><body onload=\"firstFocus()\">                ");
	
	Helper.writeTopMenu(out, url);
	out.println("<center>");
	out.println("<h2>Process Payment </h2>");
	if(!message.equals("")){
	    out.println("<h2>"+message+"</h2>");
	}
	out.println("</center>");
	out.println("<form name=myForm method=post "+
		    "onSubmit=\"return validateForm()\">");
	out.println("<input type=hidden name=lid value=\""+lid+"\">");
	out.println("<input type=hidden name=id value=\""+id+"\">");
	out.println("<input type=hidden name=rate value=\""+rate+"\">");
	//
	out.println("<table width=70%><tr><td>");
	out.println("<table border width=90%>");
	out.println("<tr><td align=center bgcolor="+bgcolor+">");
	//
	// Edit record
	//
	out.println("<table width=100%>");
	//
	// Owner 
	out.println("<tr><td>"+owner);
	out.println("</td></tr>");
	//
	// address
	out.println("<tr><td>");
	out.println(address+"</td></tr>");
	out.println("<tr><td>");
	out.println(cityStateZip+"</td></tr>");
	out.println("<tr><td>&nbsp;</td></tr>"); // Separator
	out.println("<tr><td>Monthly Payment:" +payment+"</td></tr>");
	out.println("<tr><td>Late Fees:" +lateFee+"</td></tr>");
	out.println("<tr><td>&nbsp;</td></tr>");
	out.println("</table></td><td><table>");
	//
	// Principal, rate
	out.println("<tr><td><b>Principal:</b></td><td align=right>");
	out.println(dir_amount+"&nbsp;</td></tr>");
	//
	// rate
	out.println("<tr><td><b>Interest rate:</b></td><td align=right>");
	out.println(dir_rate+"</td></tr>");
	//
	// Terms
	out.println("<tr><td><b>Terms: </b>(years)</td><td align=right>");
	out.println(terms+"</td></tr>");
	//
	// Periods
	out.println("<tr><td><b>Periods: </b>(per year)</td><td align=right>");
	out.println(periods+"</td></tr>");
	//
	// Start date
	out.println("<tr><td><b>Start Date: </b></td><td align=right>");
	out.println(startDate+"</td></tr>");
	//
	// Number of Payment
	out.println("<tr><td><b>Number of Payments:<b></td><td align=right>");
	out.println(pay_count+"</td></tr>");
	//
	// Fund type
	out.println("<tr><td><b>"+fund_type+"</b></td></tr>");
	//
	out.println("</table></td></tr>");
	//
	// Payment details
	out.println("<tr><td colspan=2><table border width=100%>");
	out.println("<tr><th valign=bottom>Due <br>Date</th><th>Paid <br>Date</th><th>Amount <br>Paid</th><th valign=bottom>Interest</th><th valign=bottom>Principal</th><th>Principal <br>Balance</th><th>Paid <br>Late&nbsp;Fee</th><th valign=bottom>Receipt</th></tr>");
	//
	// Show the current record and the prev and next ones
	//
	double prevBal = 0;
	for(Payment one:payments){
	    if(one.getId() == id - 1 ||
	       id+1 == one.getId()){
		if(id - 1 == one.getId()){ // previous to current
		    prevBal = one.getPrincipal_bal();
		}
		out.println("<tr><td align=right><a href=\"" +url+
			    "Payment?id="+one.getId()+"&lid="+lid+
			    "\">"+one.getDue_date()+
			    "</a>"+
			    "</td><td align=right>&nbsp;"+one.getPaid_date()+
			    "</td><td align=right>&nbsp;"+one.getAmount_paid()+
			    "</td><td align=right>&nbsp;"+one.getInterest()+
			    "</td><td align=right>&nbsp;"+one.getPrincipal()+
			    "</td><td align=right>&nbsp;"+one.getPrincipal_bal()+
			    "</td><td align=right>&nbsp;"+one.getLate_fee()+
			    "</td><td align=right>&nbsp;"+one.getReceipt()+
			    
			    "</tr>");
	    }
	    else if(one.getId() == id){
		if(prevBal <= 0){
		    prevBal = principal;
		}	
		double interest = Math.round(prevBal*rate*100)/100.;
		out.println("<tr><td align=right>"+pp.getDue_date()+"</td>");
		out.println("<td align=right><input name=paid_date value=\"" + 
			    pp.getPaid_date()+"\" size=10 maxlength=10></td>");
		out.println("<td align=right><input name=amount_paid value=\"" + 
			    pp.getAmount_paid()+"\" "+
			    " onChange=reSum() "+
			    " size=8 maxlength=8></td>");
		out.println("<td align=right><input name=interest value=\"" + 
			    interest+"\" size=8 maxlength=8></td>");
		out.println("<td align=right><input name=principal value=\"" + 
			    (pp.getAmount_paid()-pp.getInterest())+"\" size=8 maxlength=8></td>");
		out.println("<td align=right><input name=principal_bal value=\"" + 
			    pp.getPrincipal_bal()+"\" size=8 maxlength=8></td>");
		out.println("<td align=right><input name=late_fee value=\"" + 
			    pp.getLate_fee()+"\" size=8 maxlength=8></td>");
		out.println("<td align=right><input name=receipt value=\"" + 
			    pp.getReceipt()+"\" size=12 maxlength=20></td></tr>");
	    }
	}

	// first term of the loan

	out.println("<input type=hidden name=prevBal value=\""+
		    prevBal+"\">");
	out.println("</table></td></tr>");
	//
	// Deposit account
	out.println("<tr><td colspan=2><b>Deposit Account: </b>");
	out.println(deposit_acc+"</td></tr>"); 
	// 
	// Update button
	if(user.canEdit()){
	    out.println("<tr><td colspan=2 align=right><input type=submit "+
			"name=action value=Update></td></tr>");
	}
	out.println("</table>");
	out.println("</td></tr></table>");
	//
	// Next, Prev links
	out.println("<table width=70%><tr><td>");
	if(id-1 >= 0){
	    out.println("<a href=\""+url+"Payment?"+
			"&lid="+lid+"&id="+(id-1)+"\">");
	    out.println("<IMG src=\""+url+"images/"+"prev.JPG\" name=prev "+
			" border=0 alt=\"Previous record\"></a>");
	}
	int year = pp.getYear();
	out.println("</td><td align=center valign=top><a href=\""+url+
		    "Mortgage?lid="+lid+
		    "&yearReq="+year+"\">");
	out.println("<IMG src=\""+url+"images/"+"back.JPG\" name=back "+
		    " border=0 alt=\"Back to Mortgage Page\"></a></td>");
	out.println("<td align=right>");
	if(id+1 < pay_count){
	    out.println("<a href=\""+url+"Payment?"+
			"&lid="+lid+"&id="+(id+1)+"\">");
	    out.println("<IMG src=\""+url+"images/"+"next.JPG\" name=next "+
			" border=0 alt=\"Next record\"></a>");
	}	
	out.println("</td></tr></table>");
	out.println("</form>");
    	out.println("<br />");
	out.println("</body></html>");
	out.flush();
	out.close();
    }

}






















































