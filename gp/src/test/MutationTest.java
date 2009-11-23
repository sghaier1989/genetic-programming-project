package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import gp.FunctionalSet;
import gp.Mutation;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

public class MutationTest {

	private double[] calculateTargetValues(String targetedExpresion,
			int[] dataset) {
		int datasetSize = dataset.length;
		double[] targetTreeValues = new double[datasetSize];
		for (int x = 0; x < datasetSize; x++) {
			Expression exp = ExpressionTree.parse(targetedExpresion);
			VarMap vm = new VarMap(false /* case sensitive */);
			vm.setValue("x", dataset[x]);
			targetTreeValues[x] = exp.eval(vm, null);
		}
		return targetTreeValues;
	}

	private int[] genertateTrainingDataSet(int newMin, int newMax) {
		int[] values = new int[newMax - newMin];

		for (int x = 0; x < values.length; x++) {
			values[x] = newMin++;
		}
		return values;
	}

	@Test
	public void testMutateTrees() {
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
			Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			Node multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			newTree.addNode(new Node(minusOperator, "1", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(minusOperator, "3", Node.RIGHT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "4", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "5", Node.RIGHT,
					Node.OPERAND));
			String originalTree = newTree.toString();
			assertEquals("(1-3)/(4*5)", originalTree);
			ArrayList<Tree> newTrees = new ArrayList<Tree>();
			newTrees.add(newTree);
			assertEquals(1, newTrees.size());
			int[] training = genertateTrainingDataSet(-100, 100);
			Mutation.mutateTrees(newTrees, 1, training, calculateTargetValues(
					"x*2-1/2", training));
			Iterator<Tree> it = newTrees.iterator();
			while (it.hasNext()) {
				String t = it.next().toString();
				assertEquals(3, newTrees.size());
				assertNotSame(originalTree, t);
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public void testSetRandomNodeToValue() {
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
			Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			Node multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			newTree.addNode(new Node(minusOperator, "1", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(minusOperator, "3", Node.RIGHT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "4", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "5", Node.RIGHT,
					Node.OPERAND));
			String originalTree = newTree.toString();
			assertEquals("(1-3)/(4*5)", originalTree);
			assertTrue(newTree.toString().indexOf('x') <= 0);
			assertTrue(newTree.toString().indexOf('+') <= 0);
			Mutation.setRandomNodeToValue(newTree, "x", Node.OPERAND);
			assertTrue(newTree.toString().indexOf('x') >= 0);
			Mutation.setRandomNodeToValue(newTree, "+", Node.OPERATOR);
			assertTrue(newTree.toString().indexOf('+') >= 0);
			System.out.println(newTree);

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

}
