package ploan.list;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
import ploan.utils.*;

public class LoanList{

    static Logger logger = LogManager.getLogger(LoanList.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
    String lid="",cid="",pid="",fileId="";
    String ltype="", dir_amount_from="", dir_amount_to="",
	defer_amount_from="", defer_amount_to="",
	contract_date_from ="", contract_date_to="",
	cond_amount_from="", cond_amount_to="",
	grant_from="",grant_to="", cond_set_date_from="",
	cond_set_date_to="", appl_date_from="", appl_date_to="",
	status="", sortby="";
	
	
    List<Loan> loans;
    
    public LoanList(){

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
    public void setFileId(String val){
	if(val != null)
	    fileId = val;
    }    
    public void setLtype(String val){
	if(val != null)
	    ltype = val;
    }
    public void setDir_amount_from(String val){
	if(val != null)
	    dir_amount_from = val;
    }
    public void setDir_amount_to(String val){
	if(val != null)
	    dir_amount_to = val;
    }
    public void setDefer_amount_from(String val){
	if(val != null)
	    defer_amount_from = val;
    }
    public void setDefer_amount_to(String val){
	if(val != null)
	    defer_amount_to = val;
    }
    public void setContract_date_from(String val){
	if(val != null)
	    contract_date_from = val;
    }    
    public void setContract_date_to(String val){
	if(val != null)
	    contract_date_to = val;
    }
    public void setCond_amount_from(String val){
	if(val != null)
	    cond_amount_from = val;
    }
    public void setCond_amount_to(String val){
	if(val != null)
	    cond_amount_to = val;
    }
    public void setGrant_from(String val){
	if(val != null)
	    grant_from = val;
    }
    public void setGrant_to(String val){
	if(val != null)
	    grant_to = val;
    }
    public void setCond_set_date_from(String val){
	if(val != null)
	    cond_set_date_from = val;
    }    
    public void setCond_set_date_to(String val){
	if(val != null)
	    cond_set_date_to = val;
    }
    public void setAppl_date_from(String val){
	if(val != null)
	    appl_date_from = val;
    }    
    public void setAppl_date_to(String val){
	if(val != null)
	    appl_date_to = val;
    }
    public void setStatus(String val){
	if(val != null)
	    status = val;
    }
    public void setSortby(String val){
	if(val != null)
	    sortby = val;
    }   
    

    public List<Loan> getLoans(){
	return loans ;
    }
    public String find(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = "select lid,cid,pid,"+
	    "date_format(received,'%m/%d/%Y'),"+
	    "ptype,ptype_other,"+
	    "date_format(contract_date,'%m/%d/%Y'),"+
	    "dir_amount,dir_rate,defer_amount,cond_amount,"+
	    "cond_term,cond_term_other,"+ 
	    "date_format(cond_set_date,'%m/%d/%Y'),"+
	    "date_format(dir_set_date,'%m/%d/%Y'),"+
	    
	    "satisfied,fund_type,appraised_val,sale_price,notes,ltv, "+
	    "date_format(appl_date,'%m/%d/%Y'),"+
	    "date_format(draw_date,'%m/%d/%Y'), "+
	    "periods,terms,lateFee,deposit_acc,"+
	    "date_format(startDate,'%m/%d/%Y'), "+ // 22
	    
	    "payment,mortgage,fileId,l_grant, "+
	    "date_format(appl_dir_date,'%m/%d/%Y'), "+
	    "fund_def_type, "+
	    "land_lord, "+
	    "gen_match,"+
	    "match_amnt, "+	    

	    "pay_off,"+
	    "date_format(pay_off_date,'%m/%d/%Y'), "+
	    "inactive "+
	    "from ploan ";
	String qw = "";
	if(!cid.isEmpty()){
	    qw += " cid = ? ";
	}
	if(!pid.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " pid = ? ";
	}
	if(!lid.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " lid = ? ";
	}
	if(!fileId.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " fileId = ? ";
	}
		
	if(!ltype.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " ptype = ? ";
	}
	
	if(!dir_amount_from.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " dir_amount >= ? ";
	}
	if(!dir_amount_to.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " dir_amount <= ? ";
	}
	if(!defer_amount_from.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " defer_amount >= ? ";
	}
	if(!defer_amount_to.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " defer_amount <= ? ";
	}
	if(!cond_amount_from.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " cond_amount >= ? ";
	}
	if(!cond_amount_to.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " cond_amount <= ? ";
	}
	if(!grant_from.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " l_grant >= ? ";
	}
	if(!grant_to.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " l_grant <= ? ";
	}	
	if(!contract_date_from.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " contract_date >= ? ";
	}
	if(!contract_date_to.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " contract_date <= ? ";
	}
	if(!cond_set_date_from.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " cond_set_date >= ? ";
	}
	if(!cond_set_date_to.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " cond_set_date <= ? ";
	}	
	if(!appl_date_from.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " appl_date >= ? ";
	}
	if(!appl_date_to.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " appl_date <= ? ";
	}	
	if(!qw.isEmpty()){
	    qq += " where "+qw;
	}
	qq += " order by lid desc ";
	String str="";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}		
	logger.debug(qq);
	try{
	    int jj=1;
	    pstmt = con.prepareStatement(qq);
	    if(!cid.isEmpty()){
		pstmt.setString(jj++, cid);
	    }
	    if(!pid.isEmpty()){
		pstmt.setString(jj++, pid);
	    }
	    if(!lid.isEmpty()){
		pstmt.setString(jj++, lid);
	    }
	    if(!fileId.isEmpty()){
		pstmt.setString(jj++, fileId);
	    }	    
	    if(!ltype.isEmpty()){
		pstmt.setString(jj++, ltype);
	    }
	    
	    if(!dir_amount_from.isEmpty()){
		pstmt.setString(jj++, dir_amount_from);
	    }
	    if(!dir_amount_to.isEmpty()){
		pstmt.setString(jj++, dir_amount_to);
	    }
	    if(!defer_amount_from.isEmpty()){
		pstmt.setString(jj++, defer_amount_from);
	    }
	    if(!defer_amount_to.isEmpty()){
		pstmt.setString(jj++, defer_amount_to);
	    }
	    if(!cond_amount_from.isEmpty()){
		pstmt.setString(jj++, cond_amount_from);
	    }
	    if(!cond_amount_to.isEmpty()){
		pstmt.setString(jj++, cond_amount_to);
	    }
	    if(!grant_from.isEmpty()){
		pstmt.setString(jj++, grant_from);
	    }
	    if(!grant_to.isEmpty()){
		pstmt.setString(jj++, grant_to);
	    }	
	    if(!contract_date_from.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(contract_date_from).getTime()));		    
	    }
	    if(!contract_date_to.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(contract_date_to).getTime()));	
	    }
	    if(!cond_set_date_from.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(cond_set_date_from).getTime()));	
	    }
	    if(!cond_set_date_to.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(cond_set_date_to).getTime()));	
	    }	
	    if(!appl_date_from.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(appl_date_from).getTime()));	
	    }
	    if(!appl_date_to.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(appl_date_to).getTime()));	
	    }	
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		Loan ll = new Loan(
				   rs.getString(1),
				   rs.getString(2),
				   rs.getString(3),
				   rs.getString(4),
				   rs.getString(5),
				   rs.getString(6),
				   rs.getString(7),
				   rs.getString(8),
				   rs.getString(9),
				   rs.getString(10),
				   
				   rs.getString(11),
				   rs.getString(12),
				   rs.getString(13),
				   rs.getString(14),
				   rs.getString(15),
				   rs.getString(16),
				   rs.getString(17),
				   rs.getString(18),
				   rs.getString(19),
				   rs.getString(20),
				   
				   rs.getString(21),
				   rs.getString(22),
				   rs.getString(23),
				   rs.getString(24),
				   rs.getString(25),
				   rs.getString(26),
				   rs.getString(27),
				   rs.getString(28),
				   rs.getString(29),
				   rs.getString(30),

				   rs.getString(31),
				   rs.getString(32),
				   rs.getString(33),
				   rs.getString(34),
				   rs.getString(35),
				   rs.getString(36),
				   rs.getString(37),
				   rs.getString(38),
				   rs.getString(39),
				   rs.getString(40)
				   );
		if(loans == null)
		    loans = new ArrayList<>();
		loans.add(ll);
		
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
	
}























































