package gp;

import java.util.ArrayList;

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

	private static final long serialVersionUID = 33356472237112622L;

	private ArrayList<Tree> Trees = null;

	public PopulationFitnessPanel(JFreeChart chart) {
		super(chart);
	}

	/**
	 * Creates a pie chart.
	 * 
	 * @param dataset
	 *            the data for the chart.
	 * 
	 * @return JFreeChart.
	 */
	JFreeChart createChart(final DefaultPieDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createPieChart(
				"Population Fitness", dataset, true, true, false);

		return chart;
	}

	/**
	 * method for populating the pie chart with data.
	 * 
	 * @return DefaultPieDataset
	 */
	DefaultPieDataset createDataset() {
		DefaultPieDataset dataset = new DefaultPieDataset();

		try {
			int a = 0;
			int b = 0;
			int c = 0;
			int d = 0;
			int e = 0;
			int f = 0;
			int g = 0;
			int h = 0;
			int i = 0;
			int j = 0;
			if (getTrees() != null) {
				for (int x = 0; x < getTrees().size(); x++) {
					Tree tr = getTrees().get(x);
					double fit = tr.getFitness();
					if (fit <= 50) {
						a++;
					} else if (fit <= 100) {
						b++;
					} else if (fit <= 1000) {
						c++;
					} else if (fit <= 10000) {
						d++;
					} else if (fit <= 100000) {
						e++;
					} else if (fit <= 1000000) {
						f++;
					} else if (fit <= 10000000) {
						g++;
					} else if (fit <= 100000000) {
						h++;
					} else if (fit <= 1000000000) {
						i++;
					} else {
						j++;
					}
				}
				dataset.setValue("<=50", a);
				dataset.setValue("<=100", b);
				dataset.setValue("<=1000", c);
				dataset.setValue("<=10000", d);
				dataset.setValue("<=100000", e);
				dataset.setValue("<=1000000", f);
				dataset.setValue("<=10000000", g);
				dataset.setValue("<=100000000", h);
				dataset.setValue("<=1000000000", i);
				dataset.setValue(">1000000000", j);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataset;

	}

	/**
	 * method for getting trees that the chart uses to generate its data points
	 * 
	 * @return ArrayList<Tree>
	 */
	public ArrayList<Tree> getTrees() {
		return Trees;
	}

	/**
	 * method for setting trees that the chart uses to generate its data points
	 * 
	 * @param trees
	 *            - ArrayList of trees to plot
	 */
	public void setTrees(ArrayList<Tree> trees) {
		Trees = trees;
	}

}
