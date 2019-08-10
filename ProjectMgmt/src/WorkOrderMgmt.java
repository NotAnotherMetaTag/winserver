import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class WorkOrderMgmt {
	static Connection con = null;

	public static void main(String[] args) {
		new WorkOrderMgmt();
	}
	
	public WorkOrderMgmt() {
		//setting mainframe attributes
		JFrame gui_frame = new JFrame();
		gui_frame.setLayout(new GridLayout(2,1));
		gui_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui_frame.setTitle("Work Order Management System");
		gui_frame.setSize(400,200);
		gui_frame.setLocationRelativeTo(null);
		
		//input Panel where user will enter their information to submit
		final JPanel inputPanel = new JPanel();
		inputPanel.setLayout(new GridLayout(4,1));
		JLabel infoText = new JLabel("Valid entries are numbers 10001 through 10013");
		JLabel apptLabel = new JLabel("Appointment Number:");
		JTextField apptCode = new JTextField("",20);
		JButton submitAppt = new JButton("Submit");
		inputPanel.add(infoText);
		inputPanel.add(apptLabel);
		inputPanel.add(apptCode);
		inputPanel.add(submitAppt);
		
		//output panel where query results will display
		final JPanel outputPanel = new JPanel();
		outputPanel.setLayout(new GridLayout(6,2));
		JLabel firstName = new JLabel("First Name:");
		JLabel lastName = new JLabel("Last Name:");
		JLabel phoneNo = new JLabel("Phone #:");
		JLabel apptType = new JLabel("Type:");
		JLabel apptDate = new JLabel("Date:");
		JLabel apptTime = new JLabel("Time:");
		JTextField firstNameText = new JTextField("",20);
		JTextField lastNameText = new JTextField("",20);
		JTextField phoneNoText = new JTextField("",20);
		JTextField apptTypeText = new JTextField("",20);
		JTextField apptDateText = new JTextField("",20);
		JTextField apptTimeText = new JTextField("",20);
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
		
		//adding both panels to the main frame
		gui_frame.add(inputPanel);
		gui_frame.add(outputPanel);
		gui_frame.getRootPane().setDefaultButton(submitAppt);
		gui_frame.setVisible(true);

		//button action listener to connect to sql database, run the statement, and provide the appropriate output
		submitAppt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String text = apptCode.getText();
				//Create string for the long query to reference later
				String query = "SELECT C.FIRST_NAME, C.LAST_NAME, C.PHONE_NUMBER, A.APPT_TYPE, A.APPT_DATE, A.APPT_TIME "
						+ "FROM CUSTOMER C, APPOINTMENTS A "
						+ "WHERE C.CUST_ID = A.CUST_ID "
						+ "AND APPT_ID = ?";
				try {
					//Connecting to the database
					Class.forName("com.mysql.cj.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://winserver.serverpit.com/testworkordermgmt?user=projectAccount&password=ics311project");

					//Creating statement using query from earlier
					PreparedStatement pstm = con.prepareStatement(query);
					//passing input value from inputPanel GUI into the query
					pstm.setString(1, apptCode.getText());
					
					//Execute query
					ResultSet rs = pstm.executeQuery();
					
					//setting text based on query results (or dialog for empty data set)
					if (rs.next()) {
						do {
						firstNameText.setText(rs.getString("first_name"));
						lastNameText.setText(rs.getString("last_name"));
						phoneNoText.setText(rs.getString("phone_number"));
						apptTypeText.setText(rs.getString("appt_type"));
						apptDateText.setText(rs.getString("appt_date"));
						apptTimeText.setText(rs.getString("appt_time"));
						}while(rs.next());
					}else {
						JOptionPane.showMessageDialog(null, "No data found!");
						firstNameText.setText(" ");
						lastNameText.setText(" ");
						phoneNoText.setText(" ");
						apptTypeText.setText(" ");
						apptDateText.setText(" ");
						apptTimeText.setText(" ");
					}
					
					
				} catch (Exception ex) { 
					//Error message if you have connection issues
					JOptionPane.showMessageDialog(null, ex.getMessage());
					
				}
				
				
			}
		});
	}

}
