package edu.jspiders.studentapp.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.jspiders.studentapp.dao.StudentDataBaseOperations;
import edu.jspiders.studentapp.dto.Student;

public class SearchStudentServlet extends HttpServlet
{
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException 
	{
		resp.setContentType("text/html");

		PrintWriter out = resp.getWriter();

		HttpSession session = req.getSession(false);


		req.getRequestDispatcher("/header.html").include(req, resp);

		if(session != null)
		{

			req.getRequestDispatcher("/navigation.html").include(req, resp);


			String nm = (String) session.getAttribute("name");
			out.print("<h1>Welcome "+nm+"</h1>");
			
			String sid = req.getParameter("id");

			int id = Integer.parseInt(sid);

			StudentDataBaseOperations operations = new StudentDataBaseOperations();
			Student searchedStudent = operations.searchStudent(id);

			if(searchedStudent != null)
			{
				//display in the form of table;

				String table = "<table border='1' align='center'>"
						+ "<tr>"
						+ "<th>ID</th>"
						+ "<th>First Name</th>"
						+ "<th>Last Name</th>"
						+ "<th>Marks</th>"
						+ "</tr>"
						+ "<tr>"
						+ "<td>"
						+ searchedStudent.getId()
						+ "</td>"
						+ "<td>"
						+ searchedStudent.getFirstName()
						+ "</td>"
						+ "<td>"
						+ searchedStudent.getLastName()
						+ "</td>"
						+ "<td>"
						+ searchedStudent.getMarks()
						+ "</td>"
						+ "</tr>"
						+ "</table>";

				out.print(table);
			}
			else
			{
				out.print("<h1>Student data is not present for id "+sid+"</h1>");
			}
		}
		else
		{
			req.getRequestDispatcher("loginStudent.html").forward(req, resp);
		}
	}
}
