package pt.iscteiul.antispamfilter.jUnitTests;



import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iscteiul.antispamfilter.models.AntiSpamMethods;
import pt.iscteiul.antispamfilter.models.TipoFicheiro;
import pt.iscteiul.antispamfilter.models.dao.DadosDao;

class DadosDaoTester {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testLerFicheiro() {

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

	@Test
	void testEscreverFicheiro() {
		DadosDao d = new DadosDao();

		String nomeFicheiro = "jUnitTests/writeTest.cf";

		String text = "Ola Mundo!";

		d.escreverFicheiro(nomeFicheiro, text);

	}

}
