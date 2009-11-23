package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gp.FindThread;
import gp.Fitness;
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

public class FindThreadTest {

	private ArrayList<Tree> population = null;

	private void generateNodes(Tree newTree, Node newParent,
			int howDeepToMakeIt, TerminalSet ts, FunctionalSet fs) {
		try {
			if (newParent.getLevel() == howDeepToMakeIt - 1) {
				Node lOperand = new Node(newParent, ts.randomOperand(),
						Node.LEFT, Node.OPERAND);
				newTree.addNode(lOperand);
				Node rOperand = new Node(newParent, ts.randomOperand(),
						Node.RIGHT, Node.OPERAND);
				newTree.addNode(rOperand);
			} else {
				Node lOperator = new Node(newParent, fs.randomOperator(),
						Node.LEFT, Node.OPERATOR);
				newTree.addNode(lOperator);
				generateNodes(newTree, lOperator, howDeepToMakeIt, ts, fs);
				Node rOperator = new Node(newParent, fs.randomOperator(),
						Node.RIGHT, Node.OPERATOR);
				newTree.addNode(rOperator);
				generateNodes(newTree, rOperator, howDeepToMakeIt, ts, fs);
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

	private ArrayList<Tree> populate(int newNumberOfTrees, int newHeight,
			int[] newDataset, double[] newTargetTreeValues, TerminalSet ts,
			FunctionalSet fs) {
		this.population = new ArrayList<Tree>();
		try {
			for (int tree = 0; tree < newNumberOfTrees; tree++) {
				Node rootNode = new Node(null, fs.randomOperator(), null,
						Node.OPERATOR);
				Tree bt = new Tree(rootNode, ts, fs);
				rootNode.setTree(bt);
				generateNodes(bt, rootNode, newHeight, ts, fs);

				double fitness = Fitness.checkFitness(newTargetTreeValues,
						Fitness.calculateExpressionValues(bt, newDataset));
				bt.setFitness(fitness);
				this.population.add(bt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return population;
	}

	@Test
	public final void testSort() {
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
			int datasetSize = dataset.length;
			double[] targetTreeValues = new double[datasetSize];
			for (int x = 0; x < datasetSize; x++) {
				Expression exp = ExpressionTree.parse("(x-3)/(4*5)");
				VarMap vm = new VarMap(false /* case sensitive */);
				vm.setValue("x", dataset[x]);
				targetTreeValues[x] = exp.eval(vm, null);
			}

			ArrayList<Tree> trees = new ArrayList<Tree>();
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
			trees.add(newTree);
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
			trees.add(newTree);
			root = new Node(null, "/", null, Node.OPERATOR);
			newTree = new Tree(root, terminalSet, functionalSet);
			minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			opAOperand = new Node(minusOperator, "x", Node.LEFT, Node.OPERAND);
			newTree.addNode(opAOperand);
			opBOperand = new Node(minusOperator, "6", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opBOperand);
			opCOperand = new Node(multiOperator, "4", Node.LEFT, Node.OPERAND);
			newTree.addNode(opCOperand);
			opDOperand = new Node(multiOperator, "5", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opDOperand);
			assertEquals("(x-6)/(4*5)", newTree.toString());
			trees.add(newTree);
			root = new Node(null, "/", null, Node.OPERATOR);
			newTree = new Tree(root, terminalSet, functionalSet);
			minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			opAOperand = new Node(minusOperator, "x", Node.LEFT, Node.OPERAND);
			newTree.addNode(opAOperand);
			opBOperand = new Node(minusOperator, "7", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opBOperand);
			opCOperand = new Node(multiOperator, "4", Node.LEFT, Node.OPERAND);
			newTree.addNode(opCOperand);
			opDOperand = new Node(multiOperator, "5", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opDOperand);
			assertEquals("(x-7)/(4*5)", newTree.toString());
			trees.add(newTree);
			root = new Node(null, "/", null, Node.OPERATOR);
			newTree = new Tree(root, terminalSet, functionalSet);
			minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			opAOperand = new Node(minusOperator, "x", Node.LEFT, Node.OPERAND);
			newTree.addNode(opAOperand);
			opBOperand = new Node(minusOperator, "8", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opBOperand);
			opCOperand = new Node(multiOperator, "4", Node.LEFT, Node.OPERAND);
			newTree.addNode(opCOperand);
			opDOperand = new Node(multiOperator, "5", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opDOperand);
			assertEquals("(x-8)/(4*5)", newTree.toString());
			trees.add(newTree);
			root = new Node(null, "/", null, Node.OPERATOR);
			newTree = new Tree(root, terminalSet, functionalSet);
			minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
			newTree.addNode(minusOperator);
			multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
			newTree.addNode(multiOperator);
			opAOperand = new Node(minusOperator, "x", Node.LEFT, Node.OPERAND);
			newTree.addNode(opAOperand);
			opBOperand = new Node(minusOperator, "5", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opBOperand);
			opCOperand = new Node(multiOperator, "4", Node.LEFT, Node.OPERAND);
			newTree.addNode(opCOperand);
			opDOperand = new Node(multiOperator, "5", Node.RIGHT, Node.OPERAND);
			newTree.addNode(opDOperand);
			assertEquals("(x-5)/(4*5)", newTree.toString());
			trees.add(newTree);

			System.out.println("original trees list");
			Iterator<Tree> it = trees.iterator();
			while (it.hasNext()) {
				Tree t = it.next();
				double fitness = Fitness.checkFitness(targetTreeValues, Fitness
						.calculateExpressionValues(t, dataset));
				t.setFitness(fitness);
				System.out.println(t + " FITNESS IS " + t.getFitness());
			}
			FindThread.sortTrees(trees);
			System.out.println("sorted trees list");
			it = trees.iterator();
			while (it.hasNext()) {
				Tree t = it.next();
				double fitness = Fitness.checkFitness(targetTreeValues, Fitness
						.calculateExpressionValues(t, dataset));
				t.setFitness(fitness);
				System.out.println(t + " FITNESS IS " + t.getFitness());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

	@Test
	public final void visualTestSort() {
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
			int datasetSize = dataset.length;
			double[] targetTreeValues = new double[datasetSize];
			for (int x = 0; x < datasetSize; x++) {
				Expression exp = ExpressionTree.parse("(x-3)/(4*5)");
				VarMap vm = new VarMap(false /* case sensitive */);
				vm.setValue("x", dataset[x]);
				targetTreeValues[x] = exp.eval(vm, null);
			}
			ArrayList<Tree> populationTree = populate(100, 4, dataset,
					targetTreeValues, terminalSet, functionalSet);

			System.out.println("============Unsorted----------------");
			for (int x = 0; x < populationTree.size(); x++) {
				System.out.println(populationTree.get(x) + " FITNESS= "
						+ ((populationTree.get(x))).getFitness());
			}
			FindThread.sortTrees(populationTree);
			System.out.println("============Sorted----------------");
			for (int x = 0; x < populationTree.size(); x++) {
				System.out.println(populationTree.get(x) + " FITNESS= "
						+ ((populationTree.get(x))).getFitness());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}
}
