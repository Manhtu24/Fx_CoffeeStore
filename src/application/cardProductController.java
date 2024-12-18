package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;


public class cardProductController implements Initializable{
	
	@FXML
	private AnchorPane card_form;

	@FXML
	private Button prod_addBtn;

	@FXML
	private ImageView prod_image;

	@FXML
	private Label prod_name;

	@FXML
	private Label prod_price;

	@FXML
	private Spinner<?> prod_spinner;
	
	private productData prodData;
	
	private Image image;
	
	public void setData(productData prodData) {
		this.prodData=prodData;
		prod_name.setText(prodData.getProductName());
		prod_price.setText(String.valueOf( prodData.getPrice()));
		String path= "File:"+prodData.getImage();
		image= new Image(path,186,91, false, true);
		prod_image.setImage(image);
		
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	



}
