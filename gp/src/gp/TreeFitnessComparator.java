package gp;

import java.util.Comparator;

/**
 * This is a helper class used to sort the tree population.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class TreeFitnessComparator implements Comparator<Tree> {

	/**
	 * Method for comparing two trees.
	 * 
	 * @param newTree1
	 *            tree one
	 * @param newTree2
	 *            tree two
	 * @return value of difference in trees
	 */
	public final int compare(final Tree newTree1, final Tree newTree2) {
		int value = 2147483647;
		if (!Double.isInfinite(newTree1.getFitness())
				|| !Double.isInfinite(newTree2.getFitness())) {
			value = (int) newTree1.getFitness() - (int) newTree2.getFitness();
		}
		return value;
	}
}
// end class StringComparator

