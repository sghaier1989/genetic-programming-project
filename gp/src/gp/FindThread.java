package gp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jfree.chart.JFreeChart;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

/**
 * This class represents a thread that finds the equation using genetic
 * programming.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class FindThread extends Thread {
	/**
	 * The time formatter.
	 */
	private static final String DATE_FORMAT = "mm:ss";
	/**
	 * logger.
	 */
	private static final Logger GP_LOGGER = Logger
			.getLogger(GeneticProgramming.class);
	/**
	 * Best result label.
	 */
	private javax.swing.JLabel bestResultlabel;
	/**
	 * Crossover rate.
	 */
	private double crossoverRate;
	/**
	 * Equation panel.
	 */
	private EquationGraphPanel equationPanel = null;
	/**
	 * Final equation label.
	 */
	private javax.swing.JLabel finalEqLabel;
	/**
	 * Final generation label.
	 */
	private javax.swing.JLabel finalGenLabel;
	/**
	 * Final time label.
	 */
	private javax.swing.JLabel finalTimeLabel;
	/**
	 * Final fitness label.
	 */
	private javax.swing.JLabel fitnessValueLabel;
	/**
	 * The Jframe.
	 */
	private javax.swing.JFrame frame;
	/**
	 * The functional set.
	 */
	private FunctionalSet functionalSet = new FunctionalSet();
	/**
	 * The Generation.
	 */
	private int generation = 0;
	/**
	 * generation Value label.
	 */
	private javax.swing.JLabel genValueLabel;
	/**
	 * GUI Switch.
	 */
	private boolean gui = false;
	/**
	 * height of tree.
	 */
	private int hieghtOfTree;
	/**
	 * The lowest fitness.
	 */
	private double lowestFitness = 1.79769E+308;
	/**
	 * Max height.
	 */
	private int maxHeight;
	/**
	 * Max Range.
	 */
	private int maxRange;
	/**
	 * Min range.
	 */
	private int minRange;
	/**
	 * Mutation Rate.
	 */
	private double mutationRate;
	/**
	 * Number of trees.
	 */
	private int numberOfTrees;
	/**
	 * Population.
	 */
	private List<Tree> population = null;
	/**
	 * Population fitness panel.
	 */
	private PopulationFitnessPanel popFitnessPanel;
	/**
	 * Result dialog.
	 */
	private javax.swing.JDialog resultDialog;
	/**
	 * Runtime value label.
	 */
	private javax.swing.JLabel runtimeValueLabel;
	/**
	 * Date formatter.
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
	/**
	 * Start time.
	 */
	private long startTime = 0;
	/**
	 * Target expression.
	 */
	private String targetExpersion = null;
	/**
	 * Terminal Set.
	 */
	private TerminalSet terminalSet = new TerminalSet();

	/**
	 * Method for getting a tree comparator.
	 * 
	 * @return a tree comparator
	 */
	private TreeFitnessComparator getTreeComparator() {
		return treeComparator;
	}

	/**
	 * Method for sorting trees.
	 * 
	 * @param newTrees
	 *            - list of trees to sort
	 */
	protected final void sortTrees(final List<Tree> newTrees) {
		Collections.sort(newTrees, getTreeComparator());
	}

	/**
	 * Tree comparator used to sort the trees.
	 */
	private TreeFitnessComparator treeComparator = new TreeFitnessComparator();

	/**
	 * Method for calculating the values of the training data applied against
	 * the target equation.
	 * 
	 * @param targetedExpresion
	 *            - target expression
	 * @param dataset
	 *            - training data
	 * @return returns a list of values.
	 */
	private double[] calculateTargetValues(final String targetedExpresion,
			final int[] dataset) {
		final int datasetSize = dataset.length;
		double[] targetTreeValues = new double[datasetSize];
		final Expression exp = ExpressionTree.parse(targetedExpresion);
		final VarMap varMap = new VarMap(false /* case sensitive */);
		for (int x = 0; x < datasetSize; x++) {
			varMap.setValue("x", dataset[x]);
			targetTreeValues[x] = exp.eval(varMap, null);
		}
		return targetTreeValues;
	}

	/**
	 * Method for culling the population to an acceptable size.
	 * 
	 * @param newTrees
	 *            - tree needing to be culled
	 * @return new list of trees with the bad ones removed
	 * @throws GeneticProgrammingException
	 *             - If some thing goes wrong
	 */
	private List<Tree> cull(final List<Tree> newTrees)
			throws GeneticProgrammingException {
		GP_LOGGER.debug("Max Number of Trees: " + getNumberOfTrees());
		GP_LOGGER.debug("Original pop size: " + newTrees.size());
		for (int numbTrees = getNumberOfTrees(); getNumberOfTrees() < newTrees
				.size(); numbTrees++) {
			GP_LOGGER.debug(" pop size  = " + newTrees.size());
			newTrees.remove(getNumberOfTrees());
		}
		return newTrees;
	}

	/**
	 * Method for populating a tree with nodes.
	 * 
	 * @param newTree
	 *            - Tree that needs nodes generated
	 * @param newParent
	 *            - Parent node to attach sibling (leaf) nodes onto.
	 * @param howDeepToMakeIt
	 *            - The total depth to make the tree.
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	private void generateNodes(final Tree newTree, final Node newParent,
			final int howDeepToMakeIt) throws GeneticProgrammingException {
		if (newParent.getLevel() == howDeepToMakeIt - 1) {
			final Node lOperand = new Node(newParent, getTerminalSet()
					.randomOperand(), Node.LEFT, Node.OPERAND);
			newTree.addNode(lOperand);
			final Node rOperand = new Node(newParent, getTerminalSet()
					.randomOperand(), Node.RIGHT, Node.OPERAND);
			newTree.addNode(rOperand);
		} else {
			final Node lOperator = new Node(newParent, getFunctionalSet()
					.randomOperator(), Node.LEFT, Node.OPERATOR);
			newTree.addNode(lOperator);
			generateNodes(newTree, lOperator, howDeepToMakeIt);
			final Node rOperator = new Node(newParent, getFunctionalSet()
					.randomOperator(), Node.RIGHT, Node.OPERATOR);
			newTree.addNode(rOperator);
			generateNodes(newTree, rOperator, howDeepToMakeIt);
		}

	}

	/**
	 * This method generates a set of values that are generated by the passed in
	 * equation.
	 * 
	 * @param newMin
	 *            - Minimum integer range
	 * @param newMax
	 *            - Maximum integer range
	 * @return - ArrayList of integers in range specified.
	 */
	private int[] genertateTrainingDataSet(final int newMin, final int newMax) {
		int[] values = new int[newMax - newMin];
		int temp = newMin;
		for (int x = 0; x < values.length; x++) {
			values[x] = temp++;
		}
		return values;
	}

	/**
	 * This method gives the thread access to the best Best Result Value Label
	 * in the GUI.
	 * 
	 * @return - bestResultValueLabel
	 */
	private javax.swing.JLabel getBestResultValueLabel() {
		return this.bestResultlabel;
	}

	/**
	 * This method gives the thread access to the crossover rate in the GUI.
	 * 
	 * @return - double - crossover rate
	 */
	private double getCrossoverRate() {
		return this.crossoverRate;
	}

	/**
	 * This method gives the thread access to the equation graph panel in the
	 * GUI.
	 * 
	 * @return - Equation Graph Panel
	 */
	private EquationGraphPanel getEquationGraphPanel() {
		return this.equationPanel;
	}

	/**
	 * This method gives the thread access to the final equation result label in
	 * the GUI.
	 * 
	 * @return - final Equation Result Label
	 */
	private javax.swing.JLabel getFinalEquationResultLabel() {
		return this.finalEqLabel;
	}

	/**
	 * This method gives the thread access to the final generation result label
	 * in the GUI.
	 * 
	 * @return - final generation result label
	 */
	private javax.swing.JLabel getFinalGenerationResultLabel() {
		return this.finalGenLabel;
	}

	/**
	 * This method gives the thread access to the final time result label in the
	 * GUI.
	 * 
	 * @return - final generation time label
	 */
	private javax.swing.JLabel getFinalTimeresultLabel() {
		return this.finalTimeLabel;
	}

	/**
	 * This method gives the thread access to the fitness value label in the
	 * GUI.
	 * 
	 * @return - Fitness value label
	 */
	private javax.swing.JLabel getFitnessValueLabel() {
		return this.fitnessValueLabel;
	}

	/**
	 * This method gives the thread access to the GUI JFrame.
	 * 
	 * @return - GUI JFrame
	 */
	private javax.swing.JFrame getFrame() {
		return this.frame;
	}

	/**
	 * This method gives the thread access to the functional set.
	 * 
	 * @return - FunctionalSet
	 */
	private FunctionalSet getFunctionalSet() {
		return this.functionalSet;
	}

	/**
	 * This method gives the thread access to the Generation Value Label.
	 * 
	 * @return - GenerationValueLabel
	 */
	private javax.swing.JLabel getGenerationValueLabel() {
		return this.genValueLabel;
	}

	/**
	 * This method gives the thread access to the tree height setting.
	 * 
	 * @return - int - tree height
	 */
	private int getHieghtOfTree() {
		return this.hieghtOfTree;
	}

	// private double getLowestFitness() {
	// return lowestFitness;
	// }
	/**
	 * This method gives the thread access to the tree max height setting.
	 * 
	 * @return - tree height
	 */
	private int getMaxHeight() {
		return this.maxHeight;
	}

	/**
	 * This method gives the thread access to the training data's max size.
	 * 
	 * @return - max training size
	 */
	private int getMaxRange() {
		return this.maxRange;
	}

	/**
	 * This method gives the thread access to the training data's minimum size.
	 * 
	 * @return - minimum training size
	 */
	private int getMinRange() {
		return this.minRange;
	}

	/**
	 * This method gives the thread access to the mutation rate in the GUI.
	 * 
	 * @return - double - mutation rate
	 */
	private double getMutationRate() {
		return this.mutationRate;
	}

	/**
	 * This method gives the thread access to the max size of population.
	 * 
	 * @return - number Of Trees
	 */
	private int getNumberOfTrees() {
		return this.numberOfTrees;
	}

	/**
	 * This method gives the thread access to the Population Fitness Panel.
	 * 
	 * @return - PopulationFitnessPanel
	 */
	private PopulationFitnessPanel getPopulationFitnessPanel() {
		return popFitnessPanel;
	}

	/**
	 * This method gives the thread access to the result Dialog.
	 * 
	 * @return JDialog resultDialog
	 */
	private javax.swing.JDialog getResultDialog() {
		return this.resultDialog;
	}

	/**
	 * This method gives the thread access to the Runtime Value Label.
	 * 
	 * @return JLabel Runtime Value Label
	 */
	private javax.swing.JLabel getRuntimeValueLabel() {
		return this.runtimeValueLabel;
	}

	/**
	 * This method gives the thread access to the target expression.
	 * 
	 * @return String - target Expression
	 */
	private String getTargetExpersion() {
		return this.targetExpersion;
	}

	/**
	 * 
	 * @return TerminalSet
	 */
	private TerminalSet getTerminalSet() {
		return this.terminalSet;
	}

	/**
	 * Return true if using the GUI version or false if command-line.
	 * 
	 * @return return true if using the GUI version
	 */
	private boolean isGui() {
		return this.gui;
	}

	/**
	 * Iteration method to keep looking for a solution.
	 * 
	 * @param newTrees
	 *            - ArrayList of trees in population
	 * @param newDataset
	 *            - The training data
	 * @param targetTreeValues
	 *            - The values of the target equation
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	private void keepLooking(final List<Tree> newTrees, final int[] newDataset,
			final double[] targetTreeValues) throws GeneticProgrammingException {
		if (System.currentTimeMillis() - startTime > 900000) {
			if (isGui()) {
				JOptionPane.showMessageDialog(getFrame(),
						"This run has taken longer then 15 minutes!!", "Error",
						JOptionPane.ERROR_MESSAGE);
			}
			throw new GeneticProgrammingException(
					"This run has taken longer then 15 minutes!!");
		}
		GP_LOGGER.debug("Generation: " + generation++);
		if (isGui()) {
			this.getGenerationValueLabel().setText(
					Integer.valueOf(generation).toString());
		}
		GP_LOGGER.debug("Number of Trees: " + newTrees.size());
		Mutation.mutateTrees(newTrees, this.getMutationRate(), newDataset,
				targetTreeValues);
		sortTrees(newTrees);
		if (isGui()) {
			this.getRuntimeValueLabel().setText(
					sdf.format(System.currentTimeMillis() - startTime));
		}
		Crossover.cross(newTrees, this.getCrossoverRate(), this.getMaxHeight(),
				newDataset, targetTreeValues);
		sortTrees(newTrees);
		if (isGui()) {
			this.getRuntimeValueLabel().setText(
					sdf.format(System.currentTimeMillis() - startTime));
		}
		final ArrayList<Tree> newPop = (ArrayList<Tree>) cull(newTrees);
		final int numOfTrees = newPop.size();
		for (int x = 0; x < numOfTrees; x++) {
			final Tree tree = newPop.get(x);
			if (tree.getFitness() < lowestFitness) {
				lowestFitness = tree.getFitness();
				if (isGui()) {
					this.getBestResultValueLabel().setText(
							tree.getEquation().toString());
					this.getFitnessValueLabel().setText(
							new Double(tree.getFitness()).toString());
					if (getEquationGraphPanel().isVisible()) {
						updateEquationChart(tree);
					}
				}
			}
			if (tree.getFitness() == 0) {
				GP_LOGGER.info("found tree");
				GP_LOGGER.info("Generation: " + generation);
				GP_LOGGER.info("Equation: " + tree.getEquation().toString());
				GP_LOGGER.info("Time: "
						+ sdf.format(System.currentTimeMillis() - startTime));
				if (isGui()) {
					getFinalTimeresultLabel().setText(
							sdf.format(System.currentTimeMillis() - startTime));
					updateEquationChart(tree);
					updateFitnessChart(newDataset, targetTreeValues, newPop,
							getPopulationFitnessPanel());

					getBestResultValueLabel().setText(
							tree.getEquation().toString());
					getFitnessValueLabel().setText(
							new Double(tree.getFitness()).toString());
					getFinalEquationResultLabel().setText(
							tree.getEquation().toString());
					getFinalGenerationResultLabel().setText(
							Integer.valueOf(generation).toString());

					getResultDialog().pack();
					getResultDialog().setVisible(true);
				}
				return;
			}

		}
		if (isGui()) {
			this.getRuntimeValueLabel().setText(
					sdf.format(System.currentTimeMillis() - startTime));
		}
		if (isGui() && getPopulationFitnessPanel().isVisible()) {
			updateFitnessChart(newDataset, targetTreeValues, newTrees,
					getPopulationFitnessPanel());
		}
		keepLooking(newTrees, newDataset, targetTreeValues);

	}

	/**
	 * This method creates a random population of trees using the terminal and
	 * functional sets.
	 * 
	 * @param newNumberOfTrees
	 *            - Size of binary tree population.
	 * 
	 * @param newHeight
	 *            - height of binary tree
	 * @param newDataset
	 *            - training data
	 * @param targetTreeValues
	 *            - target values
	 * @return - Returns a ArrayList of binary trees
	 * 
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	private List<Tree> populate(final int newNumberOfTrees,
			final int newHeight, final int[] newDataset,
			final double[] targetTreeValues) throws GeneticProgrammingException {
		this.population = new ArrayList<Tree>();
		for (int y = 0; y < newNumberOfTrees; y++) {
			final Node rootNode = new Node(null, this.getFunctionalSet()
					.randomOperator(), null, Node.OPERATOR);
			final Tree tree = new Tree(rootNode, this.getTerminalSet(), this
					.getFunctionalSet());
			rootNode.setTree(tree);
			generateNodes(tree, rootNode, newHeight);

			final double fitness = Fitness.checkFitness(targetTreeValues,
					Fitness.calculateExpressionValues(tree, newDataset));
			tree.setFitness(fitness);
			this.population.add(tree);
		}

		return population;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public final void run() {
		try {
			startTime = System.currentTimeMillis();
			final int[] dataset = genertateTrainingDataSet(this.getMinRange(),
					this.getMaxRange());
			final double[] targetValues = calculateTargetValues(
					getTargetExpersion().toString(), dataset);
			final List<Tree> populationTree = populate(this.getNumberOfTrees(),
					this.getHieghtOfTree(), dataset, targetValues);
			keepLooking(populationTree, dataset, targetValues);
		} catch (GeneticProgrammingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Set the result value label.
	 * 
	 * @param newBResultLabel
	 *            - best result label
	 */
	public final void setBestResultValueLabel(
			final javax.swing.JLabel newBResultLabel) {
		this.bestResultlabel = newBResultLabel;

	}

	/**
	 * Sets the crossover rate.
	 * 
	 * @param newCrossoverRate
	 *            - crossover rate
	 */
	public final void setCrossoverRate(final double newCrossoverRate) {
		this.crossoverRate = newCrossoverRate;

	}

	/**
	 * Sets the equation graph panel.
	 * 
	 * @param newEquationPanel
	 *            equation panel
	 */
	public final void setEquationGraphPanel(
			final EquationGraphPanel newEquationPanel) {
		this.equationPanel = newEquationPanel;
	}

	/**
	 * Sets the final equation result label.
	 * 
	 * @param newFinalEqLabel
	 *            - equation label
	 */
	public final void setFinalEquationResultLabel(
			final javax.swing.JLabel newFinalEqLabel) {
		finalEqLabel = newFinalEqLabel;
	}

	/**
	 * Sets the final generation result label.
	 * 
	 * @param newFinalGenLabel
	 *            - generation label
	 */
	public final void setFinalGenerationResultLabel(
			final javax.swing.JLabel newFinalGenLabel) {
		this.finalGenLabel = newFinalGenLabel;
	}

	/**
	 * Set the final time result.
	 * 
	 * @param newFinalTimeLabel
	 *            - time result label
	 */
	public final void setFinalTimeresultLabel(
			final javax.swing.JLabel newFinalTimeLabel) {
		this.finalTimeLabel = newFinalTimeLabel;
	}

	/**
	 * Sets the fitness value.
	 * 
	 * @param newFitValueLabel
	 *            - fitness label
	 */
	public final void setFitnessValueLabel(
			final javax.swing.JLabel newFitValueLabel) {
		this.fitnessValueLabel = newFitValueLabel;
	}

	/**
	 * Set the frame.
	 * 
	 * @param newFrame
	 *            - frame
	 */
	public final void setFrame(final javax.swing.JFrame newFrame) {
		this.frame = newFrame;
	}

	/**
	 * Set the functional set.
	 * 
	 * @param newFunctionalSet
	 *            - functional set
	 */
	public final void setFunctionalSet(final FunctionalSet newFunctionalSet) {
		this.functionalSet = newFunctionalSet;
	}

	/**
	 * Set the generation.
	 * 
	 * @param newGeneration
	 *            - Generation
	 */
	public final void setGeneration(final int newGeneration) {
		this.generation = newGeneration;
	}

	/**
	 * Set the generation label.
	 * 
	 * @param newGenValueLabel
	 *            - generation label
	 */
	public final void setGenerationValueLabel(
			final javax.swing.JLabel newGenValueLabel) {
		this.genValueLabel = newGenValueLabel;
	}

	/**
	 * Set the GUI.
	 * 
	 * @param newGui
	 *            - gui
	 */
	public final void setGui(final boolean newGui) {
		this.gui = newGui;
	}

	/**
	 * Set the tree height.
	 * 
	 * @param newHieghtOfTree
	 *            - tree height
	 */
	public final void setHieghtOfTree(final int newHieghtOfTree) {
		this.hieghtOfTree = newHieghtOfTree;
	}

	/**
	 * Set the lowest fitness.
	 * 
	 * @param newLowestFitness
	 *            - lowest fitness
	 */
	public final void setLowestFitness(final double newLowestFitness) {
		this.lowestFitness = newLowestFitness;
	}

	/**
	 * Set the max tree height.
	 * 
	 * @param newMaxHeight
	 *            - max tree height
	 */
	public final void setMaxHeight(final int newMaxHeight) {
		this.maxHeight = newMaxHeight;
	}

	/**
	 * Set the max range.
	 * 
	 * @param newMaxRange
	 *            - max training data range
	 */
	public final void setMaxRange(final int newMaxRange) {
		this.maxRange = newMaxRange;
	}

	/**
	 * Set the minimum range.
	 * 
	 * @param newMinRange
	 *            - minimum training data range
	 */
	public final void setMinRange(final int newMinRange) {
		this.minRange = newMinRange;
	}

	/**
	 * Set the mutation rate.
	 * 
	 * @param newMutationRate
	 *            - mutation rate
	 */
	public final void setMutationRate(final double newMutationRate) {
		this.mutationRate = newMutationRate;
	}

	/**
	 * Set the number of trees.
	 * 
	 * @param newNumberOfTrees
	 *            - number of trees
	 */
	public final void setNumberOfTrees(final int newNumberOfTrees) {
		this.numberOfTrees = newNumberOfTrees;
	}

	/**
	 * Set the tree population.
	 * 
	 * @param newPopulation
	 *            - tree population
	 */
	public final void setPopulation(final List<Tree> newPopulation) {
		this.population = newPopulation;
	}

	/**
	 * Set the population fitness panel.
	 * 
	 * @param popFitPanel
	 *            - panel
	 */
	public final void setPopulationFitnessPanel(
			final PopulationFitnessPanel popFitPanel) {
		this.popFitnessPanel = popFitPanel;
	}

	/**
	 * Set the result dialog.
	 * 
	 * @param newResultDialog
	 *            - dialog
	 */
	public final void setResultDialog(final javax.swing.JDialog newResultDialog) {
		this.resultDialog = newResultDialog;
	}

	/**
	 * Set the runtime value label.
	 * 
	 * @param newRTValueLabel
	 *            - label
	 */
	public final void setRuntimeValueLabel(
			final javax.swing.JLabel newRTValueLabel) {
		this.runtimeValueLabel = newRTValueLabel;
	}

	/**
	 * Set the start time.
	 * 
	 * @param newStartTime
	 *            - start time
	 */
	public final void setStartTime(final long newStartTime) {
		this.startTime = newStartTime;
	}

	/**
	 * Set the target expression.
	 * 
	 * @param tarExpersion
	 *            - target expression
	 */
	public final void setTargetExpersion(final String tarExpersion) {
		this.targetExpersion = tarExpersion;
	}

	/**
	 * Set the terminal set.
	 * 
	 * @param newTerminalSet
	 *            - terminal set
	 */
	public final void setTerminalSet(final TerminalSet newTerminalSet) {
		this.terminalSet = newTerminalSet;
	}

	/**
	 * Set the tree comparator.
	 * 
	 * @param newTreeComparator
	 *            - tree comparator
	 */
	public final void setTreeComparator(
			final TreeFitnessComparator newTreeComparator) {
		    treeComparator = newTreeComparator;
	}

	/**
	 * Set the equation chart.
	 * 
	 * @param newTree
	 *            - population trees
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	private void updateEquationChart(final Tree newTree)
			throws GeneticProgrammingException {
		equationPanel.setTree(newTree);
		equationPanel.setTargetEquation(getTargetExpersion());
		equationPanel.setTrainingData(genertateTrainingDataSet(-1000, 1000));
		final JFreeChart chart = getEquationGraphPanel().createChart(
				equationPanel.createDataset());
		equationPanel.setChart(chart);
		equationPanel.repaint();
		this.getFrame().pack();
	}

	/**
	 * Set the fitness chart.
	 * 
	 * @param newDataset
	 *            - data set
	 * @param tarTreeValues
	 *            - target tree values
	 * @param newTrees
	 *            - population of trees
	 * @param popFitPanel
	 *            - fitness panel
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	private void updateFitnessChart(final int[] newDataset,
			final double[] tarTreeValues, final List<Tree> newTrees,
			final PopulationFitnessPanel popFitPanel)
			throws GeneticProgrammingException {
		popFitPanel.setDataset(newDataset);
		popFitPanel.setTrees(newTrees);
		popFitPanel.setTargetTreeValues(tarTreeValues);
		final JFreeChart chart = popFitPanel.createChart(popFitPanel
				.createDataset());
		popFitPanel.setChart(chart);
		popFitPanel.repaint();
		getFrame().pack();
	}
}
