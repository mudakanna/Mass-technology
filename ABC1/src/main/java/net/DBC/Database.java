package net.DBC;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Database
 */
@WebServlet("/Database")
public class Database extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Database() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//Connection con = DatabaseConnection.initializeDatabase();
			
			String dbDriver = "com.mysql.jdbc.Driver";
			String dbURL = "jdbc:mysql://localhost:3306/db";
			
			//String dbName = "db";
			String dbUsername = "root";
			String dbPasssword ="root";
			
			Class.forName(dbDriver);
			
			Connection con = DriverManager.getConnection(dbURL, dbUsername, dbPasssword);
			PreparedStatement st = con.prepareStatement("insert into registration values(?,?,?,?,?)");
			
			st.setString(1, request.getParameter("First name"));
			
			st.setString(2, request.getParameter("Last name"));
			
			st.setString(3, request.getParameter("Email"));
			
			st.setString(4, request.getParameter("Division"));
			
			st.setString(5, request.getParameter("Class"));
			
			st.executeUpdate();
			
			st.close();
			con.close();
			
			PrintWriter out = response.getWriter();
			out.println("<html><body><b>Successfully Inserted" + "</b></body></html>");
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
