package test;

import gp.FunctionalSet;
import gp.GeneticProgrammingException;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * This is a test for testing the Tree class.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class TreeTest extends TestCase {

	/**
	 * Test of the add node method.
	 * 
	 * @throws GeneticProgrammingException
	 *             - if something goes wrong
	 */
	@Test
	public final void testAddNode() throws GeneticProgrammingException {
		final Node root = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree = new Tree(root, TestHelper.getTerminalSet(),
				TestHelper.getFunctionalSet());
		final Node one = new Node(root, "1", Node.LEFT, Node.OPERAND);
		newTree.addNode(one);
		final Node two = new Node(root, "2", Node.RIGHT, Node.OPERAND);
		newTree.addNode(two);
		assertEquals("Node size should be 3", 3, newTree.getNodes().size());
	}

	/**
	 * Test of the copy tree method.
	 * 
	 * @throws GeneticProgrammingException
	 *             - if something goes wrong
	 */
	@Test
	public final void testCopyTree() throws GeneticProgrammingException {
		final Node root = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree = new Tree(root, TestHelper.getTerminalSet(),
				TestHelper.getFunctionalSet());
		TestHelper.generateNodes(newTree, root, 3);
		assertEquals("Trees should be the same", newTree.getEquation()
				.toString(), newTree.copyTree().getEquation().toString());
	}

	/**
	 * Method to test the getEquation method.
	 * 
	 * @throws GeneticProgrammingException
	 *             - if something went wrong
	 */
	@Test
	public final void testGetEquation() throws GeneticProgrammingException {
		final Node root = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree = new Tree(root, new TerminalSet(),
				new FunctionalSet());
		final Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
		newTree.addNode(minusOperator);
		final Node multiOperator = new Node(root, "*", Node.RIGHT,
				Node.OPERATOR);
		newTree.addNode(multiOperator);
		final Node opAOperand = new Node(minusOperator, "1", Node.LEFT,
				Node.OPERAND);
		newTree.addNode(opAOperand);
		final Node opBOperand = new Node(minusOperator, "3", Node.RIGHT,
				Node.OPERAND);
		newTree.addNode(opBOperand);
		final Node opCOperand = new Node(multiOperator, "4", Node.LEFT,
				Node.OPERAND);
		newTree.addNode(opCOperand);
		final Node opDOperand = new Node(multiOperator, "5", Node.RIGHT,
				Node.OPERAND);
		newTree.addNode(opDOperand);
		assertEquals("Equation should be (1-3)/(4*5)", "(1-3)/(4*5)", newTree
				.getEquation().toString());
	}

	/**
	 * Method to test the getFunctionalSet method.
	 * 
	 * @throws GeneticProgrammingException
	 *             - if something went wrong
	 */
	@Test
	public final void testGetFunctionalSet() throws GeneticProgrammingException {
		final TerminalSet terminalSet = TestHelper.getTerminalSet();
		final FunctionalSet functionalSet = TestHelper.getFunctionalSet();
		final Node root = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree = new Tree(root, terminalSet, functionalSet);
		assertEquals("Funcational set should be a size of four", 4, newTree
				.getFunctionalSet().size());
	}

	/**
	 * Method to test the get tree height method.
	 * 
	 * @throws GeneticProgrammingException
	 *             - if something went wrong
	 */
	@Test
	public final void testGetHeight() throws GeneticProgrammingException {
		final TerminalSet terminalSet = TestHelper.getTerminalSet();
		final FunctionalSet functionalSet = TestHelper.getFunctionalSet();
		final Node root = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree = new Tree(root, terminalSet, functionalSet);
		TestHelper.generateNodes(newTree, root, 1);
		assertEquals("height should be 1", 1, newTree.getHeight());
		TestHelper.generateNodes(newTree, root, 2);
		assertEquals("height should be 2", 2, newTree.getHeight());
		TestHelper.generateNodes(newTree, root, 3);
		assertEquals("height should be 3", 3, newTree.getHeight());
		TestHelper.generateNodes(newTree, root, 4);
		assertEquals("height should be 4", 4, newTree.getHeight());
		TestHelper.generateNodes(newTree, root, 5);
		assertEquals("height should be 5", 5, newTree.getHeight());
		TestHelper.generateNodes(newTree, root, 6);
		assertEquals("height should be 6", 6, newTree.getHeight());
		TestHelper.generateNodes(newTree, root, 7);
		assertEquals("height should be 7", 7, newTree.getHeight());
	}

	/**
	 * Method to test the get number of nodes in a tree.
	 * 
	 * @throws GeneticProgrammingException
	 *             - if something went wrong
	 */
	@Test
	public final void testGetNumberOfNodes() throws GeneticProgrammingException {
		final TerminalSet terminalSet = TestHelper.getTerminalSet();
		final FunctionalSet functionalSet = TestHelper.getFunctionalSet();
		final Node root1 = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree1 = new Tree(root1, terminalSet, functionalSet);
		TestHelper.generateNodes(newTree1, root1, 1);
		assertEquals("height should be 3", 3, newTree1.getNodes().size());
		final Node root2 = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree2 = new Tree(root2, terminalSet, functionalSet);
		TestHelper.generateNodes(newTree2, root2, 2);
		assertEquals("height should be 7", 7, newTree2.getNodes().size());
		final Node root3 = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree3 = new Tree(root3, terminalSet, functionalSet);
		TestHelper.generateNodes(newTree3, root3, 3);
		assertEquals("height should be 15", 15, newTree3.getNodes().size());
		final Node root4 = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree4 = new Tree(root4, terminalSet, functionalSet);
		TestHelper.generateNodes(newTree4, root4, 4);
		assertEquals("height should be 31", 31, newTree4.getNodes().size());
	}

	/**
	 * Method to test creating a tree.
	 * 
	 * @throws GeneticProgrammingException
	 *             - if something went wrong
	 */
	@Test
	public final void testTree() throws GeneticProgrammingException {
		final TerminalSet terminalSet = TestHelper.getTerminalSet();
		final FunctionalSet functionalSet = TestHelper.getFunctionalSet();
		final Node root = new Node(null, "/", null, Node.OPERATOR);
		final Tree newTree = new Tree(root, terminalSet, functionalSet);
		TestHelper.generateNodes(newTree, root, 3);
		assertTrue("Should be true", root.isRoot());
		assertEquals("Should be equal", newTree.getRoot().toString(), root
				.toString());
		assertEquals("Should be 15", 15, newTree.getNodes().size());
	}

}
