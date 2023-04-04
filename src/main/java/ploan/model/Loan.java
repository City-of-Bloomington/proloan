package ploan.model;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.utils.*;
import ploan.list.*;

public class Loan{

    static Logger logger = LogManager.getLogger(Loan.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
    String lid="",cid="0",pid="0",received="",ptype="",
	ptype_other="",contract_date="",dir_amount="",dir_rate="",
	defer_amount="",cond_amount="",cond_term_other="",
	appl_date="",draw_date="", fileId="",l_grant="",
	appl_dir_date="", land_lord="",
	cond_set_date="",dir_set_date="",satisfied="",appraised_val="",
	sale_price="",fund_type="",notes="",cont_date_today="",ltv="",
	fund_def_type="", gen_match="", match_amnt="", pay_off="",
	pay_off_date="", status="", cityStateZip="";
    
    String lateFee ="", owner="", mortgage="", 
	startDate="", deposit_acc="";
    String payment="", principal_bal="",principal="", address="";// info only
    int terms=0, periods = 0;
    //
    // basic info needed for payment
    int pay_count = 0, startMonth=0, startYear=0, yearReq=0, prevYear=0,
	nextYear=0;    
    int cond_term=0;
    Client client = null;
    List<Payment> payments = null;
    Property property = null;
    List<Client> clients = null;
    List<Property> properties = null;
    public Loan(){

    }
    public Loan(String val){
	setLid(val);
    }    
    public Loan(
		String val, String val2, String val3, String val4, String val5,
		String val6, String val7, String val8, String val9, String val10,
		String val11, String val12, String val13, String val14, String val15,
		String val16, String val17, String val18, String val19, String val20,
		String val21, String val22, String val23, String val24, String val25,
		String val26, String val27, String val28, String val29, String val30,
		String val31, String val32, String val33, String val34, String val35,
		String val36, String val37, String val38, String val39, String val40){
	setLid(val);
	setCid(val2);
	setPid(val3);
	setReceived(val4);
	setPtype(val5);
	setPtype_other(val6);
	setContract_date(val7);
	setDir_amount(val8);
	setDir_rate(val9);
	setDefer_amount(val10);
	//	
	setCond_amount(val11);
	setCond_term(val12);
	setCond_term_other(val13);
	setCond_set_date(val14);
	setDir_set_date(val15);
	setSatisfied(val16);
	setFund_type(val17);
	setAppraised_val(val18);
	setSale_price(val19);
	setNotes(val20);
	
	setLtv(val21);
	setAppl_date(val22);
	setDraw_date(val23);
	setPeriods(val24);
	setTerms(val25);
	setlateFee(val26);
	setDeposit_acc(val27);
	setStartDate(val28);
	setPayment(val29);
	setMortgage(val30);
	//
	setFileId(val31);
	setL_grant(val32);
	setAppl_dir_date(val33);
	setFund_def_type(val34);
	setLand_lord(val35);
	setGen_match(val36);
	setMatch_amnt(val37);
	setPay_off(val38);
	setPay_off_date(val39);
	setStatus(val40);
    }
        
    public void setLid(String val){
	if(val != null)
	    lid = val;
    }
    public void setCid(String val){
	if(val != null)
	    cid = val;
    }    
    public void setPid(String val){
	if(val != null)
	    pid = val;
    }    
    public void setReceived(String val){
	if(val != null)
	    received = val;
    }
    public void setPtype(String val){
	if(val != null)
	    ptype = val;
    }
    public void setPtype_other(String val){
	if(val != null)
	    ptype_other = val;
    }
    public void setContract_date(String val){
	if(val != null)
	    contract_date = val;
    }
    public void setDir_amount(String val){
	if(val != null)
	    dir_amount = val;
    }
    public void setDir_rate(String val){
	if(val != null)
	    dir_rate = val;
    }
    public void setDefer_amount(String val){
	if(val != null)
	    defer_amount = val;
    }
    public void setCond_amount(String val){
	if(val != null)
	    cond_amount = val;
    }
    public void setCond_term_other(String val){
	if(val != null)
	    cond_term_other = val;
    }
    public void setCond_term(String val){
	if(val != null && !val.isEmpty()){
	    try{
		cond_term = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }    
    public void setAppl_date(String val){
	if(val != null)
	    appl_date = val;
    }
    public void setDraw_date(String val){
	if(val != null)
	    draw_date = val;
    }
    public void setFileId(String val){
	if(val != null)
	    fileId = val;
    }
    public void setL_grant(String val){
	if(val != null)
	    l_grant = val;
    }
    public void setAppl_dir_date(String val){
	if(val != null)
	    appl_dir_date = val;
    }
    public void setLand_lord(String val){
	if(val != null)
	    land_lord = val;
    }
    public void setCond_set_date(String val){
	if(val != null)
	    cond_set_date = val;
    }
    public void setSatisfied(String val){
	if(val != null)
	    satisfied = val;
    }
    public void setAppraised_val(String val){
	if(val != null)
	    appraised_val = val;
    }
    public void setSale_price(String val){
	if(val != null)
	    sale_price = val;
    }
    public void setFund_type(String val){
	if(val != null)
	    fund_type = val;
    }
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    public void setCont_date_today(String val){
	if(val != null)
	    cont_date_today = val;
    }
    public void setLtv(String val){
	if(val != null)
	    ltv = val;
    }
    public void setFund_def_type(String val){
	if(val != null)
	    fund_def_type = val;
    }
    public void setGen_match(String val){
	if(val != null)
	    gen_match = val;
    }
    public void setMatch_amnt(String val){
	if(val != null)
	    match_amnt = val;
    }
    public void setPay_off(String val){
	if(val != null)
	    pay_off = val;
    }        
    public void setPay_off_date(String val){
	if(val != null)
	    pay_off_date = val;
    }
    public void setStatus(String val){
	if(val != null && val.equals("inactive"))
	    status = "y";
    }        
    public void setlateFee(String val){
	if(val != null)
	    lateFee = val;
    }
    public void setPeriods(String val){
	if(val != null){
	    try{
		periods = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setTerms(String val){
	if(val != null){
	    try{
		terms = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
	    
    }
    public void setOwner(String val){
	if(val != null)
	    owner = val;
    }
    public void setMortgage(String val){
	if(val != null)
	    mortgage = val;
    }
    public void setStartDate(String val){
	if(val != null)
	    startDate = val;
    }
    public void setDeposit_acc(String val){
	if(val != null)
	    deposit_acc = val;
    }
    public void setPayment(String val){
	if(val != null)
	    payment = val;
    }
    public void setPrincipal_bal(String val){
	if(val != null)
	    principal_bal = val;
    }
    public void setDir_set_date(String val){
	if(val != null)
	    dir_set_date = val;
    }    
    //
    public String getLid(){
	return lid ;
    }
    public String getCid(){
	return cid ;
    }    
    public String getPid(){
	return pid ;
    }    
    public String getReceived(){
	return received ;
    }
    public String getPtype(){
	return ptype ;
    }
    public String getPtype_other(){
	return ptype_other ;
    }
    public String getContract_date(){
	return contract_date ;
    }
    public double getDir_amount(){
	if(dir_amount != null && !dir_amount.isEmpty()){
	    try{
		return Double.parseDouble(dir_amount) ;
	    }catch(Exception ex){}
	}
	return 0;
    }
    public double getDir_rate(){
	if(dir_rate != null && !dir_rate.isEmpty()){
	    try{
		return Double.parseDouble(dir_rate) ;
	    }catch(Exception ex){}
	}
	return 0;	    
    }
    public String getDefer_amount(){
	return defer_amount ;
    }
    public String getCond_amount(){
	return cond_amount ;
    }
    public String getCond_term_other(){
	return cond_term_other ;
    }
    public String getAppl_date(){
	return appl_date ;
    }
    public String getDraw_date(){
	return draw_date ;
    }
    public String getFileId(){
	return fileId ;
    }
    public String getL_grant(){
	return l_grant ;
    }
    public String getAppl_dir_date(){
	return appl_dir_date ;
    }
    public String getLand_lord(){
	   return land_lord ;
    }
    public String getCond_set_date(){
	    return cond_set_date ;
    }
    public String getSatisfied(){
	return satisfied ;
    }
    public String getAppraised_val(){
	return appraised_val ;
    }
    public String getSale_price(){
	return sale_price ;
    }
    public String getFund_type(){
	return
	       fund_type ;
    }
    public String getNotes(){
	return notes ;
    }
    public String getCont_date_today(){
	return  cont_date_today ;
    }
    public String getLtv(){
	return ltv ;
    }
    public String getFund_def_type(){
	return fund_def_type ;
    }
    public String getGen_match(){
	return  gen_match ;
    }
    public String getMatch_amnt(){
	return  match_amnt ;
    }
    public String getPay_off(){
	return  pay_off ;
    }
    public String getDir_set_date(){
	return dir_set_date;
    }
    public String getPay_off_date(){
	return  pay_off_date ;
    }
    public String getStatus(){
	if(!status.isEmpty()){
	    return  "inactive";
	}
	return "acive";
    }        
    public String getLateFee(){
	return lateFee ;
    }
    public int getPeriods(){
	return periods ;
    }
    public int getTerms(){
	return terms ;
    }
    public String getOwner(){
	return
	    owner ;
    }  
    public String getMortgage(){
	return
	    mortgage; 
    }
    public String getStartDate(){
	    return startDate ;
    }
    public String getDeposit_acc(){
	return deposit_acc ;
    }
    public String getPayment(){
	return payment ;
    }
    public String getPrincipal_bal(){
	return principal_bal ;
    }
    public String getCityStateZip(){
	return cityStateZip;
    }
    public int getStartYear(){
	return startYear;
    }
    public int getStartMonth(){
	return startMonth;
    }
    public int getCond_term(){
	return cond_term;
    }

    public String findPayments(){
	String back = "";
	if(!lid.isEmpty()){
	    PaymentList pl = new PaymentList();
	    pl.setLid(lid);
	    back = pl.find();
	    if(back.isEmpty()){
		List<Payment> ones = pl.getPayments();
		if(ones != null && ones.size() > 0){
		    payments = ones;
		}
	    }
	}
	return back;
    }
    public List<Payment> getPayments(){
	if(payments == null){
	    findPayments();
	}
	return payments;
    }
    public Client getClient(){
	if(client == null && !cid.isEmpty()){
	    Client one = new Client(cid);
	    String back = one.doSelect();
	    if(back.isEmpty()){
		client = one;
	    }
	}
	return client;
    }
    public List<Client> getClients(){
	if(clients == null){
	    ClientList cl = new ClientList();
	    cl.setLid(lid);
	    String back = cl.find();
	    if(back.isEmpty()){
		clients = cl.getClients();
	    }
	}
	return clients;
    }
    public List<Property> getProperties(){
	if(properties == null){
	    PropertyList pl = new PropertyList();
	    pl.setLid(lid);
	    String back = pl.find();
	    if(back.isEmpty()){
		properties = pl.getProperties();
	    }
	}
	return properties;
    }
    public boolean hasPayments(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	if(lid.isEmpty()){
	    return false;
	}
	String qq = " select count(*) from pmortgage where lid =? ";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return false;
	}
	int cnt = 0;
	logger.debug(qq);
	try{	
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, lid);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		cnt = rs.getInt(1);
	    }
	}
	catch(Exception ex){
	    msg += " Could not save data "+ex;
	    logger.error(ex+" : "+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}	    
	return cnt > 0;
    }    
    public String doSave(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//
	String query = "insert into ploan values (0,?,?,?,?, ?,?,?,?,?,"+
	    "?,?,?,?,?, ?,?,?,?,?,"+
	    "?,?,?,?,?, ?,?,?,?,?,"+
	    "?,?,?,?,?, ?,?,?,?,?)"; //inactive
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}
	logger.debug(query);
	try{	
	    pstmt = con.prepareStatement(query);
	    msg = setParams(pstmt);
	    if(msg.isEmpty()){
		pstmt.executeUpdate();
		//
		query = "select LAST_INSERT_ID() ";
		logger.debug(query);
		pstmt = con.prepareStatement(query);
		rs = pstmt.executeQuery();
		if(rs.next()){
		    lid = rs.getString(1);
		}
	    }	    
	}
	catch(Exception ex){
	    msg += " Could not save data "+ex;
		logger.error(ex+" : "+query);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    private String setParams(PreparedStatement pstmt){
	String back = "";
	try{
	    if(cid.equals(""))
		pstmt.setString(1,"0");
	    else
		pstmt.setString(1, cid);
	    if(pid.equals(""))
		pstmt.setString(2,"0");
	    else
		pstmt.setString(2, pid);
	    if(received.equals("")){
		received = Helper.getToday();
	    }
	    pstmt.setDate(3, new java.sql.Date(dateFormat.parse(received).getTime()));		    
	    if(ptype.equals("")){
		pstmt.setNull(4, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(4, ptype);		
	    }
	    if(ptype_other.equals("")){
		pstmt.setNull(5, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(5, ptype_other);
	    }
	    if(contract_date.equals("")){
		pstmt.setNull(6, Types.VARCHAR);
	    }
	    else{
		pstmt.setDate(6, new java.sql.Date(dateFormat.parse(contract_date).getTime()));		
	    }
	    if(dir_amount.equals("")){
		dir_amount = "0";
	    }
	    pstmt.setString(7, dir_amount);		
	    if(dir_rate.equals("")){
		dir_rate = "0";
	    }
	    pstmt.setString(8, dir_rate);	
	    if(defer_amount.equals("")){
		defer_amount = "0";
	    }
	    pstmt.setString(9, defer_amount);

	    if(cond_amount.equals("")){
		cond_amount = "0";
	    }
	    pstmt.setString(10, cond_amount);
	    pstmt.setString(11, ""+cond_term);		
	    if(cond_term_other.equals("")){
		pstmt.setNull(12, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(12, cond_term_other);
	    }
	    if(cond_set_date.equals("")){
		pstmt.setNull(13, Types.VARCHAR);
	    }
	    else{
		pstmt.setDate(13, new java.sql.Date(dateFormat.parse(cond_set_date).getTime()));
	    }
	    if(dir_set_date.equals("")){
		pstmt.setNull(14, Types.VARCHAR);
	    }
	    else{
		pstmt.setDate(14, new java.sql.Date(dateFormat.parse(dir_set_date).getTime()));		

	    }
	    if(satisfied.equals("")){
		pstmt.setNull(15, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(15, satisfied);
	    }
	    if(fund_type.equals("")){
		pstmt.setNull(16, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(16, fund_type);
	    }
	    if(appraised_val.equals("")){
	       appraised_val= "0";
	    }
	    pstmt.setString(17, appraised_val);
	    if(sale_price.equals("")){
		sale_price= "0";
	    }
	    pstmt.setString(18, sale_price);
	    if(notes.equals("")){
		pstmt.setNull(19, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(19, notes);
	    }
	    if(ltv.equals("")){
		ltv ="0";
	    }
	    pstmt.setString(20, ltv);
	    if(appl_date.equals("")){
		pstmt.setNull(21, Types.VARCHAR);
	    }
	    else{
		pstmt.setDate(21, new java.sql.Date(dateFormat.parse(appl_date).getTime()));
	    }
	    if(draw_date.equals("")){
		pstmt.setNull(22, Types.VARCHAR);
	    }
	    else{
		pstmt.setDate(22, new java.sql.Date(dateFormat.parse(draw_date).getTime()));		
	    }
	    pstmt.setInt(23, periods);
	    pstmt.setInt(24, terms);
	    if(lateFee.equals("")){
		pstmt.setNull(25, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(25, lateFee);
	    }
	    if(deposit_acc.equals("")){
		pstmt.setNull(26, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(26, deposit_acc);
	    }
	    if(startDate.equals("")){
		pstmt.setNull(27, Types.VARCHAR);
	    }
	    else{
		pstmt.setDate(27, new java.sql.Date(dateFormat.parse(startDate).getTime()));		
	    }
	    if(payment.isEmpty())
		payment = "0";
	    pstmt.setString(28, payment);
	    if(mortgage.equals("")){
		mortgage ="0";
	    }
	    pstmt.setString(29, mortgage);

	    if(fileId.equals("")){
		pstmt.setNull(30, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(30, fileId);
	    }
	    if(l_grant.equals("")){
	       l_grant ="0";
	    }
	    pstmt.setString(31, l_grant);
	    if(appl_dir_date.equals("")){
		pstmt.setNull(32, Types.VARCHAR);
	    }
	    else{
		pstmt.setDate(32, new java.sql.Date(dateFormat.parse(appl_dir_date).getTime()));			
	    }
	    if(fund_def_type.equals("")){
		pstmt.setNull(33, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(33, fund_def_type);
	    }
	    if(land_lord.equals("")){
		pstmt.setNull(34, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(34, land_lord);
	    }
	    if(gen_match.equals("")){
		pstmt.setNull(35, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(35, "y");
	    }
	    if(match_amnt.equals("")){
		match_amnt = "0";
	    }
	    pstmt.setString(36, match_amnt);
	    if(pay_off.equals("")){
		pay_off= "0";
	    }
	    pstmt.setString(37, pay_off);
	    if(pay_off_date.equals("")){
		pstmt.setNull(38, Types.VARCHAR);
	    }
	    else{
		pstmt.setDate(38, new java.sql.Date(dateFormat.parse(pay_off_date).getTime()));		
	    }
	    if(status.isEmpty()){
		pstmt.setNull(39, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(39, "y");
	    }
	}catch(Exception ex){
	    back += ex;
	}
	return back;
    }

    public String doUpdate(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "update ploan set cid=?,pid=?,received=?,ptype=?,ptype_other=?,";
	qq += "contract_date=?,dir_amount=?,dir_rate=?,defer_amount=?,cond_amount=?,";
	qq += "cond_term=?,cond_term_other=?,cond_set_date=?,dir_set_date=?,"+
	    "satisfied=?,fund_type=?,appraised_val=?,sale_price=?,notes=?,"+
	    "ltv=?,appl_date=?,draw_date=?,periods=?,terms=?,lateFee=?,"+
	    "deposit_acc=?,startDate=?,payment=?,mortgage=?,fileId=?,"+
	    "l_grant=?, appl_dir_date=?,fund_def_type=?, land_lord=?,"+
	    "gen_match=?, match_amnt=?,pay_off=?, pay_off_date=?, inactive=?"+
	    " where lid = ?";
	logger.debug(qq);		
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}
	try{
	    pstmt = con.prepareStatement(qq);
	    msg = setParams(pstmt);
	    if(msg.isEmpty()){
		pstmt.setString(40, lid);
		pstmt.executeUpdate();
	    }
	}
	catch(Exception ex){
	    msg += " Could not update data "+ex;
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}	
	return msg;
    }
    
    public String deleteMortgage(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//
	String qq = "delete from pmortgage where lid=? ";
	logger.debug(qq);
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}	
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, lid);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += " Could not delete morgage data "+ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    public String doDelete(){
	//
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "delete from ploan where lid=? ";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}	
	try{
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, lid);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += " Could not delete data "+ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select cid,pid,ptype,ptype_other,"+ // 4
	    "date_format(contract_date,'%m/%d/%Y'),"+
	    "dir_amount,dir_rate,defer_amount,cond_amount,"+
	    "cond_term,cond_term_other,"+ // 11
	    "date_format(cond_set_date,'%m/%d/%Y'),"+
	    "date_format(dir_set_date,'%m/%d/%Y'),"+
	    "satisfied,fund_type,appraised_val,sale_price,notes,ltv, "+
	    "date_format(appl_date,'%m/%d/%Y'),"+
	    "date_format(draw_date,'%m/%d/%Y'), "+
	    "date_format(startDate,'%m/%d/%Y'), "+ // 22
	    "periods,terms,lateFee,deposit_acc,mortgage,fileId,l_grant, "+
	    "payment, "+
	    "date_format(appl_dir_date,'%m/%d/%Y'), "+
	    "fund_def_type, "+
	    "land_lord, "+
	    "gen_match,"+
	    "match_amnt, "+
	    "pay_off,"+
	    "date_format(pay_off_date,'%m/%d/%Y'), "+
	    "inactive, "+
	    "date_format(startDate,'%Y'), "+
	    "date_format(startDate,'%m') "+	    
	    "from ploan where lid=?";
	String str="";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}		
	logger.debug(qq);
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, lid);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		str = rs.getString(1);
		if(!str.equals("0")) cid = str;
		str = rs.getString(2);
		if(!str.equals("0")) pid = str;
		str = rs.getString(3);
		if(str != null) ptype = str;
		str = rs.getString(4);
		if(str != null) ptype_other = str;
		str = rs.getString(5);
		if(str != null){ 
		    contract_date = str;
		}
		str = rs.getString(6);
		if(str != null && !str.equals("0")) 
		    dir_amount = str;
		str = rs.getString(7);
		if(str != null && !str.equals("0")) 
		    dir_rate = str;
		str = rs.getString(8);
		if(str != null && !str.equals("0")) 
		    defer_amount = str;
		str = rs.getString(9);  
		if(str != null && !str.equals("0")) 
		    cond_amount = str; 
		cond_term = rs.getInt(10);
		str = rs.getString(11);
		if(str != null) cond_term_other = str;
		str = rs.getString(12);
		if(str != null) cond_set_date = str;
		str = rs.getString(13);
		if(str != null) dir_set_date = str;
		str = rs.getString(14);
		if(str != null) satisfied = str;
		str = rs.getString(15);
		if(str != null) fund_type = str;
		str = rs.getString(16);
		if(str != null && !str.equals("0")) 
		    appraised_val = str;
		str = rs.getString(17);
		if(str != null && !str.equals("0")) 
		    sale_price = str;
		str = rs.getString(18);
		if(str != null) notes = str;
		str = rs.getString(19);
		if(!(str == null || str.equals("0"))) 
		    ltv = str;
		str = rs.getString(20);
		if(str != null) appl_date = str;
		str = rs.getString(21);
		if(str != null) draw_date = str;
		str = rs.getString(22);
		if(str != null) startDate = str;
		periods = rs.getInt(23);
		terms = rs.getInt(24);
		str = rs.getString(25);
		if(str != null) lateFee = str;		    
		str = rs.getString(26);
		if(str != null) deposit_acc = str;
		str = rs.getString(27);
		if(str != null && !str.equals("0")) 
		    mortgage = str;
		str = rs.getString(28);
		if(str != null) fileId = str;
		str = rs.getString(29);
		if(str != null && !str.equals("0")) 
		    l_grant = str;
		str = rs.getString(30);
		if(str != null && !str.equals("0")) 
		    payment = str;
		str = rs.getString(31);
		if(str != null) appl_dir_date = str;
		str = rs.getString(32);
		if(str != null) fund_def_type = str;
		str = rs.getString(33);
		if(str != null) land_lord = str;
		str = rs.getString(34);
		if(str != null) gen_match = str;
		str = rs.getString(35);
		if(!str.equals("0")) match_amnt = str;
		str = rs.getString(36);
		if(!str.equals("0")) 
		    pay_off = str;
		str = rs.getString(37);
		if(str != null) pay_off_date = str;
		str = rs.getString(38);
		if(str != null) status = str;
		startYear =  rs.getInt(39); //startYear
		startMonth = rs.getInt(40);
	    }
	    qq = "select principal_bal from pmortgage where lid=? "+
		" and amount_paid > 0 order by id DESC ";
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, lid);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		str = rs.getString(1);
		if(str != null && !str.equals("0"))
		    principal_bal = str;
	    }
	    else{ // when we start
		principal_bal = dir_amount;
	    }
	}
	catch(Exception ex){
	    msg += " Could not retreive data "+ex;
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    // ToDo get info from client
    public String findLoanBasicInfo(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = " select l.dir_amount, l.dir_rate, "+
	    "concat_ws(' ',c.street_num,c.street_dir,c.street_name,"+
	    "c.street_type,c.post_dir,c.sud_type,"+
	    "c.sud_num), "+
	    "c.f_name, c.f_name2, c.l_name, "+
	    "l.periods,l.terms,l.lateFee,l.deposit_acc,"+
	    "date_format(l.startDate,'%m/%d/%Y'), "+
	    "concat_ws(' ',concat_ws(', ',c.city,c.state),c.zip), "+
	    "date_format(l.startDate,'%Y'),"+
	    "date_format(l.startDate,'%m'), "+
	    "l.payment,l.fund_type "+
	    "from ploan l,pclient c "+
	    "where l.cid=c.cid and l.lid=?";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}		
	logger.debug(qq);
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, lid);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		String str = rs.getString(1);
		if(str != null) dir_amount = str;		
		str = rs.getString(2);
		if(str != null) dir_rate = str;
		str = rs.getString(3);
		if(str != null) address = str;
		str = rs.getString(4);
		if(str != null) owner = str;
		str = rs.getString(5);
		if(str != null){
		    if(!owner.equals("")) owner += " and "+str;
		    else owner = str;
		}
		str = rs.getString(6);
		if(str != null) owner += " "+str;
		periods = rs.getInt(7);
		terms = rs.getInt(8);
		str = rs.getString(9);
		if(str != null) lateFee = str;
		str = rs.getString(10);
		if(str != null) deposit_acc = str;
		str = rs.getString(11);
		if(str != null) startDate = str;
		str = rs.getString(12);
		if(str != null) cityStateZip = str;
		startYear = rs.getInt(13);
		startMonth = rs.getInt(14);
		payment = rs.getString(15);
		str = rs.getString(16);
		if(str != null) fund_type = str;
		//
		pay_count = terms * periods;
		principal = dir_amount;
	    }
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += " Error getting data "+ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;	    
	
    }
    public String findCurrentBalance(){
	String msg = "", qq="";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	qq = "select principal_bal from pmortgage where lid= ? "+
	    " and amount_paid > 0 order by id DESC ";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}	
	try{
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, lid);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		String str = rs.getString(1);
		if(str != null && !str.equals("0"))
		    principal_bal = str;
	    }
	    else{
		principal_bal = dir_amount;
	    }
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += " Could not delete data "+ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
	
}























































