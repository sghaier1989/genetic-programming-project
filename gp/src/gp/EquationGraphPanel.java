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

	private static final long serialVersionUID = -8263631782800794519L;
	private String targetEquation = null;
	private int[] trainingData = null;
	private Tree tree = null;

	/**
	 * Constructor class the builds a JPanel.
	 */
	public EquationGraphPanel(JFreeChart chart) {
		super(chart);
	}

	/**
	 * method for creating the chart.
	 * 
	 * @param dataset
	 *            the data for the chart.
	 * 
	 * @return JFreeChart
	 */
	public JFreeChart createChart(final XYDataset dataset) {
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
	 * @return XYDataset.
	 */
	public XYDataset createDataset() {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		if (getTrainingData() != null && getTree() != null) {
			try {
				final XYSeries series1 = new XYSeries(getTree().getEquation()
						.toString());
				final XYSeries series2 = new XYSeries(getTargetEquation());
				int datasetSize = getTrainingData().length;
				for (int x = 0; x < datasetSize; x++) {
					String eq = getTree().getEquation().toString();
					Expression exp = ExpressionTree.parse(eq);
					VarMap vm = new VarMap(false);
					vm.setValue("x", getTrainingData()[x]);
					int val = (int) exp.eval(vm, null);
					series1.add(getTrainingData()[x], val);
				}
				for (int x = 0; x < datasetSize; x++) {
					Expression exp = ExpressionTree.parse(getTargetEquation());
					VarMap vm = new VarMap(false);
					vm.setValue("x", getTrainingData()[x]);
					int val = (int) exp.eval(vm, null);
					series2.add(getTrainingData()[x], val);
				}
				dataset.addSeries(series1);
				dataset.addSeries(series2);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return dataset;
	}

	/**
	 * method to get the target equation
	 * 
	 */
	private String getTargetEquation() {
		return targetEquation;
	}

	/**
	 * method to get the training data
	 * 
	 * @return int[].
	 */
	private int[] getTrainingData() {
		return trainingData;
	}

	/**
	 * method to get the tree
	 * 
	 * @return Tree.
	 */
	private Tree getTree() {
		return tree;
	}

	/**
	 * method to set the target equation
	 * 
	 */
	public void setTargetEquation(String targetEquation) {
		this.targetEquation = targetEquation;
	}

	/**
	 * method to set the training data
	 * 
	 */
	public void setTrainingData(int[] trainingData) {
		this.trainingData = trainingData;
	}

	/**
	 * method to set the tree
	 * 
	 */
	public void setTree(Tree tree) {
		this.tree = tree;
	}
}
