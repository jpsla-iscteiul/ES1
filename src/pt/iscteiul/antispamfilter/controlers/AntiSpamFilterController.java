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

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;

/**
 *
 * @author Jo�o Lola
 * @author D�lcio Pedro
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
	private ChoiceBox<Integer> weightCB;
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

	// Parametrizar - N�o Esquecer//
	@FXML
	private ListView<String> rulesLV;
	@FXML
	private ListView<Integer> weightsLV;
	@FXML
	private ListView<String> optRulesLV;
	@FXML
	private ListView<Integer> optWeightsLV;
	private ObservableList<String> regras = FXCollections.observableArrayList();
	private ObservableList<Integer> weights = FXCollections.observableArrayList();
	private Integer[] pesos = { -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5 };
	@FXML
	void initialize() {

	}

	@FXML

	/**
	 * 
	 * Este m�todo permite guardar as altera��es aos nomes e pesos das regras.
	 * 
	 */
	// gravar os pesos no ruleCF

	public void edit() {

	}
		
	// O metodo evaluate (com os ficheiros spam.log e ham.log)
	// Metodo para guardar as configura��es do ficheiro
	/**
	 * M�todo Save
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
	 * M�todo Browse permite procurar os ficheiros  
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
	
	
	public void generateOptimizedConfiguration(){
		
		AntiSpamFilterAutomaticConfiguration anti = new AntiSpamFilterAutomaticConfiguration();
		optFnLBL.setText(String.valueOf(20));
		optFpLBL.setText(String.valueOf(100));
	}

	/**
	 * Metodo loadHamSpamFile que chama metodos (readsHamFile e readSpamFile).
	 */
	public void loadHamSpamFile(){
		
		int FPValue;
		int FNValue;
		DadosDao dadosDao = new DadosDao();
		FPValue = dadosDao.readsHamFile(spamTF.getText(), regras, weights);
		FNValue = dadosDao.readSpamFile(hamTF.getText(), regras, weights);
		
		fnLBL.setText(String.valueOf(FNValue));
		fpLBL.setText(String.valueOf(FPValue));
	}
	
	// Metodo para ler os ficheiros e carregar o ficheiro rules file para a list
	// view rulesLV
	/**
	 * M�todo Browse permite procurar os ficheiros  
	 * 
	 * 
	 */
	public void loadFiles() {
		DadosDao d = new DadosDao();
		d.lerFicheiro(rulesTF.getText(), regras, weights);

		rulesLV.setItems(regras);
		weightsLV.setItems(weights);
		weightCB.getItems().addAll(pesos);
		optRulesLV.setItems(regras);
		
		if (weights.isEmpty())
			loadContent();
			loadHamSpamFile();
	}

	// Carregar o conteudo da primeira ListView
	private void loadContent() {

		int pesoMin = -5;
		int pesoMax = 5;
		Random random = new Random();
		for (int i = 0; i < regras.size(); i++) {
			weights.add(random.nextInt((pesoMax - pesoMin) + 1) + pesoMin);
		}
		weightsLV.setItems(weights);
	}

	public void editWeights() {

		int peso = (Integer) weightCB.getSelectionModel().getSelectedItem();
		int position = weightsLV.getSelectionModel().getSelectedIndex();
		weights.set(position, peso);
		weightsLV.setItems(weights);
	}
}
