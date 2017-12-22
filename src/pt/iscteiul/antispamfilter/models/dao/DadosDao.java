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

/**
 * Classe responsável por ler e escrever em ficheiros 
 * <p>
 *Esta classe é utilizada pelas classes 
 *@link AntiSpamFilterController  
 *@link AntiSpamMethods
 *<li>
 * @author João Lola 83169 
 * @author Délcio Pedro 81611
 */

public class DadosDao {

	/**
	 * Método para ler ficheiros e adicionar as regras e os pesos, caso haja pesos 
	 * no ficheiro
	 * <p>
	 * Recebe como parametro o nome do ficheiro, um vector ou array para as regras,
	 * outro vector para os pesos e o tipo de ficheiro 
	 * @param nomeFicheiro 
	 *  @param regras  
	 *  @param pesos 
	 *  @param tipo   
	 */
	public void lerFicheiro(String nomeFicheiro, ObservableList<String> regras,
			ObservableList<Double> pesos, TipoFicheiro tipo) {
		try {
			Scanner s = new Scanner(new File(nomeFicheiro));
			System.out.println("Lendo o ficheiro ");
			while (s.hasNext()) {
				String line = s.next();				
				
				if (line.contains(";")) {
					int pos = line.indexOf(";");
					regras.add(line.substring(0, pos));
					double peso = Double.parseDouble(line.substring(pos + 1));
					pesos.add(peso);
					System.out.println("regra "+ line.substring(0, pos));
					System.out.println("Peso " + peso);
				} else {
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
	/**
	 * Método para escrever no ficheiro ou guardar alterações    
	 * <p>
	 * Recebe como parametro o nome do ficheiro e o texto que se 
	 * pretende escrever no ficheiro 
	 * @param nomeFicheiro  
	 * @param text  
	 * @param pesos 
	 * @param tipo   
	 */
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
