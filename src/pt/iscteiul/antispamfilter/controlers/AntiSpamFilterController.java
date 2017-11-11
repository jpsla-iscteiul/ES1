package pt.iscteiul.antispamfilter.controlers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author João Lola
 * 
 * 
 *
 */

public class AntiSpamFilterController {
	
	/* GUI TEXTFIELD'S DECLARATION	*/
	
	@FXML
	private TextField rulesTF;
	
	@FXML
	private TextField spamTF;
	
	@FXML
	private TextField hamTF;
	
	
	/* GUI LABEL'S DECLARATION	*/
	
	@FXML
	private Label fpLBL;
	
	@FXML
	private Label fnLBL;
	
	@FXML
	private Label optFpLBL;
	
	@FXML
	private Label optFnLBL;
	
	
	/* GUI LISTVIEW'S DECLARATION*/
	
	//Parametrizar - Não Esquecer//
	
	@FXML
	private ListView rulesLV;
	
	@FXML
	private ListView weightsLV;
	
	@FXML
	private ListView optRulesLV;
	
	@FXML
	private ListView optWeightsLV;
	
	
	@FXML
	
	private void initialize() {
		
	}
	
	
	@FXML
	
	private void load() {
		
	}
	
	 
	@FXML
	
	/**
	 * 
	 * Este método permita guardar as alterações aos nomes e pesos das regras.
	 * 
	 */
	
	private void save() {
		
	}
	
	
	@FXML
	
	private void edit() {
		
	}
	
	@FXML
	
	private void saveConfiguration() {
		
	}
	
	
	
	
	
	
	

}
