package test;

import gp.Fitness;
import gp.FunctionalSet;
import gp.GeneticProgrammingException;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;
import gp.TreeFitnessComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import junit.framework.TestCase;

import org.junit.Test;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

/**
 * This is a test for testing the TreeFitness class.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class TreeFitnessComparatorTest extends TestCase {

	/**
	 * Sort test.
	 * 
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	@Test
	public final void testSort() throws GeneticProgrammingException {
		final TerminalSet terminalset = TestHelper.getTerminalSet();
		final FunctionalSet functionalset = TestHelper.getFunctionalSet();
		final int[] dataset = TestHelper.genertateTrainingDataSet(-100, 100);
		final int datasetSize = dataset.length;
		double[] targetTreeValues = new double[datasetSize];
		final Expression exp = ExpressionTree.parse("(x-3)/(4*5)");
		final VarMap varMap = new VarMap(false /* case sensitive */);
		for (int x = 0; x < datasetSize; x++) {
			varMap.setValue("x", dataset[x]);
			targetTreeValues[x] = exp.eval(varMap, null);
		}
		final ArrayList<Tree> trees = new ArrayList<Tree>();
		Node root = new Node(null, "/", null, Node.OPERATOR);
		Tree newTree = new Tree(root, terminalset, functionalset);
		Node minusOperator = new Node(root, "-", Node.LEFT, Node.OPERATOR);
		newTree.addNode(minusOperator);
		Node multiOperator = new Node(root, "*", Node.RIGHT, Node.OPERATOR);
		newTree.addNode(multiOperator);
		Node opAOperand = new Node(minusOperator, "x", Node.LEFT, Node.OPERAND);
		newTree.addNode(opAOperand);
		Node opBOperand = new Node(minusOperator, "3", Node.RIGHT, Node.OPERAND);
		newTree.addNode(opBOperand);
		Node opCOperand = new Node(multiOperator, "4", Node.LEFT, Node.OPERAND);
		newTree.addNode(opCOperand);
		Node opDOperand = new Node(multiOperator, "5", Node.RIGHT, Node.OPERAND);
		newTree.addNode(opDOperand);
		assertEquals("Tree did not get generated corectly", "(x-3)/(4*5)",
				newTree.getEquation().toString());
		trees.add(newTree);
		root = new Node(null, "/", null, Node.OPERATOR);
		newTree = new Tree(root, terminalset, functionalset);
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
		assertEquals("Tree did not get generated corectly", "(x-4)/(4*5)",
				newTree.getEquation().toString());
		trees.add(newTree);
		root = new Node(null, "/", null, Node.OPERATOR);
		newTree = new Tree(root, terminalset, functionalset);
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
		assertEquals("Tree did not get generated corectly", "(x-6)/(4*5)",
				newTree.getEquation().toString());
		trees.add(newTree);
		root = new Node(null, "/", null, Node.OPERATOR);
		newTree = new Tree(root, terminalset, functionalset);
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
		assertEquals("Tree did not get generated corectly", "(x-7)/(4*5)",
				newTree.getEquation().toString());
		trees.add(newTree);
		root = new Node(null, "/", null, Node.OPERATOR);
		newTree = new Tree(root, terminalset, functionalset);
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
		assertEquals("Tree did not get generated corectly", "(x-8)/(4*5)",
				newTree.getEquation().toString());
		trees.add(newTree);
		root = new Node(null, "/", null, Node.OPERATOR);
		newTree = new Tree(root, terminalset, functionalset);
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
		assertEquals("Tree did not get generated corectly", "(x-5)/(4*5)",
				newTree.getEquation().toString());
		trees.add(newTree);
		Iterator<Tree> iterator = trees.iterator();
		while (iterator.hasNext()) {
			final Tree tree = iterator.next();
			final double fitness = Fitness.checkFitness(targetTreeValues,
					Fitness.calculateExpressionValues(tree, dataset));
			tree.setFitness(fitness);
		}
		Collections.sort(trees, new TreeFitnessComparator());
		iterator = trees.iterator();
		double previousFitness = 0;
		while (iterator.hasNext()) {
			final Tree tree = iterator.next();
			final double fitness = Fitness.checkFitness(targetTreeValues,
					Fitness.calculateExpressionValues(tree, dataset));
			assertTrue("Sort must not be working right ",
					previousFitness <= fitness);
			previousFitness = fitness;
			tree.setFitness(fitness);
		}

	}
}
