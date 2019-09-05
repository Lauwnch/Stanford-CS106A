/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 * NOTE: I could have gotten more precision with double values for constants/xy coors
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 28;

/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;
	
	public void run() {
		while (numBricks > 0) {
			int currentRow = BRICKS_IN_BASE - numBricks;
			int rowStartX = findRowStartX(currentRow);
			int rowStartY = findRowStartY(currentRow);
			layRow(rowStartX, rowStartY);
			numBricks -= 1;
		}
	}
	
	/*
	 * Will determine the x coordinate of first brick in a row, based on what the row number is
	 * eg the base row is row 0
	 */
	private int findRowStartX(int rowNumber) {
		int rowStartX = 0;
		//finds x coor for first brick in base layer
		int firstRowStartX = (getWidth() / 2) - (BRICK_WIDTH * (BRICKS_IN_BASE / 2));
		//adds half brick width per row after starting row to rowStartX
		rowStartX = firstRowStartX + rowNumber * (BRICK_WIDTH / 2);		
		return rowStartX;
	}
	
	/*
	 * Will determine the y coordinate of first brick in a row, based on what the row number is
	 * Keep in mind that y increases downward!
	 */
	private int findRowStartY(int rowNumber) {
		int firstRowStartY = getHeight() - BRICK_HEIGHT;
		int rowStartY = firstRowStartY - (rowNumber * BRICK_HEIGHT);
		return rowStartY;
	}
	
	/*
	 * lays out a row of non-overlapping bricks
	 * origin of first brick is rowStartX, rowStartY
	 * number of bricks laid is numBricks from run()
	 */
	private void layRow(int rowStartX, int rowStartY) {
		int x = rowStartX;
		int y = rowStartY;
		for (int i = 0; i < numBricks; i++) {
			add(new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT));
			x += BRICK_WIDTH;
		}
	}
	
	private int numBricks = BRICKS_IN_BASE;
}

