package pt.iscteiul.antispamfilter.jUnitTests;



import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iscteiul.antispamfilter.models.AntiSpamMethods;
import pt.iscteiul.antispamfilter.models.TipoFicheiro;
import pt.iscteiul.antispamfilter.models.dao.DadosDao;

class AntiSpamMethodsTester {

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
	void testGerarPesos() {

		ObservableList<String> regras = FXCollections.observableArrayList();
		ObservableList<Double> pesosRegras = FXCollections.observableArrayList();

		DadosDao d = new DadosDao();
		d.lerFicheiro("jUnitTests/rulesTest.cf", regras, pesosRegras, TipoFicheiro.Rules);
		
		AntiSpamMethods.gerarPesos(regras, pesosRegras);

	}

	@Test
	void testCalcularFpEFn() throws IOException {
		ObservableList<String> regras = FXCollections.observableArrayList();
		ObservableList<Double> pesosRegras = FXCollections.observableArrayList();

		DadosDao d = new DadosDao();
		d.lerFicheiro("jUnitTests/rulesTest.cf", regras, pesosRegras, TipoFicheiro.Rules);

		String[] args1 = null;

		AntiSpamFilterAutomaticConfiguration.automaticConfiguration(args1, regras, pesosRegras,
				"jUnitTests/spamTest.log", "jUnitTests/hamTest.log");
	}

	@Test
	void testPesosOptimizados() {

		ObservableList<String> regras = FXCollections.observableArrayList();
		ObservableList<Double> pesosRegras = FXCollections.observableArrayList();

		DadosDao d = new DadosDao();
		d.lerFicheiro("jUnitTests/rulesTest.cf", regras, pesosRegras, TipoFicheiro.Rules);
		
		AntiSpamMethods.pesosOptimizados(regras);

	}

}
