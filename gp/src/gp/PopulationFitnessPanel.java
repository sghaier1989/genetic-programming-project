package gp;

import java.util.List;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * A chart panel that displays the fitness distribution in a pie chart.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class PopulationFitnessPanel extends ChartPanel {

	/**
	 * The Logger.
	 */
	private static final Logger GP_LOGGER = Logger
			.getLogger(PopulationFitnessPanel.class);
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 33356472237112622L;

	/**
	 * The trees.
	 */
	private List<Tree> trees = null;
	/**
	 * The training data.
	 */
	private int[] dataset = null;
	/**
	 * The target values.
	 */
	private double[] targetTreeValues;

	/**
	 * Constructor method to set the chart.
	 * 
	 * @param chart
	 *            - the chart to display
	 */
	public PopulationFitnessPanel(final JFreeChart chart) {
		super(chart);
	}

	/**
	 * Creates a pie chart.
	 * 
	 * @param newDataset
	 *            the data for the chart.
	 * 
	 * @return JFreeChart The chart
	 */
	public final JFreeChart createChart(final DefaultPieDataset newDataset) {
		final JFreeChart chart = ChartFactory.createPieChart(
				"Population Fitness", newDataset, true, true, false);

		return chart;
	}

	/**
	 * method for populating the pie chart with data.
	 * 
	 * @return DefaultPieDataset pie chart
	 * @throws GeneticProgrammingException
	 *             - something went wrong
	 */
	public final DefaultPieDataset createDataset()
			throws GeneticProgrammingException {
		final DefaultPieDataset newDataset = new DefaultPieDataset();

		int a50 = 0;
		int b100 = 0;
		int d10000 = 0;
		int f1000000 = 0;
		int h100000000 = 0;
		int everythingElse = 0;
		if (getTrees() != null) {
			final int size = getTrees().size();
			for (int x = 0; x < size; x++) {
				final double fit = getTrees().get(x).getFitness();
				if (fit <= 50) {
					a50++;
				} else if (fit <= 100) {
					b100++;
				} else if (fit <= 10000) {
					d10000++;
				} else if (fit <= 1000000) {
					f1000000++;
				} else if (fit <= 100000000) {
					h100000000++;
				} else {
					everythingElse++;
				}
			}
			GP_LOGGER.debug("Distribution is: " + a50 + " " + b100 + " "
					+ d10000 + " " + f1000000 + " " + h100000000 + " "
					+ everythingElse);
			if (a50 != 0) {
				newDataset.setValue("<=50", 50);
			}
			if (b100 != 0) {
				newDataset.setValue("<=100", b100);
			}
			if (d10000 != 0) {
				newDataset.setValue("<=10000", d10000);
			}
			if (f1000000 != 0) {
				newDataset.setValue("<=1000000", f1000000);
			}
			if (h100000000 != 0) {
				newDataset.setValue("<=100000000", h100000000);
			}
			if (everythingElse != 0) {
				newDataset.setValue(">1000000000", everythingElse);
			}
		}
		return newDataset;
	}

	/**
	 * Method for getting trees that the chart uses to generate its data points.
	 * 
	 * @return ArrayList<Tree> list of trees
	 */
	private List<Tree> getTrees() {
		return trees;
	}

	/**
	 * This method sets that array of target equation values.
	 * 
	 * @param newTargetTreeVal
	 *            array of values for target equation
	 */
	public final void setTargetTreeValues(final double[] newTargetTreeVal) {
		this.targetTreeValues = newTargetTreeVal;
	}

	/**
	 * Method for setting trees that the chart uses to generate its data points.
	 * 
	 * @param newTrees
	 *            - ArrayList of trees to plot
	 */
	public final void setTrees(final List<Tree> newTrees) {
		trees = newTrees;
	}

	/**
	 * Method for setting the training data.
	 * 
	 * @param newDataset
	 *            - training data
	 */
	public final void setDataset(final int[] newDataset) {
		this.dataset = newDataset;
	}
}
