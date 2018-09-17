package spelling;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements  Dictionary, AutoComplete {

    private TrieNode root;

    

    public AutoCompleteDictionaryTrie()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
//		System.out.println(size());
	    //TODO: Implement this method.
		if (word == null) {
			throw new NullPointerException("word can not be null");
		}
		word = word.toLowerCase();
		TrieNode prevTN = root;
	
		TrieNode currTN = null;
		for (int i = 0; i < word.length(); i ++) {
//			System.out.println(prevTN.getText());
			char c = word.charAt(i);
			
			currTN = prevTN.getChild(c);
			if (currTN == null) {
				currTN  = prevTN.insert(c);
//				System.out.println("successfull insert " + currTN.getText() + " in to " + this.toString());
//				System.out.println("successfull insert " + currTN.getText());
			}
			prevTN = currTN;
//			System.out.println(c +" " + prevTN.getText()); 
		}
		if (prevTN.endsWord()) {//the criteria to judge whether this is an successful insert. is that 
			//this node with input word as its text is not marked as end of one of the previous words. 
			return false;
		}
		else {
//			System.out.println(prevTN.getText());
			prevTN.setEndsWord(true);
//			System.out.println("successfull insert " + prevTN.getText() + "\t as word");
			return true;
		}

//		System.out.println("added \":" + word + "\", size is " + size());

	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
		//!!!part of the path can also have some nodes marked as words
	    //TODO: Implement this method
		if (root.getValidNextCharacters().isEmpty()) {
			return 0;
		}else {
			return size(root);
		}
	    
	}
	public int size(TrieNode currTN) {
		int acc = 0;
		if (currTN.endsWord()) {
			acc++;
		}
		for (char c : currTN.getValidNextCharacters()) {
			acc += size(currTN.getChild(c));
		}
		return acc;
		
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		String word = s.toLowerCase();
		TrieNode t = searchNode(root, word);
		if (t != null && t.endsWord()) {
			return true;
		}else {
			return false;			
		}
		
	}
	private TrieNode searchNode(TrieNode currTN, String word) {
//		boolean isWord = false;
//		for (int i = 0; i < word.length(); i ++) {
//			char c = word.charAt(i);
//			
//		}
		if (currTN == null) return null;
//		System.out.println("do have prefix : " + currTN.getText() + "\tis it end of word : " + currTN.endsWord());
		if (word.isEmpty()) {
				return currTN;
		}
		
		char c = word.charAt(0);
		TrieNode nextTN = currTN.getChild(c);
//		System.out.println("searching " + word + " currTN is : " + currTN.getText());
		return searchNode(nextTN, word.substring(1));
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 if (prefix == null) {
    		 throw new NullPointerException("prefix can not be null");
    	 }
    	 if (numCompletions == 0) {
    		 return new LinkedList<String>();
    	 }
    	 
    	 List<String> result = new LinkedList<String>();
    	 TrieNode startNode = root;
    	 List<TrieNode> waitingCompletions = new ArrayList<TrieNode>();
    	 if (prefix.equals("")) {//should not use == 
    		 startNode = root;   
    	 }
    	 else {
    		
    		prefix = prefix.toLowerCase();
//    		printTree();
    		startNode = searchNode(root, prefix);
//    		System.out.println("try to predict " + prefix);
    	 }
    	 waitingCompletions.add(startNode);
    	 if (startNode == null) {
    		 return result;
    	 }   	 
    	 
//    	 for(char c : startNode.getValidNextCharacters()) {
//    		 waitingCompletions.add(startNode.getChild(c));
//    	 }
//    	 System.out.println("first waiting list is ");
//    	 printList(waitingCompletions);
    	 
//    	 Iterator<TrieNode> iterator = waitingCompletions.iterator();//»áÒýÆðconcurrent ´íÎó
    	 
//    	 int numElementInWaiting = waitingCompletions.size();
    	 int currIdx = 0;
    	 while(currIdx < waitingCompletions.size()) {
    		 
    		 TrieNode currTN = waitingCompletions.get(currIdx);
    		 if (currTN == null) {
    			 currIdx++;
    			 continue;
    		 }
//    		 System.out.println(currTN.getText());
    		 if (currTN.endsWord()) {
    			 if (addCompletion(result, currTN, numCompletions)) {
    				 break;
    			 } 
    		 }
    		 for (char c : currTN.getValidNextCharacters()) {
    			 waitingCompletions.add(currTN.getChild(c));
//    			 printList(waitingCompletions);
//    			 System.out.println("curr node is " + currTN.getText() + "\t, add " + c + " to the waiting list");
    		 }
    		 currIdx++;
    	 }
//    	 System.out.println(result.toString());
         return result;
     }
     private void printList(List<TrieNode> l) {
    	 for (int i = 0; i < l.size(); i ++) {
    		 System.out.print(l.get(i).getText() + "\t");
    	 }
    	 System.out.println("current size of waiting list is :" + l.size());
     }
     //return true once arrive the ceiling of required number of completions.
     private boolean addCompletion(List<String> result, TrieNode node, int numCompletions) {
//    	 System.out.println("want to add node: " + node.getText());
    	 
    	 if (node.endsWord()) {
    		 result.add(node.getText());
    		 int sizeOfResult = result.size();
    		 if (sizeOfResult == numCompletions) {
    			 return true;
    		 }
    	 }
    	 return false;
    	 
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText() + " is word : " + curr.endsWord());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}