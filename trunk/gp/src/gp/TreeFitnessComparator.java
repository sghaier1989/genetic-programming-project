package gp;

import java.util.Comparator;

/**
 * This is a helper class used to sort the tree population.
 * 
 * @author Trevor Greene
 * @version 1.0
 * 
 */
class TreeFitnessComparator implements Comparator<Tree> {
	double[] targetTreeValues = null;
	int[] dataset = null;

	/**
	 * Description - Method for comparing two trees
	 * 
	 * @param newTree1
	 * @param newTree2
	 * 
	 * @return int - value of difference in trees
	 */
	public int compare(Tree newTree1, Tree newTree2) {
		try {
			if (Double.isInfinite(newTree1.getFitness())) {
				return 2147483647;
			} else if (Double.isInfinite(newTree2.getFitness())) {
				return -2147483646;
			} else {
				return ((int) newTree1.getFitness() - (int) newTree2
						.getFitness());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

}
// end class StringComparator

