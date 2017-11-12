package models.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javafx.collections.ObservableList;;



//Classe para ler os ficheiros 
public class DadosDao {

	public void lerFicheiro(String nomeFicheiro, ObservableList<String> regras) {
		
		try {
			Scanner s = new Scanner(new File(nomeFicheiro));
			while (s.hasNext()) {
				String regra = s.next();				
				regras.add(regra);
			} 
				s.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("Nao encontrou o ficheiro");
		}
	}
	
}

