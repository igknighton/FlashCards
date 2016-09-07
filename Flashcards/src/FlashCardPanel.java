import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class FlashCardPanel extends JPanel {
	
	
	private String term;
	private String definition;
	
	// shows the current text whether it is a term or definition
	private JLabel currentLabel;
	private static String NO_CARDS = "This set is empty";
	//current card being displayed
	private FlashCard currentCard;
	//new card that is added to the deck
	private FlashCard newCard;
	// true = term is showing; false = definition is showing
	private boolean termShowing = true;
	private boolean isEnabled;
	private AddNewCard frame;
	private CardSet cardSet;
	private int cardIndex = 0;
	private GridLayout gridLayout = new GridLayout(1,1,30,0);
	private ButtonPanel bPanel;
	private LabelPanel lPanel;
	private TopButtonPanel tPanel;
	
	private JTextArea currentDefinitionArea = new JTextArea(6, 20);
	private CardLayout cardLayout = new CardLayout();

	private static JButton flipButton;
	private static JButton nextButton;
	private static JButton prevButton;
	private static JButton addCard;
	private static JButton deleteCard;
	private static JButton editButton;
	
	
	
	public FlashCardPanel(CardSet cardSet) {
		this.cardSet = cardSet;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		
		if (!cardSet.isEmpty()) {
		term = cardSet.get(0).getTerm();
		definition = cardSet.get(0).getDefintion();
		currentCard = cardSet.get(0);
		}
		
		currentDefinitionArea.setOpaque(false);
	    currentDefinitionArea.setText(definition);
	    currentDefinitionArea.setWrapStyleWord(true);
	    currentDefinitionArea.setLineWrap(true);
	    currentDefinitionArea.setEditable(false);
	    currentDefinitionArea.setFocusable(false);

		createButtons();
		
		tPanel = new TopButtonPanel();
		lPanel = new LabelPanel();
		bPanel = new ButtonPanel();
		
		
		if
		(cardSet.isEmpty()) {
		isEnabled = true;
		enableButtons();
		}
		
		add(tPanel);
		add(lPanel);
		add(bPanel);
		
	}
	
	public FlashCardPanel() {
		
		term = "";
		definition = "";
		currentCard = null;
		
		cardSet = new CardSet("New Set");
		currentDefinitionArea.setOpaque(false);
	    currentDefinitionArea.setText(definition);
	    currentDefinitionArea.setWrapStyleWord(true);
	    currentDefinitionArea.setLineWrap(true);
	    currentDefinitionArea.setEditable(false);
	    currentDefinitionArea.setFocusable(false);
	    
	    this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

		createButtons();
		
		tPanel = new TopButtonPanel();
		lPanel = new LabelPanel();
		bPanel = new ButtonPanel();
		
		
		isEnabled = true;
		enableButtons();
		
		add(tPanel);
		add(lPanel);
		add(bPanel);
		
	}
	private class LabelPanel extends JPanel {
			public LabelPanel() {
				
				this.setLayout(cardLayout);
				currentLabel = new JLabel(term, SwingConstants.CENTER);
				if (!cardSet.isEmpty())
				currentLabel.setText(cardSet.get(0).getTerm());
				else currentLabel.setText(NO_CARDS);
				add(currentLabel, "current label");
				add(new JScrollPane(currentDefinitionArea), "definition");	
			}	
	}
	private class TopButtonPanel extends JPanel {
		public TopButtonPanel() {
			this.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
			this.setLayout(gridLayout);
			add(editButton);
			add(flipButton);
		}
	}
	private class ButtonPanel extends JPanel {
		public ButtonPanel() {
			this.setLayout(new GridLayout(2,2,0,0));
			add(prevButton);
			add(nextButton);
			add(addCard);
			add(deleteCard);
		}
	}
	
	/*
	 * creates buttons for the panel. Should be called before
	 * any subPanel is created.
	 */
	private void createButtons() {
		 flipButton = new JButton(" Flip ");
		 flipButton.addActionListener(new ButtonListener());
		 flipButton.setActionCommand("1");
		 
		 nextButton = new JButton(" > ");
		 nextButton.addActionListener(new ButtonListener());
		 nextButton.setActionCommand("2");
		 
		 prevButton = new JButton(" < ");
		 prevButton.addActionListener(new ButtonListener());
		 prevButton.setActionCommand("3");
		 
		 addCard = new JButton(" Add Card ");
		 addCard.addActionListener(new ButtonListener());
		 addCard.setActionCommand("4");
		 
		 deleteCard = new JButton(" Delete Card ");
		 deleteCard.addActionListener(new ButtonListener());
		 deleteCard.setActionCommand("5");
		 
		 editButton = new JButton("Edit");
		 editButton.addActionListener(new ButtonListener());
		 editButton.setActionCommand("6");	
		 
	}
	
	private class ButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			int action = Integer.parseInt(e.getActionCommand());
			
			switch(action) {
			case 1:
				flipCard();
				break;
			case 2:
				nextCard();
				break;
			case 3:
				previousCard();
				break;
			case 4:
				createFrame();
				break;
			case 5:
				deleteCard();
				break;
			case 6:
				if (!cardSet.isEmpty())
				createFrame(term,definition);
				break;
			}
		}
	}
	
	private void flipCard() {
		if (!cardSet.isEmpty()) {
		if (termShowing) {
			termShowing = false;
			currentLabel.setText(definition);
			}
		else {
			termShowing = true;
			currentLabel.setText(term);
			}	
		cardLayout.next(lPanel);
		}
	}
	
	private void nextCard() {
		if (!cardSet.isEmpty()) {
		if (cardIndex == cardSet.size()-1) 
			cardIndex = 0;
		else 
			cardIndex++;
		
		
		term = cardSet.get(cardIndex).getTerm();
		definition = cardSet.get(cardIndex).getDefintion();
		if(termShowing)
			currentLabel.setText(term);
		else 
			currentLabel.setText(definition);
		
		currentCard = cardSet.get(cardIndex);
		
		currentDefinitionArea.setText(definition);
		}
		else JOptionPane.showMessageDialog(this, "This set is empty");
	}
	
	private void previousCard()	{
		if (!cardSet.isEmpty()) {
		if (cardIndex == 0) 
			cardIndex = cardSet.size()-1;
		else 
			cardIndex--;
		
		term = cardSet.get(cardIndex).getTerm();
		definition = cardSet.get(cardIndex).getDefintion();
		
		if(termShowing)
			currentLabel.setText(term);
		else
			currentLabel.setText(definition);
		
		currentCard = cardSet.get(cardIndex);
		currentDefinitionArea.setText(definition);
		}
		else JOptionPane.showMessageDialog(this, "This set is empty");
	}
	/*
	 * adding a card
	 */
	private void createFrame() {
		frame = new AddNewCard(100,100,this, cardSet);
	}
	/*
	 * editing an existing card
	 */
	private void createFrame(String t, String d) {
		frame = new AddNewCard(100,100,this,t,d);
	}
	
	
	public void addNewCard(String t, String d) {
		newCard = new FlashCard(t,d);
		if (cardSet.isEmpty()) {
			enableButtons();
			currentLabel.setText(newCard.getTerm());
			currentCard = newCard;
		}
		{
			term = newCard.getTerm();
			definition = newCard.getDefintion();
			currentDefinitionArea.setText(definition);
			cardSet.add(newCard);
			
		}

	}
	
	public void editCard(String t, String d) {
		currentCard.setTerm(t);
		currentCard.setDefinition(d);
		if (termShowing) currentLabel.setText(t);
		else currentLabel.setText(d);
	}
	
	/*
	 * Deletes current card on display
	 */
	private void deleteCard() {	
		
		if (!cardSet.isEmpty()) {
		// if on the last card of the set
		if (cardIndex == cardSet.size()-1) cardIndex--;
		
		cardSet.remove(currentCard);
		
		if (!cardSet.isEmpty()) {
			currentCard = cardSet.get(cardIndex);
			currentLabel.setText(cardSet.get(cardIndex).getTerm());
		}
		else { 
			currentLabel.setText(NO_CARDS);
			enableButtons();
		}
		
		}
	}
	private void enableButtons()
	{
		if (isEnabled)
			isEnabled = false;
		else isEnabled = true;
		
		editButton.setEnabled(isEnabled);
		flipButton.setEnabled(isEnabled);
		nextButton.setEnabled(isEnabled);
		prevButton.setEnabled(isEnabled);
		deleteCard.setEnabled(isEnabled);	
	}
	public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }
}
