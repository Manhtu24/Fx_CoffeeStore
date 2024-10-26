package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;

//import javafx.scene.layout.StackPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
			Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
//			StackPane root= new StackPane();
			Scene scene= new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Coffee Store Management System");
			primaryStage.setMinHeight(400);
			primaryStage.setMinWidth(600);
			primaryStage.show();
	}
	public static void main(String[] args) {
		launch(args);
	}
}
