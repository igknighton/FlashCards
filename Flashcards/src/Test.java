import java.awt.BorderLayout;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class Test {
	public static void main(String[] arg) {
		 SwingUtilities.invokeLater(new Runnable() {
	            public void run() {
	                createGUI(); 
	            }
	      });		
	}
	public static void createGUI() {
		FlashCardFrame fcf = new FlashCardFrame();
	}
}