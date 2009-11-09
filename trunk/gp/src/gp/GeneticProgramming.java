/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GeneticProgramming.java
 *
 * Created on Oct 5, 2009, 3:38:25 PM
 */

package gp;

import java.awt.event.KeyEvent;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

/**
 * Primary class and GUI that starts the application
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class GeneticProgramming extends javax.swing.JFrame {
	static Logger logger = Logger.getLogger(GeneticProgramming.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param args
	 *            the command line arguments
	 */
	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GeneticProgramming().setVisible(true);
			}
		});
	}

	private javax.swing.JLabel bestResultLabel;

	private javax.swing.JLabel bestResultValueLabel;

	private javax.swing.JPanel buttonPanel;
	JFreeChart chart = createChart();
	private javax.swing.JLabel crossoverLabel;
	private javax.swing.JSlider crossoverSlider;
	private EquationGraphPanel equationGraphPanel = new EquationGraphPanel(
			chart);
	private javax.swing.JLabel equationLabel;
	private javax.swing.JTextField equationTextField;
	private javax.swing.JLabel finalEquationLabel;
	private javax.swing.JLabel finalEquationResultLabel;
	private javax.swing.JLabel finalGenerationLabel;
	private javax.swing.JLabel finalGenerationResultLabel;
	private javax.swing.JPanel finalResultPanel;
	private javax.swing.JLabel finalTimeLabel;
	private javax.swing.JLabel finalTimeresultLabel;
	private javax.swing.JLabel fitnassValueLabel;
	private javax.swing.JLabel fitnessLabel;
	// Variables declaration - do not modify
	FindThread ft = new FindThread();
	private javax.swing.JLabel functionalSetSizeLabel;
	private javax.swing.JTextField functionalSetTextField;
	private javax.swing.JLabel generationLabel;
	private javax.swing.JLabel generationValueLabel;
	private javax.swing.JPanel inputPanel;
	private javax.swing.JLabel maxTrainingDataLabel;
	private javax.swing.JTextField maxTrainingDataTextField;
	private javax.swing.JComboBox maxTreeHeightComboBox;
	private javax.swing.JLabel maxTreeHeightLabel;
	private javax.swing.JLabel minTrainingDataLabel;
	private javax.swing.JTextField minTrainingDataTextField;
	private javax.swing.JLabel mutationRateLabel;
	private javax.swing.JSlider mutationRateSlider;
	private javax.swing.JButton okButton;
	private PopulationFitnessPanel populationFitnessPanel = new PopulationFitnessPanel(
			chart);
	private javax.swing.JLabel populationSizeLabel;
	private javax.swing.JTextField populationSizeTextField;
	private javax.swing.JPanel realTimeResultsPanel;
	private javax.swing.JDialog resultDialog;
	private javax.swing.JPanel resultsPanel;
	private javax.swing.JButton runButton;
	private javax.swing.JLabel runtimeLabel;
	private javax.swing.JLabel runtimeValueLabel;
	private javax.swing.JSplitPane splitPane;
	private javax.swing.JTabbedPane tabbedPane;
	private javax.swing.JLabel terminalSetLabel;
	private javax.swing.JTextField terminalSetTextField;
	ThreadGroup tg = new ThreadGroup("FindThread");
	Thread thread = new Thread(tg, ft, "");
	long totalTime = 0;
	private javax.swing.JPanel trainingDataPanel;
	private javax.swing.JComboBox treeHeightComboBox;
	private javax.swing.JLabel treeHeightLabel;

	/** Creates new form NewJFrame */
	public GeneticProgramming() {
		initComponents();
	}

	@SuppressWarnings("unused")
	private void bestResultTextFieldActionPerformed(
			java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private JFreeChart createChart() {
		final JFreeChart chart = ChartFactory.createXYLineChart("Result", // chart
				// title
				"X", // x axis label
				"Y", // y axis label
				null, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);
		return chart;
	}

	private void equationTextFieldActionPerformed(java.awt.event.KeyEvent e) {
		char c = e.getKeyChar();
		if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
				|| (c == '+') || (c == '(') || (c == ')') || (c == '-')
				|| (c == '*') || (c == '^') || (c == 'x') || (c == '/')
				|| (c == ',') || (c == KeyEvent.VK_DELETE)))) {
			getToolkit().beep();
			e.consume();
		}
	}

	@SuppressWarnings("unused")
	private void fitnessTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void functionalSetTextFieldActionPerformed(java.awt.event.KeyEvent e) {
		char c = e.getKeyChar();
		if (!(((c == KeyEvent.VK_BACK_SPACE) || (c == '+') || (c == '-')
				|| (c == '*') || (c == '^') || (c == '/') || (c == ',') || (c == KeyEvent.VK_DELETE)))) {
			getToolkit().beep();
			e.consume();
		}
	}

	@SuppressWarnings("unused")
	private void generationTextFieldActionPerformed(
			java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */

	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		resultDialog = new javax.swing.JDialog();
		finalResultPanel = new javax.swing.JPanel();
		finalEquationLabel = new javax.swing.JLabel();
		finalEquationResultLabel = new javax.swing.JLabel();
		finalGenerationLabel = new javax.swing.JLabel();
		finalGenerationResultLabel = new javax.swing.JLabel();
		finalTimeLabel = new javax.swing.JLabel();
		finalTimeresultLabel = new javax.swing.JLabel();
		okButton = new javax.swing.JButton();
		splitPane = new javax.swing.JSplitPane();
		inputPanel = new javax.swing.JPanel();
		equationLabel = new javax.swing.JLabel();
		equationTextField = new javax.swing.JTextField();
		populationSizeTextField = new javax.swing.JTextField();
		populationSizeLabel = new javax.swing.JLabel();
		terminalSetLabel = new javax.swing.JLabel();
		terminalSetTextField = new javax.swing.JTextField();
		treeHeightLabel = new javax.swing.JLabel();
		treeHeightComboBox = new javax.swing.JComboBox();
		crossoverLabel = new javax.swing.JLabel();
		mutationRateSlider = new javax.swing.JSlider();
		mutationRateLabel = new javax.swing.JLabel();
		crossoverSlider = new javax.swing.JSlider();
		functionalSetSizeLabel = new javax.swing.JLabel();
		functionalSetTextField = new javax.swing.JTextField();
		maxTreeHeightLabel = new javax.swing.JLabel();
		maxTreeHeightComboBox = new javax.swing.JComboBox();
		trainingDataPanel = new javax.swing.JPanel();
		minTrainingDataLabel = new javax.swing.JLabel();
		minTrainingDataTextField = new javax.swing.JTextField();
		maxTrainingDataLabel = new javax.swing.JLabel();
		maxTrainingDataTextField = new javax.swing.JTextField();
		resultsPanel = new javax.swing.JPanel();
		realTimeResultsPanel = new javax.swing.JPanel();
		generationLabel = new javax.swing.JLabel();
		generationValueLabel = new javax.swing.JLabel();
		runtimeLabel = new javax.swing.JLabel();
		runtimeValueLabel = new javax.swing.JLabel();
		bestResultLabel = new javax.swing.JLabel();
		bestResultValueLabel = new javax.swing.JLabel();
		fitnessLabel = new javax.swing.JLabel();
		fitnassValueLabel = new javax.swing.JLabel();
		buttonPanel = new javax.swing.JPanel();
		runButton = new javax.swing.JButton();
		tabbedPane = new javax.swing.JTabbedPane();

		finalResultPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Final Result"));
		finalResultPanel.setLayout(new java.awt.GridBagLayout());

		finalEquationLabel.setText("Equation:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		finalResultPanel.add(finalEquationLabel, gridBagConstraints);

		finalEquationResultLabel.setText("(x^2+1)/2");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		finalResultPanel.add(finalEquationResultLabel, gridBagConstraints);

		finalGenerationLabel.setText("Generation:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		finalResultPanel.add(finalGenerationLabel, gridBagConstraints);

		finalGenerationResultLabel.setText("####");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		finalResultPanel.add(finalGenerationResultLabel, gridBagConstraints);

		finalTimeLabel.setText("Time:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		finalResultPanel.add(finalTimeLabel, gridBagConstraints);

		finalTimeresultLabel.setText("###");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		finalResultPanel.add(finalTimeresultLabel, gridBagConstraints);

		resultDialog.getContentPane().add(finalResultPanel,
				java.awt.BorderLayout.CENTER);

		okButton.setText("DONE");
		resultDialog.getContentPane()
				.add(okButton, java.awt.BorderLayout.SOUTH);
		okButton.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mousePressed(java.awt.event.MouseEvent evt) {
				okButtonMousePressed(evt);
			}
		});
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("Genetic Programming");

		splitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

		inputPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Settings"));
		inputPanel.setLayout(new java.awt.GridBagLayout());

		equationLabel.setText("Equation");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(equationLabel, gridBagConstraints);

		equationTextField.setText("(x^2-1)/2");
		equationTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				equationTextFieldActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(equationTextField, gridBagConstraints);

		populationSizeTextField.setText("1000");
		populationSizeTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				populationSizeTextFieldActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(populationSizeTextField, gridBagConstraints);

		populationSizeLabel.setText("Population Size");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(populationSizeLabel, gridBagConstraints);

		terminalSetLabel.setText("Terminal Set");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(terminalSetLabel, gridBagConstraints);

		terminalSetTextField.setText("1,2,3,4,5,6,7,8,9,x");
		terminalSetTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				terminalSetTextFieldActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(terminalSetTextField, gridBagConstraints);

		treeHeightLabel.setText("Tree Height");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(treeHeightLabel, gridBagConstraints);

		treeHeightComboBox.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "1", "2", "3", "4", "5" }));
		treeHeightComboBox.setSelectedIndex(3);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(treeHeightComboBox, gridBagConstraints);

		crossoverLabel.setText("Crossover Rate");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(crossoverLabel, gridBagConstraints);

		mutationRateSlider.setMajorTickSpacing(5);
		mutationRateSlider.setPaintTicks(true);
		mutationRateSlider.setSnapToTicks(true);
		mutationRateSlider.setValue(25);
		mutationRateSlider.setName(""); // NOI18N
		mutationRateSlider.setValueIsAdjusting(true);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(mutationRateSlider, gridBagConstraints);

		mutationRateLabel.setText("Mutation Rate");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(mutationRateLabel, gridBagConstraints);

		crossoverSlider.setMajorTickSpacing(5);
		crossoverSlider.setPaintTicks(true);
		crossoverSlider.setSnapToTicks(true);
		crossoverSlider.setValue(90);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(crossoverSlider, gridBagConstraints);

		functionalSetSizeLabel.setText("Functional Set");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(functionalSetSizeLabel, gridBagConstraints);

		functionalSetTextField.setText("+,-,/,*");
		functionalSetTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				functionalSetTextFieldActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(functionalSetTextField, gridBagConstraints);

		maxTreeHeightLabel.setText("Max Tree Height");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(maxTreeHeightLabel, gridBagConstraints);

		maxTreeHeightComboBox.setModel(new javax.swing.DefaultComboBoxModel(
				new String[] { "1", "2", "3", "4", "5" }));
		maxTreeHeightComboBox.setSelectedIndex(3);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(maxTreeHeightComboBox, gridBagConstraints);

		trainingDataPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Training Data"));
		trainingDataPanel.setLayout(new java.awt.GridBagLayout());

		minTrainingDataLabel.setText("Min");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 14;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
		trainingDataPanel.add(minTrainingDataLabel, gridBagConstraints);

		minTrainingDataTextField.setText("-100");
		minTrainingDataTextField
				.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyTyped(java.awt.event.KeyEvent evt) {
						minTrainingDataTextFieldActionPerformed(evt);
					}
				});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 13;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 15);
		trainingDataPanel.add(minTrainingDataTextField, gridBagConstraints);

		maxTrainingDataLabel.setText("Max");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 14;
		gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 5);
		trainingDataPanel.add(maxTrainingDataLabel, gridBagConstraints);

		maxTrainingDataTextField.setText("100");
		maxTrainingDataTextField
				.addKeyListener(new java.awt.event.KeyAdapter() {
					public void keyTyped(java.awt.event.KeyEvent evt) {
						maxTrainingDataTextFieldActionPerformed(evt);
					}
				});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 14;
		trainingDataPanel.add(maxTrainingDataTextField, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
		gridBagConstraints.ipadx = 2;
		inputPanel.add(trainingDataPanel, gridBagConstraints);

		splitPane.setTopComponent(inputPanel);

		resultsPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Status"));
		resultsPanel.setLayout(new java.awt.BorderLayout());

		realTimeResultsPanel.setLayout(new java.awt.GridBagLayout());

		generationLabel.setText("Generation:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		realTimeResultsPanel.add(generationLabel, gridBagConstraints);

		generationValueLabel.setForeground(new java.awt.Color(102, 102, 255));
		generationValueLabel.setText("0");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
		realTimeResultsPanel.add(generationValueLabel, gridBagConstraints);

		runtimeLabel.setText("Run Time:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		realTimeResultsPanel.add(runtimeLabel, gridBagConstraints);

		runtimeValueLabel.setForeground(new java.awt.Color(102, 102, 255));
		runtimeValueLabel.setText("0:00");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
		realTimeResultsPanel.add(runtimeValueLabel, gridBagConstraints);

		bestResultLabel.setText("Best Result:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		realTimeResultsPanel.add(bestResultLabel, gridBagConstraints);

		bestResultValueLabel.setForeground(new java.awt.Color(102, 102, 255));
		bestResultValueLabel.setText("none");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
		realTimeResultsPanel.add(bestResultValueLabel, gridBagConstraints);

		fitnessLabel.setText("Fitness:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 19, 0, 0);
		realTimeResultsPanel.add(fitnessLabel, gridBagConstraints);

		fitnassValueLabel.setForeground(new java.awt.Color(102, 102, 255));
		fitnassValueLabel.setText("none");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
		realTimeResultsPanel.add(fitnassValueLabel, gridBagConstraints);

		resultsPanel.add(realTimeResultsPanel, java.awt.BorderLayout.NORTH);

		splitPane.setBottomComponent(resultsPanel);

		getContentPane().add(splitPane, java.awt.BorderLayout.CENTER);

		runButton.setText("Run");
		runButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				runButtonActionPerformed(evt);
			}
		});
		buttonPanel.add(runButton);

		getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);
		tabbedPane.addTab("Result Graph", equationGraphPanel);
		tabbedPane.addTab("Population Fitness", populationFitnessPanel);

		getContentPane().add(tabbedPane, java.awt.BorderLayout.EAST);
		pack();
	}

	private boolean isInputValid() {
		try {
			Expression exp = ExpressionTree.parse(equationTextField.getText());
			VarMap vm = new VarMap(false /* case sensitive */);
			vm.setValue("x", 5);
			exp.eval(vm, null);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Bad target equiation "
					+ equationTextField.getText(), "Error",
					JOptionPane.ERROR_MESSAGE);

			return false;
		}
		try {
			Integer.parseInt(populationSizeTextField.getText());
			if (Integer.parseInt(populationSizeTextField.getText()) <= 0) {
				JOptionPane
						.showMessageDialog(
								this,
								"Bad population size needs to be in the range of 1 and 2147483647",
								"Error", JOptionPane.ERROR_MESSAGE);

				return false;
			}
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Bad population size needs to be in the range of 1 and 2147483647",
							"Error", JOptionPane.ERROR_MESSAGE);

			return false;
		}
		try {
			Integer.parseInt(maxTrainingDataTextField.getText());
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Bad max training data size needs to be in the range of -2147483647 and 2147483647",
							"Error", JOptionPane.ERROR_MESSAGE);

			return false;
		}
		try {
			Integer.parseInt(minTrainingDataTextField.getText());
		} catch (Exception e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Bad min training data size needs to be in the range of -2147483648 and 2147483646",
							"Error", JOptionPane.ERROR_MESSAGE);

			return false;
		}
		try {
			int max = Integer.parseInt(maxTrainingDataTextField.getText());
			int min = Integer.parseInt(minTrainingDataTextField.getText());
			if (max <= min) {
				JOptionPane
						.showMessageDialog(
								this,
								"Bad training data values.  Max needs to be more then the Min",
								"Error", JOptionPane.ERROR_MESSAGE);

				return false;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Bad training data values of -2147483648 and 2147483647",
					"Error", JOptionPane.ERROR_MESSAGE);

			return false;
		}
		return true;
	}

	private void maxTrainingDataTextFieldActionPerformed(
			java.awt.event.KeyEvent e) {
		char c = e.getKeyChar();
		if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
				|| (c == KeyEvent.VK_MINUS) || (c == KeyEvent.VK_DELETE)))) {
			getToolkit().beep();
			e.consume();
		}
	}

	private void minTrainingDataTextFieldActionPerformed(
			java.awt.event.KeyEvent e) {
		char c = e.getKeyChar();
		if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
				|| (c == KeyEvent.VK_MINUS) || (c == KeyEvent.VK_DELETE)))) {
			getToolkit().beep();
			e.consume();
		}
	}

	private void okButtonMousePressed(java.awt.event.MouseEvent evt) {
		System.exit(0);
	}

	private void populationSizeTextFieldActionPerformed(
			java.awt.event.KeyEvent e) {
		char c = e.getKeyChar();
		if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)))) {
			getToolkit().beep();
			e.consume();
		}
	}

	private void runButtonActionPerformed(java.awt.event.ActionEvent evt) {

		if (!thread.isAlive() && isInputValid()) {
			runButton.setEnabled(false);

			long startTime = System.currentTimeMillis();
			ft.setStartTime(startTime);
			ft.setTargetExpersion(equationTextField.getText());
			String terminalSetValue = terminalSetTextField.getText();
			StringTokenizer st = new StringTokenizer(terminalSetValue, ",");
			TerminalSet terminalSet = new TerminalSet();
			while (st.hasMoreElements()) {
				terminalSet.add(st.nextToken());
			}
			ft.setTerminalSet(terminalSet);
			String functionalSetValue = functionalSetTextField.getText();
			StringTokenizer st2 = new StringTokenizer(functionalSetValue, ",");
			FunctionalSet functionalSet = new FunctionalSet();
			while (st2.hasMoreElements()) {
				functionalSet.add(st2.nextToken());
			}
			ft.setFunctionalSet(functionalSet);
			ft.setGui(true);
			ft.setMutationRate(mutationRateSlider.getValue() / 100.00);
			ft.setCrossoverRate(crossoverSlider.getValue() / 100.00);
			int hieghtOfTree = Integer.parseInt(treeHeightComboBox
					.getSelectedItem().toString());
			ft.setHieghtOfTree(hieghtOfTree);
			int numberOfTrees = (new Integer(populationSizeTextField.getText()))
					.intValue();
			ft.setMaxRange(Integer.parseInt(this.maxTrainingDataTextField
					.getText()));
			ft.setMinRange(Integer.parseInt(this.minTrainingDataTextField
					.getText()));
			ft.setNumberOfTrees(numberOfTrees);
			int maxHeight = Integer.parseInt(treeHeightComboBox
					.getSelectedItem().toString());
			ft.setMaxHeight(maxHeight);
			ft.setGenerationValueLabel(this.generationValueLabel);
			ft.setRuntimeValueLabel(this.runtimeValueLabel);
			ft.setBestResultValueLabel(this.bestResultValueLabel);
			ft.setFitnessValueLabel(this.fitnassValueLabel);
			ft.setResultDialog(this.resultDialog);
			ft.setFinalEquationResultLabel(this.finalEquationResultLabel);
			ft.setFinalGenerationResultLabel(this.finalGenerationResultLabel);
			ft.setFinalTimeresultLabel(this.finalTimeresultLabel);
			ft.setFrame(this);
			ft.setEquationGraphPanel(equationGraphPanel);
			ft.setPopulationFitnessPanel(populationFitnessPanel);
			thread.start();
		} else {

			// do nothing its already running
		}
	}

	@SuppressWarnings("unused")
	private void runtimeTextFieldActionPerformed(java.awt.event.ActionEvent evt) {
		// TODO add your handling code here:
	}

	private void terminalSetTextFieldActionPerformed(java.awt.event.KeyEvent e) {
		char c = e.getKeyChar();
		if (!((Character.isDigit(c) || (c == KeyEvent.VK_BACK_SPACE)
				|| (c == 'x') || (c == ',') || (c == KeyEvent.VK_DELETE)))) {
			getToolkit().beep();
			e.consume();
		}
	}
}
