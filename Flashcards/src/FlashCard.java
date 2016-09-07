
public class FlashCard {
	
	private String definition;
	private String term;
	
	public FlashCard(String term, String definition) {
		this.term = term;
		this.definition = definition;
	}
	public String getTerm() {
		return term;
	}
	public String getDefintion() {
		return definition;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
}
