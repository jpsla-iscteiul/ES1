package pt.iscteiul.antispamfilter;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {

	  public AntiSpamFilterProblem() {
	    // Número de regras para gerar pesos e falsos postivos e negativos
	    this(10);
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
	  
	  
	  /*Metodo a alterar
	   * Objecto solution - onde é guardada a configuração, ou seja, o vetor de pesos atributo do objecto
	   * 
	   */
	  public void evaluate(DoubleSolution solution){
	    //double aux, xi, xj;
	    //double[] fx = new double[getNumberOfObjectives()];
	    double[] x = new double[getNumberOfVariables()];
	    /*Extracao do vetor de pesos do objecto solution*/
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i) ;
	    }
	    
	    //Dica criar uma função que calcula o numero de falos potivos e negativos e invoca-la aqui
	    
	    // Código tem de ser substituido
//	    fx[0] = 0.0;
//	    for (int var = 0; var < solution.getNumberOfVariables() - 1; var++) {
//		  fx[0] += Math.abs(x[0]); // Example for testing
//	    }
//	    
//	    fx[1] = 0.0;
//	    for (int var = 0; var < solution.getNumberOfVariables(); var++) {
//	    	fx[1] += Math.abs(x[1]); // Example for testing
//	    }
	    int FP=0, FN=0;
	    
	    
		
	    solution.setObjective(0, FP/*fx[0]*/);
	    solution.setObjective(1, FN/*fx[1]*/);
	  }
	}
