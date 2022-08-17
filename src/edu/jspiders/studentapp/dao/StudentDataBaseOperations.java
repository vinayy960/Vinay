package edu.jspiders.studentapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import edu.jspiders.studentapp.dto.Student;

public class StudentDataBaseOperations 
{
	private final static String DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private final static String DB_URL = "jdbc:mysql://localhost:3306/hejm33_db?user=root&password=root";

	public Student searchStudent(int id)
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			Class.forName(DRIVER_CLASS);

			con = DriverManager.getConnection(DB_URL);

			String query = "SELECT * FROM student_table WHERE id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, id);

			rs = pstmt.executeQuery();

			if(rs.next())
			{
				int sid = rs.getInt(1);
				String fname = rs.getString(2);
				String lname = rs.getString(3);
				double marks = rs.getDouble(4);
				String email = rs.getString(5);

				Student s = new Student();
				s.setId(sid);
				s.setFirstName(fname);
				s.setLastName(lname);
				s.setMarks(marks);
				s.setEmail(email);
				return s;
			}

		} 
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			if(pstmt != null)
			{
				try 
				{
					pstmt.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			if(rs != null)
			{
				try 
				{
					rs.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

		return null;

	}

	public boolean registerStudent(Student s) 
	{

		Connection con = null;
		PreparedStatement pstmt = null;		
		try
		{
			Class.forName(DRIVER_CLASS);

			con = DriverManager.getConnection(DB_URL);

			String query="INSERT INTO student_table VALUES(?,?,?,?,?,?)";
			pstmt=con.prepareStatement(query);
			pstmt.setInt(1,s.getId());
			pstmt.setString(2, s.getFirstName());
			pstmt.setString(3, s.getLastName());
			pstmt.setDouble(4, s.getMarks());
			pstmt.setString(5, s.getEmail());
			pstmt.setString(6, s.getPassword());
			int rowsAffected=pstmt.executeUpdate();

			//Process the Result

			return (rowsAffected >0);

		} 
		catch (Exception e)
		{
			e.printStackTrace();
		} 
		finally 
		{

			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			if(pstmt != null)
			{
				try 
				{
					pstmt.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}

		return false;

	}

	public String loginValidate(String mailid, String pswd) 
	{
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try
		{
			Class.forName(DRIVER_CLASS);
			con = DriverManager.getConnection(DB_URL);

			String query = "SELECT first_name FROM student_table WHERE email_id = ? AND password = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, mailid);
			pstmt.setString(2, pswd);

			rs = pstmt.executeQuery();

			if(rs.next())
			{
				return rs.getString(1);
			}
			else
				return null;
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			if(pstmt != null)
			{
				try 
				{
					pstmt.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			if(rs != null)
			{
				try 
				{
					rs.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public ArrayList<Student> getAllStudents() 
	{
		ArrayList<Student> allStudentsList = new ArrayList<Student>();

		Connection con =  null;
		Statement stmt =null;
		ResultSet rs = null;
		try 
		{
			Class.forName(DRIVER_CLASS);
			con = DriverManager.getConnection(DB_URL);
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM student_table");

			while (rs.next()) 
			{
				Student s = new Student();

				s.setId(rs.getInt(1));
				s.setFirstName(rs.getString(2));
				s.setLastName(rs.getString(3));
				s.setMarks(rs.getDouble(4));
				s.setEmail(rs.getString(5));

				allStudentsList.add(s);
			}

		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally
		{
			if(con != null)
			{
				try
				{
					con.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(stmt != null)
			{
				try 
				{
					stmt.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(rs != null)
			{
				try 
				{
					rs.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
		}


		return allStudentsList;
	}

	public boolean updatePassword(String email, String newPassword)
	{
		boolean isUpdated = false;

		Connection con = null;
		PreparedStatement pstmt = null;
		try 
		{
			Class.forName(DRIVER_CLASS);
			con = DriverManager.getConnection(DB_URL);

			String query = "UPDATE student_table SET password = ? WHERE email_id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, email);

			int rowsAffected = pstmt.executeUpdate();

			isUpdated = rowsAffected != 0;

		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
		return isUpdated;
	}

	public void updateStudent(Student s) 
	{
		//UPDATE student_table set first_name = ? , last_name = ? ,marks = ?, email=? WHERE id = ?


		Connection con = null;
		PreparedStatement pstmt = null;
		try 
		{
			Class.forName(DRIVER_CLASS);
			con = DriverManager.getConnection(DB_URL);

			String query = "UPDATE student_table set first_name = ? , last_name = ? ,marks = ?, email_id = ? WHERE id = ?";
			pstmt = con.prepareStatement(query);
			pstmt.setString(1, s.getFirstName());
			pstmt.setString(2, s.getLastName());
			pstmt.setDouble(3, s.getMarks());
			pstmt.setString(4, s.getEmail());
			pstmt.setInt(5, s.getId());

			int rowsAffected = pstmt.executeUpdate();



		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally 
		{
			if(con != null)
			{
				try 
				{
					con.close();
				} 
				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}
			if(pstmt != null)
			{
				try
				{
					pstmt.close();
				} 
				catch (SQLException e) 
				{
					e.printStackTrace();
				}
			}
		}
	}
}
