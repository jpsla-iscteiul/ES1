package models.dao;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;;

//Classe para ler os ficheiros 
public class DadosDao {
	public ObservableList<String> listHam = FXCollections.observableArrayList();
	public ObservableList<String> listSpam = FXCollections.observableArrayList();
	public int FN = 0;
	public int FP = 0;
	/**
	 * 	
	 * @param spamFile
	 * @param regras
	 * @param pesos
	 * @return FNValue (retorna a somatória de números de Falso Negativo)
	 */
	public void readsHamSpamFile(String spamFile,String hamFile,ObservableList<String> regras, ObservableList<Integer> pesos){
		int contFP, FPValue,contFN;
		contFP = FPValue = contFN = 0;

		String line = "";
		String runLine = "";
		String[] palavra = null;
			
		/**
		 * Lê ficheiro de ham.log para calcular a somatória de números de Falso Negativo.
		 */
		try {
			FileReader fileReader = new FileReader(spamFile);
			BufferedReader readFile = new BufferedReader(fileReader);
			line = readFile.readLine();
			while (line != null) {
				runLine += line;
				palavra = runLine.split("\t");
				listSpam.add(line);
				for (int j = 0; j < palavra.length; j++) {
					for (int i = 0; i < regras.size(); i++) {
						if(palavra[j].equals(regras.get(i))){
							contFP += pesos.get(i); // Pega peso de cada regra encontrado
							if(palavra[palavra.length-1] == palavra[j]){
								if(contFP >= 5){
									this.FP++; // contador de todos os valores de Falso Positivos de cada linha
									contFP =  0;
									break;
								}else{
									contFP =  0;
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
			System.out.println("Ficheiro não encontrado!");
		}
		
		/**
		 * Lê ficheiro de spam.log para calcular a somatória de números de Falso Positivo
		 */
		line = "";
		runLine = "";
		palavra = null;
		try {
			FileReader fileReader = new FileReader(hamFile);
			BufferedReader readFile = new BufferedReader(fileReader);
			line = readFile.readLine();
			while (line != null) {
				runLine += line;
				palavra = runLine.split("\t");
				listHam.add(line);
				for (int j = 0; j < palavra.length; j++) {
					for (int i = 0; i < regras.size(); i++) {
						if(palavra[j].equals(regras.get(i))){
							contFN += pesos.get(i); // Pega peso de cada regra encontrado
							if(palavra[palavra.length-1] == palavra[j]){
								if(contFN < 5){
									this.FN++; // contador de todos os valores de Falso Positivos de cada linha
									contFN =  0;
									break;
								}else{
									contFN =  0;
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
			System.out.println("Ficheiro não encontrado!");
		}
	}
	

	/**
	 * 
	 * @param nomeFicheiro
	 * @param regras
	 * @param pesos
	 */
	public void lerFicheiro(String nomeFicheiro, ObservableList<String> regras, ObservableList<Integer> pesos) {

		try {
			Scanner s = new Scanner(new File(nomeFicheiro));
			
			while (s.hasNext()) {
				String line = s.next();
				
				if (line.contains(";")) {
					int pos = line.indexOf(";");
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
			System.out.println("Nao encontrou o ficheiro");
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
			e.printStackTrace();
		}
	}
}
