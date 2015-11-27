import java.io.*;
import java.util.*;
import java.lang.Object;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

class filechooser implements ActionListener{
	
	private JFrame frame= new JFrame("File section");
	private JLabel lb_file = new JLabel("File");
	private JButton btn_file = new JButton("select file");
	public filechooser(){
		Font fnt_btn = new Font("Consola", Font.BOLD, 12);
		Font fnt_lb = new Font("Consola", Font.BOLD, 20);
		lb_file.setFont(fnt_lb);
		btn_file.setFont(fnt_btn);
		frame.add(this.btn_file);
		frame.add(this.lb_file);
		this.frame.setSize(300,200);
		this.frame.setVisible(true);
		this.frame.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent arg0){
				System.exit(1);
			}
		});
		this.btn_file.addActionListener(this);
		
		frame.setSize(350,250);
		frame.setVisible(true);
		
		btn_file.setBounds(5,5,100,30);
		this.lb_file.setBounds(50,50,100,30);
	}
	public void actionPerformed(ActionEvent e){
		File file = null;
		int result =0;
		JFileChooser fc = new JFileChooser();
		if(e.getSource() == this.btn_file){
			this.lb_file.setText("");
			fc.setApproveButtonText("yes");
			fc.setDialogTitle("open the directory");
			result = fc.showOpenDialog(this.frame);
			
			if(result == JFileChooser.APPROVE_OPTION){
				
				file = fc.getSelectedFile();
				this.lb_file.setText("the selected file"+file.getName());
			}
			else if(result == JFileChooser.CANCEL_OPTION){
				this.lb_file.setText("no file is selected");
			}
			else{
				this.lb_file.setText("operation error");
			}
		}
		
	}
}
public class GUI_FileChooser{
	public static void main(String[] args){
		
		filechooser a = new filechooser();
		
		
	}
}