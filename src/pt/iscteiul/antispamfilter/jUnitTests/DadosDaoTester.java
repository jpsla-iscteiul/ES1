package pt.iscteiul.antispamfilter.jUnitTests;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import pt.iscteiul.antispamfilter.models.TipoFicheiro;
import pt.iscteiul.antispamfilter.models.dao.DadosDao;

public class DadosDaoTester {

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
	 * Este metodo permite testar o metodo lerFicheiro da classe DadosDao do
	 * package pt.isteciul.antispamfilter.models.dao
	 * <p>
	 * Criamos duas observableList para armazenar as regras,seus pesos e as
	 * regras carregados a partir da leitura do ficheiro rulesTest.cf na
	 * directoria jUnitTests.
	 * 
	 * Por ultimo, chamamos o metodo passando lhe os valores.
	 */

	@Test
	public void testLerFicheiro() {

		ObservableList<String> regras = FXCollections.observableArrayList();
		ObservableList<Double> pesosRegras = FXCollections.observableArrayList();

		DadosDao d = new DadosDao();
		d.lerFicheiro("jUnitTests/rulesTest.cf", regras, pesosRegras, TipoFicheiro.Rules);

	}

	/**
	 * Este metodo permite testar o metodo escreverFicheiro da classe DadosDao
	 * do package pt.isteciul.antispamfilter.models.dao.
	 * <p>
	 * Passando lhe um ficheiro para escrita e uma string que contem uma frase
	 * para escrever dentro deste
	 *
	 */
	@Test
	public void testEscreverFicheiro() {
		DadosDao d = new DadosDao();

		String nomeFicheiro = "jUnitTests/writeTest.cf";

		String text = "Ola Mundo!";

		d.escreverFicheiro(nomeFicheiro, text);
	}

}
