package test;

import gp.FunctionalSet;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;

public class CopyTreeTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new CopyTreeTest();

	}

	CopyTreeTest() {
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
			System.out.println(newTree.getEquation());
			System.out.println(newTree.copyTree().getEquation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

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

}
