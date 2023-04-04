package ploan.web;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
import ploan.list.*;
import ploan.utils.*;

@WebServlet(urlPatterns = {"/Mortgage"})
public class MortgageServ extends TopServlet{

    String bgcolor = LoanServ.bgcolor;
    static Logger logger = LogManager.getLogger(MortgageServ.class);
    static Locale locale = new Locale("en","US");
    static NumberFormat fn = NumberFormat.getCurrencyInstance(locale);
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
    
	String lid="", address="";	// Periods

	String lateFee ="", owner="", 
	    startDate="", deposit_acc="", cityStateZip="";
	String action="", dir_amount="", dir_rate="",fund_type="&nbsp;";
	int pay_count = 0, startMonth=0, startYear=0, yearReq=0, prevYear=0,
	    nextYear=0,periods=0,terms=0;
	double principal = 0, payment=0;

	boolean success = true, 
	    newMortgage=true;
	String message = "";

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String name, value;
	String nhoodArr[] = null;
	String [] dueDateArr, paidDateArr,receiptArr,idArr;
	double [] amntPaidArr, interestArr,pricipalArr,principalBalArr,
	    lateFeeArr, principalArr;
	Enumeration values = req.getParameterNames();
	String [] vals;
	Loan loan = null;
	List<Payment> payments = null;
	while (values.hasMoreElements()){
	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("lid")){
		lid = value;
	    }
	    else if (name.equals("yearReq")) {
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
	//
	// 
	// get the basic info from the ploan record 
	//
	if(true){
	    loan = new Loan(lid);
	    
	    String str = loan.doSelect();
	    if(str.isEmpty()){
		terms = loan.getTerms();
		periods = loan.getPeriods();
		dir_amount = ""+loan.getDir_amount();
		dir_rate = ""+loan.getDir_rate();
		pay_count = terms * periods;
		// owner = loan.getOwner();
		if(loan.getClient() != null){
		    address = loan.getClient().getAddress();
		    owner = loan.getClient().getOwner();
		}
		startYear = loan.getStartYear();
		startMonth = loan.getStartMonth();
		cityStateZip = loan.getCityStateZip();
		startDate = loan.getStartDate();
		lateFee = loan.getLateFee();
		str = loan.getPayment();
		if(str != null && !str.isEmpty()){
		    try{
			payment = Double.parseDouble(str);
		    }catch(Exception ex){}
		}
		fund_type = loan.getFund_type();
		double rate = 0;
		if(loan.hasPayments()){
		    newMortgage = false;
		    payments = loan.getPayments();
		}
		try{
		    principal = Double.valueOf(dir_amount).doubleValue();
		    rate = Double.valueOf(dir_rate).doubleValue();
		}
		catch(Exception ex){}
		if(rate > 0){
		    rate = rate /(100.* periods );
		}
	    }
	}
	if(terms > 0)
	    pay_count = terms * periods;  // terms = years
	else {
	    pay_count = periods; // for loans of less than one year
	}
	//
	// needed for next prev links
	//
	if(yearReq == 0){
	    yearReq = startYear;
	}
	if(yearReq > 0){
	    if(startYear < yearReq) prevYear = yearReq - 1;
	    if(startYear + terms > yearReq) nextYear = yearReq + 1; 
	}
	
	//
	double rate=0;  // dir_rate as percentage
	try{
	    principal = Double.valueOf(dir_amount).doubleValue();
	    rate = Double.valueOf(dir_rate).doubleValue();
	    if(rate > 0){
		rate = rate /(100.* periods ); 
	    }
	}
	catch(Exception ex){}
	// 
	// from the given info calculate the monthly payment
	// interest, principal, according to the formaul 
	// mentioned above set the payment dates 
	//
	//
	// If the mortgage details are not set, now is the time
	// to do so
	//
	if(newMortgage){
	    //
	    // compute the monthly payment from the given info
	    // if interest rate is given than we use it
	    // otherwise we consider interest free loan
	    if(rate > 0){
		double D = 0;
		D = Math.pow((1+rate),(double) pay_count);
		D = (D-1)/(rate*D);
		payment = ((int)(100*principal / D+0.5))/100.;
	    }
	    else{
		//
		// interest free loan
		//
		payment = ((int)(100*(principal /pay_count+0.005)))/100.;
	    }
	    //
	    int currentMonth = startMonth, currentYear = startYear;
	    if(terms > 1){
		nextYear = startYear + 1;
	    }
	    double interest = 0;
	    double prevPrincipal = principal;
	    double currentPrincipal = 0;
	    //
	    loan.setPayment(""+payment);
	    loan.doUpdate();
	    // qq =" update ploan set payment="+payment+" where lid="+lid;
	    for(int i=0; i<pay_count;i++){
		interest = 0;
		currentPrincipal = 0;
		Payment pp = new Payment();
		pp.setId(""+i);
		pp.setLid(lid);
		pp.setDue_date(""+currentMonth+"/1/"+currentYear);
		pp.doSave();
		//
		currentMonth += 1; 
		if(currentMonth > 12) {
		    currentYear++;
		    currentMonth = 1;
		}
	    }
	}
	PaymentList pl = new PaymentList();
	pl.setLid(lid);
	pl.setYear(""+yearReq);
	String back = pl.find();
	if(back.isEmpty()){
	    List<Payment> ones = pl.getPayments();
	    if(ones != null){
		payments = ones;
	    }
	}
	int i=0, cc=0, firstRec = -1,prevRec=-1;
	double xx = 0, prevBalance = 0;
	if(payments != null){
	    firstRec = payments.get(0).getId();
	    // if(rs.next()){
	    if(firstRec == 0) prevBalance = principal; 
	    else if(firstRec > 0) prevRec = firstRec-1;
	    if(prevRec > -1){
		Payment pone = new Payment();
		pone.setLid(lid);
		pone.setId(""+prevRec);
		back = pone.doSelect();
		prevBalance = pone.getPrincipal_bal();
	    }
	}

	//
	out.println("<html><head><title>ProLoan</title>         ");
	Helper.writeWebCss(out, url);
	out.println("<script language=javascript>            ");
	out.println("  function validateForm(){		            ");
	out.println("     return true;				            ");
	out.println("	}	         			                ");
	out.println("  function validateDelete(){	            ");
	out.println("   var x = false;                          ");
	out.println("   x = confirm(\"Are you sure you want to delete this record\");");
	out.println("  return x;                            ");
	out.println(" }	   		                            ");
	out.println(" </script>				                ");
    	out.println(" </head><body>                         ");
	Helper.writeTopMenu(out, url);	
	//
    	// delete startNew
	out.println("<center><h2>Mortgage Details for "+yearReq+"</h2>");
	if(!message.isEmpty()){
	    out.println("<h3>"+message+"</h3>");
	}
	out.println("</center>");
	//
	out.println("<table border width=70%>");
	out.println("<tr><td align=center>");
	//
	// Add/Edit record
	//
	out.println("<table width=100%>");
	//the real table
	out.println("<form name=myForm method=post "+
		    "onSubmit=\"return validateForm2()\">");
	out.println("<input type=hidden name=lid value=\""+lid+"\">");
	//
	// Owner 
	out.println("<tr><td>"+owner);
	out.println("</td></tr>");
	//
	// Address
	out.println("<tr><td>");
	out.println(address+"</td></tr>");
	out.println("<tr><td>");
	out.println(cityStateZip+"</td></tr>");
	out.println("<tr><td>&nbsp;</td></tr>"); // separator
	out.println("<tr><td>Monthly Payment:" +payment+"</td></tr>");
	out.println("<tr><td>Late Fees:" +lateFee+"</td></tr>");
	out.println("<tr><td>&nbsp;</td></tr>");
	//
	out.println("</table></td><td><table>");
	//
	// Principal, rate
	out.println("<tr><td><b>Principal:</b></td><td align=right>");
	out.println(dir_amount+"</td></tr>");
	//
	// Rate
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
	// Direct Start date
	out.println("<tr><td><b>Start Date: </b></td><td align=right>");
	out.println(startDate+"</td></tr>");
	//
	// Number of payments
	out.println("<tr><td><b>Number of Payments:<b></td><td align=right>");
	out.println(pay_count+"</td></tr>");
	//
	// Fund type
	out.println("<tr><td><b>Fund Type: </b></td><td align=right>"+fund_type+"</td></tr>");
	//
	out.println("</table></td></tr>");
	if(payments != null){
	    out.println("<tr><td colspan=2><table border width=100%>");
	    out.println("<tr><th valign=bottom>Due <br>Date</th><th>Paid <br>Date</th><th>Amount <br>Paid</th><th valign=bottom>Interest</th><th valign=bottom>Principal</th><th>Principal <br>Balance</th><th>Paid <br>Late&nbsp;Fee</th><th valign=bottom>Receipt</th></tr>");
	    double paidTtl =0, principalTtl=0,lateFeeTtl=0,interestTtl=0,princBal=0;
	    for(Payment one:payments){
		out.println("<tr><td align=right><a href=\"" +url+
			    "Payment?id="+one.getId()+"&lid="+lid+
			    "\">"+
			    one.getDue_date()+"</a>"+
			    "</td><td align=right>" + 
			    one.getPaid_date()+
			    "</td><td align=right>" + 
			    fn.format(one.getAmount_paid())+
			    "</td><td align=right>" + 
			    fn.format(one.getInterest())+
			    "</td><td align=right>" + 
			    fn.format(one.getPrincipal())+
			    "</td><td align=right>" + 
			    fn.format(one.getPrincipal_bal())+
			    "</td><td align=right>" + 
			    fn.format(one.getLate_feeDbl())+
			    "</td><td align=right>" + 
			    one.getReceipt()+	       
			    "</tr>");
		if(one.getAmount_paid() > 0){
		    paidTtl = paidTtl + one.getAmount_paid();
		    principalTtl += one.getPrincipal();
		    lateFeeTtl += one.getLate_feeDbl();
		    interestTtl += one.getInterest();
		    princBal = one.getPrincipal_bal(); // keep the last one
		}
	    }
	    out.println("<tr><td colspan=8></td></tr>");
	    //
	    // Totals
	    out.println("<tr><td colspan=2><b>TOTAL PD THRU DEC</b></td>"+
			"<td align=right>"+"<b>"+fn.format(paidTtl+0.005)+
			"</b></td><td align=right><b>"+
			fn.format(interestTtl)+
			"</b></td><td align=right><b>"+
			fn.format(principalTtl)+
			"</b></td><td align=right><b>"+
			fn.format(princBal)+
			"</b></td><td align=right><b>"+
			fn.format(lateFeeTtl)+
			"</b></td><td>&nbsp; </td></tr>");
	    //
	    // Deposit account
	    out.println("<tr><td colspan=8><b>Deposit Account: </b>");
	    out.println(deposit_acc+"</td></tr>"); 
	    //
	    out.println("</form></table></td></tr>");
	}
	out.println("</table>");	    
	//
	// Next and prev links
	out.println("<table width=70%><tr><td>&nbsp;");
	if(prevYear > 0){
	    out.println("<a href=\""+url+
			"Mortgage?lid="+lid+
			"&yearReq="+prevYear+
			"\">Previous Year</a>");
	}	
	out.println("</td><td align=right>&nbsp;");
	if(nextYear > 0){
	    out.println("<a href=\""+url+"Mortgage?lid="+lid+
			"&yearReq="+nextYear+
			"\">Next Year </a>");
	}	
	out.println("</td></tr></table>");
    	out.println("<br />");
	out.println("<a href="+url+"Loan?lid="+lid+">Back to Loan Page</a>");
	out.println("</body></html>");
	out.flush();
	out.close();
    }

}






















































