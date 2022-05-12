package com.VishnuKurup.web_student_tracker;
//this is a helper class that helps us to communicate with the database 

import java.sql.*;
import java.util.*;


public class student_db_util {
	Connection c=null;
	Statement myStmt = null;
	ResultSet myRs = null;
	PreparedStatement myStmt2=null;

	//jdbc code that displays all the students present in the database 
	public List<student> getStudents() throws Exception{
		
		ArrayList <student> myStudents = new ArrayList<student>();
		
		try {
			Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/web_student_tracker",
	            "postgres", "webstudent");
	         myStmt = c.createStatement();
	         myRs = myStmt.executeQuery("select * from student order by id");//query to display all students
	         while(myRs.next()) {
	        	 //getting all the data 
	        	 int ID = myRs.getInt("id");
	        	 String firstname = myRs.getString("first_name");
	        	 String lastname = myRs.getString("last_name");
	        	 String email = myRs.getString("email");
	        	 
	        	 //creating an instance of student
	        	 student mystud= new student(ID,firstname,lastname,email);
	        	 
	        	 //adding it to the array list of students 
	        	 myStudents.add(mystud);
	         }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			close(c,myStmt,myRs,null);
		}
		return myStudents;
		
	}
	
	//jdbc code that helps to close all the objects which prevents memory leaks
	private void close(Connection c2, Statement myStmt2, ResultSet myRs2,PreparedStatement myPSt2) {
		try {
			if(c2 != null)
				c2.close(); //does'nt delete the connection just puts it back in the pool
			if(myStmt2 != null) 
				myStmt2.close();
			if(myRs2 != null)
				myRs2.close();
			if(myPSt2 != null)
				myPSt2.close();
		}
		
		catch(Exception e) {
			e.printStackTrace();
			
		}
		
	}
	
	//jdbc code that helps to add students to the database 
	public void add_student(String firstname, String lastname, String email) {


		try {
			Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/web_student_tracker",
	            "postgres", "webstudent");
	         myStmt = c.createStatement();
	         myRs = myStmt.executeQuery("select * from student order by id");
	         int lastID = 0;
	        
	          
	         while (myRs.next()) {
	        	  lastID = myRs.getInt("id");
	          }//loop to find out the last entered id 
	         
	         //this is the statement that allows us to create a prepared statement where 
	         //there are no hard-coded values only placeholders 
	         String addQr="insert into student "+
	        		 	"(id,first_name,last_name,email) "+
	        		 	"values(?,?,?,?)"; //query to add a new record
	         myStmt2 = c.prepareStatement(addQr);
	         
	         //setting the parameter values 
	         myStmt2.setInt(1, lastID+1);
	         myStmt2.setString(2, firstname);
	         myStmt2.setString(3, lastname);
	         myStmt2.setString(4, email);
	         
	         myStmt2.execute();
	         
	         
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			close(c,myStmt,myRs,myStmt2);
		}
		
		
	}
	
	//jdbc code that gets the information on a student based on the id given
	public student get_student_on_ID(int theID) {
		
		try {
			//create the connection
			Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/web_student_tracker",
	            "postgres", "webstudent");
	        
	         //creating the statement
	         String loadQr="select * from student where id = ?";//query to get the details of a student based on the condition
	         myStmt2= c.prepareStatement(loadQr);
	         myStmt2.setInt(1, theID);
	         myRs=myStmt2.executeQuery();//got the student
	         
	         //creating the student variable to pass back to controller servlet
	         if(myRs.next()) {
	        	 student TheStudent = new student(myRs.getInt("id"),myRs.getString("first_name"),
		        		 myRs.getString("last_name"),myRs.getString("email"));
	        	 close(c,null,myRs,myStmt2);
		         
		         return TheStudent;
	         }
	         
	         else {
	        	 throw new Exception("Student with the student Id "+theID+" not Found");
	         }
	         //cleans all the jdbc objects 
		}
		
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	//jdbc code to update an existing record
	public void updateStudent(student myStudent) {
		try {
			//create connection
			Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/web_student_tracker",
	            "postgres", "webstudent");
	         
	         //creating the statement 
	         String upQr="update student "+
	         "set first_name=?, last_name=?, email=? "+
	        		 "where id=?";//query to update 
	         
	         //preparing the statement 
	         myStmt2 = c.prepareStatement(upQr);
	         
	         //setting up the parameters
	         myStmt2.setString(1, myStudent.getFirstname());
	         myStmt2.setString(2, myStudent.getLastname());
	         myStmt2.setString(3, myStudent.getEmail());
	         myStmt2.setInt(4, myStudent.getID());
	         
	         //executing the statement
	         myStmt2.execute();
	         
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			close(c,null,null,myStmt2);
		}
		
	}
	
	//function to delete a record 
	public void deleteStudent(int theID) {
		
		try {
			//create connection
			Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/web_student_tracker",
	            "postgres", "webstudent");
	         
	         //creating a statement 
	         String delQr="delete from student where id =?";//query to delete
	         
	         //prepare statement
	         myStmt2 = c.prepareStatement(delQr);
	         
	         //setting parameters 
	         myStmt2.setInt(1, theID);
	        		 
	        //execute query
	        myStmt2.execute(); 
	        		 
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			close(c,null,null,myStmt2);
		}
		
	}
	
}
