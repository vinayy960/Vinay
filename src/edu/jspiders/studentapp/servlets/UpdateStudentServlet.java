package edu.jspiders.studentapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.jspiders.studentapp.dao.StudentDataBaseOperations;
import edu.jspiders.studentapp.dto.Student;

@WebServlet("/update")
public class UpdateStudentServlet  extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/html");

		PrintWriter out=resp.getWriter();

		HttpSession session = req.getSession(false);

		if(session != null)
		{
			String sid=req.getParameter("id");
			int id=Integer.parseInt(sid);
			String fname=req.getParameter("fname");
			String lname=req.getParameter("lname");
			String mark=req.getParameter("marks");
			double marks=Double.parseDouble(mark);
			String email=req.getParameter("Email");

			Student s=new Student();
			s.setId(id);
			s.setFirstName(fname);
			s.setLastName(lname);
			s.setMarks(marks);
			s.setEmail(email);

			StudentDataBaseOperations operations=new StudentDataBaseOperations();
			operations.updateStudent(s);


			req.getRequestDispatcher("/allStudents").forward(req, resp);
		}
		else
		{
			req.getRequestDispatcher("/loginStudent.html").forward(req, resp);
		}

	}
}
