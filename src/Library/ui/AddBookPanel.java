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

import Library.services.BookService;

public class AddBookPanel extends JPanel {
	
	private BookService bService = null;
	private JTextField newID=null;
	private JTextField newTitle;
	private JTextField newPublisher;
	private JTextField newLibraryID;
	private JTextField newAuthor;
	private JTextField newPermissiontype;
	private JTextField newEdition;
	private JTextField newISBN;

	
	public AddBookPanel(BookService bService2) {
		this.bService = bService2;
		this.setLayout(new BorderLayout());
		JPanel createBookPanel = this.createBookAddtionPanel();
		this.add(createBookPanel, BorderLayout.CENTER);
	}

	private JPanel createBookAddtionPanel() {
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
		this.newEdition = new JTextField();
		this.newEdition.setPreferredSize(new Dimension(100, 30));
		this.newISBN = new JTextField();
		this.newISBN.setPreferredSize(new Dimension(100, 30));
		this.newAuthor = new JTextField();
		this.newAuthor.setPreferredSize(new Dimension(100, 30));
		this.newPermissiontype = new JTextField();
		this.newPermissiontype.setPreferredSize(new Dimension(100, 30));
		
//		toReturn.add(new JLabel("ID:"));
//		toReturn.add(this.newID);

		toReturn.add(new JLabel("Title:"));
		toReturn.add(this.newTitle);

		toReturn.add(new JLabel("Publisher:"));
		toReturn.add(this.newPublisher);
		
		toReturn.add(new JLabel("LibraryID:"));
		toReturn.add(this.newLibraryID);

		toReturn.add(new JLabel("Edition:"));
		toReturn.add(this.newEdition);

		toReturn.add(new JLabel("ISBN:"));
		toReturn.add(this.newISBN);
		
		toReturn.add(new JLabel("Author:"));
		toReturn.add(this.newAuthor);
		
		toReturn.add(new JLabel("Permissiontype:"));
		toReturn.add(this.newPermissiontype);
//		
//		toReturn.add(new JLabel("Availability:"));
//		toReturn.add(this.newavailability);
		
		JButton addButton = new JButton("Add Book");
		toReturn.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					addBook();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		return toReturn;
	}
	
	private boolean addBook() throws SQLException {
		int ID ;
		Double LibraryID = null;
		Double edition = null;
		Double ISBN = null;
		Random rnd = new Random();
		ID = 10000000 + rnd.nextInt(90000000);
		ID=ID*10+1;
//		try {
//			ID = Double.parseDouble(this.newID.getText());
//		}
//		catch (NumberFormatException nfe) {
//			JOptionPane.showMessageDialog(this, "ID was not in proper numeric format.");
//			return false;
//		}
		try {
			LibraryID = Double.parseDouble(this.newLibraryID.getText());
		}
		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "LibraryID was not in proper numeric format.");
			return false;
		}
		try {
			edition = Double.parseDouble(this.newEdition.getText());
		}
		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "Edition was not in proper numeric format.");
			return false;
		}
		try {
			ISBN = Double.parseDouble(this.newISBN.getText());
		}
		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "ISBN was not in proper numeric format.");
			return false;
		}
		if (this.bService.addBook(ID, this.newTitle.getText(), this.newPublisher.getText(), LibraryID, edition, ISBN,
				this.newAuthor.getText(), this.newPermissiontype.getText())) {
			this.newTitle.setText("");
			this.newPublisher.setText("");
			this.newLibraryID.setText("");
			this.newAuthor.setText("");
			this.newPermissiontype.setText("");

			return true;
		}
		return false;
	}
	
	
}
