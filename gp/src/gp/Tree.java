package gp;

import java.util.ArrayList;
import java.util.Iterator;
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
	/**
	 * 
	 */
	private static final long serialVersionUID = -1310947610682501856L;
	static Logger logger = Logger.getLogger(Tree.class);
	private StringBuffer equation = new StringBuffer();
	private double fitness = 1.79769E+308;
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
		try {
			setFunctionalSet(newFunctionalSet);
			setTerminalSet(newTerminalSet);
			setRoot(newRoot);
			setFitness(1.79769E+308);
			if (!newRoot.isRoot()) {
				logger
						.error("The root node used to create tree is not set to root.");
				newRoot.printNodeInfo();
				new Exception(
						"The root node used to create tree is not set to root.");
			} else {
				addNode(newRoot);
			}
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
		getNodes().add(newNode);
		newNode.setTree(this);
		if (!newNode.isRoot()) {
			newNode.setLevel(newNode.getParent().getLevel() + 1);
		} else {
			newNode.setLevel(0);
		}
		if (newNode.getType() == Node.OPERAND) {
			getOperands().add(newNode);
		} else if (newNode.getType() == Node.OPERATOR) {
			getOperators().add(newNode);
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
		try {
			Tree copy = Node.copyNode(null, getRoot(), null, getTerminalSet(),
					getFunctionalSet(), 0).getTree();
			copy.setFitness(this.getFitness());
			copy.setFunctionalSet(this.getFunctionalSet());
			copy.setTerminalSet(this.getTerminalSet());
			return copy;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Method for comparing two trees
	 * 
	 * @return boolean
	 */
	@Override
	public boolean equals(Object obj) {
		if (toString() == ((Tree) obj).toString()) {
			return true;
		}
		return false;
	}

	private void evaluateTree(Node newParentNode, Node newNode, int newCount)
			throws Exception {
		if (!newNode.isRoot()) {
			newNode.setLevel(newNode.getParent().getLevel() + 1);
		} else {
			newNode.setLevel(0);
		}
		if (newParentNode == newNode) {
			newParentNode.printNodeInfo();
			newNode.printNodeInfo();
			throw new Exception(
					"We have a problem.  The node being evaluated is equal to the parent node");
		}
		if (newCount > 100) {
			logger.error("May be in a loop with the define tree method");
			logger.error("Size of bad tree is: " + getNodes().size());
			Iterator<Node> ir = getNodes().iterator();
			while (ir.hasNext()) {
				System.out.print(ir.next());
			}
			System.exit(0);
		}
		Node leftChild = newNode.getLeft();
		Node rightChild = newNode.getRight();
		if (leftChild != null) {
			evaluateTree(newNode, leftChild, newCount++);
		}
		if (newNode.getPointer() == Node.LEFT
				&& newNode.getType() == Node.OPERAND) {
			equation.append("(");
			equation.append(newNode.getValue());
			addNode(newNode);
		} else if (newNode.getPointer() == Node.RIGHT
				&& newNode.getType() == Node.OPERAND) {
			equation.append(newNode.getValue());
			equation.append(")");
			addNode(newNode);
		} else {
			equation.append(newNode.getValue());
			addNode(newNode);
		}
		if (rightChild != null) {
			evaluateTree(newNode, rightChild, newCount++);
		}
	}

	/**
	 * This is a helper method for getting a random non-root node.
	 * 
	 * @return Node
	 */
	public Node findRandomNonRootOperatorNode(int newCount) throws Exception {
		Node n = null;
		if (newCount > 100) {
			logger
					.error("We may be in a loop in the findRandomNonRootOperatorNode method");
		}
		if (getOperators().size() > 1) {
			Random randomGenerator = new Random();
			int randomInt = randomGenerator.nextInt(getOperators().size());
			n = getOperators().get(randomInt);
			if (n.getParent() == null || n.isRoot()) {
				n = findRandomNonRootOperatorNode(newCount++);
			} else {
				return n;
			}
		} else {
			throw new Exception(
					"There are not enough operators to find a rendom operator node");
		}
		return n;
	}

	/**
	 * This method is the main method for retrieving the tree's equation. It's
	 * currently a very expensive operation because it recalculates the tree. We
	 * added a dirty flag that will reduce the cost by only recalculating when
	 * the tree changes
	 * 
	 * @return tree equation as a stringbuffer.
	 * @throws Exception
	 */
	public StringBuffer getEquation() throws Exception {
		if (this.equation == null) {
			this.equation = new StringBuffer();
		}
		this.equation.delete(0, equation.length());
		getOperators().clear();
		getOperands().clear();
		getNodes().clear();
		try {
			evaluateTree(null, getRoot(), 0);
		} catch (Exception e) {
			logger
					.error("An error occured while generating the tree equation.");
			throw e;
		}
		return this.equation;
	}

	/**
	 * This class gets the tree's fitness. To make sorting easier and to make
	 * fitness more useful we change negative numbers to positive and change non
	 * double values to a very large number.
	 * 
	 * @return double
	 */
	public double getFitness() throws Exception {
		return this.fitness;
	}

	/**
	 * Gets the trees functional set
	 * 
	 * @return FunctionalSet
	 */
	public FunctionalSet getFunctionalSet() {
		return this.functionalSet;
	}

	/*
	 * The height of a tree is the length of the path from the root to the
	 * deepest node in the tree. A (rooted) tree with only a node (the root) has
	 * a height of zero.
	 * 
	 * @return int
	 */
	public int getHeight() {
		Iterator<Node> it = getNodes().iterator();
		int longestLeg = 0;
		while (it.hasNext()) {
			Node n = it.next();
			int h = 0;
			if (n.getType() == Node.OPERAND) {
				h++;
				Node parent = n.getParent();
				while (parent != null) {
					h++;
					parent = parent.getParent();
				}
				if (h > longestLeg) {
					longestLeg = h;
				}
			}
		}
		return longestLeg - 1;
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
		return this.operands.get(newIndex);
	}

	/*
	 * returns a ArrayList of operands
	 * 
	 * @return ArrayList<Node>
	 */
	public ArrayList<Node> getOperands() {
		return this.operands;
	}

	/*
	 * returns an operator node
	 * 
	 * @return Node
	 */
	public Node getOperatorAt(int newIndex) {
		return this.operators.get(newIndex);
	}

	/*
	 * returns a ArrayList of Operands
	 * 
	 * @return ArrayList<Node>
	 */
	public ArrayList<Node> getOperators() {
		return this.operators;
	}

	/*
	 * returns the trees root node
	 * 
	 * @return Node
	 */
	public Node getRoot() throws Exception {
		return this.root;
	}

	/*
	 * returns the trees terminal set
	 * 
	 * @return TerminalSet
	 */
	public TerminalSet getTerminalSet() {
		return this.terminalSet;
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
		try {
			return getEquation().toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}