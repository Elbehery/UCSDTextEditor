package document;

/**
 * A class that represents a text document
 *
 * @author UC San Diego Intermediate Programming MOOC team
 */

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Document {

    private String text;

    /**
     * Create a new document from the given text.
     * Because this class is abstract, this is used only from subclasses.
     *
     * @param text The text of the document.
     */
    protected Document(String text) {
        this.text = text;
    }

    /**
     * Returns the tokens that match the regex pattern from the document
     * text string.
     *
     * @param pattern A regular expression string specifying the
     *                token pattern desired
     * @return A List of tokens from the document text that match the regex
     * pattern
     */
    protected List<String> getTokens(String pattern) {
        ArrayList<String> tokens = new ArrayList<String>();
        Pattern tokSplitter = Pattern.compile(pattern);
        Matcher m = tokSplitter.matcher(text);

        while (m.find()) {
            tokens.add(m.group());
        }

        return tokens;
    }

    // This is a helper function that returns the number of syllables
    // in a word.  You should write this and use it in your
    // BasicDocument class.
    // You will probably NOT need to add a countWords or a countSentences method
    // here.  The reason we put countSyllables here because we'll use it again
    // next week when we implement the EfficientDocument class.
    protected int countSyllables(String word) {
        // TODO: Implement this method so that you can call it from the
        // getNumSyllables method in BasicDocument (module 1) and
        // EfficientDocument (module 2).

        int syllablesCounter = 0;
        String vowels = "aeiouyAEIOUY";
        boolean lastCharFlag = true;
        boolean updateCounterFlag = true;

        for (int i = 0; i < word.length(); i++) {

            if (i == word.length() - 1) {
                if ((word.charAt(i) == 'e' || word.charAt(i) == 'E') && syllablesCounter != 0) {
                    // very special case
                    updateCounterFlag = false;
                }
                lastCharFlag = false;
            }

            if (vowels.indexOf(word.charAt(i)) != -1) {
                // update the counter
                if (updateCounterFlag)
                    syllablesCounter++;

                // handling the corner cases
                if (lastCharFlag && vowels.indexOf(word.charAt(i + 1)) != -1) {
                    updateCounterFlag = false;
                } else {
                    updateCounterFlag = true;
                }
            }
        }

        return syllablesCounter;
    }

    /**
     * A method for testing
     *
     * @param doc       The Document object to test
     * @param syllables The expected number of syllables
     * @param words     The expected number of words
     * @param sentences The expected number of sentences
     * @return true if the test case passed.  False otherwise.
     */
    public static boolean testCase(Document doc, int syllables, int words, int sentences) {
        System.out.println("Testing text: ");
        System.out.print(doc.getText() + "\n....");
        boolean passed = true;
        int syllFound = doc.getNumSyllables();
        int wordsFound = doc.getNumWords();
        int sentFound = doc.getNumSentences();
        if (syllFound != syllables) {
            System.out.println("\nIncorrect number of syllables.  Found " + syllFound
                    + ", expected " + syllables);
            passed = false;
        }
        if (wordsFound != words) {
            System.out.println("\nIncorrect number of words.  Found " + wordsFound
                    + ", expected " + words);
            passed = false;
        }
        if (sentFound != sentences) {
            System.out.println("\nIncorrect number of sentences.  Found " + sentFound
                    + ", expected " + sentences);
            passed = false;
        }

        if (passed) {
            System.out.println("passed.\n");
        } else {
            System.out.println("FAILED.\n");
        }
        return passed;
    }


    /**
     * Return the number of words in this document
     */
    public abstract int getNumWords();

    /**
     * Return the number of sentences in this document
     */
    public abstract int getNumSentences();

    /**
     * Return the number of syllables in this document
     */
    public abstract int getNumSyllables();

    /**
     * Return the entire text of this document
     */
    public String getText() {
        return this.text;
    }

    /**
     * return the Flesch readability score of this document
     */
    public double getFleschScore() {
        // TODO: Implement this method

        double secondTerm = 1.015 * getNumWords() / getNumSentences();
        double thirdTerm = 84.6 * getNumSyllables() / getNumWords();
        double fleschScore = 206.835 - secondTerm - thirdTerm;
        return fleschScore;
    }


}
