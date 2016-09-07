import java.util.LinkedList;


public class CardSet extends LinkedList<FlashCard> {

	
	private String nameOfSet;
	private String term;
	private String definition;
	
	public CardSet(String nameOfSet) {
		this.nameOfSet = nameOfSet;
	}
	public String getTitle() {
		return nameOfSet;
	}
	public void addCard(String term, String definition) {
		this.term = term;
		this.definition = definition;
		this.add(new FlashCard(term, definition));
	}
		
}
