package gp;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class represents crossover functionality. Crossover is a means of
 * combining genetic material of two parents (trees) by swapping a part of one
 * parent (tree) with another parent (tree).
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public abstract class Crossover {
	/**
	 * logger.
	 */
	private static final Logger GP_LOGGER = Logger.getLogger(Crossover.class);


	/**
	 * Description: This method is for performing a crossover on a percentage of
	 * the tree population.
	 * 
	 * @param newTrees
	 *            - List of Tree objects that represent the population.
	 * @param newPercentage
	 *            - Double value that represents the percentage of the
	 *            population to crossover.
	 * @param newMaxTreeHeight
	 *            - This is an optional parameter that will enforce the max tree
	 *            height if set to greater then zero. The crossover process can
	 *            cause a tree to grow.
	 * @param trainingData
	 *            - The training data.
	 * @param targetTreeValues
	 *            - values of the target tree.
	 * @throws GeneticProgrammingException
	 *             - if something goes wrong
	 */
	public static void cross(final List<Tree> newTrees,
			final double newPercentage, final int newMaxTreeHeight,
			final int[] trainingData, final double[] targetTreeValues)
			throws GeneticProgrammingException {
		// calculate the number of crossovers to perform
		final int crossovers = (int) Math
				.floor(newPercentage * newTrees.size());
		GP_LOGGER.debug("Performing Crossover");
		for (int x = 0; x < crossovers - 1; x++) {
			final Tree tree1 = newTrees.get(x);
			final Tree tree2 = newTrees.get(++x);
			if (!tree1.equals(tree2)) {
				final double fit1 = Fitness.checkFitness(targetTreeValues,
						Fitness.calculateExpressionValues(tree1, trainingData));
				tree1.setFitness(fit1);
				final double fit2 = Fitness.checkFitness(targetTreeValues,
						Fitness.calculateExpressionValues(tree1, trainingData));
				tree2.setFitness(fit2);
				newTrees.add(tree1.copyTree());
				newTrees.add(tree2.copyTree());
				Node node1 = tree1.findRandomNonRootOperatorNode();
				Node node2 = tree2.findRandomNonRootOperatorNode();
				while (node2.getLevel() != node1.getLevel()) {
					node1 = tree1.findRandomNonRootOperatorNode();
					node2 = tree2.findRandomNonRootOperatorNode();
				}
				GP_LOGGER.debug(" Crossing " + tree1 + " WITH " + tree2);
				if (!node1.isRoot() && !node2.isRoot()
						&& node1.getType() == Node.OPERATOR
						&& node2.getType() == Node.OPERATOR) {
					final Node parent1 = node1.getParent();
					final Node parent2 = node2.getParent();
					if (parent1 != null && parent2 != null
							&& !parent1.equals(node2) && !parent2.equals(node1)) {
						node2.setParent(parent1);
						node1.setParent(parent2);
						parent1.setChild(node2);
						parent2.setChild(node1);
						node2.setLevel(node1.getLevel());
						node2.setPointer(node1.getPointer());
						node2.setTree(tree1);
						tree1.setFitness(Fitness.checkFitness(targetTreeValues,
								Fitness.calculateExpressionValues(tree1,
										trainingData)));

						node1.setLevel(node2.getLevel());
						node1.setTree(tree2);
						node1.setPointer(node2.getPointer());
						tree2.setFitness(Fitness.checkFitness(targetTreeValues,
								Fitness.calculateExpressionValues(tree1,
										trainingData)));

					}
				} else {
					// This should not happen.
					// If it does there is a bug with
					// the NODE::findRandomNonRootNode method
					throw new GeneticProgrammingException(
							"Can not crossover at the root");
				}
			}
		}
	}
}
