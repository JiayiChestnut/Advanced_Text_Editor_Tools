package spelling;

import java.util.LinkedList;

/**
 * A class that implements the Dictionary interface using a LinkedList
 *
 */
public class DictionaryLL implements Dictionary 
{

	private LinkedList<String> dict;
	private int size;
	
    // TODO: Add a constructor
	public DictionaryLL() {
		size = 0;
		dict = new LinkedList<String>();
	}
	public DictionaryLL(String word) {
		if (word == null) {
			throw new NullPointerException("can not add null into dictionary");
		}
		else {
			word = word.toLowerCase();
			dict = new LinkedList<String>();
			dict.add(word);
			size = 1;
		}
		 
	}


    /** Add this word to the dictionary.  Convert it to lowercase first
     * for the assignment requirements.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
    public boolean addWord(String word) {
    	// TODO: Implement this method
    	word = word.toLowerCase();
//    	System.out.println(word);
    	boolean isContain = dict.contains(word);//!!! 
    	if (isContain) {
    		return false;
    	}
    	else {
    		dict.add(word);
    		size++;
    		return true;
    	}
    }


    /** Return the number of words in the dictionary */
    public int size()
    {
        // TODO: Implement this method
        return size;
    }

    /** Is this a word according to this dictionary? */
    public boolean isWord(String s) {
        //TODO: Implement this method
    	if (s == null) {
    		throw new NullPointerException("word can not be null");
    	}
    	String word = s.toLowerCase();
        return dict.contains(word);
    }

    
}
