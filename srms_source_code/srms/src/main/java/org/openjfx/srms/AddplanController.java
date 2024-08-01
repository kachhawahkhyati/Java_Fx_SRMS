/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;



/**
 * FXML Controller class
 *
 * 
 */
public class AddplanController implements Initializable {
    
    @FXML
    private Label welcome_string;
    @FXML
    private ComboBox final_grade;
    @FXML
    private ComboBox student_name;
    @FXML
    private ComboBox roll_no;
    @FXML
    private ComboBox monthly_payment;
    @FXML
    private Button plan_button;
    @FXML 
    private Button update_button;
    @FXML
    private String instype;
    @FXML
    private String insname;
    @FXML
    private String roll_noval;
    @FXML
    private int payment;
    
    private final ObservableList<String> ins_type = FXCollections.observableArrayList("O","A","B","C","D","E","F");
    private final ObservableList<String> ins_name = FXCollections.observableArrayList("Kate","Ross","Rachel","Ian","Mike","Atharva","Jasmine","Zhao","Ruben","Bashu","Divya","Shivani","Vishu","Khyati","Neo");
    private final ObservableList<Integer> mon = FXCollections.observableArrayList(10,11,12,13,14,15,16,17);
    private final ObservableList<String> ten = FXCollections.observableArrayList("1","2","3","4","5","6","7","8","9","10","11","12", "13", "14","15","16","17","18","19","141120","21","22", "23", "24","25","26","27","28","29","230","31","32", "33", "34","35","36","37","38","39","40");
    
    /**
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        final_grade.setItems(ins_type);
        student_name.setItems(ins_name);
        roll_no.setItems(ten);
        monthly_payment.setItems(mon);
        
        UserSession user = UserSession.getInstance();
        String emailId = (String) user.getUserName();
        List<Student> plans = new ArrayList<Student>();
        try {
            plans = getPlans(emailId);
            Iterator i = plans.iterator();
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AddplanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator i = plans.iterator();
        if(i.hasNext())
        {
           welcome_string.setText("Update Student's Result");
           Student plan = new Student();
           plan = plans.get(0);
           final_grade.setValue(plan.getfinal_grade());
           student_name.setValue(plan.getstudent_name());
           monthly_payment.setValue(plan.getage());
           roll_no.setValue(plan.getroll_no());
           update_button.setVisible(true);
           plan_button.setVisible(false);
        }
        else
        {
           welcome_string.setText("Add New Student Result");
           plan_button.setVisible(true);
           update_button.setVisible(false); 
        }
    }
    
    @FXML
    public void updateplan(ActionEvent event) throws SQLException, IOException {

        Window owner = update_button.getScene().getWindow();
        
        int tenval;
        String roll_no_value;
        roll_no_value = String.valueOf(roll_no.getValue());
//        roll_no_value = roll_no_value.substring(0,2);
        instype = (String) final_grade.getValue();
        insname = (String) student_name.getValue();
        payment = (int) monthly_payment.getValue();
        tenval = Integer.parseInt(roll_no_value);
        
         
            UserSession user = UserSession.getInstance();
            String emailId = (String) user.getUserName();
            List<Student> plans = new ArrayList<>();
            try {
                plans = getPlans(emailId);
            } catch (SQLException | IOException ex) {
                Logger.getLogger(AddplanController.class.getName()).log(Level.SEVERE, null, ex);
            }
            if(plans!=null)
            {
                Student plan = new Student();
                plan = plans.get(0);
                JdbcDao jdbcDao = new JdbcDao();
                jdbcDao.setRecord(plan.email_id, instype, insname,
                        payment, tenval);
                showAlert(Alert.AlertType.CONFIRMATION, owner, "Result updated Successful!",
                     "Updated Result "+insname+" with "+ instype +""+" ");
            }
        switchToPostLogin();
    }
    
    
    @FXML
    public void addplan(ActionEvent event) throws SQLException, IOException {

        Window owner = plan_button.getScene().getWindow();
        
        if (student_name.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please Add Student Name");
            return;
        }
        if(final_grade.getValue() == null)
        {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please Add Student Final Grade");
        }
        
        if(monthly_payment.getValue() == null)
        {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please Add Student Age");
        }
        if(roll_no.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please Add Student Enrollment Number");
            return;
        }
        
        int tenval;
        roll_noval = (String) roll_no.getValue();
//        roll_noval = roll_noval.substring(0,2);
        instype = (String) final_grade.getValue();
        insname = (String) student_name.getValue();
        payment = (int) monthly_payment.getValue();
        tenval = Integer.parseInt(roll_noval);
        
        if (instype.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please Add Student Name");
            return;
        }

        if (insname.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please Add Student Final Grade");
            return;
        }
        if (payment==0) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please Add Student Enrollment Number");
            return;
        }
        if(roll_noval.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Form Error!",
                "Please Add Student Age");
            return;
        }
         
        System.out.println(instype);
        System.out.println(insname);
        System.out.println(payment);
        System.out.println(tenval);
        //sSystem.out.println(passwordField.getText());
        
        //get emailId of User
        UserSession user = UserSession.getInstance();
        String emailId = (String) user.getUserName();
        List<Student> plans = new ArrayList<>();
        JdbcDao jdbcDao = new JdbcDao();

        jdbcDao.addRecord(emailId, instype, insname , payment, tenval);
        showAlert(Alert.AlertType.CONFIRMATION, owner, "Result added Successful!",
                 "Added result "+insname+" with "+ instype +""+" ");
        switchToPostLogin();
    }
    
    @FXML
    private void switchToPostLogin() throws IOException {
        App.setRoot("PostLogin");
    }
    
    @FXML
    private void switchToTerms() throws IOException {
        App.setRoot("terms");
    }
    
    @FXML
    private void switchtoAddPlan() throws IOException {
        App.setRoot("addplan");
    }
    
    @FXML public List<Student> getPlans(String emailid) throws SQLException, IOException {
        
        List<Student> plans = new ArrayList<>();
        JdbcDao jdbcDao = new JdbcDao();
        plans = jdbcDao.getRecord(emailid);
        return plans;
    }
    
    
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
}
