package ploan.model;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.utils.*;
import ploan.list.*;

public class Unit{

    static Logger logger = LogManager.getLogger(Unit.class);
    String pid="",oid="", address="", 
	notes="", occupancy="", subsidy="", rent_total="",
	rent_assist="", nhood="",
	owner_type="", act_type="",chdo="",district="";
	
    int bedrooms = 0,tenure_type = 0, ptype=0, ami=0, race=0,hh_size=0,
	hh_type=0;

    String est_const_year="", action="", message="";
    public Unit(){

    }
    public Unit(
		String val,
		String val2,
		String val3,
		String val4,
		String val5,
		String val6,
		String val7,
		String val8,
		String val9,
		String val10,
		String val11,
		String val12){
	setOid(val);
	setAddress(val2);
	setBedrooms(val3);
	setOccupancy(val4);
	setSubsidy(val5);
	setRent_total(val6);
	setAmi(val7);
	setRace(val8);
	setHh_size(val9);
	setHh_type(val10);
	setRent_assist(val11);
	setNotes(val12);
    }    
    public void setOid(String val){
	if(val != null)
	    oid = val;
    }
    public void setPid(String val){
	if(val != null)
	    pid = val;
    }
    public void setAddress(String val){
	if(val != null)
	    address = val;
    }    
    public void setOccupancy(String val){
	if(val != null)
	    occupancy = val;
    }
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    public void setRent_assist(String val){
	if(val != null)
	    rent_assist = val;
    }
    public void setRent_total(String val){
	if(val != null)
	    rent_total = val;
    }
    public void setSubsidy(String val){
	if(val != null)
	    subsidy = val;
    }
    public void setNhood(String val){
	if(val != null)
	    nhood = val;
    }	
    public void setBedrooms(String val){
	if(val != null && !val.isEmpty()){
	    try{
		bedrooms = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setAmi(String val){
	if(val != null && !val.isEmpty()){
	    try{
	       ami = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setTenure_type(String val){
	if(val != null && !val.isEmpty()){
	    try{
		tenure_type = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setHh_type(String val){
	if(val != null && !val.isEmpty()){
	    try{
		hh_type = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setHh_size(String val){
	if(val != null && !val.isEmpty()){
	    try{
		hh_size = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setPtype(String val){
	if(val != null && !val.isEmpty()){
	    try{
		ptype = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }
    public void setRace(String val){
	if(val != null && !val.isEmpty()){
	    try{
		race = Integer.parseInt(val);
	    }catch(Exception ex){}
	}
    }    
    public int getPtype(){
	return ptype;
    }
    public int getAmi(){
	return ami;
    }
    public int getHh_type(){
	return hh_type;
    }
    public int getHh_size(){
	return hh_size;
    }    
    public int getTenure_type(){
	return tenure_type;
    }
    public int getBedrooms(){
	return bedrooms;
    }
    public String getOid(){
	return oid;
    }
    public String getPid(){
	return pid;
    }
    public String getAddress(){
	return address;
    }
    public String getOccupancy(){
	return occupancy;
    }
    public String getRent_total(){
	return rent_total;
    }
    public String getRent_assist(){
	return rent_assist;
    }    
    public String getSubsidy(){
	return subsidy;
    }
    public String getNotes(){
	return notes;
    }    
    public int getRace(){
	return race;
    }
    
    public String doSave(){
	String qq = "";
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null, pstmt2=null;
	ResultSet rs = null;
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}
	qq = "insert into punits values (0,?,?,?,?, ?,?,?,?,?,";
	qq += "?,?,?)";
	try{
	    pstmt = con.prepareStatement(qq);
	    msg = setParams(pstmt);
	    logger.debug(qq);

	    pstmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    logger.debug(qq);
	    pstmt2 = con.prepareStatement(qq);
	    rs = pstmt2.executeQuery();
	    if(rs.next()){
		oid = rs.getString(1);
	    }
	    //
	}
	catch(Exception ex){
	    logger.error(ex+": "+qq);
	    msg += ex;
	}
	finally{
	    Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
	}
	return msg;
    }
    private String setParams(PreparedStatement pstmt){
	String msg = "";
	try{

	    pstmt.setString(1, pid);
	    if(address.equals("")){
		pstmt.setNull(2, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(2, address);
	    }
	    pstmt.setInt(3, bedrooms);
	    if(occupancy.equals("")){
		pstmt.setNull(4, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(4, occupancy);
	    }
	    if(subsidy.equals("")){
	        subsidy = "0";
	    }
	    pstmt.setString(5, subsidy);
	    if(rent_total.equals("")){
		rent_total = "0";
	    }
	    pstmt.setString(6, rent_total);
	    pstmt.setInt(7, ami);
	    pstmt.setInt(8, race);
	    pstmt.setInt(9, hh_size);
	    pstmt.setInt(10, hh_type);
	    if(rent_assist.equals("")){
		pstmt.setNull(11, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(11, rent_assist);
	    }
	    if(notes.equals("")){
		pstmt.setNull(12, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(12, notes);
	    }
	}
	catch(Exception ex){
	    logger.error(ex);
	    msg += ex;
	}
	return msg;
    }
    public String doUpdate(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}
	String qq = "update punits set ";
	qq += " pid=?,"+         
	    " address=?,"+
	    " bedrooms=?,"+
	    " occupancy=?,"+
	    " subsidy=?,"+
	    " rent_total=?,"+
	    " ami=?,"+
	    " race=?,"+
	    " hh_size=?,"+
	    " hh_type=?,"+
	    " rent_assist=?,"+
	    " notes=? where oid = ? ";
	logger.debug(qq);
	try{	
	    pstmt = con.prepareStatement(qq);
	    msg = setParams(pstmt);
	    if(msg.isEmpty()){
		pstmt.setString(13, oid);
		pstmt.executeUpdate();
		msg = "updated success";
	    }
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    public String doDelete(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//
	String qq = "delete from punits where oid=?";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}	
	logger.debug(qq);
	try{
	    pstmt  = con.prepareStatement(qq);
	    pstmt.setString(1, oid);
	    pstmt.executeUpdate();
	    msg = "Deleted success";
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += ex;
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
	//
	//  System.err.println("zoom");
	//
	String qq = "select "+
	    " address,bedrooms,occupancy,subsidy,rent_total,"+
	    " ami,race,hh_size,hh_type,rent_assist,notes "+
	    " from punits where oid=?";//  + oid;
	logger.debug(qq);
	String str="";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}		
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, oid);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		str = rs.getString(1);
		if(str != null) address = str;
		bedrooms = rs.getInt(2);
		str = rs.getString(3);
		if(str != null) occupancy = str;
		str = rs.getString(4);
		if(!(str == null || str.equals("0"))) 
		    subsidy = str;
		str = rs.getString(5);
		if(!(str == null || str.equals("0"))) 
		    rent_total = str;
		ami = rs.getInt(6);
		race = rs.getInt(7);

		hh_size = rs.getInt(8);
		hh_type = rs.getInt(9);
		str = rs.getString(10);
		if(str != null) 
		    rent_assist = str;
		str = rs.getString(11);
		if(str != null){ 
		    notes = str;
		}
	    }
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }


}






















































