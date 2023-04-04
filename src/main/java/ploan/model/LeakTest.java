package ploan.model;
import java.sql.*;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.utils.*;
import ploan.list.*;
/**
 *
 *
 */
public class LeakTest{

    String blowRead="", flowRing="", testDate="",
	testEvent = "", // before, after
	notes = "",
	pid="",   // property id
	id="";   
    boolean debug = false;
    String errors = "";
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	
    static Logger logger = LogManager.getLogger(LeakTest.class);
    Connection con = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;	
	
    public LeakTest(Boolean val){
	debug = val;
    }
    public LeakTest(Boolean val, String val2, String val3, String val4,
		    String val5, String val6, String val7, String val8
		    ){
	debug = val;
	setId(val2);
	setPid(val3);
	setBlowRead(val4);
	setFlowRing(val5);
	setDate(val6);
	setTestEvent(val7);
	setNotes(val8);
    }	
    //
    // getters
    //
    public String getId(){
	return id;
    }
    public String getPid(){
	return pid;
    }	
    public String getBlowRead(){
	return blowRead;
    }
    public String getFlowRing(){
	return flowRing;
    }
    public String getDate(){
	return testDate;
    }
    public String getTestEvent(){
	return testEvent;
    }	
    public String getNotes(){
	return notes;
    }	
    //
    // setters
    //
    public void setId (String val){
	if(val != null)
	    id = val;
    }
    public void setPid (String val){
	if(val != null)
	    pid = val;
    }
    public void setBlowRead (String val){
	if(val != null)
	    blowRead = val;
    }
    public void setFlowRing (String val){
	if(val != null)
	    flowRing = val;
    }
    public void setDate (String val){
	if(val != null)
	    testDate = val;
    }
    public void setTestEvent (String val){
	if(val != null)
	    testEvent = val;
    }	
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    public boolean isValid(){
	//
	// if any of these is not set
	//
	return !(blowRead.equals("") ||
		 flowRing.equals("") ||
		 testEvent.equals("") ||
		 pid.equals(""));
		
    }
    public String doSelect(){
		
	String msg="";
		
	String qq = "select pid,blowRead,flowRing,"+
	    " date_format(testDate,'%m/%d/%Y'), "+
	    " testEvent,notes "+
	    " from p_leakage_tests where id=?";
	if(debug)
	    logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		logger.error("Could not connect to DB ");
		return "Could not connect to DB ";
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, id);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		String str = rs.getString(1);
		if(str != null)
		    pid = str;
		str = rs.getString(2);
		if(str != null)
		    blowRead = str;
		str = rs.getString(3);
		if(str != null)
		    flowRing = str;
		str = rs.getString(4);
		if(str != null)
		    testDate = str;
		str = rs.getString(5);
		if(str != null)
		    testEvent = str;
		str = rs.getString(6);
		if(str != null)
		    notes = str;
	    }
	}
	catch(Exception ex){
	    msg += " "+ex;
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    public String doSave(){
		
	String msg="";
	if(testDate.equals(""))
	    testDate = Helper.getToday();
	String qq = " insert into "+
	    " p_leakage_tests values(0,?,?,?,?,?,?)";
	if(debug)
	    logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		logger.error("Could not connect to DB ");
		return "Could not connect to DB ";
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, pid);
	    pstmt.setString(2, blowRead);
	    pstmt.setString(3, flowRing);
	    pstmt.setDate(4,new java.sql.Date(formatter.parse(testDate).getTime()));			
	    pstmt.setString(5,testEvent);
	    if(notes.equals(""))
		pstmt.setNull(6, java.sql.Types.VARCHAR);
	    else
		pstmt.setString(6,notes);		
	    pstmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
	    }				
	}
	catch(Exception ex){
	    msg += " "+ex;
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    public String doUpdate(){
		
	String msg="";
	if(testDate.equals(""))
	    testDate = Helper.getToday();
	String qq = " update "+
	    " p_leakage_tests set blowRead=?, "+
	    " flowRing=?, testDate=?, "+
	    " testEvent=?, notes=? where id=?";
	if(debug)
	    logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		logger.error("Could not connect to DB ");
		return "Could not connect to DB ";
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, blowRead);
	    pstmt.setString(2, flowRing);
	    pstmt.setDate(3,new java.sql.Date(formatter.parse(testDate).getTime()));			
	    pstmt.setString(4,testEvent);
	    if(notes.equals(""))
		pstmt.setNull(5, java.sql.Types.VARCHAR);
	    else
		pstmt.setString(5,notes);
	    pstmt.setString(6,id);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    msg += " "+ex;
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    public String doDelete(){
	String msg = "";
	String qq = " delete from "+
	    " p_leakage_tests "+
	    " where id=?";
	if(debug)
	    logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		logger.error("Could not connect to DB ");
		return "Could not connect to DB ";
	    }
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, id);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    msg += " "+ex;
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
	
}
