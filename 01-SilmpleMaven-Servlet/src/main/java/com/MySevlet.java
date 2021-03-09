package com;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
/**
 * Servlet implementation class MySevlet
 */
@WebServlet("/MyServlet")
public class MySevlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MySevlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
	        	PrintWriter out = response.getWriter();
                String test = request.getParameter("product");             
	            out.println("<html><body>");
	            //I know hard coding the connection is not industry standard. I just got a bit lazy because these are practice programs. But... I used a 'PreparedStatement'! :D
	            //This code is meant to run off the databases that come with MySQL when installed, so no need to make the database, but I have included the SQL in a document for you just in case you no longer have the database.
	            DBConnection conn = new DBConnection("jdbc:mysql://localhost:3306/stock", "EnterDBUserNameHere", "EnterPasswordHere");
	        	Statement stmt = conn.getConnection().createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        	String rstSet = "select * from products where (name = ?)";
	        	final PreparedStatement prepState = conn.getConnection().prepareStatement(rstSet);
	        	prepState.setString(1, test);
	            ResultSet rst =  prepState.executeQuery();	            
	            if(!rst.next()) {
	            	out.print("<center> <span style='color:red'>ERROR: No match in database for requested search</span></center>");
	            }else {
	            	 out.println("<table><tr><th>ID Number</th><th>Name</th><th>Count</th></tr>");
	            	 do {
	            		 out.println("<tr><td>" + rst.getString("id") + "</td>" + "<td>" +
	            		 rst.getString("name") + "</td><td>" + rst.getString("count") + "</td><td>\n");
	            	 }while(rst.next());//end of 'do-while'
	            }//end of 'if statement'	            
	            out.println("</table>");	        
	            stmt.close();        	        
	            out.println("</body></html>");
	            conn.closeConnection();	        
	        } catch (ClassNotFoundException e) {
	        	e.printStackTrace();
	        } catch (SQLException e) {
	        	e.printStackTrace();
	        } catch (Exception e) {
	        	e.printStackTrace();
	        }//end of 'try-catch' block
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
