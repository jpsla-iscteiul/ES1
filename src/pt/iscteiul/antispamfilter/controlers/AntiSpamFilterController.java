package pt.iscteiul.antispamfilter.controlers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import models.dao.*;
import java.io.File;
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
	private ListView<Integer> weightsLV;
	@FXML
	private ListView optRulesLV;
	@FXML
	private ListView optWeightsLV;
	ObservableList<String> regras = FXCollections.observableArrayList();
	ObservableList<Integer> weights = FXCollections.observableArrayList();

	@FXML
	void initialize() {

	}

	@FXML

	/**
	 * 
	 * Este método permite guardar as alterações aos nomes e pesos das regras.
	 * 
	 */

	public void save() {

	}

	public void edit() {

	}

	public void saveConfiguration() {

	}

	// Metodo para procurar o ficheiro e guardar o seu path
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

	// Metodo para ler os ficheiros e carregar o ficheiro rules file para a list
	// view rulesLV
	public void load() {

		DadosDao d = new DadosDao();
		d.lerFicheiro(rulesTF.getText(), regras);

		rulesLV.setItems(regras);
		for (int i = 0; i < regras.size(); i++) {
			Random random = new Random();
			weights.add(random.nextInt(6));
		}
		weightsLV.setItems(weights);
	}

	public void editWeights() {

		int weight = Integer.parseInt(weightsTF.getText());
		int position = weightsLV.getSelectionModel().getSelectedIndex();
		System.out.println("peso" + weight + "position" + position);
		weights.set(position, weight);
		weightsLV.setItems(weights);
	}

}
