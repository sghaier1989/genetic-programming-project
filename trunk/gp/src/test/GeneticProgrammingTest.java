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
		final TerminalSet terminalSet = TestHelper.getTerminalSet();
		final FunctionalSet functionalSet = TestHelper.getFunctionalSet();

		startTime = System.currentTimeMillis();

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
		findThread.setNumberOfTrees(1000);
		findThread.setMaxHeight(maxHeight);
		final ThreadGroup threadGroup = new ThreadGroup("FindThread");
		final Thread thread = new Thread(threadGroup, findThread, "");
		thread.start();

		while (thread.isAlive()) {
			// looking
		}
		assertTrue("needs to solve in less then 15 minutes", (System
				.currentTimeMillis() - startTime) < 900000);

	}
}
