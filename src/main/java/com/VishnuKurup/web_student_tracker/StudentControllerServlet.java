package com.VishnuKurup.web_student_tracker;

import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StudentControllerServlet
 */
@WebServlet("/StudentControllerServlet")
public class StudentControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private student_db_util studentDB=new student_db_util();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmd = request.getParameter("command");
		if(cmd==null)
			cmd="LIST";
		
		
		try {
			switch(cmd) {
			
				case "LIST":
					//to show all the students in database 
					listStudents(request,response);
					break;
					
				case "ADD":
					//to add new students in the database 
					addStudent(request,response);
					break;
					
				case "LOAD":
					//to get information on the student to update and show those information in the
					//update form as default values
					getStudent(request,response);
					break;
					
				case "UPDATE":
					updateStudent(request,response);
					break;
					
				case "DELETE":
					deleteStudent(request,response);
					break;
					
				default :
					listStudents(request,response);
					break;
				
			}
				
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//function to delete a record 
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) {
		
		//getting the id of the student to be deleted 
		int theID = Integer.parseInt(request.getParameter("studentID"));
		
		//calling the function in the student db util to delete a record
		studentDB.deleteStudent(theID);
		
		//listing the students from the database
		try {
			listStudents(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	private void updateStudent(HttpServletRequest request, HttpServletResponse response) {
		  
		//get the id from the request object and creating the student object to pass 
		// to the update function in the database util
		
		student myStudent = new student(Integer.parseInt(request.getParameter("student_id")),
				request.getParameter("firstName"),request.getParameter("lastName"),
				request.getParameter("email"));
	
		
		//pass the id to the update method in studentDB util
		studentDB.updateStudent(myStudent);
		
		//list out the new list
		try {
			listStudents(request,response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	private void getStudent(HttpServletRequest request, HttpServletResponse response) {
		
		//get the student id from the update form 
				int theID=Integer.parseInt(request.getParameter("studentID"));
						
			
		//get the information on that student from the db using the student db util
				student theStudent=studentDB.get_student_on_ID(theID);
		//attach those information to the request object and dispatching it to the 
				RequestDispatcher dispatcher = request.getRequestDispatcher("/update-student-form.jsp");
				request.setAttribute("theStudent", theStudent);
			

				try {
					dispatcher.forward(request, response);
				} catch (ServletException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}


	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		
		//get all the parameters that come from the form 
		String firstname = request.getParameter("firstName");
		
		String lastname = request.getParameter("lastName");
		
		String email = request.getParameter("email");
		
		studentDB.add_student(firstname,lastname,email);
		
		listStudents(request , response);
	}

	private void listStudents(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
			List<student> myStudents = studentDB.getStudents();
			
			//getting the request dispatcher 
			RequestDispatcher dispatcher = request.getRequestDispatcher("/list-students.jsp");
			
			//adding attributes to the request object
			request.setAttribute("myStudents", myStudents);
			
			//dispatching the request to the display jsp
			dispatcher.forward(request, response);
		
	}

}
