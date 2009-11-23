package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gp.FunctionalSet;
import gp.Node;
import gp.TerminalSet;

import org.junit.Test;

public class NodeTest {

	@Test
	public final void testCopyNode() {
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
			Node newNode = Node.copyNode(null, root, null, terminalSet,
					functionalSet, 0);
			assertEquals(root.getValue(), newNode.getValue());

		} catch (Exception e) {
			e.printStackTrace();
			fail("Threw exception ");
		}
	}

}
