package gp;

import java.awt.Color;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

/**
 * This class represents a JPanel with a XY chart that compares the target
 * equation with the evolved equation.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class EquationGraphPanel extends ChartPanel {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -8263631782800794519L;
	/**
	 * Target equation.
	 */
	private String targetEquation = null;
	/**
	 * Training data.
	 */
	private int[] trainingData = null;
	/**
	 * Tree value.
	 */
	private Tree tree = null;

	/**
	 * Constructor class the builds a JPanel.
	 * 
	 * @param chart
	 *            - chart object.
	 */
	public EquationGraphPanel(final JFreeChart chart) {
		super(chart);
	}

	/**
	 * Method for creating the chart.
	 * 
	 * @param dataset
	 *            the data for the chart.
	 * 
	 * @return JFreeChart - XY chart of equation
	 */
	public final JFreeChart createChart(final XYDataset dataset) {
		final JFreeChart chart = ChartFactory.createXYLineChart(

		"Equation Graph", // chart
				// title
				"X", // x axis label
				"Y", // y axis label
				dataset, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);
		chart.setBackgroundPaint(Color.white);
		final XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.lightGray);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesShapesVisible(1, false);
		plot.setRenderer(renderer);
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return chart;
	}

	/**
	 * Populates graph with data.
	 * 
	 * @return XYDataset - XY data set
	 * @exception GeneticProgrammingException
	 *                - if something goes wrong
	 */
	public final XYDataset createDataset() throws GeneticProgrammingException {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		if (getTrainingData() != null && getTree() != null) {
			final XYSeries series1 = new XYSeries(getTree().getEquation()
					.toString());
			final XYSeries series2 = new XYSeries(getTargetEquation());
			final int datasetSize = getTrainingData().length;
			final String equation = getTree().getEquation().toString();
			Expression exp = ExpressionTree.parse(equation);
			final VarMap variableMap = new VarMap(false);
			for (int x = 0; x < datasetSize; x++) {
				variableMap.setValue("x", getTrainingData()[x]);
				series1.add(getTrainingData()[x], (int) exp.eval(variableMap,
						null));
			}
			exp = ExpressionTree.parse(getTargetEquation());
			for (int x = 0; x < datasetSize; x++) {
				variableMap.setValue("x", getTrainingData()[x]);
				series2.add(getTrainingData()[x], (int) exp.eval(variableMap,
						null));
			}
			dataset.addSeries(series1);
			dataset.addSeries(series2);
		}
		return dataset;
	}

	/**
	 * method to get the target equation.
	 * 
	 * @return - the tree equation
	 * 
	 */
	private String getTargetEquation() {
		return targetEquation;
	}

	/**
	 * method to get the training data.
	 * 
	 * @return training data
	 */
	private int[] getTrainingData() {
		return trainingData;
	}

	/**
	 * method to get the tree.
	 * 
	 * @return Tree.
	 */
	private Tree getTree() {
		return tree;
	}

	/**
	 * Method to set the target equation.
	 * 
	 * @param newTargetEq
	 *            - The target equation.
	 */
	public final void setTargetEquation(final String newTargetEq) {
		this.targetEquation = newTargetEq;
	}

	/**
	 * Method to set the training data.
	 * 
	 * @param newTrainingData
	 *            - The training data.
	 */
	public final void setTrainingData(final int[] newTrainingData) {
		this.trainingData = newTrainingData;
	}

	/**
	 * method to set the tree.
	 * 
	 * @param newTree
	 *            - The tree.
	 */
	public final void setTree(final Tree newTree) {
		this.tree = newTree;
	}
}
