package edu.jspiders.studentapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.jspiders.studentapp.dao.StudentDataBaseOperations;

public class LoginStudentServlet extends HttpServlet
{
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		String mailid = req.getParameter("mailId");
		String pswd = req.getParameter("password");
		
		StudentDataBaseOperations op = new StudentDataBaseOperations();
		String name = op.loginValidate(mailid,pswd);
		
		if(name == null)
		{
			//out.print("<h1 style='color: red;'>Invalid Credentials!!!!</h1>");
			
			String url = "/loginStudent.html";
			RequestDispatcher dispatcher = req.getRequestDispatcher(url);
			dispatcher.forward(req, resp);
		}
		else
		{
			HttpSession session = req.getSession();
			session.setMaxInactiveInterval(60*30);
			
			session.setAttribute("name", name);
			
			//out.print("<h1 style='color: green;'>Welcome "+name+"</h1>");
			req.getRequestDispatcher("dashboard.html").forward(req, resp);
		}
	}
}
