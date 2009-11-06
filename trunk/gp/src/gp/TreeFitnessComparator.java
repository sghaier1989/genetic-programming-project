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

	/**
	 * Description - Method for comparing two trees
	 * 
	 * @param newTree1
	 * @param newTree2
	 * 
	 * @return int - value of difference in trees
	 * @Override
	 */
	public int compare(Tree newTree1, Tree newTree2) {
		return ((int) newTree1.getFitness() - (int) newTree2.getFitness());
	}
}
// end class StringComparator

