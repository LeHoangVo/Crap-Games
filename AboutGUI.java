import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class AboutGUI extends JFrame{
	
	public AboutGUI(){
		super("About");
		this.setSize(300, 300);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE); // exits program when x is clicked
		this.setLocationRelativeTo(null); // shows up on center of screen
		this.setLayout(new FlowLayout());
		

	}

	public static void main(String[] args) {

	}
}