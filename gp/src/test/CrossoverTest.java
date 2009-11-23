package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.fail;
import gp.Crossover;
import gp.FunctionalSet;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;

import java.util.ArrayList;
import java.util.Iterator;

import org.junit.Test;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

public class CrossoverTest {

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
	public void testCross() {
		try {
			Node root = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree = new Tree(root, new TerminalSet(),
					new FunctionalSet());
			Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			Node multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			newTree.addNode(new Node(minusOperator, "1", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(minusOperator, "3", Node.RIGHT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "x", Node.LEFT,
					Node.OPERAND));
			newTree.addNode(new Node(multiOperator, "5", Node.RIGHT,
					Node.OPERAND));
			assertEquals("(1-3)/(x*5)", newTree.toString());
			Node root2 = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree2 = new Tree(root2, new TerminalSet(),
					new FunctionalSet());
			Node minusOperator2 = new Node(root2, "-", Node.LEFT, Node.OPERATOR);
			newTree2.addNode(minusOperator2);
			Node multiOperator2 = new Node(root2, "*", Node.RIGHT,
					Node.OPERATOR);
			newTree2.addNode(multiOperator2);
			newTree2.addNode(new Node(minusOperator2, "x", Node.LEFT,
					Node.OPERAND));
			newTree2.addNode(new Node(minusOperator2, "7", Node.RIGHT,
					Node.OPERAND));
			newTree2.addNode(new Node(multiOperator2, "8", Node.LEFT,
					Node.OPERAND));
			newTree2.addNode(new Node(multiOperator2, "9", Node.RIGHT,
					Node.OPERAND));
			assertEquals("(x-7)/(8*9)", newTree2.toString());

			Node root3 = new Node(null, "/", null, Node.OPERATOR);
			Tree newTree3 = new Tree(root3, new TerminalSet(),
					new FunctionalSet());
			Node minusOperator3 = new Node(root3, "-", Node.LEFT, Node.OPERATOR);
			newTree3.addNode(minusOperator3);
			Node multiOperator3 = new Node(root3, "*", Node.RIGHT,
					Node.OPERATOR);
			newTree3.addNode(multiOperator3);
			Node multiOperator4 = new Node(multiOperator3, "*", Node.LEFT,
					Node.OPERATOR);
			newTree3.addNode(multiOperator4);
			Node multiOperator5 = new Node(multiOperator3, "*", Node.RIGHT,
					Node.OPERATOR);
			newTree3.addNode(multiOperator5);
			newTree3.addNode(new Node(minusOperator3, "x", Node.LEFT,
					Node.OPERAND));
			newTree3.addNode(new Node(minusOperator3, "7", Node.RIGHT,
					Node.OPERAND));
			newTree3.addNode(new Node(multiOperator4, "1", Node.LEFT,
					Node.OPERAND));
			newTree3.addNode(new Node(multiOperator4, "2", Node.RIGHT,
					Node.OPERAND));
			newTree3.addNode(new Node(multiOperator5, "3", Node.LEFT,
					Node.OPERAND));
			newTree3.addNode(new Node(multiOperator5, "4", Node.RIGHT,
					Node.OPERAND));
			assertEquals("(x-7)/(1*2)*(3*4)", newTree3.toString());

			ArrayList<Tree> newTrees = new ArrayList<Tree>();
			newTrees.add(newTree3);
			newTrees.add(newTree2);
			newTrees.add(newTree);
			assertEquals(3, newTrees.size());
			Iterator<Tree> it234 = newTrees.iterator();
			while (it234.hasNext()) {

				String firstTree = it234.next().toString();
				System.out.println(firstTree);

			}
			System.out.println("=============================");
			int[] training = genertateTrainingDataSet(-100, 100);

			Crossover.cross(newTrees, 1, 100, training, calculateTargetValues(
					"x*2-1/2", training));

			Iterator<Tree> it = newTrees.iterator();
			while (it.hasNext()) {
				// All five trees should be different
				String firstTree = it.next().toString();
				assertNotSame(firstTree, it.next().toString());
				assertNotSame(firstTree, it.next().toString());
				assertNotSame(firstTree, it.next().toString());
				assertNotSame(firstTree, it.next().toString());
			}
			Iterator<Tree> it23 = newTrees.iterator();
			while (it23.hasNext()) {
				String firstTree = it23.next().toString();
				System.out.println(firstTree);
			}

			// should be 5 trees after the crossover
			assertEquals(5, newTrees.size());
		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}
}
