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
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Library.services.ArticleService;

public class AddArticlePanel extends JPanel {
	
	private ArticleService aService = null;
	private JTextField newID=null;
	private JTextField newTitle;
	private JTextField newPublisher;
	private JTextField newLibraryID;
	private JTextField newAuthor;
	private JTextField newPermissiontype;
	
	private JTextField newDate;

	
	public AddArticlePanel(ArticleService aService2) {
		this.aService = aService2;
		this.setLayout(new BorderLayout());
		JPanel createArticlePanel = this.createArticleAddtionPanel();
		this.add(createArticlePanel, BorderLayout.CENTER);
	}

	private JPanel createArticleAddtionPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
//		this.newID = new JTextField();
//		this.newID.setPreferredSize(new Dimension(100, 30));
		this.newTitle = new JTextField();
		this.newTitle.setPreferredSize(new Dimension(100, 30));
		this.newPublisher = new JTextField();
		this.newPublisher.setPreferredSize(new Dimension(100, 30));
		this.newLibraryID = new JTextField();
		this.newLibraryID.setPreferredSize(new Dimension(100, 30));
		this.newAuthor = new JTextField();
		this.newAuthor.setPreferredSize(new Dimension(100, 30));
		this.newPermissiontype = new JTextField();
		this.newPermissiontype.setPreferredSize(new Dimension(100, 30));
		
		this.newDate = new JTextField();
		this.newDate.setPreferredSize(new Dimension(100, 30));
//		
//		
//		toReturn.add(new JLabel("ID:"));
//		toReturn.add(this.newID);

		toReturn.add(new JLabel("Title:"));
		toReturn.add(this.newTitle);

		toReturn.add(new JLabel("Publisher:"));
		toReturn.add(this.newPublisher);
		
		toReturn.add(new JLabel("LibraryID:"));
		toReturn.add(this.newLibraryID);
		
		toReturn.add(new JLabel("Author:"));
		toReturn.add(this.newAuthor);
		
		toReturn.add(new JLabel("Permissiontype:"));
		toReturn.add(this.newPermissiontype);
		
	
		
		toReturn.add(new JLabel("Published Date:"));
		toReturn.add(this.newDate);
		
		JButton addButton = new JButton("Add Article");
		toReturn.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addArticle();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		return toReturn;
	}
	
	private boolean addArticle() throws SQLException {
		int ID ;
		Double LibraryID = null;
		java.util.Date date =null;
		java.sql.Date sqldate=null;
		Random rnd = new Random();
		ID = 10000000 + rnd.nextInt(90000000);
		ID=ID*10;
		System.out.println(ID);

		try {
			LibraryID = Double.parseDouble(this.newLibraryID.getText());
		}
		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "LibraryID was not in proper numeric format.");
			return false;
		}
		try {
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			try {
				 date = format.parse(this.newDate.getText());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			sqldate = new java.sql.Date(date.getTime());
		}
		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Date was not in proper date format.");
			return false;
		}
		if (this.aService.addArticle(ID, this.newTitle.getText(), this.newPublisher.getText(), LibraryID, this.newAuthor.getText(), 
				this.newPermissiontype.getText(), sqldate)) {
			this.newTitle.setText("");
			this.newPublisher.setText("");
			this.newLibraryID.setText("");
			this.newAuthor.setText("");
			this.newPermissiontype.setText("");
			
			this.newDate.setText("");
			return true;
		}
		return false;
	}
	
	
}
