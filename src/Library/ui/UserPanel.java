package Library.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.plaf.synth.SynthSeparatorUI;

import Library.services.ArticleService;
import Library.services.BookService;
//import Library.services.SodaService;

public class UserPanel extends JPanel {
	
	private JComboBox<String> restaurantComboBox = null;
	private JComboBox<String> sodaComboBox = null;
	private JTextField IDField = null;
//	private SodaService sService = null;
	private JButton filterButton = null;
	//private JTable sodasByRestTable = null;
	private ArticleService srService;
	
	private ArticleService aService;
	private BookService bService=null;
	private JComboBox<String> ArticleComboBox;
	private JTable articleTable=null;
	
	public UserPanel(ArticleService aService) {
		this.aService = aService;
		JPanel filterPanel = generateFilterUiItems();
		this.setLayout(new BorderLayout());
		this.add(filterPanel, BorderLayout.NORTH);
		JScrollPane tablePane = generateArticleTable();
		this.add(tablePane, BorderLayout.CENTER);
	}
	
	public UserPanel(ArticleService aService,BookService bService) {
		this.aService = aService;
		this.bService=bService;
		JPanel filterPanel = generateFilterUiItems();
		this.setLayout(new BorderLayout());
		this.add(filterPanel, BorderLayout.NORTH);
		JScrollPane tablePane = generateArticleTable();
		this.add(tablePane, BorderLayout.CENTER);
	}

	private JScrollPane generateArticleTable(){
		this.articleTable = new JTable(this.search());
		JScrollPane scrollPane = new JScrollPane(this.articleTable);
		this.articleTable.setFillsViewportHeight(true);
		return scrollPane;
		
	}

	private JPanel generateFilterUiItems() {
		JPanel filterPanel = new JPanel();
		this.ArticleComboBox = new JComboBox<String>();
		this.populateArticles();
		this.IDField = new JTextField();
		FlowLayout layout = new FlowLayout();
		layout.setHgap(15);
		filterPanel.setLayout(layout);
		filterPanel.add(new JLabel("ID"));
		filterPanel.add(this.IDField);
		this.IDField.setPreferredSize(new Dimension(200,30));
		this.filterButton = new JButton("Search");
		filterPanel.add(filterButton);
		this.filterButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				articleTable.setModel(search());
			}
		});
		return filterPanel;
	}
//	
	private LibraryTableModel search() {
		String selectedArt = (String)this.ArticleComboBox.getSelectedItem();
		Double ID =0.0;
		if(!IDField.getText().isEmpty())
			ID= Double.parseDouble(this.IDField.getText());
		String restSearch = selectedArt == "None" ? null : selectedArt;
		ArrayList<Article> data = this.aService.getArticle(ID);
		return new LibraryTableModel(data);
	}
	
	private void populateArticles() {
		this.ArticleComboBox.addItem("None");
		for (String s : this.aService.getArticlesTitle()) {
			this.ArticleComboBox.addItem(s);
		}
	}

	public void updateLists() {
		String curRest = (String)this.ArticleComboBox.getSelectedItem();
		this.ArticleComboBox.removeAllItems();
		this.populateArticles();
		findAndSelectItem(this.ArticleComboBox, curRest);
		this.articleTable.setModel(this.search());
	}
	
	private void findAndSelectItem(JComboBox<String> box, String item) {
		for (int i=0;i<box.getItemCount();i++) {
			if (box.getItemAt(i).equals(item)) {
				box.setSelectedItem(box.getItemAt(i));
				return;
			}
		}
	}
	
}
