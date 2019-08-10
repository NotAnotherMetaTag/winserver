import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class WorkOrderMgmt {
	static Connection con = null;

	/*
	 * Your main shouldn't have to include any business logic or constructors.
	 * made a separate file to host the main
	 */

	public WorkOrderMgmt() {
		//setting mainframe attributes
		/*
		 * Pull your GUI Build into a separate file
		 * Keep variables here for easy variable management
		 */

		//input Panel where user will enter their information to submit
		JLabel infoText = new JLabel("Valid entries are numbers 10001 through 10013");
		JLabel apptLabel = new JLabel("Appointment Number:");
		JTextField apptCode = new JTextField("",20);
		JButton submitAppt = new JButton("Submit");
		final JPanel inputPanel = GUIFormatter.makeInputPanel(infoText, apptLabel, apptCode, submitAppt);

		//output panel where query results will display
		JTextField firstNameText = new JTextField("",20);
		JTextField lastNameText = new JTextField("",20);
		JTextField phoneNoText = new JTextField("",20);
		JTextField apptTypeText = new JTextField("",20);
		JTextField apptDateText = new JTextField("",20);
		JTextField apptTimeText = new JTextField("",20);
		final JPanel outputPanel = GUIFormatter.makeOutputPanel(firstNameText, lastNameText, phoneNoText, 
				apptTypeText, apptDateText, apptTimeText);

		//adding both panels to the main frame
		JFrame gui_frame = GUIFormatter.makeFrame();
		gui_frame.add(inputPanel);
		gui_frame.add(outputPanel);
		gui_frame.getRootPane().setDefaultButton(submitAppt);
		gui_frame.setVisible(true);

		//button action listener to connect to sql database, run the statement, and provide the appropriate output
		submitAppt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//Create string for the long query to reference later
				String query = "SELECT C.FIRST_NAME, C.LAST_NAME, C.PHONE_NUMBER, A.APPT_TYPE, A.APPT_DATE, A.APPT_TIME "
						+ "FROM CUSTOMER C, APPOINTMENTS A "
						+ "WHERE C.CUST_ID = A.CUST_ID "
						+ "AND APPT_ID = ?";
				try {
					//Connecting to the database
					//this is a simple app, but its still a good idea to not store your password in plaintext in a url
					Class.forName("com.mysql.cj.jdbc.Driver");
					con = DriverManager.getConnection("jdbc:mysql://winserver.serverpit.com/testworkordermgmt?user=projectAccount&password=ics311project");

					//Creating statement using query from earlier
					PreparedStatement pstm = con.prepareStatement(query);
					//passing input value from inputPanel GUI into the query
					pstm.setString(1, apptCode.getText());

					//Execute query
					ResultSet rs = pstm.executeQuery();
					
					/*
					 * you could also pull these results into a
					 * successOutput (if) and failureOutput (else)
					 * if you do that, pay attention to whether your passing
					 * variables or making them global.
					 * 
					 * so something like processResults(rs);
					 * and 
					 * private void processResults(ResultSet rs) {
					 *   if(){
					 *   } else {
					 *   }
					 * }
					 * (LOC 97-112)
					 */

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
