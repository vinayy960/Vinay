package edu.jspiders.studentapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.jspiders.studentapp.dao.StudentDataBaseOperations;
import edu.jspiders.studentapp.dto.Student;

public class DisplayAllStudentsServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException
	{
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		req.getRequestDispatcher("/header.html").include(req, resp);

		HttpSession session = req.getSession(false);
		if(session != null)
		{

			req.getRequestDispatcher("/navigation.html").include(req, resp);
			
			String nm = (String) session.getAttribute("name");
			out.print("<h1>Welcome "+nm+"</h1>");

			StudentDataBaseOperations operations = new StudentDataBaseOperations();
			ArrayList<Student> allStudents = operations.getAllStudents();
			
			out.print("<link rel=\"stylesheet\" href=\"./resources/css/mystyle2.css\">");
			if(allStudents.isEmpty())
			{
				out.print("<h1>No records Found!!!!</h1>");
			}
			else
			{
				String table = "<table border='1' align='center'>"
						+ "<tr>"
						+ "<th>ID</th>"
						+ "<th>First Name</th>"
						+ "<th>Last Name</th>"
						+ "<th>Marks</th>"
						+ "<th>Email ID</th>"
						+ "<th>EDIT</th>"
						+ "<th>DELETE</th>"
						+ "</tr>";

				Iterator<Student> studItr = allStudents.iterator();
				while (studItr.hasNext()) 
				{
					Student student = (Student) studItr.next();
					String htmlRow = "<tr>"
							+ "<td>"+student.getId()+"</td>"
							+ "<td>"+student.getFirstName()+"</td>"
							+ "<td>"+student.getLastName()+"</td>"
							+ "<td>"+student.getMarks()+"</td>"
							+ "<td>"+student.getEmail()+"</td>"
							+ "<td><a href='./edit?id="+student.getId()+"'>edit</a></td>"
							+ "<td><a href='./delete?id="+student.getId()+"'>delete</a></td>"
							+ "</tr>" ;
					table = table + htmlRow;
				}
				table = table + "</table>";
				out.print(table);
			}
		}
		else
		{
			req.getRequestDispatcher("loginStudent.html").forward(req, resp);
		}
	}
}
