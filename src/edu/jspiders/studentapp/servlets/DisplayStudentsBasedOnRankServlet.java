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

public class DisplayStudentsBasedOnRankServlet extends HttpServlet
{
	private static ArrayList<Student> allStudents;
	@Override
	public void init() throws ServletException 
	{
		StudentDataBaseOperations operations = new StudentDataBaseOperations();
		allStudents = operations.getAllStudents();
	}
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
			
			String rank = req.getParameter("rank");

			if(rank.equalsIgnoreCase("FCD"))
			{
				ArrayList<Student> fcdStudents = getFCDStudents();

				if(fcdStudents.isEmpty())
				{
					out.print("<h1>No records Found!!!!</h1>");
				}
				else
				{
					String table = displayInTable(fcdStudents);
					out.print(table);
				}
			}

			else if(rank.equalsIgnoreCase("FC"))
			{
				ArrayList<Student> fcStudents = getFCStudents();

				if(fcStudents.isEmpty())
				{
					out.print("<h1>No records Found!!!!</h1>");
				}
				else
				{
					String table = displayInTable(fcStudents);
					out.print(table);
				}
			}
			else if(rank.equalsIgnoreCase("SC"))
			{
				ArrayList<Student> scStudents = getSCStudents();

				if(scStudents.isEmpty())
				{
					out.print("<h1>No records Found!!!!</h1>");
				}
				else
				{
					String table = displayInTable(scStudents);
					out.print(table);
				}
			}
			else if(rank.equalsIgnoreCase("FAil"))
			{
				ArrayList<Student> failedStudents = getFailedStudents();

				if(failedStudents.isEmpty())
				{
					out.print("<h1>No records Found!!!!</h1>");
				}
				else
				{
					String table = displayInTable(failedStudents);
					out.print(table);
				}
			}
		}
		else
		{
			req.getRequestDispatcher("loginStudent.html").forward(req, resp);
		}


	}


	private String displayInTable(ArrayList<Student> allStudents)
	{
		String table = "<table border='1' align='center'>"
				+ "<tr>"
				+ "<th>ID</th>"
				+ "<th>First Name</th>"
				+ "<th>Last Name</th>"
				+ "<th>Marks</th>"
				+ "<th>Email ID</th>"
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
					+ "</tr>" ;
			table = table + htmlRow;
		}
		table = table + "</table>";
		return table;
	}
	private ArrayList<Student> getFailedStudents() {
		ArrayList<Student> students = new ArrayList<Student>();

		for (Student student : allStudents)
		{
			if(student.getMarks() < 35)
			{
				students.add(student);
			}
		}
		return students;
	}
	private ArrayList<Student> getSCStudents()
	{
		ArrayList<Student> students = new ArrayList<Student>();

		for (Student student : allStudents)
		{
			if(student.getMarks() >= 50 && student.getMarks() <= 60)
			{
				students.add(student);
			}
		}
		return students;
	}
	private ArrayList<Student> getFCStudents()
	{
		ArrayList<Student> students = new ArrayList<Student>();

		for (Student student : allStudents)
		{
			if(student.getMarks() >= 60 && student.getMarks() <= 70)
			{
				students.add(student);
			}
		}
		return students;
	}
	private ArrayList<Student> getFCDStudents() 
	{
		ArrayList<Student> students = new ArrayList<Student>();

		for (Student student : allStudents)
		{
			if(student.getMarks() > 70)
			{
				students.add(student);
			}
		}
		return students;
	}
}
