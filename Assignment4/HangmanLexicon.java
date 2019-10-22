/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import acm.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HangmanLexicon {
	
	private static final String LEX_LOCATION = "./ShorterLexicon.txt";
	
	public HangmanLexicon() {
		try {
			BufferedReader read = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while(true) {
				String word = read.readLine();
				if (word == null) break;
				lex.add(word);
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new ErrorException(e);
		}
	}

/** Returns the number of words in the lexicon. */
	public int getWordCount() {
		return lex.size();
	}

/** Returns the word at the specified index. */
	public String getWord(int index) {
		if (index > lex.size()-1) {
			throw new ErrorException("getWord: Illegal index");
		} else {
			return lex.get(index);
		}
//		switch (index) {
//			case 0: return "BUOY";
//			case 1: return "COMPUTER";
//			case 2: return "CONNOISSEUR";
//			case 3: return "DEHYDRATE";
//			case 4: return "FUZZY";
//			case 5: return "HUBBUB";
//			case 6: return "KEYHOLE";
//			case 7: return "QUAGMIRE";
//			case 8: return "SLITHER";
//			case 9: return "ZIRCON";
//			default: throw new ErrorException("getWord: Illegal index");
//		}
	};
	
	private ArrayList<String> lex = new ArrayList<String>();
	
}
