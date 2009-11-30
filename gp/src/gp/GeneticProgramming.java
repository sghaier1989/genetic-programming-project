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
 * Primary class and GUI that starts the application.
 * 
 * @author Trevor Greene
 * @version 1.0
 */
public class GeneticProgramming extends javax.swing.JFrame {
	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = -7885681233614641547L;
	/**
	 * Logger.
	 */
	private static final Logger GP_LOGGER = Logger
			.getLogger(GeneticProgramming.class);

	/**
	 * Main method that launches application.
	 * 
	 * @param args
	 *            the command line arguments
	 */
	public static void main(final String[] args) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new GeneticProgramming().setVisible(true);
			}
		});
	}

	/**
	 * Best Value Label.
	 */
	private javax.swing.JLabel bestValueLabel;
	/**
	 * chart.
	 */
	private JFreeChart chart = createChart();
	/**
	 * crossover slider.
	 */
	private javax.swing.JSlider crossoverSlider;
	/**
	 * final Eq Result Label.
	 */
	private javax.swing.JLabel finalEqResultLabel;
	/**
	 * equation Graph.
	 */
	private final EquationGraphPanel equationGraph = new EquationGraphPanel(
			chart);
	/**
	 * equation Label.
	 */
	private javax.swing.JLabel equationLabel;
	/**
	 * equation TextField.
	 */
	private javax.swing.JTextField equationTextField;
	/**
	 * final eq Label.
	 */
	private javax.swing.JLabel finalEqLabel;
	/**
	 * final Gen Label.
	 */
	private javax.swing.JLabel finalGenLabel;
	/**
	 * final Gen Result Label.
	 */
	private javax.swing.JLabel finalGenResultLabel;
	/**
	 * final Result Panel.
	 */
	private javax.swing.JPanel finalResultPanel;
	/**
	 * final Time Label.
	 */
	private javax.swing.JLabel finalTimeLabel;
	/**
	 * final Timeresult Label.
	 */
	private javax.swing.JLabel finalTimeresultLabel;
	/**
	 * fitness Value Label.
	 */
	private javax.swing.JLabel fitnessValueLabel;
	/**
	 * fitness Label.
	 */
	private javax.swing.JLabel fitnessLabel;
	/**
	 * find Thread.
	 */
	private final FindThread findThread = new FindThread();
	/**
	 * functional Set Size Label.
	 */
	private javax.swing.JLabel functionalSetSizeLabel;
	/**
	 * functional Set TextField.
	 */
	private javax.swing.JTextField functionalSetTextField;
	/**
	 * generation Label.
	 */
	private javax.swing.JLabel generationLabel;
	/**
	 * generation Value Label.
	 */
	private javax.swing.JLabel generationValueLabel;
	/**
	 * input Panel.
	 */
	private javax.swing.JPanel inputPanel;
	/**
	 * max Training Data Label.
	 */
	private javax.swing.JLabel maxTrainingDataLabel;
	/**
	 * max Training Data TextField.
	 */
	private javax.swing.JTextField maxTrainingDataTextField;
	/**
	 * max Tree Height ComboBox.
	 */
	private javax.swing.JComboBox maxTreeHeightComboBox;
	/**
	 * max Tree Height Label.
	 */
	private javax.swing.JLabel maxTreeHeightLabel;
	/**
	 * min Training Data Label.
	 */
	private javax.swing.JLabel minTrainingDataLabel;
	/**
	 * min Training Data TextField.
	 */
	private javax.swing.JTextField minTrainingDataTextField;
	/**
	 * mutation Rate Label.
	 */
	private javax.swing.JLabel mutationRateLabel;
	/**
	 * mutation Rate Slider.
	 */
	private javax.swing.JSlider mutationRateSlider;
	/**
	 * ok Button.
	 */
	private javax.swing.JButton okButton;
	/**
	 * pop Fitness Panel.
	 */
	private final PopulationFitnessPanel popFitnessPanel = new PopulationFitnessPanel(
			chart);
	/**
	 * population Size Label.
	 */
	private javax.swing.JLabel populationSizeLabel;
	/**
	 * population Size TextField.
	 */
	private javax.swing.JTextField populationSizeTextField;
	/**
	 * realTime Results Panel.
	 */
	private javax.swing.JPanel realTimeResultsPanel;
	/**
	 * result Dialog.
	 */
	private javax.swing.JDialog resultDialog;
	/**
	 * results Panel.
	 */
	private javax.swing.JPanel resultsPanel;
	/**
	 * run Button.
	 */
	private javax.swing.JButton runButton;
	/**
	 * runtime Label.
	 */
	private javax.swing.JLabel runtimeLabel;
	/**
	 * runtime Value Label.
	 */
	private javax.swing.JLabel runtimeValueLabel;
	/**
	 * Split Pane.
	 */
	private javax.swing.JSplitPane splitPane;
	/**
	 * tabbed Pane.
	 */
	private javax.swing.JTabbedPane tabbedPane;
	/**
	 * terminal Set Label.
	 */
	private javax.swing.JLabel terminalSetLabel;
	/**
	 * terminal Set TextField.
	 */
	private javax.swing.JTextField terminalSetTextField;
	/**
	 * The thread group.
	 */
	private final ThreadGroup threadGroup = new ThreadGroup("FindThread");
	/**
	 * The thread.
	 */
	private Thread thread = new Thread(threadGroup, findThread, "");
	/**
	 * training Data Panel.
	 */
	private javax.swing.JPanel trainingDataPanel;
	/**
	 * tree Height ComboBox.
	 */
	private javax.swing.JComboBox treeHeightComboBox;
	/**
	 * tree Height Label.
	 */
	private javax.swing.JLabel treeHeightLabel;
	/**
	 * tree Panel.
	 */
	private javax.swing.JPanel treePanel;
	/**
	 * tree View.
	 */
	private javax.swing.JTree treeView;
	/**
	 * tree View Scroll Pane.
	 */
	private javax.swing.JScrollPane treeViewScrollPane;

	/**
	 * Creates new form NewJFrame.
	 * 
	 */
	public GeneticProgramming() {
		super();
		initComponents();
	}

	/**
	 * Method for creating xy line graph.
	 * 
	 * @return a xy graph of the equation
	 */
	private JFreeChart createChart() {
		return ChartFactory.createXYLineChart("Equation Graph", // chart title
				"X", // x axis label
				"Y", // y axis label
				null, // data
				PlotOrientation.VERTICAL, true, // include legend
				true, // tooltips
				false // urls
				);
	}

	/**
	 * method to capture the key event.
	 * 
	 * @param event
	 *            - the key event
	 */
	private void equationTextFieldActionPerformed(
			final java.awt.event.KeyEvent event) {
		final char value = event.getKeyChar();
		if (!(Character.isDigit(value) || value == KeyEvent.VK_BACK_SPACE
				|| value == KeyEvent.VK_DELETE || value == '+' || value == '('
				|| value == ')' || value == '*' || value == '-' || value == '^'
				|| value == 'x' || value == '/')) {
			getToolkit().beep();
			event.consume();
		}
	}

	/**
	 * Method to capture the key event.
	 * 
	 * @param event
	 *            - the key event
	 */
	private void functionalSetTextFieldActionPerformed(
			final java.awt.event.KeyEvent event) {
		final char value = event.getKeyChar();
		if (!(value == KeyEvent.VK_BACK_SPACE || value == KeyEvent.VK_DELETE
				|| value == '+' || value == '-' || value == '*' || value == '^'
				|| value == '/' || value == ',')) {

			getToolkit().beep();
			event.consume();
		}
	}

	/**
	 * This method is called from within the constructor to initialize the form.
	 */
	private void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		resultDialog = new javax.swing.JDialog();
		finalResultPanel = new javax.swing.JPanel();
		finalEqLabel = new javax.swing.JLabel();
		finalEqResultLabel = new javax.swing.JLabel();
		finalGenLabel = new javax.swing.JLabel();
		finalGenResultLabel = new javax.swing.JLabel();
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
		final javax.swing.JLabel crossoverLabel = new javax.swing.JLabel();
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
		final javax.swing.JLabel bestResultLabel = new javax.swing.JLabel();
		bestValueLabel = new javax.swing.JLabel();
		fitnessLabel = new javax.swing.JLabel();
		fitnessValueLabel = new javax.swing.JLabel();
		final javax.swing.JPanel buttonPanel = new javax.swing.JPanel();
		runButton = new javax.swing.JButton();
		tabbedPane = new javax.swing.JTabbedPane();
		treePanel = new javax.swing.JPanel();
		treeViewScrollPane = new javax.swing.JScrollPane();
		treeView = new javax.swing.JTree();
		finalResultPanel.setBorder(javax.swing.BorderFactory
				.createTitledBorder("Final Result"));
		finalResultPanel.setLayout(new java.awt.GridBagLayout());

		finalEqLabel.setText("Equation:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		finalResultPanel.add(finalEqLabel, gridBagConstraints);

		finalEqResultLabel.setText("(x^2+1)/2");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		finalResultPanel.add(finalEqResultLabel, gridBagConstraints);

		finalGenLabel.setText("Generation:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		finalResultPanel.add(finalGenLabel, gridBagConstraints);

		finalGenResultLabel.setText("####");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.ipadx = 4;
		gridBagConstraints.ipady = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 20, 0, 0);
		finalResultPanel.add(finalGenResultLabel, gridBagConstraints);

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
			public void mousePressed(final java.awt.event.MouseEvent evt) {
				okButtonMousePressed();
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
			@Override
			public void keyTyped(final java.awt.event.KeyEvent evt) {
				equationTextFieldActionPerformed(evt);
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		inputPanel.add(equationTextField, gridBagConstraints);

		populationSizeTextField.setText("1000");
		populationSizeTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(final java.awt.event.KeyEvent evt) {
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
			@Override
			public void keyTyped(final java.awt.event.KeyEvent evt) {
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
			@Override
			public void keyTyped(final java.awt.event.KeyEvent evt) {
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

		minTrainingDataTextField.setText("-10");
		minTrainingDataTextField
				.addKeyListener(new java.awt.event.KeyAdapter() {
					@Override
					public void keyTyped(final java.awt.event.KeyEvent evt) {
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

		maxTrainingDataTextField.setText("10");
		maxTrainingDataTextField
				.addKeyListener(new java.awt.event.KeyAdapter() {
					@Override
					public void keyTyped(final java.awt.event.KeyEvent evt) {
						minTrainingDataTextFieldActionPerformed(evt);
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

		bestValueLabel.setForeground(new java.awt.Color(102, 102, 255));
		bestValueLabel.setText("none");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
		realTimeResultsPanel.add(bestValueLabel, gridBagConstraints);

		fitnessLabel.setText("Fitness:");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 19, 0, 0);
		realTimeResultsPanel.add(fitnessLabel, gridBagConstraints);

		fitnessValueLabel.setForeground(new java.awt.Color(102, 102, 255));
		fitnessValueLabel.setText("none");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 0);
		realTimeResultsPanel.add(fitnessValueLabel, gridBagConstraints);

		resultsPanel.add(realTimeResultsPanel, java.awt.BorderLayout.NORTH);

		splitPane.setBottomComponent(resultsPanel);

		getContentPane().add(splitPane, java.awt.BorderLayout.CENTER);

		runButton.setText("Run");
		runButton.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(final java.awt.event.ActionEvent evt) {
				runButtonActionPerformed();
			}
		});
		buttonPanel.add(runButton);

		getContentPane().add(buttonPanel, java.awt.BorderLayout.SOUTH);

		treePanel.setLayout(new java.awt.BorderLayout());

		treeViewScrollPane.setViewportView(treeView);

		treePanel.add(treeViewScrollPane, java.awt.BorderLayout.CENTER);

		tabbedPane.addTab("Result Graph", equationGraph);
		tabbedPane.addTab("Population Fitness", popFitnessPanel);
		// tabbedPane.addTab("Tree", treePanel);
		getContentPane().add(tabbedPane, java.awt.BorderLayout.EAST);
		pack();
	}

	/**
	 * Method that will verify if the form has valid values.
	 * 
	 * @return - if the form has valid values
	 * 
	 */
	private boolean isInputValid() {
		boolean valid = true;
		try {
			final Expression exp = ExpressionTree.parse(equationTextField
					.getText());
			final VarMap varMap = new VarMap(false /* case sensitive */);
			varMap.setValue("x", 5);
			exp.eval(varMap, null);

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Bad target equiation "
					+ equationTextField.getText(),
					"Bad target equiation error", JOptionPane.ERROR_MESSAGE);

			valid = false;
		}
		try {
			Integer.parseInt(populationSizeTextField.getText());
			if (Integer.parseInt(populationSizeTextField.getText()) <= 0) {
				JOptionPane.showMessageDialog(this,
						"Bad population size needs to be "
								+ "in the range of 1 and 2147483647",
						"Bad population size error", JOptionPane.ERROR_MESSAGE);

				valid = false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Bad population size needs to"
					+ " be in the range of 1 and 2147483647",
					"Bad population error", JOptionPane.ERROR_MESSAGE);

			valid = false;
		}
		try {
			Integer.parseInt(maxTrainingDataTextField.getText());
		} catch (NumberFormatException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Bad max training data size needs "
									+ "to be in the range of -2147483647 and 2147483647",
							"Bad max training data error",
							JOptionPane.ERROR_MESSAGE);

			valid = false;
		}
		try {
			Integer.parseInt(minTrainingDataTextField.getText());
		} catch (NumberFormatException e) {
			JOptionPane
					.showMessageDialog(
							this,
							"Bad min training data size needs "
									+ "to be in the range of -2147483648 and 2147483646",
							"Error", JOptionPane.ERROR_MESSAGE);

			valid = false;
		}
		try {
			final int max = Integer
					.parseInt(maxTrainingDataTextField.getText());
			final int min = Integer
					.parseInt(minTrainingDataTextField.getText());
			if (max <= min) {
				JOptionPane.showMessageDialog(this,
						"Bad training data values.  "
								+ "Max needs to be more then the Min", "Error",
						JOptionPane.ERROR_MESSAGE);

				valid = false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this,
					"Bad training data values of -2147483648 and 2147483647",
					"Error", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}
		return valid;
	}

	/**
	 * Method to capture the key event.
	 * 
	 * @param event
	 *            - the key event
	 */
	private void minTrainingDataTextFieldActionPerformed(
			final java.awt.event.KeyEvent event) {
		final char value = event.getKeyChar();
		if (!(Character.isDigit(value) || value == KeyEvent.VK_BACK_SPACE
				|| value == KeyEvent.VK_MINUS || value == KeyEvent.VK_DELETE)) {
			getToolkit().beep();
			event.consume();
		}
	}

	/**
	 * Method to capture the key event.
	 * 
	 */
	private void okButtonMousePressed() {
		System.exit(0);
	}

	/**
	 * Method to capture the key event.
	 * 
	 * @param event
	 *            - the key event
	 */
	private void populationSizeTextFieldActionPerformed(
			final java.awt.event.KeyEvent event) {
		final char value = event.getKeyChar();
		if (!(Character.isDigit(value) || value == KeyEvent.VK_DELETE || value == KeyEvent.VK_BACK_SPACE)) {
			getToolkit().beep();
			event.consume();
		}
	}

	/**
	 * Method to capture the key event.
	 * 
	 */
	private void runButtonActionPerformed() {
		if (!thread.isAlive() && isInputValid()) {
			GP_LOGGER.debug("Starting Run");
			runButton.setEnabled(false);
			final long startTime = System.currentTimeMillis();
			findThread.setStartTime(startTime);
			findThread.setTargetExpersion(equationTextField.getText());
			final String terminalSetValue = terminalSetTextField.getText();
			final StringTokenizer tokenizer = new StringTokenizer(
					terminalSetValue, ",");
			final TerminalSet terminalSet = new TerminalSet();
			while (tokenizer.hasMoreElements()) {
				terminalSet.add(tokenizer.nextToken());
			}
			findThread.setTerminalSet(terminalSet);
			final String functionalSetValue = functionalSetTextField.getText();
			final StringTokenizer tokenizer2 = new StringTokenizer(
					functionalSetValue, ",");
			final FunctionalSet functionalSet = new FunctionalSet();
			while (tokenizer2.hasMoreElements()) {
				functionalSet.add(tokenizer2.nextToken());
			}
			findThread.setFunctionalSet(functionalSet);
			findThread.setGui(true);
			findThread.setMutationRate(mutationRateSlider.getValue() / 100.00);
			findThread.setCrossoverRate(crossoverSlider.getValue() / 100.00);
			final int hieghtOfTree = Integer.parseInt(treeHeightComboBox
					.getSelectedItem().toString());
			findThread.setHieghtOfTree(hieghtOfTree);
			final int numberOfTrees = Integer.parseInt(populationSizeTextField
					.getText());
			findThread.setMaxRange(Integer
					.parseInt(this.maxTrainingDataTextField.getText()));
			findThread.setMinRange(Integer
					.parseInt(this.minTrainingDataTextField.getText()));
			findThread.setNumberOfTrees(numberOfTrees);
			final int maxHeight = Integer.parseInt(treeHeightComboBox
					.getSelectedItem().toString());
			findThread.setMaxHeight(maxHeight);
			findThread.setGenerationValueLabel(this.generationValueLabel);
			findThread.setRuntimeValueLabel(this.runtimeValueLabel);
			findThread.setBestResultValueLabel(this.bestValueLabel);
			findThread.setFitnessValueLabel(this.fitnessValueLabel);
			findThread.setResultDialog(this.resultDialog);
			findThread.setFinalEquationResultLabel(this.finalEqResultLabel);
			findThread.setFinalGenerationResultLabel(this.finalGenResultLabel);
			findThread.setFinalTimeresultLabel(this.finalTimeresultLabel);
			findThread.setFrame(this);
			findThread.setEquationGraphPanel(equationGraph);
			findThread.setPopulationFitnessPanel(popFitnessPanel);
			thread.start();
		}
	}

	/**
	 * Method to capture the key event.
	 * 
	 * @param event
	 *            - the key event
	 */
	private void terminalSetTextFieldActionPerformed(
			final java.awt.event.KeyEvent event) {
		final char value = event.getKeyChar();
		if (!(Character.isDigit(value) || value == KeyEvent.VK_BACK_SPACE
				|| value == KeyEvent.VK_COMMA || value == 'x' || value == KeyEvent.VK_DELETE)) {
			getToolkit().beep();
			event.consume();
		}
	}
}
