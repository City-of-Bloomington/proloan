package ploan.list;

import java.util.*;
import java.sql.*;
import java.io.*;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.utils.*;
import ploan.model.*;

public class UnitList{

    static Logger logger = LogManager.getLogger(UnitList.class);
    String pid="",oid="", address="", 
	notes="", occupancy="", subsidy="", rent_total="",
	rent_assist="", nhood="",
	owner_type="", act_type="",chdo="",district="";
    List<Unit> units = null;
    public UnitList(){

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
    public List<Unit> getUnits(){
	return units;
    }
    
    public String find(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//
	//  System.err.println("zoom");
	//
	String qq = "select oid,"+
	    " address,bedrooms,occupancy,subsidy,rent_total,"+
	    " ami,race,hh_size,hh_type,rent_assist,notes "+
	    " from punits ";//  + oid;
	String qw = "";
	if(!oid.isEmpty()){
	    qw += " oid = ? ";
	}
	if(!qw.isEmpty()){
	    qq += " where "+qw;
	}
	qq += " order by address ";
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
	    if(!oid.isEmpty()){
		pstmt.setString(1, oid);
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		if(units == null)
		    units = new ArrayList<>();
		Unit one = new Unit(
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
				    rs.getString(12));
		units.add(one);
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






















































