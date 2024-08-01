
package org.openjfx.srms;
import static org.openjfx.srms.LogController.push_log;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.openjfx.srms.Student;

import javafx.fxml.FXML;



public class JdbcDao {

    // Replace below database url, username and password with your actual database credentials
    private static final String INSERT_QUERY = "INSERT INTO user_registration (full_name, email_id, password) VALUES (?, ?, ?)";
    private static final String SELECT_QUERY = "SELECT id, email_id, final_grade, student_name, age, roll_no FROM student_result WHERE email_id=?";
    private static final String SEARCH_QUERY = "SELECT * FROM student_result WHERE email_id like ? ";
    private static final String SEARCH_Q = "SELECT id FROM user_registration WHERE email_id = ? and password = ?";
    private static final String DELETE_QUERY = "DELETE FROM student_result WHERE email_id = ?";
    private static final String SELECT_QUERY_ALL = "SELECT email_id, final_grade, student_name, age, roll_no FROM student_result";
    @FXML public List<Student> getPlans() throws SQLException, IOException {
        
        List<Student> plans = new ArrayList<>();
        JdbcDao jdbcDao = new JdbcDao();
        UserSession user = UserSession.getInstance();
        plans = jdbcDao.getRecord(user.getUserName());
        return plans;
    }
    private static final String ADD_QUERY = "INSERT INTO student_result (email_id, final_grade, student_name, age, roll_no)VALUES (?, ?, ?, ?, ?)";
    private static final String SET_QUERY = "UPDATE student_result set final_grade = ?, student_name=?, age = ?, roll_no = ? WHERE email_id = ?";
    public void insertRecord(String fullName, String emailId, String password) throws SQLException {

        // Step 1: Establishing a Connection and 
        // try-with-resource statement will auto close the connection.
        try (
                
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ood_project?allowPublicKeyRetrieval=true&useSSL=false","root","Abhi$0454");
            PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY) ){
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, emailId);
            preparedStatement.setString(3, password);
            System.out.println(preparedStatement);
            // Step 3: Execute the query or update query
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
    }
    
    public void addRecord(String email_id, String final_grade, String student_name, 
            int age, int roll_no) throws SQLException {

        // Step 1: Establishing a Connection and 
        // try-with-resource statement will auto close the connection.
        try (
                
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ood_project?allowPublicKeyRetrieval=true&useSSL=false","root","Abhi$0454");
            //INSERT INTO student_result (email_id, final_grade, student_name, age, roll_no) VALUES (?, ?, ?, ?, ?)
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_QUERY) )
            {
                preparedStatement.setString(1, email_id);
                preparedStatement.setString(2, final_grade);
                preparedStatement.setString(3, student_name);
                preparedStatement.setInt(4, age);
                preparedStatement.setInt(5, roll_no);
                System.out.println(preparedStatement);
                // Step 3: Execute the query or update query
                push_log("Add Student Result from user: "+email_id);
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
            // print SQL exception information
            printSQLException(e);
        }
    }
    
    public void setRecord(String email_id, String final_grade, String student_name, 
            int age, int roll_no ) throws SQLException
    {
        List<Student> plans = new ArrayList<>();
        try(
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ood_project?allowPublicKeyRetrieval=true&useSSL=false","root","Abhi$0454");
            PreparedStatement preparedStatement = connection.prepareStatement(SET_QUERY);) 
        {
            preparedStatement.setString(1,final_grade);
            preparedStatement.setString(2,student_name);
            preparedStatement.setInt(3, age);
            preparedStatement.setInt(4, roll_no);
            preparedStatement.setString(5,email_id);
            System.out.println(preparedStatement);
            push_log("Update Student Result from user: "+email_id+" with "+" Student Age: "+age+" and Enrollment No: "+roll_no);
            int row = preparedStatement.executeUpdate();
        }catch(SQLException e){
        
            printSQLException(e);     
        }
        return;
    }
    
    public List<Student> getRecord(String emailId) throws SQLException
    {
        List<Student> plans = new ArrayList<>();
        try(
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ood_project?allowPublicKeyRetrieval=true&useSSL=false","root","Abhi$0454");
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY);) 
        {
            preparedStatement.setString(1,emailId);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
               Student insure = new Student();
               insure.setId(rs.getInt("id"));
               System.out.println(insure.getId());
               insure.setEmail_id(rs.getString("email_id"));
               System.out.println(insure.getEmail_id());
               insure.setfinal_grade(rs.getString("final_grade"));
               System.out.println(insure.getfinal_grade());
               insure.setstudent_name(rs.getString("student_name"));
               System.out.println(insure.getstudent_name());
               insure.setage(rs.getInt("age"));
               System.out.println(insure.getage());
               insure.setroll_no(rs.getInt("roll_no"));
               System.out.println(insure.getroll_no());
               plans.add(insure);
            }
            push_log("Print the student result for teacher from records: "+ emailId);
            return plans;
        }catch(SQLException e){
        
            printSQLException(e);     
        }
        return plans;
    }
    public List<Student> searchRecord(String emailId) throws SQLException
    {
        List<Student> plans = new ArrayList<>();
        try(
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ood_project?allowPublicKeyRetrieval=true&useSSL=false","root","Abhi$0454");
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_QUERY);) 
        {
            preparedStatement.setString(1,"%"+emailId+"%");
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            System.out.println(rs);
            while(rs.next())
            {
               Student insure = new Student();
               insure.setId(rs.getInt("id"));
               System.out.println(insure.getId());
               insure.setEmail_id(rs.getString("email_id"));
               System.out.println(insure.getEmail_id());
               insure.setfinal_grade(rs.getString("final_grade"));
               System.out.println(insure.getfinal_grade());
               insure.setstudent_name(rs.getString("student_name"));
               System.out.println(insure.getstudent_name());
               insure.setage(rs.getInt("age"));
               System.out.println(insure.getage());
               insure.setroll_no(rs.getInt("roll_no"));
               System.out.println(insure.getroll_no());
               plans.add(insure);
            }
            return plans;
        }catch(SQLException e){
        
            printSQLException(e);     
        }
        return plans;
    }
        
    public boolean searchRecord(String emailId, String password) throws SQLException
    {
        List<Student> plans = new ArrayList<>();
        try(
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ood_project?allowPublicKeyRetrieval=true&useSSL=false","root","Abhi$0454");
            PreparedStatement preparedStatement = connection.prepareStatement(SEARCH_Q);) 
        {
            preparedStatement.setString(1,emailId);
            preparedStatement.setString(2,password);
            System.out.println(preparedStatement);
            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next())
            {
                return true;
            }     
        }catch(SQLException e){
        
            printSQLException(e);     
        }
        return false;
    }
    
    public boolean searchAdminRecord(String emailId, String password) 
    {
        if(emailId.equals("sysadmin") && password.equals("abcd1234"))
        {
            return true;
        }
        return false;
    }
    public void deleteRecord(String emailid) {
        try(
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ood_project?allowPublicKeyRetrieval=true&useSSL=false","root","Abhi$0454");
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY);) 
        {
            preparedStatement.setString(1,emailid);
            push_log("Delete the student result entry from user: "+ emailid);
            int row = preparedStatement.executeUpdate();  
        }catch(SQLException e){
        
            printSQLException(e);     
        }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }
    
    public void printRecordToCsv() throws SQLException
    {

        try {
        	Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ood_project?allowPublicKeyRetrieval=true&useSSL=false","root","Abhi$0454");
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY_ALL);
         

            FileWriter writer = new FileWriter("student_output.csv");
            writer.append("teacher name,roll_no,student name,final grade,age\n"); // Column headers
      
            System.out.println(preparedStatement);
            
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next())
            {
               Student insure = new Student();
               
               insure.setEmail_id(rs.getString("email_id"));
              
               insure.setfinal_grade(rs.getString("final_grade"));
               
               insure.setstudent_name(rs.getString("student_name"));
           
               insure.setage(rs.getInt("age"));
              
               insure.setroll_no(rs.getInt("roll_no"));
             
               writer.append(insure.getEmail_id()+","+rs.getInt("roll_no") + "," + rs.getString("student_name") + "," + insure.getfinal_grade()+","+rs.getInt("age") + "\n"); // Row data
            }

            writer.flush();
            writer.close();

            System.out.println("CSV file generated successfully.");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    
}
