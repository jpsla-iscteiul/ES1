package models.dao;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;;

//Classe para ler os ficheiros 
public class DadosDao {
	
	/**
	 * 	
	 * @param spamFile
	 * @param regras
	 * @param pesos
	 * @return FNValue (retorna a somat�ria de n�meros de Falso Negativo)
	 */
	public int readsHamFile(String hamFile,ObservableList<String> regras, ObservableList<Double> pesos){
		int contFP, contPeso,FPValue;
		contFP = contPeso = FPValue = 0;
		String line = "";
		String runLine = "";
		String[] palavra = null;
			
		/**
		 * L� ficheiro de ham.log para calcular a somat�ria de n�meros de Falso Negativo.
		 */
		try {
			FileReader fileReader = new FileReader(hamFile);
			BufferedReader readFile = new BufferedReader(fileReader);
			
			line = readFile.readLine();
			
			while (line != null) {
				
				runLine += line;
				palavra = runLine.split("\t");
				for(String role: regras){	
					for (int j = 0; j < palavra.length; j++) {
						if(palavra[j].equals(role)){
							contFP += pesos.get(contPeso); // Pega peso de cada regra encontrado
							contPeso++;
							if(palavra[palavra.length-1] == palavra[j]){
								if(contFP > 5){
									FPValue++; // contador de todos os valores de Falso Positivos de cada linha
									contFP =  0;
									contPeso = 0;
									break;
								}else{
									contFP =  0;
									contPeso = 0;
									break;
								}
							}
						}
					}
				}

				line = readFile.readLine();
			}
			readFile.close();
		} catch (Exception e) {
			System.out.println("Ficheiro n�o encontrado!");
		}
		return FPValue;
	}
	
	
	/**
	 * 
	 * @param hamFile
	 * @param regras
	 * @param pesos
	 * @return FNValue (retorna a somat�ria de n�meros de Falso Positivo)
	 */
	public int readSpamFile(String spamFile,ObservableList<String> regras, ObservableList<Double> pesos){
		int contFN,contPeso,FNValue;
		contFN = FNValue = contPeso = 0;
		String line = "";
		String runLine = "";
		String[] palavra = null;

		
		/**
		 * L� ficheiro de spam.log para calcular a somat�ria de n�meros de Falso Positivo
		 */
		try {
			FileReader fileReader = new FileReader(spamFile);
			BufferedReader readFile = new BufferedReader(fileReader);
			
			line = readFile.readLine();
			
			while (line != null) {
				
				runLine += line;
				palavra = runLine.split("\t");
				for(String role: regras){	
					for (int j = 0; j < palavra.length; j++) {
						if(palavra[j].equals(role)){
							contFN += pesos.get(contPeso); // Pega peso de cada regra encontrado
							contPeso++;
							if(palavra[palavra.length-1] == palavra[j]){
								if(contFN <= 5){
									FNValue++; // contador de todos os valores de Falso Positivos de cada linha
									contFN =  0;
									contPeso = 0;
									break;
								}else{
									contFN =  0;
									contPeso = 0;
									break;
								}
							}
						}
					}
				}
				line = readFile.readLine();
			}
			readFile.close();
		} catch (Exception e) {
			System.out.println("Ficheiro n�o encontrado!");
		}
		return FNValue;
	}

	/**
	 * 
	 * @param nomeFicheiro
	 * @param regras
	 * @param pesos
	 */
	public void lerFicheiro(String nomeFicheiro, ObservableList<String> regras, ObservableList<Double> pesos) {

		try {
			Scanner s = new Scanner(new File(nomeFicheiro));
			
			while (s.hasNext()) {
				String line = s.next();
				
				if (line.contains(";")) {
					int pos = line.indexOf(";");
					regras.add(line.substring(0, pos));
					double peso = Double.parseDouble(line.substring(pos+1));
					pesos.add(peso);
					System.out.println(peso);
				}else {
				regras.add(line);
				}
			}
			s.close();
		} catch (FileNotFoundException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Erro! O Ficheiro N�o Foi Encontrado Na Direct�ria!");
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
			System.out.println("File Written");
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Erro! N�o foi escrever no ficheiro.");
			alert.showAndWait();
		}
	}
}
