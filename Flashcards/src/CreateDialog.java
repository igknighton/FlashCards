import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedList;

import javax.swing.*;


public class CreateDialog extends JDialog implements ActionListener, PropertyChangeListener{

	
	private String prompt;
	private JTextField textField = new JTextField();
	private JOptionPane optionPane;
	private String newSetName = null;
	private String ok = "ok";
	private String cancel = "cancel";
	private static JFrame frame = new JFrame();
	private LinkedList<CardSet> setOfCards;
	
	public CreateDialog(LinkedList<CardSet> setOfCards) {
		
		super(frame, true);
		this.setLocationRelativeTo(null);
		this.setOfCards = setOfCards;
		prompt = "Enter the name of your set";
		Object[] array = {prompt, textField};
		Object[] options = {ok,cancel};
		
		optionPane = new JOptionPane(array, 
				JOptionPane.QUESTION_MESSAGE,
				JOptionPane.YES_NO_OPTION,
				null,
				options,
				options[0]);

		setContentPane(optionPane);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		addComponentListener(new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				textField.requestFocusInWindow();
			}
		});
		textField.addActionListener(this);
		optionPane.addPropertyChangeListener(this);
		pack();	
	}
	
	private boolean nullOrWhiteSpaceOnly(String s) {
		return s != null && !s.trim().isEmpty() && !s.trim().isEmpty();
	}
	
	public void actionPerformed(ActionEvent e) {
		optionPane.setValue(ok);	
	}
	
	public void propertyChange(PropertyChangeEvent e) {	
		 String prop = e.getPropertyName();
	        if (isVisible()
	                && (e.getSource() == optionPane)
	                && (JOptionPane.VALUE_PROPERTY.equals(prop)
	                || JOptionPane.INPUT_VALUE_PROPERTY.equals(prop))) {
	        	
	            Object value = optionPane.getValue();
	            
	            if (value == JOptionPane.UNINITIALIZED_VALUE) {
	                //ignore reset
	                return;
	            }
	            //Reset the JOptionPane's value.
	            //If you don't do this, then if the user
	            //presses the same button next time, no
	            //property change event will be fired.
	            optionPane.setValue(
	                    JOptionPane.UNINITIALIZED_VALUE);

	            if (ok.equals(value)) {
	            	
	            	newSetName = textField.getText().trim();
	                String ucText = newSetName;
	                if (!nullOrWhiteSpaceOnly(newSetName)) { //invalid text
	                    JOptionPane.showMessageDialog(this, "Type in a name for your set.");
	                    textField.selectAll();
	                    textField.requestFocusInWindow();
	                } else {
	                	
	                	if (isDuplicate(newSetName)){
	                		JOptionPane.showMessageDialog(this, "Set name already exists.");
	                		textField.setText("");
	                		newSetName = "";
	                	}
	                	else
	                    exit();
	                }
	                
	                
	            } else { //user closed dialog or clicked cancel
	                
	                exit();
	            }
	        }
	}
	public String getSetName() {
		return newSetName;
	}
	public void exit() {
		dispose();
	}
	
	private boolean isDuplicate(String s) {
		for ( CardSet temp: setOfCards)
			if(temp.getTitle().equals(s))
				return true;
		return false;
	}
}
