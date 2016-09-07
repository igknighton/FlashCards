
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;




public class Tester extends JPanel{
	
	private CardSet cardSet;
	private CardSet questionSet;
	private ArrayList<FlashCard> aList = new ArrayList<FlashCard>();
	private JLabel definitionLabel;
	private JRadioButton option1;
	private JRadioButton option2;
	private JRadioButton option3;
	private JRadioButton option4;
	private JButton next;
	private JButton prev;
	private JButton submit;
	private ArrayList<Question> questions = new ArrayList<Question>(); 
	private Question currentQuestion;
	
	private Random random = new Random();
	private FlashCard flashCard;
	private String answer;
	private int rand;
	private int questionIndex = 0;
	ButtonGroup group;
	
	
	public Tester(CardSet cardSet) {
		this.cardSet = cardSet;
		
		this.setBorder(BorderFactory.createEmptyBorder(100,0,0,0));
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		questionSet = new CardSet("");

		questionSet.addAll(cardSet);
		aList.addAll(cardSet);
		add(new LabelPanel());
		add(new ChoicePanel());
		add(new ButtonPanel());
		
		
		questionGenerator();
		
		currentQuestion = questions.get(questionIndex);
		definitionLabel.setText(currentQuestion.getDefinition());
		option1.setText(currentQuestion.getOption1());
		option2.setText(currentQuestion.getOption2());
		option3.setText(currentQuestion.getOption3());
		option4.setText(currentQuestion.getOption4());
		prev.setEnabled(false);
		
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(500,500);
	}
		
	private class ChoicePanel extends JPanel {	
		
		public ChoicePanel() {
			this.setLayout(new GridLayout(2,2));
			createRadioButtons();
			
			add(option1);
			add(option2);
			add(option3);
			add(option4);

		}
		
		private void createRadioButtons() {
			
			option1 = new JRadioButton("");
			option2 = new JRadioButton("");
			option3 = new JRadioButton("");
			option4 = new JRadioButton("");
			
			option1.addActionListener(new RadioButtonListener());
			option2.addActionListener(new RadioButtonListener());
			option3.addActionListener(new RadioButtonListener());
			option4.addActionListener(new RadioButtonListener());
			
			group = new ButtonGroup();
			group.add(option1);
			group.add(option2);
			group.add(option3);
			group.add(option4);		
		}

		
	}
	private class LabelPanel extends JPanel {
	
		
		public LabelPanel() {
			definitionLabel = new JLabel();
			add(definitionLabel,BorderLayout.CENTER);
		}
	}
	
	private class ButtonPanel extends JPanel {
		
		public ButtonPanel() {
			this.setLayout(new GridLayout(1,1));
			next = new JButton(">");
			prev = new JButton("<");
			submit = new JButton("submit");
			next.addActionListener(new ButtonListener());
			prev.addActionListener(new ButtonListener());
			submit.addActionListener(new ButtonListener());
			prev.setActionCommand("2");
			next.setActionCommand("1");
			submit.setActionCommand("3");
			add(prev);
			add(next);
			add(submit);
		}
	}
	
	
	private class ButtonListener implements ActionListener {
		
		public int action;
		
		public void actionPerformed(ActionEvent e) {
			action = Integer.parseInt(e.getActionCommand());
			
			switch(action) {
			case 1:
				nextQuestion(true);
				break;
			case 2:
				nextQuestion(false);
				break;
			case 3:
				checkAnswers();
				break;
			}
		}
	}
	private class RadioButtonListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			if (option1.isSelected()) 
				currentQuestion.setButton(option1);
			else if (option2.isSelected())
				currentQuestion.setButton(option2);
			else if (option3.isSelected())
				currentQuestion.setButton(option3);
			else if (option4.isSelected())
				currentQuestion.setButton(option4);	
			
			System.out.println("Answer chosen: " + currentQuestion.getButton().getText());
		}	
	}
	
	private void checkAnswers() {
		
		String txt = "";
		int count = 1;
		String answer = "";
		int score = 0;
		for (Question temp: questions) {
			
			txt = temp.getButton().getText();
			answer = temp.getAnswer();
			
			if (txt.equals(answer)) score++;
			
			System.out.println("Question " + count);
			System.out.println(temp.getDefinition());
			System.out.println("Chosen answer: "+txt);
			System.out.println("Correct answer: "+temp.getAnswer());
			System.out.println();
			count++;			
		}
		System.out.println("You've gotten " + score + " out of " + questions.size());
		
	}
	
	private void setQuestion(Question question) {
		definitionLabel.setText(question.getDefinition());
		option1.setText(question.getOption1());
		option2.setText(question.getOption2());
		option3.setText(question.getOption3());
		option4.setText(question.getOption4());

	}
	private void nextQuestion(boolean nextCard) {
		
		if (nextCard) {
			questionIndex++;
			currentQuestion = questions.get(questionIndex);
			if (questionIndex == questions.size()-1) 
				next.setEnabled(false);
			prev.setEnabled(true);
			setQuestion(currentQuestion);
			
			if (currentQuestion.getButton() != null)
			currentQuestion.getButton().setSelected(true);
			else group.clearSelection();
		}
		else {
		   questionIndex--;
		   currentQuestion = questions.get(questionIndex);
		   if (questionIndex == 0)
			   prev.setEnabled(false);
		   	   next.setEnabled(true);
		   setQuestion(currentQuestion);
		  
		   if (currentQuestion.getButton() != null)
				currentQuestion.getButton().setSelected(true);
		   else group.clearSelection();
		}
	}
	
	private void questionGenerator() {
		
		Set<String> choice = new HashSet<String>();
		
		while(!questionSet.isEmpty()) {
		if (questionSet.size() !=1)
			flashCard = questionSet.remove(random.nextInt(questionSet.size()-1));
		else
			flashCard = questionSet.remove(0);
		answer = flashCard.getTerm();
		choice.add(answer);
		
		while (choice.size() < 4) {
			rand = random.nextInt(cardSet.size());
			choice.add(aList.get(rand).getTerm());
		}
		questions.add(new Question(flashCard, choice));
		choice.removeAll(choice);
		}	
	}
	
	private class Question {
		
		private FlashCard fc;
		private String[] choices = new String[4];
		private Set<String> set = new HashSet<String>();
		private int i = 0;
		private JRadioButton selectedButton = null;
		
		public Question(FlashCard fc, Set<String> set) {
			this.fc = fc;
			this.set = set;
			setChoices();
		}
		public String getAnswer() {
			return fc.getTerm();
		}
		public String getDefinition() {
			return fc.getDefintion();
		}
		public String getOption1() {
			return choices[0];
		}
		public String getOption2() {
			return choices[1];
		}
		public String getOption3() {
			return choices[2];
		}
		public String getOption4() {
			return choices[3];
		}
		public void setButton(JRadioButton button) {
			this.selectedButton = button;
		}
		public JRadioButton getButton() {
			return selectedButton;
		}
		private void setChoices() {
			 
			 for (String temp: set){
				 choices[i] = temp;
				 System.out.println("Choice " + Integer.parseInt(String.valueOf(i+1))+ " = "+choices[i]);
				 i++;
			 }
			 System.out.println(fc.getTerm());
			 System.out.println();
		}
		public void printChoices() {
			
			System.out.println(getDefinition());
			System.out.println("a) " + choices[0]);
			System.out.println("b) " + choices[1]);
			System.out.println("c) " + choices[2]);
			System.out.println("d) " + choices[3]);
			
			for (int i = 0; i < choices.length; i++)
				if (fc.getTerm().equals(choices[i]))
					System.out.println("Answer: " + choices[i]);
			System.out.println();
		}
	}
		
	public static void main(String[] args) {

		CardSet cardSet = new CardSet("Test");
		for (int i = 0; i < 5; i++)
			cardSet.add(new FlashCard("Term " + i,"Definition " + i ));
		
		JFrame frame = new JFrame("test");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.add(new QuizPanel(cardSet));
        frame.pack();
        frame.setVisible(true);     	
	}
}