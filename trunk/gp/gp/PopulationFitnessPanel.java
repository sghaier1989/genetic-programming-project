package gp;

import java.awt.Color;
import java.util.ArrayList;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class PopulationFitnessPanel extends ChartPanel {
	private Color color = null;

	private ArrayList<Tree> Trees = null;
	public PopulationFitnessPanel(JFreeChart chart) {
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
	JFreeChart createChart(final DefaultPieDataset dataset) {

		// create the chart...
		final JFreeChart chart = ChartFactory.createPieChart(
				"Population Fitness",dataset, true, true, false);
	
		return chart;
	}

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
					}else if (fit <= 100) {
							b++;
					}else if (fit <= 1000) {
							c++;
					}else if (fit <= 10000) {
							d++;
					}else if (fit <= 100000) {
							e++;
					}else if (fit <= 1000000) {
							f++; 
					}else if (fit <= 10000000) {
							g++; 
					}else if (fit <= 100000000) {
							h++; 
					}else if (fit <= 1000000000) {
							i++; 
					}else  {
						j++;
					}
				}
				dataset.setValue("<=50",a   );
				dataset.setValue("<=100", b  );
				dataset.setValue("<=1000", c  );
				dataset.setValue("<=10000", d  );
				dataset.setValue("<=100000", e  );
				dataset.setValue("<=1000000", f  );
				dataset.setValue("<=10000000", g  );
				dataset.setValue("<=100000000", h  );
				dataset.setValue("<=1000000000", i  );
				dataset.setValue(">1000000000", j  );
				

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return dataset;

	}

	public Color getColor() {
		return color;
	}

	public ArrayList<Tree> getTrees() {
		return Trees;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setTrees(ArrayList<Tree> trees) {
		Trees = trees;
	}

}
