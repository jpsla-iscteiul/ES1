package pt.iscteiul.antispamfilter.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
	
		@Override
		public void start(Stage primaryStage) {
			
			try {
				Parent root = FXMLLoader.load(getClass().getResource("../views/MainView.fxml"));
				Scene scene = new Scene(root,1000,897);
				primaryStage.setScene(scene);
				primaryStage.show();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
		}
	

	public static void main(String[] args) {
		
		launch(args);

	}

}
