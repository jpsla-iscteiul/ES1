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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;

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
	public Label fpLBL;
	@FXML
	public Label fnLBL;
	@FXML
	public Label optFpLBL;
	@FXML
	public Label optFnLBL;

	/* GUI LISTVIEW'S DECLARATION */

	// Parametrizar - Não Esquecer//
	@FXML
	private ListView<String> rulesLV;
	@FXML
	private ListView<Integer> weightsLV;
	@FXML
	private ListView<String> optRulesLV;
	@FXML
	private ListView<String> optWeightsLV;
	private ObservableList<String> listWights = FXCollections.observableArrayList();
	public ObservableList<String> listHam = FXCollections.observableArrayList();
	public ObservableList<String> listSpam = FXCollections.observableArrayList();
	private ObservableList<String> regras = FXCollections.observableArrayList();
	private ObservableList<Integer> weights = FXCollections.observableArrayList();
	private Integer[] pesos = { -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5 };
	public int FN,FP;
	private float FNValue, FPValue;
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
	 * Metodo que exibe as regras automatico no listView e só muda Path quando lé o fichiros de extensão .rf e .rs
	 */
	public void listaOptimizado(){
		
		int linha = 0;
		float menor = (float) 9999999.99;
		float numero = 0;
		List<String[]> lista = new ArrayList<>();
		List<String[]> listaRegra = new ArrayList<>();
		String line = null;
		String[] role = null;		
		try {
			FileReader fileReader = new FileReader("C:/Users/amane/git/ES1-2017-METIA1-44/experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rf");
			BufferedReader readFile = new BufferedReader(fileReader);
			line = readFile.readLine();
			while (line != null){
				role = line.split(" ");
				lista.add(role);
				line = readFile.readLine();
			}
			linha = 0;
			for (int i = 0; i <= lista.size()-1; i++) {
				for (int j = 0; j < lista.size(); j++) {
					if(j == 1){
						numero = Float.parseFloat(lista.get(i)[j]); // Converte o valor de um String para um nomero float
						if(numero < menor){
							menor = numero;
							linha++;
							FNValue = Float.parseFloat(lista.get(i)[j-1]);
							FPValue = Float.parseFloat(lista.get(i)[j]);
						}
					}
				}			
			}
			readFile.close();
		} catch (Exception e) {
			System.out.println("Ficheiro nao encontrado!\n");
		}
		
		try {
			line = "";
			String roles = "";
			int i = 0;
			FileReader fileReader = new FileReader("C:/Users/amane/git/ES1-2017-METIA1-44/experimentBaseDirectory/referenceFronts/AntiSpamFilterProblem.NSGAII.rs");
			BufferedReader readFile = new BufferedReader(fileReader);
			line = readFile.readLine();
			while (line != null){
				role = line.split(" ");
				listaRegra.add(role);
				i++;
				if(linha == i){
					roles = line.replace(" ", "\n\n");
					listWights.add(roles);
					break;
				}
				line = readFile.readLine();
			}		
			readFile.close();;
		} catch (Exception e) {
			System.out.println("Ficheiro nao encontrado!\n");
		}
	}
	

	/**
	 * Metodo qaue aciona o botão generate Optimized Configuration no interface.
	 * @throws IOException
	 */
	public void generateOptimizedConfiguration() throws IOException{
		
		String[] args = null; 
		AntiSpamFilterAutomaticConfiguration.main(args,listHam,listSpam,regras);		
		optWeightsLV.setItems(listWights);
		listaOptimizado();
		optFnLBL.setText(String.valueOf(FNValue));
		optFpLBL.setText(String.valueOf(FPValue));
	}

	/**
	 * Metodo loadHamSpamFile que chama metodos readsHamSpamFile.
	 */
	public void loadHamSpamFile(){
		
		DadosDao dadosDao = new DadosDao();
		dadosDao.readsHamSpamFile(spamTF.getText(),hamTF.getText(), regras, weights);
		FN = dadosDao.FN;
		FP = dadosDao.FP;
		listHam = dadosDao.listHam;
		listSpam = dadosDao.listSpam;
		fnLBL.setText(String.valueOf(FN));
		fpLBL.setText(String.valueOf(FP));
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

		rulesLV.setItems(regras);
		weightsLV.setItems(weights);
		weightCB.getItems().addAll(pesos);
		optRulesLV.setItems(regras);
		
		if (weights.isEmpty())
			loadContent();
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
