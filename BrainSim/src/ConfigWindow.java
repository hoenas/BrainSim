import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.Timer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JComboBox;


public class ConfigWindow extends JFrame {

	private JPanel contentPane;
	private JSpinner eruptionPropabilitySpinner;
	private JSpinner eruptionOutputSpinner;
	private JSpinner thresholdSpinner;
	private JSpinner cooldownSpinner;
	private JSpinner linkDistanceSpinner;
	private JSpinner neurocellsizeSpinner;
	
	private Brain brain;
	private Timer simTimer;
	private Random ran;
	
	/**
	 * Create the frame.
	 */
	public ConfigWindow( Brain brain, Timer simTimer ) {
		this.brain = brain;
		this.simTimer = simTimer;
		this.ran = new Random(1);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 477, 381);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblSimulationStatus = new JLabel("Simulation Status:");
		lblSimulationStatus.setBounds(10, 11, 99, 14);
		contentPane.add(lblSimulationStatus);
		
		JLabel lblPaused = new JLabel("stopped");
		lblPaused.setBounds(119, 11, 87, 14);
		contentPane.add(lblPaused);
		
		JButton btnNewButton = new JButton("start");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if( simTimer.isRunning() ) {
					simTimer.stop();
					lblPaused.setText("stopped");
					btnNewButton.setText("start");
				} else {
					simTimer.start();
					lblPaused.setText("running");
					btnNewButton.setText("stop");
				}
			}
		});
		btnNewButton.setBounds(216, 11, 137, 14);
		contentPane.add(btnNewButton);
		
		JLabel label_4 = new JLabel("16ms");
		label_4.setBounds(363, 36, 80, 14);
		contentPane.add(label_4);
		
		JSlider slider = new JSlider();
		slider.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				simTimer.setDelay((int)slider.getValue());
				label_4.setText((int)slider.getValue() + "ms");
			}
		});
		slider.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				simTimer.setDelay((int)slider.getValue());	
				label_4.setText((int)slider.getValue() + "ms");
			}
		});
		slider.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent arg0) {
			}
		});
		slider.setMaximum(1000);
		slider.setMinimum(16);
		slider.setValue(16);
		slider.setBounds(119, 36, 234, 14);
		contentPane.add(slider);
		
		JCheckBox chckbxDrawLinkLines = new JCheckBox("draw link lines");
		chckbxDrawLinkLines.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				brain.setDrawLinkLines(chckbxDrawLinkLines.isSelected());
			}
		});
		chckbxDrawLinkLines.setBounds(10, 83, 110, 23);
		contentPane.add(chckbxDrawLinkLines);
		
		JLabel lblAddNeurocells = new JLabel("Add Neurocells:");
		lblAddNeurocells.setBounds(10, 62, 80, 14);
		contentPane.add(lblAddNeurocells);
		
		JSpinner spinner = new JSpinner();
		spinner.setModel(new SpinnerNumberModel(new Integer(25600), new Integer(1), null, new Integer(1)));
		spinner.setBounds(119, 62, 147, 14);
		contentPane.add(spinner);
		
		JButton btnAdd = new JButton("add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < (int) spinner.getValue(); i++) {
					brain.getNeurocells().add(new NeuroCell((float)thresholdSpinner.getValue(), (int)cooldownSpinner.getValue(), (float)eruptionPropabilitySpinner.getValue(), (float)eruptionOutputSpinner.getValue(), (int)(ran.nextFloat() * brain.getWidth()), (int)(ran.nextFloat() * brain.getHeight()), (int)neurocellsizeSpinner.getValue(), Color.gray, Color.magenta));				
				}
			}
		});
		btnAdd.setBounds(276, 61, 80, 15);
		contentPane.add(btnAdd);
		
		JLabel lblEruptionProbability = new JLabel("Eruption Probability:");
		lblEruptionProbability.setBounds(10, 146, 99, 14);
		contentPane.add(lblEruptionProbability);
		
		eruptionPropabilitySpinner = new JSpinner();
		eruptionPropabilitySpinner.setModel(new SpinnerNumberModel(new Float(0.001), new Float(0), new Float(2), new Float(0)));
		eruptionPropabilitySpinner.setBounds(119, 147, 147, 14);
		contentPane.add(eruptionPropabilitySpinner);
		
		JButton button = new JButton("set");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < brain.getNeurocells().size(); i++) {
					brain.getNeurocells().get(i).setEruptionPropability( (float)eruptionPropabilitySpinner.getValue() );
				}
			}
		});
		button.setBounds(276, 146, 80, 15);
		contentPane.add(button);
		
		JLabel lblThreshold = new JLabel("Threshold:");
		lblThreshold.setBounds(10, 197, 99, 14);
		contentPane.add(lblThreshold);
		
		thresholdSpinner = new JSpinner();
		thresholdSpinner.setModel(new SpinnerNumberModel(new Float(5), null, null, new Float(1)));
		thresholdSpinner.setBounds(119, 198, 147, 14);
		contentPane.add(thresholdSpinner);
		
		JButton button_1 = new JButton("set");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < brain.getNeurocells().size(); i++) {
					brain.getNeurocells().get(i).setThreshold( (float)thresholdSpinner.getValue() );
				}
			}
		});
		button_1.setBounds(276, 197, 80, 15);
		contentPane.add(button_1);
		
		JButton button_2 = new JButton("set");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// destroy links
				ArrayList<NeuroCell> tmpList = new ArrayList<NeuroCell>();
				for(int i = 0; i < brain.getNeurocells().size(); i++) {
					brain.getNeurocells().get(i).getInputs().clear();
					tmpList.add(brain.getNeurocells().get(i));
				}
				
				brain.setLinkRadius((double)linkDistanceSpinner.getValue());
				brain.getNeurocells().clear();
				
				for(int i = 0; i < tmpList.size(); i++) {
					brain.addNeurocell(tmpList.get(i));
				}
			}
		});
		button_2.setBounds(276, 248, 80, 15);
		contentPane.add(button_2);
		
		linkDistanceSpinner = new JSpinner();
		linkDistanceSpinner.setModel(new SpinnerNumberModel(new Double(9), new Double(0), null, new Double(1)));
		linkDistanceSpinner.setBounds(119, 249, 147, 14);
		contentPane.add(linkDistanceSpinner);
		
		JLabel lblLinkDistance = new JLabel("Link Distance:");
		lblLinkDistance.setBounds(10, 248, 99, 14);
		contentPane.add(lblLinkDistance);
		
		JButton button_3 = new JButton("set");
		button_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < brain.getNeurocells().size(); i++) {
					brain.getNeurocells().get(i).setCooldown( (int)cooldownSpinner.getValue() );
				}
			}
		});
		button_3.setBounds(276, 222, 80, 15);
		contentPane.add(button_3);
		
		cooldownSpinner = new JSpinner();
		cooldownSpinner.setModel(new SpinnerNumberModel(new Integer(22), new Integer(0), null, new Integer(1)));
		cooldownSpinner.setBounds(119, 223, 147, 14);
		contentPane.add(cooldownSpinner);
		
		JLabel label = new JLabel("Cooldown:");
		label.setBounds(10, 222, 99, 14);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("Neurocell Size:");
		label_1.setBounds(10, 274, 99, 14);
		contentPane.add(label_1);
		
		neurocellsizeSpinner = new JSpinner();
		neurocellsizeSpinner.setModel(new SpinnerNumberModel(4, 1, 100, 1));
		neurocellsizeSpinner.setBounds(119, 275, 147, 14);
		contentPane.add(neurocellsizeSpinner);
		
		JButton button_4 = new JButton("set");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < brain.getNeurocells().size(); i++) {
					brain.getNeurocells().get(i).setSize( (int)neurocellsizeSpinner.getValue() );
				}
			}
		});
		button_4.setBounds(276, 274, 80, 15);
		contentPane.add(button_4);
		
		eruptionOutputSpinner = new JSpinner();
		eruptionOutputSpinner.setModel(new SpinnerNumberModel(new Float(1.0f), null, null, new Float(1)));
		eruptionOutputSpinner.setBounds(119, 172, 147, 14);
		contentPane.add(eruptionOutputSpinner);
		
		JButton button_5 = new JButton("set");
		button_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i = 0; i < brain.getNeurocells().size(); i++) {
					brain.getNeurocells().get(i).setEruptionOutput( (float)eruptionOutputSpinner.getValue() );
				}
			}
		});
		button_5.setBounds(276, 171, 80, 15);
		contentPane.add(button_5);
		
		JLabel label_2 = new JLabel("Eruption Output:");
		label_2.setBounds(10, 171, 99, 14);
		contentPane.add(label_2);
		
		JButton button_6 = new JButton("clear");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				brain.getNeurocells().clear();
			}
		});
		button_6.setBounds(366, 61, 80, 15);
		contentPane.add(button_6);
		
		JLabel label_3 = new JLabel("Timer Interval:");
		label_3.setBounds(10, 36, 80, 14);
		contentPane.add(label_3);
		
		JButton button_7 = new JButton("randomize positions");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < brain.getNeurocells().size(); i++)
				{
					brain.getNeurocells().get(i).setX((int)(ran.nextFloat() * brain.getWidth()));
					brain.getNeurocells().get(i).setY((int)(ran.nextFloat() * brain.getHeight()));
				}
			}
		});
		button_7.setBounds(119, 83, 147, 15);
		contentPane.add(button_7);
		
		JButton btnCalculateLinks = new JButton("calculate links");
		btnCalculateLinks.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// destroy links
				ArrayList<NeuroCell> tmpList = new ArrayList<NeuroCell>();
				for(int i = 0; i < brain.getNeurocells().size(); i++) {
					brain.getNeurocells().get(i).getInputs().clear();
					tmpList.add(brain.getNeurocells().get(i));
				}
				
				brain.setLinkRadius((double)linkDistanceSpinner.getValue());
				brain.getNeurocells().clear();
				
				for(int i = 0; i < tmpList.size(); i++) {
					brain.addNeurocell(tmpList.get(i));
				}
			}
		});
		btnCalculateLinks.setBounds(276, 83, 147, 15);
		contentPane.add(btnCalculateLinks);
		
		JButton button_9 = new JButton("square positions");
		button_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int sqrt = (int)Math.sqrt( brain.getNeurocells().size() );
				
				// get rid of rest
				int tmp = sqrt * sqrt;
				while(brain.getNeurocells().size() != tmp) {
					brain.getNeurocells().remove(tmp);
				}
				
				spinner.setValue( tmp );
				
				int counter = 0;
				for(int i = 0; i < sqrt; i++) {
					for(int k = 0; k < sqrt; k++) {
						brain.getNeurocells().get(counter).setX(i * (int)neurocellsizeSpinner.getValue());
						brain.getNeurocells().get(counter).setY(k * (int)neurocellsizeSpinner.getValue());
						counter++;
					}
				}
			}
		});
		button_9.setBounds(119, 109, 147, 15);
		contentPane.add(button_9);
		
		JCheckBox checkBox = new JCheckBox("draw rectangles");
		checkBox.setSelected(true);
		checkBox.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				brain.setDrawRects(checkBox.isSelected());
			}
		});
		checkBox.setBounds(10, 109, 110, 23);
		contentPane.add(checkBox);
		
		JButton button_8 = new JButton("reset simulation");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < brain.getNeurocells().size(); i++) {
					brain.getNeurocells().get(i).setOutput(0.0f);
				}
			}
		});
		button_8.setBounds(276, 109, 147, 15);
		contentPane.add(button_8);
	}
}
