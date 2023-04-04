package ploan.list;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.NumberFormat;
import java.util.Locale;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
import ploan.utils.*;

public class PaymentList{

    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    NumberFormat formatNumber=NumberFormat.getInstance(new Locale("en", "US"));
    static Logger logger = LogManager.getLogger(PaymentList.class);
    String lid="", year="", startDate="";
    boolean currentYearOnly = false;
    List<Payment> payments = null;
    public PaymentList(){

    }
    public void setLid(String val){
	if(val != null)
	    lid = val;
    }
    public void setYear(String val){
	if(val != null)
	   year = val;
    }
    public void setCurrentYearOnly(boolean val){
	if(val)
	    currentYearOnly = true;
    }
    public List<Payment> getPayments(){
	return payments;
    }
    public String find(){
	String msg = "", qw="", startDate="",endDate="";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;	
	con = Helper.getConnection();
	/**
	   select lid,id,date_format(due_date,'%m/%d/%Y'),date_format(paid_date,'%m/%d/%Y'),amount_paid,interest,principal,principal_bal,late_fee, receipt from pmortgage ; 
	   

	 */
	String qq = "select lid,id,"+
	    " date_format(due_date,'%m/%d/%Y'),"+
	    " date_format(paid_date,'%m/%d/%Y'),"+
	    " amount_paid,"+
	    " interest,"+
            " principal,"+
            " principal_bal,"+
	    " late_fee, "+
	    " receipt "+
	    " from pmortgage ";
	if(!lid.isEmpty()){
	    qw += " lid = ? ";
	}
	if(!year.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " year(due_date) = ? ";
	}
	if(currentYearOnly){
	    startDate = "01/01/"+Helper.getCurrentYear();
	    endDate = "12/31/"+Helper.getCurrentYear();
	    if(!qw.isEmpty())
		qw += " and ";
	    qw += " due_date >= ? ";
	    qw += " and due_date <= ? ";
	}
	try{
	    if(!qw.isEmpty()){
		qq += " where "+qw;
	    }
	    
	    qq += " order by lid, id ";
	    logger.debug(qq);
	    // System.err.println(qq);
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!lid.isEmpty())
		pstmt.setString(jj++, lid);
	    if(!year.isEmpty())
		pstmt.setString(jj++, year);
	    if(currentYearOnly){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(startDate).getTime()));
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(endDate).getTime()));		
		
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		Payment pp =
		    new Payment(
				rs.getString(1),
				rs.getString(2),
				rs.getString(3),
				rs.getString(4),
				rs.getString(5),
				rs.getString(6),
				rs.getString(7),
				rs.getString(8),
				rs.getString(9),
				rs.getString(10));
		if(payments == null)
		    payments = new ArrayList<>();
		payments.add(pp);
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

}






















































