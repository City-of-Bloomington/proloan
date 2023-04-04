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

@WebServlet(urlPatterns = {"/Loan"})
public class LoanServ extends TopServlet{

    static Logger logger = LogManager.getLogger(LoanServ.class);
	
    public final static String bgcolor = "silver";// #bfbfbf gray
    public final static String [] strMatchArr = {"",
	"is",
	"starts with",
	"ends with",
	"contains"
    };

    public static final String[] YES_NO_ARR ={"","Y","N"};
    public static final String[] ETHENIC_ARR ={
	"",
	"Hispanic",
	"Non Hispanic"};
    public static final String[] RACE_ARR ={
	"",	
	"White",
	"Black/African American",
	"Asian",
	"American Indian/Alaskan Native",
	"Native Hawaiian/ Other Pacific Islander",
	"American Indian/ Alaskan Native & White",
	"Asian & White",
	"Black/ African American & White",
	"Amer. Indian/Alaskan Native & Black/ African Amer.",
	"Other, Multi-racial"
    };
    public final static String [] SUD_TYPE_ARR = {
	"",
	"Apartment",
	"Building",
	"Dept",
	"Floor",
	"Lot",
	"Room",
	"Suite",
	"Unit"};
    public final static String [] SUD_KEY_ARR = {
	"",
	"A",
	"B",
	"D",
	"F",
	"L",
	"R",
	"S",
	"U"};
    public final static String STREET_KEY_ARR[] = {"\"\"",
	"AVE", "BND", "BLVD", "CIR", "CT", 
	"CTR", "DR",  "EXPY", "LN", "PIKE",
	"PKY", "PL",  "RD",    "ST", "TER", 
	"TPKE", "TURN"};
    public final static String STREET_TYPE_ARR[] = {"",
	"Avenue","Bend", "Boulevard", "Circle", "Court",
	"Center", "Drive", "Expressway", "Lane", "Pike" ,
	"Parkway" ,"Place" ,"Road" , "Street", "Terrace",
	"Turnpike","Turn"}; 
    public final static String DIR_ARR [] = {
	"",
	"N",
	"S",
	"E",
	"W"};
    public final static String LOAN_TYPE_ARR [] = {
	"",
	"DP&CC", 
	"EHR",
	"HMAL",
	"OOR", 
	"PR", 
	"RNC", 
	"RR", 
	"R101",
	"Other"
    };
    public final static String COND_TERM_ARR [] = {
	"",
	"5 yr. forgiveness", 
        "7 yr. forgiveness", 
        "5 yr. affordability/5 yr. forgiveness", 
        "10 yr. affordability/10 yr. forgiveness", 
        "15 yr. forgiveness", 
        "20 yr. forgiveness", 
        "Other"
    }; 
    public final static String FUND_TYPE_ARR [] = {
	"",
	"CBDG",
	"HOME",
	"Other"
    };

    public final static String AMI_ARR [] = { // area median income
	"",
	"0-30%",
	"31-50%",
	"51-60%",
	"61-80%",
	"81-100%",
	"100%+"
    };
    public final static String BEDROOMS_ARR [] = { // bedrooms
	"",
	"1",
	"2",
	"3",
	"4",
	"5+"
    };
    public final static String TENURE_ARR [] = {
	"",
	"Rental",
	"Homeownership (first-time buyer)",
	"Homeownership (rehab.)"
    };
    public final static String PROP_TYPE_ARR [] = {
	"",
	"Condominium",
	"Single family attached",
	"Single family detached",
	"Cooperative",
	"Manufactured Home",
	"Trailer",
	"Appartment"
    };
    public final static String HOUSEHOLD_TYPE_ARR [] = {
	"",
	"Single/Non Elderly",
	"Elderly",
	"Related/Single Parent",
	"Related/Two Parents",
	"Other",
	"Vacant"
    };

    public final static String OWNER_TYPE_ARR [] = {
	"",
	"Individual",
	"Corporation",
	"Public entity",
	"Joint tenants",
	"Partnership",
	"Not-for-profit",
	"Other"
    };
    public final static String ACTIVITY_TYPE_ARR [] = {
	"",
	"Acquisition", 
	"Acquisition/Rehab", 
	"Moderate Rehab", 
	"Substantial Rehab", 
	"New Construction"
    }; 
    public final static String ASSIST_ARR [] = {
	"",
	"Section 8", 
	"HOME TBRA", 
	"Other", 
	"No assistance", 
	"Vacant unit"
    }; 
    public final static String OCCUPANCY_ARR [] = {
	"",
	"Tenant", 
	"Owner",
	"Vacant"
    };
    public final static String BLOCK_ARR [] = {
	"",
	"1","2","3","4","5","6","7","8","9"
    };
    public final static String CENSUS_ARR [] = {
	"",
	"1.00",
	"2.01",
	"2.02",
	"3.01",
	"3.02",
	"4.01",
	"4.02",
	"5.01",
	"5.02",
	"6.00",
	"7.00",
	"8.00",
	"9.01",
	"9.03",
	"9.04",
	"10.01",
	"10.02",
	"11.01",
	"11.02",
	"11.03",
	"12.00",
	"13.01",
	"14.02",
	"15.00",
	"16.00",
	"20.33"
    };
    public final static String ER_ID_ARR [] = {
	"",
	"EHR","HMAL","OOR","PR","TBRA","HAND","SS","RR"
    };
    public final static String ER_ARR [] = {
	"",
	"EHR Exempt 24 CFR  58.34 (a) (10)",
	"HMAL Categorically Excluded 24 CFR 58.35 (a) (2)",
	"OOR Categorically Excluded 24 CFR 58.35 (a) (4)",
	"PR Categorically Excluded 24 CFR 58.35 (a) (4)",
	"TBRA Categorically Excluded 24 CFR 58.35  (a) (1)",
	"HAND DP & CC Categorically Excluded 24 CFR 58.35 (b0 (5)",
	"Social Service funding Exempt 24 CFR 58.34 (a) (4)",
	"Rental Rehab Single Family 58.35 (a) (4)"

    };
    public final static String NATION_OBJ_ARR [] = {
	"",
	"570.208(a)(1) Low Mod Area Benefit",
	"570.208(a)(2) Limited Clientele",
	"570.208(a)(3) Low/Mod Housing"
    };

    public final static String ELIGIBLE_ACT_ARR [] = {
	"",
	"570.201(n)",
	"570.202(1)(a)"
    };
    public final static String BENIFICIARY_TYPE_ARR [] = {
	"",
	"Elderly",
	"Small Related 2-4",
	"Large Related 5+",
	"Other Household"
    };

    /**
     * Generates the HelpDesk form and processes view, add, update and delete
     * operations.
     * @param req
     * @param res
     */
    
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	doPost(req,res);
    }

    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException {
    
	String lid="",cid="0",pid="0",received="",ptype="",
	    ptype_other="",contract_date="",dir_amount="",dir_rate="",
	    defer_amount="",cond_amount="",cond_term_other="",
	    appl_date="",draw_date="", fileId="",l_grant="",
	    appl_dir_date="", land_lord="",
	    cond_set_date="",dir_set_date="",satisfied="",appraised_val="",
	    sale_price="",fund_type="",notes="",cont_date_today="",ltv="",
	    fund_def_type="", gen_match="", match_amnt="", pay_off="",
	    pay_off_date="", status="";

	String lateFee ="", periods="0", terms="0", owner="", mortgage="",
	    startDate="", deposit_acc="";
	String payment="", principal_bal="";// info only
	int cond_term=0;

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String name, value, action="", message="";

	List<Client> allClients = null;
	List<Property> allProps = null;

	boolean success = true, wizard=false;
	Enumeration values = req.getParameterNames();
	String [] vals;
	Loan ll = new Loan();
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("lid")){
		ll.setLid(value);
		lid = value;
	    }
	    else if (name.equals("fund_type")) {
		ll.setFund_type(value);
		fund_type = value;
	    }
	    else if (name.equals("inactive")) {
		ll.setStatus(value);
		status = value;
	    }						
	    else if (name.equals("fund_def_type")) {
		ll.setFund_def_type(value);
		fund_def_type = value;
	    }
	    else if (name.equals("cond_term")) {
		ll.setCond_term(value);
		if(!value.equals("")){
		    try{
			cond_term= Integer.parseInt(value);
		    }catch(Exception ex){
			System.err.println(ex);
		    }
		}
	    }
	    else if (name.equals("cid")){
		ll.setCid(value);
		cid =value;
	    }
	    else if (name.equals("pid")){
		ll.setPid(value);
		pid =value;
	    }
	    else if (name.equals("gen_match")){
		ll.setGen_match(value);
		gen_match =value;
	    }
	    else if (name.equals("match_amnt")){
		ll.setMatch_amnt(value);
		match_amnt =value;
	    }
	    else if (name.equals("land_lord")){
		ll.setLand_lord(value);
		land_lord =value;
	    }
	    else if (name.equals("principal_bal")){
		ll.setPrincipal_bal(value);
		principal_bal=value;
	    }
	    else if (name.equals("payment")){
		ll.setPayment(value);
		payment =value;
	    }
	    else if (name.equals("l_grant")){
		ll.setL_grant(value);
		l_grant =value;
	    }
	    else if (name.equals("pay_off")){
		ll.setPay_off(value);
		pay_off=value;
	    }
	    else if (name.equals("pay_off_date")){
		ll.setPay_off_date(value);
		pay_off_date=value;
	    }
	    else if (name.equals("wizard")){
		wizard =true;
	    }
	    else if (name.equals("fileId")){
		ll.setFileId(value);
		fileId =value;
	    }
	    else if (name.equals("mortgage")){
		ll.setMortgage(value);
		mortgage =value;
	    }
	    else if (name.equals("cont_date_today")){
		ll.setCont_date_today(value);
		cont_date_today = value;
	    }
	    else if (name.equals("received")){
		ll.setReceived(value);
		received =value;
	    }
	    else if (name.equals("ptype")){
		ll.setPtype(value);
		ptype = value;
	    }
	    else if (name.equals("notes")){
		ll.setNotes(value);
		notes = value;
	    }
	    else if (name.equals("ltv")){
		ll.setLtv(value);
		ltv = value;
	    }
	    else if (name.equals("ptype_other")){
		ll.setPtype_other(value);
		ptype_other =value;
	    }
	    else if (name.equals("contract_date")){
		ll.setContract_date(value);
		contract_date =value;
	    }
	    else if (name.equals("appl_date")){
		ll.setAppl_date(value);
		appl_date =value;
	    }
	    else if (name.equals("appl_dir_date")){
		ll.setAppl_dir_date(value);
		appl_dir_date =value;
	    }
	    else if (name.equals("draw_date")){
		ll.setDraw_date(value);
		draw_date =value;
	    }
	    else if (name.equals("dir_amount")){
		ll.setDir_amount(value);
		dir_amount =value;
	    }
	    else if (name.equals("dir_rate")){
		ll.setDir_rate(value);
		dir_rate = value;
	    }
	    else if (name.equals("defer_amount")){
		ll.setDefer_amount(value);
		defer_amount =value;
	    }
	    else if (name.equals("cond_amount")){
		ll.setCond_amount(value);
		cond_amount =value;
	    }
	    else if (name.equals("cond_term_other")){
		ll.setCond_term_other(value);
		cond_term_other =value;
	    }
	    else if (name.equals("cond_set_date")){
		ll.setCond_set_date(value);
		cond_set_date =value;
	    }
	    else if (name.equals("dir_set_date")){
		ll.setDir_set_date(value);
		dir_set_date =value;
	    }
	    else if (name.equals("satisfied")){
		ll.setSatisfied(value);
		satisfied =value;
	    }
	    else if (name.equals("appraised_val")){
		ll.setAppraised_val(value);
		appraised_val=value;
	    }
	    else if (name.equals("sale_price")){
		ll.setSale_price(value);
		sale_price = value;
	    }
	    else if (name.equals("periods")){
		ll.setPeriods(value);
		periods = value;  
	    }
	    else if (name.equals("terms")){
		ll.setTerms(value);
		terms = value;  
	    }
	    else if (name.equals("lateFee")){
		ll.setlateFee(value);
		lateFee = value;  
	    }
	    else if (name.equals("deposit_acc")){
		ll.setDeposit_acc(value);
		deposit_acc = value;  
	    }
	    else if (name.equals("startDate")){
		ll.setStartDate(value);
		startDate = value;  
	    }
	    else if (name.equals("action")){ 
		action = value;  
	    }
	    else if (name.equals("action2")){
		if(!value.equals(""))
		    action = value;  
	    }						
	}
	//
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
	// Prepare the database connection
	//
	//
	// Get the list of clients and props
	//
	if(allClients == null){
	    ClientList cll = new ClientList();
	    String msg = cll.find();
	    if(msg.isEmpty()){
		allClients = cll.getClients();
	    }
	    List<Property> properties;
	    PropertyList pll = new PropertyList();
	    msg = pll.find();
	    if(msg.isEmpty()){
		allProps = pll.getProperties();
	    }
	}

	if(!cont_date_today.equals("")){
	    contract_date = Helper.getToday();
	    ll.setContract_date(contract_date);
	}
	if(action.equals("Save")){
	    //
	    if(user.canEdit()){
		message = ll.doSave();
		if(message.isEmpty()){
		    lid = ll.getLid();
		    message += " Data saved successfully ";
		}
	    }
	    else{
		success = false;
		message += " You can not Update data ";
	    }
	}
	else if(action.equals("Update")){
	    //
	    if(user.canEdit()){
		message = ll.doUpdate();
		if(message.isEmpty()){
		    message += " Data updated successfully ";
		}
	    }
	    else{
		success = false;
		message += " You can not Update data ";
	    }
	}
	else if(action.endsWith("Mortgage")){ // delete mortgage
	    //
	    message = ll.deleteMortgage();
	    if(message.isEmpty()){
		message = "Mortgage delete successfully";
	    }
	}
	else if(action.equals("Delete")){
	    //
	    if(user.canDelete()){
		message = ll.doDelete();
		if(message.isEmpty()){
		    message = " Deleted successfully ";
		    ll = new Loan();
		}
	    }
	    else{
		success = false;
		message += " You can not delete data ";
	    }
	    lid="";cid="";pid="0";received="";ptype=""; land_lord="";
	    ptype_other="";contract_date="";dir_amount="";dir_rate="";
	    defer_amount="";cond_amount="";cond_term_other="";
	    cond_set_date="";dir_set_date="";satisfied="";appraised_val="";
	    sale_price="";notes=""; ltv="";appl_date="";draw_date="";
	    fund_type="";cond_term=0;fileId="";appl_dir_date="";
	    terms="0"; periods="0"; lateFee=""; deposit_acc="";
	    startDate="";mortgage="";l_grant="";fund_def_type="";
	    gen_match="";match_amnt="";pay_off="";pay_off_date="";
	    status="";
	}
	else if(!lid.isEmpty()){	
	    message = ll.doSelect();
	    if(message.isEmpty()){
		pid = ll.getPid();
		cid = ll.getCid();
		message = ll.findCurrentBalance();
		if(message.isEmpty()){
		    principal_bal = ll.getPrincipal_bal();
		}
	    }
	}
	else if(action.startsWith("New")){	
	    //
	    ll = new Loan();
	    lid="";cid="";pid="0";received="";ptype="";
	    ptype_other="";contract_date="";dir_amount="";dir_rate="";
	    defer_amount="";cond_amount="";cond_term_other="";
	    cond_set_date="";dir_set_date="";satisfied="";appraised_val="";
	    sale_price="";notes=""; ltv="";appl_date="";draw_date="";
	    fund_type="";cond_term=0;fileId="";l_grant="";appl_dir_date="";
	    fund_def_type=""; land_lord="";gen_match="";match_amnt="";
	    pay_off="";pay_off_date=""; status="";
	}
	//
	// check boxes
	//
	if(!ll.getGen_match().equals("")) gen_match="checked=\"checked\"";
	else gen_match="";
	//
	out.println("<html><head><title>Help Desk</title>              ");
	Helper.writeWebCss(out, url);
	out.println("<script language=Javascript>                   ");
	out.println("  function checkDate(dt){		               ");
	out.println("    var dd = dt;                                  ");
	out.println("   if(dd.length == 0) return true;                "); 
	out.println("   var dar = dd.split(\"/\");                     "); 
	out.println(" if(dar.length < 3){                              ");
	out.println(" alert(\"Not a valid date: \"+dd);                ");
	out.println("      return false;}                              ");
	out.println("   var m = dar[0];                                ");
	out.println("   var d = dar[1];                                ");
	out.println("   var y = dar[2];                                ");
	out.println("   if(isNaN(m) || isNaN(d) || isNaN(y)){          ");
	out.println(" alert(\"Not a valid date: \"+dd);                ");
	out.println("      return false; }                             ");
	out.println("   if( !((m > 0 && m < 13) && (d > 0 && d <32) && ");
	out.println("    (y > 1970 && y < 2099))){                     "); 
	out.println(" alert(\"Not a valid date: \"+dd);                ");
	out.println("      return false; }                             ");
	out.println("    return true;                                  ");
	out.println("    }                                             ");
	out.println("  function validateForm(){	                       ");
	// 
	// check if loan type is selected
	//
	out.println("  if (document.myForm.fund_def_type.options[document.myForm.fund_def_type.selectedIndex].text == \"\"){ ");
	out.println("  alert(\"Please set loan funding type\"); ");
	out.println("     document.myForm.fund_def_type.focus(); ");
	out.println("     return false;}                  ");
	// checking numeric values
	out.println("  if (document.myForm.dir_amount.value.length>0){ ");
	out.println("  if (isNaN(document.myForm.dir_amount.value)){   ");
	out.println(" alert(\"Direct loan amount is not a valid number.\");");
	out.println("  	 document.myForm.dir_amount.focus();           ");
	out.println("     return false;			               ");
	out.println("	}}					       ");
	out.println("  if (document.myForm.dir_rate.value.length>0){   ");
	out.println("  if (isNaN(document.myForm.dir_rate.value)){     ");
	out.println(" alert(\"Direct loan interest rate is not a valid number.\");");
	out.println("  	 document.myForm.dir_amount.focus();            ");
	out.println("     return false;			                ");
	out.println("	}}					        ");
	out.println("  if (document.myForm.cond_amount.value.length>0){ ");
	out.println("  if (isNaN(document.myForm.cond_amount.value)){   ");
	out.println(" alert(\"Conditional loan amount is not a valid number.\");");
	out.println("  	 document.myForm.cond_amount.focus();            ");
	out.println("     return false;			                 ");
	out.println("	}}					         ");
	out.println("  if (document.myForm.defer_amount.value.length>0){ ");
	out.println("  if (isNaN(document.myForm.defer_amount.value)){   ");
	out.println(" alert(\"Defered loan amount is not a valid number.\");");
	out.println("  	 document.myForm.defer_amount.focus();           ");
	out.println("     return false;			                 ");
	out.println("	}}					         ");
	out.println("  if (document.myForm.appraised_val.value.length>0){");
	out.println("  if (isNaN(document.myForm.appraised_val.value)){  ");
	out.println(" alert(\"Appraised value is not a valid number.\"); ");
	out.println("  	 document.myForm.appraised_val.focus();          ");
	out.println("     return false;			                 ");
	out.println("	}}					         ");
	out.println("  if (document.myForm.sale_price.value.length>0){   ");
	out.println("  if (isNaN(document.myForm.sale_price.value)){     ");
	out.println(" alert(\"Sale price is not a valid number.\");      ");
	out.println("  	 document.myForm.sale_price.focus();             ");
	out.println("     return false;			                 ");
	out.println("	}}					         ");
	out.println("  if (document.myForm.mortgage.value.length>0){     ");
	out.println("  if (isNaN(document.myForm.mortgage.value)){       ");
	out.println(" alert(\"Total mortgage is not a valid number.\");  ");
	out.println("  	 document.myForm.mortgage.focus();               ");
	out.println("     return false;			                 ");
	out.println("	}}					         ");
	out.println("  if (document.myForm.l_grant.value.length>0){     ");
	out.println("  if (isNaN(document.myForm.l_grant.value)){       ");
	out.println(" alert(\"Grant is not a valid number.\");  ");
	out.println("  	 document.myForm.l_grant.focus();               ");
	out.println("     return false;			                 ");
	out.println("	}}					         ");
	out.println("  if (document.myForm.pay_off.value.length>0){     ");
	out.println("  if (isNaN(document.myForm.pay_off.value)){       ");
	out.println(" alert(\"Grant is not a valid number.\");  ");
	out.println("  	 document.myForm.pay_off.focus();               ");
	out.println("     return false;			                 ");
	out.println("	}}					         ");
	out.println("  if (document.myForm.match_amnt.value.length>0){     ");
	out.println("  if (isNaN(document.myForm.match_amnt.value)){       ");
	out.println(" alert(\"Match amount is not a valid number.\");  ");
	out.println("  	 document.myForm.match_amnt.focus();               ");
	out.println("     return false;			                 ");
	out.println("	}}					         ");
	//
	// checking dates
	out.println(" if(!checkDate(document.myForm.contract_date.value)) return false;	");
	out.println(" if(!checkDate(document.myForm.cond_set_date.value)) return false;	");
	out.println(" if(!checkDate(document.myForm.dir_set_date.value)) return false; ");
	out.println(" if(!checkDate(document.myForm.appl_date.value)) return false; ");
	out.println(" if(!checkDate(document.myForm.appl_dir_date.value)) return false; ");
	out.println(" if(!checkDate(document.myForm.draw_date.value)) return false; ");
	out.println(" if(!checkDate(document.myForm.startDate.value)) return false; ");
	out.println(" if(!checkDate(document.myForm.pay_off_date.value)) return false; ");
	out.println("     return true;				         ");
	out.println("	}	         			         ");
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
	out.println("  function firstFocus(){                            ");
	out.println("     document.myForm.dir_amount.focus();            ");
	out.println("	}			       	                 ");
	out.println("  function verifyMortgage(){                        ");
	out.println("  if (document.myForm.startDate.value.length==0){   ");
	out.println(" alert(\"Start date is required.\");                ");
	out.println("  	 document.myForm.startDate.focus();              ");
	out.println("     return false;			                 ");
	out.println("	}					         ");
	out.println("  if (document.myForm.dir_amount.value.length==0){  ");
	out.println(" alert(\"Direct loan amount is required.\");        ");
	out.println("  	 document.myForm.dir_amount.focus();             ");
	out.println("     return false;			                 ");
	out.println("	}					         ");
	out.println("  if (document.myForm.terms.value.length==0){       ");
	out.println(" alert(\"Terms field is required.\");               ");
	out.println("  	 document.myForm.terms.focus();                  ");
	out.println("     return false;			                 ");
	out.println("	}					         ");
	out.println("  if (document.myForm.periods.value.length==0){     ");
	out.println(" alert(\"Periods field is required.\");             ");
	out.println("  	 document.myForm.startDate.focus();              ");
	out.println("     return false;			                 ");
	out.println("	}					         ");
	out.println("     return true;			                 ");
	out.println("	}					         ");
				
	out.println(" </script>				                 ");
	out.println(" </head><body onload=\"firstFocus()\" ><center>   ");
	Helper.writeTopMenu(out, url);	
	//
	// delete startNew
	if(lid.equals("")){
	    out.println("<h2>New Loan </h2>");
	}
	else { // zoom, update, add
	    out.println("<h2>Edit Loan</h2>");
	}
	if(!message.equals("")){
	    if(success)
		out.println("<h3>"+message +"</h3>");
	    else
		out.println("<p><font color=red>"+message +"</font></p>");
	}
	out.println("<form name=myForm method=post "+
		    "onSubmit=\"return validateForm()\">");
	if(!lid.equals("")){
	    out.println("<input type=hidden name=lid value=\""+lid+"\" />");
	    out.println("<input type=hidden name=action2 value=\"\" />");
	}
	//
	out.println("<table border width=95%>");
	out.println("<tr><td align=center bgcolor="+bgcolor+">");
	//
	// Add/Edit record
	//
	out.println("<table width=100%>");
	out.println("<tr><td>");
	out.println("<tr><td>");
	if(!lid.equals("")){
	    out.println("<b>Loan ID:</b>"+lid);
	    out.println("&nbsp;&nbsp;");
	}
	if(wizard){
	    out.println("<input type=hidden name=wizard value=y>");
	}
	out.println("<b>File ID:</b><input name=fileId value=\""+
		    ll.getFileId()+"\" size=12 maxlength=20>");
	out.println("</td></tr>");
	out.println("<tr><td><b>Loan type:</b>");
       	out.println(" <select name=ptype size=1>");
	//
	for(int i=0; i<LOAN_TYPE_ARR.length; ++i){
	    if(ll.getPtype().equals(LOAN_TYPE_ARR[i]))
		out.println("<option selected>" + LOAN_TYPE_ARR[i]);
	    else
		out.println("<option>" + LOAN_TYPE_ARR[i]);		
	}
	out.println("</select>");
	out.println("&nbsp;&nbsp;<b> Other </b>specify:");
	out.println("<input name=ptype_other size=20 "+
		    "value=\""+ll.getPtype_other()+
		    "\" maxlength=20></td></tr>");
	//
       	if(cid.equals("0") || cid.isEmpty() || pid.isEmpty()
	   || pid.equals("0")){
	    out.println("<tr><td><font color=green size=-1>"+
			" Select client and "+
			" property from lists below. If not in the list "+
			" add them as New Client and/or New Property using "+
			" the left panel.</td></tr>");
	}
	//
	// Client
	out.println("<tr><td><b>Client:</b>");
       	out.println(" <select name=cid>");
	if(allClients != null){
	    for(Client one:allClients){
		if(cid.equals(one.getCid()))
		    out.println("<option selected value="+
				one.getCid()+">" + one.getFullName());
		else
		    out.println("<option value="+
				one.getCid()+">" + one.getFullName());
	    }
	}
	out.println("</select>");
	//
	// client link
	if(!(cid.isEmpty() || cid.equals("0"))){
	    out.println("<a href="+url+"Client?cid="+cid+
			">Client Info</a>");
	}
	out.println("</td></tr>");
	//
	// Property
	out.println("<tr><td><b>Property:</b>");
       	out.println(" <select name=pid>");
	if(allProps != null){
	    for(Property one:allProps){
		if(pid.equals(one.getPid()))
		    out.println("<option selected value="+
				one.getPid()+">" +
				one.getAddress());
		else
		    out.println("<option value="+
				one.getPid()+">" +
				one.getAddress());		
	    }
	}
	out.println("</select> ");
	//
	// Property info
	if(!(pid.isEmpty() || pid.equals("0"))){
	    out.println("<a href="+url+"Property?pid="+pid+
			"&action=zoom>Propety Info</a>");
	}
	out.println("</td></tr>");
	//
	// Application date
	out.println("<tr><td><b>Date of Application: </b>");
	out.println("<input name=appl_date size=10 "+
		    "value=\""+ll.getAppl_date()+"\" maxlength=10></td></tr>");
	//
	// Contract date
	out.println("<tr><td><b>Contract date: </b>");
	out.println("<input name=contract_date size=10 "+
		    "value=\""+ll.getContract_date()+"\" maxlength=10>&nbsp;");
	//
	// Final draw date
	out.println("<b>Final draw date: </b>");
	out.println("<input name=draw_date size=10 "+
		    "value=\""+ll.getDraw_date()+"\" maxlength=10></td></tr>");
	//
	// Deferred loan amount
	out.println("<tr><td><b>Deferred loan amount:</b>");
	out.println("<input type=text name=defer_amount size=8 "+
		    "value=\""+ll.getDefer_amount()+
		    "\" maxlength=8>&nbsp;&nbsp;");
	//
	out.println("<b> Funding type:</b> ");
	out.println("<select name=fund_def_type>");
	for(int i=0; i<FUND_TYPE_ARR.length; ++i){
	    if(ll.getFund_def_type().equals(FUND_TYPE_ARR[i]))
		out.println("<option selected>" + FUND_TYPE_ARR[i]);
	    else
		out.println("<option>" + FUND_TYPE_ARR[i]);		
	}
	out.println("</select> </td></tr> ");
	//
	// Conditional loan amount
	out.println("<tr><td><b>Conditional loan amount:</b>");
	out.println("<input type=text name=cond_amount size=8 "+
		    "value=\""+ll.getCond_amount()+
		    "\" maxlength=8></td></tr>");
	//
	// Conditional loan term
	out.println("<tr><td><b>Conditional loan terms:</b>");
	out.println("<select name=cond_term>");
	for(int i=0; i<COND_TERM_ARR.length; ++i){
	    if(ll.getCond_term() == i)
		out.println("<option selected value="+i+">" +
			    COND_TERM_ARR[i]);
	    else
		out.println("<option value="+i+">" +
			    COND_TERM_ARR[i]);		
	}
	out.println("</select> <b>other</b>");
	out.println("<input type=text name=cond_term_other size=20 "+
		    "value=\""+ll.getCond_term_other()+
		    "\" maxlength=30></td></tr>");
	//
	// Conditional loan settlement date 
	out.println("<tr><td><b>Conditional loan settlement date:</b>");
	out.println("<input type=text name=cond_set_date maxlength=10 "+
		    "size=10 value=\""+ll.getCond_set_date()+"\"></td></tr>");
	//
	// Grant amount
	out.println("<tr><td><b>Grant amount:</b>");
	out.println("<input type=text name=l_grant size=8 "+
		    "value=\""+ll.getL_grant()+
		    "\" maxlength=8>, ");
	out.println("<input type=checkbox name=gen_match value=\"y\" "+
		    gen_match+">");
	out.println("<b>Generate Match, Match Amount:</b> ");
	out.println("<input type=text name=match_amnt size=6 "+
		    "value=\""+ll.getMatch_amnt()+
		    "\" maxlength=6></td></tr>");
	out.println("</table></td></tr>");
	//
	out.println("<tr><td><table width=100%>");
	out.println("<tr><td align=center><b> Direct Loan Info "+
		    "</b></td></tr>");
	// 
	// It should not be in any of these states to show the message
	//
	if(lid.isEmpty()){
	    out.println("<tr><td align=center><font color=green> The "+	       
			"`following fields are used to generate the "+
			"Mortgage details. <br>"+
			"Every time a change is made to any one of these, "+
			"you need to click on \"Update\" <br>"+
			"before clicking on \" Mortgage Details\""+
			"</font></td></tr>");
	}
	//
	// Direct loan amount
	out.println("<tr><td><b>Direct loan amount:</b>");
	out.println("<input type=text name=dir_amount size=12 "+
		    "value=\""+ll.getDir_amount()+"\" maxlength=12>");
	//
	// Direct Loan Appl date
	out.println("<b> Application Date: </b>");
	out.println("<input type=text name=appl_dir_date maxlength=10 "+
		    "size=10 value=\""+ll.getAppl_dir_date()+"\">&nbsp;&nbsp;");
	//
	// Direct loan rate
	out.println("<b> Interest rate: </b>");
	out.println("<input type=text name=dir_rate maxlength=6 "+
		    "size=6 value=\""+ll.getDir_rate()+"\"></td></tr>");
	//
	// Start payment date
	out.println("<tr><td><b>Start payment date: </b>");
	out.println("<input type=text name=startDate size=10 "+
		    "value=\""+ll.getStartDate()+
		    "\" maxlength=10> <b>Late payment fees:</b>");
	//
	// Late fees
	out.println("<input type=lateFee name=lateFee size=10 "+
		    "value=\""+ll.getLateFee()+
		    "\" maxlength=20> &nbsp;</td></tr>"); 
	//
	// Terms
	out.println("<tr><td><b>Terms: </b>(years)");
	out.println("<input type=text name=terms size=3 "+
		    "value=\""+ll.getTerms()+"\" maxlength=4> &nbsp;"); 
	//
	// Periods
	out.println("<b>Periods: </b>(per year)");
	out.println("<input type=text name=periods size=3 "+
		    "value=\""+ll.getPeriods()+"\" maxlength=4> </td></tr>"); 
	//
	// Deposit account
	out.println("<tr><td>");
	out.println("<b>Deposit Account: </b>");
	out.println("<input type=text name=deposit_acc size=20 "+
		    "value=\""+ll.getDeposit_acc()+
		    "\" maxlength=20>&nbsp;&nbsp;"); 
	//
	// Direct loan settlement date 
	out.println("<b>Direct loan settlement date: </b>");
	out.println("<input type=text name=dir_set_date maxlength=10 "+
		    "size=10 value=\""+ll.getDir_set_date()+"\"></td></tr>");
	//
	if(!ll.getPayment().equals("")){
	    out.println("<tr><td><b>Monthly payment: $</b>");
	    out.println("<input type=text name=payment maxlength=8 value=\"");
	    out.println(ll.getPayment());
	    out.println("\" size=8> &nbsp;&nbsp;<b>Principal Balance: $</b>");
	    out.println(ll.getPrincipal_bal());
	    out.println("</td></tr>");
	    out.println("<input type=hidden name=principal_bal value="+
			ll.getPrincipal_bal()+">");
	}
	out.println("<tr><td>");
	out.println("<b>Land Lord: </b>");
	out.println("<input type=text name=land_lord size=30 "+
		    "value=\""+ll.getLand_lord()+
		    "\" maxlength=30></td></tr>"); 
	out.println("<tr><td>&nbsp;</td></tr>"); // separator
	out.println("</table></td></tr>"); // end direct loan table
	//
	out.println("<tr><td><table width=100%>");
	//
	// Loan satisfied
	out.println("<tr><td><b>Mortgage satisfied: </b>");
	out.println("<select name=satisfied>");
	for(int i=0; i<YES_NO_ARR.length; ++i){
	    if(ll.getSatisfied().equals(YES_NO_ARR[i]))
		out.println("<option selected>" + YES_NO_ARR[i]);
	    else
		out.println("<option>" + YES_NO_ARR[i]);		
	}
	out.println("</select> <b> Funding type:</b> ");
	out.println("<select name=fund_type>");
	for(int i=0; i<FUND_TYPE_ARR.length; ++i){
	    if(fund_type.equals(FUND_TYPE_ARR[i]))
		out.println("<option selected>" + FUND_TYPE_ARR[i]);
	    else
		out.println("<option>" + FUND_TYPE_ARR[i]);		
	}
	out.println("</select> </td></tr> ");
	//
	// Appraised value 
	out.println("<tr><td><b>Appraised value: </b>");
	out.println("<input type=text name=appraised_val maxlength=10 "+
		    "size=10 value=\""+ll.getAppraised_val()+
		    "\"> &nbsp;&nbsp;<b> Total Mortgage:</b>");
	//
	// Total mortgage (include all mortgages 1st, 2nd, ..)
	out.println("<input type=text name=mortgage maxlength=10 "+
		    "size=10 value=\""+ll.getMortgage()+
		    "\"> &nbsp;&nbsp;<b> Sale price:</b>");
	//
	// sale price
	out.println("<input type=text name=sale_price maxlength=10 "+
		    "size=10 value=\""+ll.getSale_price()+"\"> </td></tr>");
	//
	// LTV
	out.println("<tr><td><b>LTV: </b>");
	out.println("<input type=text name=ltv maxlength=6 "+
		    "size=6 value=\""+ll.getLtv()+"\">%</td></tr>");
	//
	// Pays Off
	out.println("<tr><td><b>Pays Off, Amount: </b>");
	out.println("<input type=text name=pay_off maxlength=10 "+
		    "size=10 value=\""+ll.getPay_off()+
		    "\"> &nbsp;&nbsp;<b> Date: </b>");
	out.println("<input type=text name=pay_off_date maxlength=10 "+
		    "size=10 value=\""+ll.getPay_off_date()+
		    "\"></td></tr>");
	out.println("<tr><td><b>Status? </b>");
				
	String checked="";
	if(ll.getStatus().isEmpty()) checked="checked=\"checked\""; 
	out.println("<input type=\"radio\" name=\"inactive\" value=\"\" "+checked+
		    " />Active ");
	checked = "";
	if(!ll.getStatus().isEmpty()) checked="checked=\"checked\""; 
	out.println("<input type=\"radio\" name=\"inactive\" value=\"y\" "+checked+
		    " />Inactive</td></tr>");				
	//
	// notes
	out.println("<tr><td valign=top><br>"+
		    "<b>Notes </b><Font color=green size=1> "+
		    "Maximum number of characters is 250</Font><br>" +
		    "<textarea rows=3 cols=50 wrap "+
		    "name=notes>"+ ll.getNotes() +"</textarea></td></tr>");
	//
	out.println("<tr><td><hr></td></tr>");
	//
	// submit
	if(lid.equals("")){
	    if(user.canEdit()){
		out.println("<tr>");	
		out.println("<td align=right><input type=submit "+
			    "name=action value=Save>&nbsp;</td></tr>");
	    }
	}
	else{ // save, update
	    out.println("<tr><td valign=top align=\"center\"><table width=\"80%\"><tr>");
	    if(user.canEdit()){
		out.println("<td valign=top><input "+
			    "type=submit name=action value=Update></td>");
	    }
	    //
	    // restricted to admin
	    if(user.isAdmin()){
		out.println("<td valign=top><input "+
			    "type=submit name=action "+
			    "value=\"Delete Mortgage\"></td>");
	    }
	    out.println("<td valign=top><input "+
			"type=button name=action "+
			"onclick=\"if(verifyMortgage())document.location='"+
			url+"Mortgage?lid="+lid+
			"'\" value=\"Mortgage Details\">");
	    out.println("</form></td><td>");
	    //
	    if(wizard){
		out.println("<td valign=\"top\"><input type=\"button\" onclick=\"document.location='"+url+"Property?pid="+pid+"&cid="+cid+"&lid="+lid+"&action=zoom&wizard=y';return false;\" value=\"Back << Property Page\" /></td>");
	    }

	    out.println("<td valign=\"top\"><input type=\"button\" onclick=\"document.location='"+url+"MediaUploadServ?obj_id="+lid+"&obj_type=Loan';return false;\" value=\"Attachment\" />");
	    if(user.canDelete()){
		out.println("<td valign=\"top\"><input type=\"button\" onclick=\"return validateDelete();\" value=\"Delete\" /></td>");
	    }						
	    out.println("</td>");						
	    out.println("</tr></table></center></td></tr>");
	}
	out.println("</table>");
	out.println("</td></tr></table>");
	out.println("</form>");

	if(!lid.equals("")){
	    MediaFileList mfl = new MediaFileList(debug, lid, "Loan");
	    String back = mfl.find();
	    if(back.equals("")){
		List<MediaFile> ones = mfl.getMediaFiles();
		Helper.writeMedia(ones, out, url);								
	    }
	}
	out.println("<br />");
	out.flush(); 
	out.print("</body></html>");
	out.flush();
	out.close();
    }
    //

	
}























































