package ploan.model;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.sql.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.utils.*;
import ploan.list.*;
/**
 *
 *
 */

public class Property{

    static Logger logger = LogManager.getLogger(Property.class);
    static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");    
    //
    String pid="",street_num="",street_dir="",street_name="",
	street_type="",post_dir="",sud_num="",sud_type="",
	block_group="",hist_elegible="",national_reg="",units="",
	accessible="",rev_106 = "", idis_no="",leverage="", notes="",
	insur_expire ="",contract="",cont_addr="",census_tract="",
	owner_type="", act_type="",chdo="",district="";
    String plan_ref="",
	eligible_act="", eligible_act2="", 
	reference="", 
	nation_obj="", // list
	envir_ref="",    // list
	proj_manager=""; // list
	
    int bedrooms = 0,tenure_type = 0, nhood=0, ptype=0;
    String est_const_year="";
    //
    // additional parameters
    //
    String cid="", lid="";    
    String message = "";
    public Property(){

    }
    public Property(
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
		  String val12,
		  String val13,
		  String val14,
		  String val15,
		  String val16,
		  String val17,
		  String val18,
		  String val19,
		  String val20,

		  String val21,
		  String val22,
		  String val23,
		  String val24,
		  String val25,
		  String val26,
		  String val27,
		  String val28,
		  String val29,
		  String val30,

		  String val31,
		  String val32,
		  String val33,
		  String val34,
		  String val35,
		  String val36		  
		  ){
	setVals(val, val2, val3,val4, val5, val6, val7, val8, val9, val10,
		val11, val12, val13,val14, val15, val16, val17, val18, val19, val20,
		val21, val22, val23,val24, val25, val26, val27, val28, val29, val30,
		val31, val32, val33,val34, val35, val36);
    }
    private void setVals(
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
		  String val12,
		  String val13,
		  String val14,
		  String val15,
		  String val16,
		  String val17,
		  String val18,
		  String val19,
		  String val20,

		  String val21,
		  String val22,
		  String val23,
		  String val24,
		  String val25,
		  String val26,
		  String val27,
		  String val28,
		  String val29,
		  String val30,
		  
		  String val31,
		  String val32,
		  String val33,
		  String val34,
		  String val35,
		  String val36
			 ){
	setPid(val);
	setStreet_num(val2);
	setStreet_dir(val3);
	setStreet_name(val4);
	setStreet_type(val5);
	setPost_dir(val6);
	setSud_type(val7);
	setSud_num(val8);
	setPtype(val9);
	setBedrooms(val10);
	
	setTenure_type(val11);
	setNhood(val12);
	setDistrict(val13);
	setBlock_group(val14);
	setEst_const_year(val15);
	setHist_elegible(val16);
	setNational_reg(val17);
	setAccessible(val18);
	setRev_106(val19);
	setIdis_no(val20);

	setLeverage(val21);
	setInsur_expire(val22);
	setNotes(val23);
	setContract(val24);
	setCont_addr(val25);
	setCensus_tract(val26);
	setOwner_type(val27);
	setAct_type(val28);
	setChdo(val29);
	setPlan_ref(val30);
	
	setEligible_act(val31);
	setReference(val32);
	setNation_obj(val33);
	setEnvir_ref(val34);
	setProj_manager(val35);
	setUnits(val36);

    }
    
    public void setPid(String val){
	if(val != null)
	    pid = val;
    }
    public void setStreet_num(String val){
	if(val != null)
	    street_num = val;
    }
    public void setStreet_dir(String val){
	if(val != null)
	    street_dir = val;
    }
    public void setStreet_name(String val){
	if(val != null)
	    street_name = val;
    }
    public void setStreet_type(String val){
	if(val != null)
	    street_type = val;
    }
    public void setPost_dir(String val){
	if(val != null)
	    post_dir = val;
    }
    public void setSud_num(String val){
	if(val != null)
	    sud_num = val;
    }
    public void setSud_type(String val){
	if(val != null)
	    sud_type = val;
    }
    public void setCid(String val){
	if(val != null)
	    cid = val;
    }
    public void setLid(String val){
	if(val != null)
	    lid = val;
    }
    public void setBlock_group(String val){
	if(val != null)
	    block_group = val;
    }
    public void setHist_elegible(String val){
	if(val != null)
	    hist_elegible = val;
    }
    public void setNational_reg(String val){
	if(val != null)
	    national_reg = val;
    }
    public void setUnits(String val){
	if(val != null)
	    units = val;
    }
    public void setAccessible(String val){
	if(val != null)
	    accessible = val;
    }
    public void setRev_106(String val){
	if(val != null)
	    rev_106 = val;
    }
    public void setIdis_no(String val){
	    if(val != null)
	    idis_no = val;
    }
    public void setLeverage(String val){
	if(val != null)
	    leverage = val;
    }
    public void setNotes(String val){
	if(val != null)
	    notes = val;
    }
    public void setInsur_expire(String val){
	if(val != null)
	    insur_expire = val;
    }
    public void setContract(String val){
	if(val != null)
	    contract = val;
    }
    public void setCont_addr(String val){
	if(val != null)
	    cont_addr = val;
    }
    public void setCensus_tract(String val){
	if(val != null)
	    census_tract = val;
    }
    public void setOwner_type(String val){
	if(val != null)
	    owner_type = val;
    }
    public void setAct_type(String val){
	if(val != null)
	    act_type = val;
    }
    public void setChdo(String val){
	if(val != null)
	    chdo = val;
    }
    public void setDistrict(String val){
	if(val != null)
	    district = val;
    }
    public void setPlan_ref(String val){
	if(val != null)
	    pid = val;
    }
    public void setEligible_act(String val){
	if(val != null)
	    eligible_act = val;
    }
    public void setEligible_act2(String val){
	if(val != null)
	    eligible_act2 = val;
    }
    public void setReference(String val){
	if(val != null)
	    reference = val;
    }
    public void setNation_obj(String val){
	if(val != null)
	    nation_obj = val;
    }
    public void setEnvir_ref(String val){
	if(val != null)
	    envir_ref = val;
    }
    public void setProj_manager(String val){
	if(val != null)
	    proj_manager = val;
    }
    public void setEst_const_year(String val){
	if(val != null)
	    est_const_year = val;
    }    
    //
    // get list
    public String getPid(){
	return
	    pid ;
    }
    public String getStreet_num(){
	return
	    street_num ;
    }
    public String getStreet_dir(){
	return
	    street_dir ;
    }
    public String getStreet_name(){
	return
	    street_name ;
    }
    public String getStreet_type(){
	return
	    street_type ;
    }
    public String getPost_dir(){
	return
	    post_dir ;
    }
    public String getSud_num(){
	return
	    sud_num ;
    }
    public String getSud_type(){
	return
	    sud_type ;
    }
    public String getCid(){
	return
	    cid ;
    }
    public String getLid(){
	return
	    lid ;
    }
    public String getBlock_group(){
	return
	    block_group ;
    }
    public String getHist_elegible(){
	return
	    hist_elegible ;
    }
    public String getNational_reg(){
	return
	    national_reg ;
    }
    public String getUnits(){
	return
	    units ;
    }
    public String getAccessible(){
	return
	    accessible ;
    }
    public String getRev_106(){
	return
	    rev_106 ;
    }
    public String getIdis_no(){
	    return
	    idis_no ;
    }
    public String getLeverage(){
	return
	    leverage ;
    }
    public String getNotes(){
	return
	    notes ;
    }
    public String getInsur_expire(){
	return
	    insur_expire ;
    }
    public String getContract(){
	return
	    contract ;
    }
    public String getCont_addr(){
	return
	    cont_addr ;
    }
    public String getCensus_tract(){
	return
	    census_tract ;
    }
    public String getOwner_type(){
	return
	    owner_type ;
    }
    public String getAct_type(){
	return
	    act_type ;
    }
    public String getChdo(){
	return
	    chdo ;
    }
    public String getDistrict(){
	return
	    district ;
    }
    public String getPlan_ref(){
	return
	    pid ;
    }
    public String getEligible_act(){
	return
	    eligible_act ;
    }
    public String getEligible_act2(){
	return
	    eligible_act2 ;
    }
    public String getReference(){
	return
	    reference ;
    }
    public String getNation_obj(){
	return
	    nation_obj ;
    }
    public String getEnvir_ref(){
	return
	    envir_ref ;
    }
    public String getProj_manager(){
	return
	    proj_manager ;
    }
    public String getEst_const_year(){
	return est_const_year;
    }
    public int getNhood(){
	return nhood;
    }
    public int getPtype(){
	return ptype;
    }
    public int getBedrooms(){
	return bedrooms;
    }
    public int getTenure_type(){
	return tenure_type;
    }
    public void setNhood(String val){
	if(val != null && !val.equals("")){
	    try{
		nhood = Integer.parseInt(val);
	    }catch(Exception ex){};
	}
    }
    
    public void setPtype(String val){
	if(val != null && !val.equals("")){
	    try{
		ptype = Integer.parseInt(val);
	    }catch(Exception ex){};
	}
    }
    public void setBedrooms(String val){
	if(val != null && !val.equals("")){
	    try{
		bedrooms = Integer.parseInt(val);
	    }catch(Exception ex){};
	}
    }
    public void setTenure_type(String val){
	if(val != null && !val.equals("")){
	    try{
		tenure_type = Integer.parseInt(val);
	    }catch(Exception ex){};
	}
    }
    public boolean hasLoan(){
	return !lid.isEmpty();
    }
    public boolean hasClient(){
	return !cid.isEmpty();
    }
    public String getProj_manager_name(){
	if(!proj_manager.isEmpty()){
	    User one = new User(false, proj_manager);
	    String back = one.doSelect();
	    if(back.isEmpty()){
		return one.getFullName();
	    }
	}
	return "";
    }
    public String getAddress(){
	String ret = street_num;
	if(!street_dir.isEmpty()){
	    if(!ret.isEmpty()) ret += " ";
	    ret += street_dir;
	}
	if(!street_name.isEmpty()){
	    if(!ret.isEmpty()) ret += " ";
	    ret += street_name;
	}
	if(!street_type.isEmpty()){
	    if(!ret.isEmpty()) ret += " ";
	    ret += street_type;
	}
	if(!post_dir.isEmpty()){
	    if(!ret.isEmpty()) ret += " ";
	    ret += post_dir;
	}
	if(!sud_type.isEmpty()){
	    if(!ret.isEmpty()) ret += " ";
	    ret += sud_type;
	}
	if(!sud_num.isEmpty()){
	    if(!ret.isEmpty()) ret += " ";
	    ret += sud_num;
	}	
	return ret;
    }

    //
    public String doSave(){
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
	String qq = "";
	try{
	    qq = "insert into pproperty values (0,?,?,?,?, ?,?,?,?,?,";
	    qq += "?,?,?,?,?, ?,?,?,?,?,";
	    qq += "?,?,?,?,?, ?,?,?,?,?,";
	    qq += "?,?,?,?,?, ?)";
	    logger.debug(qq);	    
	    pstmt = con.prepareStatement(qq);
	    //
	    msg = setParams(pstmt);
	    if(msg.isEmpty()){	    
		pstmt.executeUpdate();
		//
		qq = "select LAST_INSERT_ID() ";
		logger.debug(qq);
		pstmt2 = con.prepareStatement(qq);
		rs = pstmt2.executeQuery();
		if(rs.next()){
		    pid = rs.getString(1);
		}
	    }
	}
	catch(Exception ex){
	    msg += " Could not save Data ";
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
	}	
	return msg;
    }
    private String setParams(PreparedStatement pstmt){
	String msg = "";
	try{
	    if(street_num.equals("")){
		pstmt.setNull(1, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(1, street_num);
	    }
	    if(street_dir.equals("")){
		pstmt.setNull(2, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(2, street_dir);
	    }
	    if(street_name.equals("")){
		pstmt.setNull(3, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(3, street_name);
	    }
	    if(street_type.equals("")){
		pstmt.setNull(4, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(4, street_type);
	    }
	    if(post_dir.equals("")){
		pstmt.setNull(5, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(5, post_dir);
	    }
	    if(sud_type.equals("")){
		pstmt.setNull(6, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(6, sud_type);
	    }
	    if(sud_num.equals("")){
		pstmt.setNull(7, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(7, sud_num);
	    }
	    pstmt.setInt(8, ptype);
	    pstmt.setInt(9, bedrooms);
	    pstmt.setInt(10, tenure_type);
	    pstmt.setInt(11, nhood);

	    if(district.equals("")){
		pstmt.setString(12, "0");
	    }
	    else {
		pstmt.setString(12, district);
	    }
	    if(block_group.equals("")){
		pstmt.setNull(13, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(13, block_group);
	    }
	    if(est_const_year.equals("")){
		pstmt.setString(14, "0");
	    }
	    else {
		pstmt.setString(14, est_const_year);
	    }
	    if(hist_elegible.equals("")){
		pstmt.setNull(15, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(15, hist_elegible);
	    }
	    if(national_reg.equals("")){
		pstmt.setNull(16, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(16, national_reg);
	    }
	    if(accessible.equals("")){
		pstmt.setNull(17, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(17, accessible);
	    }
	    if(rev_106.equals("")){
		pstmt.setNull(18, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(18, rev_106);
	    }
	    if(idis_no.equals("")){
		pstmt.setString(19, "0");		
	    }
	    else {
		pstmt.setString(19, idis_no);
	    }                         // 20
	    if(leverage.equals("")){
		pstmt.setNull(20, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(20, leverage);
	    }
	    if(insur_expire.equals("")){
		pstmt.setNull(21, Types.VARCHAR);
	    }
	    else {
		pstmt.setDate(21, new java.sql.Date(dateFormat.parse(insur_expire).getTime()));			
	    }
	    if(notes.equals("")){
		pstmt.setNull(22, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(22, notes);
	    }
	    if(contract.equals("")){
		pstmt.setNull(23, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(23, contract);
	    }
	    if(cont_addr.equals("")){
		pstmt.setNull(24, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(24, cont_addr);
	    }
	    if(census_tract.equals("")){
		pstmt.setNull(25, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(25, census_tract);
	    }
	    if(owner_type.equals("")){
		pstmt.setNull(26, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(26, owner_type);
	    }
	    if(act_type.equals("")){
		pstmt.setNull(27, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(27, act_type);
	    }
	    if(chdo.equals("")){
		pstmt.setNull(28, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(28, chdo);
	    }
	    if(plan_ref.equals("")){
		pstmt.setNull(29, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(29, plan_ref);
	    }
	    if(eligible_act.equals("")){
		pstmt.setNull(30, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(30, eligible_act);
	    }
	    if(reference.equals("")){
		pstmt.setNull(31, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(31, reference);
	    }
	    if(nation_obj.equals("")){
		pstmt.setNull(32, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(32, nation_obj);
	    }
	    if(envir_ref.equals("")){
		pstmt.setNull(33, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(33, envir_ref);
	    }
	    if(proj_manager.equals("")){
		pstmt.setNull(34, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(34, proj_manager);
	    }
	    if(units.equals("")){
		pstmt.setString(35, "0");
	    }
	    else {
		pstmt.setString(35, units);
	    }
	}
	catch(Exception ex){
	    msg += " Could not save Data ";
	    logger.error(ex);
	}
	return msg;
    }
    public String doUpdate(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//
	String qq = "update pproperty p set ";
	qq += "STREET_NUM     =?,"+
	    "STREET_DIR     =?,"+
	    "STREET_NAME    =?,"+
	    "STREET_TYPE    =?,"+
	    "POST_DIR       =?,"+
	    "SUD_TYPE       =?,"+
	    "SUD_NUM        =?,"+
	    "ptype          =?,"+
	    "bedrooms       =?,"+
	    "tenure_type    =?,"+
	    "nhood          =?,"+
	    "district       =?,"+
	    "block_group    =?,"+
	    "est_const_year =?,"+
	    "hist_elegible  =?,"+
	    "national_reg   =?,"+
	    "access_friendly=?,"+
	    "rev_106        =?,"+
	    "idis_no        =?,"+
	    "leverage       =?,"+
	    "insur_expire   =?,"+
	    "notes          =?,"+
	    "contract       =?,"+
	    "cont_addr      =?,"+
	    "census_tract   =?,"+
	    "owner_type     =?,"+
	    "act_type       =?,"+
	    "chdo           =?,"+
	    "plan_ref       =?,"+
	    "eligible_act   =?,"+
	    "reference      =?,"+
	    "nation_obj     =?,"+
	    "envir_ref      =?,"+
	    "proj_manager   =?,"+
	    "units          =? "+
	    "where pid=? ";
	
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}
	try{
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    msg = setParams(pstmt);
	    if(msg.isEmpty()){
		pstmt.setString(36, pid);
		pstmt.executeUpdate();
	    }
	}
	catch(Exception ex){
	    msg += " Could not update data "+ex;
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;
    }
    public String doDelete(){
	String msg="";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	//
	String qq = "delete from pproperty where pid=? ";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}	
	try{
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, pid);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg += " Could not delete Data "+ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}	
	return msg;
    }
    /**
       ALTER TABLE pproperty CHANGE `accessible` access_friendly char(1);
 select pid,STREET_NUM, STREET_DIR,STREET_NAME,STREET_TYPE,POST_DIR,SUD_TYPE,                SUD_NUM,ptype,bedrooms,tenure_type,nhood,                                       district,block_group,est_const_year,                                            hist_elegible,national_reg,                                                     rev_106,                                                             idis_no,leverage,                                                               date_format(insur_expire,'%m/%d/%Y'),                                           notes, contract,cont_addr,census_tract,owner_type,                              act_type,chdo,plan_ref,eligible_act,                                            reference,nation_obj,envir_ref,proj_manager,                                    units from pproperty where pid =920;

     */
    public String doSelect(){
	//
	String msg = "";
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
	     "p.units from pproperty p where p.pid=? ";
	String str="";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}		
	logger.debug(qq);
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, pid);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		setVals(
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
    public String findRelatedLoan(){
	//
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;		
	String qq = " select lid,cid from ploan where pid=? and lid > 0";	
	String str="";
	
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}		
	logger.debug(qq);
	try{
	    pstmt = con.prepareStatement(qq);
	    pstmt.setString(1, pid);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		lid = rs.getString(1);
		cid = rs.getString(2);
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
    static public Touple [] getAllNhood(){
	Touple[] nhoodArr = null;
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null, pstmt2=null;
	ResultSet rs = null;			
	String qq = "select count(*) from nhoods";
	int n = 0;
	String str="";
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return null;
	}		
	try{
	    logger.debug(qq);
	    pstmt = con.prepareStatement(qq);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		n = rs.getInt(1);
	    }
	    if(n > 0){
		int j=0,jt=0;
		nhoodArr = new Touple[n];
		qq = "select id,name from nhoods order by name ";
		logger.debug(qq);
		pstmt2 = con.prepareStatement(qq);
		rs = pstmt2.executeQuery();		
		while(rs.next()){
		    if(j > n) break; // should not happen
		    jt = rs.getInt(1);
		    str = rs.getString(2);
		    if(str != null && jt > -1)
			nhoodArr[j] = new Touple(jt,str);
		    j++;
		}
	    }
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
	}	
	return nhoodArr;
    }
    static public String getNameForId(Touple[] nhoodArr, int jj){
	String str = "";
	if(nhoodArr != null){
	    for(Touple one:nhoodArr){
		if(one.getId() == jj){
		    str = one.getName();
		}
	    }
	}
	return str;
    }
}





















































