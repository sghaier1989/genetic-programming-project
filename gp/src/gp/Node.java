package gp;

//import javax.swing.tree.DefaultMutableTreeNode;

import org.apache.log4j.Logger;

/**
 * A class the represents a binary tree node. A leaf node has no children. The
 * depth of a node n is the length of the path from the root to the node. The
 * set of all nodes at a given depth is sometimes called a level of the tree.
 * The root node is at depth zero. Siblings are nodes that share the same parent
 * node. If a path exists from node p to node q, where node p is closer to the
 * root node than q, then p is an ancestor of q and q is a descendant of p. The
 * size of a node is the number of descendants it has including itself.
 * In-degree of a node is the number of edges arriving at that node. Out-degree
 * of a node is the number of edges leaving that node.
 * 
 * @author Trevor Greene
 * @version 1.0
 */

// public class Node extends DefaultMutableTreeNode {
public class Node {
	/**
	 * 
	 */
	private static final long serialVersionUID = 401754073156283817L;
	static Logger logger = Logger.getLogger(GeneticProgramming.class);
	public static final String OPERAND = "OPERAND";
	public static final String OPERATOR = "OPERATOR";
	public static final String RIGHT = "RIGHT";
	public static final String LEFT = "LEFT";
	public static final String ROOT = "ROOT";
	public static final String VARIABLE = "VARIABLE";

	public static Node copyNode(Tree newTree, Node nodeToCopy,
			Node newNodeParent, TerminalSet ts, FunctionalSet fs, int count)
			throws Exception {
		Node newNode = null;
		Tree tree = newTree;
		if (count > 100) {
			throw new Exception(
					"We are likely in a loop, trying to copy a node.  AHHHHH!");
		}
		count++;
		try {
			if (newNodeParent == null) {
				newNode = new Node(null, nodeToCopy.getValue(), nodeToCopy
						.getPointer(), nodeToCopy.getType());
				newNode.setLevel(nodeToCopy.getLevel());
				newNode.setRoot(true);
				tree = new Tree(newNode, ts, fs);
				newNode.setTree(tree);
			} else {
				newNode = new Node(newNodeParent, nodeToCopy.getValue(),
						nodeToCopy.getPointer(), nodeToCopy.getType());
				newNode.setLevel(nodeToCopy.getLevel());
				newNode.setRoot(nodeToCopy.isRoot);
				tree.addNode(newNode);
			}
			Node leftNodeToCopy = nodeToCopy.getLeft();
			if (leftNodeToCopy != null) {
				newNode.setLeft(copyNode(tree, leftNodeToCopy, newNode, ts, fs,
						count));
			}
			Node rightNodeToCopy = nodeToCopy.getRight();
			if (rightNodeToCopy != null) {
				newNode.setRight(copyNode(tree, rightNodeToCopy, newNode, ts,
						fs, count));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newNode;
	}

	private boolean isRoot = false;
	private Node left = null;
	private int level = 0;
	private Node parent = null;
	private String pointer = null;
	private Node right = null;
	private Tree tree = null;
	private String type = null;

	private String value = null;

	public Node(Node newParent, String newValue, String newPointer,
			String newType) throws Exception {
		setType(newType);
		setValue(newValue);
		if (newParent == null
				&& !((newPointer == LEFT) && (newPointer == RIGHT))) {
			setRoot(true);
			setLevel(0);
		} else if (!(newParent == null)
				&& ((newPointer == LEFT) || (newPointer == RIGHT))) {
			setPointer(newPointer);
			setParent(newParent);
			setRoot(false);
			newParent.setChild(this);
		} else {
			throw new Exception("Invalid node inputs");
		}

	}

	public Node getLeft() {
		return this.left;
	}

	public int getLevel() {
		return this.level;
	}

	public Node getParent() {
		return this.parent;
	}

	public String getPointer() {
		return this.pointer;
	}

	public Node getRight() {
		return this.right;
	}

	public Tree getTree() {
		return this.tree;
	}

	public String getType() {
		return this.type;
	}

	public String getValue() {
		return this.value;
	}

	public boolean isRoot() {
		return this.isRoot;
	}

	public void printNodeInfo() {
		logger.info("-------------" + this + "------------- ");
		logger.info("Get tree: " + getTree());
		logger.info("Is Root?: " + isRoot());
		logger.info("Level: " + getLevel());
		logger.info("Pointer: " + getPointer());
		logger.info("Type: " + getType());
		logger.info("Parent: " + getParent());
		logger.info("Node: " + this);
		logger.info("Left Node: " + getLeft());
		logger.info("Right Node: " + getRight());
		logger.info("Value: " + getValue());
		logger.info("-------------END------------- ");
	}

	public void setChild(Node newNode) throws Exception {
		if (newNode.getPointer() == Node.LEFT) {
			setLeft(newNode);
		} else if (newNode.getPointer() == Node.RIGHT) {
			setRight(newNode);
		} else {
			throw new Exception("No pointer set on node");
		}
	}

	public void setLeft(Node newLeft) throws Exception {
		try {
			if (!newLeft.isRoot()) {
				this.left = newLeft;
			} else {
				logger.error("Root can not be set to left node");
				printNodeInfo();
				newLeft.printNodeInfo();
				new Exception("Root can not be set to left node");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void setLevel(int newLevel) {
		this.level = newLevel;
	}

	public void setParent(Node newParent) throws Exception {
		if (isRoot) {
			logger
					.error("This node is a root and can not have a parent assigned to it.");
			printNodeInfo();
			logger.error("The parent looks like this:");
			newParent.printNodeInfo();
			throw new Exception(
					"This node is a root and can not have a parent assigned to it.");
		}
		if (this == newParent) {
			logger
					.error("Error:  Tried to set the nodes parent to itself ex node.setPartent(node)");
			printNodeInfo();
			logger.error("The parent looks like this:");
			newParent.printNodeInfo();
			throw new Exception(
					"Error:  Tried to set the nodes parent to itself ex node.setPartent(node)");
		} else {
			this.parent = newParent;
		}
	}

	public void setPointer(String newPointer) throws Exception {
		if ((newPointer == LEFT) || (newPointer == RIGHT)) {
			this.pointer = newPointer;
		} else {
			logger.error("Invalid pointer must be RIGHT or LEFT");
			printNodeInfo();
			throw new Exception("Invalid pointer must be RIGHT or LEFT");
		}

	}

	public void setRight(Node newRight) throws Exception {
		try {
			if (!newRight.isRoot()) {
				this.right = newRight;
			} else {
				logger.error("Root can not be set to right node");
				printNodeInfo();
				newRight.printNodeInfo();
				new Exception("Root can not be set to right node");
			}
		} catch (Exception e) {
			throw e;
		}
	}

	public void setRoot(boolean newIsRoot) throws Exception {
		this.isRoot = newIsRoot;
	}

	public void setTree(Tree newTree) {
		this.tree = newTree;
	}

	public void setType(String newType) throws Exception {
		if ((newType == OPERAND) || (newType == OPERATOR)
				|| (newType == VARIABLE)) {
			this.type = newType;
		} else {
			throw new Exception(
					"Invalid type must be OPERAND, OPERATOR or VARIABLE");
		}

	}

	public void setValue(String newValue) {
		this.value = newValue;
	}

}
