import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class AddNewCard extends JFrame {

	/*
	 * produces another frame for adding a new card for the set
	 */
	
	private CardSet cardSet;
	private FlashCardPanel panel;
	private JLabel termLabel = new JLabel("Term");
	private JTextField termField = new JTextField();
	private JLabel definitionLabel = new JLabel("Definition");
	private JTextArea definitionArea = new JTextArea();
	private JScrollPane scrollPane;
	private boolean editing = false;
	private String term;
	private String definition;
	private AddNewCard editFrame;

	/*
	 * adding a new card
	 */
	public AddNewCard(int x, int y, FlashCardPanel panel, CardSet cardSet) {
		super("Add New Card");
		this.panel = panel;
		this.cardSet = cardSet;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.add(new CardPanel());
		this.setSize(100, 200);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}

	/*
	 * editing an existing card
	 */
	public AddNewCard(int x, int y, FlashCardPanel panel, String term,
			String definition) {
		super("Edit Card");
		this.setName("Edit Card");
		this.panel = panel;
		editFrame = this;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);

		editing = true;
		termField.setText(term);
		definitionArea.setText(definition);
		this.add(new CardPanel());
	}

	private class CardPanel extends JPanel {
		public Dimension getPreferredSize() {
			return new Dimension(300, 300);
		}

		public CardPanel() {

			this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			termField.setMaximumSize(new Dimension(1000, 20));

			termLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
			termField.setAlignmentX(Component.LEFT_ALIGNMENT);
			definitionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

			this.add(termLabel);
			this.add(termField);
			this.add(definitionLabel);

			definitionArea.setOpaque(true);
			definitionArea.setText(definition);
			definitionArea.setWrapStyleWord(true);
			definitionArea.setLineWrap(true);
			definitionArea.setEditable(true);
			definitionArea.setFocusable(true);
			scrollPane = new JScrollPane(definitionArea);
			scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
			this.add(scrollPane);

			createButtons(this);
		}

		private void createCard() {
			if (checkForTerm(termField.getText())) {
				JOptionPane.showMessageDialog(this,
						"Term already exists in the set");
			} else {
				if (!termField.getText().isEmpty()
						&& !definitionArea.getText().isEmpty()) {
					term = termField.getText();
					definition = definitionArea.getText();
					termField.setText("");
					definitionArea.setText("");

					if (!editing)
						panel.addNewCard(term, definition);
					else {
						panel.editCard(term, definition);
						editFrame.dispose();
					}
				} else
					JOptionPane.showMessageDialog(this,
							"At least one of your fields is empty.");
			}
		}

		private boolean checkForTerm(String term) {
			for (FlashCard temp : cardSet) {
				if (term.equals(temp.getTerm()))
					return true;
			}

			return false;
		}

		private void createButtons(CardPanel panel) {
			JButton create;
			
			if (!editing) {
				create = new JButton("Create");
				
			} else
				create = new JButton("Change");

			create.addActionListener(new ButtonListener());
			create.setActionCommand("1");
			create.setAlignmentX(Component.LEFT_ALIGNMENT);

			panel.add(create);
			

		}


		class ButtonListener implements ActionListener {

			private int action;

			public void actionPerformed(ActionEvent e) {

				action = Integer.parseInt(e.getActionCommand());

				switch (action) {
				case 1:
					createCard();
					break;
				
				}
			}
		}
	}
}
