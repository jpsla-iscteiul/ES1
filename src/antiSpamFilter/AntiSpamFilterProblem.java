package antiSpamFilter;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import javafx.collections.ObservableList;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {
	public ObservableList<String> listSpam;
	public ObservableList<String> listHam;
	public ObservableList<String> regras;

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AntiSpamFilterProblem(ObservableList<String> listHam, ObservableList<String> listSpam,ObservableList<String> regras) {
	    // 10 variables (anti-spam filter rules) by default 
	    this(335);
	    this.listHam = listHam;
		this.listSpam = listSpam;
		this.regras = regras;
		System.out.println("listHam size ==> " + this.listHam.size() + " listSpam size ==> " + this.listSpam.size() + "\n");
	  }

	  public AntiSpamFilterProblem(Integer numberOfVariables) {
	    setNumberOfVariables(numberOfVariables);
	    setNumberOfObjectives(2);
	    setName("AntiSpamFilterProblem");
	    

	    List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables()) ;
	    List<Double> upperLimit = new ArrayList<>(getNumberOfVariables()) ;

	    for (int i = 0; i < getNumberOfVariables(); i++) {
	      lowerLimit.add(-5.0);
	      upperLimit.add(5.0);
	    }

	    setLowerLimit(lowerLimit);
	    setUpperLimit(upperLimit);
	  }

	  public void evaluate(DoubleSolution solution){
//	    double aux, xi, xj;
	    double[] fx = new double[getNumberOfObjectives()];
	    double[] x = new double[getNumberOfVariables()];
//	    String[] emailHam = null;
//	    String[] emailSpam = null;
//	    String line = "";
//	    double FN = 0, FP = 0; 
//
//	    
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i);
	    }
//	    
//	    fx[0] =0.0;
//	    for (int l = 0; l < listHam.size(); l++) {
//			line += listHam.get(l);
//			emailHam = line.split("\t");
//			for (int j = 0; j < emailHam.length; j++) {
//				for (int m = 0; m < regras.size(); m++) {
//					if(emailHam[j].equals(regras.get(m))){
//						FN += x[m];
//						if(emailHam[emailHam.length-1] == emailHam[j]){
//							if(FN < 5){
//								fx[0]++;
//								FN = 0;
//								break;
//							}else{
//								FN = 0;
//								break;
//							}
//						}
//					}
//				}
//			
//			}
//
//		}
//	    
//	    fx[1] =0.0;
//	    line = "";
//	    for (int k = 0; k < listSpam.size(); k++) {
//	    	line += listSpam.get(k);
//			emailSpam = line.split("\t");
//			for (int j = 0; j < emailSpam.length; j++) {
//				for (int l = 0; l < regras.size(); l++) {
//					if(emailSpam[j].equals(regras.get(l))){
//						FP += x[l];
//						if(emailSpam[emailSpam.length-1] == emailSpam[j]){
//							if(FP >= 5){
//								fx[1]++;
//								FP = 0;
//								break;
//							}else{
//								FP = 0;
//								break;
//							}
//						}
//					}
//				}
//				
//			}
//	    }

	    fx[0] = 0.0;
	    for (int var = 0; var < solution.getNumberOfVariables() - 1; var++) {
	    	fx[0] += Math.abs(x[0]); // Example for testing
	    }
	    
	    fx[1] = 0.0;
	    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
	    	fx[1] += Math.abs(x[1]); // Example for testing
	    }


	    solution.setObjective(0, fx[0]); //FN  fx[0]);
	    solution.setObjective(1, fx[1]); //FP  fx[1]);
	  }
	}
