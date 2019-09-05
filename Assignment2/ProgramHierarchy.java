/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {	
	// I like numbers divisible by 12...
	private static final int BOX_HEIGHT = 48;
	private static final int BOX_WIDTH = 144;
	private static final int BOX_GUTTER = 24; //unit of spacing between boxes
	
	public void run() {
		TextBox pBox = new TextBox("Program");
		TextBox gpBox = new TextBox("GraphicsProgram");
		TextBox cpBox = new TextBox("ConsoleProgram");
		TextBox dpBox = new TextBox("DialogProgram");
		
		pBox.moveUp();
		
		gpBox.moveDown();
		gpBox.moveLeft();
		
		cpBox.moveDown();
		
		dpBox.moveDown();
		dpBox.moveRight();
		
		connect(pBox, gpBox);
		connect(pBox, cpBox);
		connect(pBox, dpBox);
	}
	
	/*
	 * adds line from bottom middle of "hibox" to top middle of "lobox"
	 */
	private void connect(TextBox hiBox, TextBox loBox) {
		int x1 = hiBox.getCoorX() + (BOX_WIDTH/2); //not going to make methods for these... sorry professor
		int y1 = hiBox.getCoorY() + (BOX_HEIGHT); //if I were allowed to use arrays on this assignment, I might have...
		int x2 = loBox.getCoorX() + (BOX_WIDTH/2);
		int y2 = loBox.getCoorY();

		add(new GLine(x1, y1, x2, y2));	
	}

	private class TextBox {
		public TextBox(String text) {
			drawBox();
			drawLabel(text);
		}
		
		private void drawBox() {
			box = genCenteredBox();
			add(box);
		}

		// generates and returns a GRect whose center lies on center of window
		private GRect genCenteredBox() {
			int x = getWidth()/2 - (BOX_WIDTH/2);
			int y = getHeight()/2 - (BOX_HEIGHT/2);
			GRect centeredBox = new GRect(x, y, BOX_WIDTH, BOX_HEIGHT);
			return centeredBox;
		}

		private void drawLabel(String text) {
			label = genCenteredLabel(text);
			add(label);
		}

		// generates and returns a GLabel centered in window		 
		private GLabel genCenteredLabel(String text) {
			GLabel centeredLabel = new GLabel(text, getWidth()/2, getHeight()/2);
			int dx = (int) -(centeredLabel.getWidth() / 2);
			int dy = (int) (centeredLabel.getAscent() / 2);
			centeredLabel.move(dx, dy);
			return centeredLabel;
		}

		//each of these functions moves the TextBox by the amount specified as BOX_GUTTER
		//moves means "moves edge BOX_GUTTER away from nearest edge"
		public void moveUp() {
			int dy = BOX_GUTTER + (BOX_HEIGHT/2);
			box.move(0, -dy);
			label.move(0, -dy);
		}
		
		public void moveDown() {
			int dy = BOX_GUTTER + (BOX_HEIGHT/2);
			box.move(0, dy);
			label.move(0, dy);
		}
		
		public void moveLeft() {
			int dx = BOX_GUTTER + BOX_WIDTH;
			box.move(-dx, 0);
			label.move(-dx, 0);
		}
		
		public void moveRight() {
			int dx = BOX_GUTTER + BOX_WIDTH;
			box.move(dx, 0);
			label.move(dx, 0);
		}
		
		public int getCoorX() {
			int x = (int) box.getX();
			return x;			
		}
		
		public int getCoorY() {
			int y = (int) box.getY();
			return y;
		}
		
		private GRect box;
		private GLabel label;
	}
}