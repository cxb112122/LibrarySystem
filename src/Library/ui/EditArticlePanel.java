package Library.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Library.services.ArticleService;

public class EditArticlePanel extends JPanel {
	
	private ArticleService aService = null;
	private JTextField ID=null;
	private Object deletePanel;
	private Object currentPanel;
	private Object listPanel;
	private JTextField newID;
	

	
	public EditArticlePanel(ArticleService aService) {
		this.aService = aService;
		this.setLayout(new BorderLayout());
		JPanel createEditPanel = this.createArticleEditPanel();
		this.add(createEditPanel, BorderLayout.CENTER);
	}

	private JPanel createArticleEditPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
		this.ID = new JTextField();
		this.ID.setPreferredSize(new Dimension(100, 30));

		toReturn.add(new JLabel("ID:"));
		toReturn.add(this.ID);
		
		this.newID = new JTextField();
		this.newID.setPreferredSize(new Dimension(100, 30));

		toReturn.add(new JLabel("new ID:"));
		toReturn.add(this.newID);
		
		JButton editButton = new JButton("ID Update");
		toReturn.add(editButton);
		editButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					editAuthor();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		return toReturn;
	}
	
	private boolean editAuthor() throws SQLException {
		Double ID = null;
		String Author = null;
		try {
			ID = Double.parseDouble(this.ID.getText());
			Author = this.newID.getText();
		}
		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "ID was not in proper numeric format.");
			return false;
		}
		
		if (this.aService.editAuthor(ID, Author)) {
			this.ID.setText("");
			return true;
		}
		return false;
	}

	
	
	
}
