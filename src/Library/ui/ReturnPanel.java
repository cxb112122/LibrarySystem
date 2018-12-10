package Library.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Library.services.ArticleService;
import Library.services.BookService;

public class ReturnPanel extends JPanel{
	private BookService bService=null;
	private ArticleService aService = null;
	private JTextField ArticleID=null;
	private JTextField UserID=null;
	
	public ReturnPanel(ArticleService aService2) {
		this.aService = aService2;
		this.setLayout(new BorderLayout());
		JPanel createArticlePanel = this.createReturnPanel();
		this.add(createArticlePanel, BorderLayout.CENTER);
	}
	public ReturnPanel(ArticleService aService2,BookService bService) {
		this.aService = aService2;
		this.bService=bService;
		this.setLayout(new BorderLayout());
		JPanel createArticlePanel = this.createReturnPanel();
		this.add(createArticlePanel, BorderLayout.CENTER);
	}
	
	private JPanel createReturnPanel() {
		JPanel toReturn = new JPanel();
		toReturn.setLayout(new FlowLayout());
//		this.newID = new JTextField();
//		this.newID.setPreferredSize(new Dimension(100, 30));
		this.ArticleID = new JTextField();
		this.ArticleID.setPreferredSize(new Dimension(100, 30));
//		this.UserID = new JTextField();
//		this.UserID.setPreferredSize(new Dimension(100, 30));
		
		toReturn.add(new JLabel("ArticleID/BookID:"));
		toReturn.add(this.ArticleID);

//		toReturn.add(new JLabel("UserID:"));
//		toReturn.add(this.UserID);
//		
		
		
		JButton addButton = new JButton("Return Article/Book");
		toReturn.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					returnArticle();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		return toReturn;
	}
	private boolean returnArticle() throws SQLException {
			String ID=this.ArticleID.getText();
		if(ID.charAt(ID.length()-1)=='0'){
			if (this.aService.returnArticle(Integer.parseInt(this.ArticleID.getText()),0)) {
				this.ArticleID.setText("");
				//this.UserID.setText("");
				return true;
			}
		}
		else if (ID.charAt(ID.length()-1)=='1'){
			if (this.bService.returnBook(Integer.parseInt(this.ArticleID.getText()),1)) {
				this.ArticleID.setText("");
				//this.UserID.setText("");
				return true;
			}
		}
		
		
		
		
		return false;
	}
	

}
