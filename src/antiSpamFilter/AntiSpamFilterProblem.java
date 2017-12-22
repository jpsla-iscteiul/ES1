package antiSpamFilter;

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
	public ObservableList<Double> pesosRegrasAuto = FXCollections.observableArrayList();
	public String ficheiroSpam;
	public String ficheiroHam;
	private static final long serialVersionUID = 1L;

	public AntiSpamFilterProblem(ObservableList<String> regras, ObservableList<Double> pesosRegras, String ficheiroSpam,
			String ficheiroHam) {
		this(regras.size());
		this.regras = regras;
		this.pesosRegrasAuto = pesosRegras;
		this.ficheiroSpam = ficheiroSpam;
		this.ficheiroHam = ficheiroHam;
	}

	public AntiSpamFilterProblem(Integer numberOfVariables) {
		setNumberOfVariables(numberOfVariables);
		setNumberOfObjectives(2);
		setName("AntiSpamFilterProblem");

		List<Double> lowerLimit = new ArrayList<>(getNumberOfVariables());
		List<Double> upperLimit = new ArrayList<>(getNumberOfVariables());

		for (int i = 0; i < getNumberOfVariables(); i++) {
			lowerLimit.add(-5.0);
			upperLimit.add(5.0);
		}

		setLowerLimit(lowerLimit);
		setUpperLimit(upperLimit);
	}

	public void evaluate(DoubleSolution solution) {

		ObservableList<String> regraSpamAuto = FXCollections.observableArrayList();
		ObservableList<String> regrasHamAuto = FXCollections.observableArrayList();
		double[] fx = new double[getNumberOfObjectives()];
		double[] x = new double[getNumberOfVariables()];

		for (int i = 0; i < solution.getNumberOfVariables(); i++) {
			x[i] = solution.getVariableValue(i);
		}
		DadosDao dadosAutoSpam = new DadosDao();
		dadosAutoSpam.lerFicheiro(ficheiroSpam, regraSpamAuto, pesosRegrasAuto, TipoFicheiro.Spam);
		AntiSpamMethods.calcularFpEFn(this.regras, regraSpamAuto, null, TipoFicheiro.Spam, fx, x);

		DadosDao dadosAutoHam = new DadosDao();
		dadosAutoHam.lerFicheiro(ficheiroHam, regrasHamAuto, pesosRegrasAuto, TipoFicheiro.Ham);
		AntiSpamMethods.calcularFpEFn(this.regras, regrasHamAuto, null, TipoFicheiro.Ham, fx, x);
		solution.setObjective(0, fx[0]);
		solution.setObjective(1, fx[1]);
	}
}
