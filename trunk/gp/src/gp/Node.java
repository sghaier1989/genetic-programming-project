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
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 401754073156283817L;
	/**
	 * Logger.
	 */
	private static final Logger GP_LOGGER = Logger
			.getLogger(GeneticProgramming.class);

	/**
	 * Static operand tag.
	 */
	public static final String OPERAND = "OPERAND";
	/**
	 * Static operator tag.
	 */
	public static final String OPERATOR = "OPERATOR";
	/**
	 * Static right tag.
	 */
	public static final String RIGHT = "RIGHT";
	/**
	 * Static left tag.
	 */
	public static final String LEFT = "LEFT";
	/**
	 * Static root tag.
	 */
	public static final String ROOT = "ROOT";
	/**
	 * Static variable tag.
	 */
	public static final String VARIABLE = "VARIABLE";
	/**
	 * if its a root node.
	 */
	private boolean isNodeRoot = false;
	/**
	 * the left node.
	 */
	private Node left = null;
	/**
	 * The level.
	 */
	private int level = 0;
	/**
	 * The parent.
	 */
	private Node parent = null;
	/**
	 * the pointer.
	 */
	private String pointer = null;
	/**
	 * The right node.
	 */
	private Node right = null;
	/**
	 * The Tree.
	 */
	private Tree tree = null;
	/**
	 * The type.
	 */
	private String type = null;
	/**
	 * The value .
	 */
	private String value = null;

	/**
	 * Method for copying a node.
	 * 
	 * @param newTree
	 *            - the tree the a the node belongs to
	 * @param nodeToCopy
	 *            the node to copy
	 * @param newNodeParent
	 *            - the new nodes parent. Can be null if root node
	 * @param termSet
	 *            - terminal set of node
	 * @param functSet
	 *            - functional set of node
	 * @return - returns a copy of the node passed to it
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public static Node copyNode(final Tree newTree, final Node nodeToCopy,
			final Node newNodeParent, final TerminalSet termSet,
			final FunctionalSet functSet) throws GeneticProgrammingException {
		Node newNode = null;
		Tree tree = newTree;
		if (newNodeParent == null) {
			newNode = new Node(null, nodeToCopy.getValue(), nodeToCopy
					.getPointer(), nodeToCopy.getType());
			newNode.setLevel(nodeToCopy.getLevel());
			newNode.setRoot(true);
			tree = new Tree(newNode, termSet, functSet);
			newNode.setTree(tree);
		} else {
			newNode = new Node(newNodeParent, nodeToCopy.getValue(), nodeToCopy
					.getPointer(), nodeToCopy.getType());
			newNode.setLevel(nodeToCopy.getLevel());
			newNode.setRoot(nodeToCopy.isNodeRoot);
			tree.addNode(newNode);
		}
		final Node leftNodeToCopy = nodeToCopy.getLeft();
		if (leftNodeToCopy != null) {
			newNode.setLeft(copyNode(tree, leftNodeToCopy, newNode, termSet,
					functSet));
		}
		final Node rightNodeToCopy = nodeToCopy.getRight();
		if (rightNodeToCopy != null) {
			newNode.setRight(copyNode(tree, rightNodeToCopy, newNode, termSet,
					functSet));
		}

		return newNode;
	}

	/**
	 * Method for creating a node.
	 * 
	 * @param newParent
	 *            - parent of node
	 * @param newValue
	 *            - value of node
	 * @param newPointer
	 *            - pointer of node - RIGHT or LEFT
	 * @param newType
	 *            - type of node - OPERAND or OPERATOR or VARIABLE
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public Node(final Node newParent, final String newValue,
			final String newPointer, final String newType)
			throws GeneticProgrammingException {
		super();
		this.type = newType;
		this.value = newValue;
		if (newParent == null && newPointer == null) {
			this.isNodeRoot = true;
			this.level = 0;
		} else if (newPointer.equals(Node.LEFT)
				|| newPointer.equals(Node.RIGHT)) {
			this.pointer = newPointer;
			this.parent = newParent;
			this.isNodeRoot = false;
			newParent.setChild(this);
		} else {
			throw new GeneticProgrammingException("Invalid node inputs");
		}
	}

	/**
	 * Gets the left node.
	 * 
	 * @return left node
	 */
	public final Node getLeft() {
		return this.left;
	}

	/**
	 * Gets the nodes level.
	 * 
	 * @return level of node
	 */
	public final int getLevel() {
		return this.level;
	}

	/**
	 * Gets the nodes parent node.
	 * 
	 * @return parent of node
	 */
	public final Node getParent() {
		return this.parent;
	}

	/**
	 * Gets node pointer - right or left.
	 * 
	 * @return node pointer
	 */
	public final String getPointer() {
		return this.pointer;
	}

	/**
	 * Gets right node.
	 * 
	 * @return right node
	 */
	public final Node getRight() {
		return this.right;
	}

	/**
	 * Tree that node is in.
	 * 
	 * @return tree that node is in
	 */
	public final Tree getTree() {
		return this.tree;
	}

	/**
	 * Type of node.
	 * 
	 * @return type of node
	 */
	public final String getType() {
		return this.type;
	}

	/**
	 * Value of node.
	 * 
	 * @return value of node
	 */
	public final String getValue() {
		return this.value;
	}

	/**
	 * If node is a root node.
	 * 
	 * @return true or false
	 */
	public final boolean isRoot() {
		return this.isNodeRoot;
	}

	/**
	 * Helper method that will print the node info.
	 */
	public final void printNodeInfo() {
		GP_LOGGER.info("-------------" + this + "------------- ");
		GP_LOGGER.info("Get tree: " + getTree());
		GP_LOGGER.info("Is Root?: " + isRoot());
		GP_LOGGER.info("Level: " + getLevel());
		GP_LOGGER.info("Pointer: " + getPointer());
		GP_LOGGER.info("Type: " + getType());
		GP_LOGGER.info("Parent: " + getParent());
		GP_LOGGER.info("Node: " + this);
		GP_LOGGER.info("Left Node: " + getLeft());
		GP_LOGGER.info("Right Node: " + getRight());
		GP_LOGGER.info("Value: " + getValue());
		GP_LOGGER.info("-------------END------------- ");
	}

	/**
	 * Sets the child node of this node.
	 * 
	 * @param newNode
	 *            - nodes child
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final void setChild(final Node newNode)
			throws GeneticProgrammingException {
		if (newNode.getPointer() == Node.LEFT) {
			setLeft(newNode);
		} else if (newNode.getPointer() == Node.RIGHT) {
			setRight(newNode);
		} else {
			throw new GeneticProgrammingException("No pointer set on node");
		}
	}

	/**
	 * Set the nodes left node.
	 * 
	 * @param newLeft
	 *            - left node
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final void setLeft(final Node newLeft)
			throws GeneticProgrammingException {
		if (newLeft.isRoot()) {
			GP_LOGGER.error("Root can not be set to left node");
			printNodeInfo();
			newLeft.printNodeInfo();
			throw new GeneticProgrammingException(
					"Root can not be set to left node");
		}
		this.left = newLeft;
	}

	/**
	 * Sets the nodes level.
	 * 
	 * @param newLevel
	 *            - node level
	 */
	public final void setLevel(final int newLevel) {
		this.level = newLevel;
	}

	/**
	 * Sets the nodes parent node.
	 * 
	 * @param newParent
	 *            - node parent
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final void setParent(final Node newParent)
			throws GeneticProgrammingException {
		if (isNodeRoot) {
			GP_LOGGER.error("This node is a root and can "
					+ "not have a parent assigned to it.");
			printNodeInfo();
			GP_LOGGER.error("The parent looks like this:");
			newParent.printNodeInfo();
			throw new GeneticProgrammingException(
					"This node is a root and can not have a parent assigned to it.");
		}
		if (this == newParent) {
			GP_LOGGER.error("Error:  Tried to set the "
					+ "nodes parent to itself ex node.setPartent(node)");
			printNodeInfo();
			GP_LOGGER.error("The parent looks like this:");
			newParent.printNodeInfo();
			throw new GeneticProgrammingException(
					"Error:  Tried to set the nodes "
							+ "parent to itself ex node.setPartent(node)");
		}
		this.parent = newParent;

	}

	/**
	 * Sets the nodes pointer - right or left.
	 * 
	 * @param newPointer
	 *            - node pointer
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final void setPointer(final String newPointer)
			throws GeneticProgrammingException {
		if (newPointer.equals(Node.LEFT) || newPointer.equals(Node.RIGHT)) {
			this.pointer = newPointer;
		} else {
			GP_LOGGER.error("Invalid pointer must be RIGHT or LEFT");
			printNodeInfo();
			throw new GeneticProgrammingException(
					"Invalid pointer must be RIGHT or LEFT");
		}

	}

	/**
	 * Sets the nodes right node.
	 * 
	 * @param newRight
	 *            - set right node
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final void setRight(final Node newRight)
			throws GeneticProgrammingException {
		if (newRight.isRoot()) {
			GP_LOGGER.error("Root can not be set to right node");
			printNodeInfo();
			newRight.printNodeInfo();
			throw new GeneticProgrammingException(
					"Root can not be set to right node");
		}
		this.right = newRight;

	}

	/**
	 * Sets the node node to a root node.
	 * 
	 * @param newIsRoot
	 *            - Set root flag
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final void setRoot(final boolean newIsRoot)
			throws GeneticProgrammingException {
		this.isNodeRoot = newIsRoot;
	}

	/**
	 * Sets the nodes tree.
	 * 
	 * @param newTree
	 *            - Tree the node is part of
	 */
	public final void setTree(final Tree newTree) {
		this.tree = newTree;
	}

	/**
	 * Sets the node type - operand, operator or variable.
	 * 
	 * @param newType
	 *            - node type
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final void setType(final String newType)
			throws GeneticProgrammingException {
		if (newType.equals(Node.OPERAND) || newType.equals(Node.OPERATOR)
				|| newType.equals(Node.VARIABLE)) {
			this.type = newType;
		} else {
			throw new GeneticProgrammingException(
					"Invalid type must be OPERAND, OPERATOR or VARIABLE");
		}

	}

	/**
	 * Sets the nodes value.
	 * 
	 * @param newValue
	 *            - node value
	 */
	public final void setValue(final String newValue) {
		this.value = newValue;
	}

}
