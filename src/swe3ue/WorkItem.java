package swe3ue;

/**
 * A single WorkItem.
 * 
 * @author patrick
 *
 */
public class WorkItem {
	private int id_;
    private String word_;
    private int vocals_;
    private int length_;
    
    public int getID() {
    	return id_;
    }
    
    public String getWord() {
    	return word_;
    }
    
    public int getVocals() {
    	return vocals_;
    }
    
    public int getLength() {
    	return length_;
    }
    
    public void setID(int id) {
    	id_ = id;
    }
    
    public void setWord(String word) {
    	word_ = word;
    }
    
    public void setVocals(int vocals) {
    	vocals_ = vocals;
    }
    
    public void setLength(int length) {
    	length_ = length;
    }
}
