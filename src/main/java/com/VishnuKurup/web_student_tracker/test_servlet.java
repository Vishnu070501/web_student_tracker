package com.VishnuKurup.web_student_tracker;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class test_servlet
 */
@WebServlet("/test_servlet")
public class test_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//step 1 getting the print writer 
		PrintWriter out = response.getWriter();
		
		//step 2:getting the connection to our postgre sql server 
		
		Connection c = null;
		
		try {
	         Class.forName("org.postgresql.Driver");
	         c = DriverManager.getConnection("jdbc:postgresql://localhost:5432/web_student_tracker",
	            "postgres", "webstudent");
	         Statement myStmt=c.createStatement();
	         String myQuery="select * from student order by last_name";
	         ResultSet myRs=myStmt.executeQuery(myQuery);
	         while (myRs.next()) {
	        	out.println(myRs.getString("email"));
	         }
	      } 
		
		catch (Exception e) {
	         e.printStackTrace();
	         System.err.println(e.getClass().getName()+": "+e.getMessage());
	         System.exit(0);
	      }
		
	}

}
