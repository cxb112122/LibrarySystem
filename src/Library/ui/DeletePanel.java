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
import Library.services.BookService;

public class DeletePanel extends JPanel {
	
	private ArticleService aService = null;
	private JTextField ID=null;
	private Object deletePanel;
	private Object currentPanel;
	private Object listPanel;
	private int idm;
	private BookService bService=null;
	
	public DeletePanel(ArticleService aService,BookService b) {
		this.aService = aService;
		this.bService=b;
		
		this.setLayout(new BorderLayout());
		JPanel createDeletePanel = this.createArticleDeletePanel();
		this.add(createDeletePanel, BorderLayout.CENTER);
	}

	private JPanel createArticleDeletePanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
		this.ID = new JTextField();
		this.ID.setPreferredSize(new Dimension(100, 30));

		toReturn.add(new JLabel("ID:"));
		toReturn.add(this.ID);

		
		JButton deleteButton = new JButton("Delete Publication");
		toReturn.add(deleteButton);
		deleteButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					deleteArticle();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		return toReturn;
	}
	
	private boolean deleteArticle() throws SQLException {
		String ID ;
		try {
			ID = this.ID.getText();
		}
		catch (NumberFormatException nfe) {
			JOptionPane.showMessageDialog(this, "ID was not in proper numeric format.");
			return false;
		}
		if(ID.charAt(ID.length()-1)=='0'){
		if (this.aService.deleteArticle(Integer.parseInt(ID))) {
			this.ID.setText("");
			
			return true;
		}
		}
		
		if(ID.charAt(ID.length()-1)=='1'){
			if (this.bService.deleteBook(Integer.parseInt(ID))) {
				this.ID.setText("");
				
				return true;
			}
		}
		
		
		return false;
	}

	
	
	
}
