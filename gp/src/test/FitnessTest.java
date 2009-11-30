package test;

import gp.Fitness;
import gp.FunctionalSet;
import gp.GeneticProgrammingException;
import gp.Node;
import gp.TerminalSet;
import gp.Tree;
import junit.framework.TestCase;

import org.junit.Test;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

/**
 * This is a test for testing the fitness class.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class FitnessTest extends TestCase {

	/**
	 * The Fitness test.
	 * 
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	@Test
	public final void testFitness() throws GeneticProgrammingException {
		try {
			final TerminalSet terminalset = TestHelper.getTerminalSet();
			final FunctionalSet functionalset = TestHelper.getFunctionalSet();
			final int[] dataset = TestHelper.genertateTrainingDataSet(-10, 10);
			final int datasetSize = dataset.length;
			double[] targetTreeValues = new double[datasetSize];
			final Expression exp = ExpressionTree.parse("(x-3)/(4*5)");
			final VarMap varMap = new VarMap(false /* case sensitive */);
			for (int x = 0; x < datasetSize; x++) {
				varMap.setValue("x", dataset[x]);
				targetTreeValues[x] = exp.eval(varMap, null);
			}
			final Node root = new Node(null, "/", null, Node.OPERATOR);
			final Tree newTree = new Tree(root, terminalset, functionalset);
			final Node minusOperator = new Node(root, "-", Node.LEFT,
					Node.OPERATOR);
			newTree.addNode(minusOperator);
			final Node multiOperator = new Node(root, "*", Node.RIGHT,
					Node.OPERATOR);
			newTree.addNode(multiOperator);
			final Node opAOperand = new Node(minusOperator, "x", Node.LEFT,
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
			assertEquals("Tree did not get generated corectly", "(x-3)/(4*5)",
					newTree.getEquation().toString());
			final double fitness = Fitness.checkFitness(targetTreeValues,
					Fitness.calculateExpressionValues(newTree, dataset));
			assertTrue("The fitness did not calculate correctly", 0 == fitness);
		} catch (GeneticProgrammingException e) {
			fail("Threw exception ");
			throw e;
		}
	}
}
