package pt.iscteiul.antispamfilter.controlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import pt.iscteiul.antispamfilter.models.AntiSpamMethods;
import pt.iscteiul.antispamfilter.models.TipoFicheiro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;

import pt.iscteiul.antispamfilter.models.dao.DadosDao;

/**
 * Classe controladora da GUI Main View (Interface gráfica)
 * <p>
 * Esta classe contém:
 * <ul>
 * <li>Três checkBoxs que permitem selecionar o tipo de ficheiro a carregar,
 * como exemplo a spamCB para carregar um ficheiro Spam
 * <li>Quatro textfields para receberem textos
 * <li>Quatro labels onde são adicionados os valores dos falsos positivos e
 * falsos negativos de ambas configurações
 * <li>
 * 
 * @author João Lola 83169 Délcio Pedro 81611 Ansumane Mane 79170
 * 
 * 
 */

public class AntiSpamFilterController {

	@FXML
	private CheckBox rulesCB;
	@FXML
	private CheckBox spamCB;
	@FXML
	private CheckBox hamCB;
	@FXML
	private TextField weightsTF;
	@FXML
	private TextField rulesTF;
	@FXML
	private TextField spamTF;
	@FXML
	private TextField hamTF;

	@FXML
	private Label fpLBL;
	@FXML
	private Label fnLBL;
	@FXML
	private Label optFpLBL;
	@FXML
	private Label optFnLBL;

	@FXML
	private ListView<String> rulesLV;
	@FXML
	private ListView<Double> weightsLV;
	@FXML
	private ListView<String> optRulesLV;
	@FXML
	private ListView<String> optWeightsLV;
	private ObservableList<String> regras = FXCollections.observableArrayList();
	private ObservableList<Double> pesosRegras = FXCollections.observableArrayList();

	@FXML
	void initialize() {
	}

	/**
	 * Permite procurar por ficheiros na directoria
	 * <p>
	 * Utiliza checkBoxs para selecionar o tipo de ficheiro que se pretende
	 * carregar e se estiver selecionado carrega o caminho até ao ficheiro e
	 * desseleciona a checkBox {@code if (rulesCB.isSelected()) {
	 * rulesTF.setText(path); rulesCB.setSelected(false); } }
	 */
	@FXML
	public void browse() {

		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
		if (selectedFile == null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setHeaderText(" Operação cancelada!! ");
			alert.showAndWait();

		} else {
			String path = selectedFile.getPath();
			if (rulesCB.isSelected()) {
				rulesTF.setText(path);
				rulesCB.setSelected(false);
			}
			if (hamCB.isSelected()) {
				hamTF.setText(path);
				hamCB.setSelected(false);
			}
			if (spamCB.isSelected()) {
				spamTF.setText(path);
				spamCB.setSelected(false);
			}
		}
	}

	/**
	 * Carrega os dados dos ficheiros para as duas ListView de regras e para a
	 * ListView de pesos de configuração manual
	 * <p>
	 * Verifica se o ficheiro contém os pesos das regras, caso não contém
	 * adiciona pesos aleatórios {@code if (pesosRegras.isEmpty()) {
	 * AntiSpamMethods.gerarPesos(regras, pesosRegras);
	 * weightsLV.setItems(pesosRegras); } }
	 */

	public void loadFiles() {

		DadosDao dadosRules = new DadosDao();
		dadosRules.lerFicheiro(rulesTF.getText(), regras, pesosRegras, TipoFicheiro.Rules);

		rulesLV.setItems(regras);
		weightsLV.setItems(pesosRegras);
		optRulesLV.setItems(regras);

		if (pesosRegras.isEmpty()) {
			AntiSpamMethods.gerarPesos(regras, pesosRegras);
			weightsLV.setItems(pesosRegras);
		}

	}

	/**
	 * Permite alterar os pesos da ListView de pesos de configuração manual
	 * <p>
	 * Verifica se o valor que se pretende adicionar a textField para alterar na
	 * ListView está entre -6 e 6 exclusivos {@code if (peso > -6 && peso < 6) {
	 * pesosRegras.set(position, peso); weightsLV.setItems(pesosRegras); } }
	 * <p>
	 * Caso não esteja lança um alerta do tipo erro
	 */
	public void editWeights() {

		double peso = Double.parseDouble(weightsTF.getText());
		int position = weightsLV.getSelectionModel().getSelectedIndex();
		if (peso > -6 && peso < 6) {
			pesosRegras.set(position, peso);
			weightsLV.setItems(pesosRegras);
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Erro! Apenas valores entre -6 e 6 exclusive!!!");
			alert.showAndWait();
		}
	}

	/**
	 * Avalia se a configuração manual existente é adequada calculando o número
	 * de falsos positivos (spam) e falsos negativos (ham)
	 * <p>
	 * Por fim adiciona os falsos positivos e negativos calculados as Labels
	 * {@code
	 * fpLBL.setText(String.valueOf(AntiSpamMethods.falsoPositivo));
	 * fnLBL.setText(String.valueOf(AntiSpamMethods.falsoNegativo));
	 *}
	 */

	public void filterEvaluation() {

		ObservableList<String> regraSpam = FXCollections.observableArrayList();
		DadosDao dadosSpam = new DadosDao();
		dadosSpam.lerFicheiro(spamTF.getText(), regraSpam, pesosRegras, TipoFicheiro.Spam);
		AntiSpamMethods.calcularFpEFn(regras, regraSpam, pesosRegras, TipoFicheiro.Spam, null, null);
		fpLBL.setText(String.valueOf(AntiSpamMethods.falsoPositivo));

		ObservableList<String> regrasHam = FXCollections.observableArrayList();
		DadosDao dadosHam = new DadosDao();
		dadosHam.lerFicheiro(hamTF.getText(), regrasHam, pesosRegras, TipoFicheiro.Ham);
		AntiSpamMethods.calcularFpEFn(regras, regrasHam, pesosRegras, TipoFicheiro.Ham, null, null);
		fnLBL.setText(String.valueOf(AntiSpamMethods.falsoNegativo));
	}

	/**
	 * Gera configuração optimizada, calculando os falsos positivos e negativos
	 * automaticamente através do algoritmo dado NSGAII {@code
	 * AntiSpamFilterAutomaticConfiguration.automaticConfiguration(args, regras, pesosRegras, spamTF.getText(),
		hamTF.getText());	
	 *}
	 * <p>
	 * Carrega os pesos das regras gerado pelo algoritmo para a ListView de
	 * pesos
	 * <p>
	 * E adiciona as labels o número de falsos positivos e negativos optimazados
	 * gerados pelo algoritmo
	 */

	public void generateOptimizedConfiguration() throws IOException {

		String[] args = null;

		AntiSpamFilterAutomaticConfiguration.automaticConfiguration(args, regras, pesosRegras, spamTF.getText(),
				hamTF.getText());

		AntiSpamMethods.pesosOptimizados(regras);
		optWeightsLV.setItems(AntiSpamMethods.pesosListView);
		optFnLBL.setText(String.valueOf(AntiSpamMethods.falsoNegativoAuto));
		optFpLBL.setText(String.valueOf(AntiSpamMethods.falsoPositivoAuto));
	}

	/**
	 * Método para guardar no ficheiro a configuração manual gerada pelo calculo
	 * dos falsos positivos e negativos
	 */

	public void saveConfiguration() throws FileNotFoundException {

		PrintWriter fileWriterrulesTF = new PrintWriter(new File("AntiSpamConfigurationForLeisureMailbox/rules.cf"));
		for (int i = 0; i < regras.size(); i++) {
			fileWriterrulesTF.write(regras.get(i) + ";" + pesosRegras.get(i) + " ");
		}
		fileWriterrulesTF.close();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("A configuração foi salva com sucesso no ficheiro.");
		alert.showAndWait();
	}

	/**
	 * Método para guardar ou sobrepôr no ficheiro a configuração automática
	 * gerada pelo calculo dos falsos positivos e negativos
	 */

	public void overwriteSavedConfiguration() throws FileNotFoundException {

		PrintWriter fileWriterrules = new PrintWriter(new File("AntiSpamConfigurationForLeisureMailbox/rules.cf"));
		for (int i = 0; i < regras.size(); i++) {
			fileWriterrules.write(regras.get(i) + ";" + AntiSpamMethods.pesosListView.get(i) + " ");
		}
		fileWriterrules.close();

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setHeaderText("A configuração foi salva com sucesso no ficheiro.");
		alert.showAndWait();
	}
}
