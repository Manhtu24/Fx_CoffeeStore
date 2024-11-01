package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;





public class mainFormController implements Initializable {
	
	@FXML
    private Button customers_btn;

    @FXML
    private Button dashboard_btn;

    @FXML
    private Button inventory_addBtn;

    @FXML
    private Button inventory_btn;

    @FXML
    private Button inventory_clearBtn;

    @FXML
    private TableColumn<?, ?> inventory_col_date;

    @FXML
    private TableColumn<?, ?> inventory_col_price;

    @FXML
    private TableColumn<?, ?> inventory_col_productID;

    @FXML
    private TableColumn<?, ?> inventory_col_productName;

    @FXML
    private TableColumn<?, ?> inventory_col_status;

    @FXML
    private TableColumn<?, ?> inventory_col_stock;

    @FXML
    private TableColumn<?, ?> inventory_col_type;

    @FXML
    private Button inventory_deleteBtn;

    @FXML
    private AnchorPane inventory_form;

    @FXML
    private ImageView inventory_imageView;

    @FXML
    private Button inventory_importBtn;

    @FXML
    private TableView<productData> inventory_tableView;

    @FXML
    private Button inventory_updateBtn;

    @FXML
    private Button logout_btn;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button menu_btn;

    @FXML
    private Label username;
    
    @FXML
    private TextField inventory_price;

    @FXML
    private TextField inventory_productID;

    @FXML
    private TextField inventory_productName;
    
    @FXML
    private TextField inventory_stock;
    
    @FXML
    private ComboBox<?> inventory_status;
    
    @FXML
    private ComboBox<?> inventory_type;
	
    private Alert alert;
    
    private String [] typeList= {"Meals", "Drinks"};
    
    private String [] statusList= {"Available","Unavailable"};
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    
    
    public ObservableList<productData> inventoryDataList(){
    	ObservableList<productData> listData=FXCollections.observableArrayList();
    	
    	String sql="SELECT * FROM product";
    	connection= database.connectDB();
    	
    	try {
			preparedStatement=connection.prepareStatement(sql);
			resultSet= preparedStatement.executeQuery();
			productData prodData;
			
			while(resultSet.next()) {
				prodData= new productData(resultSet.getInt("id"), resultSet.getString("prod_id"),
						resultSet.getString("prod_name"),resultSet.getString("type"), resultSet.getString("status"), resultSet.getInt("stock"),
						resultSet.getDouble("price"),resultSet.getDate("date"), resultSet.getString("image"));
				listData.add(prodData);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	
    	return listData;
    }
    
    private ObservableList<productData> inventoryListData;
    public void inventoryShowData() {
    	inventoryListData=inventoryDataList();
    	inventory_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
    	inventory_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
    	inventory_col_type.setCellValueFactory(new PropertyValueFactory<>("type"));
    	inventory_col_stock.setCellValueFactory(new PropertyValueFactory<>("stock"));
    	inventory_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));
    	inventory_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
    	inventory_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
    	
    	inventory_tableView.setItems(inventoryListData);
    }
    
    public void inventoryTypeList() {
    	
    	
    	List<String> typeL= new ArrayList<String>();
    	for(String type:typeList) {
    		typeL.add(type);
    	}
    	
    	 ObservableList listData= FXCollections.observableArrayList(typeL);
    	 inventory_type.setItems(listData);
    }
    public void inventoryStatusList() {
    	List<String> statusL= new ArrayList<String>();
    	for(String data:statusList) {
    		statusL.add(data);
    	}
    	 ObservableList listData= FXCollections.observableArrayList(statusL);
    	 inventory_status.setItems(listData);
    }
    public void logout(){
    	try {
    		alert= new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Are you sure you want to log out?");
    		Optional<ButtonType> optional= alert.showAndWait();
    		
    		if (optional.get().equals(ButtonType.OK)) {
    			
    			//HIDE MAIN FORM
    			logout_btn.getScene().getWindow().hide();
    			
    			//Back to login form
    			Parent root =FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
    			Stage stage= new Stage();
    			Scene scene= new Scene(root);
    			stage.setTitle("Coffe Store Management System");
    			stage.setScene(scene);
    			stage.show();
    		}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    public void displayUsername() {
    	String userString= data.username;
    	userString= userString.substring(0, 1).toUpperCase()+ userString.substring(1);
    	username.setText(userString);
    }
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		displayUsername();
		inventoryTypeList();
		inventoryStatusList();
		inventoryShowData();
		
	}

}
