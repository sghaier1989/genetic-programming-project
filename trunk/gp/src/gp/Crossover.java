package gp;

import java.util.ArrayList;

import org.apache.log4j.Logger;

/**
 * This class represents crossover functionality. Crossover is a means of
 * combining genetic material of two parents (trees) by swapping a part of one
 * parent (tree) with another parent (tree).
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public final class Crossover {
	static Logger logger = Logger.getLogger(Crossover.class);

	/**
	 * Description: This method is for performing a crossover on a percentage of
	 * the tree population.
	 * 
	 * @param newTrees
	 *            - ArrayList of Tree objects that represent the population.
	 * @param newPercentage
	 *            - Double value that represents the percentage of the
	 *            population to crossover.
	 * @param newMaxTreeHeight
	 *            - This is an optional parameter that will enforce the max tree
	 *            height if set to greater then zero. The crossover process can
	 *            cause a tree to grow.
	 * @throws Exception
	 */
	public static void cross(ArrayList<Tree> newTrees, double newPercentage,
			int newMaxTreeHeight, int[] trainingData,
			double[] newTargetTreeValues) throws Exception {
		try {
			// calculate the number of crossovers to perform
			int numberOfCrossovers = (int) Math.floor(newPercentage
					* newTrees.size());
			logger.debug("Performing Crossover");
			for (int x = 0; x < numberOfCrossovers - 1; x++) {
				Tree tree1 = newTrees.get(x);
				Tree tree2 = newTrees.get(++x);
				if (tree1.toString() != tree2.toString()) {
					double fit1 = Fitness.checkFitness(newTargetTreeValues,
							Fitness.calculateExpressionValues(tree1,
									trainingData));
					tree1.setFitness(fit1);
					double fit2 = Fitness.checkFitness(newTargetTreeValues,
							Fitness.calculateExpressionValues(tree1,
									trainingData));
					tree2.setFitness(fit2);
					newTrees.add(tree1.copyTree());
					newTrees.add(tree2.copyTree());
					Node node1 = tree1.findRandomNonRootOperatorNode(0);
					Node node2 = tree2.findRandomNonRootOperatorNode(0);
					while (node2.getLevel() != node1.getLevel()) {
						node1 = tree1.findRandomNonRootOperatorNode(0);
						node2 = tree2.findRandomNonRootOperatorNode(0);
					}
					logger.debug(" t1h " + tree1.getHeight() + " t2h "
							+ tree2.getHeight());
					logger.debug(" Crossing " + tree1 + " WITH " + tree2);

					if (!node1.isRoot() && !node2.isRoot()
							&& node1.getType() == Node.OPERATOR
							&& node2.getType() == Node.OPERATOR) {
						Node parent1 = node1.getParent();
						Node parent2 = node2.getParent();
						if (parent1 != null && parent2 != null
								&& parent1 != node2 && node1 != parent2) {
							if (node1 == tree1.getRoot()
									|| node2 == tree2.getRoot()) {
								node1.printNodeInfo();
								node2.printNodeInfo();
								throw new Exception(
										"Something is wrong with the parent nodes");
							}
							node2.setParent(parent1);
							node1.setParent(parent2);
							parent1.setChild(node2);
							parent2.setChild(node1);

							// we must set the pointer before the child so
							// that the
							// setLeft and setRight are set correctly
							node2.setLevel(node1.getLevel());
							node2.setPointer(node1.getPointer());
							node2.setTree(tree1);
							tree1.setFitness(Fitness.checkFitness(
									newTargetTreeValues, Fitness
											.calculateExpressionValues(tree1,
													trainingData)));

							node1.setLevel(node2.getLevel());
							node1.setTree(tree2);
							node1.setPointer(node2.getPointer());
							tree2.setFitness(Fitness.checkFitness(
									newTargetTreeValues, Fitness
											.calculateExpressionValues(tree1,
													trainingData)));

						}
					} else {
						// This should not happen. If it does there is a bug
						// with
						// the NODE::findRandomNonRootNode method
						throw new Exception("Can not crossover at the root");
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("An error occured while crossing two trees ");
		}
	}
}
