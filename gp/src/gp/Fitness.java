package gp;

import org.apache.log4j.Logger;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

/**
 * A class to compare produced outputs with expected results. If a tree is
 * invalid due to math issues like divide by zero or a fitness that is too large
 * to be represented by a java double then this class returns a very large
 * fitness. For example: 1.79769E+308
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public abstract class Fitness {
	/**
	 * Logger.
	 */
	private static final Logger GP_LOGGER = Logger.getLogger(Fitness.class);

	/**
	 * Method for calculation the expression values.
	 * 
	 * @param expression
	 *            - The math expression
	 * @param trainingData
	 *            - The training data
	 * @return - the expression value
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public static double[] calculateExpressionValues(final String expression,
			final int[] trainingData) throws GeneticProgrammingException {
		final int datasetSize = trainingData.length;
		double[] expressionValues = new double[datasetSize];
		if (expression.indexOf('x') < 0) {
			throw new GeneticProgrammingException(
					"Expression does not have a variable.  The expression is: "
							+ expression);
		}
		double value = 1.79769E+308;
		final Expression exp = ExpressionTree.parse(expression);
		final VarMap varMap = new VarMap(false /* case sensitive */);
		for (int x = 0; x < datasetSize; x++) {
			varMap.setValue("x", trainingData[x]);
			value = exp.eval(varMap, null);
			if (Double.isInfinite(value) || Double.isNaN(value)) {
				expressionValues[x] = 1.79769E+308;
			} else {
				expressionValues[x] = value;
			}
		}
		GP_LOGGER.debug("The fitness is: " + expressionValues.toString());
		return expressionValues;
	}

	/**
	 * Method for calculation the expression values.
	 * 
	 * @param expression
	 *            - The math expression
	 * @param trainingData
	 *            - The training data
	 * @return - the expression value
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public static double[] calculateExpressionValues(final Tree expression,
			final int[] trainingData) throws GeneticProgrammingException {
		if (expression.getEquation().toString().indexOf("x") < 0) {
			Mutation.setRandomNodeToValue(expression, "x", Node.OPERAND);
		}
		return calculateExpressionValues(expression.getEquation().toString(),
				trainingData);
	}

	/**
	 * Method for checking the fitness of an expression.
	 * 
	 * @param targetTreeValues
	 *            - Target equations calculation values
	 * @param newPopTreeValues
	 *            - Population equations calculation values
	 * @return - the fitness
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public static double checkFitness(final double[] targetTreeValues,
			final double[] newPopTreeValues) throws GeneticProgrammingException {
		if (targetTreeValues.length != newPopTreeValues.length) {
			throw new GeneticProgrammingException(
					"The number of values in the target tree "
							+ " values and population tree values is not the same ");
		}

		double totalFit = 0;
		final int size = targetTreeValues.length;
		for (int x = 0; x < size; x++) {
			totalFit = totalFit
					+ Math.pow((targetTreeValues[x] - newPopTreeValues[x]), 2);
		}
		GP_LOGGER.debug("Fitness = " + totalFit);
		return totalFit;

	}
}
