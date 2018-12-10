package Library.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPrompt extends JFrame {

	private JTextField usernameBox = null;
	private JTextField passwordBox = null;
	private JButton loginButton = null;
	private JButton registerButton = null;
	private JTextField regusernameBox;
	private JPasswordField regpasswordBox;
	private JTextField regNameBox;
	private JComboBox<String> userComboBox;
	private JTextField regIDBox;
	
	public LoginPrompt(LoginComplete complete) {
		GridLayout layout = new GridLayout(1,2,10,10);
		JPanel f = new JPanel();
		f.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		f.setLayout(layout);
		f.add(getLoginPanel(complete));
		f.add(getRegisterPanel(complete));
		this.add(f);
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}
	private JPanel getRegisterPanel(LoginComplete complete) {
		this.userComboBox =new JComboBox<String>();
		userComboBox.addItem("None");
		userComboBox.addItem("Other");
		userComboBox.addItem("Student");
		userComboBox.addItem("Professor");
		JPanel panel = new JPanel();
		this.regusernameBox = new JTextField();
		this.regpasswordBox = new JPasswordField();
		this.regIDBox = new JTextField();
		this.regNameBox = new JTextField();
		GridLayout layout = new GridLayout(6,2);
		layout.setHgap(50);
		layout.setVgap(25);
		panel.setLayout(layout);
		panel.add(new JLabel("Username:"));
		panel.add(this.regusernameBox);
		panel.add(new JLabel("Password:"));
		panel.add(this.regpasswordBox);
		panel.add(new JLabel("UserType:"));
		panel.add(this.userComboBox);
		panel.add(new JLabel("Professor/Student ID:"));
		panel.add(this.regIDBox);
		panel.add(new JLabel("Name:"));
		panel.add(this.regNameBox);
		this.registerButton = new JButton("Register");
		
		this.registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				complete.register(Integer.parseInt(regIDBox.getText()) ,regusernameBox.getText(), (String) userComboBox.getSelectedItem() ,regpasswordBox.getText(), regNameBox.getText());
			}
		});
		
		panel.add(this.registerButton);
		return panel;

	}
//	private int checkmanager(){
//		if(this.usernameBox.getText().charAt(0)=='*'){
//			return 1;
//		}
//		return 0;
//	}
	private JPanel getLoginPanel(LoginComplete complete) {
		JPanel panel = new JPanel();
		this.usernameBox = new JTextField();
		this.passwordBox = new JPasswordField();
		GridLayout layout = new GridLayout(5,2);
		layout.setHgap(25);
		layout.setVgap(25);
		panel.setLayout(layout);
		panel.add(new JLabel("Username:"));
		panel.add(this.usernameBox);
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));
		panel.add(new JLabel("Password:"));
		panel.add(this.passwordBox);
		panel.add(new JLabel(""));
		panel.add(new JLabel(""));
		this.loginButton = new JButton("Login");
		
		this.loginButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				complete.login(usernameBox.getText(), passwordBox.getText());
			}
		});

		panel.add(this.loginButton);
		panel.setBorder(BorderFactory.createMatteBorder(0,0,0, 2, Color.BLACK));
		return panel;

	}
}