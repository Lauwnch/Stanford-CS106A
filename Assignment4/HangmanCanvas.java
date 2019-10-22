/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	private void addLabels() {
		badGuesses = new GLabel("", 30, this.getHeight() - 20);
		badGuesses.setFont("*-*-16");
		add(badGuesses);
		
		guessedWord = new GLabel("", 30, badGuesses.getLocation().getY() - badGuesses.getAscent() - 10);
		guessedWord.setFont("*-*-38");
		add(guessedWord);
	}

	private void addArms() {
		double armY = this.getHeight()/2 - BODY_LENGTH + ARM_OFFSET_FROM_HEAD;
		
		this.lArm = new GLine(this.getWidth()/2, armY, this.getWidth()/2 - UPPER_ARM_LENGTH, armY);
		lArm.setVisible(false);
		add(lArm);
		this.rArm = new GLine(this.getWidth()/2, armY, this.getWidth()/2 + UPPER_ARM_LENGTH, armY);
		rArm.setVisible(false);
		add(rArm);
	}

	private void addFeet() {
		double feetY = this.getHeight()/2 + LEG_Y_OFFSET;
		double lFootStartX = this.getWidth()/2 - LEG_X_OFFSET;
		double rFootStartX = this.getWidth()/2 + LEG_X_OFFSET;
		
		this.lFoot = new GLine(lFootStartX, feetY, lFootStartX - FOOT_LENGTH, feetY);
		lFoot.setVisible(false);
		add(lFoot);
		this.rFoot = new GLine(rFootStartX, feetY, rFootStartX + FOOT_LENGTH, feetY);
		rFoot.setVisible(false);
		add(rFoot);
	}

	private void addLegs() {
		double legCenterX = this.getWidth()/2;
		double legCenterY = this.getHeight()/2;
		
		this.rLeg = new GLine(legCenterX, legCenterY, legCenterX + LEG_X_OFFSET, legCenterY + LEG_Y_OFFSET);
		rLeg.setVisible(false);
		add(rLeg);
		this.lLeg = new GLine(legCenterX, legCenterY, legCenterX - LEG_X_OFFSET, legCenterY + LEG_Y_OFFSET);
		lLeg.setVisible(false);
		add(lLeg);
	}

	private void addHead() {
		double headBottomY = this.getHeight()/2 - BODY_LENGTH;
		double headBottomX = this.getWidth()/2;
		
		this.head = new GOval(headBottomX - HEAD_RADIUS, headBottomY - HEAD_RADIUS*2, HEAD_RADIUS*2, HEAD_RADIUS*2);
		head.setVisible(false);
		add(head);
	}

	private void addBody() {
		double x1 = this.getWidth()/2;
		double y1 = this.getHeight()/2;
		
		this.body = new GLine(x1, y1, x1, y1 - BODY_LENGTH);
		body.setVisible(false);
		add(body);
	}

	private void addScaffold() {
		double scaffoldX = this.getWidth()/2 - BEAM_LENGTH;
		double scaffoldTopY = this.getHeight()/2 - BODY_LENGTH - HEAD_RADIUS*2 - ROPE_LENGTH;
		this.scaffold = new GLine(scaffoldX, scaffoldTopY, scaffoldX, scaffoldTopY + SCAFFOLD_HEIGHT);
		add(scaffold);
		
		this.beam = new GLine(scaffoldX, scaffoldTopY, scaffoldX + BEAM_LENGTH, scaffoldTopY);
		add(beam);
		
		this.rope = new GLine(this.getWidth()/2, scaffoldTopY, this.getWidth()/2, scaffoldTopY + ROPE_LENGTH);
		add(rope);
	}

/** Resets the display so that only the scaffold appears */
	public void reset() {
		this.removeAll();
		addScaffold();
		addBody();
		addHead();
		addLegs();
		addFeet();
		addArms();
		addLabels();
	}

/**
 * Updates the word on the screen to correspond to the current
 * state of the game.  The argument string shows what letters have
 * been guessed so far; unguessed letters are indicated by hyphens.
 */
	public void displayWord(String word) {
		guessedWord.setLabel(word);
	}

/**
 * Updates the display to correspond to an incorrect guess by the
 * user.  Calling this method causes the next body part to appear
 * on the scaffold and adds the letter to the list of incorrect
 * guesses that appears at the bottom of the window.
 */
	public void noteIncorrectGuess(char letter) {
		addBodyPart(Hangman.getBadGuessCount());
		addBadLetter(letter);
	}

private void addBadLetter(char letter) {
	String newGuesses = badGuesses.getLabel() + letter;
	badGuesses.setLabel(newGuesses);
}

private void addBodyPart(int badGuessCount) {
	switch (badGuessCount) {
		case 1: head.setVisible(true); break;
		case 2: body.setVisible(true); break;
		case 3: rArm.setVisible(true); break;
		case 4: lArm.setVisible(true); break;
		case 5: rLeg.setVisible(true); break;
		case 6: rFoot.setVisible(true); break;
		case 7: lLeg.setVisible(true); break;
		case 8: lFoot.setVisible(true); break;
	}
}

/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int LEG_X_OFFSET = LEG_LENGTH*3/4;
	private static final int LEG_Y_OFFSET = LEG_LENGTH*4/5;
	private static final int FOOT_LENGTH = 28;

	
/* Instance variables for every graphic element */
	private GLine scaffold;
	private GLine beam;
	private GLine rope;
	private GLine body;
	private GOval head;
	private GLine lLeg;
	private GLine lFoot;
	private GLine rLeg;
	private GLine rFoot;
	private GLine lArm;
	private GLine rArm;
	private GLabel guessedWord;
	private GLabel badGuesses;
}
