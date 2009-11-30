package test;

import gp.Crossover;
import gp.FunctionalSet;
import gp.GeneticProgrammingException;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * This is a test for testing the crossover class.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class CrossoverTest extends TestCase {

	/**
	 * Test of the cross function.
	 * 
	 * @throws GeneticProgrammingException
	 *             - if something goes wrong
	 */
	@Test
	public final void testCross() throws GeneticProgrammingException {
		final int[] training = TestHelper.genertateTrainingDataSet(-100, 100);
		final double[] targetValues = TestHelper.calculateTargetValues(
				"x*2-1/2", training);
		final Node root = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree = new Tree(root, new TerminalSet(),
				new FunctionalSet());
		final Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
		newTree.addNode(minusOperator);
		final Node multiOperator = new Node(root, "*", Node.RIGHT,
				Node.OPERATOR);
		newTree.addNode(multiOperator);
		newTree.addNode(new Node(minusOperator, "1", Node.LEFT, Node.OPERAND));
		newTree.addNode(new Node(minusOperator, "3", Node.RIGHT, Node.OPERAND));
		newTree.addNode(new Node(multiOperator, "x", Node.LEFT, Node.OPERAND));
		newTree.addNode(new Node(multiOperator, "5", Node.RIGHT, Node.OPERAND));
		assertEquals("Tree did not get generated corectly", "(1-3)/(x*5)",
				newTree.getEquation().toString());
		final Node root2 = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree2 = new Tree(root2, new TerminalSet(),
				new FunctionalSet());
		final Node minusOperator2 = new Node(root2, "-", Node.LEFT,
				Node.OPERATOR);
		newTree2.addNode(minusOperator2);
		final Node multiOperator2 = new Node(root2, "*", Node.RIGHT,
				Node.OPERATOR);
		newTree2.addNode(multiOperator2);
		newTree2
				.addNode(new Node(minusOperator2, "x", Node.LEFT, Node.OPERAND));
		newTree2
				.addNode(new Node(minusOperator2, "7", Node.RIGHT, Node.OPERAND));
		newTree2
				.addNode(new Node(multiOperator2, "8", Node.LEFT, Node.OPERAND));
		newTree2
				.addNode(new Node(multiOperator2, "9", Node.RIGHT, Node.OPERAND));
		assertEquals("Tree did not get generated corectly", "(x-7)/(8*9)",
				newTree2.getEquation().toString());

		final Node root3 = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree3 = new Tree(root3, new TerminalSet(),
				new FunctionalSet());
		final Node minusOperator3 = new Node(root3, "-", Node.LEFT,
				Node.OPERATOR);
		newTree3.addNode(minusOperator3);
		final Node multiOperator3 = new Node(root3, "*", Node.RIGHT,
				Node.OPERATOR);
		newTree3.addNode(multiOperator3);
		final Node multiOperator4 = new Node(multiOperator3, "*", Node.LEFT,
				Node.OPERATOR);
		newTree3.addNode(multiOperator4);
		final Node multiOperator5 = new Node(multiOperator3, "*", Node.RIGHT,
				Node.OPERATOR);
		newTree3.addNode(multiOperator5);
		newTree3
				.addNode(new Node(minusOperator3, "x", Node.LEFT, Node.OPERAND));
		newTree3
				.addNode(new Node(minusOperator3, "7", Node.RIGHT, Node.OPERAND));
		newTree3
				.addNode(new Node(multiOperator4, "1", Node.LEFT, Node.OPERAND));
		newTree3
				.addNode(new Node(multiOperator4, "2", Node.RIGHT, Node.OPERAND));
		newTree3
				.addNode(new Node(multiOperator5, "3", Node.LEFT, Node.OPERAND));
		newTree3
				.addNode(new Node(multiOperator5, "4", Node.RIGHT, Node.OPERAND));
		assertEquals("Tree did not get generated corectly",
				"(x-7)/(1*2)*(3*4)", newTree3.getEquation().toString());
		final ArrayList<Tree> newTrees = new ArrayList<Tree>();
		newTrees.add(newTree3);
		newTrees.add(newTree2);
		newTrees.add(newTree);
		assertEquals("Tree should have a size of 3 before the cross", 3,
				newTrees.size());
		Crossover.cross(newTrees, 1, 100, training, targetValues);
		final Iterator<Tree> iterator = newTrees.iterator();
		while (iterator.hasNext()) {
			// All five trees should be different
			final String firstTree = iterator.next().toString();
			assertNotSame("Cross should change the tree but did not 1",
					firstTree, iterator.next().toString());
			assertNotSame("Cross should change the tree but did not 2",
					firstTree, iterator.next().toString());
			assertNotSame("Cross should change the tree but did not 3",
					firstTree, iterator.next().toString());
			assertNotSame("Cross should change the tree but did not 4",
					firstTree, iterator.next().toString());
		}
		// should be 5 trees after the crossover
		assertEquals("Tree should have a size of 5 but was not", 5, newTrees
				.size());
	}
}
