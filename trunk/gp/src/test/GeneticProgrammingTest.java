package test;

import static org.junit.Assert.fail;
import gp.FindThread;
import gp.FunctionalSet;
import gp.TerminalSet;

import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import org.junit.Test;

public class GeneticProgrammingTest {
	public static final String DATE_FORMAT = "mm:ss";
	private SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

	@Test
	public final void bestPopulationTest() {
		int maxTrainingData = 100;
		int minTrainingData = -100;
		int mutation = 25;
		int crossover = 80;
		int hieghtOfTree = 4;
		int maxHeight = 5;
		long startTime = System.currentTimeMillis();
		String terminalSetValue = "1,2,3,4,5,6,7,8,9,x";
		StringTokenizer st = new StringTokenizer(terminalSetValue, ",");
		TerminalSet terminalSet = new TerminalSet();
		while (st.hasMoreElements()) {
			terminalSet.add(st.nextToken());
		}
		String functionalSetValue = "+,-,/,*";
		StringTokenizer st2 = new StringTokenizer(functionalSetValue, ",");
		FunctionalSet functionalSet = new FunctionalSet();
		while (st2.hasMoreElements()) {
			functionalSet.add(st2.nextToken());
		}
		for (int numberOfTrees = 100; numberOfTrees <= 2000; numberOfTrees = numberOfTrees + 100) {
			startTime = System.currentTimeMillis();
			System.out.println("Number Of Trees " + numberOfTrees);
			createAndRun(startTime, terminalSet, functionalSet,
					maxTrainingData, minTrainingData, mutation, crossover,
					hieghtOfTree, maxHeight, numberOfTrees);
			System.out.println("Time: "
					+ sdf.format(System.currentTimeMillis() - startTime));
		}
	}

	public synchronized void createAndRun(long startTime,
			TerminalSet terminalSet, FunctionalSet functionalSet,
			int maxTrainingData, int minTrainingData, int mutation,
			int crossover, int hieghtOfTree, int maxHeight, int numberOfTrees) {
		try {
			FindThread ft = new FindThread();
			ft.setGui(false);
			ft.setTerminalSet(terminalSet);
			ft.setStartTime(startTime);
			ft.setTargetExpersion("(x^2-1)/2");
			ft.setFunctionalSet(functionalSet);
			ft.setMutationRate(mutation / 100.00);
			ft.setCrossoverRate(crossover / 100.00);
			ft.setHieghtOfTree(hieghtOfTree);
			ft.setMaxRange(maxTrainingData);
			ft.setMinRange(minTrainingData);
			ft.setNumberOfTrees(numberOfTrees);
			ft.setMaxHeight(maxHeight);
			ThreadGroup tg = new ThreadGroup("FindThread");
			Thread thread = new Thread(tg, ft, "");
			thread.start();
			while (thread.isAlive()) {
			}

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}
}
