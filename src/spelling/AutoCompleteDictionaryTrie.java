package spelling;

import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
 * 
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

	private TrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
	}

	/**
	 * Insert a word into the trie. For the basic part of the assignment (part
	 * 2), you should ignore the word's case. That is, you should convert the
	 * string to all lower case as you insert it.
	 */
	public boolean addWord(String word) {
		// TODO: Implement this method.

		// preparing before the loop
		char[] chars = word.toLowerCase().toCharArray();
		TrieNode dummyRoot = root;
		TrieNode temp = new TrieNode();
		boolean sizeFlag = false;

		for (int i = 0; i < chars.length; i++) {
			// retrieving the corresponding TrieNode if exist.
			temp = dummyRoot.getChild(chars[i]);
			if (temp == null) {
				if (i < chars.length - 1) {
					temp = dummyRoot.insert(chars[i]);
				} else {
					temp = dummyRoot.insert(chars[i]);
					temp.setEndsWord(true);
					sizeFlag = true;
				}
			} else {
				if (i == chars.length - 1 && !temp.endsWord()) { // Fucken
																	// special
																	// case
					// THIS IS A WORD, SO UPDATE SIZE && SET FLAG TO TRUE
					temp.setEndsWord(true);
					sizeFlag = true;
				}
			}

			dummyRoot = temp;
		}
		// update size
		if (sizeFlag) {
			size++;
			return true;
		}
		
		return false;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size() {
		// TODO: Implement this method
		return size;
	}

	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) {
		// TODO: Implement this method
		// sanity check
		if (s.length() == 0 || s.equals("") || s == null) {
			return false;
		}
		char[] chars = s.toLowerCase().toCharArray();
		TrieNode temp = root;
		for (int i = 0; i < chars.length; i++) {
			if (temp.getChild(chars[i]) != null) {
				temp = temp.getChild(chars[i]);
			} else
				return false;
		}

		return true;
	}

	/**
	 * * Returns up to the n "best" predictions, including the word itself, in
	 * terms of length If this string is not in the trie, it returns null.
	 * 
	 * @param text
	 *            The text to use at the word stem
	 * @param n
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to n best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		// TODO: Implement this method
		// This method should implement the following algorithm:
		// 1. Find the stem in the trie. If the stem does not appear in the
		// trie, return an
		// empty list
		// 2. Once the stem is found, perform a breadth first search to generate
		// completions
		// using the following algorithm:
		// Create a queue (LinkedList) and add the node that completes the stem
		// to the back
		// of the list.
		// Create a list of completions to return (initially empty)
		// While the queue is not empty and you don't have enough completions:
		// remove the first Node from the queue
		// If it is a word, add it to the completions list
		// Add all of its child nodes to the back of the queue
		// Return the list of completions

		LinkedList<TrieNode> queue = new LinkedList<>();
		LinkedList<String> predictions = new LinkedList<>();

		// finding the stem in the dictionary
		if (!isExistInDict(prefix, root)) {
			return predictions;
		}

		// finding the starting node for creating the list of predictions
		TrieNode target = findStemTrieNodeInDictionary(prefix, root);
		queue.add(target);

		// BFS to find the predictions
		while (!queue.isEmpty() && predictions.size() < numCompletions) {
			TrieNode trieNode = queue.removeFirst();
			if (trieNode.endsWord()) {
				predictions.add(trieNode.getText());
			}

			// add children of this node to queue
			for (Character c : trieNode.getValidNextCharacters()) {
				queue.addLast(trieNode.getChild(c));
			}
		}

		return predictions;
	}

	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;

		System.out.println(curr.getText());

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}

	// added by Mustafa
	private boolean isExistInDict(String stem, TrieNode currentNode) {

		// Stopping condition
		if (stem.equals("") && currentNode.getValidNextCharacters().size() > 0) {
			return true;
		}

		// checking if the currentNode still has children
		String newStem = stem.substring(1);
		TrieNode newStartNode = currentNode.getChild(Character.toLowerCase(stem.charAt(0)));
		if (newStartNode != null) {
			return isExistInDict(newStem, newStartNode);
		}

		return false;
	}

	// added by Mustafa
	private TrieNode findStemTrieNodeInDictionary(String stem, TrieNode currentNode) {

		// Stopping condition
		if (stem.equals("") && currentNode.getValidNextCharacters().size() > 0) {
			return currentNode;
		}

		// checking if the currentNode still has children
		String newStem = stem.substring(1);
		TrieNode newStartNode = currentNode.getChild(Character.toLowerCase(stem.charAt(0)));
		if (newStartNode != null) {
			return findStemTrieNodeInDictionary(newStem, newStartNode);
		}

		return null;
	}

}