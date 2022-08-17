package edu.jspiders.studentapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.jspiders.studentapp.dao.StudentDataBaseOperations;
import edu.jspiders.studentapp.dto.Student;

public class RegisterStudentServlet  extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
	{
		resp.setContentType("text/html");

		PrintWriter out=resp.getWriter();

		String sid=req.getParameter("id");
		int id=Integer.parseInt(sid);
		String fname=req.getParameter("fname");
		String lname=req.getParameter("lname");
		String mark=req.getParameter("marks");
		double marks=Double.parseDouble(mark);
		String email=req.getParameter("Email");
		String password=req.getParameter("Password");

		Student s=new Student();
		s.setId(id);
		s.setFirstName(fname);
		s.setLastName(lname);
		s.setMarks(marks);
		s.setEmail(email);
		s.setPassword(password);

		StudentDataBaseOperations operations=new StudentDataBaseOperations();
		if(operations.registerStudent(s))
			out.println("<h1>Student Data Registered</h1>");
		else
			out.println("<h1>Student Data Not Registered</h1>");
	}
}

