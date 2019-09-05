/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {
	private static final int SENTINEL = 0;
	
	public void run() {
		greetUser();
		askFirstNumber();
		askAdditionalNumbers();
		printRange();		
	}
	
	private void printRange() {
		//checks if sentinel was first value
		if (smallestNum == SENTINEL && largestNum == SENTINEL) {
			println("Your first input indicated that you wanted to immediately exit. Goobye.");
		} else {
		println("smallest: " + smallestNum);
		println("largest: " + largestNum);		
		}
	}

	private void askAdditionalNumbers() {
		while (true) {
			if (smallestNum == SENTINEL && largestNum == SENTINEL) {
				break; //checks if sentinel was first value
			}
			int curNum = readInt("? ");
			if (curNum == SENTINEL) {
				break;
			} else if (curNum > largestNum) {
				largestNum = curNum;
			} else if (curNum < smallestNum) {
				smallestNum = curNum;
			}
		}		
	}

	private void askFirstNumber() {
		int curNum = readInt("? ");		
		smallestNum = curNum;
		largestNum = curNum;
	}

	private void greetUser() {
		println("This program finds the largest and smallest numbers.");
		
	}

	private int smallestNum = 0;
	private int largestNum= 0;
}

