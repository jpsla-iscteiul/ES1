package pt.iscteiul.antispamfilter.controlers;

import javafx.fxml.FXML;
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
	
	/* GUI TEXT FIELDS DECLARATION	*/
	
	@FXML
	private TextField nameTF;
	
	@FXML
	private TextField weight1TF;
	
	@FXML
	private TextField weight2TF;
	
	@FXML
	private TextField nameOptTF;
	
	@FXML
	private TextField weight1OptTF;
	
	@FXML
	private TextField weight2OptTF;
	
	@FXML
	private TextField rulesFileTF;
	
	@FXML
	private TextField spamFileTF;
	
	@FXML
	private TextField hamFileTF;
	
	
	/* GUI LIST VIEWS DECLARATION*/
	
	@FXML
	private ListView rulesLV;
	
	@FXML
	private ListView optRulesLV;
	
	
	@FXML
	
	private void initialize() {
		
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
	
	private void delete() {
		
		
	}
	
	@FXML
	
	private void edit() {
		
	}
	
	
	
	
	
	
	

}
