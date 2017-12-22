package pt.iscteiul.antispamfilter.models;

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iscteiul.antispamfilter.models.dao.DadosDao;

/**
 * Classe para gerar os pesos aleatórios, os pesos optimizados  
 * e calcular os falsos positivos e negativos. 
 * Serve de auxilio a classe AntiSpamFilterController 
 *<li>
 * @author João Lola 83169, Délcio Pedro 81611
 */

public abstract class AntiSpamMethods {

	public static int falsoPositivo = 0;
	public static int falsoNegativo = 0;
	public static int falsoPositivoAuto = 0;
	public static int falsoNegativoAuto = 0;
	public static ObservableList<String> pesosListView = FXCollections.observableArrayList();

	/**
	 *Método que gera pesos aleatórios a serem utilizados na ListView de pesos 
	 *caso as regras não tenham pesos associados 
	 *<li>
	 *Este método tem como parametros:
	 * <br>
	 * uma lista de regras, que será utilizado o seu tamanho 
	 * para a lista de pesos 
	 * @link regras
	 * <br>
	 * e uma lista de pesos onde serão adicionaos os pesos
	 * correspondentes as regras 
	 *@link pesos   
	 * 
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
	 * Método para calcular os falsos positivos e negativos para ambos 
	 * os tipos de ficheiros e configuração (automática ou manual)
	 * <p>  
	 * Recebe como parametros:
	 * <br>
	 * A lista com regras
	 * @param regras
	 * <p>
	 * Uma lista  de regras que pode ser do ficheiro spam ou do ham
	 * @param regraSpamEHam
	 * <br>
	 * Uma lista com os pesos das regras 
	 * @param pesosRegras
	 * <p>
	 * O tipo de ficheiro, se spam calcula falsos positivos e se ham 
	 * calcula falsos negativos
	 * @param tipo
	 * <p>
	 * E os arrays ou listas pertencentes ao código da classe
	 * @link AntiSpamFilterProblem 
	 * @param fx 
	 * @param x 
	 */
	public static void calcularFpEFn(ObservableList<String> regras, ObservableList<String> regraSpamEHam,
			ObservableList<Double> pesosRegras, TipoFicheiro tipo, double[] fx, double[] x) {

		double somaPesos = 0;
		for (int i = 0; i < regraSpamEHam.size(); i++) {
			if (Character.isDigit(regraSpamEHam.get(i).charAt(0))) {
			}
			if (!Character.isDigit(regraSpamEHam.get(i).charAt(0))) {
				for (int j = 0; j < regras.size(); j++) {
					if (regras.get(j).equals(regraSpamEHam.get(i))) {
						if (pesosRegras != null) {
							somaPesos += pesosRegras.get(j);
						}
						if (x != null) {
							somaPesos += x[j];
						}
						j = 0;
						break;
					}
				}
			} else {
				if (i > 0) {

					if (tipo.equals(TipoFicheiro.Spam)) {
						if (somaPesos >= 5) {
							System.out.println("somaPesos maior "+ somaPesos);
							falsoPositivo++;
							System.out.println("falsos positivos "+ falsoPositivo);
							if (fx != null) {
								fx[0]++;
								System.out.println("FX [0] " + fx[0]);
							}
							somaPesos = 0;
						} else {
							somaPesos = 0;
						}
					}
					if (tipo.equals(TipoFicheiro.Ham)) {
						if (somaPesos <= 5) {
							falsoNegativo++;
							System.out.println("falsos negativos "+ falsoNegativo);
							if (fx != null) {
								fx[1]++;
								System.out.println("FX[1]" + fx[1]);
							}
							somaPesos = 0;
						} else {
							somaPesos = 0;

						}
					}
				}
			}
		}
	}

	/**
	 * Método que calcula o menor valor de uma lista de valores 
	 * e converte o resultado em inteiro. Este é utilizado  
	 * para auxiliar o método pesos optimizados. 
	 * <br>
	 * Tem como parametro uma lista de valores do tipo String
	 * @param valores
	 */
	private static int menorValor(ObservableList<String> valores) {
		int fnMenor = 0;
		ObservableList<Integer> fNs = FXCollections.observableArrayList();

		for (int i = 0; i < valores.size(); i++) {
			if (i % 2 != 0) {
				double valor = Double.parseDouble(valores.get(i));
				fNs.add((int) valor);
			}
		}
		int comparador = fNs.get(0);
		for (int i = 1; i < fNs.size(); i++) {
			if (fNs.get(i) < comparador) {
				comparador = fNs.get(i);
				fnMenor = comparador;
			}
		}

		return fnMenor;
	}

	/**
	 * Método para obter a lista de pesos optimizados pelo algoritmo NSGAII.
	 * Este, utiliza o método menorValor para encontrar o menor valor falso negativo 
	 * gerado pelo algoritmo.    
	 * 
	 * <br>
	 * Tem como parametro a lista de regras que será utilizado o seu 
	 * comprimento para localizar as posições de inicio e fim da lista 
	 * de pesos a ela atribuida pelo algoritmo ou configuração automatizada 
	 * {@code
	 * int fim = regras.size() * linha;
		int inicio = fim - regras.size()}
	 * @param regras
	 * Por fim adiciona os pesos  obtidos a lista de pesos a ser 
	 * utilizada na ListView 
	 */
	public static void pesosOptimizados(ObservableList<String> regras) {

		DadosDao rf = new DadosDao();
		ObservableList<String> listaFpEFN = FXCollections.observableArrayList();
		int linha = 0;
		rf.lerFicheiro("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rf", listaFpEFN, null,
				null);

		falsoNegativoAuto = menorValor(listaFpEFN);
		for (int i = 0; i < listaFpEFN.size(); i++) {
			if (listaFpEFN.get(i).contains(String.valueOf(falsoNegativoAuto))) {
				falsoPositivoAuto = (int) Double.parseDouble(listaFpEFN.get(i - 1));
				linha = (i + 1) / 2;
			}
		}

		DadosDao rs = new DadosDao();
		ObservableList<String> pesosAlgoritmo = FXCollections.observableArrayList();
		rs.lerFicheiro("experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs", pesosAlgoritmo, null,
				null);
		int fim = regras.size() * linha;
		int inicio = fim - regras.size();
		for (int i = inicio; i < fim; i++) {
			pesosListView.add(pesosAlgoritmo.get(i));
		}
	}

}
