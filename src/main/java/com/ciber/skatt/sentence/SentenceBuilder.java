package com.ciber.skatt.sentence;

/**
 * This program writes a sentence using the BNF system. It is possible to have
 * a stack overflow error although the chances are very small. Simply print sentence()
 * method to see it work
 * @author Murphy
 *
 */

public class SentenceBuilder {

	public static String[] conjunction = new String[]{"but", "and", "or", "because"};
	public static String[] proper_noun = new String[]{"Fred", "Jane", "Richard Nixon", "Miss America"};
	public static String[] common_noun = new String[]{"man", "woman", "fish", "elephant", "unnicorn"};
	public static String[] determiner = new String[]{"a", "the", "every", "some"};
	public static String[] transitive_verb = new String[]{"loves", "hates", "sees","looks for", "finds"};
	public static String[] intransitive_verb = new String[]{"runs", "jumps", "talks", "sleeps"};
	public static String[] adjective = new String[]{"big", "tiny", "pretty", "bald"};

	public static void main(String[] args){

		System.out.println(sentence());

	}
	/**
	 * This method has two possible outcomes, either it returns simple_sentence()
	 * or it returns simple_sentence plus a word for the conjugation array and
	 * a revursive call of the method sentence.
	 * @return
	 */
	public static String sentence(){
		//int i is random number between one and 10 that is used for probabilities
		int i = 0 + (int)(Math.random() * ((10 - 0) + 1));

		//if i is lower than 5 then we do a recursion of sentence along with a conjugation
		if (i < 4){
			 // integer j will be what index of conjunction we will return
			//  e.g. j = 1 is and
			 int j = 0 + (int)(Math.random() * ((3 - 0) + 1));
			 return simple_Sentence() + conjunction[j] + " " + sentence();

		}
		else{
			return simple_Sentence();
		}
	}

	/***
	 * returns a noun_phrase() method and concatenates is with a
	 * verb_phrase();
	 * @return
	 */
	public static String simple_Sentence(){
		return noun_Phrase() + verb_Phrase();
	}

	/**
	 * returns a proper noun, or a determiner plus a possible adjectives and a common
	 * noun. Or a determiner + possible adjectives + common + "who" + plus a
	 * call to noun_phrase.
	 * @return
	 */
	public static String noun_Phrase(){
		//Random number between 0 and 3, decides number of adjectives to use.
		int numberOfAdjectives = 0 + (int)(Math.random() * ((3 - 0) + 1));
		int fillAdjectives = 0;
		String adjectives = "";

		//Fill string with adjectives, duplicatte adjective are possible,
		//no adjective is also possible.
		while(fillAdjectives < numberOfAdjectives){
			int randomAdjective = 0 + (int)(Math.random() * ((3 - 0) + 1));
			adjectives = adjectives + " " + adjective[randomAdjective];
			fillAdjectives = fillAdjectives + 1;
		}
		//int i is random number between one and 10 that is used for probabilities
		int i = 0 + (int)(Math.random() * ((10 - 0) + 1));

		//integer j is the index of proper_noun
		if (i < 5){
			int j = 0 + (int)(Math.random() * ((3 - 0) + 1));
			return proper_noun[j] + " ";
			}

		if (i > 3 && i < 8){


			// j is used for determiner element.
			int j = 0 + (int)(Math.random() * ((3 - 0) + 1));
			// k is used for common noun element.
			int k = 0 + (int)(Math.random() * ((4 - 0) + 1));
			return determiner[j]  + adjectives + " " + common_noun[k] + " ";

		}
		else{
			//j is used for determiner element.
			int j = 0 + (int)(Math.random() * ((3 - 0) + 1));
			// k is used for common noun element.
			int k = 0 + (int)(Math.random() * ((4 - 0) + 1));
			return determiner[j] + adjectives + " " + common_noun[k] + " who " + verb_Phrase();
		}
	}
	/**
	 * returns an intransitive verb, or a transitive verb lus a call to noun_phrase
	 * method. Or "is" plus an adjective. Or "believes" plus a call to sentence() method
	 * @return
	 */
	public static String verb_Phrase(){

		//int i is random number between one and 10 that is used for probabilities
		int i = 0 + (int)(Math.random() * ((10 - 0) + 1));
		if (i < 5){
			// int randomIntransitive will choose the instransitive_verb element.
			int randomIntransitive = 0 + (int)(Math.random() * ((3 - 0) + 1));
			return intransitive_verb[randomIntransitive] + " ";
		}
		if (i == 5 && i <7){
			//int randomTransitive will choose the tranitive_verb array element.
			int randomTransitive = 0 + (int)(Math.random() * ((4 - 0) + 1));
			return transitive_verb[randomTransitive] + " " + noun_Phrase();
		}
		if (i == 7 && i < 9){
			//int randomAdjective will choose the adjective array element
			int randomAdjective = 0 + (int)(Math.random() * ((3 - 0) + 1));
			return "is " + adjective[randomAdjective] + " ";
		}
		else{

			return "believes that " + sentence();
		}
	}
}