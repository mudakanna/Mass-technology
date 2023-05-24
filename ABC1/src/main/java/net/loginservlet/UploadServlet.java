package net.loginservlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet("/file-upload")
@MultipartConfig(maxFileSize = 1024 * 1024 * 5) // max file size of 5MB
public class UploadServlet extends HttpServlet {
  
  // Database connection parameters
  private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
  private static final String DB_URL = "jdbc:mysql://localhost:3306/db";
  private static final String DB_USERNAME = "root";
  private static final String DB_PASSWORD = "root";
  
  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    // Get file details from request
    Part filePart = request.getPart("fupload");
    String fileName = filePart.getSubmittedFileName();
    InputStream fileContent = filePart.getInputStream();
    
    // Open database connection
    Connection conn = null;
    try {
      Class.forName(DB_DRIVER);
      conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Failed to connect to database");
      return;
    }
    
    // Prepare statement to insert file into database
    String sql = "INSERT INTO files1 (name, content) VALUES (?, ?)";
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, fileName);
      stmt.setBlob(2, fileContent);
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
      response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
          "Failed to upload file to database");
      return;
    } finally {
      if (conn != null) {
        try {
          conn.close();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }
    
    // Redirect to success page
    response.sendRedirect("Registration.html");
  }
}
