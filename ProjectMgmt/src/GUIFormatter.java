import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIFormatter {

	public static JFrame makeFrame() {
		
		JFrame gui_frame = new JFrame();
		gui_frame.setVisible(true);
		gui_frame.setLayout(new GridLayout(2,1));
		gui_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui_frame.setTitle("Work Order Management System");
		gui_frame.setSize(400,200);
		gui_frame.setLocationRelativeTo(null);
		
		return gui_frame;
	}
	
	public static JPanel makeInputPanel(JLabel infoText, JLabel apptLabel, JTextField apptCode, JButton submitAppt) {
		
		final JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4,1));
		inputPanel.add(infoText);
		inputPanel.add(apptLabel);
		inputPanel.add(apptCode);
		inputPanel.add(submitAppt);
		
		return inputPanel;
	}
	
	public static JPanel makeOutputPanel(JTextField firstNameText, JTextField lastNameText, JTextField phoneNoText, 
			JTextField apptTypeText, JTextField apptDateText, JTextField apptTimeText) {
		
		final JPanel outputPanel = new JPanel();
		outputPanel.setLayout(new GridLayout(6,2));
		JLabel firstName = new JLabel("First Name:");
		JLabel lastName = new JLabel("Last Name:");
		JLabel phoneNo = new JLabel("Phone #:");
		JLabel apptType = new JLabel("Type:");
		JLabel apptDate = new JLabel("Date:");
		JLabel apptTime = new JLabel("Time:");
		firstNameText.setEditable(false);
		lastNameText.setEditable(false);
		phoneNoText.setEditable(false);
		apptTypeText.setEditable(false);
		apptDateText.setEditable(false);
		apptTimeText.setEditable(false);
		outputPanel.add(firstName);
		outputPanel.add(firstNameText);
		outputPanel.add(lastName);
		outputPanel.add(lastNameText);
		outputPanel.add(phoneNo);
		outputPanel.add(phoneNoText);
		outputPanel.add(apptType);
		outputPanel.add(apptTypeText);
		outputPanel.add(apptDate);
		outputPanel.add(apptDateText);
		outputPanel.add(apptTime);
		outputPanel.add(apptTimeText);
		
		return outputPanel;
	}
}
