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
import models.dao.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

/**
 *
 * @author João Lola
 * @author Délcio Pedro
 * 
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
	private ObservableList<Double> weights = FXCollections.observableArrayList();
	private Double[] pesos = { -5.0, -4.0, -3.0, -2.0, -1.0, 0.0, 1.0, 2.0, 3.0, 4.0, 5.0 };

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
	public void saveConfiguration() throws FileNotFoundException {

		PrintWriter fileWriterrulesTF = new PrintWriter(new File(rulesTF.getText()));

		for (int i = 0; i < regras.size(); i++) {
			fileWriterrulesTF.write(regras.get(i) + ";" + weights.get(i) + " ");
			System.out.println(regras.get(i) + ";" + weights.get(i) + " ");
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

	/**
	 * Metodo loadHamSpamFile que chama metodos (readsHamFile e readSpamFile).
	 */
	public void loadHamSpamFile() {

		int FPValue;
		int FNValue;
		DadosDao dadosDao = new DadosDao();
		FPValue = dadosDao.readsHamFile(spamTF.getText(), regras, weights);
		FNValue = dadosDao.readSpamFile(hamTF.getText(), regras, weights);

		System.out.println("FP ==> " + FPValue + "\n\n");
		System.out.println("FN ==> " + FNValue + "\n\n");
	}

	// Metodo para ler os ficheiros e carregar o ficheiro rules file para a list
	// view rulesLV
	/**
	 * Método Browse permite procurar os ficheiros
	 * 
	 * 
	 */
	public void loadFiles() {
		DadosDao d = new DadosDao();
		d.lerFicheiro(rulesTF.getText(), regras, weights);

		// System.out.println("Regras ===>" + regras + "\n\n\n");
		// d.lerFicheiro(hamTF.getText(), regras);
		// d.lerFicheiro(spamTF.getText(), regras);

		rulesLV.setItems(regras);
		weightsLV.setItems(weights);
		weightCB.getItems().addAll(pesos);

		if (weights.isEmpty())
			loadContent();
		

	}

	// Carregar o conteudo da primeira ListView
	private void loadContent() {

		Random random = new Random();
		double pesoMin = -5;
		double pesoMax = 5;
		for (int i = 0; i < 10; i++) {
			
			weights.addAll(random.nextDouble() * (pesoMax - pesoMin) + pesoMin);

		}
		weightsLV.setItems(weights);
		System.out.println("weights ===>" + weights + "\n");
	}

	public void editWeights() {

		double peso = (Double) weightCB.getSelectionModel().getSelectedItem();
		int position = weightsLV.getSelectionModel().getSelectedIndex();
		weights.set(position, peso);
		weightsLV.setItems(weights);
	}
}
