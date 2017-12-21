package pt.iscteiul.antispamfilter.models.dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import pt.iscteiul.antispamfilter.models.TipoFicheiro;

public class DadosDao {
	/**
	 * 
	 * @param nomeFicheiro
	 * @param regras
	 * @param pesos
	 */
	public void lerFicheiro(String nomeFicheiro, ObservableList<String> regras, ObservableList<Double> pesos,
			TipoFicheiro tipo) {
		try {
			Scanner s = new Scanner(new File(nomeFicheiro));

			while (s.hasNext()) {
				String line = s.next();

				if (line.contains(";")) {
					int pos = line.indexOf(";");
					regras.add(line.substring(0, pos));
					double peso = Double.parseDouble(line.substring(pos + 1));
					pesos.add(peso);
				} else {
					regras.add(line);
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Erro! O ficheiro não foi encontrado na directória!");
			alert.showAndWait();
		}
	}

	/**
	 * 
	 * @param nomeFicheiro
	 * @param text
	 */
	public void escreverFicheiro(String nomeFicheiro, String text) {

		try {
			FileWriter fileW = new FileWriter(new File(nomeFicheiro));
			BufferedWriter buffW = new BufferedWriter(fileW);
			buffW.write(text);
			buffW.close();

		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Erro! Não foi escrever no ficheiro.");
			alert.showAndWait();
		}
	}
}
