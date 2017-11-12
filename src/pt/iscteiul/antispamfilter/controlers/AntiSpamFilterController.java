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
    CheckBox rulesCB;
    @FXML 
    CheckBox spamCB;
    @FXML 
    CheckBox hamCB;
	
    
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

	public void browse() {

		FileChooser fc = new FileChooser();
		File selectedFile = fc.showOpenDialog(null);
		String path = selectedFile.getPath();
		
		if(rulesCB.isSelected()) {
			rulesTF.setText(path);
			rulesCB.setSelected(false);
		}else if(hamCB.isSelected()){
		hamTF.setText(path);
		hamCB.setSelected(false);
		}else if(spamCB.isSelected()) {
			spamTF.setText(path);
			spamCB.setSelected(false);		
		}
	}


	public void load() {
		
		ObservableList<String> regras = FXCollections.observableArrayList();
		ObservableList<String> hams = FXCollections.observableArrayList();
		ObservableList<String> spams = FXCollections.observableArrayList();
		ObservableList<Integer> weights = FXCollections.observableArrayList();
	
			
		DadosDao d = new DadosDao();
		d.lerFicheiro(rulesTF.getText(), regras);
		d.lerFicheiro(hamTF.getText(), hams);
		d.lerFicheiro(spamTF.getText(), spams);
		rulesLV.setItems(regras);
			
		for (int i = 0; i < regras.size(); i++) {
			weights.add(0);
		}

		weightsLV.setItems(weights);
		weightsLV.setEditable(true);
	}
	
	
	
}
