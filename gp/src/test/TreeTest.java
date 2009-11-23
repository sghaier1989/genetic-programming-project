package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gp.Fitness;
import gp.FunctionalSet;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;

import org.junit.Test;

public class TreeTest {
	private void generateNodes(Tree newTree, Node newParent, int howDeepToMakeIt) {
		try {
			if (newParent.getLevel() == howDeepToMakeIt - 1) {

				Node lOperand = new Node(newParent, newTree.getTerminalSet()
						.randomOperand(), Node.LEFT, Node.OPERAND);
				newTree.addNode(lOperand);
				Node rOperand = new Node(newParent, newTree.getTerminalSet()
						.randomOperand(), Node.RIGHT, Node.OPERAND);
				newTree.addNode(rOperand);
			} else {
				Node lOperator = new Node(newParent, newTree.getFunctionalSet()
						.randomOperator(), Node.LEFT, Node.OPERATOR);
				newTree.addNode(lOperator);
				generateNodes(newTree, lOperator, howDeepToMakeIt);
				Node rOperator = new Node(newParent, newTree.getFunctionalSet()
						.randomOperator(), Node.RIGHT, Node.OPERATOR);
				newTree.addNode(rOperator);
				generateNodes(newTree, rOperator, howDeepToMakeIt);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private int[] genertateTrainingDataSet(int newMin, int newMax) {
		int[] values = new int[newMax - newMin];

		for (int x = 0; x < values.length; x++) {
			values[x] = newMin++;
		}
		return values;
	}

	@Test
	public final void testAddNode() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			generateNodes(newTree, root, 3);
			assertEquals(15, newTree.getNodes().size());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testCopyTree() {
		try {

			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			functionalSet.add("/");
			functionalSet.add("*");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			terminalSet.add("4");
			terminalSet.add("5");
			terminalSet.add("6");
			terminalSet.add("7");
			terminalSet.add("8");
			terminalSet.add("9");
			terminalSet.add("x");
			Node root = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			generateNodes(newTree, root, 3);
			System.out.println("Original Tree: " + newTree.toString());
			System.out.println("Copy Tree:     "
					+ newTree.copyTree().toString());
			assertEquals(newTree.toString(), newTree.copyTree().toString());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testFitness() {
		try {

			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			functionalSet.add("/");
			functionalSet.add("*");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			terminalSet.add("4");
			terminalSet.add("5");
			terminalSet.add("6");
			terminalSet.add("7");
			terminalSet.add("8");
			terminalSet.add("9");
			terminalSet.add("x");

			int[] dataset = genertateTrainingDataSet(-100, 100);
			double[] targetTreeValues = Fitness.calculateExpressionValues(
					"(x-3)/(4*5)", dataset);
			Node root = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			Node multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			Node opAOperand = new Node(minusOperator, "x", Node.LEFT,
					Node.OPERAND);
			newTree.addNode(opAOperand);
			Node opBOperand = new Node(minusOperator, "3", Node.RIGHT,
					Node.OPERAND);
			newTree.addNode(opBOperand);
			Node opCOperand = new Node(multiOperator, "4", Node.LEFT,
					Node.OPERAND);
			newTree.addNode(opCOperand);
			Node opDOperand = new Node(multiOperator, "5", Node.RIGHT,
					Node.OPERAND);
			newTree.addNode(opDOperand);
			assertEquals("(x-3)/(4*5)", newTree.toString());

			assertTrue(Fitness.checkFitness(targetTreeValues, Fitness
					.calculateExpressionValues(newTree, dataset)) == 0);

			System.out.println(newTree + " FITNESS IS " + newTree.getFitness());
			root = new Node(null, "/", null, Node.OPERATOR);
			newTree = new Tree(root, terminalSet, functionalSet);
			minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			opAOperand = new Node(minusOperator, "x", Node.LEFT, Node.OPERAND);
			newTree.addNode(opAOperand);
			opBOperand = new Node(minusOperator, "4", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opBOperand);
			opCOperand = new Node(multiOperator, "4", Node.LEFT, Node.OPERAND);
			newTree.addNode(opCOperand);
			opDOperand = new Node(multiOperator, "5", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opDOperand);
			assertEquals("(x-4)/(4*5)", newTree.toString());

			assertTrue(Fitness.checkFitness(targetTreeValues, Fitness
					.calculateExpressionValues(newTree, dataset)) == .5000000000000004);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testGetEquation() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree = new Tree(root, new TerminalSet(),
					new FunctionalSet());
			Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			Node multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);

			Node opAOperand = new Node(minusOperator, "1", Node.LEFT,
					Node.OPERAND);
			newTree.addNode(opAOperand);
			Node opBOperand = new Node(minusOperator, "3", Node.RIGHT,
					Node.OPERAND);
			newTree.addNode(opBOperand);

			Node opCOperand = new Node(multiOperator, "4", Node.LEFT,
					Node.OPERAND);
			newTree.addNode(opCOperand);
			Node opDOperand = new Node(multiOperator, "5", Node.RIGHT,
					Node.OPERAND);
			newTree.addNode(opDOperand);
			assertEquals("(1-3)/(4*5)", newTree.toString());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testGetFunctionalSet() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			functionalSet.add("/");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			assertEquals(3, newTree.getFunctionalSet().size());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testGetHeight() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			functionalSet.add("/");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			generateNodes(newTree, root, 1);
			assertEquals(1, newTree.getHeight());
			generateNodes(newTree, root, 2);
			assertEquals(2, newTree.getHeight());
			generateNodes(newTree, root, 3);
			assertEquals(3, newTree.getHeight());
			generateNodes(newTree, root, 4);
			assertEquals(4, newTree.getHeight());
			generateNodes(newTree, root, 5);
			assertEquals(5, newTree.getHeight());
			generateNodes(newTree, root, 6);
			assertEquals(6, newTree.getHeight());
			generateNodes(newTree, root, 7);
			assertEquals(7, newTree.getHeight());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testGetNumberOfNodes() {
		try {

			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			functionalSet.add("/");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			Node root1 = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree1 = new Tree(root1, terminalSet, functionalSet);
			generateNodes(newTree1, root1, 1);
			assertEquals(3, newTree1.getNodes().size());
			Node root2 = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree2 = new Tree(root2, terminalSet, functionalSet);
			generateNodes(newTree2, root2, 2);
			assertEquals(7, newTree2.getNodes().size());

			Node root3 = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree3 = new Tree(root3, terminalSet, functionalSet);
			generateNodes(newTree3, root3, 3);
			assertEquals(15, newTree3.getNodes().size());
			Node root4 = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree4 = new Tree(root4, terminalSet, functionalSet);
			generateNodes(newTree4, root4, 4);
			assertEquals(31, newTree4.getNodes().size());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testToString() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree = new Tree(root, new TerminalSet(),
					new FunctionalSet());
			Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			Node multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);

			Node opAOperand = new Node(minusOperator, "1", Node.LEFT,
					Node.OPERAND);
			newTree.addNode(opAOperand);
			Node opBOperand = new Node(minusOperator, "3", Node.RIGHT,
					Node.OPERAND);
			newTree.addNode(opBOperand);

			Node opCOperand = new Node(multiOperator, "4", Node.LEFT,
					Node.OPERAND);
			newTree.addNode(opCOperand);
			Node opDOperand = new Node(multiOperator, "5", Node.RIGHT,
					Node.OPERAND);
			newTree.addNode(opDOperand);
			assertEquals("(1-3)/(4*5)", newTree.toString());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testTree() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			generateNodes(newTree, root, 3);
			assertTrue(root.isRoot());
			assertEquals(newTree.getRoot().toString(), root.toString());
			assertEquals(15, newTree.getNodes().size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

}
