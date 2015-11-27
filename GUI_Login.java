import java.io.*;
import java.util.*;
import java.lang.Object;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class GUI_login{
		public static void main(String[] args){
			
			JFrame frame = new JFrame("Login");
			
			JLabel lb_account = new JLabel("account");
			JLabel lb_password = new JLabel("Password");
			JLabel lb_login_state = new JLabel("Welcome to the Login System");
			JButton btn_submit= new JButton("submit");
			JTextField user = new JTextField();
			JPasswordField user_password = new JPasswordField();
			
			Font fnt = new Font("Consola", Font.BOLD, 14);
			lb_login_state.setFont(fnt);
			lb_account.setFont(fnt);
			lb_password.setFont(fnt);
			btn_submit.setFont(fnt);
			btn_submit.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent arg0){
					if(arg0.getSource() == btn_submit){
						String name = user.getText();
						String pwd = new String(user_password.getPassword());
						System.out.println(name);
						System.out.println(pwd);
						lb_account.setVisible(false);
						lb_password.setVisible(false);
						user.setVisible(false);
						user_password.setVisible(false);
						btn_submit.setVisible(false);
						lb_login_state.setText("Login Succcessfully!");
						try{
							Thread.sleep(3000);
						}catch(Exception e){
							e.printStackTrace();
						}
						System.exit(1);
					}
					else{
						lb_login_state.setText("Wrong account or password");							
					}
				}
		});
		frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0){
				System.exit(1);
			}
		});
		
			lb_account.setBounds(5,5,100,30);
			lb_password.setBounds(5,40,100,30);
			user.setBounds(110,5,100,30);
			user_password.setBounds(110,40,100,30);
			btn_submit.setBounds(220,40,100,30);
			lb_login_state.setBounds(5,65,250,30);
			
			frame.setLayout(null);
			frame.setSize(350,200);
			frame.setBackground(Color.WHITE);
			frame.setLocation(500,300);
			frame.add(lb_account);
			frame.add(lb_password);
			frame.add(user);
			frame.add(user_password);
			frame.add(btn_submit);
			frame.add(lb_login_state);
			frame.setVisible(true);
			
		}
}