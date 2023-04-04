package ploan.model;


public class Touple{

    int id;
    String name;
    // 
    // constructor
    public Touple(int id, String name){
	this.id = id;
	this.name = name;
    }
    //
    // empty constructor
    //
    public Touple(){
	this(0,"");
    }

    public int getId(){
	return id;
    }
    
    public String getName(){
	return name;
    }

}
