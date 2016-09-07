import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;


public class FlashCardFrame extends JFrame {

	/*
	 * Constructor for MenuPanel
	 */
	public FlashCardFrame() {
		super("Flash Card Program");
   		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.add(new MenuPanel());
        this.pack();
        this.setVisible(true);     
	}
	
	/* 
	 * Constructor for FlashCardPanel 
	 */
	public FlashCardFrame(String nameOfSet, CardSet cardSet) {
		super(nameOfSet);
		
		
		this.add(new FlashCardPanel(cardSet));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
	    this.setVisible(true);
	}
	
	/*
	 * Constructor for QuizPanel
	 */
	public FlashCardFrame(String nameOfSet, CardSet cardSet,String quizPanel) {
		super(nameOfSet);
		
		this.add(new QuizPanel(cardSet));
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
	    this.setVisible(true);
		
	}
	
}
