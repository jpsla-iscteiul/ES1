package pt.iscteiul.antispamfilter.jUnitTests;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iscteiul.antispamfilter.models.AntiSpamMethods;
import pt.iscteiul.antispamfilter.models.TipoFicheiro;
import pt.iscteiul.antispamfilter.models.dao.DadosDao;

public class AntiSpamMethodsTester {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Este metodo permite testar o metodo gerarPesos da classe
	 * AntiSpamFilterMethods do package pt.isteciul.antispamfilter.models
	 * <p>
	 * Criamos duas observableList para armazenar as regras e os seus pesos
	 * carregados a partir da leitura do ficheiro rules.cf na directoria
	 * jUnitTests.
	 * 
	 * Por ultimo, chamamos o metodo passando lhe os valores.
	 */
	@Test
	public void testGerarPesos() {

		ObservableList<String> regras = FXCollections.observableArrayList();
		ObservableList<Double> pesosRegras = FXCollections.observableArrayList();

		DadosDao d = new DadosDao();
		d.lerFicheiro("jUnitTests/rulesTest.cf", regras, pesosRegras, TipoFicheiro.Rules);

		AntiSpamMethods.gerarPesos(regras, pesosRegras);

	}

	/**
	 * Este metodo permite testar o metodo calcularFpEFn da classe
	 * AntiSpamFilterMethods do package pt.isteciul.antispamfilter.models
	 * <p>
	 * Criamos tres observableList para armazenar as regras e os seus pesos
	 * carregados a partir da leitura do ficheiro rules.cf na directoria
	 * jUnitTests. Juntamente com os ficheiros ham.log e spam.log.
	 * 
	 * Por ultimo, chamamos o metodo passando lhe os valores, juntamente com os
	 * ficheiros de ham.log e spam.log para percorrer os mesmos substituir as
	 * regras pelos pesos correspondente, fazer a respectiva soma da linha e
	 * calcular o nº de fp's e fn's.
	 */

	@Test
	public void testCalcularFpEFn() throws IOException {
		ObservableList<String> regras = FXCollections.observableArrayList();
		ObservableList<Double> pesosRegras = FXCollections.observableArrayList();
		ObservableList<String> regraSpamEHam = FXCollections.observableArrayList();

		DadosDao d = new DadosDao();
		d.lerFicheiro("jUnitTests/rulesTest.cf", regras, pesosRegras, TipoFicheiro.Rules);

		DadosDao dS = new DadosDao();
		dS.lerFicheiro("jUnitTests/spamTest.log", regraSpamEHam, pesosRegras, TipoFicheiro.Spam);
		AntiSpamMethods.calcularFpEFn(regras, regraSpamEHam, pesosRegras, TipoFicheiro.Spam, null, null);

		ObservableList<String> regras1 = FXCollections.observableArrayList();
		ObservableList<Double> pesosRegras1 = FXCollections.observableArrayList();
		ObservableList<String> regraSpamEHam1 = FXCollections.observableArrayList();

		DadosDao d1 = new DadosDao();
		d1.lerFicheiro("jUnitTests/rulesTest.cf", regras1, pesosRegras1, TipoFicheiro.Rules);

		DadosDao dS1 = new DadosDao();
		dS1.lerFicheiro("jUnitTests/hamTest.log", regraSpamEHam1, pesosRegras, TipoFicheiro.Ham);
		AntiSpamMethods.calcularFpEFn(regras1, regraSpamEHam1, pesosRegras1, TipoFicheiro.Ham, null, null);
	}

	/**
	 * Este metodo permite testar o metodo pesosOptimizados da classe
	 * AntiSpamFilterMethods do package pt.isteciul.antispamfilter.models
	 * <p>
	 * Criamos duas observableList para armazenar as regras e os seus pesos
	 * carregados a partir da leitura do ficheiro rules.cf na directoria
	 * jUnitTests.
	 * 
	 * Por ultimo, chamamos o metodo passando lhe as regras para este gerar um
	 * conjunto de valores otimizados recorrendo ao algoritmo NSGA-II.
	 */

	@Test
	public void testPesosOptimizados() {
		ObservableList<String> regras = FXCollections.observableArrayList();
		ObservableList<Double> pesosRegras = FXCollections.observableArrayList();

		DadosDao d = new DadosDao();
		d.lerFicheiro("jUnitTests/rulesTest.cf", regras, pesosRegras, TipoFicheiro.Rules);

		AntiSpamMethods.pesosOptimizados(regras);
	}

}

