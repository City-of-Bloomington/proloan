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
/**
 *
 *
 */
@WebServlet(urlPatterns = {"/Property"})
public class PropertyServ extends TopServlet{

    String bgcolor = LoanServ.bgcolor;
    static Logger logger = LogManager.getLogger(PropertyServ.class);
    //
    public void doGet(HttpServletRequest req, 
		      HttpServletResponse res) 
	throws ServletException, IOException {
	doPost(req,res);
    }
    //
    public void doPost(HttpServletRequest req, 
		       HttpServletResponse res) 
	throws ServletException, IOException {
    
	String pid="",street_num="",street_dir="",street_name="",
	    street_type="",post_dir="",sud_num="",sud_type="", cid="", lid="",
	    block_group="",hist_elegible="",national_reg="",units="",
	    accessible="",rev_106 = "", idis_no="",leverage="", notes="",
	    insur_expire ="",contract="",cont_addr="",census_tract="",
	    owner_type="", act_type="",chdo="",district="";
	String plan_ref="",
	    eligible_act="", eligible_act2="", 
	    reference="", 
	    nation_obj="", // list
	    envir_ref="",    // list
	    proj_manager=""; // list
	
	int bedrooms = 0,tenure_type = 0, nhood=0,ptype=0;
	String est_const_year="", action="";

	boolean userFoundFlag = false, wizard=false;
	boolean success = true;
	String message = "";

	res.setContentType("text/html");
	PrintWriter out = res.getWriter();
	String name, value;
	Touple nhoodArr[] = null;
	Enumeration values = req.getParameterNames();
	String [] vals;
	Property pp = new Property();
	while (values.hasMoreElements()){

	    name = ((String)values.nextElement()).trim();
	    vals = req.getParameterValues(name);
	    value = vals[vals.length-1].trim();	
	    if (name.equals("pid")){
		pid = value;
		pp.setPid(value);
	    }
	    else if (name.equals("ptype")) {
		pp.setPtype(value);
		if(!value.equals("")){
		    try{
			ptype = Integer.parseInt(value);
		    }catch(Exception ex){
			System.err.println(ex);
		    }
		}
	    }
	    else if (name.equals("street_num")) {
		pp.setStreet_num(value);
		street_num =value;
	    }
	    else if (name.equals("street_dir")) {
		pp.setStreet_dir(value);
		street_dir =value;
	    }
	    else if (name.equals("street_name")) {
		pp.setStreet_name(value);
		street_name =value;
	    }
	    else if (name.equals("street_type")) {
		pp.setStreet_type(value);
		street_type =value;
	    }
	    else if (name.equals("post_dir")) {
		pp.setPost_dir(value);
		post_dir =value;
	    }
	    else if (name.equals("sud_num")) {
		pp.setSud_num(value);
		sud_num =value;
	    }
	    else if (name.equals("sud_type")) {
		pp.setSud_type(value);
		sud_type =value;
	    }
	    else if (name.equals("act_type")) {
		pp.setAct_type(value);
		act_type =value;
	    }
	    else if (name.equals("owner_type")) {
		pp.setOwner_type(value);
		owner_type =value;
	    }
	    else if (name.equals("wizard")) {
		if(!value.equals(""))
		    wizard=true;
	    }
	    else if (name.equals("cid")){
		pp.setCid(value);
		cid = value;  
	    }
	    else if (name.equals("lid")){
		pp.setLid(value);
		lid = value;  
	    }
	    else if (name.equals("units")){
		pp.setUnits(value);
		units = value;  
	    }
	    else if (name.equals("hist_elegible")) {
		pp.setHist_elegible(value);
		hist_elegible =value;
	    }
	    else if (name.equals("national_reg")) {
		pp.setNational_reg(value);
		national_reg = value;
	    }
	    else if (name.equals("census_tract")) {
		pp.setCensus_tract(value);
		census_tract =value;
	    }
	    else if (name.equals("bedrooms")) {
		pp.setBedrooms(value);
		if(!value.equals("")){
		    try{
			bedrooms = Integer.parseInt(value);
		    }catch(Exception ex){};
		}
	    }
	    else if (name.equals("tenure_type")) {
		pp.setTenure_type(value);
		if(!value.equals("")){
		    try{
			tenure_type = Integer.parseInt(value);
		    }catch(Exception ex){};
		}
	    }
	    else if (name.equals("est_const_year")) {
		pp.setEst_const_year(value);
		if(!value.equals(""))
		    est_const_year = value;
	    }
	    else if (name.equals("nhood")) {
		pp.setNhood(value);
		if(!value.equals("")){
		    try{
			nhood = Integer.parseInt(value);
		    }catch(Exception ex){};
		}
	    }
	    else if (name.equals("district")) {
		pp.setDistrict(value);
		district = value;
	    }
	    else if (name.equals("block_group")){
		pp.setBlock_group(value);
		block_group = value;  
	    }
	    else if (name.equals("accessible")){
		pp.setAccessible(value);
		accessible = value;  
	    }
	    else if (name.equals("rev_106")){
		pp.setRev_106(value);
		rev_106 = value;  
	    }
	    else if (name.equals("idis_no")){
		pp.setIdis_no(value);
		idis_no = value;  
	    }
	    else if (name.equals("leverage")){
		pp.setLeverage(value);
		leverage = value;  
	    }
	    else if (name.equals("insur_expire")){
		pp.setInsur_expire(value);
		insur_expire = value;  
	    }
	    else if (name.equals("contract")){
		pp.setContract(value);
		contract = value;  
	    }
	    else if (name.equals("notes")){
		pp.setNotes(value);
		notes = value;  
	    }
	    else if (name.equals("chdo")){
		pp.setChdo(value);
		chdo = value;  
	    }
	    else if (name.equals("plan_ref")){
		pp.setPlan_ref(value);
		plan_ref = value;  
	    }
	    else if (name.equals("eligible_act")){
		pp.setEligible_act(value);
		eligible_act = value;  
	    }
	    else if (name.equals("eligible_act2")){
		pp.setEligible_act2(value);
		eligible_act2 = value;  
	    }
	    else if (name.equals("reference")){
		pp.setReference(value);
		reference = value;  
	    }
	    else if (name.equals("nation_obj")){
		pp.setNation_obj(value);
		nation_obj = value;  
	    }
	    else if (name.equals("envir_ref")){
		pp.setEnvir_ref(value);
		envir_ref = value;  
	    }
	    else if (name.equals("proj_manager")){
		pp.setProj_manager(value);
		proj_manager = value;  
	    }
	    else if (name.equals("cont_addr")){
		pp.setCont_addr(value);
		cont_addr = value;  
	    }
	    else if (name.equals("action")){ 
		action = value;  
	    }
	    else if (name.equals("action2")){
		if(!value.equals("")){
		    action = value;
		}
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
	    nhoodArr = pp.getAllNhood();
	}
		
	// Note
	if(!eligible_act2.equals("")){
	    eligible_act= eligible_act2;
	    eligible_act2 = "";
	    pp.setEligible_act(eligible_act);
	    pp.setEligible_act2(eligible_act2);
	}
	if(action.equals("Save")){
	    if(user.canEdit()){
		message = pp.doSave();
		pid = pp.getPid();
	    }
	}
	else if(action.equals("Update")){
	    //
	    if(user.canEdit()){
		message += pp.doUpdate();
	    }
	}
	else if(action.equals("Delete")){
	    //
	    if(user.canDelete()){
		message = pp.doDelete();
		pp = new Property();
	    }
	    pid="";street_num="";street_dir="";street_name="";
	    street_type="";post_dir="";sud_num="";sud_type="";
	    ptype=0; block_group="";hist_elegible="";national_reg="";
	    accessible="";rev_106 = ""; idis_no="";leverage="";
	    insur_expire =""; contract="";cont_addr="";units="";
	    bedrooms = 0;tenure_type = 0; nhood=0; district="";
	    est_const_year="";census_tract="";owner_type="";
	    act_type="";chdo="";plan_ref="";eligible_act="";
	    reference="";nation_obj="";proj_manager="";envir_ref="";
	}
	else if(!pid.isEmpty()){	
	    message = pp.doSelect();
	    cid = pp.getCid();
	    String ld = pp.getLid();
	    if(!ld.isEmpty())
		lid = ld;

	}
	//
	out.println("<html><head><title>ProLoan</title>");
	Helper.writeWebCss(out, url);	
	out.println("<script language=javascript>");
	out.println("  function validateForm(){		                ");
	out.println("  if((document.myForm.street_name.value.length==0)){");
	out.println("     alert(\"'Street Name' field is required.\");	");
	out.println("     return false;				       	");
	out.println("	}						");
	out.println("     return true;				    ");
	out.println("	}	         			    ");
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
	out.println("     document.myForm.street_num.focus();            ");
	out.println("	}			       		         ");
	out.println(" </script>				        ");
	out.println(" </head><body onload=\"firstFocus()\" >");
	Helper.writeTopMenu(out, url);
	//
	// delete startNew
	if(pid.equals("")){
	    out.println("<center><h2>New Property"+
			"</h2>");
	}
	else { // zoom, update, add
	    out.println("<center><h2>Check/Update Property</h2>");
	}
	if(!message.equals("")){
	    if(success)
		out.println("<h3>"+message+"</h3>");
	    else
		out.println("<p><font color=red>"+message+"</font></p>");	
	}
	out.println("<form name=myForm method=post "+
		    "onSubmit=\"return validateForm()\">");
	if(!pid.equals("")){
	    out.println("<input type=\"hidden\" name=\"pid\" value=\""+pid+"\">");
	    out.println("<input type=\"hidden\" name=\"action2\" value=\"\" >");
	}
	if(wizard){
	    out.println("<input type=hidden name=wizard value=y>");
	    out.println("<input type=hidden name=cid value=\""+cid+
			"\">");
	    if(!lid.equals(""))
		out.println("<input type=hidden name=lid value=\""+lid+
			    "\">");
	}
	//
	out.println("<table border width=\"95%\">");
	out.println("<tr><td align=\"center\" bgcolor=\""+bgcolor+"\">");
	//
	// Add/Edit record
	//
	out.println("<table width=\"100%\">");
	//the real table
	out.println("<tr><td><b>Property type:</b>");
	out.println("<select name=ptype>");
	for(int i=0;i<LoanServ.PROP_TYPE_ARR.length;i++){
	    if(pp.getPtype() == i)
		out.println("<option selected value="+i+
			    ">"+LoanServ.PROP_TYPE_ARR[i]);
	    else
		out.println("<option value="+i+
			    ">"+LoanServ.PROP_TYPE_ARR[i]);
	}
	out.println("</select>&nbsp; <b>Property ID:</b>"+pid+"</td></tr>");

	//
	// Project Manager 
	out.println("<tr><td><b>Project Managed by:</b></b>");
	out.println("<select name=proj_manager>");
	out.println("<option value=\"\"></option>");
	if(proj_managers != null && proj_managers.size() > 0){
	    for(User one:proj_managers){
		String selected = "";
		if(one.getId().equals(pp.getProj_manager())) selected = "selected=\"selected\"";
		if(selected.equals("") && !one.isActive()) continue;
		out.println("<option "+selected+" value=\""+one.getId()+"\">"+one+"</option>");
	    }
	}
	out.println("</select></td></tr>");
	out.println("<tr><td>Address</td></tr>");
	//
	// Street num
	out.println("<tr><td><b>Street num:</b>");
	out.println("<input type=text name=street_num maxlength=8 "+
		    "size=8  value=\"" + pp.getStreet_num() +
		    "\"> <b>dir:</b>");
	//
	// street dir 
	out.println(" <select name=street_dir size=1>");
	for(int i=0; i<LoanServ.DIR_ARR.length; ++i){
	    if(pp.getStreet_dir().equals( LoanServ.DIR_ARR[i]))
		out.println("<option selected>" +
			    LoanServ.DIR_ARR[i]);
	    else
		out.println("<option>" +
			    LoanServ.DIR_ARR[i]);		
	}
	out.println("</select> <b> name:</b>");
	out.println("<input type=text name=street_name maxlength=20 "+
		    "size=10  value=\"" +pp.getStreet_name() +
		    "\"> <b>type:</b>");
	//
	// street type 
	out.println(" <select name=street_type>");
	for(int i=0; i<LoanServ.STREET_KEY_ARR.length; ++i){
	    if(pp.getStreet_type().equals(LoanServ.STREET_KEY_ARR[i]))
		out.println("<option selected value=\"" +
			    pp.getStreet_type()+"\">"+LoanServ.STREET_TYPE_ARR[i]);
	    else
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
	    if(pp.getPost_dir().equals(LoanServ.DIR_ARR[i]))
		out.println("<option selected>" +
			    LoanServ.DIR_ARR[i]);
	    else
		out.println("<option>" +
			    LoanServ.DIR_ARR[i]);		
	}
	out.println("</select>");
	//
	// sud type
	//
	out.println(" <b>SUD type:</b>");
	out.println(" <select name=sud_type>");
	for(int i=0; i<LoanServ.SUD_KEY_ARR.length; ++i){
	    if(pp.getSud_type().equals(LoanServ.SUD_KEY_ARR[i]))
		out.println("<option selected value=\"" +
			    pp.getSud_type() + "\">"+LoanServ.SUD_TYPE_ARR[i]);
	    else
		out.println("<option value=\"" + LoanServ.SUD_KEY_ARR[i]+
			    "\">" + LoanServ.SUD_TYPE_ARR[i]);		
	}
	// sud num
	out.println("</select> <b>SUD num:</b>");
	out.println("<input type=text name=sud_num maxlength=8 "+
		    "size=8  value=\"" +pp.getSud_num() +
		    "\"></td></tr>");
	//
	// contractor
	out.println("<tr><td><b>Contractor: </b>");
	out.println("<input type=text name=contract size=40 "+
		    "value=\""+pp.getContract()+
		    "\" maxlength=40></td></tr>");
	//
	// contractor address
	out.println("<tr><td><b>Contractor Address: </b>");
	out.println("<input type=text name=cont_addr size=40 "+
		    "value=\""+pp.getCont_addr()+
		    "\" maxlength=50></td></tr>");
	//
	out.println("<tr><td><b>Tenure type: </b>");
	out.println("<select name=tenure_type>");
	for(int i=0;i<LoanServ.TENURE_ARR.length; ++i){
	    if(i == pp.getTenure_type()) // todo
		out.println("<option selected value="+i+">"+
			    LoanServ.TENURE_ARR[i]);
	    else
		out.println("<option value="+i+">"+
			    LoanServ.TENURE_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
	// construction year
	out.println("<tr><td><b> Estimated construction year:</b>");
	out.println("<input type=text name=est_const_year size=4 "+
		    "value=\""+pp.getEst_const_year()+
		    "\" maxlength=4> ");
	// Bedrooms
	out.println("<b>Bedrooms: </b>");
	out.println("<select name=bedrooms>");
	for(int i=0;i<LoanServ.BEDROOMS_ARR.length; ++i){
	    if(i == pp.getBedrooms()) // todo
		out.println("<option selected value="+i+">"+
			    LoanServ.BEDROOMS_ARR[i]);
	    else
		out.println("<option value="+i+">"+
			    LoanServ.BEDROOMS_ARR[i]);
	}
	out.println("</select>&nbsp;&nbsp;<b> Units:</b>");
	out.println("<input type=text name=units size=3 "+
		    "value=\""+pp.getUnits()+
		    "\" maxlength=3></td></tr>");
	out.println("<tr><td><b>District: </b>");
	out.println("<input type=text name=district size=2 "+
		    "value=\""+pp.getDistrict()+
		    "\" maxlength=2>&nbsp;&nbsp;");
	// idis num
	out.println("&nbsp;&nbsp;<b> IDIS #:</b>");
	out.println("<input type=text name=idis_no size=10 "+
		    "value=\""+pp.getIdis_no()+
		    "\" maxlength=10></td></tr>");
	//
	// Neighborhood
	out.println("<tr><td><b>Neighborhood: </b>");
	out.println("<select name=nhood>");
	if(nhoodArr != null){
	    for(int i=0;i<nhoodArr.length; ++i){
		if(nhoodArr[i].getId() == pp.getNhood())
		    out.println("<option selected value="+nhood+">"+
				nhoodArr[i].getName());
		else
		    out.println("<option value="+nhoodArr[i].getId()+">"+
				nhoodArr[i].getName());
	    }
	}
	out.println("</select></td></tr>");
	//
	// block group
	out.println("<tr><td><b> Block group:</b>");
	out.println("<select name=block_group>");
	for(int i=0;i<LoanServ.BLOCK_ARR.length; ++i){
	    if(pp.getBlock_group().equals(LoanServ.BLOCK_ARR[i]))
		out.println("<option selected>"+
			    LoanServ.BLOCK_ARR[i]);
	    else
		out.println("<option>"+
			    LoanServ.BLOCK_ARR[i]);
	    
	}
	out.println("</select> &nbsp;&nbsp;");
	//
	// Census tract
	out.println("<b> Census tract:</b>");
	out.println("<select name=census_tract>");
	for(int i=0;i<LoanServ.CENSUS_ARR.length; ++i){
	    if(pp.getCensus_tract().equals(LoanServ.CENSUS_ARR[i]))
		out.println("<option selected>"+
			    LoanServ.CENSUS_ARR[i]);
	    else
		out.println("<option>"+
			    LoanServ.CENSUS_ARR[i]);
	    
	}
	out.println("</select></td></tr>");
	//
	// Ownership type
	out.println("<tr><td><b>Ownership type:</b>");
	out.println("<select name=owner_type>");
	for(int i=0;i<LoanServ.OWNER_TYPE_ARR.length; ++i){
	    if(LoanServ.OWNER_TYPE_ARR[i].equals(pp.getOwner_type())){
		out.println("<option selected>"+
			    LoanServ.OWNER_TYPE_ARR[i]);
	    }
	    else
		out.println("<option>"+
			    LoanServ.OWNER_TYPE_ARR[i]);
	}
	out.println("</select>");
	//
	// Activity type
	out.println("&nbsp;&nbsp;<b>Activity type:</b>");
	out.println("<select name=act_type>");
	for(int i=0;i<LoanServ.ACTIVITY_TYPE_ARR.length; ++i){
	    if(LoanServ.ACTIVITY_TYPE_ARR[i].equals(pp.getAct_type())){
		out.println("<option selected>"+
			    LoanServ.ACTIVITY_TYPE_ARR[i]);
	    }
	    else
		out.println("<option>"+
			    LoanServ.ACTIVITY_TYPE_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
	// historic elegible
	out.println("<tr><td><b>Historically eligible?</b>");
	out.println("<select name=hist_elegible>");
	for(int i=0;i<LoanServ.YES_NO_ARR.length; ++i){
	    if(LoanServ.YES_NO_ARR[i].equals(pp.getHist_elegible())){
		out.println("<option selected>"+
			    LoanServ.YES_NO_ARR[i]);
	    }
	    else
		out.println("<option>"+
			    LoanServ.YES_NO_ARR[i]);
	}
	out.println("</select> &nbsp;<b>National register?</b>");
	// 
	// national reg
	out.println("<select name=national_reg>");
	for(int i=0;i<LoanServ.YES_NO_ARR.length; ++i){
	    if(LoanServ.YES_NO_ARR[i].equals(pp.getNational_reg())){
		out.println("<option selected>"+
			    LoanServ.YES_NO_ARR[i]);
	    }
	    else
		out.println("<option>"+
			    LoanServ.YES_NO_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
	// Annual plan ref
	out.println("<tr><td><b> Annual Plan Reference #: </b>");
	out.println("<input name=plan_ref value=\""+pp.getPlan_ref()+"\" size=10"+
		    " maxlength=10></td></tr>");
	//
	// Eligible Activity
	out.println("<tr><td>");
	out.println("<b> Eligible Activity:</b>");
	out.println("<select name=eligible_act>");
	out.println("<option selected>"+pp.getEligible_act()+"\n");
	for(int i=0;i<LoanServ.ELIGIBLE_ACT_ARR.length;i++){
	    out.println("<option>"+LoanServ.ELIGIBLE_ACT_ARR[i]);
	}
	out.println("</select>&nbsp;&nbsp;<b>Other</b>, specity");
	out.println("<input name=eligible_act2 value=\""+pp.getEligible_act2()+
		    "\" size=20 maxlength=30></td></tr>");
	//
	// Reference
	out.println("<tr><td><b> Reference: </b>");
	out.println("<input name=reference value=\""+pp.getReference()+
		    "\" size=20 maxlength=30>&nbsp;&nbsp;");
	//
	// National Objective
	out.println("<b> National Objective: </b>");
	out.println("<select name=nation_obj>");
	out.println("<option selected>"+pp.getNation_obj());
	for(int i=0;i<LoanServ.NATION_OBJ_ARR.length; ++i){
	    out.println("<option>"+LoanServ.NATION_OBJ_ARR[i]);
	}
	// Accessible
	out.println("<tr><td><b> Accessible? </b>");
	out.println("<select name=accessible>");
	for(int i=0;i<LoanServ.YES_NO_ARR.length; ++i){
	    if(LoanServ.YES_NO_ARR[i].equals(pp.getAccessible())){
		out.println("<option selected>"+
			    LoanServ.YES_NO_ARR[i]);
	    }
	    else
		out.println("<option>"+
			    LoanServ.YES_NO_ARR[i]);
	}
	out.println("</select> ");
	//
	// CHDO
	out.println("&nbsp;&nbsp;<b> CHDO:</b>");
	out.println("<select name=chdo>");
	for(int i=0;i<LoanServ.YES_NO_ARR.length; ++i){
	    if(LoanServ.YES_NO_ARR[i].equals(pp.getChdo())){
		out.println("<option selected>"+ LoanServ.YES_NO_ARR[i]);
	    }
	    else
		out.println("<option>"+ LoanServ.YES_NO_ARR[i]);
	}
	//
	// leverage
	// leverage was replaced by 1st Mortgage by Lisa 
	out.println("</select> &nbsp;&nbsp;<b> 1<sup>st</sup> Mortgage:</b>");
	out.println("<input type=text name=leverage size=10 "+
		    "value=\""+pp.getLeverage()+
		    "\" maxlength=10></td></tr>");
	//
	// 106 rev
	out.println("<tr><td><b> 106 review:</b>");
	out.println("<select name=rev_106>");
	for(int i=0;i<LoanServ.YES_NO_ARR.length; ++i){
	    if(LoanServ.YES_NO_ARR[i].equals(pp.getRev_106())){
		out.println("<option selected>"+
			    LoanServ.YES_NO_ARR[i]);
	    }
	    else
		out.println("<option>"+
			    LoanServ.YES_NO_ARR[i]);
	}
	out.println("</select><b> ER Reference:</b> ");
	out.println("<select name=envir_ref>");
	for(int i=0;i<LoanServ.ER_ID_ARR.length; ++i){
	    if(LoanServ.ER_ID_ARR[i].equals(pp.getEnvir_ref())){
		out.println("<option selected value=\""+pp.getEnvir_ref()+"\">"+
			    LoanServ.ER_ARR[i]);
	    }
	    else
		out.println("<option value=\""+LoanServ.ER_ID_ARR[i]+"\">"+
			    LoanServ.ER_ARR[i]);
	}
	out.println("</select></td></tr>");
	//
	// Insurance expires
	out.println("<tr><td><b> Insurance expires:</b>");
	out.println("<input type=text name=insur_expire size=10 "+
		    "value=\""+pp.getInsur_expire()+"\" maxlength=10>(mm/dd/yyyy)</td></tr>");
	//
	// Notes
	out.println("<tr><td><b>Description of Work</b>"+
		    "<Font color=green size=1>Up to 250 characters</Font><br>"+
		    "<textarea rows=3 cols=50 wrap name=notes>"+pp.getNotes() + 
		    "</textarea></td></tr>");
	//
	// submit
	if(pid.isEmpty()){
	    if(user.canEdit()){
		out.println("<tr><td></td><td align=right> "+
			    "<input type=submit name=action "+
			    "value=Save>&nbsp;&nbsp;&nbsp;</td></tr>");
	    }
	    out.println("</table></td></tr>");
	}
	else{ // save, update
	    out.println("</table></td></tr>");
	    out.println("<tr>");
	    out.println("<td align=\"center\">"+
			"<table width=\"80%\"><tr>");						
	    if(user.canEdit()){
		out.println("<td valign=\"top\"><input "+
			    "type=submit name=action "+
			    "value=Update>");
		out.println("</td>");
	    }
	    //
	    // unit form
	    out.println("<td valign=\"top\"><input type=\"button\" onclick=\"document.location='"+url+"Unit?pid="+pid+"';return false;\" value=\"Add New Occupant\" /></td>");								
	    out.println("<td valign=\"top\"><input type=\"button\" onclick=\"document.location='"+url+"LeakTestServ?pid="+pid+"';return false;\" value=\"Add Leak Test\" /></td>");										
	    if(wizard){
		//
		out.println("<td valign=\"top\"><input type=\"button\" onclick=\"document.location='"+url+"Client?cid="+cid+"&lid="+lid+"&action=zoom&wizard=y';return false;\" value=\"Back << Client Page\" /></td>");
		//
		out.println("<td valign=\"top\"><input type=\"button\" onclick=\"document.location='"+url+"Property?pid="+pid+"&cid="+cid+"&lid="+lid+"&action=zoom&wizard=y';return false;\" value=\"Next >> Property Page\" /></td>");
	    }
	    out.println("<td valign=\"top\"><input type=\"button\" onclick=\"document.location='"+url+"MediaUploadServ?obj_id="+pid+"&obj_type=Property';return false;\" value=\"Attachment\" /></td>");												
	    //
	    if(user.canDelete()){
		out.println("<td valign=\"top\"><input type=\"button\" onclick=\"return validateDelete();\" value=\"Delete\" /></td>");
	    }
	    out.println("</tr></table></td></tr>");
	}
	out.println("</table>");
	out.println("</form>");
	if(!pid.isEmpty()){
	    pp.findRelatedLoan();
	    if(pp.hasLoan()){
		out.print("<b>Loans associated with this property"+
			  "</b>:");
		String str = pp.getLid();;
		if(str != null && !str.equals("0")){
		    out.print("<a href="+url+"Loan?"+
			      "&lid="+str+
			      "&action=zoom>"+str+"</a>");
		    out.println("<br />");		    
		}		
	    }
	    else{
		out.print(", ");
		out.println("No Loan is  associated with this "+
			    " property.<br />");		
	    }
	}
	LeakTestList ltl = new LeakTestList(debug, pid);
	message = ltl.lookFor();
	if(message.equals("")){
	    if(ltl.size() > 0){
		out.println("<table border>"+
			    "<caption>Previous Leakage Tests</caption>"+
			    "<tr><th>&nbsp;</th><th>Date</th>"+
			    "<th>Before?After Rehab</th>"+
			    "<th>Blower Door Reading</th>"+
			    "<th>Flow Ring</th>"+
			    "<th>Notes</th></tr>");
		for(LeakTest tt:ltl){
		    out.println("<tr><td>"+
				"<a href="+url+"LeakTestServ?id="+tt.getId()+"&pid="+pid+"&action=zoom>Edit</a>"+								
				"</td><td>"+tt.getDate()+
				"</td><td>"+tt.getTestEvent()+
				"</td><td>"+tt.getBlowRead()+
				"</td><td>"+tt.getFlowRing()+
				"</td><td>"+tt.getNotes()+"</td></tr>");
		}
		out.println("</table>");
	    }
	}
	out.println("<br />");
	if(!pid.equals("")){
	    MediaFileList mfl = new MediaFileList(debug, pid, "Property");
	    String back = mfl.find();
	    if(back.equals("")){
		List<MediaFile> ones = mfl.getMediaFiles();
		if(ones != null && ones.size() > 0){
		    out.println("<table border width=\"80%\">");
		    out.println("<caption>Related Attachments</caption>");
		    out.println("<tr><th>File</th><th>Date</th><th>User</th><th>Notes</th></tr>");
		    out.println("</tr>");
		    for(MediaFile one:ones){
			out.println("<tr><td><a href=\""+url+"FileDownload.do?id="+one.getId()+"\">"+one.getOld_file_name()+"</a></td><td>"+one.getDate()+"</td><td>"+one.getUser()+"</td><td>"+one.getNotes()+"</td></tr>");
		    }
		    out.println("</table>");
		}
	    }				
	    out.print("</body></html>");
	    out.flush();
	    out.close();
	}
    }

}






















































