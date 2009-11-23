package gp;

import java.util.ArrayList;
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
public final class Mutation {
	private static Logger logger = Logger.getLogger(Mutation.class);

	/**
	 * Method for mutating a specific tree.
	 * 
	 */
	private static void mutateTree(Tree newTree) {
		try {
			ArrayList<Node> nodes = newTree.getNodes();
			int size = nodes.size();
			Random randomGenerator = new Random();
			int random = randomGenerator.nextInt(size);
			Node randomNode = nodes.get(random);
			String type = randomNode.getType();
			if (randomNode.getType() == Node.OPERAND) {
				randomNode.setValue(newTree.getTerminalSet().randomOperand());
			} else if (type == Node.OPERATOR) {
				randomNode
						.setValue(newTree.getFunctionalSet().randomOperator());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Method for mutating a list of trees
	 * 
	 * @param newTrees
	 *            - ArrayList of Tree objects that represent the population.
	 * @param newMutationRate
	 *            - Double value that represents the percentage of the
	 *            population to mutate.
	 * 
	 */
	public static void mutateTrees(ArrayList<Tree> newTrees,
			double newMutationRate, int[] trainingData,
			double[] newTargetTreeValues) {
		logger.debug("Performing mutation on trees");
		try {
			Random randomGenerator = new Random();
			int size = newTrees.size();
			int numberToMutate = (int) Math.ceil(newMutationRate * size);
			logger.debug("numberToMutate " + numberToMutate);
			for (int x = 0; x < numberToMutate; x++) {
				int randomInt = randomGenerator.nextInt(numberToMutate);
				Tree tree = newTrees.get(randomInt);
				logger.debug("BEFORE MUTATION " + tree.getEquation()
						+ " Fitness = " + tree.getFitness());
				// make a copy of the tree before mutation
				Tree copy = tree.copyTree();
				logger.debug("ADDING A COPY  " + copy.getEquation()
						+ " Fitness = " + copy.getFitness());
				newTrees.add(copy);
				FindThread.sortTrees(newTrees);

				mutateTree(tree);
				tree.setFitness(Fitness.checkFitness(newTargetTreeValues,
						Fitness.calculateExpressionValues(tree, trainingData)));
				logger.debug("AFTER MUTATION " + tree.getEquation()
						+ " Fitness = " + tree.getFitness());
				logger.debug("AFTER MUTATION COPY  " + copy.getEquation()
						+ " Fitness = " + copy.getFitness());
				newTrees.add(copy);
			}

		} catch (Exception e) {
			e.printStackTrace();
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
	public static void setRandomNodeToValue(Tree newTree, String newValue,
			String newType) {
		try {
			Node node = null;
			Random randomGenerator = new Random();
			if (newType == Node.OPERAND) {
				int randomInt = randomGenerator.nextInt(newTree.getOperands()
						.size());
				node = newTree.getOperandAt(randomInt);
			} else if (newType == Node.OPERATOR) {
				int randomInt = randomGenerator.nextInt(newTree.getOperators()
						.size());
				node = newTree.getOperatorAt(randomInt);
			}
			node.setValue(newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
