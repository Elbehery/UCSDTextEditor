package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	public MarkovTextGeneratorLoL(Random generator) {
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}

	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText) {
		// TODO: Implement this method
		String[] textWithoutSpaces = sourceText.split(" ");
		this.starter = textWithoutSpaces[0];

		String prevWord = this.starter;

		// iterate and build the model
		for (int i = 1; i < textWithoutSpaces.length; i++) {

			// current word
			String w = textWithoutSpaces[i];

			// create a listNode of prevWord for comparison
			ListNode comparisonListNode = new ListNode(prevWord);
			// check if the prevWord is in the model
			if (wordList.contains(comparisonListNode)) {
				int index = wordList.indexOf(comparisonListNode);
				wordList.get(index).addNextWord(w);
			} else // if not, create and add to wordList
			{
				ListNode newListNode = new ListNode(prevWord);
				newListNode.addNextWord(w);
				wordList.add(newListNode);
			}

			// update prevWord
			prevWord = w;
		}

		// handling the special case of the last word in the training text
		ListNode newListNode = new ListNode(prevWord);

		if (wordList.contains(newListNode)) {
			int index = wordList.indexOf(newListNode);
			wordList.get(index).addNextWord(this.starter);
		} else {

			newListNode.addNextWord(this.starter);
			wordList.add(newListNode);
		}
	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
		// TODO: Implement this method

		/*
		 * if (this.wordList.size() == 0) { return ""; }
		 */
		// preparing the generated text
		StringBuilder output = new StringBuilder();
		String currWord = this.starter;
		output.append(currWord);

		int count = 1;
		ListNode nextWordNode = null;

		while (count < numWords) {
			output.append(" ");
			nextWordNode = new ListNode(currWord);
			int listIndex = wordList.indexOf(nextWordNode);
			if (listIndex == -1) {
				throw new NoSuchElementException("The required word does not exist in the List");
			}
			String w = wordList.get(listIndex).getRandomNextWord(rnGenerator);
			output.append(w);
			currWord = w;
			count++;
		}

		return output.toString().trim();
	}

	// Can be helpful for debugging
	@Override
	public String toString() {
		String toReturn = "";
		for (ListNode n : wordList) {
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText) {
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		starter = "";

		train(sourceText);
	}

	// TODO: Add any private helper methods you need here.

	/**
	 * This is a minimal set of tests. Note that it can be difficult to test
	 * methods/classes with randomized behavior.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));

		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, " + "You say stop, and I say go, go, go, "
				+ "Oh no. You say goodbye and I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "I say high, you say low, "
				+ "You say why, and I say I don't know. " + "Oh no. "
				+ "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "Why, why, why, why, why, why, "
				+ "Do you say goodbye. " + "Oh no. " + "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello. " + "You say yes, I say no, "
				+ "You say stop and I say go, go, go. " + "Oh, oh no. "
				+ "You say goodbye and I say hello, hello, hello. "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello, "
				+ "I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));

	}

}

/**
 * Links a word to the next words in the list You should use this class in your
 * implementation.
 */
class ListNode {
	// The word that is linking to the next words
	private String word;

	// The next words that could follow it
	private List<String> nextWords;

	ListNode(String word) {
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord() {
		return word;
	}

	public void addNextWord(String nextWord) {
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(Random generator) {
		// TODO: Implement this method
		// The random number generator should be passed from
		// the MarkovTextGeneratorLoL class
		int randomIndex;
		randomIndex = generator.nextInt(this.nextWords.size());
		//System.out.println(this.nextWords.size() + "*********************" + randomIndex);
		String randomWord = this.nextWords.get(randomIndex);
		return randomWord;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if (obj == null) {
			return false;
		}

		if (((ListNode) obj).getWord().equals(null)) {
			return false;
		}

		return this.word.equals(((ListNode) obj).getWord());
	}

	public String toString() {
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}

}
