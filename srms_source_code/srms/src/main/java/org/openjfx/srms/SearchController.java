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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import org.openjfx.srms.JdbcDao;
/**
 *
 * 
 */
public class SearchController implements Initializable {

    @FXML
    private Label email_id;
    @FXML
    private Label student_result;
    
    @FXML 
    private Label student_name;
    
    @FXML
    private Label monthly_payment;
    
    @FXML 
    private Label roll_no;
    
    @FXML
    private TextField search_bar;
    
    @FXML
    private Button searchButton;
    
    @FXML
    private Button delete_button;
    
    @FXML
    private Button dataview_button;
    
    @FXML
    private void switchtoDataView() throws IOException {
        App.setRoot("Main");
        JdbcDao dao=new JdbcDao();
        try {
			dao.printRecordToCsv();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
        
    }
    
    
    @FXML public List<Student> getPlans(String emailid) throws SQLException, IOException {
        
        List<Student> plans = new ArrayList<>();
        JdbcDao jdbcDao = new JdbcDao();
        UserSession user = UserSession.getInstance();
        plans = jdbcDao.searchRecord(emailid);
        return plans;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        return;
    }
    
    @FXML
    public void searchfun(ActionEvent event) throws SQLException, IOException {

        Window owner = searchButton.getScene().getWindow();

        List<Student> plans = new ArrayList<>();
        
        if (search_bar.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Search 404",
                "Enter a username email to search");
            return;
        }
        System.out.println(search_bar.getText());
        
        if (search_bar.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, owner, "Search 404",
                "Enter a username email to search");
            return;
        }

        String searchid = search_bar.getText();
        
        try {
            plans = getPlans(searchid);
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AddplanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        Iterator it = plans.iterator();
        if(it.hasNext())
        {
            Student plan = new Student();
            plan = plans.get(0);
            student_name.setText(plan.student_name);
            student_result.setText(plan.final_grade);
            monthly_payment.setText(plan.age+"");
            roll_no.setText(plan.roll_no+"");
            email_id.setText(plan.email_id);
        }
        else
        {
            student_name.setText("There is no customer with active plans and given email id");
            student_result.setText("");
            monthly_payment.setText("");
            email_id.setText("");
            roll_no.setText("");
        }
    }

    @FXML
    public void deletefun(ActionEvent event) throws SQLException, IOException {

        Window owner = delete_button.getScene().getWindow();
        
        String email = email_id.getText();
        JdbcDao jdbcdao = new JdbcDao();
        jdbcdao.deleteRecord(email);
        ReloadSearch();
    }
    private static void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    
    @FXML
    private void ReloadSearch() throws IOException {
        App.setRoot("Search");
    }
    
    @FXML
    private void switchToLogin() throws IOException {
        App.setRoot("Login");
    }
}
