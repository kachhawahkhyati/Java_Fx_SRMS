/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.openjfx.srms;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openjfx.srms.Student;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Window;

/**
 *
 *
 */
public class PostLoginController implements Initializable {
    
    @FXML
    private Label headval;
    
    @FXML
    private Label email_id;

    @FXML
    private Label roll_no;
   
    @FXML
    private Label premium;
    
    @FXML
    private Label student_name;
    
    @FXML
    private Label final_grade;
    
    @FXML
    private Button add_plan;
    
    @FXML
    private Button update_plan;
    
    @FXML
    private Button new_plan;
    
    @FXML
    private Button delete_plan;
    
    @FXML
    private void switchToPlanlist() throws IOException {
        App.setRoot("plans");
    }

    @FXML public List<Student> getPlans() throws SQLException, IOException {
        
        List<Student> plans = new ArrayList<>();
        JdbcDao jdbcDao = new JdbcDao();
        UserSession user = UserSession.getInstance();
        plans = jdbcDao.getRecord(user.getUserName());
        return plans;
    }
            
    /**
     *
     * @param url
     * @param rb
     * @throws SQLException
     * @throws IOException
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Insurance insure = new Insurance();
        UserSession user = UserSession.getInstance();
        headval.setText("Hi "+user.getUserName());
        List<Student> plans = new ArrayList<>();
        try {
            plans = getPlans();
            Iterator i = plans.iterator();
            if(i.hasNext())
            {
                add_plan.setVisible(false);
            }
            else
            {
//            	add_plan.setVisible(false);//added this line
                update_plan.setVisible(false);
                delete_plan.setVisible(false);
                new_plan.setVisible(false);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostLoginController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PostLoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //student_result.setText("Insurance values");
        
        Iterator<Student> iterate = plans.iterator();
        String emailid = "";
        while(iterate.hasNext())
        {   
                Student val = new Student();
                val = iterate.next();
                premium.setText("Student Age: "+val.age);
                roll_no.setText("Student Enrollment No: "+ val.roll_no);
                student_name.setText("Student Name: "+val.student_name);
//                email_id.setText("Teacher Email Id: "+val.email_id);
                emailid = val.email_id;
                final_grade.setText("Student Final Grade: "+val.final_grade);
                System.out.println(val.final_grade);
        }
        //Button add_plan = new Button();
        add_plan.setOnAction(new BtHandler());
        update_plan.setOnAction(new BtHandler());
        delete_plan.setOnAction(new BtHandler2());
        new_plan.setOnAction(new BtHandler2());
        return;
    }
    
    class BtHandler2 implements EventHandler   
    {       
        @Override
        public void handle(Event t) {
            System.out.println("Button handle event");
            JdbcDao jdbcdao = new JdbcDao();
            UserSession user = UserSession.getInstance();
            
            String email = user.getUserName();
            jdbcdao.deleteRecord(email);
            try {
                App.setRoot("PostLogin");
            } catch (IOException ex) {
                Logger.getLogger(PostLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    class BtHandler implements EventHandler   
    {       
        @Override
        public void handle(Event t) {
            try {
                System.out.println("Button handle event");
                App.setRoot("addplan");
            } catch (IOException ex) {
                Logger.getLogger(PostLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    @FXML
    private void switchToLogin() throws IOException{
        App.setRoot("Login");
    }
    
    
    @FXML
    private void switchToLog() throws IOException{
        App.setRoot("Log");
    }
}

