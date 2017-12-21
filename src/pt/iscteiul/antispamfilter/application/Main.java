package pt.iscteiul.antispamfilter.application;

//import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {

		try {
			Parent root = FXMLLoader.load(getClass().getResource("../views/MainView.fxml"));
			Scene scene = new Scene(root, 840, 775);

			primaryStage.setTitle("AntiSpam Filter Configurator");
			primaryStage.getIcons().add(new Image(Main.class.getResourceAsStream("email.png")));
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) throws IOException {

		launch(args);

		/*
		 * String[] paramsLatex = new String[2];
		 * 
		 * paramsLatex[0] =
		 * "C:\\Program Files\\MiKTeX 2.9\\miktex\\bin\\x64\\miktex-pdflatex.exe";
		 * 
		 * paramsLatex[1] =
		 * "C:\\Users\\Bedrick Beta\\git\\ES1-2017-METIA1-44\\experimentBaseDirectory\\AntiSpamStudy\\latex\\AntiSpamStudy.tex"
		 * ;
		 * 
		 * String[] envpLatex = new String[1];
		 * 
		 * envpLatex[0] = "Path=C:\\Program Files\\MiKTeX 2.9\\miktex\\bin\\x64";
		 * 
		 * Process p = Runtime.getRuntime().exec(paramsLatex, envpLatex, new File(
		 * "C:\\Users\\Bedrick Beta\\git\\ES1-2017-METIA1-44\\experimentBaseDirectory\\AntiSpamStudy\\latex"
		 * ));
		 * 
		 * System.exit(0);
		 */

		/*
		 * String[] paramsR = new String[2];
		 * 
		 * paramsR[0] = "C:\\Program Files\\R\\R-3.4.3\\bin\\x64\\Rscript.exe";
		 * 
		 * paramsR[1] = "experimentBaseDirectory/AntiSpamStudy/R/HV.Boxplot.R";
		 * 
		 * String[] envpR = new String[1];
		 * 
		 * envpR[0] = "Path=C:\\Program Files\\R\\R-3.4.3\\bin\\x64";
		 * 
		 * Process pR = Runtime.getRuntime().exec(paramsR, envpR, new
		 * File("experimentBaseDirectory/AntiSpamStudy/R"));
		 * 
		 */

	}

}
