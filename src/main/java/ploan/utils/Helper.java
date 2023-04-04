package ploan.utils;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.AlphaComposite;
import java.awt.image.BufferedImage;
import javax.sql.*;
import java.nio.file.*;
import javax.naming.*;
import javax.naming.directory.*;
import java.security.MessageDigest;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ploan.model.*;
/**
 *
 */

public class Helper{

    static int c_con = 0;
    final static String bgcolor = "silver";// #bfbfbf gray
    final static String fgcolor = "navy";// for titles

    //
    // Non static variables
    //
    static Logger logger = LogManager.getLogger(Helper.class);

    //
    // basic constructor
    public Helper(boolean deb){
	//
	// initialize
	//
    }
    final static String getHashCodeOf(String buffer){

	String key = "Apps Secret Key "+getToday();
	byte[] out = performDigest(buffer.getBytes(),buffer.getBytes());
	String ret = bytesToHex(out);
	return ret;
	// System.err.println(ret);

    }
    final static byte[] performDigest(byte[] buffer, byte[] key) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(buffer);
            return md5.digest(key);
        } catch (Exception e) {
	    System.err.println(e);
        }
        return null;
    }

    final static String bytesToHex(byte in[]) {
	byte ch = 0x00;
	int i = 0; 
	if (in == null || in.length <= 0)
	    return null;
	String pseudo[] = {"0", "1", "2",
	    "3", "4", "5", "6", "7", "8",
	    "9", "A", "B", "C", "D", "E",
	    "F"};
	StringBuffer out = new StringBuffer(in.length * 2);
	while (i < in.length) {
	    ch = (byte) (in[i] & 0xF0); // Strip off high nibble
		
	    ch = (byte) (ch >>> 4);
	    // shift the bits down
	    
	    ch = (byte) (ch & 0x0F);    
	    // must do this is high order bit is on!

	    out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
	    ch = (byte) (in[i] & 0x0F); // Strip off low nibble 
	    out.append(pseudo[ (int) ch]); // convert the nibble to a String Character
	    i++;
	}
	String rslt = new String(out);
	return rslt;
    }
    //
    public final static String getFileExtension(File file) {
	String ext = "";
	if(file == null) return ext;
	try {
	    String name = file.getName();
	    String pp = file.getAbsolutePath();
	    Path path = Paths.get(pp);
	    String fileType = Files.probeContentType(path);
	    if(fileType != null){
		// application/pdf
		if(fileType.endsWith("pdf")){
		    ext="pdf";
		}
		//image/jpeg
		else if(fileType.endsWith("jpeg")){
		    ext="jpg";
		}
		//image/gif
		else if(fileType.endsWith("gif")){
		    ext="gif";
		}
		//image/bmp
		else if(fileType.endsWith("bmp")){
		    ext="bmp";
		}
		// application/msword
		else if(fileType.endsWith("msword")){
		    ext="doc";
		}
		//application/vnd.ms-excel
		else if(fileType.endsWith("excel")){
		    ext="csv";
		}
		//application/vnd.openxmlformats-officedocument.wordprocessingml.document
		else if(fileType.endsWith(".document")){
		    ext="docx";
		}
		// text/plain
		else if(fileType.endsWith("plain")){
		    ext="txt";
		}
		//application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
		else if(fileType.endsWith(".sheet")){
		    ext="xlsx";
		}
		// audio/wav
		else if(fileType.endsWith("wav")){
		    ext="wav";
		}
		// text/xml
		else if(fileType.endsWith("xml")){
		    ext="xml";
		}										
		else if(fileType.endsWith("html")){
		    ext="html";
		}
		// video/mng
		else if(fileType.endsWith("mng")){
		    ext="mng";
		}
		else if(fileType.endsWith("mpeg")){
		    ext="mpg";
		}
		// video/mp4
		else if(fileType.endsWith("mp4")){
		    ext="mp4";
		}										
		else if(fileType.endsWith("avi")){
		    ext="avi";
		}
		else if(fileType.endsWith("mov")){
		    ext="mov";
		}
		// quick time video
		else if(fileType.endsWith("quicktime")){
		    ext="qt";
		}
		else if(fileType.endsWith("wmv")){
		    ext="wmv"; 
		}
		else if(fileType.endsWith("asf")){
		    ext="asf";
		}
		// flash video
		else if(fileType.endsWith("flash")){
		    ext="swf";
		}										
		else if(fileType.startsWith("image")){
		    ext="jpg";
		}
	    }
	} catch (Exception e) {
	    System.err.println(e);
	}
	return ext;
    }				
    //
    /**
     * Adds escape character before certain characters
     *
     */
    public final static String escapeIt(String s) {
		
	StringBuffer safe = new StringBuffer(s);
	int len = s.length();
	int c = 0;
	boolean noEscapeBefore = true;
	while (c < len) {                           
	    if ((safe.charAt(c) == '\'' ||
		 safe.charAt(c) == '"') && noEscapeBefore){
		safe.insert(c, '\\');
		c += 2;
		len = safe.length();
		noEscapeBefore = true;
	    }
	    else if(safe.charAt(c) == '\\'){ // to avoid double \\ before '
		noEscapeBefore = false;
		c++;
	    }
	    else {
		noEscapeBefore = true;
		c++;
	    }
	}
	return safe.toString();
    }
    //
    // users are used to enter comma in numbers such as xx,xxx.xx
    // as we can not save this in the DB as a valid number
    // so we remove it 
    public final static String cleanNumber(String s) {

	if(s == null) return null;
	String ret = "";
	int len = s.length();
	int c = 0;
	int ind = s.indexOf(",");
	if(ind > -1){
	    ret = s.substring(0,ind);
	    if(ind < len)
		ret += s.substring(ind+1);
	}
	else
	    ret = s;
	return ret;
    }
    /**
     * replaces the special chars that has certain meaning in html
     *
     * @param s the passing string
     * @returns string the modified string
     */
    public final static String replaceSpecialChars(String s) {
	char ch[] ={'\'','\"','>','<'};
	String entity[] = {"&#39;","&#34;","&gt;","&lt;"};
	//
	// &#34; = &quot;
	//
	String ret ="";
	int len = s.length();
	int c = 0;
	boolean in = false;
	while (c < len) {             
	    for(int i=0;i< entity.length;i++){
		if (s.charAt(c) == ch[i]) {
		    ret+= entity[i];
		    in = true;
		}
	    }
	    if(!in) ret += s.charAt(c);
	    in = false;
	    c ++;
	}
	return ret;
    }
    public final static String replaceQuote(String s) {
	char ch[] ={'\''};
	String entity[] = {"_"};
	//
	// &#34; = &quot;

	String ret ="";
	int len = s.length();
	int c = 0;
	boolean in = false;
	while (c < len) {             
	    for(int i=0;i< entity.length;i++){
		if (s.charAt(c) == ch[i]) {
		    ret+= entity[i];
		    in = true;
		}
	    }
	    if(!in) ret += s.charAt(c);
	    in = false;
	    c ++;
	}
	return ret;
    }
    /**
     * adds another apostrify to the string if there is any next to it
     *
     * @param s the passing string
     * @returns string the modified string
     */
    public final String doubleApostrify(String s) {
	StringBuffer apostrophe_safe = new StringBuffer(s);
	int len = s.length();
	int c = 0;
	while (c < len) {                           
	    if (apostrophe_safe.charAt(c) == '\'') {
		apostrophe_safe.insert(c, '\'');
		c += 2;
		len = apostrophe_safe.length();
	    }
	    else {
		c++;
	    }
	}
	return apostrophe_safe.toString();
    }
    public final static Connection getConnection(){
	return getConnectionProd();
    }
	
    public final static Connection getConnectionProd(){

	Connection con = null;
	int trials = 0;
	boolean pass = false;
	while(trials < 3 && !pass){
	    try{
		trials++;
		logger.debug("Connection try "+trials);
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/MySQL_proloan");
		con = ds.getConnection();
		if(con == null){
		    String str = " Could not connect to DB ";
		    logger.error(str);
		}
		else{
		    pass = testCon(con);
		    if(pass){
			c_con++;
			logger.debug("Got connection: "+c_con);
			logger.debug("Got connection at try "+trials);
		    }
		}
	    }
	    catch(Exception ex){
		logger.error(ex);
	    }
	}
	return con;
    }
    /**
     * we need to connect to Rental database to get neigborhood data
     */ 
    public final static Connection getConnectionOra(){

	Connection con = null;
	int trials = 0;
	boolean pass = false;
	while(trials < 3 && !pass){
	    try{
		trials++;
		logger.debug("Connection try "+trials);
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		DataSource ds = (DataSource)envCtx.lookup("jdbc/ora_rent");
		con = ds.getConnection();
		if(con == null){
		    String str = " Could not connect to DB ";
		    logger.error(str);
		}
		else{
		    pass = testCon2(con);
		    if(pass){
			c_con++;
			logger.debug("Got connection: "+c_con);
			logger.debug("Got connection at try "+trials);
		    }
		}
	    }
	    catch(Exception ex){
		logger.error(ex);
	    }
	}
	return con;
    }	
    //
    final static boolean testCon(Connection con){
	boolean pass = false;
	Statement stmt  = null;
	ResultSet rs = null;
	String qq = "select 1+1";		
	try{
	    if(con != null){
		stmt = con.createStatement();
		logger.debug(qq);
		rs = stmt.executeQuery(qq);
		if(rs.next()){
		    pass = true;
		}
	    }
	    rs.close();
	    stmt.close();
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	}
	return pass;
    }
    public final static void writeMedia(List<MediaFile> ones,
				 PrintWriter out,
				 String url){
	if(ones != null && ones.size() > 0){
	    out.println("<table border width=\"80%\">");
	    out.println("<tr><th>File</th><th>File Name</th><th>Date</th><th>User</th><th>Notes</th></tr>");
	    out.println("</tr>");
	    for(MediaFile one:ones){
		if(one.isImage()){
		    out.println("<tr><td><a href=\""+url+"FileDownload?id="+one.getId()+"\"><img src=\"FileDownload?type=thumb&id="+one.getId()+"\" /></a></td><td>"+one.getOld_file_name()+"</td><td>"+one.getDate()+"</td><td>"+one.getUser()+"</td><td>"+one.getNotes()+"</td></tr>");
		}
		else if(one.isPdf()){
		    out.println("<tr><td><a href=\""+url+"FileDownload?id="+one.getId()+"\"><img src=\""+url+"images/pdf_thumb.png\" /></a></td><td>"+one.getOld_file_name()+"</td><td>"+one.getDate()+"</td><td>"+one.getUser()+"</td><td>"+one.getNotes()+"</td></tr>");
		}
		else if(one.isSheet()){
		    out.println("<tr><td><a href=\""+url+"FileDownload?id="+one.getId()+"\"><img src=\""+url+"images/sheet_thumb.png\" /></a></td><td>"+one.getOld_file_name()+"</td><td>"+one.getDate()+"</td><td>"+one.getUser()+"</td><td>"+one.getNotes()+"</td></tr>");
		}												
		else{
		    out.println("<tr><td><a href=\""+url+"FileDownload?id="+one.getId()+"\"><img src=\""+url+"images/doc_thumb.png\" /></a></td><td>"+one.getOld_file_name()+"</td><td>"+one.getDate()+"</td><td>"+one.getUser()+"</td><td>"+one.getNotes()+"</td></tr>");
		}												
	    }
	    out.println("</table>");
	}
    }
    /*
     * testing oracle connections
     */
    final static boolean testCon2(Connection con){
	boolean pass = false;
	Statement stmt  = null;
	ResultSet rs = null;
	String qq = "select 1 from dual ";		
	try{
	    if(con != null){
		stmt = con.createStatement();
		logger.debug(qq);
		rs = stmt.executeQuery(qq);
		if(rs.next()){
		    pass = true;
		}
	    }
	    rs.close();
	    stmt.close();
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	}
	return pass;
    }	
    /**
     * Connect to Oracle database
     *
     * @param dbStr database connect string
     * @param dbUser database user string
     * @param dbPass database password string
     */
    public final static Connection databaseConnect(String dbStr, 
						   String dbUser, 
						   String dbPass) {
	Connection con=null;
	try {
	    Class.forName("oracle.jdbc.OracleDriver");
	    con = DriverManager.getConnection(dbStr,
					      dbUser,dbPass);

	}
	catch (Exception sqle) {
	    logger.error(sqle);
	}
	return con;
    }
	
    /**
     * Disconnect the database and related statements and result sets
     * 
     * @param con
     * @param stmt
     * @param rs
     */
    public final static void databaseDisconnect(Connection con,
						Statement stmt,
						ResultSet rs) {
	try {
	    if(rs != null) rs.close();
	    rs = null;
	    if(stmt != null) stmt.close();
	    stmt = null;
	    if(con != null) con.close();
	    con = null;
			
	    logger.debug("Closed Connection "+c_con);
	    c_con--;
	    if(c_con < 0) c_con = 0;
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
	finally{
	    if (rs != null) {
		try { rs.close(); } catch (SQLException e) { ; }
		rs = null;
	    }
	    if (stmt != null) {
		try { stmt.close(); } catch (SQLException e) { ; }
		stmt = null;
	    }
	    if (con != null) {
		try { con.close(); } catch (SQLException e) { ; }
		con = null;
	    }
	}
    }
    public final static void databaseDisconnect(Connection con,
						PreparedStatement stmt,
						ResultSet rs) {
	try {
	    if(rs != null) rs.close();
	    rs = null;
	    if(stmt != null) stmt.close();
	    stmt = null;
	    if(con != null) con.close();
	    con = null;
			
	    logger.debug("Closed Connection "+c_con);
	    c_con--;
	    if(c_con < 0) c_con = 0;
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
	finally{
	    if (rs != null) {
		try { rs.close(); } catch (SQLException e) { ; }
		rs = null;
	    }
	    if (stmt != null) {
		try { stmt.close(); } catch (SQLException e) { ; }
		stmt = null;
	    }
	    if (con != null) {
		try { con.close(); } catch (SQLException e) { ; }
		con = null;
	    }
	}
    }		
    public final static void databaseDisconnect(Connection con,
						ResultSet rs,
						Statement... stmt
						) {
	try {
	    if(rs != null) rs.close();
	    rs = null;
	    if(stmt != null){
		for(Statement one:stmt){
		    if(one != null)
			one.close();
		    one = null;
		}
	    }
	    if(con != null) con.close();
	    con = null;
	    logger.debug("Closed Connection "+c_con);
	    c_con--;
	    if(c_con < 0) c_con = 0;
	}
	catch (Exception e) {
	    e.printStackTrace();
	}
	finally{
	    if (rs != null) {
		try { rs.close(); } catch (SQLException e) { }
		rs = null;
	    }
	    if (stmt != null) {
		try {
		    for(Statement one:stmt){										
			if(one != null)
			    one.close(); 
			one = null;
		    }
		} catch (SQLException e) { }
	    }
	    if (con != null) {
		try { con.close(); } catch (SQLException e) { }
		con = null;
	    }
	}				
    }	
    /**
     * Write the number in bbbb.bb format needed for currency.
     * = toFixed(2)
     * @param dd the input double number
     * @returns the formated number as string
     */
    public final static String formatNumber(double dd){
	//
	String str = ""+dd;
	String ret="";
	int l = str.length();
	int i = str.indexOf('.');
	int r = i+3;  // required length to keep only two decimal
	// System.err.println(str+" "+l+" "+r);
	if(i > -1 && r<l){
	    ret = str.substring(0,r);
	}
	else{
	    ret = str;
	}
	return ret;
    }

    //
    // format a number with only 2 decimal
    // usefull for currency numbers
    //
    public final static String formatNumber(String that){

	String str = "";
	if(that != null){
	    int ind = that.indexOf(".");
	    int len = that.length();
	    
	    if(ind == -1){  // whole integer
		str = that + ".00";
	    }
	    else if(len-ind == 2){  // one decimal
		str = that + "0";
	    }
	    else if(len - ind > 3){ // more than two
		str = that.substring(0,ind+3);
	    }
	    else str = that;
	}
	return str;
    }

    //
    public final static String getToday(){

	String day="",month="",year="";
	Calendar current_cal = Calendar.getInstance();
	int mm =  (current_cal.get(Calendar.MONTH)+1);
	int dd =   current_cal.get(Calendar.DATE);
	year = ""+ current_cal.get(Calendar.YEAR);
	if(mm < 10) month = "0";
	month += mm;
	if(dd < 10) day = "0";
	day += dd;
	return month+"/"+day+"/"+year;
    }
    public final static int getCurrentYear(){

	int year = 0;
	Calendar current_cal = Calendar.getInstance();
	year = current_cal.get(Calendar.YEAR);
	return year;
    }		
    //
    // initial cap a word
    //
    public final static String initCapWord(String str_in){
	String ret = "";
	if(str_in !=  null){
	    if(str_in.length() == 0) return ret;
	    else if(str_in.length() > 1){
		ret = str_in.substring(0,1).toUpperCase()+
		    str_in.substring(1).toLowerCase();
	    }
	    else{
		ret = str_in.toUpperCase();   
	    }
	}
	return ret;
    }
    //
    // init cap a phrase
    //
    public final static String initCap(String str_in){
	String ret = "";
	if(str_in != null){
	    if(str_in.indexOf(" ") > -1){
		String[] str = str_in.split("\\s"); // any space character
		for(int i=0;i<str.length;i++){
		    if(i > 0) ret += " ";
		    ret += initCapWord(str[i]);
		}
	    }
	    else
		ret = initCapWord(str_in);// it is only one word
	}
	return ret;
    }
    final static User findUserFromList(List users, String empid){
	User foundUser = null;
	if(users != null && users.size() > 0){ 
	    for (int i=0;i<users.size();i++){
		User user = (User)users.get(i);
		if(user != null && user.getUsername().equals(empid)){
		    foundUser = user;
		    break;
		}
	    }
	}
	return foundUser;
    }
    public final static Vector<String> getInspectorNames(Statement stmt,
						  ResultSet rs,
						  boolean debug){
	Vector<String> vnames = new Vector<String>(10);
	String qq = "select * from citation_inspectors order by fname";
	try{
	    if(debug)
		logger.debug(qq);
	    rs = stmt.executeQuery(qq);
	    while (rs.next()){
		String name = rs.getString(1);
		vnames.add(name);
	    }
	}
	catch(Exception ex){
	    logger.error(ex+":"+qq);
	}
	return vnames;
    }

    public final static void writeWebCss(PrintWriter out,
					    String url
					    ){
	if(out != null && !url.isEmpty()){    
	    out.println("<link rel=\"stylesheet\" href=\""+url+"css/menu_style.css\" />");
	}
    }

    public final static void writeTopMenu(PrintWriter out,
					  String url
					  ){
	if(out != null && !url.isEmpty()){
	    out.println("<center>");
	    out.println("<h3>HAND - ProLoan</h3>");	    	    
	    out.println("<div id=\"div_top\">");
	    out.println("<ul id=\"ul_top\">");
	    out.println("<li><a href=\""+url+"Loan\">New Loan</a></li>");
	    out.println("<li><a href=\""+url+"Client\">New Client</a></li>");
	    out.println("<li><a href=\""+url+"Property\">New Property</a></li>");
	    out.println("<li><a href=\""+url+"Search\">Loans</a></li>");
	    out.println("<li><a href=\""+url+"PropSearch\">Properties</a></li>");
	    out.println("<li><a href=\""+url+"ClientSearch\">Clients</a></li>");
	    out.println("<li><a href=\""+url+"Report\">Reports</a></li>");
	    out.println("<li><a href=\""+url+"logout\">Logout</a></li>");	    
	    out.println("</ul>");
	    out.println("</div><br />");
	    out.println("</center>");	    
	}
    }
    
}






















































