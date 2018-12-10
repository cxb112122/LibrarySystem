package Library.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Library.services.ArticleService;
import Library.services.BookService;
import Library.services.RoomService;
//import Library.services.SodaService;

public class Frame extends JFrame {

	private JMenuItem articleList = null;
	private JMenu menu;
	private JMenu menuL;
	private JMenuItem restaurantList;
	private JMenuItem addArticle;
	private JMenuItem returnArticle;
	
	private UserPanel listPanel;
	private JPanel addPanel;
	private JPanel containerPanel;
	
	private JPanel returnPanel;
	private JPanel currentPanel = null;
	private JPanel borrowPanel=null;
	private JMenuItem borrowArticle=null;
	private JMenuItem borrowMaterial;
	private JMenuItem reserveRoom;
	private JPanel reserveRoomPanel;
	private JMenuItem addBook;
	private JPanel addBookPanel;
	private JMenuItem delete;
	private JPanel deletePanel;
	public Frame(ArticleService aService,BookService bService,RoomService rService,int check) {
		super();
		this.setSize(3000, 3000);
		this.setResizable(true);
		this.setTitle("Library");
		this.containerPanel = new JPanel();
		
		this.listPanel = new UserPanel(aService);
		this.addPanel = getAddPanel(aService);
		
		///--------------------------------------
		this.reserveRoomPanel = new ReserveRoomPanel(rService);
        this.reserveRoomPanel.setVisible(false);
		//-------------------------------------
		
		this.returnPanel=this.getReturnPanel(aService,bService);
		this.returnPanel.setVisible(true);
		this.addBookPanel=this.getAddBookPanel(bService);
		this.addBookPanel.setVisible(false);
		this.deletePanel=this.getdelete(aService,bService);
		this.deletePanel.setVisible(false);
		
		
		this.borrowPanel=this.getBorrowPanel(aService,bService);
		this.borrowPanel.setVisible(true);
		this.currentPanel = this.listPanel;
		if(check==0)this.addPanel.setVisible(false);
		//System.out.println(check);
		this.menu = new JMenu("Function");
		this.menuL=new JMenu("Edit Material");
		
		this.articleList = new JMenuItem("Article List");
		this.articleList.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToArticleList();
				
			}

		}));
		//===================
		this.reserveRoom=new JMenuItem("Reserve Room");
        this.reserveRoom.addActionListener((new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                        switchToReserveRoom();
                }

        }));
        
        this.delete=new JMenuItem("delete Material");
        this.delete.addActionListener((new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                        switchTodelete();
                }

        }));
        
        
		//===================
		this.addArticle = new JMenuItem("Add Articles");
		this.addArticle.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToAddArticle();
			}

		}));
		this.addBook=new JMenuItem("Add Book");
		
		
		this.addBook.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchToAddBook();
			}

		}));
		
		this.returnArticle=new JMenuItem("return Material");
		this.returnArticle.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchTOReturn();
			}

		}));
		
		this.borrowMaterial=new JMenuItem("Borrow Material");
		this.borrowMaterial.addActionListener((new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				switchTOBorrow();
			}

		}));
		
		//System.out.println();
		
		this.menu.add(this.articleList);
		if(check==1){this.menu.add(this.addArticle);
		
		
		this.menu.add(this.addBook);
		this.menu.add(this.delete);}
		this.menu.add(this.reserveRoom);
		this.menuL.add(this.returnArticle);
		this.menuL.add(this.borrowMaterial);
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(this.menu);
		menuBar.add(this.menuL);
		this.add(menuBar, BorderLayout.NORTH);
		//System.out.println(this.containerPanel);
		this.add(this.containerPanel, BorderLayout.CENTER);
		
		this.containerPanel.add(this.listPanel,BorderLayout.CENTER);
		this.containerPanel.add(this.addPanel,BorderLayout.CENTER);
		this.addPanel.setVisible(false);
		this.containerPanel.add(this.returnPanel,BorderLayout.CENTER);
		this.returnPanel.setVisible(false);
		this.containerPanel.add(this.borrowPanel,BorderLayout.CENTER);
		this.borrowPanel.setVisible(false);
		this.containerPanel.add(this.reserveRoomPanel,BorderLayout.CENTER);
		this.containerPanel.add(this.addBookPanel, BorderLayout.CENTER);
		this.containerPanel.add(this.deletePanel, BorderLayout.CENTER);
		this.setVisible(true);
		this.pack();
	}
	private JPanel getdelete(ArticleService aService, BookService bService) {
		JPanel toReturn = new JPanel();
		GridLayout layout = new GridLayout(1,1);
		layout.setVgap(100);
		toReturn.setLayout(layout);
		//
		toReturn.add(new DeletePanel(aService,bService));
		
		
	
		return toReturn;
		
	}
	public void setCheck(int ch){
		if(ch==0){
			this.menu.remove(this.addArticle);
			this.containerPanel.remove(this.addPanel);
		}
	}
	private JPanel getAddPanel(ArticleService aService) {
		JPanel toReturn = new JPanel();
		GridLayout layout = new GridLayout(3,1);
		layout.setVgap(100);
		toReturn.setLayout(layout);
		
		toReturn.add(new AddArticlePanel(aService));
		//toReturn.add(new DeleteArticlePanel(aService,bService);
		
	
		return toReturn;
		
	}
	
	private JPanel getAddBookPanel(BookService bService) {
		JPanel toReturn = new JPanel();
		GridLayout layout = new GridLayout(3,1);
		layout.setVgap(100);
		toReturn.setLayout(layout);
		
		toReturn.add(new AddBookPanel(bService));
		
	
		return toReturn;
		
	}
	
	private JPanel getReturnPanel(ArticleService aService,BookService bService){
		JPanel toReturn = new JPanel();
		GridLayout layout = new GridLayout(3,1);
		layout.setVgap(100);
	    toReturn.setLayout(layout);
		toReturn.add(new ReturnPanel(aService,bService));
		return toReturn;
	}
	private void switchToAddBook(){
		if(this.addBookPanel==this.currentPanel)
     		return;
     	JPanel beforePanel = this.currentPanel;
     	this.currentPanel= this.addBookPanel;
     	this.switchPanel(beforePanel);
	}
	
	
	 private void switchToReserveRoom(){
     	if(this.reserveRoomPanel==this.currentPanel)
     		return;
     	JPanel beforePanel = this.currentPanel;
     	this.currentPanel= this.reserveRoomPanel;
     	this.switchPanel(beforePanel);
     }
	 
	 private void switchTodelete(){
	     	if(this.deletePanel==this.currentPanel)
	     		return;
	     	JPanel beforePanel = this.currentPanel;
	     	this.currentPanel= this.deletePanel;
	     	this.switchPanel(beforePanel);
	     }
	 
	private JPanel getBorrowPanel(ArticleService aService,BookService bService){
		JPanel toBorrow = new JPanel();
		GridLayout layout = new GridLayout(3,1);
		layout.setVgap(100);
	    toBorrow.setLayout(layout);
		toBorrow.add(new BorrowPanel(aService,bService));
		return toBorrow;
	}
	
	
	
	private void switchToArticleList() {
		if (this.listPanel == this.currentPanel) {
			return;
		}
		JPanel tem=this.currentPanel;
		this.currentPanel = this.listPanel;
		this.listPanel.updateLists();
		this.switchPanel(tem);
	
	}

	private void switchPanel(Component toRemove) {
		toRemove.setVisible(false);
		this.currentPanel.setVisible(true);
		
	}

	private void switchToAddArticle() {
		if (this.addPanel == this.currentPanel) {
			return;
		}
		JPanel tem=this.currentPanel;
		this.currentPanel = this.addPanel;
		this.switchPanel(tem);
	
	}
	
	private void switchTOReturn(){
		if (this.returnPanel == this.currentPanel) {
			return;
		}
		JPanel tem=this.currentPanel;
		this.currentPanel = this.returnPanel;
		this.switchPanel(tem);
//	
	}
	
	private void switchTOBorrow(){
		if (this.borrowPanel == this.currentPanel) {
			return;
		}
		JPanel tem=this.currentPanel;
		this.currentPanel = this.borrowPanel;
		this.switchPanel(tem);
	
	}
}
