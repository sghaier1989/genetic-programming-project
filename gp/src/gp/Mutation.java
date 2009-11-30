package gp;

import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * A class file that can change the value of a random node. Will only replace a
 * node with an equivalent type (For example, replace a terminal set value with
 * a terminal set value. Replace functional set value with a functional set
 * value )
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public abstract class Mutation {
	/**
	 * logger.
	 */
	private static final Logger GP_LOGGER = Logger.getLogger(Mutation.class);

	/**
	 * Method for mutating a specific tree.
	 * 
	 * @param newTree
	 *            - the tree to mutate
	 */
	private static void mutateTree(final Tree newTree) {
		final List<Node> nodes = newTree.getNodes();
		final int size = nodes.size();
		final Random randomGenerator = new Random();
		final int random = randomGenerator.nextInt(size);
		final Node randomNode = nodes.get(random);
		final String type = randomNode.getType();
		if (randomNode.getType() == Node.OPERAND) {
			randomNode.setValue(newTree.getTerminalSet().randomOperand());
		} else if (type == Node.OPERATOR) {
			randomNode.setValue(newTree.getFunctionalSet().randomOperator());
		}
	}

	/**
	 * Method for mutating a list of trees.
	 * 
	 * @param newTrees
	 *            - ArrayList of Tree objects that represent the population.
	 * @param newMutationRate
	 *            - Double value that represents the percentage of the
	 *            population to mutate.
	 * @param trainingData
	 *            - training data.
	 * @param targetTreeValues
	 *            - target tree values.
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 * 
	 */
	public static void mutateTrees(final List<Tree> newTrees,
			final double newMutationRate, final int[] trainingData,
			final double[] targetTreeValues) throws GeneticProgrammingException {
		GP_LOGGER.debug("Performing mutation on trees");

		final Random randomGenerator = new Random();
		final int size = newTrees.size();
		final int numberToMutate = (int) Math.ceil(newMutationRate * size);
		GP_LOGGER.debug("numberToMutate " + numberToMutate);
		for (int x = 0; x < numberToMutate; x++) {
			final int randomInt = randomGenerator.nextInt(numberToMutate);
			final Tree tree = newTrees.get(randomInt);
			GP_LOGGER.debug("BEFORE MUTATION " + tree.getEquation()
					+ " Before Fitness = " + tree.getFitness());
			// make a copy of the tree before mutation
			final Tree copy = tree.copyTree();
			GP_LOGGER.debug("ADDING A COPY  " + copy.getEquation()
					+ " Copy Fitness = " + copy.getFitness());
			newTrees.add(copy);
			mutateTree(tree);
			tree.setFitness(Fitness.checkFitness(targetTreeValues, Fitness
					.calculateExpressionValues(tree, trainingData)));
			GP_LOGGER.debug("AFTER MUTATION " + tree.getEquation()
					+ " After Fitness = " + tree.getFitness());
			GP_LOGGER.debug("AFTER MUTATION COPY  " + copy.getEquation()
					+ " After copy Fitness = " + copy.getFitness());
			newTrees.add(copy);
		}
	}

	/**
	 * Helper method for fixing trees that do not have a variable in them. For
	 * example this method can be used to inject an x into an equation.
	 * 
	 * @param newTree
	 *            - Specific tree to mutate.
	 * @param newValue
	 *            - value to set a random node to.
	 * @param newType
	 *            - type to set a random node to.
	 * 
	 */
	public static void setRandomNodeToValue(final Tree newTree,
			final String newValue, final String newType) {
		Node node = null;
		final Random randomGenerator = new Random();
		if (newType.equals(Node.OPERAND)) {
			final int randomInt = randomGenerator.nextInt(newTree.getOperands()
					.size());
			node = newTree.getOperandAt(randomInt);
		} else if (newType.equals(Node.OPERATOR)) {
			final int randomInt = randomGenerator.nextInt(newTree
					.getOperators().size());
			node = newTree.getOperatorAt(randomInt);
		}
		if (node != null) {
			node.setValue(newValue);
		}
	}

}
