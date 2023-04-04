package ploan.list;
import java.sql.*;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
import ploan.utils.*;


public class UserList extends ArrayList<User>{

    String role="", project_manager="", active="", username="", fullName="";
    boolean debug = false;
    static Logger logger = LogManager.getLogger(UserList.class);
    public UserList(boolean deb){
	debug = deb;
    }
    public void setUsername (String val){
	if(val != null)
	    username = val;
    }
    public void setFullName (String val){
	if(val != null)
	    fullName = val;
    }
    public void hasRole (String val){
	if(val != null)
	    role = val;
    }
    public void isActive(){
	active = "y";
    }
    public void isProjectManager(){
	project_manager = "y";
    }	
    public String find(){
	PreparedStatement pstmt = null;
	Connection con = null;
	ResultSet rs = null;
	String back = "";
	String qq = " select * from users ";
	String qw = "";
	try{
	    con = Helper.getConnection();
	    if(con == null){
		back += " could not connect to database";
		logger.error(back);
		return back;
	    }
	    if(!username.equals("")){
		qw += " username = ? ";
	    }
	    if(!fullName.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " fullName = ? ";
	    }
	    if(!role.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " role = ? ";
	    }
	    if(!active.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " active = ? ";
	    }
	    if(!project_manager.equals("")){
		if(!qw.equals("")) qw += " and ";
		qw += " project_manager = ? ";
	    }
	    if(!qw.equals("")){
		qq += " where "+qw;
	    }
	    qq += " order by fullName ";
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(!username.equals("")){
		pstmt.setString(jj++, username);
	    }
	    if(!fullName.equals("")){
		pstmt.setString(jj++, "%"+fullName+"%");
	    }
	    if(!role.equals("")){
		pstmt.setString(jj++, role);
	    }
	    if(!active.equals("")){
		pstmt.setString(jj++, "y");
	    }
	    if(!project_manager.equals("")){
		pstmt.setString(jj++, "y");
	    }			
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		String str = rs.getString(1);
		String str2 = rs.getString(2);
		String str3 = rs.getString(3);
		String str4 = rs.getString(4);
		String str5 = rs.getString(5);
		String str6 = rs.getString(6);
		User user = new User(debug, str, str2, str3, str4, str5, str6);
		add(user);
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
