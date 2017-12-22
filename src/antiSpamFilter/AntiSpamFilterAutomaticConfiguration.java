package antiSpamFilter;

import org.uma.jmetal.algorithm.Algorithm;
import org.uma.jmetal.algorithm.multiobjective.nsgaii.NSGAIIBuilder;
import org.uma.jmetal.operator.impl.crossover.SBXCrossover;
import org.uma.jmetal.operator.impl.mutation.PolynomialMutation;
import org.uma.jmetal.qualityindicator.impl.hypervolume.PISAHypervolume;
import org.uma.jmetal.solution.DoubleSolution;
import org.uma.jmetal.util.experiment.Experiment;
import org.uma.jmetal.util.experiment.ExperimentBuilder;
import org.uma.jmetal.util.experiment.component.*;
import org.uma.jmetal.util.experiment.util.ExperimentAlgorithm;
import org.uma.jmetal.util.experiment.util.ExperimentProblem;

import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AntiSpamFilterAutomaticConfiguration {

	private static final int INDEPENDENT_RUNS = 5;

	public static void automaticConfiguration(String[] args, ObservableList<String> regras,
			ObservableList<Double> pesosRegras, String spamTF, String hamTF) throws IOException {

		String experimentBaseDirectory = "experimentBaseDirectory";

		List<ExperimentProblem<DoubleSolution>> problemList = new ArrayList<>();
		problemList.add(new ExperimentProblem<>(new AntiSpamFilterProblem(regras, pesosRegras, spamTF, hamTF)));

		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithmList = configureAlgorithmList(
				problemList);

		Experiment<DoubleSolution, List<DoubleSolution>> experiment = new ExperimentBuilder<DoubleSolution, List<DoubleSolution>>(
				"AntiSpamStudy").setAlgorithmList(algorithmList).setProblemList(problemList)
						.setExperimentBaseDirectory(experimentBaseDirectory).setOutputParetoFrontFileName("FUN")
						.setOutputParetoSetFileName("VAR")
						.setReferenceFrontDirectory(experimentBaseDirectory + "/referenceFronts")
						.setIndicatorList(Arrays.asList(new PISAHypervolume<DoubleSolution>()))
						.setIndependentRuns(INDEPENDENT_RUNS).setNumberOfCores(8).build();

		new ExecuteAlgorithms<>(experiment).run();
		new GenerateReferenceParetoSetAndFrontFromDoubleSolutions(experiment).run();
		new ComputeQualityIndicators<>(experiment).run();
		new GenerateLatexTablesWithStatistics(experiment).run();
		new GenerateBoxplotsWithR<>(experiment).setRows(1).setColumns(1).run();

		// pdfLatexGenerator();
		// graphGenerator();

	}

	static List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> configureAlgorithmList(
			List<ExperimentProblem<DoubleSolution>> problemList) {
		List<ExperimentAlgorithm<DoubleSolution, List<DoubleSolution>>> algorithms = new ArrayList<>();

		for (int i = 0; i < problemList.size(); i++) {
			Algorithm<List<DoubleSolution>> algorithm = new NSGAIIBuilder<>(problemList.get(i).getProblem(),
					new SBXCrossover(1.0, 5),
					new PolynomialMutation(1.0 / problemList.get(i).getProblem().getNumberOfVariables(), 10.0))
							.setMaxEvaluations(300).setPopulationSize(100).build();
			algorithms.add(new ExperimentAlgorithm<>(algorithm, "NSGAII", problemList.get(i).getTag()));
		}

		return algorithms;
	}
	
	public static void graphGenerator() throws IOException {

		String[] paramsLatex = new String[2];

		paramsLatex[0] = "C:\\Program Files\\MiKTeX 2.9\\miktex\\bin\\x64\\miktex-pdflatex.exe";

		paramsLatex[1] = "experimentBaseDirectory/AntiSpamStudy/latex/AntiSpamStudy.tex";

		String[] envpLatex = new String[1];

		envpLatex[0] = "Path=C:\\Program Files\\MiKTeX 2.9\\miktex\\bin\\x64";

		Process p = Runtime.getRuntime().exec(paramsLatex, envpLatex,
				new File("experimentBaseDirectory/AntiSpamStudy/latex"));

		p.exitValue();
	}

	public static void pdfLatexGenerator() throws IOException {

		String[] paramsR = new String[2];

		paramsR[0] = "C:\\Program Files\\R\\R-3.4.3\\bin\\x64\\Rscript.exe";

		paramsR[1] = "experimentBaseDirectory/AntiSpamStudy/R/HV.Boxplot.R";

		String[] envpR = new String[1];

		envpR[0] = "Path=C:\\Program Files\\R\\R-3.4.3\\bin\\x64";

		Process pR = Runtime.getRuntime().exec(paramsR, envpR, new File("experimentBaseDirectory/AntiSpamStudy/R"));

		pR.exitValue();

	}


}