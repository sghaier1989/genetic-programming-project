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
public final class Fitness {
	static Logger logger = Logger.getLogger(Fitness.class);

	public static double[] calculateExpressionValues(String expression,
			int[] trainingData) throws Exception {
		int datasetSize = trainingData.length;
		double[] expressionValues = new double[datasetSize];
		if (expression.indexOf("x") < 0) {
			throw new Exception(
					"Expression does not have a variable.  The expression is: "
							+ expression);
		}
		double pv = 1.79769E+308;
		for (int x = 0; x < datasetSize; x++) {
			try {
				Expression exp = ExpressionTree.parse(expression);
				VarMap vm = new VarMap(false /* case sensitive */);
				vm.setValue("x", trainingData[x]);
				pv = exp.eval(vm, null);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("The bad equatoin is: " + expression);
				expressionValues[x] = Double.NaN;
			}
			if (Double.isInfinite(pv) || Double.isNaN(pv)) {
				expressionValues[x] = 1.79769E+308;
			} else {
				expressionValues[x] = pv;
			}
		}
		return expressionValues;
	}

	public static double[] calculateExpressionValues(Tree expression,
			int[] trainingData) throws Exception {
		if (expression.toString().indexOf("x") < 0) {
			Mutation.setRandomNodeToValue(expression, "x", Node.OPERAND);
		}
		try {
			return calculateExpressionValues(expression.toString(),
					trainingData);
		} catch (Exception e) {
			throw e;
		}
	}

	public static double checkFitness(double[] newTargetTreeValues,
			double[] newPopulationTreeValues) throws Exception {
		if (newTargetTreeValues.length != newPopulationTreeValues.length) {
			new Exception(
					"The number of values in the target tree values and population tree values is not the same ");
		}
		try {
			double totalFit = 0;
			int size = newTargetTreeValues.length;
			for (int x = 0; x < size; x++) {
				totalFit = totalFit
						+ Math
								.pow(
										(newTargetTreeValues[x] - newPopulationTreeValues[x]),
										2);
			}
			logger.debug("Fitness = " + totalFit);
			return totalFit;
		} catch (Exception e) {
			throw e;
		}
	}
}
