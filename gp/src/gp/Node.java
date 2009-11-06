package gp;

import java.util.ArrayList;

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

public class Node {
	public static final String LEFT = "LEFT";

	static Logger logger = Logger.getLogger(GeneticProgramming.class);
	public static final String OPERAND = "OPERAND";
	public static final String OPERATOR = "OPERATOR";
	public static final String RIGHT = "RIGHT";
	public static final String ROOT = "ROOT";
	public static final String VARIABLE = "VARIABLE";
	private boolean isRoot = false;
	private Node left = null;
	private int level = 0;
	private String operand = null;
	private String operator = null;
	private Node parent = null;
	// pointer
	private String pointer = null;
	private Node right = null;

	// types
	private String type = null;
	private String value = null;

	public Node(Node newParent, String newValue, String newPointer,
			String newType) throws Exception {
		this.setValue(newValue);
		this.setType(newType);
		if (newType == Node.OPERAND) {
			this.setOperand(newValue);
		} else if (newType == Node.OPERATOR) {
			this.setOperator(newValue);
		} else {
			throw new Exception("Invalid type ");
		}
		if (newParent == null) {
			this.setRoot(true);
		} else if ((newPointer == LEFT) || (newPointer == RIGHT)) {
			this.setParent(newParent);
			this.setRoot(false);
			if (newPointer == LEFT) {
				this.setPointer(newPointer);
				getParent().setLeft(this);
			} else {
				this.setPointer(newPointer);
				getParent().setRight(this);
			}
		} else {
			throw new Exception(
					"Invalid pointer must be RIGHT, LEFT or parent must be null");
		}

	}

	public Node copyNode(Tree newTree, Node nodeToCopy, Node newNodeParent) {
		Node newNode = null;
		Node newLeft = null;
		Node newRight = null;
		try {
			if (!nodeToCopy.isRoot) {
				newNode = new Node(newNodeParent, nodeToCopy.getValue(),
						nodeToCopy.getPointer(), nodeToCopy.getType());
				newNode.setLevel(nodeToCopy.getLevel());
				newNode.setOperand(nodeToCopy.getOperand());
				newNode.setOperator(nodeToCopy.getOperator());
				newNode.setRoot(nodeToCopy.isRoot());

				newTree.addNode(newNode);
			} else {
				newNode = newTree.getRoot();
			}
			Node leftNodeToCopy = nodeToCopy.getLeft();
			// System.out.println("leftNodeToCopy " + leftNodeToCopy);
			if (leftNodeToCopy != null) {
				newLeft = copyNode(newTree, leftNodeToCopy, newNode);
				newNode.setLeft(newLeft);
			}
			Node rightNodeToCopy = nodeToCopy.getRight();
			// System.out.println("rightNodeToCopy " + rightNodeToCopy);
			if (rightNodeToCopy != null) {
				newRight = copyNode(newTree, rightNodeToCopy, newNode);
				newNode.setRight(newRight);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return newNode;
	}

	private ArrayList<Node> findChildren(Node par, ArrayList<Node> nods) {
		if (par != null) {
			Node l = par.getLeft();
			if (l != null) {
				nods.add(l);
				findChildren(l, nods);
			}
			Node r = par.getRight();
			if (r != null) {
				nods.add(r);
				findChildren(r, nods);
			}
		}
		return nods;

	}

	public ArrayList<Node> getChildren() {
		return findChildren(this, new ArrayList<Node>());
	}

	public Node getLeft() {
		return left;
	}

	public int getLevel() {
		int newLevel = 0;
		Node par = this.getParent();
		while (par != null && newLevel < 100) {
			newLevel++;
			par = par.getParent();
		}
		this.level = newLevel;
		return level;
	}

	public String getOperand() {
		return operand;
	}

	public String getOperator() {
		return operator;
	}

	public Node getParent() {
		return parent;
	}

	public String getPointer() {
		return pointer;
	}

	public Node getRight() {
		return right;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void printNodeInfo() {
		logger.info("-------------" + this + "------------- ");
		logger.info("Is Root?: " + this.isRoot());
		logger.info("Operand: " + this.getOperand());
		logger.info("Operator: " + this.getOperator());
		logger.info("Level: " + this.getLevel());
		logger.info("Pointer: " + this.getPointer());
		logger.info("Type: " + this.getType());
		logger.info("Parent: " + this.getParent());
		logger.info("Node: " + this);
		logger.info("Left Node: " + this.getLeft());
		logger.info("Right Node: " + this.getRight());
		logger.info("Value: " + this.getValue());
		logger.info("-------------END------------- ");
	}

	public void setChild(Node newNode) throws Exception {

		if (newNode.getPointer() == Node.LEFT) {
			this.setLeft(newNode);
		} else if (newNode.getPointer() == Node.RIGHT) {
			this.setRight(newNode);
		} else {
			throw new Exception("No pointer set on node");
		}

	}

	public void setLeft(Node newLeft) throws Exception {
		try {
			if (!newLeft.isRoot()) {
				this.setRoot(false);
				this.left = newLeft;
				this.setPointer(Node.LEFT);
			} else {
				logger.error("Root can not be set to left node");
				this.printNodeInfo();
				new Exception("Root can not be set to left node");
			}
		} catch (Exception e) {
			throw e;
		}

	}

	public void setLevel(int newLevel) {
		this.level = newLevel;
	}

	public void setOperand(String newOperand) {

		this.operand = newOperand;

	}

	public void setOperator(String newOperator) {

		this.operator = newOperator;

	}

	public void setParent(Node newParent) throws Exception {

		if (!this.isRoot) {
			this.parent = newParent;
		} else {
			logger
					.error("This node is a root and can not have a parent assigned to it.");
			printNodeInfo();
			throw new Exception(
					"This node is a root and can not have a parent assigned to it.");
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
				this.setRoot(false);
				this.setPointer(Node.RIGHT);
				this.right = newRight;
			} else {
				logger.error("Root can not be set to right node");
				this.printNodeInfo();
				new Exception("Root can not be set to right node");
			}
		} catch (Exception e) {
			throw e;
		}

	}

	public void setRoot(boolean newIsRoot) throws Exception {

		if (newIsRoot && this.getParent() != null) {
			logger
					.error("Can not make a root of this node because it has a parent");
			printNodeInfo();
			throw new Exception(
					"Can not make a root of this node because it has a parent");
		} else {
			this.isRoot = newIsRoot;
		}
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
