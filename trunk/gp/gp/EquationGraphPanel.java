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

public class EquationGraphPanel extends ChartPanel {
	private Color color = null;

	private int[] trainingData = null;
	private Tree tree = null;
	public EquationGraphPanel(JFreeChart chart) {
		super(chart);
		// TODO Auto-generated constructor stub
	}

	/**
	 * Creates a chart.
	 * 
	 * @param dataset
	 *            the data for the chart.
	 * 
	 * @return a chart.
	 */
	JFreeChart createChart(final XYDataset dataset) {

		// create the chart...
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
		// renderer.setSeriesLinesVisible(0, false);
		renderer.setSeriesShapesVisible(0, false);
		renderer.setSeriesShapesVisible(1, false);
		plot.setRenderer(renderer);
		final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
		rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
		return chart;
	}

	XYDataset createDataset() {
		final XYSeriesCollection dataset = new XYSeriesCollection();
		if (trainingData != null && tree != null) {
			try {
				final XYSeries series1 = new XYSeries(tree.getEquation()
						.toString());
				final XYSeries series2 = new XYSeries("(x^2-1)/2");
				int datasetSize = trainingData.length;
				for (int x = 0; x < datasetSize; x++) {
					String eq = tree.getEquation().toString();
					Expression exp = ExpressionTree.parse(eq);
					VarMap vm = new VarMap(false);
					vm.setValue("x", trainingData[x]);
					int val = (int) exp.eval(vm, null);
					series1.add(trainingData[x], val);
				}

				for (int x = 0; x < datasetSize; x++) {
					String eq = "(x^2-1)/2";
					Expression exp = ExpressionTree.parse(eq);
					VarMap vm = new VarMap(false);
					vm.setValue("x", trainingData[x]);
					int val = (int) exp.eval(vm, null);
					series2.add(trainingData[x], val);

				}
				dataset.addSeries(series1);
				dataset.addSeries(series2);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return dataset;

	}

	public Color getColor() {
		return color;
	}

	public int[] getTrainingData() {
		return trainingData;
	}

	public Tree getTree() {
		return tree;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setTrainingData(int[] trainingData) {
		this.trainingData = trainingData;
	}

	public void setTree(Tree tree) {
		this.tree = tree;
	}
}
