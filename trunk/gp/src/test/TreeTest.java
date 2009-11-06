package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gp.FunctionalSet;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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

	@Test
	public final void testTree() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			root.setLevel(0);
			root.setOperator("/");
			root.setRoot(true);
			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			newTree.addNode(root);
			generateNodes(newTree, root, 3);
			assertEquals(16, newTree.getNodes().size());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testAddNode() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			root.setLevel(0);
			root.setOperator("/");
			root.setRoot(true);
			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			newTree.addNode(root);
			generateNodes(newTree, root, 3);
			assertEquals(16, newTree.getNodes().size());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testCopyTree() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			root.setLevel(0);
			root.setOperator("/");
			root.setRoot(true);
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
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			newTree.addNode(root);
			generateNodes(newTree, root, 3);
			assertEquals(newTree.getEquation().toString(), newTree.copyTree()
					.getEquation().toString());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testGetEquation() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			root.setLevel(0);
			root.setOperator("/");
			root.setRoot(true);

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
			assertEquals("(1-3)/(4*5)", newTree.getEquation().toString());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testGetFunctionalSet() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			root.setLevel(0);
			root.setOperator("/");
			root.setRoot(true);
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
			root.setLevel(0);
			root.setOperator("/");
			root.setRoot(true);
			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			functionalSet.add("/");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			newTree.addNode(root);
			generateNodes(newTree, root, 3);
			System.out.println(newTree.getHeight());
			System.out.println(newTree.getEquation());
			assertEquals(3, newTree.getHeight());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void testGetNumberOfNodes() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			root.setLevel(0);
			root.setOperator("/");
			root.setRoot(true);
			FunctionalSet functionalSet = new FunctionalSet();
			functionalSet.add("+");
			functionalSet.add("-");
			functionalSet.add("/");
			TerminalSet terminalSet = new TerminalSet();
			terminalSet.add("1");
			terminalSet.add("2");
			terminalSet.add("3");
			Tree newTree = new Tree(root, terminalSet, functionalSet);
			newTree.addNode(root);
			generateNodes(newTree, root, 3);
			System.out.println(newTree.getHeight());
			System.out.println(newTree.getEquation());
			System.out.println(newTree.getNodes().size());
			assertEquals(16, newTree.getNodes().size());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

}
