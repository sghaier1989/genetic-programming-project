package gp;

import java.util.Comparator;

class TreeFitnessComparator implements Comparator<Tree> {

	@Override
	public int compare(Tree newTree1, Tree newTree2) {
		return ((int) newTree1.getFitness() - (int) newTree2.getFitness());
	}
}
// end class StringComparator

