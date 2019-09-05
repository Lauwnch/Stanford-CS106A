/* COMPLETE 9/1/2019
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		while (frontIsClear()) {
			repairColumn();
			moveToNextColumn();
		}
		//fencepost
		repairColumn();
	}

	/*
	 * Moves Karel forward to the next column, assuming that columns are separated by four spaces
	 * precondition: Karel at bottom of column, facing east
	 * postcondition: Karel at bottom of next column, facing east
	 */
	private void moveToNextColumn() {
		move();
		move();
		move();
		move();	
	}

	/*
	 * Places a beeper down on every corner without a beeper for a column, then returns to original postion
	 * precondition: Karel at bottom of column, facing east
	 * postcondition: Karel at bottom of column, facing east
	 */
	private void repairColumn() {
		turnLeft();
		while (frontIsClear()) {
			repairCorner();
			move();
		}
		//fencepost
		repairCorner();
		returnToBase();
	}

	/*
	 * Moves Karel from the top of a column to the bottom of the column, in position to move to next column
	 * precondition: Karel at top of column facing north
	 * postcondition: Karel at bottom of column facing east
	 */
	private void returnToBase() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}

	/*
	 * Places beeper down if at least one not already present
	 */
	private void repairCorner() {
		if (noBeepersPresent()) {
			putBeeper();
		}
	}

}
