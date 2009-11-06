package gp;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * 
 * A class file that represents a binary tree A complete binary tree is a binary
 * tree in which every level, except possibly the last, is completely filled,
 * and all nodes are as far left as possible. A directed edge refers to the link
 * from the parent to the child (the arrows in the picture of the tree). The
 * root node of a tree is the node with no parents. There is at most one root
 * node in a rooted tree. A leaf node has no children. The depth of a node n is
 * the length of the path from the root to the node. The set of all nodes at a
 * given depth is sometimes called a level of the tree. The root node is at
 * depth zero. The height of a tree is the length of the path from the root to
 * the deepest node in the tree. A (rooted) tree with only a node (the root) has
 * a height of zero. Siblings are nodes that share the same parent node. If a
 * path exists from node p to node q, where node p is closer to the root node
 * than q, then p is an ancestor of q and q is a descendant of p. In-degree of a
 * node is the number of edges arriving at that node. Out-degree of a node is
 * the number of edges leaving that node. Root is the only node in the tree with
 * In-degree = 0
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class Tree {
	static Logger logger = Logger.getLogger(Tree.class);
	private StringBuffer equation = new StringBuffer();
	private double fitness = 999999999;
	private FunctionalSet functionalSet = new FunctionalSet();
	private ArrayList<Node> nodes = new ArrayList<Node>();
	private ArrayList<Node> operands = new ArrayList<Node>();
	private ArrayList<Node> operators = new ArrayList<Node>();
	private Node root = null;
	private TerminalSet terminalSet = new TerminalSet();

	/**
	 * Description - This constructs a new tree with a single root node. It also
	 * defines the terminal and functional set that the tree uses.
	 * 
	 * @param newRoot
	 * @param newTerminalSet
	 * @param newFunctionalSet
	 */
	public Tree(Node newRoot, TerminalSet newTerminalSet,
			FunctionalSet newFunctionalSet) throws Exception {
		this.setFunctionalSet(newFunctionalSet);
		this.setTerminalSet(newTerminalSet);
		this.setRoot(newRoot);
		this.setFitness(this.fitness);
		try {
			if (!newRoot.isRoot()) {
				logger
						.error("The root node used to create tree is not set to root.");
				newRoot.printNodeInfo();
				new Exception(
						"The root node used to create tree is not set to root.");
			}
			this.addNode(newRoot);
		} catch (Exception e) {
			logger
					.error("Error on creating tree while adding root node to tree");
			throw e;
		}

	}

	/**
	 * This is the main method for adding nodes to a tree.
	 * 
	 * @param newNode
	 * @throws Exception
	 */
	public void addNode(Node newNode) throws Exception {
		this.getNodes().add(newNode);
		if (newNode.getType() == Node.OPERAND) {
			this.getOperands().add(newNode);
		} else if (newNode.getType() == Node.OPERATOR) {
			this.getOperators().add(newNode);
		} else {
			new Exception(
					"Error while adding node to tree.  The node type is not an operator or operand.  It is currently set to: "
							+ newNode.getType());
		}

	}

	/**
	 * A method for cloning a tree
	 * 
	 * @return Tree
	 */
	public Tree copyTree() {
		Tree newTree = null;
		try {
			Node oldRoot = this.getRoot();
			Node newRootNode = new Node(null, oldRoot.getValue(), oldRoot
					.getPointer(), oldRoot.getType());
			newRootNode.setLevel(oldRoot.getLevel());
			newRootNode.setOperand(oldRoot.getOperand());
			newRootNode.setOperator(oldRoot.getOperator());
			newRootNode.setRoot(true);
			newTree = new Tree(newRootNode, this.getTerminalSet(), this
					.getFunctionalSet());
			oldRoot.copyNode(newTree, oldRoot, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newTree;
	}

	/**
	 * Method for comparing two trees
	 * 
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		Tree other = (Tree) obj;
		if (this.equation.toString() == other.equation.toString()) {
			return true;
		}
		return false;
	}

	/**
	 * This is a helper method for getting a random non-root node.
	 * 
	 * @return Node
	 */
	public Node findRandomNonRootNode() {
		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(getOperators().size() - 1) + 1;
		Node n = getOperatorAt(randomInt);
		if (n.getParent() == null || n.isRoot()) {
			n.printNodeInfo();
			findRandomNonRootNode();

		} else {
			return n;
		}
		return null;
	}

	/**
	 * This method is the main method for retrieving the tree's equation.
	 * 
	 * @return tree equation as a stringbuffer.
	 * @throws Exception
	 */
	public StringBuffer getEquation() throws Exception {
		equation.delete(0, equation.length());
		getOperators().clear();
		getOperands().clear();
		getNodes().clear();
		try {

			printTree(this.getRoot(), 0);

		} catch (Exception e) {
			logger
					.error("An error occured while generating the tree equation.");
			throw e;
		}

		return equation;
	}

	/**
	 * This class gets the tree's fitness. To make sorting easier and to make
	 * fitness more useful we change negative numbers to positive and change non
	 * double values to a very large number.
	 * 
	 * @return double
	 */
	public double getFitness() {
		if (Double.isNaN(fitness) || Double.isInfinite(fitness)) {
			fitness = 999999999;
		}
		if (fitness < 0) {
			fitness = fitness * -1;
		}
		return fitness;
	}

	/**
	 * Gets the trees functional set
	 * 
	 * @return FunctionalSet
	 */
	public FunctionalSet getFunctionalSet() {
		return functionalSet;
	}

	/*
	 * The height of a tree is the length of the path from the root to the
	 * deepest node in the tree. A (rooted) tree with only a node (the root) has
	 * a height of zero.
	 * 
	 * @return int
	 */
	public int getHeight() {
		return getHeight(nodes.size());
	}

	/*
	 * The height of a tree is the length of the path from the root to the
	 * deepest node in the tree. A (rooted) tree with only a node (the root) has
	 * a height of zero.
	 * 
	 * @return int
	 */
	private int getHeight(int newNumberOfNodes) {
		return (int) Math.log(newNumberOfNodes + 1) - 1;
	}

	/*
	 * returns an ArrayList of the trees' nodes
	 * 
	 * @return ArrayList<Node>
	 */
	public ArrayList<Node> getNodes() {
		return this.nodes;
	}

	/*
	 * returns an operand node
	 * 
	 * @return Node
	 */
	public Node getOperandAt(int newIndex) {
		return operands.get(newIndex);
	}

	/*
	 * returns a ArrayList of operands
	 * 
	 * @return ArrayList<Node>
	 */
	public ArrayList<Node> getOperands() {
		return operands;
	}

	/*
	 * returns an operator node
	 * 
	 * @return Node
	 */
	public Node getOperatorAt(int newIndex) {
		return operators.get(newIndex);
	}

	/*
	 * returns a ArrayList of Operands
	 * 
	 * @return ArrayList<Node>
	 */
	public ArrayList<Node> getOperators() {
		return operators;
	}

	/*
	 * returns the trees root node
	 * 
	 * @return Node
	 */
	public Node getRoot() throws Exception {
		try {
			root.setRoot(true);
		} catch (Exception e) {
			throw e;
		}
		return root;
	}

	/*
	 * returns the trees terminal set
	 * 
	 * @return TerminalSet
	 */
	public TerminalSet getTerminalSet() {
		return terminalSet;
	}

	private void printTree(Node newNode, int newCount) throws Exception {
		Node leftChild = null;
		Node rightChild = null;
		leftChild = newNode.getLeft();
		rightChild = newNode.getRight();
		if (leftChild != null) {
			printTree(leftChild, newCount++);
		}
		if (newNode.getPointer() == Node.LEFT
				&& newNode.getType() == Node.OPERAND) {
			equation.append("(");
			equation.append(newNode.getValue());
			getOperands().add(newNode);
		} else if (newNode.getPointer() == Node.RIGHT
				&& newNode.getType() == Node.OPERAND) {
			equation.append(newNode.getValue());
			equation.append(")");
			getOperators().add(newNode);
		} else {
			equation.append(newNode.getValue());
			getNodes().add(newNode);
		}
		if (rightChild != null) {
			printTree(rightChild, newCount++);
		}
	}

	public void setFitness(double newFitness) {
		this.fitness = newFitness;
	}

	public void setFunctionalSet(FunctionalSet newFunctionalSet) {
		this.functionalSet = newFunctionalSet;
	}

	public void setNodes(ArrayList<Node> newNodes) {

		this.nodes = newNodes;

	}

	public void setOperands(ArrayList<Node> newOperands) {

		this.operands = newOperands;

	}

	public void setOperators(ArrayList<Node> newOperators) {

		this.operators = newOperators;

	}

	public void setRoot(Node newRoot) throws Exception {

		if (newRoot.getParent() == null) {
			this.root = newRoot;
		} else {
			logger
					.error("Can not make a root of this node because it has a parent");
			newRoot.printNodeInfo();
			throw new Exception(
					"Can not make a root of this node because it has a parent");
		}

	}

	public void setTerminalSet(TerminalSet newTerminalSet) {
		this.terminalSet = newTerminalSet;

	}

	@Override
	public String toString() {
		return "Tree [equation=" + equation + "]";
	}
}