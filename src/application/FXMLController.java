package application;

//import java.awt.Button;
//import java.awt.TextField;
//import java.awt.event.ActionEvent;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;
//import java.sql.Date;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.spi.DateFormatProvider;
import java.util.ArrayList;
import java.util.List;
//import java.util.Observable;
import java.util.ResourceBundle;
//import java.util.prefs.PreferenceChangeListener;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class FXMLController implements Initializable {
	
    @FXML
    private TextField fp_username;
    
    @FXML
    private TextField fp_answer;

    @FXML
    private Button fp_back;

    @FXML
    private Button fp_proceedBtn;

    @FXML
    private ComboBox<?> fp_question;

    @FXML
    private AnchorPane fp_questionForm;

    @FXML
    private Button np_back;

    @FXML
    private Button np_changePassBtn;

    @FXML
    private PasswordField np_comfirmPassword;

    @FXML
    private AnchorPane np_newPassForm;

    @FXML
    private PasswordField np_newPassword;

    @FXML
    private Hyperlink s_forgotPass;

    @FXML
    private Button s_loginBtn;

    @FXML
    private AnchorPane s_loginForm;

    @FXML
    private PasswordField s_password;

    @FXML
    private TextField s_username;

    @FXML
    private Button side_alreadyHave;

    @FXML
    private Button side_createBtn;

    @FXML
    private AnchorPane side_form;

    @FXML
    private TextField su_answer;

    @FXML
    private PasswordField su_password;

    @FXML
    private ComboBox<?> su_question;

    @FXML
    private Button su_signupBtn;

    @FXML
    private AnchorPane su_siignupForm;

    @FXML
    private TextField su_username;
    
    private Alert alert;;
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;
    
    public void loginBtn() {
    	if(s_username.getText().isEmpty()||s_password.getText().isEmpty() ) {
    		alert= new Alert(AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Incorrect username/password ");
    		alert.showAndWait();
    	}else {
    		String selectData="Select username , passw from employee where username=? and passw=? ";
    		
    		connection= database.connectDB();
    		
    		try {
				preparedStatement= connection.prepareStatement(selectData);
				preparedStatement.setString(1, s_username.getText());
				preparedStatement.setString(2, s_password.getText());
				
				resultSet= preparedStatement.executeQuery();
				//check login account
				if(resultSet.next()) {
		    		alert= new Alert(AlertType.INFORMATION);
		    		alert.setTitle("Information Message");
		    		alert.setHeaderText(null);
		    		alert.setContentText("Successfully Login ");
		    		alert.showAndWait();
				}else {
		    		alert= new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Message");
		    		alert.setHeaderText(null);
		    		alert.setContentText("Incorrect username/password");
		    		alert.showAndWait();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public void regBtn() {
    	if(su_username.getText().isEmpty()||su_password.getText().isEmpty()
    			||su_question.getSelectionModel().getSelectedItem()==null
    			||su_answer.getText().isEmpty()) {
    		alert= new Alert(AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill all blank fields");
    		alert.showAndWait();
    	}else {
    		String sqlStatemant="INSERT into employee(username,passw, question, answer,datet) "+
    				"VALUES(?,?,?,?,?)";
    		connection=database.connectDB();
    		try {
    			String checkUsername="select username from employee where username='"
    					+ su_username.getText() + "'" ;
    			preparedStatement=connection.prepareStatement(checkUsername);
    			resultSet=preparedStatement.executeQuery();
    			
    			if(resultSet.next()) {
    	    		alert= new Alert(AlertType.ERROR);
    	    		alert.setTitle("Error Message");
    	    		alert.setHeaderText(null);
    	    		alert.setContentText(su_username.getText()+" is already taken");
    	    		alert.showAndWait();
    			}else if(su_password.getText().length()<8) {
    	    		alert= new Alert(AlertType.ERROR);
    	    		alert.setTitle("Error Message");
    	    		alert.setHeaderText(null);
    	    		alert.setContentText("Invalid password, at least 8 characters are needed");
    	    		alert.showAndWait();
    			}else {
    				//Connect with database (MYSQL)
        			preparedStatement= connection.prepareStatement(sqlStatemant);
        			preparedStatement.setString(1,su_username.getText());
        			preparedStatement.setString(2,su_password.getText());
        			preparedStatement.setString(3,(String)su_question.getSelectionModel().getSelectedItem());
        			preparedStatement.setString(4,su_answer.getText());
        			
        			Date date = new Date();
        			java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        			preparedStatement.setString(5,String.valueOf(sqlDate));
        			
        			preparedStatement.executeUpdate();
        			
            		alert= new Alert(AlertType.INFORMATION);
            		alert.setTitle("Information Message");
            		alert.setHeaderText(null);
            		alert.setContentText("Successfully registered Account!");
            		alert.showAndWait();
            		
            		su_username.setText("");
            		su_password.setText("");
            		su_question.getSelectionModel().clearSelection();
            		su_answer.setText("");
            		
            		
            		TranslateTransition sliderTransition= new TranslateTransition();
        			sliderTransition.setNode(side_form);
        			sliderTransition.setToX(0);
        			sliderTransition.setDuration(Duration.seconds(.5));
        			
        			
        			sliderTransition.setOnFinished(e->{
        				side_alreadyHave.setVisible(false);
        				side_createBtn.setVisible(true);
        			});
        			sliderTransition.play();
    			}
    			
    			
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public String[] questionList= {"What is your favorite color?", "What is your favorite food?", "What is your birth date?"};
    
    public void reqLquestionList() {
    	List<String> listQ= new ArrayList<>();
    	
    	for(String data:questionList) {
    		listQ.add(data);
    	}
    	ObservableList listData = FXCollections.observableArrayList(listQ) ;
    	su_question.setItems(listData);
    }
    
    
    public void switchForgotPass(){
    	fp_questionForm.setVisible(true);
    	s_loginForm.setVisible(false);
    	forgotQuestionList();
    	
    }
    
    public void proceedBtn() {
    	if(fp_username.getText().isEmpty()||fp_question.getSelectionModel().getSelectedItem()==null
    			||fp_answer.getText().isEmpty()) {
    		alert= new Alert(AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill or select all the fields");
    		alert.showAndWait();
    	}else {
    		String selectData="SELECT username, question, answer from employee WHERE username=? and question=? and answer=?";
    		connection=database.connectDB();
    		
    		try {
				preparedStatement=connection.prepareStatement(selectData);
				preparedStatement.setString(1, fp_username.getText());
				preparedStatement.setString(2, (String)fp_question.getSelectionModel().getSelectedItem());
				preparedStatement.setString(3, fp_answer.getText());
				
				resultSet=preparedStatement.executeQuery();
				
				if(resultSet.next()) {
					np_newPassForm.setVisible(true);
					fp_questionForm.setVisible(false);
					
				}else {
		    		alert= new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Message");
		    		alert.setHeaderText(null);
		    		alert.setContentText("Incorrect information");
		    		alert.showAndWait();
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    		
    }
    
    public void changePassBtn() {
    	if(np_newPassword.getText().isEmpty()||np_comfirmPassword.getText().isEmpty()) {
    		alert= new Alert(AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill all blank fields");
    		alert.showAndWait();
    	}else {
    		if(np_newPassword.getText().equals(np_comfirmPassword.getText())) {
    			String getDate="SELECT datet FROM employee WHERE username='"+
    							fp_username.getText()+"'";
    			connection=database.connectDB();
    			
    			try {
					preparedStatement=connection.prepareStatement(getDate);
					resultSet=preparedStatement.executeQuery();
					
					String date="";
					if(resultSet.next()) {
						date=resultSet.getString("datet");
					}
					String updatePass="UPDATE employee SET passw='"+
							np_newPassword.getText()+"',question='"+
							fp_question.getSelectionModel().getSelectedItem()+"', answer= '"+
							fp_answer.getText()+"', datet='"+
							date+"' WHERE username='"+
							fp_username.getText()+"' " ;
					preparedStatement=connection.prepareStatement(updatePass);
					preparedStatement.executeUpdate();
					
	        		alert= new Alert(AlertType.INFORMATION);
	        		alert.setTitle("Information Message");
	        		alert.setHeaderText(null);
	        		alert.setContentText("Successfully changed Password!");
	        		alert.showAndWait();
	        		
	        		
	        		s_loginForm.setVisible(true);
	        		np_newPassForm.setVisible(false);
	        		
	        		np_newPassword.setText("");
	        		np_comfirmPassword.setText("");
	        		fp_question.getSelectionModel().clearSelection();
	        		fp_answer.setText("");
	        		fp_username.setText("");
	        		
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
    			
    			
    		}else {
        		alert= new Alert(AlertType.ERROR);
        		alert.setTitle("Error Message");
        		alert.setHeaderText(null);
        		alert.setContentText("Not match");
        		alert.showAndWait();
    		}
    	}
    }
    
    public void forgotQuestionList() {
    	List<String> listQ= new ArrayList<String>();
    	for(String dataL:questionList) {
    		listQ.add(dataL);
    	}
    	ObservableList listData= FXCollections.observableArrayList(listQ);
    	fp_question.setItems(listData);
    }
    public void backToLoginForm() {
    	s_loginForm.setVisible(true);
    	fp_questionForm.setVisible(false);
    	
    }
    public void backToQuestionForm() {
    	fp_questionForm.setVisible(true);
    	np_newPassForm.setVisible(false);
    }
    
    public void switchForm(ActionEvent event) {
		TranslateTransition sliderTransition= new TranslateTransition();
    	if (event.getSource()==side_createBtn) {
			sliderTransition.setNode(side_form);
			sliderTransition.setToX(300);
			sliderTransition.setDuration(Duration.seconds(.5));
			
			
			sliderTransition.setOnFinished((e)->{
				side_alreadyHave.setVisible(true);
				side_createBtn.setVisible(false);
				
				fp_questionForm.setVisible(false);
				s_loginForm.setVisible(true);
				np_newPassForm.setVisible(false);
				reqLquestionList();
			});
			
			sliderTransition.play();
		}else if(event.getSource()==side_alreadyHave) {
			sliderTransition.setNode(side_form);
			sliderTransition.setToX(0);
			sliderTransition.setDuration(Duration.seconds(.5));
			
			
			sliderTransition.setOnFinished(e->{
				side_alreadyHave.setVisible(false);
				side_createBtn.setVisible(true);
				
				fp_questionForm.setVisible(false);
				s_loginForm.setVisible(true);
				np_newPassForm.setVisible(false);
			});
			sliderTransition.play();
		}
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
