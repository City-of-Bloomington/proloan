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

public class ClientList{

    static Logger logger = LogManager.getLogger(ClientList.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
    String lid="",cid="",pid="";
    String l_name="",f_name="",
	company="", phone="", street_num="",
	street_name="",street_dir="",
	street_type="",post_dir="",sud_type="",sud_num="",
	sortby="", pobox=""; 

    
    List<Client> clients;
    
    public ClientList(){

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
    public void setL_name(String val){
	if(val != null)
	    l_name= val;
    }
    public void setF_name(String val){
	if(val != null)
	    f_name= val;
    }
    public void setCompany(String val){
	if(val != null)
	    company= val;
    }
    public void setPhone(String val){
	if(val != null)
	    phone= val;
    }
    public void setStreet_num(String val){
	if(val != null)
	    street_num= val;
    }
    public void setStreet_dir(String val){
	if(val != null)
	    street_dir= val;
    }
    public void setStreet_name(String val){
	if(val != null)
	    street_name= val;
    }
    public void setStreet_type(String val){
	if(val != null)
	    street_type= val;
    }
    public void setPost_dir(String val){
	if(val != null)
	    post_dir= val;
    }
    public void setSud_num(String val){
	if(val != null)
	    sud_num= val;
    }
    public void setSud_type(String val){
	if(val != null)
	    sud_type= val;
    }
    public void setPobox(String val){
	if(val != null)
	    pobox = val;
    }
    public void setSortby(String val){
	if(val != null)
	    sortby = val;
    }    
    public List<Client> getClients(){
	return clients ;
    }
    public String find(){
	String msg = "", qw="";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = " select cid, date_format(entry_date,'%m/%d/%Y'),"+
	    "f_name, l_name2, f_name2 ,company, w_phone, h_phone,email,notes,STREET_NUM,STREET_DIR  ,STREET_NAME ,STREET_TYPE ,POST_DIR,SUD_TYPE,SUD_NUM ,city  , state ,zip ,hh_size ,ethnicity,race ,female_hhh, AMI, hh_type, pobox, ben_type, l_name, house_ratio from pclient ";	
	if(!cid.isEmpty()){
	    qw += " cid = ? ";
	}
	if(!lid.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " lid = ? ";
	}
	if(!f_name.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " (f_name like ? or f_name2 like ?)";
	}
	if(!l_name.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " (l_name like ? or l_name2 like ?)";
	}	
	if(!company.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " company like ? ";
	}
	if(!phone.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " (w_phone like ? or h_phone like ?) ";
	}
	if(!street_num.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " street_num like ? ";
	}	
	if(!street_name.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " street_name like ? ";
	}
	if(!street_dir.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " street_dir like ? ";
	}
	if(!street_type.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " street_type = ? ";
	}
	if(!post_dir.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " post_dir like ? ";
	}	
	if(!sud_type.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " sud_type = ? ";
	}	
	if(!sud_num.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " sud_name = ? ";
	}
	if(!pobox.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " pobox = ? ";
	}	
	if(!qw.isEmpty()){
	    qq += " where "+qw;
	}
	qq += " order by l_name,f_name,company ";
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
	    if(!lid.isEmpty()){
		pstmt.setString(jj++, lid);
	    }
	    if(!f_name.isEmpty()){
		pstmt.setString(jj++, f_name+"%");
		pstmt.setString(jj++, f_name+"%");	    
	    }
	    if(!l_name.isEmpty()){
		pstmt.setString(jj++, l_name+"%");
		pstmt.setString(jj++, l_name+"%");
	    }	
	    if(!company.isEmpty()){
		pstmt.setString(jj++, company+"%");
	    }
	    if(!phone.isEmpty()){
		pstmt.setString(jj++, phone+"%");
	    }
	    if(!street_num.isEmpty()){
		pstmt.setString(jj++, street_num);
	    }
	    if(!street_name.isEmpty()){
		pstmt.setString(jj++, street_name);
	    }
	    	    
	    if(!street_dir.isEmpty()){
		pstmt.setString(jj++, street_dir);
	    }
	    if(!street_type.isEmpty()){
		pstmt.setString(jj++, street_type);
	    }
	    if(!post_dir.isEmpty()){
		pstmt.setString(jj++, post_dir);
	    }	
	    if(!sud_type.isEmpty()){
		pstmt.setString(jj++, sud_type);
	    }	
	    if(!sud_num.isEmpty()){
		pstmt.setString(jj++, sud_num);
	    }
	    if(!pobox.isEmpty()){
		pstmt.setString(jj++, pobox);
	    }		    
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		Client cl = new Client(
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
				       rs.getString(30)
				   );
		if(clients == null)
		    clients = new ArrayList<>();
		clients.add(cl);
		
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























































