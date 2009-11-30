package test;

import gp.FunctionalSet;
import gp.GeneticProgrammingException;
import gp.Node;
import gp.TerminalSet;
import junit.framework.TestCase;

import org.junit.Test;

/**
 * This is a test for testing the node class.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class NodeTest extends TestCase {

	/**
	 * Test copying the node.
	 * 
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	@Test
	public final void testCopyNode() throws GeneticProgrammingException {
		final TerminalSet terminalSet = TestHelper.getTerminalSet();
		final FunctionalSet functionalSet = TestHelper.getFunctionalSet();
		final Node root = new Node(null, "/", null, Node.OPERATOR);
		final Node newNode = Node.copyNode(null, root, null, terminalSet,
				functionalSet);
		assertEquals("The trees should be the same", root.getValue(), newNode
				.getValue());
	}

}
