package pt.iscteiul.antispamfilter.controlers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author João Lola
 * @author Délcio Pedro
 * 
 *
 */

public class AntiSpamFilterController {
	
	/* GUI TEXTFIELD'S DECLARATION	*/
	
	@FXML
	private TextField rulesFileTF;
	
	@FXML
	private TextField spamFileTF;
	
	@FXML
	private TextField hamFileTF;
	
	@FXML
	private TextField weight1TF;
	
	@FXML
	private TextField weight2TF;
	
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
	 * Este método permite guardar as alterações aos nomes e pesos das regras.
	 * 
	 */
	
	private void save() {
		
	}
	
	
	@FXML
	
	private void edit() {
		
	}
	
	
	
	
	
	
	

}
