package models.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;;

//Classe para ler os ficheiros 
public class DadosDao {

	public void lerFicheiro(String nomeFicheiro, ObservableList<String> regras, ObservableList<Integer> pesos) {

		try {
			Scanner s = new Scanner(new File(nomeFicheiro));
			while (s.hasNext()) {
				String line = s.next();

				if (line.contains(";")) {
					int pos = line.indexOf(";");
					//String regra = line.substring(0, pos);
					regras.add(line.substring(0, pos));
					int peso = Integer.parseInt(line.substring(pos+1));
					pesos.add(peso);
					System.out.println(peso);
				}else {
				regras.add(line);
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Erro! O Ficheiro Não Foi Encontrado Na Directória!");
			alert.showAndWait();
		}
	}

	public void escreverFicheiro(String nomeFicheiro, String text) {

		try {
			FileWriter fileW = new FileWriter(new File(nomeFicheiro));
			BufferedWriter buffW = new BufferedWriter(fileW);
			buffW.write(text);
			buffW.close();
			System.out.println("File Written");
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Erro! Não foi escrever no ficheiro.");
			alert.showAndWait();
		}
	}
}
