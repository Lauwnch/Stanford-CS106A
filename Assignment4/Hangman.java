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
	private static final int MAXBADGUESS = 8; //based on the number of limbs of the hangman
	private static RandomGenerator rgen = RandomGenerator.getInstance();
	private HangmanCanvas canvas;
	private HangmanLexicon hl;
	
	public void init() {
		hl = new HangmanLexicon();
		canvas = new HangmanCanvas();
		add(canvas);
	}
	
    public void run() {
		initGameValues();
		runGame();
		endGame();
	}

    private void initGameValues() {
		canvas.reset();
		isOutOfGuesses = false;
		isWordFinished = false;
		setActualWord();
		initGuessedWord();
	}

    private void setActualWord() {
    	int wordIndex = rgen.nextInt(hl.getWordCount());
		actualWord = hl.getWord(wordIndex);
	}
    
	private void initGuessedWord() {
		guessedWord = "";
		for (int i = 0; i < actualWord.length(); i++) {
			guessedWord = guessedWord + "-";
		}	
	}

    private void endGame() {
    	if (isWordFinished) {
    		println("You guessed the word: " + actualWord);
    		println("You win.");
    	} else if (isOutOfGuesses) {
    		println("You're completely hung.");
    		println("The word was: " + actualWord);
    		println("You lose.");
    	}
	}
    
    private void runGame() {
    	println(GREETING);
    	while(!isOutOfGuesses && !isWordFinished) {
    		printGameState();
    		updateGameState(readGuess());
    		checkWordFinished();
    		checkOutOfGuesses();
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
	
	/*
	 * reads input from user, which is intended to be a single English character
	 * if input does not meet this requirement, user will be prompted to try again
	 */
	private String readGuess() {
		String unsafeGuess = readLine("Your Guess: ").trim().toUpperCase();
		if (!unsafeGuess.matches("[a-zA-z]") || unsafeGuess.length() != 1) {
			println("Please enter a single letter.");
			return readGuess();
		} else {
			return unsafeGuess.toUpperCase();
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
			println("That guess is correct.");
			updateGuessedWord(guessedLetter);
		} else {
			badGuessCount++;
			canvas.noteIncorrectGuess(guessedLetter.charAt(0));
			println("There is no " + guessedLetter + " in the word.");
			//whatever other action that will happen for bad guesses can go here
		}
	}

    /*
     * Inputs - letter that is confirmed as being in actualWord
     * Output - no return, guessedWord is updated to include the guessed letter in proper position
     */
	private void updateGuessedWord(String guessedLetter) {
		int guessIndex = 0;
		int indexOffset = 0;
		//we loop here because there can potentially be multiple instances of guessedLetter in actualWord
		while (indexOffset < actualWord.length()) { //could be while true, but this protects against double letters at end of word
			guessIndex = actualWord.indexOf(guessedLetter, indexOffset);
			if (guessIndex < 0) return; //exits loop if no further instances of guessed letter are in actual word
			else {
				guessedWord = guessedWord.substring(0, guessIndex) + guessedLetter + guessedWord.substring(guessIndex + 1);
				indexOffset = guessIndex + 1;
			}
		}
	}

	private void checkWordFinished() {
		if (guessedWord.equals(actualWord)) {
			isWordFinished = true;
		} else {
			return;
		}
	}

	private void checkOutOfGuesses() {
		if (MAXBADGUESS - badGuessCount <= 0) {
			isOutOfGuesses = true;
		} else {
			return;
		}
	}
		
	private boolean isWordFinished;
    private boolean isOutOfGuesses;
    private static int badGuessCount;
    private String actualWord;
    private String guessedWord;

	public static int getBadGuessCount() {
		return badGuessCount;
	}
}
