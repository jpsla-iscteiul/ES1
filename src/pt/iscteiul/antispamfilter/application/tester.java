package pt.iscteiul.antispamfilter.application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iscteiul.antispamfilter.models.AntiSpamMethods;
import pt.iscteiul.antispamfilter.models.TipoFicheiro;
import pt.iscteiul.antispamfilter.models.dao.DadosDao;

public class tester {

	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		 ObservableList<String> regras = FXCollections.observableArrayList();
		 ObservableList<Double> pesosRegras = FXCollections.observableArrayList();
		 ObservableList<String> regraSpamEHam = FXCollections.observableArrayList();
		 
		DadosDao d = new DadosDao();
		 d.lerFicheiro("C:\\Users\\Sony Vaio\\Desktop\\file.txt", regras, pesosRegras, TipoFicheiro.Rules);
		 
		 DadosDao dS = new DadosDao();
		 dS.lerFicheiro("C:\\Users\\Sony Vaio\\Desktop\\projectspam.log", regraSpamEHam, 
				 pesosRegras, TipoFicheiro.Spam);
		 	 AntiSpamMethods.calcularFpEFn(regras, regraSpamEHam, pesosRegras, TipoFicheiro.Spam, null, null);
	
		 	 
		 	ObservableList<String> regras1 = FXCollections.observableArrayList();
			 ObservableList<Double> pesosRegras1 = FXCollections.observableArrayList();
			 ObservableList<String> regraSpamEHam1 = FXCollections.observableArrayList();
			 
			DadosDao d1 = new DadosDao();
			 d1.lerFicheiro("C:\\Users\\Sony Vaio\\Desktop\\file.txt", regras1, pesosRegras1, TipoFicheiro.Rules);
			 
			 DadosDao dS1 = new DadosDao();
			 dS1.lerFicheiro("C:\\Users\\Sony Vaio\\Desktop\\projectham.log", regraSpamEHam1, 
					 pesosRegras, TipoFicheiro.Ham);
			 	 AntiSpamMethods.calcularFpEFn(regras1, regraSpamEHam1, pesosRegras1, TipoFicheiro.Ham, null, null);
		 	 
		 
	}

}
