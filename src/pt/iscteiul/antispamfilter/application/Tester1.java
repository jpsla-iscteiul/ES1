package pt.iscteiul.antispamfilter.application;

import java.io.IOException;

import antiSpamFilter.AntiSpamFilterAutomaticConfiguration;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iscteiul.antispamfilter.models.TipoFicheiro;
import pt.iscteiul.antispamfilter.models.dao.DadosDao;

public class Tester1 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ObservableList<String> regras = FXCollections.observableArrayList();
		 ObservableList<Double> pesosRegras = FXCollections.observableArrayList();
		 ObservableList<String> regraSpamEHam = FXCollections.observableArrayList();
		 
		DadosDao d = new DadosDao();
		 d.lerFicheiro("C:\\Users\\Sony Vaio\\Desktop\\file.txt", regras, pesosRegras, TipoFicheiro.Rules);
		 
		 String[] args1 = null;
		 
		 AntiSpamFilterAutomaticConfiguration.automaticConfiguration(args1, regras, 
				 pesosRegras, "C:\\Users\\Sony Vaio\\Desktop\\projectspam.log",
				 "C:\\Users\\Sony Vaio\\Desktop\\projectspam.log");
	}

}
