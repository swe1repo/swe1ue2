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
    
    int getID() {
    	return id_;
    }
    
    String getWord() {
    	return word_;
    }
    
    int getVocals() {
    	return vocals_;
    }
    
    int getLength() {
    	return length_;
    }
    
    void setID(int id) {
    	id_ = id;
    }
    
    void setWord(String word) {
    	word_ = word;
    }
    
    void setVocals(int vocals) {
    	vocals_ = vocals;
    }
    
    void setLength(int length) {
    	length_ = length;
    }
}
