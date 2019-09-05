/*
 * File: MidpointFindingKarel.java
 * -------------------------------
 * When you finish writing it, the MidpointFindingKarel class should
 * leave a beeper on the corner closest to the center of 1st Street
 * (or either of the two central corners if 1st Street has an even
 * number of corners).  Karel can put down additional beepers as it
 * looks for the midpoint, but must pick them up again before it
 * stops.  The world may be of any size, but you are allowed to
 * assume that it is at least as tall as it is wide.
 */

import stanford.karel.*;

public class MidpointFindingKarel extends SuperKarel {

	public void run() {
		diagonalBeepersRight();
		moveToFloor();
		findIntersection();
		putBeeperOnFloor();
		cleanUpBeepers();		
	}

	private void faceSouth() {
		while (notFacingSouth()) {
			turnRight();
		}
	}
	
	private void moveToWall() {
		while (frontIsClear()) {
			move();
		}
	}
	
	private void moveToFloor() {
		faceSouth();
		moveToWall();
		turnRight();
	}

	private void putBeeperOnFloor() {
		moveToFloor();
		putBeeper();
	}

	private void cleanUpBeepers() {
		moveToWall();
		turnAround();
		while (frontIsClear()) {
			pickBeeper();
			move();
			turnLeft();
			move();
			turnRight();
		}
		pickBeeper();
	}

	private void findIntersection() {
		while (noBeepersPresent()) {
			move();
			turnRight();
			if (noBeepersPresent()) {
				move();
				turnLeft();
			}
		}	
	}

	private void diagonalBeepersRight() {
		while (frontIsClear()) {
			putBeeper();
			move();
			turnLeft();
			move();
			turnRight();
		}
		putBeeper();
		
	}

}
