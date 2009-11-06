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
			int newMaxTreeHeight) throws Exception {
		try {

			logger.debug("Performing Crossover");
			// get the size of the population
			int size = newTrees.size();
			// calculate the number of crossovers to perform
			int numberOfCrossovers = (int) Math.floor(newPercentage * size);
			for (int x = 0; x <= numberOfCrossovers - 1; x++) {
				Tree tree1 = newTrees.get(x);
				Tree tree2 = newTrees.get(++x);
				newTrees.add(tree1.copyTree());
				newTrees.add(tree2.copyTree());
				Node randomNonRootNode1 = tree1.findRandomNonRootNode();
				Node randomNonRootNode2 = tree2.findRandomNonRootNode();
				if (newMaxTreeHeight > 0) {
					if (tree1.getHeight() > newMaxTreeHeight
							|| tree2.getHeight() > newMaxTreeHeight)
						// this will prevent the tree from growing by crossing
						// nodes at the same level
						while (randomNonRootNode1.getLevel() == randomNonRootNode2
								.getLevel()) {
							randomNonRootNode1 = newTrees.get(x)
									.findRandomNonRootNode();
						}
				}
				if (!randomNonRootNode1.isRoot()
						&& !randomNonRootNode2.isRoot()) {
					Node parent1 = randomNonRootNode1.getParent();
					Node parent2 = randomNonRootNode2.getParent();
					if (parent1 != null && parent2 != null) {
						if (randomNonRootNode1 == tree1.getRoot()
								|| randomNonRootNode2 == tree2.getRoot()) {
							randomNonRootNode1.printNodeInfo();
							randomNonRootNode2.printNodeInfo();
							throw new Exception(
									"Something is wrong with the parent nodes");
						}
						randomNonRootNode2.setPointer(randomNonRootNode1
								.getPointer());
						randomNonRootNode1.setPointer(randomNonRootNode2
								.getPointer());
						parent2.setChild(randomNonRootNode1);
						parent1.setChild(randomNonRootNode2);
						randomNonRootNode2.setParent(parent1);
						randomNonRootNode1.setParent(parent2);

					} else {
						// This should not happen. If it does there is a bug
						// with
						// the NODE::findRandomNonRootNode method
						randomNonRootNode1.printNodeInfo();
						randomNonRootNode2.printNodeInfo();
						throw new Exception(
								"Something is wrong with the parent nodes");
					}
				} else {
					// This should not happen. If it does there is a bug with
					// the NODE::findRandomNonRootNode method
					throw new Exception("Can not crossover at the root");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("An error occured while crossing two trees ");
		}
	}

}
