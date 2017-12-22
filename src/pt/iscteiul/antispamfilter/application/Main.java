package pt.iscteiul.antispamfilter.application;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
			Scene scene = new Scene(root);
			Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
			primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 4);
			primaryStage.setTitle("AntiSpam Filter Configurator");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("email.png")));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Erro! Não foi possivel iniciar a aplicação!");
			alert.showAndWait();
		}

	}

	public static void main(String[] args) {

		launch(args);

	}

}
