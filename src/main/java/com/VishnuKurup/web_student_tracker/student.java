package com.VishnuKurup.web_student_tracker;
//this student class just helps us store all the content that comes to us from the database

public class student {
	private int ID;
	private String firstname;
	private String lastname;
	private String email;
	
	//constructor with all the fields
	public student(int iD, String firstname, String lastname, String email) {
		super();
		this.ID = iD;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	//constructor without the id
	public student(String firstname, String lastname, String email) {
		super();
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}

	//the getters and setters for all the fields
	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "student [ID=" + ID + ", firstname=" + firstname + ", lastname=" + lastname + ", email=" + email + "]";
	}	
	
	//the toString method
	
}
