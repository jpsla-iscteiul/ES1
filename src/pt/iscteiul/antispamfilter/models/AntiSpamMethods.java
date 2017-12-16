package pt.iscteiul.antispamfilter.models;

import java.util.Random;
import javafx.collections.ObservableList;

public abstract class AntiSpamMethods {

	public static int falsoPositivo = 0;
	public static int falsoNegativo = 0;

	public static void gerarPesos(ObservableList<String> regras, ObservableList<Double> pesos) {

		double pesoMin = -5.1;
		double pesoMax = 5.1;
		Random random = new Random();

		for (int i = 0; i < regras.size(); i++) {
			pesos.addAll(random.nextDouble() * (pesoMax - pesoMin) + pesoMin);
		}
	}

	public static void calcularFpEFn(ObservableList<String> regras, ObservableList<String> regraSpamEHam,
			ObservableList<Double> pesosRegras, TipoFicheiro tipo) {

		double somaPesos = 0;

		
		for (int i = 0; i < regraSpamEHam.size(); i++) {
			if (Character.isDigit(regraSpamEHam.get(i).charAt(0))) {
				System.out.println("\\n");
			System.out.println("Achei um numero!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ");
			}
			
			if (!Character.isDigit(regraSpamEHam.get(i).charAt(0))) {
				for (int j = 0; j < regras.size(); j++) {
					if (regras.get(j).equals(regraSpamEHam.get(i))) {
						somaPesos += pesosRegras.get(j);
						System.out.println("Regra a procurar " + regraSpamEHam.get(i)
						+ " Igual a essa: " + regras.get(j));
						System.out.println("Regra  " + regras.get(j) + "  Peso: " + pesosRegras.get(j));
						 j = 0;
						break;
					}
				}
			} else {
				if (i > 0) {
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
					if (somaPesos <= -5) {
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
			}
		}
	}
   }
  }
		System.out.println("Numero de Falsos Positivos: " + falsoPositivo + 
				"  Numero de Falsos Negativos " + falsoNegativo);
	
 }
}