package application;

import java.io.File;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
    private TableColumn<productData, String> inventory_col_date;

    @FXML
    private TableColumn<productData, String> inventory_col_price;

    @FXML
    private TableColumn<productData, String> inventory_col_productID;

    @FXML
    private TableColumn<productData, String> inventory_col_productName;

    @FXML
    private TableColumn<productData, String> inventory_col_status;

    @FXML
    private TableColumn<productData, String> inventory_col_stock;

    @FXML
    private TableColumn<productData, String> inventory_col_type;

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
    
    @FXML
    private TextField menu_amount;

    @FXML
    private Label menu_change;

    @FXML
    private TableColumn<?, ?> menu_col_price;

    @FXML
    private TableColumn<?, ?> menu_col_productName;

    @FXML
    private TableColumn<?, ?> menu_col_quantity;

    @FXML
    private AnchorPane menu_form;

    @FXML
    private GridPane menu_gridPane;

    @FXML
    private Button menu_payBtn;

    @FXML
    private Button menu_receiptBtn;

    @FXML
    private Button menu_removeBtn;

    @FXML
    private ScrollPane menu_scrollPane;

    @FXML
    private TableView<?> menu_tableView;

    @FXML
    private Label menu_total;
	
    private Alert alert;
    
    private String [] typeList= {"Meals", "Drinks"};
    
    private String [] statusList= {"Available","Unavailable"};
    
    private Image image;
    
    private Connection connection;
    private PreparedStatement preparedStatement;
    private Statement statement;
    private ResultSet resultSet;
    
    
    public ObservableList<productData> cardListData;
    
    public void inventoryAddBtn() {
    	if(inventory_productID.getText().isEmpty()||
    			inventory_productName.getText().isEmpty()||
    			inventory_stock.getText().isEmpty()||
    			inventory_price.getText().isEmpty()||
    			inventory_type.getSelectionModel().getSelectedItem()==null||
    			inventory_status.getSelectionModel().getSelectedItem()==null||
    			data.path==null) {
    		
    		alert= new Alert(AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill all blank fields");
    		alert.showAndWait();
    		
    	}else {
    		String checkProdID= "SELECT prod_id FROM product WHERE prod_id= '"
    				+inventory_productID.getText()+"'" ;
    		
    		connection= database.connectDB();
    		
    		try {
				statement= connection.createStatement();
				resultSet= statement.executeQuery(checkProdID);
				
				if(resultSet.next()) {
		    		alert= new Alert(AlertType.ERROR);
		    		alert.setTitle("Error Message");
		    		alert.setHeaderText(null);
		    		alert.setContentText(inventory_productID.getText()+" is already taken");
		    		alert.showAndWait();
				}else {
					String insertData= "INSERT INTO product "
							+"(prod_id, prod_name,type, stock, price, status, image, date)"
							+"VALUES(?,?,?,?,?,?,?,?)";
					
					preparedStatement= connection.prepareStatement(insertData);
					preparedStatement.setString(1,inventory_productID.getText() );
					preparedStatement.setString(2, inventory_productName.getText());
					preparedStatement.setString(3, (String)inventory_type.getSelectionModel().getSelectedItem());
					preparedStatement.setString(4, inventory_stock.getText());
					preparedStatement.setString(5, inventory_price.getText());
					preparedStatement.setString(6,(String)inventory_status.getSelectionModel().getSelectedItem() );
					
					String path= data.path;
					path= path.replace("\\", "\\\\");
					preparedStatement.setString(7, path);
					//for get current data
					Date date= new Date();
					java.sql.Date sqlDate= new java.sql.Date(date.getTime());
					preparedStatement.setString(8, String.valueOf(sqlDate));
					preparedStatement.executeUpdate();
					
		    		alert= new Alert(AlertType.INFORMATION);
		    		alert.setTitle("Error Message");
		    		alert.setHeaderText(null);
		    		alert.setContentText("Successfully Added");
		    		alert.showAndWait();
		    		
					inventoryShowData();
					inventoryClearBtn();
					
				}
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    }
    
    public void inventoryUpdateBtn() {
    	if(inventory_productID.getText().isEmpty()||
    			inventory_productName.getText().isEmpty()||
    			inventory_stock.getText().isEmpty()||
    			inventory_price.getText().isEmpty()||
    			inventory_type.getSelectionModel().getSelectedItem()==null||
    			inventory_status.getSelectionModel().getSelectedItem()==null||
    			data.path==null||data.id==0) {
    		
    		alert= new Alert(AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill all blank fields");
    		alert.showAndWait();
    		
    	}else {
    		String path=data.path;
    		path=path.replace("\\", "\\\\");
    		String updateData= "UPDATE product SET "
    				+"prod_id = '"+inventory_productID.getText() + "', prod_name = '" +
    				inventory_productName.getText() + "', type = '" +
    				inventory_type.getSelectionModel().getSelectedItem() + "' , stock = '"+
    				inventory_stock.getText() + "' , price = '"+ 
    				inventory_price.getText()+"', status= '"+
    				inventory_status.getSelectionModel().getSelectedItem()+"', image='"
    				+path +"', date= '"+ data.date +"' WHERE id= "+data.id;
    		connection=database.connectDB();
    		
    		try {
        		alert= new Alert(AlertType.CONFIRMATION);
        		alert.setTitle("Error Message");
        		alert.setHeaderText(null);
        		alert.setContentText("Are you sure you want to UPDATE Product ID : "+inventory_productID.getText()+" ?");
        		Optional<ButtonType> optional=alert.showAndWait();
        		
        		if (optional.get().equals(ButtonType.OK)) {
    				preparedStatement=connection.prepareStatement(updateData);
    				preparedStatement.executeUpdate();
    	    		alert= new Alert(AlertType.INFORMATION);
    	    		alert.setTitle("Error Message");
    	    		alert.setHeaderText(null);
    	    		alert.setContentText("Successfully Updated");
    	    		alert.showAndWait();
    				
    				inventoryShowData(); //update data in table view
    				inventoryClearBtn();
				}else {
	   	    		alert= new Alert(AlertType.ERROR);
    	    		alert.setTitle("Error Message");
    	    		alert.setHeaderText(null);
    	    		alert.setContentText("Cancelled");
    	    		alert.showAndWait();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
    	}
    }
    
    public void inventoryDeleteBtn() {
    	if(data.id==0) {
    		alert= new Alert(AlertType.ERROR);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Please fill all blank fields");
    		alert.showAndWait();
    		
    	}else {
	    	alert= new Alert(AlertType.CONFIRMATION);
    		alert.setTitle("Error Message");
    		alert.setHeaderText(null);
    		alert.setContentText("Are you sure you want to DELETE Product ID: "+inventory_productID.getText()+" ?");
    		Optional<ButtonType> optional=alert.showAndWait();
    		if(optional.get().equals(ButtonType.OK)) {
    			String deleteData="DELETE FROM product WHERE id= "+data.id;
    			try {
					preparedStatement=connection.prepareStatement(deleteData);
					preparedStatement.executeUpdate();
					
		    		alert= new Alert(AlertType.INFORMATION);
		    		alert.setTitle("Error Message");
		    		alert.setHeaderText(null);
		    		alert.setContentText("Successfully Deleted");
		    		alert.showAndWait();
		    		
    				inventoryShowData(); //update data in table view
    				inventoryClearBtn();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    		}else {
	    		alert= new Alert(AlertType.ERROR);
	    		alert.setTitle("Error Message");
	    		alert.setHeaderText(null);
	    		alert.setContentText("Cancelled");
	    		alert.showAndWait();
    		}
    		
    		
    		
    	}
    }
    
    public void inventoryClearBtn() {
    	inventory_productID.setText("");
    	inventory_productName.setText("");
    	inventory_type.getSelectionModel().clearSelection();
    	inventory_stock.setText("");
    	inventory_price.setText("");
    	inventory_status.getSelectionModel().clearSelection();
    	data.path="";
    	data.id=0;
    	inventory_imageView.setImage(null);
    }
    
    public void inventoryImportBtn() {
    	FileChooser openfile= new FileChooser();
    	openfile.getExtensionFilters().add(new ExtensionFilter("Open image File","*png","*jpg"));
    	File file= openfile.showOpenDialog(main_form.getScene().getWindow());
    	
    	if(file!=null) {
    		data.path=file.getAbsolutePath();
    		image= new Image(file.toURI().toString(), 157, 139, false, true);
    		
    		inventory_imageView.setImage(image);
    		
    		//not done
    		
    }
 }    	
    
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
    
    //to show data in table at inventory
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
    
    public void inventorySelecData() {
    	productData prodData= inventory_tableView.getSelectionModel().getSelectedItem();
    	int number=inventory_tableView.getSelectionModel().getSelectedIndex();
    	if((number-1)<-1) {
    		return;
    	}
    	inventory_productID.setText(prodData.getProductId());
    	inventory_productName.setText(prodData.getProductName());
    	inventory_stock.setText(String.valueOf(prodData.getStock()));
    	inventory_price.setText(String.valueOf(prodData.getPrice()));
    	data.path=prodData.getImage();
    	String path= "File:"+prodData.getImage();
    	
    	data.date= String.valueOf(prodData.getDate());
    	data.id= prodData.getId();
    	image= new Image(path, 157,139, false, true);
    	inventory_imageView.setImage(image);
    	
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
