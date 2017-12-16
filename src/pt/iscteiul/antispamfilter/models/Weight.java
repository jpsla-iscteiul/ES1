package pt.iscteiul.antispamfilter.models;

import java.util.Random;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Weight {
	
	private ObservableList<String> regras = FXCollections.observableArrayList();
	private ObservableList<Double> weights = FXCollections.observableArrayList();
	

	public void randomGenerator() {
		
		Random random = new Random();
		double minWeight = -5.0;
		double maxWeight = 5.0;
		
		for(int i = 0; i < regras.size(); i++) {
			
			weights.add(random.nextDouble() * (maxWeight - minWeight) + minWeight);
		}
		
	}

}
