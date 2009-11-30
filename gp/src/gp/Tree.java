package gp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
	 * serial Version UID.
	 */
	private static final long serialVersionUID = -1310947610682501856L;
	/**
	 * The logger.
	 */
	private static final Logger GP_LOGGER = Logger.getLogger(Tree.class);
	/**
	 * The equation.
	 */
	private StringBuilder equation = new StringBuilder();
	/**
	 * The fitness.
	 */
	private double fitness = 1.79769E+308;
	/**
	 * The functional set.
	 */
	private FunctionalSet functionalSet = new FunctionalSet();
	/**
	 * The nodes.
	 */
	private ArrayList<Node> nodes = new ArrayList<Node>();
	/**
	 * The operands.
	 */
	private ArrayList<Node> operands = new ArrayList<Node>();
	/**
	 * The operators.
	 */
	private ArrayList<Node> operators = new ArrayList<Node>();
	/**
	 * The root node.
	 */
	private Node root = null;
	/**
	 * The terminal set.
	 */
	private TerminalSet terminalSet = new TerminalSet();

	/**
	 * Description - This constructs a new tree with a single root node. It also
	 * defines the terminal and functional set that the tree uses.
	 * 
	 * @param newRoot
	 *            - the root of the tree
	 * @param newTerminalSet
	 *            - the terminal set of the tree
	 * @param newFunctionalSet
	 *            - the functional set of the tree
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public Tree(final Node newRoot, final TerminalSet newTerminalSet,
			final FunctionalSet newFunctionalSet)
			throws GeneticProgrammingException {
		super();
		try {
			functionalSet = newFunctionalSet;
			terminalSet = newTerminalSet;
			root = newRoot;
			fitness = 1.79769E+308;
			if (!newRoot.isRoot()) {
				GP_LOGGER
						.error("The root node used to create tree is not set to root.");
				newRoot.printNodeInfo();
				throw new GeneticProgrammingException(
						"The root node used to create tree is not set to root.");
			}
			nodes.add(newRoot);
			newRoot.setTree(this);
			newRoot.setLevel(0);
			getOperators().add(newRoot);
		} catch (GeneticProgrammingException e) {
			GP_LOGGER
					.error("Error on creating tree while adding root node to tree");
			throw e;
		}

	}

	/**
	 * This is the main method for adding nodes to a tree.
	 * 
	 * @param newNode
	 *            - node to add
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final void addNode(final Node newNode)
			throws GeneticProgrammingException {
		getNodes().add(newNode);
		newNode.setTree(this);
		if (newNode.isRoot()) {
			newNode.setLevel(0);
		} else {
			newNode.setLevel(newNode.getParent().getLevel() + 1);
		}
		if (newNode.getType() == Node.OPERAND) {
			getOperands().add(newNode);
		} else if (newNode.getType() == Node.OPERATOR) {
			getOperators().add(newNode);
		} else {
			throw new GeneticProgrammingException(
					"Error while adding node to tree.  The node type is"
							+ " not an operator or operand.  It is currently set to: "
							+ newNode.getType());
		}
	}

	/**
	 * A method for cloning a tree.
	 * 
	 * @return Tree copy of the tree
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final Tree copyTree() throws GeneticProgrammingException {
		final Tree copy = Node.copyNode(null, getRoot(), null,
				getTerminalSet(), getFunctionalSet()).getTree();
		copy.setFitness(this.getFitness());
		copy.setFunctionalSet(this.getFunctionalSet());
		copy.setTerminalSet(this.getTerminalSet());
		return copy;

	}

	/**
	 * This method evaluates the tress and creates the equation.
	 * 
	 * @param newParentNode
	 *            - parent node
	 * @param newNode
	 *            - node to evaluate
	 * @throws GeneticProgrammingException
	 *             - if something goes wrong
	 */
	private void evaluateTree(final Node newParentNode, final Node newNode)
			throws GeneticProgrammingException {
		if (newNode.isRoot()) {
			newNode.setLevel(0);
		} else {
			newNode.setLevel(newNode.getParent().getLevel() + 1);
		}
		if (newParentNode == newNode) {
			newParentNode.printNodeInfo();
			newNode.printNodeInfo();
			throw new GeneticProgrammingException(
					"We have a problem.  The node "
							+ "being evaluated is equal to the parent node");
		}
		final Node leftChild = newNode.getLeft();
		final Node rightChild = newNode.getRight();
		if (leftChild != null) {
			evaluateTree(newNode, leftChild);
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
			evaluateTree(newNode, rightChild);
		}
	}

	/**
	 * This is a helper method for getting a random non-root node.
	 * 
	 * @return Node - random non-root operator node
	 * @exception GeneticProgrammingException
	 *                - if something goes wrong.
	 */
	public final Node findRandomNonRootOperatorNode()
			throws GeneticProgrammingException {
		Node node = null;
		if (getOperators().size() < 1) {
			throw new GeneticProgrammingException(
					"There are not enough operators to find a rendom operator node");
		}
		final Random randomGenerator = new Random();
		final int randomInt = randomGenerator.nextInt(getOperators().size());
		node = getOperators().get(randomInt);
		if (node.getParent() == null || node.isRoot()) {
			node = findRandomNonRootOperatorNode();
		}
		return node;
	}

	/**
	 * This method is the main method for retrieving the tree's equation. It's
	 * currently a very expensive operation because it recalculates the tree. We
	 * added a dirty flag that will reduce the cost by only recalculating when
	 * the tree changes
	 * 
	 * @return tree equation as a String Builder.
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final StringBuilder getEquation() throws GeneticProgrammingException {
		if (this.equation == null) {
			this.equation = new StringBuilder();
		}
		this.equation.delete(0, equation.length());
		getOperators().clear();
		getOperands().clear();
		getNodes().clear();
		evaluateTree(null, getRoot());
		return this.equation;
	}

	/**
	 * This class gets the tree's fitness. To make sorting easier and to make
	 * fitness more useful we change negative numbers to positive and change non
	 * double values to a very large number.
	 * 
	 * @return double - the trees fitness
	 */
	public final double getFitness() {
		return this.fitness;
	}

	/**
	 * Gets the trees functional set.
	 * 
	 * @return FunctionalSet - Functional set for tree
	 */
	public final FunctionalSet getFunctionalSet() {
		return this.functionalSet;
	}

	/**
	 * The height of a tree is the length of the path from the root to the
	 * deepest node in the tree. A (rooted) tree with only a node (the root) has
	 * a height of zero.
	 * 
	 * @return height of tree
	 */
	public final int getHeight() {
		final Iterator<Node> iterator = getNodes().iterator();
		int longestLeg = 0;
		while (iterator.hasNext()) {
			final Node node = iterator.next();
			int counter = 0;
			if (node.getType() == Node.OPERAND) {
				counter++;
				Node parent = node.getParent();
				while (parent != null) {
					counter++;
					parent = parent.getParent();
				}
				if (counter > longestLeg) {
					longestLeg = counter;
				}
			}
		}
		return longestLeg - 1;
	}

	/**
	 * Returns an ArrayList of the trees nodes.
	 * 
	 * @return list of nodes in tree
	 */
	public final List<Node> getNodes() {
		return this.nodes;
	}

	/**
	 * Returns an operand node.
	 * 
	 * @param newIndex
	 *            - index of operand
	 * @return operand node
	 */
	public final Node getOperandAt(final int newIndex) {
		return this.operands.get(newIndex);
	}

	/**
	 * A list of operands.
	 * 
	 * @return operand nodes
	 */
	public final List<Node> getOperands() {
		return this.operands;
	}

	/**
	 * Returns an operator node.
	 * 
	 * @param newIndex
	 *            - index of operand
	 * @return operator node
	 */
	public final Node getOperatorAt(final int newIndex) {
		return this.operators.get(newIndex);
	}

	/**
	 * Returns a ArrayList of Operands.
	 * 
	 * @return list of operator nodes
	 */
	public final List<Node> getOperators() {
		return this.operators;
	}

	/**
	 * Returns the trees root node.
	 * 
	 * @return Node - root node
	 */
	public final Node getRoot() {
		return this.root;
	}

	/**
	 * Returns the trees terminal set.
	 * 
	 * @return TerminalSet - terminal set
	 */
	public final TerminalSet getTerminalSet() {
		return this.terminalSet;
	}

	/**
	 * Sets the equation.
	 * 
	 * @param newEq
	 *            - The equation
	 */
	public final void setEquation(final StringBuilder newEq) {
		this.equation = newEq;
	}

	/**
	 * Sets the tree fitness.
	 * 
	 * @param newFitness
	 *            - fitness
	 */
	public final void setFitness(final double newFitness) {
		this.fitness = newFitness;
	}

	/**
	 * Sets the tree functional set.
	 * 
	 * @param newFunctionalSet
	 *            - functional set
	 */
	public final void setFunctionalSet(final FunctionalSet newFunctionalSet) {
		this.functionalSet = newFunctionalSet;
	}

	/**
	 * Sets the tree's nodes.
	 * 
	 * @param newNodes
	 *            - list of nodes
	 */
	public final void setNodes(final ArrayList<Node> newNodes) {
		this.nodes = newNodes;
	}

	/**
	 * Sets the trees operands.
	 * 
	 * @param newOperands
	 *            - operands
	 */
	public final void setOperands(final ArrayList<Node> newOperands) {
		this.operands = newOperands;
	}

	/**
	 * Sets the trees operators.
	 * 
	 * @param newOpers
	 *            - operators
	 */
	public final void setOperators(final ArrayList<Node> newOpers) {
		this.operators = newOpers;
	}

	/**
	 * Set the trees root node.
	 * 
	 * @param newRoot
	 *            - root node
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final void setRoot(final Node newRoot)
			throws GeneticProgrammingException {
		if (newRoot.getParent() == null) {
			this.root = newRoot;
		} else {
			GP_LOGGER
					.error("Can not make a root of this node because it has a parent");
			newRoot.printNodeInfo();
			throw new GeneticProgrammingException(
					"Can not make a root of this node because it has a parent");
		}
	}

	/**
	 * Set the terminal set.
	 * 
	 * @param newTerminalSet
	 *            - terminal set
	 */
	public final void setTerminalSet(final TerminalSet newTerminalSet) {
		this.terminalSet = newTerminalSet;

	}
}