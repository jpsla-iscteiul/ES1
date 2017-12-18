package pt.iscteiul.antispamfilter;

import java.util.ArrayList;
import java.util.List;

import org.uma.jmetal.problem.impl.AbstractDoubleProblem;
import org.uma.jmetal.solution.DoubleSolution;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import pt.iscteiul.antispamfilter.models.AntiSpamMethods;
import pt.iscteiul.antispamfilter.models.TipoFicheiro;
import pt.iscteiul.antispamfilter.models.dao.DadosDao;

public class AntiSpamFilterProblem extends AbstractDoubleProblem {
	
	public ObservableList<String> regras = FXCollections.observableArrayList();
	public ObservableList<Double> pesosRegras = FXCollections.observableArrayList();
	public String spamTF;
	public String hamTF;

	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public AntiSpamFilterProblem(ObservableList<String> regras,
			ObservableList<Double> pesosRegras,String spamTF,String hamTF) {
	    // Número de regras para gerar pesos e falsos postivos e negativos
	    this(335);
	    this.regras = regras;
	    this.pesosRegras = pesosRegras;
	    this.spamTF = spamTF;
	    this.hamTF = hamTF;
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
		  
	    double[] fx = new double[getNumberOfObjectives()];
	    double[] x = new double[getNumberOfVariables()];
	    ObservableList<String> regraSpam = FXCollections.observableArrayList();
	    ObservableList<String> regraHam = FXCollections.observableArrayList();   
	    
	    /*Extracao do vetor de pesos do objecto solution*/
	    for (int i = 0; i < solution.getNumberOfVariables(); i++) {
	      x[i] = solution.getVariableValue(i) ;
	    }
	    
	    DadosDao dadosSpam = new DadosDao();
	    dadosSpam.lerFicheiro(spamTF, regraSpam, pesosRegras, TipoFicheiro.Spam);
	    AntiSpamMethods.calcularFpEFnAutomantico(this.regras, regraSpam, x, TipoFicheiro.Spam, fx);
	    
	    
	    DadosDao dadosHam = new DadosDao();
	    dadosHam.lerFicheiro(hamTF, regraHam, pesosRegras, TipoFicheiro.Ham);
	    AntiSpamMethods.calcularFpEFnAutomantico(this.regras, regraHam, x, TipoFicheiro.Ham,fx);

	    solution.setObjective(0, fx[0]);   //FP
	    solution.setObjective(1, fx[1]);   //FN
	  }
	}
