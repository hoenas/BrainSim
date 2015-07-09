import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JSlider;
import javax.swing.JTabbedPane;
import javax.swing.JButton;


public class Main extends JFrame {

	private JPanel contentPane;
	private Timer simTimer;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 410, 410);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JButton btnStartStop = new JButton("Start / Stop");
		btnStartStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( simTimer.isRunning()) {
					simTimer.stop();
				} else {
					simTimer.start();
				}
			}
		});
		contentPane.add(btnStartStop, BorderLayout.NORTH);
		
		JSlider slider = new JSlider();
		slider.setMinimum(16);
		slider.setMaximum(10000);
		slider.setValue(250);
		contentPane.add(slider, BorderLayout.SOUTH);
		
		Brain brain = new Brain(Color.white, Color.cyan, 490, 10, 200, true);
		contentPane.add(brain, BorderLayout.CENTER);
		
		// Timerkonfiguration
		simTimer = new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// SiMTIMER UPDATE
				simTimer.setDelay( (int)slider.getValue() );
				brain.update();
			}
		});
	}
}
