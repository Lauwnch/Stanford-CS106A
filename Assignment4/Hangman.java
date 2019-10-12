/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.awt.*;

public class Hangman extends ConsoleProgram {

	private static final String GREETING = "Welcome to Hangman!";
	private static final int MAXBADGUESS = 8; 
	
    public void run() {
		setWord();
		initGameValues();
		runGame();
		endGame();
	}

    private void initGameValues() {
		currentRound = 0;
		isOutOfGuesses = false;
		isWordFinished = false;
		initGuessedWord();
	}

	private void initGuessedWord() {
		guessedWord = "";
		for (int i = 0; i < actualWord.length(); i++) {
			guessedWord = guessedWord + "-";
		}
		
	}

	/*
     * TODO add logic to check for ending state
     */
    private void endGame() {
		println("This is the end");		
	}

	/*
     * TODO bring in lexicon
     */
    private void setWord() {
		actualWord = "SALMON";
	}
    
    private void runGame() {
    	println(GREETING);
    	while(!isOutOfGuesses && !isWordFinished) {
    		printGameState();
    		updateGameState(readGuess());
    		currentRound++;
    		checkWordFinished();
    		checkOutOfGuesses();
    	}
    }

    
    /*
     * Based on guessed letter, actual word, and guessed word -
     * Updates guessed word and guess count
     */
    private void updateGameState(String guessedLetter) {
		if (guessedWord.contains(guessedLetter)) {
			return;
		} else if (actualWord.contains(guessedLetter)) {
			updateGuessedWord(guessedLetter);
		} else {
			badGuessCount++;
		}
	}

    /*
     * Inputs - letter that is confirmed as being in actualWord
     * Output - no return, guessedWord is updated to include the guessed letter in proper position
     */
	private void updateGuessedWord(String guessedLetter) {
		int guessIndex = actualWord.indexOf(guessedLetter);
		guessedWord = guessedWord.substring(0, guessIndex) + guessedLetter + guessedWord.substring(guessIndex + 1);
	}

	private String readGuess() {
		String unsafeGuess = readLine("").trim().toUpperCase();
		if (!unsafeGuess.matches("[a-zA-z]") || unsafeGuess.length() != 1) {
			println("Please enter a single letter.");
			return readGuess();
		} else {
			return unsafeGuess.toUpperCase();
		}
		
	}

	private void printGameState() {
    	int guessRemaining = MAXBADGUESS - badGuessCount;
		println("The word now looks like this: " + guessedWord);
		if (guessRemaining == 1) {
			println("You have only one guess left.");
		} else {
			println("You now have " + guessRemaining + " guesses left.");
		}
	}

	private void checkWordFinished() {
		// TODO Auto-generated method stub
		
	}

	private void checkOutOfGuesses() {

		}
		


	private boolean isWordFinished;
    private boolean isOutOfGuesses;
    private int badGuessCount;
	private int roundNum;
    private int currentRound;
    private String actualWord;
    private String guessedWord;
}
