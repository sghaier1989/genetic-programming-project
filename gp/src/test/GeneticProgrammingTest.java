package test;

import gp.FindThread;
import gp.FunctionalSet;
import gp.TerminalSet;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * This is a test for testing the GeneticProgramming class.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class GeneticProgrammingTest extends TestCase {

	/**
	 * Test for finding the best starting population.
	 */
	@Test
	public final void testBestPopulationTest() {
		final int maxTrainingData = 10;
		final int minTrainingData = -10;
		final int mutation = 25;
		final int crossover = 80;
		final int hieghtOfTree = 4;
		final int maxHeight = 5;
		long startTime = System.currentTimeMillis();
		long endTime = 0;
		final TerminalSet terminalSet = TestHelper.getTerminalSet();
		final FunctionalSet functionalSet = TestHelper.getFunctionalSet();
		for (int numOfTs = 100; numOfTs <= 2000; numOfTs = numOfTs + 100) {
			startTime = System.currentTimeMillis();
			createAndRun(startTime, terminalSet, functionalSet,
					maxTrainingData, minTrainingData, mutation, crossover,
					hieghtOfTree, maxHeight, numOfTs);
			endTime = System.currentTimeMillis();
			assertTrue("Needs to solve the problem in 15 minutes", endTime
					- startTime <= 900000);
		}
	}

	/**
	 * Method for running a genetic programming application.
	 * 
	 * @param startTime
	 *            - the start time of the test.
	 * @param terminalSet
	 *            - the terminal set.
	 * @param functionalSet
	 *            - the functional set.
	 * @param maxTrainingData
	 *            - Training data maximum.
	 * @param minTrainingData
	 *            - Training data minimum
	 * @param mutation
	 *            - Mutation rate of test
	 * @param crossover
	 *            - crossover rate of test
	 * @param hieghtOfTree
	 *            - starting height of tree
	 * @param maxHeight
	 *            - max tree height
	 * @param numberOfTrees
	 *            - population/number of trees
	 */
	public final synchronized void createAndRun(final long startTime,
			final TerminalSet terminalSet, final FunctionalSet functionalSet,
			final int maxTrainingData, final int minTrainingData,
			final int mutation, final int crossover, final int hieghtOfTree,
			final int maxHeight, final int numberOfTrees) {
		final FindThread findThread = new FindThread();

		findThread.setGui(false);
		findThread.setTerminalSet(terminalSet);
		findThread.setStartTime(startTime);
		findThread.setTargetExpersion("(x^2-1)/2");
		findThread.setFunctionalSet(functionalSet);
		findThread.setMutationRate(mutation / 100.00);
		findThread.setCrossoverRate(crossover / 100.00);
		findThread.setHieghtOfTree(hieghtOfTree);
		findThread.setMaxRange(maxTrainingData);
		findThread.setMinRange(minTrainingData);
		findThread.setNumberOfTrees(numberOfTrees);
		findThread.setMaxHeight(maxHeight);
		final ThreadGroup threadGroup = new ThreadGroup("FindThread");
		final Thread thread = new Thread(threadGroup, findThread, "");
		thread.start();

	}
}
