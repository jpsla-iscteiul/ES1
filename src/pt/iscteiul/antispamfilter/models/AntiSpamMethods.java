package pt.iscteiul.antispamfilter.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class AntiSpamMethods {

	public static int falsoPositivo = 0;
	public static int falsoNegativo = 0;
	public static ObservableList<String> listWights = FXCollections.observableArrayList();
	public static float FNValue, FPValue;

	/**
	 * 
	 * @param regras
	 * @param pesos
	 */
	public static void gerarPesos(ObservableList<String> regras, ObservableList<Double> pesos) {

		double pesoMin = -5.1;
		double pesoMax = 5.1;
		Random random = new Random();

		for (int i = 0; i < regras.size(); i++) {
			pesos.addAll(random.nextDouble() * (pesoMax - pesoMin) + pesoMin);
		}
	}

	/**
	 * 
	 * @param regras
	 * @param regraSpamEHam
	 * @param pesosRegras
	 * @param tipo
	 */
	public static void calcularFpEFn(ObservableList<String> regras, ObservableList<String> regraSpamEHam,
			ObservableList<Double> pesosRegras, TipoFicheiro tipo) {

		double somaPesos = 0;
		for (int i = 0; i < regraSpamEHam.size(); i++) {
			if (Character.isDigit(regraSpamEHam.get(i).charAt(0))) {}
			if (!Character.isDigit(regraSpamEHam.get(i).charAt(0))) {
				for (int j = 0; j < regras.size(); j++) {
					if (regras.get(j).equals(regraSpamEHam.get(i))) {
						somaPesos += pesosRegras.get(j);
						 j = 0;
						break;
					}
				}
			} else {
				if (i > 0) {
<<<<<<< HEAD
					if(tipo.equals(TipoFicheiro.Spam)) {
						if (somaPesos >= 5) {
							falsoPositivo++;
							somaPesos = 0;
						}else {
							somaPesos = 0;
						}
					} 	
					if(tipo.equals(TipoFicheiro.Ham)) {
						if (somaPesos < 5) {
							falsoNegativo++;
							somaPesos = 0;
						}else {
							somaPesos = 0;
						}
					}
				}
=======
				if(tipo.equals(TipoFicheiro.Spam)) {
					if (somaPesos > 5) {
						falsoPositivo++;
						somaPesos = 0;
			}else {
			somaPesos = 0;
			}
		} 	
				if(tipo.equals(TipoFicheiro.Ham)) {
					/*try {
						  Thread.sleep(4000);// pausa de 2000 milisegundos
						}catch (InterruptedException e) {
						  e.printStackTrace(); 
						}*/
					if (somaPesos <= 5) {
					falsoNegativo++;
					/*System.out.println("\\n");
					System.out.println("Valor total da soma "+ somaPesos);
					System.out.println("\\n");
					System.out.println("Falsos negativos: " + falsoNegativo);
					System.out.println("\\n");
						*/
			somaPesos = 0;
			}else {
				somaPesos = 0;
>>>>>>> branch 'Evaluation_Delcio' of https://github.com/jpsla-iscteiul/ES1-2017-METIA1-44.git
			}
		}
	}
	
	
	
	/**
	 * 	
	 * @param regras
	 * @param regraSpamEHam
	 * @param x
	 * @param tipo
	 * @param fx
	 */
	public static void calcularFpEFnAutomantico(ObservableList<String> regras, ObservableList<String> regraSpamEHam,
			double[] x, TipoFicheiro tipo, double[] fx) {

		double somaPesos = 0;	
		for (int i = 0; i < regraSpamEHam.size(); i++) {
			if (Character.isDigit(regraSpamEHam.get(i).charAt(0))) { }
			
			if (!Character.isDigit(regraSpamEHam.get(i).charAt(0))) {
				for (int j = 0; j < regras.size(); j++) {
					if (regras.get(j).equals(regraSpamEHam.get(i))) {
						somaPesos += x[j];  
						 j = 0;
						break;
					}
				}
			} else {
				if (i > 0) {
					if(tipo.equals(TipoFicheiro.Spam)) {
						if (somaPesos >= 5) {
							fx[0]++; 
							somaPesos = 0;
						}else {
							somaPesos = 0;
						}
					} 	
					if(tipo.equals(TipoFicheiro.Ham)) {
						if (somaPesos < 5) {
							fx[1]++;  
							somaPesos = 0;
						}else {
							somaPesos = 0;
						}
					}
				}
			}
		}
//		System.out.println("Numero de Falsos Positivos: " + fx[0] + 
//				"  Numero de Falsos Negativos " + fx[1]);
	}
		

	/**
	 * Metodo que exibe as regras automatico no listView e s� muda Path quando l� o fichiros de extens�o .rf e .rs
	 */
	public static void listaOptimizado(){
		
		int linha = 0, posicao = 0;
		float menor =  999999999;
		float numero = 0;
		List<String[]> lista = new ArrayList<>();
		List<String[]> listaRegra = new ArrayList<>();
		String line = null;
		String[] role = null;
		String roles = null;
		try {
			FileReader fileReader = new FileReader("C:/Users/amane/git/ES1-2017-METIA1-44/experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rf");
			BufferedReader readFile = new BufferedReader(fileReader);
			line = readFile.readLine();
			while (line != null){
				role = line.split(" ");
				lista.add(role);
				line = readFile.readLine();
			}
			linha = 0;
			for (int i = 0; i <= lista.size()-1; i++) {
				for (int j = 0; j < lista.size(); j++) {
					if(j == 1){
						linha++;
						numero = Float.parseFloat(lista.get(i)[j]); // Converte o valor de um String para um nomero float
						if(numero < menor){
							menor = numero;
							posicao = linha;
							FNValue = Float.parseFloat(lista.get(i)[j-1]);
							FPValue = Float.parseFloat(lista.get(i)[j]);
						}
					}
				}			
			}
			readFile.close();
		} catch (Exception e) {
			System.out.println("Ficheiro nao encontrado!\n");
		}
		

		try {
			line = "";
			int i = 0;
			FileReader fileReader = new FileReader("C:/Users/amane/git/ES1-2017-METIA1-44/experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
			BufferedReader readFile = new BufferedReader(fileReader);
			line = readFile.readLine();
			while (line != null){
				role = line.split(" ");
				listaRegra.add(role);
				i++;
				if(posicao == i){
					roles = line.replace(" ", "\n\n");
					listWights.add(roles);
					break;
				}
				line = readFile.readLine();
			}		
			readFile.close();;
		} catch (Exception e) {
			System.out.println("Ficheiro nao encontrado!\n");
		}		
	}
}