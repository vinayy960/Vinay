package edu.jspiders.studentapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.jspiders.studentapp.dao.StudentDataBaseOperations;

@WebServlet("/updatePswd")
public class UpdatePasswordServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException 
	{
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		String email = request.getParameter("mailId");
		String newPassword = request.getParameter("newpassword");
		
		StudentDataBaseOperations op = new StudentDataBaseOperations();
		boolean isUpdated = op.updatePassword(email,newPassword);
		
		if(isUpdated)
		{
			request.getRequestDispatcher("/loginStudent.html").forward(request, response);
		}
		else
		{
			out.print("<h1 style='background: red; color: #aeaeae;'>Invalid Email ID</h1>");
			request.getRequestDispatcher("/forgetPassword.html").include(request, response);
		}
	}

}
