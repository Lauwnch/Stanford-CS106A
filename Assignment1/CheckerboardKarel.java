/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {
	
	public void run() {
		putFirstRow();
		while (frontIsClear()) {
			putCheckerRow();
			faceNorth();
			moveIfClear();
		}
		//fence-post
		faceSouth();
		if (frontIsClear()) { //checks south in case only 1 row in world
			putCheckerRow();
		}
	}
	
	private void moveIfClear() {
		if (frontIsClear()) {
			move();
		}	
	}

	private void faceNorth() {
		while (notFacingNorth()) {
			turnLeft();
		}
	}

	private void faceSouth() {
		while (notFacingSouth()) {
			turnLeft();
		}
	}
	
	/*
	 * Rotates Karel until facing East or West with unblocked path
	 * Precondition: Karel facing North or South (Karel always facing North at beginning of while loop in run(), South on failed beeper south check)
	 * Postcondition: Karel facing direction of row
	 */
	private void faceRowDirection() {
		turnLeft();
		if (frontIsBlocked()) {
			turnAround();
		}
	}
	
	private void putFirstRow() {
		//explicit first beeper, incase of 1 column world
		putBeeper();
		moveIfClear();
		moveIfClear();
		while (frontIsClear()) {
			putBeeper();
			moveIfClear();
			moveIfClear();
		}
		putBeeperIfWestClear();
		faceNorth();
		moveIfClear();
	}
	
	private void putCheckerRow() {
		moveIfBeeperSouth();
		putBeeperEveryOther();
	}
	
	private void putBeeperIfWestClear() {
		turnAround();
		if (frontIsClear()) {
			move();
			turnAround();
			if (beepersPresent()) {
				move();
			} else {
				move();
				putBeeper();
			}
		}
	}
	
	/*
	 * Handles "odd # columns" issue by moving forward on row one corner if there is a beeper directly south of Karel on first corner of row
	 * precondition: Karel on first corner of row
	 * postcondition: Karel on first corner of row THAT NEEDS BEEPER facing row direction
	 */
	private void moveIfBeeperSouth() {
		faceSouth();
		if (frontIsBlocked()) {
			faceRowDirection();
		} else {
			move();
			faceNorth();
			if (beepersPresent()) {
				move();
				faceRowDirection();
				moveIfClear();
			} else {
				move();
				faceRowDirection();
			}
		}
	}

	private void putBeeperEveryOther() {
		while (frontIsClear()) {
			putBeeper();
			moveIfClear();
			moveIfClear();
		}
		putBeeperIfSouthClear();
	}

	private void putBeeperIfSouthClear() {
		faceSouth();
		if (frontIsBlocked()) {
			putBeeper();
		} else {
			move();
			faceNorth();
			if (noBeepersPresent()) {
				move();
				putBeeper();
			} else {
				move();
			}
		}
	}
	
}
