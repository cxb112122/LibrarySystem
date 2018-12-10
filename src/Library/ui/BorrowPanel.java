package Library.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import Library.services.ArticleService;
import Library.services.BookService;

public class BorrowPanel extends JPanel{
	private BookService bService=null;
	private ArticleService aService = null;
	private JTextField ArticleID=null;
	private JTextField UserID=null;
	private JTextField idate=null;
	private JTextField ddate=null;
	private JTable BorrowTable=null;
	private JScrollPane createtable=null;
	
	public BorrowPanel(ArticleService aService2) {
		this.aService = aService2;
		this.setLayout(new BorderLayout());
		
		JPanel createArticlePanel = this.createBorrowPanel();
		this.add(createArticlePanel, BorderLayout.CENTER);
	}
	public BorrowPanel(ArticleService aService2,BookService bService) {
		this.aService = aService2;
		this.bService=bService;
		this.setLayout(new BorderLayout());
		this.createtable=this.generateBorrowTable();
		JPanel createArticlePanel = this.createBorrowPanel();
		
		//createArticlePanel.add(generateBorrowTable());
		
		this.add(createArticlePanel, BorderLayout.NORTH);
		this.add(createtable,BorderLayout.CENTER);
		
	}
	private BorrowTableModel search() {
		
		ArrayList<Borrow> data = this.aService.getBorrow();
		return new BorrowTableModel(data);
	}
	
	private JScrollPane generateBorrowTable(){
		this.BorrowTable = new JTable(this.search());
		JScrollPane scrollPane = new JScrollPane(this.BorrowTable);
		this.BorrowTable.setFillsViewportHeight(true);
		scrollPane.setSize(60, 60);
	
		return scrollPane;
		
	}
	private JPanel createBorrowPanel() {
		JPanel toBorrow = new JPanel();
		toBorrow.setLayout(new FlowLayout());
//		this.newID = new JTextField();
//		this.newID.setPreferredSize(new Dimension(100, 30));
		this.ArticleID = new JTextField();
		this.ArticleID.setPreferredSize(new Dimension(100, 30));
//		this.UserID = new JTextField();
//		this.UserID.setPreferredSize(new Dimension(100, 30));

		toBorrow.add(new JLabel("ArticleID/BookID:"));
		toBorrow.add(this.ArticleID);

//		toBorrow.add(new JLabel("UserID:"));
//		toBorrow.add(this.UserID);
		
		
		
		
		JButton addButton = new JButton("Borrow Article/Book");
		toBorrow.add(addButton);
		addButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					BorrowArticle();
					BorrowTable.setModel(search());
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			
		});
		
		return toBorrow;
	}
	private boolean BorrowArticle() throws SQLException {
		//this.remove(createtable);
		//createtable=this.generateBorrowTable();
		//this.add(createtable);
		
			String ID=this.ArticleID.getText();
		if(ID.charAt(ID.length()-1)=='0'){
			if (this.aService.BorrowArticle(Integer.parseInt(this.ArticleID.getText()),0)) {
				this.ArticleID.setText("");
				//this.UserID.setText("");
				return true;
			}
		}
		else if (ID.charAt(ID.length()-1)=='1'){
			if (this.bService.BorrowBook(Integer.parseInt(this.ArticleID.getText()),1)) {
				this.ArticleID.setText("");
				//this.UserID.setText("");
				return true;
			}
		}
		
		
		
		
		return false;
	}
	/////////////////////////////////


}
