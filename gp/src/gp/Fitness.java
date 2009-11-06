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

	public static double checkFitness(double[] newTargetTreeValues,
			Tree newPopulationTree, int[] newDataset) throws Exception {
		try {
			double totalFit = 0;
			double pv = 1.79769E+308;
			int datasetSize = newDataset.length;
			for (int x = 0; x < datasetSize; x++) {
				// make sure the tree has an X if not mutate the tree and put
				// one in randomly.
				if (newPopulationTree.getEquation().indexOf("x") < 0) {
					Mutation.setRandomNodeToValue(newPopulationTree, "x",
							Node.OPERAND);
				}
				try {
					String eq = newPopulationTree.getEquation().toString();

					Expression exp = ExpressionTree.parse(eq);
					VarMap vm = new VarMap(false /* case sensitive */);
					vm.setValue("x", newDataset[x]);
					pv = exp.eval(vm, null);

				} catch (Exception e) {
					e.printStackTrace();
					return Double.NaN;
				}
				if (Double.isInfinite(pv) || Double.isNaN(pv)) {
					return 999999999;
				}

				totalFit = totalFit
						+ Math.pow((newTargetTreeValues[x] - pv), 2);

			}
			newPopulationTree.setFitness(totalFit);
			logger.debug(" Tree = " + newPopulationTree.getEquation()
					+ " Fitness = " + newPopulationTree.getFitness());
			return totalFit;
		} catch (Exception e) {
			throw e;
		}

	}

}
