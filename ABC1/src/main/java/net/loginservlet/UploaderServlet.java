package net.loginservlet;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebServlet("/FileUploadServlet")
@MultipartConfig
public class UploaderServlet extends HttpServlet {
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String fileName = null;
        InputStream fileContent = null;
        
        // Get the file chosen by the user
        Part filePart = request.getPart("file");
        fileName = filePart.getSubmittedFileName();
        fileContent = filePart.getInputStream();
        
        // Save the file to a folder on the local drive
        String savePath = "D:\\new";
        File fileSaveDir = new File(savePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        File file = new File(savePath + fileName);
        OutputStream outputStream = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        while ((bytesRead = fileContent.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
        outputStream.close();
        
        // Save the file path to the database using JDBC
        Connection conn = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "root", "root");
            stmt = conn.prepareStatement("INSERT INTO files3 (file_name, file_path) VALUES (?, ?)");
            stmt.setString(1, fileName);
            stmt.setString(2, savePath + fileName);
            stmt.executeUpdate();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // Display a message to the user
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h3>File " + fileName + " uploaded successfully!</h3>");
        out.println("</body></html>");
    }
}
