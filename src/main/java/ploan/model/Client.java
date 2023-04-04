package ploan.model;

import java.util.*;
import java.sql.*;
import java.io.*;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.utils.*;
import ploan.list.*;
/**
 *
 */

public class Client{

    String url="";
    boolean debug = false;
    static Logger logger = LogManager.getLogger(Client.class);
    static SimpleDateFormat dF = new SimpleDateFormat("MM/dd/yyyy");
    static SimpleDateFormat dF2 = new SimpleDateFormat("yyyy:MM:dd");		

    String race="0",ami="0", ethnicity="0";
    String cid="", email="",
	l_name="",
	f_name="",
	f_name2="",
	l_name2="",
	company="",
	w_phone="",
	h_phone="",
	notes="",
	lid="",
	street_num="",
	street_dir="",
	street_name="",
	street_type="",
	post_dir="",
	sud_type="",
	sud_num="",
	city="",
	state="",
	zip="",
	hh_size="",
	female_hhh="",
	entry_date="",
	hh_type="",
	pid="",
	pobox="",
	ben_type="",
	house_ratio="";
    public Client(){

    }
    public Client(String val){
	setCid(val);
    }
    public Client(
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
		  String val30
		  ){
	setVals(val, val2, val3,val4, val5, val6, val7, val8, val9, val10,
		val11, val12, val13,val14, val15, val16, val17, val18, val19, val20,
		val21, val22, val23,val24, val25, val26, val27, val28, val29, val30);
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
		  String val30
			 ){
	setCid(val);
	setEntry_date(val2);
	setF_name(val3);
	setL_name2(val4);
	setF_name2(val5);
	setCompany(val6);
	setW_phone(val7);
	setH_phone(val8);
	setEmail(val9);
	setNotes(val10);
	//
	setStreet_num(val11);
	setStreet_dir(val12);
	setStreet_name(val13);
	setStreet_type(val14);
	setPost_dir(val15);
	setSud_type(val16);
	setSud_num(val17);
	setCity(val18);
	setState(val19);
	setZip(val20);
	//
	setHh_size(val21);
	setEthnicity(val22);
	setRace(val23);
	setFemale_hhh(val24);
	setAmi(val25);
	setHh_type(val26);
	setPobox(val27);
	setBen_type(val28);
	setL_name(val29);
	setHouse_ratio(val30);
	
    }
    public String getL_name(){
	return l_name;
    }
    public String getF_name(){
	return f_name;
    }
    public String getL_name2(){
	return l_name2;
    }
    public String getF_name2(){
	return f_name2;
    }
    public String getCompany(){
	return company;
    }
    public String getW_phone(){
	return w_phone;
    }
    public String getH_phone(){
	return h_phone;
    }						
    public String getNotes(){
	return notes;
    }
    public String getLid(){
	return lid;
    }
    public String getStreet_num(){
	return street_num;
    }
    public String getStreet_dir(){
	return street_dir;
    }
    public String getStreet_name(){
	return street_name;
    }
    public String getStreet_type(){
	return street_type;
    }
    public String getPost_dir(){
	return post_dir;
    }
    public String getSud_num(){
	return sud_num;
    }
    public String getSud_type(){
	return sud_type;
    }
    public String getCity(){
	return city;
    }
    public String getState(){
	return state;
    }
    public String getZip(){
	return zip;
    }		
    public String getCityStateZip(){
	String ret = city;
	if(!state.isEmpty()){
	    if(!ret.isEmpty()){
		ret +=", ";
		ret += state;
	    }
	}
	if(!zip.isEmpty()){
	    if(!ret.isEmpty()){
		ret +=" ";
		ret += zip;
	    }
	}
	return ret;
    }
    public String getHh_size(){
	return hh_size;
    }
    public String getFemaile_hhh(){
	return female_hhh;
    }
    public String getEntry_date(){
	return entry_date;
    }
    public String getHh_type(){
	return hh_type;
    }
    public String getPid(){
	return pid;
    }
    public String getPobox(){
	return pobox;
    }
    public String getBen_type(){
	return ben_type;
    }
    public String getHouse_ratio(){
	return house_ratio;
    }
    public int getRace(){
	int ret = 0;
	if(race != null && !race.isEmpty()){
	    try{
		ret =  Integer.parseInt(race);
	    }catch(Exception ex){}
	}
	return ret;
    }		
    public int getAmi(){
	int ret = 0;
	if(ami != null && !ami.isEmpty()){
	    try{
		ret =  Integer.parseInt(ami);
	    }catch(Exception ex){}
	}
	return ret;				

    }		
    public int getEthnicity(){
	int ret = 0;
	if(ethnicity != null && !ethnicity.isEmpty()){
	    try{
		ret =  Integer.parseInt(ethnicity);
	    }catch(Exception ex){}
	}
	return ret;					
    }
    public String getFullName(){
	String ret = l_name;
	if(!f_name.isEmpty()){
	    if(!ret.isEmpty()) ret += ", ";
	    ret += f_name;
	}
	return ret;
    }
    public String getNames(){
	String ret = getFullName();
	if(!l_name2.isEmpty()){
	    if(!ret.isEmpty()) ret += ", ";
	    ret += l_name2;
	}	
	if(!f_name2.isEmpty()){
	    if(!ret.isEmpty()) ret += ", ";
	    ret += f_name2;
	}
	return ret;
    }    
    public String getOwner(){
	String ret = company;
	if(ret.isEmpty()){
	    ret = getFullName();
	}
	return ret;
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
    public String getPhones(){
	String ret = h_phone;
	if(!w_phone.isEmpty()){
	    if(!ret.isEmpty()) ret += " ";
	    ret += w_phone;
	}
	return ret;
    }
    // setters
    //
    public void setCid(String val){
	if(val != null)
	    cid = val;
    }
    public String getCid(){
	return cid;
    }
    public String getEmail(){
	return email;
    }
    public void setL_name(String val){
	if(val != null)
	    l_name= val;
    }
    public void setF_name(String val){
	if(val != null)
	    f_name= val;
    }
    public void setL_name2(String val){
	if(val != null)
	    l_name2= val;
    }
    public void setF_name2(String val){
	if(val != null)
	    f_name2= val;
    }
    public void setEmail(String val){
	if(val != null)
	    email = val;
    }    
    public void setCompany(String val){
	if(val != null)
	    company= val;
    }
    public void setW_phone(String val){
	if(val != null)
	    w_phone= val;
    }
    public void setH_phone(String val){
	if(val != null)
	    h_phone= val;
    }						
    public void setNotes(String val){
	if(val != null)
	    notes= val;
    }
    public void setLid(String val){
	if(val != null)
	    lid= val;
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
    public void setCity(String val){
	if(val != null)
	    city= val;
    }
    public void setState(String val){
	if(val != null)
	    state= val;
    }
    public void setZip(String val){
	if(val != null)
	    zip = val;
    }
    public void setHh_size(String val){
	if(val != null)
	    hh_size= val;
    }
    public void setFemale_hhh(String val){
	if(val != null)
	    female_hhh= val;
    }
    public void setEntry_date(String val){
	if(val != null)
	    entry_date= val;
    }
    public void setHh_type(String val){
	if(val != null)
	    hh_type= val;
    }
    public void setPid(String val){
	if(val != null)
	    pid= val;
    }
    public void setPobox(String val){
	if(val != null)
	    pobox= val;
    }
    public void setBen_type(String val){
	if(val != null)
	    ben_type= val;
    }
    public void setHouse_ratio(String val){
	if(val != null)
	    house_ratio= val;
    }
    public void setRace(String val){
	if(val != null)
	    race= val;
    }
    public void setAmi(String val){
	if(val != null)
	    ami= val;
    }
    public void setEthnicity(String val){
	if(val != null)
	    ethnicity= val;
    }
    public boolean hasLoan(){
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String qq = " select lid from ploan where cid=? and lid > 0";
	if(debug)
	    logger.debug(qq);			
	con = Helper.getConnection();
	if(con != null){				
	    try {
		pstmt = con.prepareStatement(qq);
		pstmt.setString(1, cid);
		rs = pstmt.executeQuery();
		if(rs.next()){
		    lid = rs.getString(1);
		}
	    }
	    catch(Exception ex){
		logger.error(ex+":"+qq);
	    }
	    finally{
		Helper.databaseDisconnect(con, pstmt, rs);
	    }
	}
	return !lid.isEmpty() && !lid.equals("0");						
    }
    public String doSave(){

	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null, pstmt2=null;
	ResultSet rs = null;
	String qq = "insert into pclient values (0,"+
	    "now(),?,?,?,?, ?,?,?,?,?,"+
	    "?,?,?,?,?, ?,?,?,?,?,"+
	    "?,?,?,?,?, ?,?,?,?)";				
	try {
	    entry_date = Helper.getToday();
	    con = Helper.getConnection();
	    if(con == null){
		msg = "Could not connect to Database ";
		logger.error(msg);
		return msg;
	    }
	    if(debug)
		logger.debug(qq);			
	    pstmt = con.prepareStatement(qq);
	    int jj=1;
	    if(f_name.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {	   
		pstmt.setString(jj++, f_name);
	    }
						
	    if(l_name2.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(jj++, l_name2);
	    }
	    if(f_name2.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, f_name2);
	    }
	    if(company.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, company);
	    }
	    if(w_phone.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, w_phone);
	    }
	    if(h_phone.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, h_phone);
	    }
	    if(email.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, email);
	    }
	    if(notes.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, notes);
	    }
	    if(street_num.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, street_num);
	    }
	    if(street_dir.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, street_dir);
	    }
	    if(street_name.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, street_name);
	    }
	    if(street_type.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, street_type);
	    }
	    if(post_dir.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, post_dir);
	    }
	    if(sud_type.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, sud_type);
	    }
	    if(sud_num.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);								

	    }
	    else{
		pstmt.setString(jj++, sud_num);
	    }
	    if(city.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, city);
	    }
	    if(state.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, state);
	    }
	    if(zip.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, zip);
	    }
	    if(hh_size.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, hh_size);
	    }
	    if(ethnicity.equals("")){
		ethnicity = "0";
	    }
	    pstmt.setString(jj++, ethnicity);
	    if(race.equals("")){
		race = "0";
	    }
	    pstmt.setString(jj++, race);
	    if(female_hhh.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, female_hhh);
	    }
	    if(ami.equals("")){
		ami="0";
	    }
	    pstmt.setString(jj++, ami);
	    if(hh_type.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, hh_type);
	    }
	    if(pobox.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, pobox);
	    }
	    if(ben_type.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, ben_type);
	    }
	    if(l_name.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(jj++, l_name);
	    }
	    if(house_ratio.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(jj++, house_ratio);
	    }
	    pstmt.executeUpdate();
	    qq = "select LAST_INSERT_ID() ";
	    if(debug){
		logger.debug(qq);
	    }
	    pstmt2 = con.prepareStatement(qq);
	    rs = pstmt2.executeQuery(qq);
	    if(rs.next()){
		cid = rs.getString(1);
	    }
	}
	catch(Exception ex){
	    msg += "Could not save data "+ex;
	    logger.error(ex+":"+qq);
	}
	finally{
	    Helper.databaseDisconnect(con, rs, pstmt, pstmt2);
	}
	return msg;
    }
    /**
       update pclient set
       f_name=?,
       l_name2=?,
       f_name2=?,
       company=?,
       w_phone=?,
			 
       h_phone=?,
       email=?,
       notes=?,
       STREET_NUM=?,
       STREET_DIR=?, // 10
			 
       STREET_NAME=?,
       STREET_TYPE=?,
       POST_DIR=?,
       SUD_TYPE=?,
       SUD_NUM=?,
			 
       city=?,
       state=?,
       zip=?,
       hh_size=?,
       ethnicity=?, // 20
			 
       race=?,
       female_hhh=?,
       AMI=?,
       hh_type=?,
       pobox=?, // 25
			 
       ben_type=?,
       l_name=?,
       house_ratio=?
       where cid=? // 29


    */
    public String doUpdate(){
	//
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;				
	String qq = "";
	qq = "update pclient set f_name=?,l_name2=?,f_name2=?,company=?,"+
	    "w_phone=?,h_phone=?,email=?,notes=?,STREET_NUM=?,STREET_DIR=?,"+
	    "STREET_NAME=?,STREET_TYPE=?,POST_DIR=?,SUD_TYPE=?,SUD_NUM=?,"+
	    "city=?,state=?,zip=?,hh_size=?,ethnicity=?,race=?,female_hhh=?,"+
	    "AMI=?,hh_type=?,pobox=?,ben_type=?,l_name=?,house_ratio=? where cid=? ";
	//
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}
	if(debug)
	    logger.debug(qq);			

	int jj=1;				
	try{
	    pstmt = con.prepareStatement(qq);
	    if(f_name.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {	   
		pstmt.setString(jj++, f_name);
	    }
						
	    if(l_name2.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(jj++, l_name2);
	    }
	    if(f_name2.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, f_name2);
	    }
	    if(company.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, company);
	    }
	    if(w_phone.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, w_phone);
	    }
	    if(h_phone.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, h_phone);
	    }
	    if(email.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, email);
	    }
	    if(notes.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, notes);
	    }
	    if(street_num.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, street_num);
	    }
	    if(street_dir.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, street_dir);
	    }
	    if(street_name.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, street_name);
	    }
	    if(street_type.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, street_type);
	    }
	    if(post_dir.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else{
		pstmt.setString(jj++, post_dir);
	    }
	    if(sud_type.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, sud_type);
	    }
	    if(sud_num.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);								

	    }
	    else{
		pstmt.setString(jj++, sud_num);
	    }
	    if(city.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, city);
	    }
	    if(state.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, state);
	    }
	    if(zip.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);

	    }
	    else {
		pstmt.setString(jj++, zip);
	    }
	    if(hh_size.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, hh_size);
	    }
	    if(ethnicity.equals("")){
		ethnicity = "0";
	    }
	    pstmt.setString(jj++, ethnicity);

	    if(race.equals("")){
		race = "0";
	    }
	    pstmt.setString(jj++, race);

	    if(female_hhh.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, female_hhh);
	    }
	    if(ami.equals("")){
		ami="0";
	    }
	    pstmt.setString(jj++, ami);
	    if(hh_type.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, hh_type);
	    }
	    if(pobox.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, pobox);
	    }
	    if(ben_type.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else {
		pstmt.setString(jj++, ben_type);
	    }
	    if(l_name.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(jj++, l_name);
	    }
	    if(house_ratio.equals("")){
		pstmt.setNull(jj++, Types.VARCHAR);
	    }
	    else{
		pstmt.setString(jj++, house_ratio);
	    }
	    pstmt.setString(jj++, cid);
	    pstmt.executeUpdate();
	}

	catch(Exception ex){
	    msg += "Could not save data "+ex;
	    logger.error(ex+":"+qq);
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
	String qq = "delete from pclient where cid=?";
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}
	if(debug)
	    logger.debug(qq);			

	try{
	    pstmt = con.prepareStatement(qq);						
	    pstmt.setString(1, cid);
	    pstmt.executeUpdate();
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	    msg = " Could not delete "+ex;
	}
	finally{
	    Helper.databaseDisconnect(con, pstmt, rs);
	}
	return msg;		
    }
    public String doSelect(){
	//
	String msg = "";
	Connection con = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;

	String qq = " select cid, date_format(entry_date,'%m/%d/%Y'),"+
	    "f_name, l_name2, f_name2 ,company, w_phone, h_phone,email,notes,STREET_NUM,STREET_DIR  ,STREET_NAME ,STREET_TYPE ,POST_DIR,SUD_TYPE,SUD_NUM ,city  , state ,zip ,hh_size ,ethnicity,race ,female_hhh, AMI, hh_type, pobox, ben_type, l_name, house_ratio from pclient where cid=? ";
	    
	String str="";
	if(debug){
	    logger.debug(qq);
	}
	con = Helper.getConnection();
	if(con == null){
	    msg = "Could not connect to Database ";
	    logger.error(msg);
	    return msg;
	}
	if(debug)
	    logger.debug(qq);			
	try{
	    pstmt = con.prepareStatement(qq);						
	    pstmt.setString(1, cid);
	    rs = pstmt.executeQuery();
	    if(rs.next()){
		setVals(cid,
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
			rs.getString(30));
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






















































