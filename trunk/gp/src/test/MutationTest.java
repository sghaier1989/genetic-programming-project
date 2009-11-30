package test;

import gp.FunctionalSet;
import gp.Mutation;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;

import java.util.ArrayList;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;

/**
 * This is a test for testing the mutation class.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class MutationTest extends TestCase {
	/**
	 * Test method for testing a mutation.
	 */
	@Test
	public final void testMutateTrees() {
		try {
			final TerminalSet terminalSet = TestHelper.getTerminalSet();
			final FunctionalSet functionalSet = TestHelper.getFunctionalSet();
			final Node root = new Node(null, "/", null, Node.OPERATOR);
			final Tree newTree = new Tree(root, terminalSet, functionalSet);
			final Node minusOperator = new Node(root, "-", Node.LEFT,
					Node.OPERATOR);
			newTree.addNode(minusOperator);
			final Node multiOperator = new Node(root, "*", Node.RIGHT,
					Node.OPERATOR);
			newTree.addNode(multiOperator);
			newTree.addNode(new Node(minusOperator, "1", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(minusOperator, "3", Node.RIGHT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "4", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "5", Node.RIGHT,
					Node.OPERAND));
			final String originalTree = newTree.getEquation().toString();
			assertEquals("Expected tree (1-3)/(4*5)", "(1-3)/(4*5)",
					originalTree);
			final ArrayList<Tree> newTrees = new ArrayList<Tree>();
			newTrees.add(newTree);
			assertEquals("Expected a tree size of one", 1, newTrees.size());
			final int[] training = TestHelper.genertateTrainingDataSet(-100,
					100);
			Mutation.mutateTrees(newTrees, 1, training, TestHelper
					.calculateTargetValues("x*2-1/2", training));
			final Iterator<Tree> iterator = newTrees.iterator();
			while (iterator.hasNext()) {
				assertEquals("Expected a tree size of three", 3, newTrees
						.size());
				assertNotSame("Expected differnt trees after the mutation",
						originalTree, iterator.next().toString());
			}

		} catch (Exception e) {
			fail("Threw exception ");
		}
	}

	/**
	 * Method for testing the set random node to value.
	 */
	@Test
	public final void testSetRandomNodeToValue() {
		try {
			final TerminalSet terminalSet = TestHelper.getTerminalSet();
			final FunctionalSet functionalSet = TestHelper.getFunctionalSet();
			final Node root = new Node(null, "/", null, Node.OPERATOR);
			final Tree newTree = new Tree(root, terminalSet, functionalSet);
			final Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			final Node multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			newTree.addNode(new Node(minusOperator, "1", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(minusOperator, "3", Node.RIGHT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "4", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "5", Node.RIGHT,
					Node.OPERAND));
			final String originalTree = newTree.getEquation().toString();
			assertEquals("Trees should be equal", "(1-3)/(4*5)", originalTree);
			assertTrue("Trees should not have an x", newTree.toString()
					.indexOf('x') <= 0);
			assertTrue("Trees should have a +",
					newTree.toString().indexOf('+') <= 0);
			Mutation.setRandomNodeToValue(newTree, "x", Node.OPERAND);
			assertTrue("Trees should have an x", newTree.getEquation()
					.toString().indexOf('x') >= 0);
			Mutation.setRandomNodeToValue(newTree, "+", Node.OPERATOR);
			assertTrue(newTree.getEquation().toString().indexOf('+') >= 0);

		} catch (Exception e) {
			fail("Threw exception ");
		}
	}
}
