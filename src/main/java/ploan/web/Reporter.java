package ploan.web;

import java.util.*;
import java.text.NumberFormat;
import java.util.Locale;
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

@WebServlet(urlPatterns = {"/Report","/Reporter"})
public class Reporter extends TopServlet {

    PrintWriter os;
    String bgcolor = LoanServ.bgcolor;
    static NumberFormat nf=NumberFormat.getInstance(new Locale("en", "US"));        
    int maxlimit = 100; // limit on records
    static Logger logger = LogManager.getLogger(Reporter.class);
    /**
     * Generates the request form for this kind of reports.
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	res.setContentType("text/html");
	PrintWriter out = res.getWriter();

	String name, value;
	String assigned_to="", assigned_to_2="",
	    assigned_to_3="", usetodaysdate="";
	String date_resolved="", added_by="",empid="", pcid="";
	String action="", problem="", resolved="n", pub="";
	String time_spent="", resolved_day="",
	    resolved_year="", resolved_notes="";
	String entry_date="", id="", subcat="", title="";
	String allDepts="",busCat="\n";
	String date_res_to="", date_res_from="";
	String date_req_to="", date_req_from="";
	String time_spent_to="", time_spent_from="";
	String req_mm_to="", req_dd_to="", req_yy_to="";
	String req_mm_from="", req_dd_from="", req_yy_from="";
	String allCats = "", allSubcatArrs="", allSubcat="";
	int category = 0, resolved_month=0;

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
	out.println("<html><head><title>Loan Report</title>");
	Helper.writeWebCss(out, url);
	out.println("<script language=Javascript>");
	out.println(" function moveToNext(item, nextItem, e){ ");
	out.println(" keyCode = (window.Event) ? e.which: e.keyCode;  ");
	//out.println("  alert(\" keycode = \"+keyCode);  ");
	out.println("  if(keyCode > 47 && keyCode < 58){  "); // only numbers
	out.println("  if(item.value.length > 1){               ");
	out.println("  eval(nextItem.focus());                  ");
	out.println("  }}}      ");
	out.println("  function validateInteger(mm){	        ");
	out.println(" if(mm == \"0\" ||  mm == \"1\" || mm == \"2\" || ");
	out.println("   mm == \"3\" ||  mm == \"4\" || mm == \"5\"  || ");
	out.println("   mm == \"6\" ||  mm == \"7\" || mm == \"8\"  || ");
	out.println("   mm == \"9\"){                           ");
	out.println("     return true;				");
	out.println("  }                                        ");
	out.println("     return false;				");
	out.println("  }                                        ");
	out.println("  function validateMonth(mm){	        ");
	out.println(" var len = mm.length;                      ");
	out.println(" if(len == 1){                             ");
	out.println("     if(!validateInteger(mm)){             ");
	out.println("        return false;		        ");
	out.println("      }                                    ");
	out.println("     if(mm == \"0\"){                      ");
	out.println("       return false;		        ");
	out.println("     }                                     ");
	out.println("  }else{                                   ");
	out.println("     if(!validateInteger(mm.charAt(0)) && !validateInteger(mm.charAt(1))){     ");
	out.println("        return false;		      	");
	out.println("      }                                    ");
	out.println("    if(mm == \"00\" || mm > 12){           ");
	out.println("     return false;				");
	out.println("   } else if(mm.charAt(0) != \"0\" && mm.charAt(0) != \"1\"){");
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
	out.println("  function validateDay(dd){	        ");
	out.println(" var len = dd.length;                      ");
	out.println(" if(len == 1){                             ");
	out.println("    if(!validateInteger(dd))               "); 
	out.println("     return false;				");
	out.println("  }else{                                   ");
	out.println("    if(!validateInteger(dd.charAt(0)) || !validateInteger(dd.charAt(1))){   "); 
	out.println("     return false;				");
	out.println("     }                                     ");
	out.println("    if(dd.charAt(0) < 0 || dd.charAt(0) > 3 ){    ");
	out.println("     return false;				");
	out.println("    } else if(!dd.charAt(0) < 3 && dd.charAt(1) > 9){");
	out.println("     return false;				");
	out.println("    } else if(!dd.charAt(0) == \"3\" && dd.charAt(1) > 1){");
	out.println("     return false;				        ");
	out.println("    }}			                        ");
	out.println("     return true;				        ");
	out.println("  }                                                ");
	//
	out.println("  function validateForm(){		                ");
	out.println("     return true;				        ");
	out.println("	}	         			        ");
	//       
	out.println("  function fillSubList(selectCtrl, itemArray){     ");
	// out.println("      alert(\"In cat list \"+itemArray[0]);     ");
	out.println(" var i, j=0, prompt;                               ");
	out.println("  if( selectCtrl.options.length > 0){              ");
	out.println("  for (i = selectCtrl.options.length; i >= 0; i--){ ");
	out.println("     selectCtrl.options[i] = null;            "); 
	out.println("   }}                                         ");
	out.println("   if (itemArray != null) {                   ");
	out.println(" // add new items                             ");
	out.println(" for (i = 0; i < itemArray.length; i++) {     ");
	out.println(" selectCtrl.options[j] = new Option(itemArray[i]);");
	out.println("    j++;                                      ");
	out.println("   }                                          ");
	out.println("   // select first item (prompt) for sub list ");
	out.println("  selectCtrl.options[0].selected = true;      ");
	out.println("   }                                          ");
       	out.println("  }                                           ");
	out.println(" </script>				           ");
	out.println(" </head><body>                                ");
	Helper.writeTopMenu(out, url);
	//
	out.println("<h2><center>Loan Report</center></h2>");
	out.println("<center><table width=70% align=center border>");
	out.println("<tr><td bgcolor="+bgcolor+">");
	out.println("<table width=100%>");
	// 
	out.println("<form name=myForm method=POST>");
	out.println("<tr><td colspan=2><center>Select from the following "+
		    " report options.</td></tr>");
	// 1
	out.println("<tr><td align=right valign=top>1 - ");
	out.println("<input type=radio name=report checked "+
		    "value=cdbg_own "+
		    "></td><td valign=top>Loans, Funding & tenure type *");
	out.println("</td></tr>");
	//
	// 2
	out.println("<tr><td align=right>2 - ");
	out.println("<input type=radio name=report "+
		    "value=cdbg_rehab "+
		    "></td><td>CDBG/HOME, Rehab </td></tr>");
	// 3
	out.println("<tr><td align=right>3 - ");
	out.println("<input type=radio name=report "+
		    "value=cdbg_rent "+
		    "></td><td>CDBG/HOME, Rental </td></tr>");
	// 4
	out.println("<tr><td align=right>4 - ");
	out.println("<input type=radio name=report "+
		    "value=loan "+
		    "></td><td>Loans & Grants </td></tr>");
	// 5
	out.println("<tr><td align=right>5 - ");
	out.println("<input type=radio name=report "+
		    "value=clientLoan "+
		    "></td><td>Ethnicity, Race Loans Report </td></tr>");
	// 6
	out.println("<tr><td align=right>6 - ");
	out.println("<input type=radio name=report "+
		    "value=sec_106 "+
		    "></td><td>Section 106 Report</td></tr>");
	// 7
	out.println("<tr><td align=right>7 - ");
	out.println("<input type=radio name=report "+
		    "value=overdue "+
		    "></td><td>Overdue Payments</td></tr>");		
	// 8
	out.println("<tr><td align=right>8 - ");
	out.println("<input type=radio name=report "+
		    "value=expires "+
		    "></td><td>Loans Insurance Expire within one month "+
		    "</td></tr>");
	out.println("<tr><td colspan=2 align=center><hr></td></tr>");
	//
	// 9
	out.println("<tr><td align=right>9 - ");
	out.println("<input type=radio name=report "+
		    "value=expired "+
		    "></td><td>Expired Loans' Insurance </td></tr>");
		
	// 10
	out.println("<tr><td align=right>10 - ");
	out.println("<input type=radio name=report "+
		    "value=break_down "+
		    "></td><td>Income Break Down Loan/Grants for "+
		    "the period set below: "+
		    "</td></tr>");
	// 11
	out.println("<tr><td align=right>11 - ");
	out.println("<input type=radio name=report "+
		    "value=dir_loan "+
		    "></td><td>Direct Loans for "+
		    "the period set below:* "+
		    "</td></tr>");
	// 12
	out.println("<tr><td align=right>12 - ");
	out.println("<input type=radio name=report "+
		    "value=pay_off "+
		    "></td><td>Pays Off Loans for "+
		    "the period set below:* "+
		    "</td></tr>");
	// 13
	out.println("<tr><td align=right>13 - ");
	out.println("<input type=radio name=report "+
		    "value=grant "+
		    "></td><td>Grants for "+
		    "the period set below: "+
		    "</td></tr>");
	//
	// Narrow your search
	//
	out.println("<tr><td colspan=2>*<font color=green size=-1>You can "+
		    "select from the following options to narrow your "+
		    "search</font></td></tr>");
	out.println("<tr><td></td><td>"+
		    "Loans Funding Type:");
	out.println("<select name=fund_def_type>");
	out.println("<option value=\"\">All</option>");
	for(int i=1; i<LoanServ.FUND_TYPE_ARR.length; ++i){
	    out.println("<option>" + LoanServ.FUND_TYPE_ARR[i]);		
	}
	out.println("</select> ");
	out.println(" <b>Loan type:</b>");
       	out.println(" <select name=ptype size=1>");
	out.println("<option value=\"\">All</option>");
	for(int i=1; i<LoanServ.LOAN_TYPE_ARR.length; ++i){
	    out.println("<option>" + LoanServ.LOAN_TYPE_ARR[i]);
	}
	out.println("</select> Status:");
       	out.println(" <select name=active>");
	out.println("<option value=\"\">All</option>");
	out.println("<option value=\"y\">Active only</option>");				
	out.println("</select></td></tr>");				
	//
	out.println("<tr><td></td><td> Tenure Type: ");
	out.println("<select name=\"tenure_type\">");
	out.println("<option value=\"\">All</option>");
	for(int i=1;i<LoanServ.TENURE_ARR.length; ++i){
	    out.println("<option value=\""+i+"\">"+
			LoanServ.TENURE_ARR[i]);
	}
	out.println("</select>");
	out.println("</td></tr>");
	//
	out.println("<tr><td colspan=2 align=center>");
	out.println("<table border><tr><td></td>");
	out.println("<td>From <font color=green size=-1>"+
		    "mm/dd/yyyy </font></td><td>&nbsp;</td>"+
		    "<td>To <font color=green size=-1>"+
		    "mm/dd/yyyy </font></td></tr>");
	out.println("<tr><td>Date:</td>");
	out.println("<td><input type=text name=dd_from value=\""+
		    "\" size=10 maxlength=10></td>");
	out.println("<td>&nbsp;</td>");
	out.println("<td><input type=text name=dd_to value=\""+
		    "\" size=10 maxlength=10></td></tr>");
	out.println("</td></tr></table>");
	out.println("</td></tr>");
	//
	out.println("<tr><td colspan=2 align=center><hr></td></tr>");
	out.println("<tr><td colspan=2 align=right><input type=submit " +
		    "value=Submit></td></tr>");
	out.println("</table></td></tr>");
	// 
	out.println("</form></table>");
	out.println("<br>");
	String log_out = "<a href="+url+"Logout?"+
	    " target=_top>Log Out</a><br>";
	//	
	out.println(log_out);
	out.println("<br>");
	out.print("</center></body></html>");
	out.close();

    }
    /**
     * Processes the request and generates the report.
     * @param req
     * @param res
     * @throws ServletException
     * @throws IOException
     */
    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException{
	res.setContentType("text/html");

	// fields to be shown
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;

	PrintWriter out = res.getWriter();	
	// 
	// Client		  
	String c_titles [] = {  

	    "Client ID",
	    "Client Name",
	    "Company",
	    "Phones",
	    "Email",

	    "Address",
	    "City",
	    "State, Zip",
	    "House hold size",
	    "Ethnicity",

	    "Race",
	    "House hold type",
	    "Female head house hold?",
	    "Area median income"   // client
	};
	boolean c_show[] = { true, true, true, true, true,
	    true, true, true, true, true,
	    true, true, true, true
	};
	//
	// property
	String p_titles [] = {  // property
	    "ID",
	    "Type",
	    "Address",
	    "Contractor",
	    "Contractor Address",

	    "Tenure Type",
	    "Construction Year",
	    "Bedrooms",
	    "District",
	    "Neighborhood",

	    "IDIS #",
	    "Block Group",
	    "Census Tract",
	    "Ownership Type",
	    "Activity Type",

	    "Historically Elegible?",
	    "National Registered",
	    "Accessible",
	    "CHDO",
	    "Leverage",

	    "106 Review",
	    "Insurance Expiration"
	};
	boolean p_show[] = { true, true, true, true, true,
	    true, true, true, true, true,
	    true, true, true, true, true,
	    true, true, true, true, true,
	    true, true
	};
	//
	// Loan
	String l_titles [] = {  // Loan
	    "ID",
	    "Type",
	    "Contract Date",
	    "Direct Loan Amount",
	    "Interest Rate",

	    "Deferred Loan Amount",
	    "Conditional Loan Amount",
	    "Conditional Loan Terms",
	    "Conditional Loan Settlement Date",
	    "Direct Loan Settlement Date",

	    "Mortgage Satisfied?",
	    "Funding Type",
	    "Appraised Value",
	    "Sale Price",
	    "LTV"
	};
	boolean l_show[] = { true, true, true, true, true,
	    true, true, true, true, true,
	    true, true, true, true, true
	};

	String name, value;
	String report="";
	String date_res_to="", date_res_from="";
	String date_rec_to="", date_rec_from="";
	String time_spent_to="", time_spent_from="";
	String date_assigned="", busCat="", fund_type="";
	String rec_mm_to="", rec_dd_to="", rec_yy_to="";
	String rec_mm_from="", rec_dd_from="", rec_yy_from="";
	String dd_from="",dd_to="",active="";
	String tenure_type="",fund_def_type="", ptype="";

	Enumeration values = req.getParameterNames();
	String [] vals;
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("report")) {
		report =value;
	    }
	    else if (name.equals("tenure_type")) {
		tenure_type =value.trim();
	    }
	    else if (name.equals("fund_def_type")) {
		fund_def_type =value;
	    }
	    else if (name.equals("active")) {
		active=value;
	    }						
	    else if (name.equals("dd_from")) {
		dd_from=value;
	    }
	    else if (name.equals("dd_to")) {
		dd_to=value;
	    }
	    else if (name.equals("ptype")) {
		ptype=value;
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
	Calendar current_cal = Calendar.getInstance();
	int mm = current_cal.get(Calendar.MONTH)+1;
	int dd = current_cal.get(Calendar.DATE);
	int yyyy = current_cal.get(Calendar.YEAR);
	String today = ""+mm+"/"+dd+"/"+yyyy;
	out.println("<html><head><title>Loan Report</title>");
	Vector wherecases = new Vector();
	try{
	    con = Helper.getConnection();
	    if(con != null){
		stmt = con.createStatement();
	    }
	    else{
		logger.error("could not connect to DB ");
	    }
	}catch(Exception ex){
	    logger.error(ex);
	}
	//
	if(report.startsWith("cdbg")){
	    String titles[]={
		"ID",
		"Fund",
		"Name",
		"Address",
		"City state zip",
		"Contract Date",
		"Deferred",
		"Direct",
		"Conditional"
	    };
	    String qq = "select l.lid,"+
		"l.fund_def_type,"+
		"concat_ws(' ',c.f_name,c.l_name),concat_ws(' ',c.street_num,"+
		"c.street_dir,c.street_name,c.street_type,"+
		"c.post_dir,c.sud_type,c.sud_num),"+
		"concat_ws(' ',concat_ws(', ',c.city,c.state),c.zip),"+
		"date_format(l.contract_date,'%m/%d/%Y'),"+
		"l.defer_amount,l.dir_amount,l.cond_amount "+
		" from ploan l, pproperty p, pclient c "+
		" where l.pid = p.pid and l.cid=c.cid ";
	    //
	    String ttl = "<b>";
	    out.println("<center>");
	    if(!fund_def_type.equals("")){
		qq += " and l.fund_def_type='"+fund_def_type+"'"; 
		ttl += fund_def_type;
	    }
	    else{
		ttl += " CDBG/HOME ";
	    }
	    if(!ptype.equals("")){
		ttl += " vs ";
		ttl += "Loan "+ptype+" ";
		qq += " and l.ptype='"+ptype+"' "; 
	    }
	    if(!active.equals("")){
		ttl += " vs ";
		ttl += "Loan "+ptype+" ";
		qq += " and l.inactive is null "; 
	    }						
	    ttl += "<br>";
	    if(!tenure_type.equals("")){
		qq += " and p.tenure_type="+tenure_type; 
	    }
	    //
	    // title related
	    if(tenure_type.equals("2")){
		ttl += "Owner Occupied";
	    }
	    else if(tenure_type.equals("3")){
		ttl += "Owner Occupied/Rehab";
	    }
	    else if(tenure_type.equals("1")){
		out.println("<b>Rental</b><br>");
		ttl += " Rental<br>";
	    }
	    else
		ttl +="Owner Occupied/Rehab/Rental";
	    ttl += "</b><br>";
	    if(!dd_from.equals("")){
		qq += " and str_to_date('"+dd_from+
		    "','%m/%d/%Y') <= l.contract_date ";
		ttl += dd_from +" - ";
	    }
	    if(!dd_to.equals("")){
		qq += " and str_to_date('"+dd_to+
		    "','%m/%d/%Y') >= l.contract_date ";
		ttl += dd_to;
	    }
	    if(!dd_from.equals("") && dd_to.equals(""))
		ttl += today;
	    ttl += "<br>";
	    out.println(ttl);
	    qq += " order by c.l_name,c.f_name ";
	    try{
		//
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		// Report date
		//
		out.println("<table border>");
		int row = 0;
		while(rs.next()){
		    if(row%20 == 0){
			out.println("<tr><th></th>");
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
		    out.println("<td>"+row+"</td>");
		    String color = "";
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);
			if(that != null){
			    that = that.trim(); 
			    if(that.equals("")) that = "&nbsp;";
			    else if(that.equals("0")) that = "&nbsp;";
			}
			else that = "&nbsp;";
			if(c == 0){
			    that = "<a href=\""+url+"Loan?lid="+that+
				"&action=zoom"+
				"\">"+that+
				"</a>";
			}
			out.println("<td>"+that+"</td>");
		    }
		    out.println("</tr>");
		}
		out.println("</table>");
	    }catch(Exception ex){
		logger.error(ex+":"+qq);
		out.println(ex);
	    }
	}
	else if(report.equals("loan")){
	    // 
	    String titles [] = {  // client 

		"Loan ID",               // loan
		"Client Name",
		"Property Type",
		"Address",               
		"Insurance Expiration",  

		"Loan Type",
		"Contract Date",
		"Direct Loan Amount",     // 8
		"Interest Rate",
		"Deferred Loan Amount",   // 10
				
		"Conditional Loan Amount", // 11
		"Grant",                   // 12				
		"Conditional Loan Terms",
		"Conditional Loan Settlement Date",
		"Direct Loan Settlement Date", // 15
				
		"Mortgage Satisfied?",        
		"Funding Type",
		"Appraised Value",
		"Sale Price",                       
		"LTV"                         // 20
	    };
	    String ttl ="Loans & Grants Report ";
	    //
	    boolean show[] = {
		true, true, true, true, true, 
		true, true, true, true, true,
		true, true, true, true, true, 
		true, true, true, true, true
	    };
	    String qq = "select "+
		"l.lid,"+
		"concat_ws(' ',c.f_name,c.l_name,c.company),"+
		"p.ptype,concat_ws(' ',"+
		"p.street_num,"+  // 5
		"p.street_dir,p.street_name,p.street_type,"+
		"p.post_dir,p.sud_type,p.sud_num),"+
		"date_format(p.insur_expire,'%m/%d/%Y'),"+
		"l.ptype,"+
		"date_format(l.contract_date,'%m/%d/%Y'),"+
		"l.dir_amount,l.dir_rate,"+ // 7
		"l.defer_amount,l.cond_amount,l.l_grant, "+
		"l.cond_term,"+
		"date_format(l.cond_set_date,'%m/%d/%Y'),"+  // 15
		"date_format(l.dir_set_date,'%m/%d/%Y'),"+
		"l.satisfied,"+
		"l.fund_type,l.appraised_val,l.sale_price,l.ltv "+ // 20
		" from ploan l, pproperty p, pclient c "+
		" where l.pid = p.pid and l.cid=c.cid ";
	    if(!dd_from.equals("")){
		qq += " and str_to_date('"+dd_from+
		    "','%m/%d/%Y') <= l.contract_date ";
		ttl += " for "+dd_from+" - ";
	    }
	    if(!dd_to.equals("")){
		qq += " and str_to_date('"+dd_to+
		    "','%m/%d/%Y') >= l.contract_date ";
		ttl += " "+dd_to;
	    }
	    qq += " order by c.l_name,c.f_name";
	    //
	    out.println("<center><b>"+ttl+"</b><br />");
	    try{
		//
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		// Title page
		//
		out.println("<b>"+today+"</b><br>");
		out.println("<table border>");
		int row = 0;
		double l_total =0, g_total=0,d_total=0,c_total=0;
		double grant_ttl=0;
		while(rs.next()){
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

		    String color = "";
		    l_total += rs.getDouble(8);
		    d_total += rs.getDouble(10);
		    c_total += rs.getDouble(11);
		    g_total += rs.getDouble(12);
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);
			if(that != null){
			    that = that.trim(); 
			    if(that.equals("")) that = "&nbsp;";
			    if(c == 0){
				that = "<a href=\""+url+"Loan?lid="+that+
				    "&action=zoom"+
				    "\">"+that+
				    "</a>";
			    }
			    else if(c == 2){
				int jj = rs.getInt(c+1);
				that = LoanServ.PROP_TYPE_ARR[jj];
				if(that.trim().equals(""))
				    that = "&nbsp;";
			    }
			    else if(c == 12){
				int jj = rs.getInt(c+1);
				that = LoanServ.COND_TERM_ARR[jj];
				if(that.trim().equals(""))
				    that = "&nbsp;";
			    }
			}
			else that = "&nbsp;";
			if(c == 7 || c==8 || c == 9 || c == 10 || c == 11)
			    out.println("<td align=right>"+that+"</td>");
			else
			    out.println("<td>"+that+"</td>");
		    }
		    out.println("</tr>");
		}
		out.println("<tr><td colspan=7>Total</td>");
		out.println("<td align=right>"+l_total+"</td>");
		out.println("<td>&nbsp;</td>");
		out.println("<td align=right>"+d_total+"</td>");
		out.println("<td align=right>"+c_total+"</td>");
		out.println("<td align=right>"+g_total+"</td>");
		out.println("<td colspan=8>&nbsp;</td>");
		out.println("</tr></table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }
	}
	else if(report.equals("clientLoan")){
	    String titles [] = {  
		"Loan ID",               
		"Client Name",
		"Ethnicity",
		"Race",
		"Loan Type",
		"Contract Date"
	    };
	    //
	    boolean show[] = {
		true, true, true, true, true, 
		true
	    };
	    String qq = "select "+
		"l.lid,"+
		"concat_ws(' ',c.f_name,c.l_name,c.company),"+
		"c.ethnicity,"+
		"c.race,"+
		"l.ptype,"+
		"date_format(l.contract_date,'%m/%d/%Y') "+

		" from ploan l, pclient c "+
		" where l.cid=c.cid "+
		" order by c.l_name,c.f_name";
	    //
	    out.println("<center><b>Clients/Loan Report</b><br>");
	    try{
		//
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		// Title page
		//
		out.println("<b>"+today+"</b><br>");
		out.println("<table border>");
		int row = 0;
		while(rs.next()){
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
		    String color = "";
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);
			if(that != null){
			    that = that.trim(); 
			    if(c == 0){
				that = "<a href=\""+url+"Loan?lid="+that+
				    "&action=zoom"+
				    "\">"+that+
				    "</a>";
			    }
			    else if(c == 4){

			    }
			    else if(c == 2){
				int jj = rs.getInt(c+1);
				that = LoanServ.ETHENIC_ARR[jj];
				if(that.equals("")) that = "&nbsp;";
			    }
			    else if(c == 3){
				int jj = rs.getInt(c+1);
				that = LoanServ.RACE_ARR[jj];
				if(that.equals("")) that = "&nbsp;";
			    }
			}
			else that = "&nbsp;";
			if(that.trim().equals("")) that = "&nbsp;";
			out.println("<td>"+that+"</td>");
		    }
		    out.println("</tr>");
		}
		out.println("</table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }
	}
	else if(report.equals("sec_106")){
	    // 
	    String titles [] = {  // client 
		"Loan ID",            
		"Client Name",
		"Address",               
		"Historically Eligible",
		"National Register",
		"106 Review"    
	    };
	    //
	    boolean show[] = {
		true, true, true, true, true, 
		true, true 
	    };
	    String qq = "select "+
		"l.lid,"+
		"concat_ws('/ ',concat_ws(' ',c.f_name,c.l_name),c.company),"+
		"concat_ws(' ',"+
		"p.street_num,"+  // 5
		"p.street_dir,p.street_name,p.street_type,"+
		"p.post_dir,p.sud_type,p.sud_num),"+
		"p.hist_elegible,p.national_reg,p.rev_106 "+
		" from ploan l, pproperty p, pclient c "+
		" where l.pid = p.pid and l.cid=c.cid "+
		" order by c.l_name,c.f_name,c.company";
	    //
	    out.println("<center><b>Loans Report</b><br>");
	    try{
		//
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		// Title page
		//
		out.println("<b>"+today+"</b><br>");
		out.println("<table border>");
		int row = 0;
		while(rs.next()){
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
		    String color = "";
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);
			if(that != null){
			    that = that.trim(); 
			    if(c == 0){
				that = "<a href=\""+url+"Loan?lid="+that+
				    "&action=zoom"+
				    "\">"+that+
				    "</a>";
			    }
			    else if(c == 1){
				that = that.trim();
				if(that.equals("/")) that = "&nbsp;";
				else if(that.startsWith("/"))
				    that = that.substring(1);
				else if(that.endsWith("/"))
				    that = that.substring(0,that.length()-1);
			    }
			    else if(that.equals("")) 
				that = "&nbsp;";
			}
			else that = "&nbsp;";
			out.println("<td>"+that+"</td>");
		    }
		    out.println("</tr>");
		}
		out.println("</table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }
	}
	else if(report.startsWith("expired")){
	    //
	    String titles [] = {  // client 

		"Loan ID",              
		"Client Name",
		"Address",               
		"Insurance Expiration",  
		"Type",                  // 5

		"Contract Date",
		"Direct Loan Amount",        
		"Interest Rate",
		"Deferred Loan Amount",
		"Conditional Loan Amount",

		"Conditional Loan Terms",
		"Conditional Loan Settlement Date",  
		"Direct Loan Settlement Date",
		"Funding Type"
	    };
	    //
	    boolean show[] = {
		true, true, true, true, true, 
		true, true, true, true, true, 
		true, true, true, true
	    };
	    String qq = "select l.lid,"+ 
		"concat_ws(' ',c.f_name,c.l_name, c.company),"+
		"concat_ws(' ',p.street_num,"+  
		"p.street_dir,p.street_name,p.street_type,"+
		"p.post_dir,p.sud_type,p.sud_num),"+
		"date_format(p.insur_expire,'%m/%d/%Y'),"+
		"l.ptype,"+
		"date_format(l.contract_date,'%m/%d/%Y'),"+
		"l.dir_amount,l.dir_rate,"+
		"l.defer_amount,l.cond_amount, "+
		"l.cond_term,"+
		"date_format(l.cond_set_date,'%m/%d/%Y'),"+  // 12
		"date_format(l.dir_set_date,'%m/%d/%Y'),"+
		"l.fund_type "+                            // 14
		" from ploan l, pproperty p, pclient c "+
		" where l.pid = p.pid and l.cid=c.cid "+
		" and (p.insur_expire < now() and not p.insur_expire is null) "+
		" and (l.satisfied is null or l.satisfied ='N') "+
		" and l.inactive is null ";
	    if(!fund_def_type.equals("")){
		qq += " and l.fund_def_type='"+fund_def_type+"'"; 
	    }
	    if(!ptype.equals("")){
		qq += " and l.ptype='"+ptype+"' "; 
	    }
	    if(!tenure_type.equals("")){
		qq += " and p.tenure_type="+tenure_type; 
	    }			
	    //
	    // Page title
	    //
	    out.println("<center><b>Expired Loan Insurance </b><br>");
	    try{
		//
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		out.println("<b>"+today+"</b><br>");
		out.println("<table border>");
		int row = 0;
		while(rs.next()){
		    if(row%20 == 0){
			out.println("<tr><th></th>");
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
		    out.println("<td>"+row+"</td>");
		    String color = "";
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);
			if(that != null){
			    that = that.trim(); 
			    if(that.equals("")) that = "&nbsp;";
			    if(c == 0){
				that = "<a href=\""+url+"Loan?lid="+that+
				    "&action=zoom"+
				    "\">"+that+"</a>";
			    }
			    else if(c == 10){
				int jj = rs.getInt(c+1);
				that = LoanServ.COND_TERM_ARR[jj];
				if(that.equals("")) that = "&nbsp;";
			    }
			}
			else that = "&nbsp;";
			out.println("<td>"+that+"</td>");
		    }
		    out.println("</tr>");
		}
		out.println("</table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }
	}
	else if(report.startsWith("break")){
	    String titles [] = {  
		"Loan ID",   
		"Client Name",
		"Address",               
		"Program",  
		"Contract Date",

		"Fund",    
		"Income"
	    };
	    boolean show[] = {
		true, true, true, true, true,
		true, true
	    };
	    String qq = "select l.lid,"+ 
		"concat_ws(' ',c.f_name,c.l_name,c.company),"+
		"concat_ws(' ',p.street_num,"+  
		"p.street_dir,p.street_name,p.street_type,"+
		"p.post_dir,p.sud_type,p.sud_num),"+
		"l.ptype,"+
		"date_format(l.contract_date,'%m/%d/%Y'),"+
		"l.fund_type,c.ami "+ 
		" from ploan l, pproperty p, pclient c "+
		" where l.pid = p.pid and l.cid=c.cid ";
	    if(!dd_from.equals(""))
		qq += " and str_to_date('"+dd_from+
		    "','%m/%d/%Y') < l.contract_date ";
	    if(!dd_to.equals(""))
		qq += " and str_to_date('"+dd_to+
		    "','%m/%d/%Y') > l.contract_date ";
	    qq += " order by l.contract_date,c.l_name ";
	    out.println("<center><b> Income Breakdown Loans "+
			"</b><br>");
	    //
	    try{
		//
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		out.println("<b>"+today+"</b><br>");
		out.println("<table border>");
		int row = 0;
		while(rs.next()){
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
		    String color = "";
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);
			if(that != null){
			    that = that.trim(); 
			    if(that.equals("")) that = "&nbsp;";
			    if(c == 0){
				that = "<a href=\""+url+"Loan?lid="+that+
				    "&action=zoom"+
				    "\">"+that+
				    "</a>";
			    }
			    else if(c == 6){
				int jj = rs.getInt(c+1);
				that = LoanServ.AMI_ARR[jj];
				if(that.equals("")) that = "&nbsp;";
			    }
			}
			else that = "&nbsp;";
			out.println("<td>"+that+"</td>");
		    }
		    out.println("</tr>");
		}
		out.println("</table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }
	}
	else if(report.startsWith("dir_l")){ // direct loans
	    String titles [] = {  
		"Loan ID",               //  loan ID
		"Client Name",
		"Total Payment",
		"Total Principle",  
		"Total Interest",    
		"Late Fees",
		"Principal Balance"
	    };
	    boolean show[] = {
		true, true, true, true, 
		true, true, true
	    };
	    String qq = "select ga.n1,ga.n2,sum(ga.s1),sum(ga.s2),sum(ga.s3),"+
		"sum(ga.s4),"+
		"min(ga.s5) from ("+
		"select m.lid n1, "+ 
		"concat_ws(' ',c.f_name, c.l_name, c.company) n2,"+
		"m.amount_paid s1, "+
		"m.amount_paid-m.interest s2,"+
		"m.interest s3, "+
		"m.late_fee s4, "+
		"m.principal_bal s5 "+
		" from "+
		" ploan l, pclient c,"+
		" pmortgage m ";
	    if(!tenure_type.equals("")){
		qq += ", pproperty p ";
	    }
	    qq +=" where l.cid=c.cid and l.lid=m.lid  ";
	    if(!active.equals("")){
		qq += " and l.inactive is null ";
	    }
	    if(!dd_from.equals(""))
		qq += " and str_to_date('"+dd_from+
		    "','%m/%d/%Y') <= m.paid_date ";
	    if(!dd_to.equals(""))
		qq += " and str_to_date('"+dd_to+
		    "','%m/%d/%Y') >= m.paid_date ";
	    if(!fund_def_type.equals("")){
		qq += " and l.fund_def_type='"+fund_def_type+"'"; 
	    }
	    if(!ptype.equals("")){
		qq += " and l.ptype='"+ptype+"' "; 
	    }
	    if(!tenure_type.equals("")){
		qq += " and l.pid = p.pid ";
		qq += " and p.tenure_type="+tenure_type; 
	    }						
	    qq += ") as ga group by ga.n1,ga.n2 order by ga.n2";
	    if(!dd_from.equals("") && dd_to.equals(""))
		dd_to = today;
	    double ttlPr=0.,ttlInt=0.,ttlLate=0.,ttlPaid=0.,total=0.,ttlBal=0.;
	    out.println("<center><b> Direct Loan Amount Payments Details<br> "+
			" "+dd_from+" - "+dd_to + "</b><br>");
	    //
	    try{
		//
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		out.println("<table border>");
		int row = 0;
		while(rs.next()){
		    if(row == 0){
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
		    String color = "";
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);
			if(that != null){
			    that = that.trim(); 
			    if(that.equals("")) that = "&nbsp;";
			    if(c == 0){
				that = "<a href=\""+url+"Loan?lid="+that+
				    "&action=zoom"+
				    "\">"+that+
				    "</a>";
			    }
			}
			else that = "&nbsp;";
			if(c == 2){
			    ttlPaid += rs.getDouble(c+1);
			    that = nf.format(that);
			}
			else if(c == 3){
			    ttlPr += rs.getDouble(c+1);
			    that = nf.format(that);
			}
			else if(c == 4){
			    ttlInt += rs.getDouble(c+1);
			    that = nf.format(that);
			}
			else if(c == 5){
			    ttlLate += rs.getDouble(c+1);
			    that = nf.format(that);
			}
			else if(c == 6){
			    ttlBal += rs.getDouble(c+1);
			    that = nf.format(that);
			}
			if(c > 1)
			    out.println("<td align=right>$"+that+"</td>");
			else if(c == 1)
			    out.println("<td>"+Helper.escapeIt(that)+"</td>");
			else
			    out.println("<td>"+that+"</td>");							
		    }
		    out.println("</tr>");
		}
		total = ttlPaid +ttlPr+ttlInt+ttlLate;
		out.println("<tr><td>Total</td>");
		out.println("<td align=right>$"+Math.round(total*100)/100.+"</td>");
		out.println("<td align=right>$"+Math.round(ttlPaid*100)/100.+"</td>");
		out.println("<td align=right>$"+Math.round(ttlPr*100)/100.+"</td>");
		out.println("<td align=right>$"+Math.round(ttlInt*100)/100.+"</td>");
		out.println("<td align=right>$"+Math.round(ttlLate*100)/100.+"</td>");
		out.println("<td align=right>$"+Math.round(ttlBal*100)/100.+"</td>");
		out.println("</tr>");
		out.println("</table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }
	}
	else if(report.startsWith("overdue")){
	    String titles [] = {  
		"Loan ID",               //  loan ID
		"Client Name",
		"last Payment",
		"Last Payment Date"
	    };
	    Calendar cal = Calendar.getInstance();
	    mm =  (current_cal.get(Calendar.MONTH)+1);
	    dd =   current_cal.get(Calendar.DATE);
	    int year = current_cal.get(Calendar.YEAR);
	    if(mm == 1){
		mm = 12;
		year = year -1;
	    }
	    else mm = mm-1;
			
	    String dueDate = year+"/"+((mm > 9)? ""+mm:"0"+mm)+"/15";
	    String qq =	
		" select l.lid, "+ 
		" concat_ws(' ',c.f_name, c.l_name, c.company),"+
		" m.amount_paid, "+
		" date_format(m.paid_date,'%m/%d/%Y'), "+
		" date_format(m.paid_date,'%Y/%m/%d') "+				
		" from "+
		" ploan l, pclient c, pmortgage m "+
		" where m.amount_paid > 0 and "+
		" not m.paid_date is null "+
		" and l.cid=c.cid and m.lid=l.lid "+
		" and l.pay_off_date is null "+
		" and (l.startDate+INTERVAL l.terms YEAR) > str_to_date('"+dueDate+"','%Y/%m/%d') and l.inactive is null "+
		" order by l.lid, m.paid_date DESC ";
	    out.println("<center><b> Overdue Loan Payments </b><br>");
	    out.println("<table border>");			
	    try{
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		int row = 0;
		int id = 0, prev_id = 0;
		while(rs.next()){
		    id = rs.getInt(1);
		    if(id == prev_id) continue;
		    else{
			prev_id = id;
		    }
		    String str = rs.getString(5);
		    if(str.compareTo(dueDate) > 0) continue;
		    if(row%20 == 0){
			out.println("<tr>");
			for (int c = 0; c < titles.length; c++){
			    out.println("<th>"+titles[c]+
					"</th>");
			}
			out.println("</tr>");
		    }
		    if(row%2 == 0)
			out.println("<tr bgcolor=#CDC9A3>");
		    else
			out.println("<tr>");
		    row++;				
		    str = rs.getString(1);
		    str = "<a href=\""+url+"Loan?lid="+str+
			"&action=zoom"+
			"\">"+str+
			"</a>";
		    out.println("<td>"+str+"</td>");
		    for(int i=2;i<5;i++){
			str = rs.getString(i);
			if(str == null || str.equals(""))
			    str = "&nbsp;";
			out.println("<td>"+str+"</td>");
		    }
		    out.println("</tr>");
		}
		out.println("</table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }				
	}
	else if(report.startsWith("expires")){
	    //
	    String from = ""+mm+"/"+dd+"/"+yyyy;
	    int m2 = mm+1, d2=dd, y2=yyyy;
	    if(mm == 12){
		m2 = 1;
		y2 += 1;
	    }
	    if(m2 == 2 && d2 > 28){
		d2 = 28;
	    }
	    else if(d2 == 31){
		if(m2 == 4 || m2 == 6 || m2 == 9 || m2 == 11)
		    d2 = 30;
	    }
	    //
	    // one month from now
	    //
	    String to = ""+m2+"/"+d2+"/"+y2;
	    String titles [] = {  // client 

		"Loan ID",               //  loan
		"Client Name",
		"Address",               
		"Insurance Expiration",  
		"Type",                  // 5

		"Contract Date",
		"Direct Loan Amount",    
		"Interest Rate",
		"Deferred Loan Amount",
		"Conditional Loan Amount", // 10

		"Conditional Loan Terms",
		"Conditional Loan Settlement Date",  
		"Direct Loan Settlement Date",
		"Funding Type"             // 14

	    };
	    boolean show[] = {
		true, true, true, true, true,
		true, true, true, true, true, 
		true, true, true, true
	    };
	    ///
	    String qq = "select l.lid,"+ 
		"concat_ws(' ',c.f_name,c.l_name, c.company),"+
		"concat_ws(' ',p.street_num,"+  
		"p.street_dir,p.street_name,p.street_type,"+
		"p.post_dir,p.sud_type,p.sud_num),"+
		"date_format(p.insur_expire,'%m/%d/%Y'),"+
		"l.ptype,"+
		"date_format(l.contract_date,'%m/%d/%Y'),"+
		"l.dir_amount,l.dir_rate,"+
		"l.defer_amount,l.cond_amount, "+
		"l.cond_term,"+
		"date_format(l.cond_set_date,'%m/%d/%Y'),"+  // 13
		"date_format(l.dir_set_date,'%m/%d/%Y'),"+
		"l.fund_type "+ // 15
		" from ploan l, pproperty p, pclient c "+
		" where l.pid = p.pid and l.cid=c.cid "+
		" and p.insur_expire >= str_to_date('"+from +  // between
		"','%m/%d/%Y') and p.insur_expire <= str_to_date('"+to+
		"','%m/%d/%Y') and "+
		" (l.satisfied is null "+
		" or l.satisfied='N') and l.inactive is null ";
	    //
	    // Page title
	    //
	    out.println("<center><b> Loan Insurance Expire within One "+
			"Month</b><br>");
	    //
	    try{
		//
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		out.println("<b>"+today+"</b><br>");
		out.println("<table border>");
		int row = 0;
		while(rs.next()){
		    if(row%20 == 0){
			out.println("<tr>");
			for (int c = 0; c < titles.length; c++){
			    out.println("<th>"+titles[c]+
					"</th>");
			}
			out.println("</tr>");
		    }
		    if(row%2 == 0)
			out.println("<tr bgcolor=#CDC9A3>");
		    else
			out.println("<tr>");
		    row++;
		    String color = "";
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);
			if(that != null){
			    that = that.trim(); 
			    if(that.equals("")) that = "&nbsp;";
			    if(c == 0){
				that = "<a href=\""+url+"Loan?lid="+that+
				    "&action=zoom"+
				    "\">"+that+
				    "</a>";
			    }
			    else if(c == 10){
				int jj = rs.getInt(c+1);
				that = LoanServ.COND_TERM_ARR[jj].trim();
				if(that.equals("")) that = "&nbsp;";
			    }
			}
			else that = "&nbsp;";
			out.println("<td>"+that+"</td>");
		    }
		    out.println("</tr>");
		}
		out.println("</table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }
	}
	else if(report.startsWith("grant")){

	    String titles [] = {  // client 

		"Loan ID",               //  loan
		"Client Name",
		"Address",               
		"Insurance Expiration",  
		"Type",                  // 5

		"Contract Date",
		"Direct Loan Amount",
		"Grant Amount",
		"Interest Rate",
		"Deferred Loan Amount",  // 10
				
		"Conditional Loan Amount", // 11
		"Conditional Loan Terms",
		"Conditional Loan Settlement Date",  
		"Direct Loan Settlement Date",
		"Funding Type"             // 15

	    };
	    boolean show[] = {
		true, true, true, true, true,
		true, true, true, true, true, 
		true, true, true, true, true
	    };
	    double g_total = 0;
	    double l_total = 0;
	    double d_total = 0;
	    double c_total = 0;
	    ///
	    String qq = "select l.lid,"+ 
		"concat_ws(' ',c.f_name,c.l_name, c.company),"+
		"concat_ws(' ',p.street_num,"+  
		"p.street_dir,p.street_name,p.street_type,"+
		"p.post_dir,p.sud_type,p.sud_num),"+
		"date_format(p.insur_expire,'%m/%d/%Y'),"+
		"l.ptype,"+
		"date_format(l.contract_date,'%m/%d/%Y'),"+
		"l.dir_amount,l.l_grant,l.dir_rate,"+
		"l.defer_amount,l.cond_amount, "+
		"l.cond_term,"+
		"date_format(l.cond_set_date,'%m/%d/%Y'),"+  // 13
		"date_format(l.dir_set_date,'%m/%d/%Y'),"+
		"l.fund_type "+ // 15
		" from ploan l, pproperty p, pclient c "+
		" where l.pid = p.pid and l.cid=c.cid "+
		" and l.l_grant > 0 ";
	    if(!dd_from.equals(""))
		qq += " and str_to_date('"+dd_from+
		    "','%m/%d/%Y') < l.contract_date ";
	    if(!dd_to.equals(""))
		qq += " and str_to_date('"+dd_to+
		    "','%m/%d/%Y') > l.contract_date ";			
	    //
	    // Page title
	    //
	    out.println("<center><b> Loans with Grants</b><br>");
	    //
	    try{
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		out.println("<b>"+today+"</b><br>");
		out.println("<table border>");
		int row = 0;
		while(rs.next()){
		    if(row%20 == 0){
			out.println("<tr>");
			for (int c = 0; c < titles.length; c++){
			    out.println("<th>"+titles[c]+
					"</th>");
			}
			out.println("</tr>");
		    }
		    if(row%2 == 0)
			out.println("<tr bgcolor=#CDC9A3>");
		    else
			out.println("<tr>");
		    row++;
		    String color = "";
		    l_total += rs.getDouble(7);
		    g_total += rs.getDouble(8);
		    d_total += rs.getDouble(10);
		    c_total += rs.getDouble(11);
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);

			if(that != null){
			    that = that.trim(); 
			    if(that.equals("")) that = "&nbsp;";
			    if(c == 0){
				that = "<a href=\""+url+"Loan?lid="+that+
				    "&action=zoom"+
				    "\">"+that+
				    "</a>";
			    }
			    else if(c == 11){
				int jj = rs.getInt(c+1);
				that = LoanServ.COND_TERM_ARR[jj].trim();
				if(that.equals("")) that = "&nbsp;";
			    }
			}
			else that = "&nbsp;";
			if(c == 6 || c == 7 || c == 8 || c == 9 || c == 10)
			    out.println("<td align=right>"+that+"</td>");
			else
			    out.println("<td>"+that+"</td>");
		    }
		    out.println("</tr>");
					
		}
		if(g_total > 0){
		    out.println("<tr><td colspan=6 align=right>Total</td>");
		    out.println("<td align=right>$"+l_total+"</td>");
		    out.println("<td align=right>$"+g_total+"</td>");
		    out.println("<td>&nbsp;</td>");
		    out.println("<td align=right>$"+d_total+"</td>");
		    out.println("<td align=right>$"+c_total+"</td>");
		    out.println("<td colspan=4>&nbsp;</td></tr>");
		}
		out.println("</table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }
	}		
	else if(report.startsWith("pay_off")){
	    //
	    // one month from now
	    //
	    String titles [] = {  
		"Loan ID",          
		"Client Name",
		"Address",               
		"Loan Type",                  
		"Funding Type",

		"Contract Date",
		"Deferred Loan Amount",
		"Pays Off Amount",
		"Pays Off Date"
	    };
	    boolean show[] = {
		true, true, true, true, true,
		true, true, true, true
	    };
	    String qq = "select l.lid,"+ 
		"concat_ws(' ',c.f_name,c.l_name, c.company),"+
		"concat_ws(' ',p.street_num,"+  
		"p.street_dir,p.street_name,p.street_type,"+
		"p.post_dir,p.sud_type,p.sud_num),"+
		"l.ptype,"+
		"l.fund_type, "+ 
		"date_format(l.contract_date,'%m/%d/%Y'),"+
		"l.defer_amount, "+
		"l.pay_off,"+
		"date_format(l.pay_off_date,'%m/%d/%Y') "+  
		" from ploan l, pproperty p, pclient c "+
		" where l.pid = p.pid and l.cid=c.cid and pay_off > 0 ";
	    if(!active.equals("")){
		qq += " and l.inactive is null ";
	    }						
	    if(!dd_from.equals(""))
		qq += " and l.pay_off_date >= str_to_date('"+dd_from +  
		    "','%m/%d/%Y') ";
	    if(!dd_to.equals(""))
		qq += " and l.pay_off_date <= str_to_date('"+dd_to +  
		    "','%m/%d/%Y') ";
	    if(!fund_def_type.equals("")){
		qq += " and l.fund_def_type='"+fund_def_type+"'"; 
	    }
	    if(!ptype.equals("")){
		qq += " and l.ptype='"+ptype+"' "; 
	    }
	    if(!tenure_type.equals("")){
		qq += " and p.tenure_type="+tenure_type; 
	    }
	    qq += " order by c.l_name,c.f_name ";
	    //
	    // Page Title
	    //
	    out.println("<center><b> Pays Off Loans <br> ");
	    if(!dd_from.equals("") && dd_to.equals("")) 
		dd_to = today;
	    if(!dd_from.equals(""))
		out.println(dd_from+" - ");
	    if(!dd_to.equals(""))
		out.println(dd_to );
	    out.println("</b><br>");
	    //
	    try{
		if(debug){
		    logger.debug(qq);
		}
		rs = stmt.executeQuery(qq);
		// 
		out.println("<table border>");
		int row = 0;
		double pay_off_ttl = 0.;
		while(rs.next()){
		    if(row%20 == 0){
			out.println("<tr>");
			for (int c = 0; c < titles.length; c++){
			    out.println("<th>"+titles[c]+"</th>");
			}
			out.println("</tr>");
		    }
		    if(row%2 == 0)
			out.println("<tr bgcolor=#CDC9A3>");
		    else
			out.println("<tr>");
		    row++;
		    String color = "";
		    pay_off_ttl += rs.getDouble(8);
		    for (int c = 0; c < titles.length; c++){
			String that = rs.getString(c+1);
			if(that != null){
			    that = that.trim(); 
			    if(that.equals("")) that = "&nbsp;";
			    if(c == 0){
				that = "<a href=\""+url+"Loan?lid="+that+
				    "&action=zoom"+
				    "\">"+that+"</a>";
			    }
			    else if(that.equals("0") || that.equals("")){
				that = "&nbsp;";
			    }
			}
			else that = "&nbsp;";
			//
			if(c == 6 || c == 7){
			    out.println("<td align=right>");
			    if(!that.equals("&nbsp;"))
				out.println("$"+nf.format(that));
			    else
				out.println(that);
			    out.println("</td>");
			}
			else
			    out.println("<td>"+that+"</td>");
		    }
		    out.println("</tr>");
		}
		if(pay_off_ttl > 0){
		    out.println("<tr><td colspan=7 align=right>Total</td>"+
				"<td align=right>$"+
				nf.format(""+pay_off_ttl)+
				"</td><td>&nbsp;</td></tr>");
		}
		out.println("</table>");
	    }catch(Exception ex){
		out.println(ex);
		logger.error(ex+":"+qq);
	    }
	}
	out.println("<br>");
	out.println("</body>");
	out.println("</html>");
	Helper.databaseDisconnect(con, stmt, rs);
		
    }

}






















































