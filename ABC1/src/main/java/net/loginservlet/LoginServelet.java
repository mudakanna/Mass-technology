package net.loginservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.DBC.DatabaseConnection;
import net.DBC.DatabaseConnection1;
/**
 * 
 */

@WebServlet("/LoginServelet")
public class LoginServelet extends HttpServlet {
    private static final long serialVersionUID = 1 ;
    /*private LoginDao loginDao;

    public void init() {
        loginDao = new LoginDao();
    }*/

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
    	String username = request.getParameter("username");
        String password = request.getParameter("password");
    	try {
			Connection con = DatabaseConnection.initializeDatabase();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from login where username = '"+username+"' and password = '"+password+"'");
			if(rs.next()) {
				response.sendRedirect("/Data_Deduplication_/src/main/webapp/Upload.html");
			}
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}