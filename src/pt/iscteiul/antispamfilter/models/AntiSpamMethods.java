package pt.iscteiul.antispamfilter.models;

import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iscteiul.antispamfilter.models.dao.DadosDao;

public abstract class AntiSpamMethods {

	public static int falsoPositivo = 0;
	public static int falsoNegativo = 0;
	public static int falsoPositivoAuto = 0;
	public static int falsoNegativoAuto = 0;
	public static ObservableList<String> pesosListView = FXCollections.observableArrayList();

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
							falsoPositivo++;
							if (fx != null) {
								fx[0]++;
							}
							somaPesos = 0;
						} else {
							somaPesos = 0;
						}
					}
					if (tipo.equals(TipoFicheiro.Ham)) {
						if (somaPesos <= 5) {
							falsoNegativo++;
							if (fx != null) {
								fx[1]++;
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
	 * 
	 * @param regras
	 * @param regraSpamEHam
	 * @param x
	 * @param tipo
	 * @param fx
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
