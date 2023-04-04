package ploan.list;
import java.util.ArrayList;
import java.sql.*;
import java.text.SimpleDateFormat;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
import ploan.utils.*;

public class LeakTestList extends ArrayList<LeakTest>{

    static Logger logger = LogManager.getLogger(LeakTestList.class);
    String blowRead="", flowRing="", testDate="",
	testEvent = "", // before, after
	notes = "",
	pid="",   // property id
	id="";   
    boolean debug = false;
    String errors = "";
    SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
	

	
    public LeakTestList(Boolean val){
	debug = val;
    }
    public LeakTestList(Boolean val, String val2){
	debug = val;
	setPid(val2);
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
    public void setBlowRing (String val){
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
    public String lookFor(){
		
	String msg="";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;	
		
	String qq = "select id,pid,blowRead,flowRing,"+
	    " date_format(testDate,'%m/%d/%Y'), "+
	    " testEvent,notes "+
	    " from p_leakage_tests ";
	String qw = "";
	if(debug)
	    logger.debug(qq);
	try{
	    con = Helper.getConnection();
	    if(con == null){
		logger.error("Could not connect to DB ");
		return "Could not connect to DB ";
	    }
	    if(!pid.equals("")){
		qw += " pid = ? "; 
	    }
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by id desc ";
	    pstmt = con.prepareStatement(qq);
	    if(!qw.equals("")){	
		pstmt.setString(1, pid);
	    }
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		id="";pid="";blowRead=""; flowRing="";testDate="";
		testEvent=""; notes="";
		String str = rs.getString(1);
		if(str != null)
		    id = str;
		str = rs.getString(2);
		if(str != null)
		    pid = str;			
		str = rs.getString(3);
		if(str != null)
		    blowRead = str;
		str = rs.getString(4);
		if(str != null)
		    flowRing = str;
		str = rs.getString(5);
		if(str != null)
		    testDate = str;
		str = rs.getString(6);
		if(str != null)
		    testEvent = str;
		str = rs.getString(7);
		if(str != null)
		    notes = str;
		LeakTest ltest = new LeakTest(debug, id, pid,
					      blowRead, flowRing,
					      testDate, testEvent,
					      notes);
		this.add(ltest);
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
	
}
