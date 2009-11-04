package gp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYDataset;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

public class FindThread extends Thread {
	public static final String DATE_FORMAT = "mm:ss";
	static Logger logger = Logger.getLogger(GeneticProgramming.class);
	private javax.swing.JLabel bestResultValueLabel;
	private Tree bestTree = null;
	private double crossoverRate;
	private Tree equation = null;
	private EquationGraphPanel equationGraphPanel = null;
	private javax.swing.JLabel finalEquationResultLabel;
	private javax.swing.JLabel finalGenerationResultLabel;
	private javax.swing.JLabel finalTimeresultLabel;
	private javax.swing.JLabel fitnessValueLabel;
	private javax.swing.JFrame frame;
	private FunctionalSet functionalSet = new FunctionalSet();
	private int generation = 0;
	private javax.swing.JLabel generationValueLabel;
	private int hieghtOfTree;
	private double lowestFitness = 99999999;
	private int maxHeight;
	private int maxRange;
	private int minRange;
	private double mutationRate;
	private int numberOfTrees;
	private ArrayList<Tree> population = null;
	private PopulationFitnessPanel populationFitnessPanel;
	private javax.swing.JDialog resultDialog;
	private javax.swing.JLabel runtimeValueLabel;
	private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

	private long startTime = 0;

	private long stopTime = 0;

	private String targetExpersion = new String();

	private TerminalSet terminalSet = new TerminalSet();

	private TreeFitnessComparator treeComparator = new TreeFitnessComparator();

	/**
	 * Method for populating a tree with nodes.
	 * 
	 * @param tree
	 *            - Tree that needs nodes generated
	 * @param parent
	 *            - Parent node to attach sibling (leaf) nodes onto.
	 * @param level
	 *            - The level the nodes are being applied to.
	 * @param height
	 *            - The height of the tree when finished
	 */
	private void generateNodes(Tree newTree, Node newParent, int howDeepToMakeIt) {
		try {
			if (newParent.getLevel() == howDeepToMakeIt - 1) {

				Node lOperand = new Node(newParent, getTerminalSet()
						.randomOperand(), Node.LEFT, Node.OPERAND);
				newTree.addNode(lOperand);
				Node rOperand = new Node(newParent, getTerminalSet()
						.randomOperand(), Node.RIGHT, Node.OPERAND);
				newTree.addNode(rOperand);
			} else {
				Node lOperator = new Node(newParent, getFunctionalSet()
						.randomOperator(), Node.LEFT, Node.OPERATOR);
				newTree.addNode(lOperator);

				generateNodes(newTree, lOperator, howDeepToMakeIt);

				Node rOperator = new Node(newParent, getFunctionalSet()
						.randomOperator(), Node.RIGHT, Node.OPERATOR);
				newTree.addNode(rOperator);

				generateNodes(newTree, rOperator, howDeepToMakeIt);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method generates a set of values that are generated by the passed in
	 * equation.
	 * 
	 * @param min
	 *            - Minimum integer range
	 * @param max
	 *            - Maximum integer range
	 * @return - ArrayList of integers in range specified.
	 */
	private int[] genertateTrainingDataSet(int newMin, int newMax) {
		int[] values = new int[newMax - newMin];

		for (int x = 0; x < values.length; x++) {
			values[x] = newMin++;
		}
		return values;
	}

	public javax.swing.JLabel getBestResultValueLabel() {
		return bestResultValueLabel;
	}
	public Tree getBestTree() {
		return bestTree;
	}
	public double getCrossoverRate() {
		return crossoverRate;
	}
	public Tree getEquation() {
		return equation;
	}

	public EquationGraphPanel getEquationGraphPanel() {
		return equationGraphPanel;
	}

	public javax.swing.JLabel getFinalEquationResultLabel() {
		return finalEquationResultLabel;
	}

	public javax.swing.JLabel getFinalGenerationResultLabel() {
		return finalGenerationResultLabel;
	}

	public javax.swing.JLabel getFinalTimeresultLabel() {
		return finalTimeresultLabel;
	}

	public javax.swing.JLabel getFitnessValueLabel() {
		return fitnessValueLabel;
	}

	public javax.swing.JFrame getFrame() {
		return frame;
	}

	public FunctionalSet getFunctionalSet() {
		return functionalSet;
	}

	public int getGeneration() {
		return generation;
	}

	public javax.swing.JLabel getGenerationValueLabel() {
		return generationValueLabel;
	}

	public int getHieghtOfTree() {
		return hieghtOfTree;
	}

	public double getLowestFitness() {
		return lowestFitness;
	}

	public int getMaxHeight() {
		return maxHeight;
	}

	public int getMaxRange() {
		return maxRange;
	}

	public int getMinRange() {
		return minRange;
	}

	public double getMutationRate() {
		return mutationRate;
	}

	public int getNumberOfTrees() {
		return numberOfTrees;
	}

	public ArrayList<Tree> getPopulation() {
		return population;
	}

	public PopulationFitnessPanel getPopulationFitnessPanel() {
		return populationFitnessPanel;
	}

	public javax.swing.JDialog getResultDialog() {
		return resultDialog;
	}

	public javax.swing.JLabel getRuntimeValueLabel() {
		return runtimeValueLabel;
	}

	public long getStartTime() {
		return startTime;
	}

	public long getStopTime() {
		return stopTime;
	}

	public String getTargetExpersion() {
		return targetExpersion;
	}

	public TerminalSet getTerminalSet() {
		return terminalSet;
	}

	public TreeFitnessComparator getTreeComparator() {
		return treeComparator;
	}

	private void keepLooking(ArrayList<Tree> newTrees, double newCrossoverRate,
			double newMutationRate, int[] newDataset,
			double[] newTargetTreeValues, int newMaxHeight) {
		try {
			logger.debug("Generation: " + generation++);
			this.getGenerationValueLabel().setText(generation + "");
			sortTree(newTrees);
			//logger.debug("+++++++++++++++++++TOP TEN++++++++++++++++++++++");
		//	int count = 0;
			//while (count < 10) {
			//	Tree p = newTrees.get(count++);
				//logger.debug(count + ") Equation: " + p.getEquation()
				//		+ " Fittness: " + p.getFitness());
			//}
			//logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++");

			// getUniqueValues(newTrees);

			logger
					.debug("+++++++++++++++++++Trimming Tree++++++++++++++++++++++");
			while (newTrees.size() > this.getNumberOfTrees()) {
				Tree p = newTrees.get(newTrees.size() - 1);
				logger.debug("Removing Equation: " + p.getEquation()
						+ " Fittness: " + p.getFitness());
				newTrees.remove(newTrees.size() - 1);
			}
			logger.debug("+++++++++++++++++++++++++++++++++++++++++++++++");

			logger.debug("Number of Trees: " + newTrees.size());
			Crossover.cross(newTrees, newCrossoverRate, newMaxHeight);
			sortTree(newTrees);
			Mutation.mutateTrees(newTrees, newMutationRate);
			DefaultPieDataset defaultPieDataset = null;
			JFreeChart populationFitnessPanelChart = null;
			for (int x = 0; x < newTrees.size(); x++) {
				Tree tr = newTrees.get(x);
				double fn = Fitness.checkFitness(newTargetTreeValues, tr,
						newDataset);
				if (getPopulationFitnessPanel().isVisible()) {
					getPopulationFitnessPanel().setTrees(newTrees);
					defaultPieDataset = getPopulationFitnessPanel()
							.createDataset();
					populationFitnessPanelChart = getPopulationFitnessPanel()
							.createChart(defaultPieDataset);
					getPopulationFitnessPanel().setChart(
							populationFitnessPanelChart);
					getPopulationFitnessPanel().repaint();
					this.getFrame().pack();
				}

				if (fn < lowestFitness) {
					lowestFitness = fn;
					this.getBestResultValueLabel().setText(
							tr.getEquation().toString());
					this.getFitnessValueLabel().setText(fn + "");
					//getEquationGraphPanel().setTrainingData(newDataset);
					getEquationGraphPanel().setTree(tr);
					getEquationGraphPanel().setTrainingData(genertateTrainingDataSet(-10000,10000));
					XYDataset dataset = getEquationGraphPanel().createDataset();
					JFreeChart chart = getEquationGraphPanel().createChart(
							dataset);
					getEquationGraphPanel().setChart(chart);
					getEquationGraphPanel().repaint();
					this.getFrame().pack();

				}
				if (fn == 0) {
					
					logger.info("found tree");
					logger.info("Generation: " + generation);
					logger.info("Equation: " + tr.getEquation());
					logger.info("Time: "
							+ sdf
									.format(System.currentTimeMillis()
											- startTime));
					this.getBestResultValueLabel().setText(
							tr.getEquation().toString());
					this.getFitnessValueLabel().setText(fn + "");
					getFinalEquationResultLabel().setText(
							tr.getEquation().toString());
					getFinalGenerationResultLabel().setText(generation + "");
					getFinalTimeresultLabel().setText(
							sdf.format(System.currentTimeMillis() - startTime));
					getResultDialog().pack();
					getResultDialog().setVisible(true);
					return;
					}
					
				}
			

			this.getRuntimeValueLabel().setText(
					sdf.format(System.currentTimeMillis() - startTime));
			keepLooking(newTrees, newCrossoverRate, newMutationRate,
					newDataset, newTargetTreeValues, newMaxHeight);

		} catch (Exception e) {
			e.printStackTrace();

		}
	}



	/*
	 * This method creates a random population of trees using the terminal and
	 * functional sets
	 * 
	 * @param numberOfTrees - Size of binary tree population.
	 * 
	 * @param height - height of binary tree
	 * 
	 * @return - Returns a ArrayList of binary trees
	 */
	private ArrayList<Tree> populate(int newNumberOfTrees, int newHeight) {
		this.population = new ArrayList<Tree>();
		try {
			for (int tree = 0; tree < newNumberOfTrees; tree++) {
				Node rootNode = new Node(null, this.getFunctionalSet()
						.randomOperator(), null, Node.OPERATOR);
				Tree bt = new Tree(rootNode, this.getTerminalSet(), this
						.getFunctionalSet());
				generateNodes(bt, rootNode, newHeight);
				this.population.add(bt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return population;
	}

	@Override
	public void run() {
		try {
			startTime = System.currentTimeMillis();
			int[] dataset = genertateTrainingDataSet(this.getMinRange(), this
					.getMaxRange());
			int datasetSize = dataset.length;
			double[] targetTreeValues = new double[datasetSize];
			for (int x = 0; x < datasetSize; x++) {
				Expression exp = ExpressionTree.parse(getTargetExpersion()
						.toString());
				VarMap vm = new VarMap(false /* case sensitive */);
				vm.setValue("x", dataset[x]);
				targetTreeValues[x] = exp.eval(vm, null);
			}
			ArrayList<Tree> populationTree = populate(this.getNumberOfTrees(),
					this.getHieghtOfTree());

			keepLooking(populationTree, this.getCrossoverRate(), this
					.getMutationRate(), dataset, targetTreeValues, this
					.getMaxHeight());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setBestResultValueLabel(
			javax.swing.JLabel newBestResultValueLabel) {

		this.bestResultValueLabel = newBestResultValueLabel;

	}

	public void setBestTree(Tree newBestTree) {

		this.bestTree = newBestTree;

	}

	public void setCrossoverRate(double newCrossoverRate) {

		this.crossoverRate = newCrossoverRate;

	}

	public void setEquation(Tree newEquation) {

		this.equation = newEquation;

	}

	public void setEquationGraphPanel(EquationGraphPanel equationGraphPanel) {
		this.equationGraphPanel = equationGraphPanel;
	}

	public void setFinalEquationResultLabel(
			javax.swing.JLabel finalEquationResultLabel) {
		this.finalEquationResultLabel = finalEquationResultLabel;
	}

	public void setFinalGenerationResultLabel(
			javax.swing.JLabel finalGenerationResultLabel) {
		this.finalGenerationResultLabel = finalGenerationResultLabel;
	}

	public void setFinalTimeresultLabel(javax.swing.JLabel finalTimeresultLabel) {
		this.finalTimeresultLabel = finalTimeresultLabel;
	}

	public void setFitnessValueLabel(javax.swing.JLabel newFitnessValueLabel) {
		this.fitnessValueLabel = newFitnessValueLabel;
	}

	public void setFrame(javax.swing.JFrame frame) {
		this.frame = frame;
	}

	public void setFunctionalSet(FunctionalSet newFunctionalSet) {
		this.functionalSet = newFunctionalSet;
	}

	public void setGeneration(int newGeneration) {
		this.generation = newGeneration;
	}

	public void setGenerationValueLabel(
			javax.swing.JLabel newGenerationValueLabel) {
		this.generationValueLabel = newGenerationValueLabel;
	}

	public void setHieghtOfTree(int newHieghtOfTree) {
		this.hieghtOfTree = newHieghtOfTree;
	}

	public void setLowestFitness(double newLowestFitness) {
		this.lowestFitness = newLowestFitness;
	}

	public void setMaxHeight(int newMaxHeight) {
		this.maxHeight = newMaxHeight;
	}

	public void setMaxRange(int newMaxRange) {
		this.maxRange = newMaxRange;
	}

	public void setMinRange(int newMinRange) {
		this.minRange = newMinRange;
	}

	public void setMutationRate(double newMutationRate) {
		this.mutationRate = newMutationRate;
	}

	public void setNumberOfTrees(int newNumberOfTrees) {
		this.numberOfTrees = newNumberOfTrees;
	}

	public void setPopulation(ArrayList<Tree> newPopulation) {
		this.population = newPopulation;
	}

	public void setPopulationFitnessPanel(
			PopulationFitnessPanel populationFitnessPanel) {
		this.populationFitnessPanel = populationFitnessPanel;
	}

	public void setResultDialog(javax.swing.JDialog resultDialog) {
		this.resultDialog = resultDialog;
	}

	public void setRuntimeValueLabel(javax.swing.JLabel newRuntimeValueLabel) {
		this.runtimeValueLabel = newRuntimeValueLabel;
	}

	public void setStartTime(long newStartTime) {
		this.startTime = newStartTime;
	}

	public void setStopTime(long newStopTime) {
		this.stopTime = newStopTime;
	}

	public void setTargetExpersion(String newTargetExpersion) {
		this.targetExpersion = newTargetExpersion;
	}

	public void setTerminalSet(TerminalSet newTerminalSet) {
		this.terminalSet = newTerminalSet;
	}

	public void setTreeComparator(TreeFitnessComparator newTreeComparator) {
		this.treeComparator = newTreeComparator;
	}

	private void sortTree(ArrayList<Tree> newTrees) {
		Collections.sort(newTrees, treeComparator);
	}
	/*
	 * public void getUniqueValues(ArrayList<Tree> newTrees) { try {
	 * HashMap<String, Tree> map = new HashMap<String, Tree>(); for (int t = 0;
	 * t < newTrees.size() - 1; t++) { String id =
	 * newTrees.get(t).getEquation().toString(); map.put(id, newTrees.get(t)); }
	 * newTrees.clear(); Iterator<Tree> values = map.values().iterator(); while
	 * (values.hasNext()) { newTrees.add(values.next()); } } catch (Exception e)
	 * { e.printStackTrace(); }
	 * 
	 * }
	 */

}
