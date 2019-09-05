/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	private int curNum;
	private int stepCount = 0;

	public void run() {
		askForNumber();
		processNumber();
		printResult();
	}

	/*
	 * Runs through the hailstone process outlined in the assignment until it completes by reaching 1
	 * prints the specified messages at each step of the process through sub methods
	 * keeps track of the number of steps completed with stepCount
	 */
	private void processNumber() {
		int oldNum = curNum;
		while (curNum != 1) {
			if ((oldNum % 2) == 0) {
				evenProcess(oldNum);
			} else {
				oddProcess(oldNum);
			}
			oldNum = curNum;
			stepCount++;
		}
	}

	private void oddProcess(int oldNum) {
		curNum = (3 * oldNum) + 1;
		println(oldNum + " is odd, so I make 3n + 1: " + curNum);
	}

	private void evenProcess(int oldNum) {
		curNum = oldNum / 2;
		println(oldNum + " is even, so I take half: " + curNum);
	}

	private void printResult() {
		println("The process took " + stepCount  + " to reach 1");
	}

	/*
	 * Gets number from user, and does error handling for 0 and negative numbers
	 * Sets valid user input to curNum
	 */
	private void askForNumber() {
		int startingNum = 0;
		while (startingNum <= 0) {
			startingNum = readInt("Enter a number: ");
			if (startingNum <= 0) {
				println("Enter a number greater than 0");
			} else {
			curNum = startingNum;
			}
		}
	}
}

