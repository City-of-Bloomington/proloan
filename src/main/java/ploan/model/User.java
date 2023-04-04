package ploan.model;
import java.sql.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.utils.*;
import ploan.list.*;

public class User implements java.io.Serializable{

    String id="",username="", fullName="", dept="", role="", project_manager="", active="";
    boolean debug = false, userExists = false;
    String errors = "";
    static Logger logger = LogManager.getLogger(User.class);
    public User(String val){
	setUsername(val);
    }
    public User(boolean deb, String val){
	debug = deb;
	setId(val);
    }		
    public User(boolean deb, String val, String val2, String val3, String val4, String val5, String val6){
	debug = deb;
	setId(val);
	setUsername(val2);
	setFullName(val3);
	setRole(val4);
	setProjectManager(val5);
	setActive(val6);
    }	
    //
    public boolean hasRole(String val){
	if(role.indexOf(val) > -1) return true;
	return false;
    }
    public boolean canEdit(){
	return hasRole("Edit");
    }
    public boolean canDelete(){
	return hasRole("Delete");
    }
    public boolean isAdmin(){
	return hasRole("Admin");
    }
    public boolean isActive(){
	return !active.equals("");
    }
    public boolean isProjectManager(){
	return !project_manager.equals("");
    }	
    //
    // getters
    //
    public String getId(){
	return id;
    }	
    public String getUsername(){
	return username;
    }
    public String getFullName(){
	return fullName;
    }
    public String getProjectManager(){
	return project_manager;
    }
    public String getActive(){
	return active;
    }
	
    public String getDept(){
	return dept;
    }
    public String getRole(){
	return role;
    }

    //
    // setters
    //
    public void setId(String val){
	if(val != null)
	    id = val;
    }	
    public void setUsername (String val){
	if(val != null)
	    username = val;
    }
    public void setFullName (String val){
	if(val != null)
	    fullName = val;
    }
    public void setRole (String val){
	if(val != null)
	    role = val;
    }
    public void setDept (String val){
	if(val != null)
	    dept = val;
    }
    public void setActive(String val){
	if(val != null)
	    active = val;
    }
    public void setProjectManager(String val){
	if(val != null)
	    project_manager = val;
    }	
    public boolean userExists(){
	return userExists;
    }
    public String toString(){
	return fullName;
    }

    public String doSelect(){
	PreparedStatement pstmt = null;
	Connection con = null;
	ResultSet rs = null;
	String back = "";
	String qq = " select * from users ";
	try{
	    con = Helper.getConnection();
	    if(con == null){
		back += " could not connect to database";
		logger.error(back);
		return back;
	    }
	    if(!username.equals("")){
		qq += " where username = ? ";
	    }
	    else if(!id.equals("")){
		qq += " where id = ? ";
	    }
	    pstmt = con.prepareStatement(qq);						
	    if(!username.equals("")){
		pstmt.setString(1, username);
	    }
	    else if(!id.equals("")){
		pstmt.setString(1, id);
	    }
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		id = rs.getString(1);
		username = rs.getString(2);
		setFullName(rs.getString(3));
		setRole(rs.getString(4));
		setProjectManager(rs.getString(5));
		setActive(rs.getString(6));
		userExists = true;
	    }
	}
	catch (Exception ex) {
	    logger.error(ex);
	    back += ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}		
	return back;
    }
	
	
}
