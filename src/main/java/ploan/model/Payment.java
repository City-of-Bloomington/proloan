package ploan.model;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.utils.*;
import ploan.list.*;

public class Payment{

    static Logger logger = LogManager.getLogger(Payment.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
    //
    /**
     *
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
    String lid="", address="";      
    String lateFee ="", owner="", due_date="", 
	startDate="", deposit_acc="", cityStateZip="";
    String action="", dir_amount="", dir_rate="";
    int pay_count = 0, startMonth=0, startYear=0, yearReq=0, prevYear=0,
	nextYear=0,periods=0,terms=0,id=0, year=0;
    double payment=0;
    String amount_paid="0",late_fee="0",receipt="", paid_date="",
	principal_bal="", fund_type="", interest="", principal="";
    boolean newMortgage=true;
    String message="";
    double prevBalance = 0.0;

    Loan loan = null;
    public Payment(){

    }
    public Payment(String val, String val2, String val3, String val4, 
		   String val5, String val6, String val7, String val8,
		   String val9, String val10
		   ){
	setVals(val, val2, val3, val4, val5, val6, val7, val8, val9, val10);
    }
    private void setVals(String val, String val2, String val3, String val4, 
		   String val5, String val6, String val7, String val8,
		   String val9, String val10
		   ){
	setLid(val);
	setId(val2);
	setDue_date(val3);
	setPaid_date(val4);
	setAmount_paid(val5);
	setInterest(val6);
	setPrincipal(val7);
	setPrincipal_bal(val8);
	setLate_fee(val9);
	setReceipt(val10);
    }
			 
    public void setLid(String val){
	if(val != null)
	    lid = val;
    }
    public void setId(String val){
	if(val != null){
	    try{
		id = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setDue_date(String val){
	if(val != null)
	    due_date = val;
    }
    public void setPaid_date(String val){
	if(val != null)
	    paid_date = val;
    }
    public void setAmount_paid(String val){
	if(val != null)
	    amount_paid = val;
    }
    public void setInterest(String val){
	if(val != null)
	    interest = val;
    }
    
    public void setPrincipal(String val){
	if(val != null)
	    principal = val;
    }
    public void setPrincipal_bal(String val){
	if(val != null)
	    principal_bal = val;
    }
    public void setLate_fee(String val){
	if(val != null)
	    late_fee = val;
    }
    public void setReceipt(String val){
	if(val != null)
	    receipt = val;
    }
    public void setPrevBalance(double val){
	prevBalance = val;
    }
    public void setYearReq(String val){
	if(val != null && !val.isEmpty()){
	    try{
		yearReq = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    //
    public String getLid(){
	return lid ;
    }
    public int getId(){
	return
	    id;
    }
    public String getDue_date(){
	return
	    due_date;
    }
    public String getPaid_date(){
	return
	    paid_date;
    }
    public double getAmount_paid(){
	if(amount_paid != null && !amount_paid.isEmpty()){
	    try{
		return Double.parseDouble(amount_paid);
	    }catch(Exception ex){}
	}
	return 0;
	    
    }
    public double getInterest(){
	if(interest != null && !interest.isEmpty()){
	    try{
		return Double.parseDouble(interest);
	    }catch(Exception ex){}
	}
	return 0;
    }
    public double getPrincipal(){
	if(principal != null && !principal.isEmpty()){
	    try{
		return Double.parseDouble(principal);
	    }catch(Exception ex){}
	}
	return 0;
    }
    public double getPrincipal_bal(){
	if(principal_bal != null && !principal_bal.isEmpty()){
	    try{
		return Double.parseDouble(principal_bal);
	    }catch(Exception ex){}
	}
	return 0;
    }
    public String getLate_fee(){
	return
	    late_fee;
    }
    public double getLate_feeDbl(){
	double ret = 0;
	if(late_fee != null && !late_fee.isEmpty()){
	    try{
		ret = Double.parseDouble(late_fee);
	    }catch(Exception ex){}
	}
	return ret;
    }    
    public String getReceipt(){
	return 
	    receipt;
    }
    public Loan getLoan(){
	if(!lid.isEmpty()){
	    Loan ll = new Loan(lid);
	    String back = ll.doSelect();
	    if(back.isEmpty()){
		loan = ll;
	    }
	}
	return loan;
    }
    public int getYear(){
	return year;
    }
    public String doSelect(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;	
	con = Helper.getConnection();
	String qq = "select lid,id,date_format(due_date,'%m/%d/%Y'),date_format(paid_date,'%m/%d/%Y'),amount_paid,interest,principal,principal_bal,late_fee, receipt from pmortgage where lid=? and id=? ";
	logger.debug(qq);
	// System.err.println(qq);
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1,lid);
	    pstmt.setInt(2, id);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		setVals(rs.getString(1),
			rs.getString(2),
			rs.getString(3),
			rs.getString(4),
			rs.getString(5),
			rs.getString(6),
			rs.getString(7),
			rs.getString(8),
			rs.getString(9),
			rs.getString(10));
	    }
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += " Could not update Data "+ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;	
    }
    public String doSave(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "insert into pmortgage values(?,?,?,null,0, 0,0,0,0,null)";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}		
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, lid);
	    pstmt.setString(2, ""+id);
	    pstmt.setDate(3, new java.sql.Date(dateFormat.parse(due_date).getTime()));
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += " Could not update Data "+ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;	    

    }
    public String doUpdate(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;	
	double p_bal = 0.0, amnt_paid = 0.0;
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}		
	try{
	    p_bal = Double.parseDouble(principal_bal);
	    amnt_paid = Double.parseDouble(amount_paid);
	}catch(Exception ex){}
	if(prevBalance > 0 && p_bal < 0){
	    p_bal = prevBalance - amnt_paid;
	}
	String qq = "update pmortgage set amount_paid=?,late_fee=?,"+
	    "principal_bal=?,interest=?,principal=?,paid_date=?,receipt=? where lid=? "+
	    " and id = ? ";
	logger.debug(qq);
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, amount_paid);
	    pstmt.setString(2, late_fee);
	    pstmt.setDouble(3, p_bal);
	    pstmt.setString(4, interest);
	    pstmt.setString(5, principal);
	    if(paid_date.equals("")){
		pstmt.setNull(6, Types.VARCHAR);
	    }
	    else {
		pstmt.setDate(6, new java.sql.Date(dateFormat.parse(paid_date).getTime()));	
	    }
	    if(receipt.equals(""))
		pstmt.setNull(7, Types.VARCHAR);	    
	    else
		pstmt.setString(7, receipt);
	    pstmt.setString(8, lid);
	    pstmt.setInt(9, id);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += " Could not update Data "+ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }

}






















































