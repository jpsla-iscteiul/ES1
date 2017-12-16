package pt.iscteiul.antispamfilter.controlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import pt.iscteiul.antispamfilter.AntiSpamFilterAutomaticConfiguration;
import pt.iscteiul.antispamfilter.models.AntiSpamMethods;
import pt.iscteiul.antispamfilter.models.TipoFicheiro;
import pt.iscteiul.antispamfilter.models.dao.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 *
 * @author João Lola
 * @author Délcio Pedro
 * 
 */
public class AntiSpamFilterController {

	/* GUI TEXTFIELD'S DECLARATION */
	@FXML
	private CheckBox rulesCB;
	@FXML
	private CheckBox spamCB;
	@FXML
	private CheckBox hamCB;
	@FXML
	private ChoiceBox<Double> weightCB;
	@FXML
	private TextField weightsTF;
	@FXML
	private TextField rulesTF;
	@FXML
	private TextField spamTF;
	@FXML
	private TextField hamTF;

	/* GUI LABEL'S DECLARATION */

	@FXML
	private Label fpLBL;
	@FXML
	private Label fnLBL;
	@FXML
	private Label optFpLBL;
	@FXML
	private Label optFnLBL;

	/* GUI LISTVIEW'S DECLARATION */

	// Parametrizar - Não Esquecer//
	@FXML
	private ListView<String> rulesLV;
	@FXML
	private ListView<Double> weightsLV;
	@FXML
	private ListView<String> optRulesLV;
	@FXML
	private ListView<Double> optWeightsLV;
	private ObservableList<String> regras = FXCollections.observableArrayList();
	private ObservableList<Double> pesosRegras = FXCollections.observableArrayList();
	private Double[] pesosChoiceBox = {-5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };

	@FXML
	void initialize() {

		
	}

	@FXML

	/**
	 * 
	 * Este método permite guardar as alterações aos nomes e pesos das regras.
	 * 
	 */
	// gravar os pesos no ruleCF

	public void edit() {

	}

	// O metodo evaluate (com os ficheiros spam.log e ham.log)
	// Metodo para guardar as configurações do ficheiro
	/**
	 * Método Save
	 * 
	 * 
	 */

	//
	public void saveConfiguration() throws FileNotFoundException {

		PrintWriter fileWriterrulesTF = new PrintWriter(new File(rulesTF.getText()));
		for (int i = 0; i < regras.size(); i++) {
			fileWriterrulesTF.write(regras.get(i) + ";" + pesosRegras.get(i) + " ");
		}
		fileWriterrulesTF.close();
	}

	// Metodo para procurar o ficheiro e guardar o seu path
	/**
	 * Método Browse permite procurar os ficheiros
	 * 
	 * 
	 */
	public void browse() {

		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
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

	public void generateOptimizedConfiguration() {

		AntiSpamFilterAutomaticConfiguration anti = new AntiSpamFilterAutomaticConfiguration();
		optFnLBL.setText(String.valueOf(20));
		optFpLBL.setText(String.valueOf(100));
	}

	/**
	 * Metodo loadHamSpamFile que chama metodos (readsHamFile e readSpamFile).
	 */
	
	// Metodo para ler os ficheiros e carregar o ficheiro rules file para a list
	// view rulesLV
	/**
	 * Método Browse permite procurar os ficheiros
	 * 
	 * 
	 */
	public void ler() {
		
	}
	public void loadFiles() {
		
		DadosDao dadosRules = new DadosDao();
		dadosRules.lerFicheiro(rulesTF.getText(), regras, pesosRegras, TipoFicheiro.Rules);
		
		rulesLV.setItems(regras);
		weightsLV.setItems(pesosRegras);
		weightCB.getItems().addAll(pesosChoiceBox);
		optRulesLV.setItems(regras);

		if (pesosRegras.isEmpty()) {
			AntiSpamMethods.gerarPesos(regras, pesosRegras);
			weightsLV.setItems(pesosRegras);	
		}
		
	}
	public void editWeights() {

		double peso = (Double) weightCB.getSelectionModel().getSelectedItem();
		int position = weightsLV.getSelectionModel().getSelectedIndex();
		pesosRegras.set(position, peso);
		weightsLV.setItems(pesosRegras);
	}

	
	public void filterEvaluation() {

		ObservableList<String> regraSpam = FXCollections.observableArrayList();
		DadosDao dadosSpam = new DadosDao();
		dadosSpam.lerFicheiro(spamTF.getText(), regraSpam, pesosRegras, TipoFicheiro.Spam);
		AntiSpamMethods.calcularFpEFn(regras, regraSpam, pesosRegras, TipoFicheiro.Spam);
		fpLBL.setText(String.valueOf(AntiSpamMethods.falsoPositivo));
		
		ObservableList<String> regrasHam = FXCollections.observableArrayList();
		DadosDao dadosHam = new DadosDao();
		dadosHam.lerFicheiro(hamTF.getText(), regrasHam, pesosRegras, TipoFicheiro.Ham);
		AntiSpamMethods.calcularFpEFn(regras, regrasHam, pesosRegras, TipoFicheiro.Ham);
		fnLBL.setText(String.valueOf(AntiSpamMethods.falsoNegativo));
		}
}
