import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


public class MenuPanel extends JPanel implements ListSelectionListener {

	private LinkedList<CardSet> setOfCards;
	private JButton createSet;
	private JButton deleteSet;
	private JButton openSet;
	private JButton quiz; 
	private JList list;
	private DefaultListModel dlm;
	private JTextField searchBar;
	private JFrame inputFrame;
	private CardSet selectedSet;
	private FlashCardFrame flashCardFrame;
	
	public MenuPanel() {
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(50,50,50,50));
		 JScrollPane scrollPane;
		
		setOfCards = new LinkedList<CardSet>();
		dlm = new DefaultListModel();
		list = new JList(dlm);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		if (!dlm.isEmpty()) list.setSelectedIndex(0);
		
		list.addListSelectionListener(this);
		list.setVisibleRowCount(5);
		scrollPane = new JScrollPane(list);	
		add(scrollPane);

		add(new ButtonPanel(),BorderLayout.SOUTH);
		if (dlm.isEmpty()) {
			deleteSet.setEnabled(false);
			openSet.setEnabled(false);
			quiz.setEnabled(false);
		}
	}
	
	
	private void createButtons() {
		createSet = new JButton("Create");
		deleteSet = new JButton("Delete");
		createSet.addActionListener(new ButtonListener());
		createSet.setActionCommand("1");
		deleteSet.addActionListener(new ButtonListener());
		deleteSet.setActionCommand("2");
		openSet = new JButton("Open");
		openSet.addActionListener(new ButtonListener());
		openSet.setActionCommand("3");
		quiz = new JButton ("Quiz");
		quiz.addActionListener(new ButtonListener());
		quiz.setActionCommand("4");
			
		
	}
	
	private class ButtonPanel extends JPanel {
		public ButtonPanel() {
			createButtons();
			add(openSet);
			add(createSet);
			add(quiz);
			add(deleteSet);
		}
	}
	private class ButtonListener implements ActionListener {	
		private int action;
		public void actionPerformed(ActionEvent e) {
			
			action = Integer.parseInt(e.getActionCommand());
			
			switch(action) {
			case 1:
				createSet();
				break;
			case 2:
				deleteSet();
				break;
			case 3:
				openSet();	
				break;
			case 4:
				quiz();
				break;
			}
		}
	}
	private boolean nullOrWhiteSpaceOnly(String s) {
		return s != null && !s.trim().isEmpty() && !s.trim().isEmpty();
	}
	
	private void createSet() {
		String newSetName;
		CardSet newCardSet;
		CreateDialog createDialog;
		createDialog = new CreateDialog(setOfCards);
		createDialog.setVisible(true);
		
		newSetName = createDialog.getSetName();
		
		if (nullOrWhiteSpaceOnly(newSetName)) {
		newCardSet = new CardSet(newSetName);
		setOfCards.add(newCardSet);
		dlm.addElement(newSetName);
		openSet.setEnabled(true);
		deleteSet.setEnabled(true);
		quiz.setEnabled(true);
		list.setSelectedIndex(dlm.size()-1);
		}
	}
	
	private void deleteSet() {
		int index = list.getSelectedIndex();
		dlm.remove(index);
		if (dlm.getSize() == 0) {
			deleteSet.setEnabled(false);
			openSet.setEnabled(false);
			quiz.setEnabled(false);
		}
		else if (index == dlm.getSize()) index--;
		list.setSelectedIndex(index);
		list.ensureIndexIsVisible(index);
	}
	
	private void openSet() {
		FlashCardPanel flashCardPanel;
		int index = list.getSelectedIndex();
		selectedSet = setOfCards.get(index);
		flashCardPanel = new FlashCardPanel(selectedSet);
		flashCardFrame = new FlashCardFrame(selectedSet.getTitle(),selectedSet);
	}
	private void quiz() {
		//System.out.println(selectedSet.size());
		if (selectedSet.size() > 3){
		int index = list.getSelectedIndex();
		selectedSet = setOfCards.get(index);
		flashCardFrame = new FlashCardFrame(selectedSet.getTitle(),selectedSet,"");
		}
		else JOptionPane.showMessageDialog(this, "Set must contain 4 or more cards");
	}
	public Dimension getPreferredSize() {
        return new Dimension(500,500);
    }
	
	public void valueChanged(ListSelectionEvent arg0) {
		
	}
	
}
