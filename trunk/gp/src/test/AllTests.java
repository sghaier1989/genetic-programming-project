package test;

import gp.GeneticProgrammingException;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Main test suite.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public final class AllTests {

	/**
	 * Constructor.
	 */
	private AllTests() {
		
	}
	
	/**
	 * Method for running all the tests.
	 * 
	 * @return the suite
	 * @throws GeneticProgrammingException
	 *             - if something goes wrong
	 */
	public static Test suite() throws GeneticProgrammingException {
		// $JUnit-BEGIN$
		final TestSuite suite = new TestSuite();
		suite.addTestSuite(NodeTest.class);
		suite.addTestSuite(FitnessTest.class);
		// suite.addTestSuite(GeneticProgrammingTest.class);
		suite.addTestSuite(TreeFitnessComparatorTest.class);
		suite.addTestSuite(CrossoverTest.class);
		suite.addTestSuite(MutationTest.class);
		suite.addTestSuite(TreeTest.class);
		// $JUnit-END$
		return suite;
	}

}
