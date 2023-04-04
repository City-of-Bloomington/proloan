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

public class PropertyList{

    static Logger logger = LogManager.getLogger(PropertyList.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");	
    String lid="",cid="",pid="";
    String street_name="",street_dir="",street_num="",
	street_type="",post_dir="",sud_type="",sud_num="",
	proj_manager="",  ptype="", contract="", cont_addr="",
	act_type="",exp_from="", exp_to="", leak_from="", leak_to="",
	sortby=""; 
    
    List<Property> properties;
    
    public PropertyList(){

    }
    public void setPid(String val){
	if(val != null)
	    pid = val;
    }
    public void setLid(String val){
	if(val != null)
	    lid = val;
    }
    public void setPtype(String val){
	if(val != null)
	    ptype = val;
    }
    public void setContract(String val){
	if(val != null)
	    contract = val;
    }
    public void setCont_addr(String val){
	if(val != null)
	    cont_addr = val;
    }
    public void setExp_from(String val){
	if(val != null)
	    exp_from = val;
    }
    public void setExp_to(String val){
	if(val != null)
	    exp_to = val;
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
    public void setProj_manager(String val){
	if(val != null)
	   proj_manager = val;
    }
    public void setAct_type(String val){
	if(val != null)
	    act_type = val;
    }
    public void setLeak_from(String val){
	if(val != null)
	   leak_from = val;
    }
    public void setLeak_to(String val){
	if(val != null)
	    leak_to = val;
    }    
    public void setSortby(String val){
	if(val != null)
	    sortby = val;
    }        

    public List<Property> getProperties(){
	return properties ;
    }
    public String find(){
	String msg = "", qw="";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = " select p.pid,p.STREET_NUM, p.STREET_DIR,"+
	     "p.STREET_NAME,p.STREET_TYPE,p.POST_DIR,p.SUD_TYPE,"+
	     "p.SUD_NUM,p.ptype,p.bedrooms,p.tenure_type,p.nhood,"+
	     "p.district,p.block_group,p.est_const_year,"+
	     "p.hist_elegible,p.national_reg,"+
	     "p.access_friendly,p.rev_106,"+
	     "p.idis_no,p.leverage,"+
	     "date_format(p.insur_expire,'%m/%d/%Y'),"+
	     "p.notes, p.contract,p.cont_addr,p.census_tract,p.owner_type,"+
	     "p.act_type,p.chdo,p.plan_ref,p.eligible_act,"+
	     "p.reference,p.nation_obj,p.envir_ref,p.proj_manager,"+
	     "p.units from pproperty p ";
	
	if(!pid.isEmpty()){
	    qw += " p.pid = ? ";
	}
	if(!lid.isEmpty()){
	    qq += " left join ploan l on l.pid=p.pid ";
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " l.lid = ? ";
	}
	if(!street_num.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.STREET_NUM like ? ";
	}	
	if(!street_name.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.STREET_NAME like ? ";
	}
	if(!street_dir.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.STREET_DIR like ? ";
	}
	if(!street_type.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.STEET_TYPE = ? ";
	}
	if(!post_dir.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.POST_DIR like ? ";
	}	
	if(!sud_type.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.SUD_TYPE = ? ";
	}	
	if(!sud_num.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.SUD_NUM = ? ";
	}
	if(!contract.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.contract like ? ";
	}
	if(!cont_addr.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.cond_addr like ? ";
	}
	if(!act_type.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.act_type = ? ";
	}
	if(!ptype.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.ptype = ? ";
	}
	if(!proj_manager.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.proj_manager = ? ";
	}
	if(!exp_from.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.insur_expire >= ? ";
	}
	if(!exp_to.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " p.insur_to <= ? ";
	}
	if(!leak_from.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " ll.testDate >= ? ";
	}
	if(!leak_to.isEmpty()){
	    if(!qw.isEmpty()) qw += " and ";
	    qw += " ll.testDate <= ? ";
	}
	if(!leak_from.isEmpty() || !leak_to.isEmpty()){
	    qq += " left join p_leakage_tests ll on ll.pid=p.pid ";
	}
	if(!qw.isEmpty()){
	    qq += " where "+qw;
	}
	
	qq += "order by p.STREET_NAME,p.STREET_DIR,"+
		"lpad(p.STREET_NUM,6,'0')";
	
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
	    if(!pid.isEmpty()){
		pstmt.setString(jj++, pid);
	    }
	    if(!lid.isEmpty()){
		pstmt.setString(jj++, lid);
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
	    if(!contract.isEmpty()){
		pstmt.setString(jj++, contract+"%");
	    }
	    if(!cont_addr.isEmpty()){
		pstmt.setString(jj++, cont_addr+"%");
	    }
	    if(!act_type.isEmpty()){
		pstmt.setString(jj++, act_type);
	    }
	    if(!ptype.isEmpty()){
		pstmt.setString(jj++, ptype);
	    }
	    if(!proj_manager.isEmpty()){
		pstmt.setString(jj++, proj_manager);
	    }
	    if(!exp_from.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(exp_from).getTime()));	
	    }
	    if(!exp_to.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(exp_to).getTime()));	
	    }
	    if(!leak_from.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(leak_from).getTime()));	
	    }
	    if(!leak_to.isEmpty()){
		pstmt.setDate(jj++, new java.sql.Date(dateFormat.parse(leak_to).getTime()));	
	    }	    
	    rs = pstmt.executeQuery();
	    while(rs.next()){
		Property one = new Property(
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
				       rs.getString(36)				       
				   );
		if(properties == null)
		    properties = new ArrayList<>();
		properties.add(one);
		
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























































