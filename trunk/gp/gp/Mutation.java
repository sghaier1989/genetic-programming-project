package gp;

import java.util.ArrayList;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Description: A class file that can change the value of a random node. Will
 * only replace a node with an equivalent type (Replace a terminal set value
 * with a terminal set value. Replace functional set value with a functional set
 * value )
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public final class Mutation {
	private static Logger logger = Logger.getLogger(Mutation.class);

	private static void mutateTree(Tree newTree) {
		try {
			ArrayList<Node> nodes = newTree.getNodes();
			int size = nodes.size();
			Random randomGenerator = new Random();
			int random = randomGenerator.nextInt(size);
			Node randomNode = nodes.get(random);
			String type = randomNode.getType();
			if (randomNode.getType() == Node.OPERAND) {
				randomNode.setValue(newTree.getTerminalSet().randomOperand());
			} else if (type == Node.OPERATOR) {
				randomNode
						.setValue(newTree.getFunctionalSet().randomOperator());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void mutateTrees(ArrayList<Tree> newTrees,
			double newMutationRate) {
		logger.debug("Performing mutation on trees");
		try {
			Random randomGenerator = new Random();
			int size = newTrees.size();
			int numberToMutate = (int) Math.ceil(newMutationRate * size);
			logger.debug("numberToMutate " + numberToMutate);
			Tree treeToMutate = null;
			for (int x = 0; x <= numberToMutate; x++) {
				int randomInt = randomGenerator.nextInt(numberToMutate);
				logger.debug("BEFORE MUTATION "
						+ newTrees.get(randomInt).getEquation());
				treeToMutate = newTrees.get(randomInt);
				// make a copy of the tree before mutation
				newTrees.add(treeToMutate);
				mutateTree(treeToMutate);
				logger.debug("AFTER MUTATION "
						+ newTrees.get(randomInt).getEquation());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setRandomNodeToValue(Tree newTree, String newValue,
			String newType) {
		try {
			Node node = null;
			Random randomGenerator = new Random();
			if (newType == Node.OPERAND) {
				int randomInt = randomGenerator.nextInt(newTree.getOperands()
						.size());
				node = newTree.getOperandAt(randomInt);
			} else if (newType == Node.OPERATOR) {
				int randomInt = randomGenerator.nextInt(newTree.getOperators()
						.size());
				node = newTree.getOperatorAt(randomInt);
			}
			node.setValue(newValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
