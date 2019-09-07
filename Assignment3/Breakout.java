/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;
	private static final int PADDLE_Y = APPLICATION_HEIGHT - PADDLE_Y_OFFSET - PADDLE_HEIGHT;

/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

/** Separation between bricks */
	private static final int BRICK_SEP = 4;

/** Width of a brick */
	private static final int BRICK_WIDTH =
	  (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;
	
/**Starting coordinates of the ball based on its radius and the dimensions of the application */
	private static final int BALL_START_Y = (APPLICATION_HEIGHT / 2) - BALL_RADIUS;
	private static final int BALL_START_X = (APPLICATION_WIDTH / 2) - BALL_RADIUS;

/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;
	
/** Offset of the left brick column from the side */
	private static final int BRICK_X_OFFSET = 
			(APPLICATION_WIDTH / 2) - ((NBRICKS_PER_ROW / 2) * BRICK_WIDTH) 
			- (((NBRICKS_PER_ROW / 2) - 1) * BRICK_SEP) - (BRICK_SEP / 2);

/** Number of turns */
	private static final int NTURNS = 3;
	
/** Maximum Y velocity of ball cannot exceed height of brick*/
	public static final double MAX_Y_VELOCITY = 9.0;
	
/** Maximum X velocity of ball cannot exceed height of brick */
	public static final double MAX_X_VELOCITY = 9.0;

/** random number generator instance for class */
	private static RandomGenerator rgen = RandomGenerator.getInstance();
	
/* Method: run() */
/** Runs the Breakout program. */
	public void run() {
		setupGame();
		runGame();
		endGame();		
	}
	
	private void runGame() {
		while( !hasWon && !hasLost ) {
			stepAnimations();
			checkCollisions();
			checkWinLose();
			pause(20);
		}	
}

	private void checkWinLose() {
		if (numBricks == 0) hasWon = true;
		if (numLives == 0) hasLost = true;
	}

	private void endGame() {
		if( hasWon ) {
			runVictory();
		} else if ( hasLost ) {
			runDefeat();
		}	
}

	private void runDefeat() {
		removeAll();
		GLabel youAreATotalLoser = new GLabel("Loser!");
		youAreATotalLoser.setFont("sans-bold-50");
		double x = (APPLICATION_WIDTH / 2) - (youAreATotalLoser.getWidth() / 2);
		double y = (APPLICATION_HEIGHT / 2) + (youAreATotalLoser.getAscent() / 2);
		add(youAreATotalLoser, x, y);
	}

	private void runVictory() {
		removeAll();
		GLabel winner = new GLabel("VICTORY!!!");
		winner.setFont("sans-bold-50");
		double x = (APPLICATION_WIDTH / 2) - (winner.getWidth() / 2);
		double y = (APPLICATION_HEIGHT / 2) + (winner.getAscent() / 2);
		add(winner, x, y);
	}

	private void stepAnimations() {
		//only ball needs to be stepped right now, later might add brick animations
		ball.stepAnimation();
	}
	
	private void checkCollisions() {
		//only ball needs checks right now, might need to add paddle checks later
		checkBallCollisions();
	}

	private void checkBallCollisions() {
		handleCollisions(ball.getTop());
		handleCollisions(ball.getBottom());
		handleCollisions(ball.getLeft());
		handleCollisions(ball.getRight());		
	}
	
	/*
	 * TODO better name (since only for balls), better paddle logic, and some way to avoid "tower" state
	 */
	private void handleCollisions(GPoint collidee) {
		GObject collider = getElementAt(collidee);
		if (collider == paddle) { //ball touches paddle
			if (collidee.equals(ball.getTop())) {
				return;
			} else { //I AM A MERCIFUL DEV so will allow hits even if paddle hits side of ball
				ball.paddleBounce();
				ball.accelerate();
			}
		} else if (collider == livesIndicator) {
			return;
		} else if (collidee.getX() <= 0 || collidee.getX() >= APPLICATION_WIDTH) { //ball touches sides
			ball.bounceHoriz();
		} else if (collidee.getY() <= 0) { //ball touches top
			ball.bounceVert();
		} else if (collidee.getY() >= APPLICATION_HEIGHT) { //ball touches bottom
			die();
		} else if (collider instanceof Brick) { //ball touches brick
			remove(collider);
			numBricks--;
			if (collidee.equals(ball.getLeft()) || collidee.equals(ball.getRight())) { //side of ball touches brick
				ball.bounceHoriz();
			} else { //top or bottom of ball touches brick
				ball.bounceVert();
			}
		}
	}
	
	private void die() {
		numLives--;
		remove(ball);
		initBall();
		if (numLives > 0) {
			livesIndicator.setLabel(String.valueOf(numLives));
			waitForClick();
		}
	}

	private void setupGame() {
		numBricks = NBRICK_ROWS * NBRICKS_PER_ROW;
		hasWon = false;
		hasLost = false;
		numLives = NTURNS;
		
		initWindow();
		initWalls();
		initBricks();
		initPaddle();
		initBall();
		addMouseListeners(); //for paddle movement with mouse
	}
	
	public void mouseMoved(MouseEvent e) {
		//true if paddle edges will remain within application
		if((e.getX() >= (PADDLE_WIDTH / 2)) && e.getX() <= (APPLICATION_WIDTH - (PADDLE_WIDTH / 2)) ) {
			paddle.setPaddleCenterLocation(e.getX());
		}
	}	

	private void initWindow() {
		//need to add in modifiers for menu height and window border width, because library
		//values found by experimentation ... unable to find documentation on this, except others having the same problem
		//see https://stackoverflow.com/questions/13379458/how-to-handle-window-size-for-graphicsprogram-acm-library
		this.setSize(APPLICATION_WIDTH + 17, APPLICATION_HEIGHT + 60);
		livesIndicator = new GLabel(String.valueOf(numLives));
		livesIndicator.setFont("sans-bold-50");
		double x = 10.0;
		double y = livesIndicator.getAscent();
		add(livesIndicator, x, y);
		pause(20);
	}
	
	private void initBall() {
		ball = new Ball();
		add(ball);
	}
	
	private void initPaddle() {
		paddle = new Paddle();
		paddle.setPaddleCenterLocation(APPLICATION_WIDTH/2);
		add(paddle);
	}

	private void initWalls() {
		// TODO make thicker/prettier border by using alternating white and black filled rectangles
		walls = new GRect(APPLICATION_WIDTH, APPLICATION_HEIGHT);
		add(walls);
	}

	/*
	 * Creates and adds all the bricks necessary for Breakout, with the colors specified in the assignment
	 * Currently assumes that any rows outside of the default 10 will just be grey
	 */
	private void initBricks() {
		for (int row = 0; row < NBRICK_ROWS; row++ ) {
			Color rowColor = setRowColor(row);
			
			for (int col = 0; col < NBRICKS_PER_ROW; col++) {
				//rows are built from top down, with y increasing by brick height and brick sep each row
				int y = BRICK_Y_OFFSET + (row * (BRICK_HEIGHT + BRICK_SEP));
				int x = BRICK_X_OFFSET + (col * (BRICK_WIDTH + BRICK_SEP));
				
				Brick brick = new Brick(rowColor);
				add(brick, x, y);
			}
		}
	}

	/*
	 * Returns the color of a row of bricks, given the row number (starting with 0)
	 */
	private Color setRowColor(int row) {
		switch (row) {
			case 0:
			case 1: return Color.RED;
			case 2: 
			case 3: return Color.ORANGE;
			case 4:
			case 5: return Color.YELLOW;
			case 6:
			case 7: return Color.GREEN;
			case 8:
			case 9: return Color.BLUE;
			default: return Color.GRAY;
		}
	}

	private int numBricks;
	private int numLives;
	private boolean hasWon;
	private boolean hasLost;
	private GLabel livesIndicator;
	private Paddle paddle;
	private Ball ball;
	private GRect walls;
	
	private class Brick extends GRect {
		public Brick() {
			super(BRICK_WIDTH, BRICK_HEIGHT);
			setFilled(true);
		}
		
		public Brick(Color color) {
			this();
			setFillColor(color);
		}
		// TODO make destroy() that includes animation (someday...)
	}

	private class Paddle extends GRect {
		public Paddle() {
			super(PADDLE_WIDTH, PADDLE_HEIGHT);
			setFilled(true);
		}
		
		/*
		 * Sets the center of paddle to the x location specified
		 * Always keeps paddle's y location the same
		 */
		public void setPaddleCenterLocation(double x) {
			double paddleX = x - (PADDLE_WIDTH / 2);
			this.setLocation(paddleX, PADDLE_Y);
		}
	}
	
	private class Ball extends GOval {
		public Ball() {
			super(BALL_START_X, BALL_START_Y, 2 * BALL_RADIUS, 2 * BALL_RADIUS);
			setFilled(true);
			initVelocity();
		}
		
		/*
		 * increase magnitude of ball velocity, regardless of direction it is traveling
		 */
		public void accelerate() {
			if (Math.abs(vy) < MAX_Y_VELOCITY) {
				if (vy < 0) vy -=0.5;
				else if (vy > 0) vy += 0.5;
			}
			if (Math.abs(vx) < MAX_X_VELOCITY) 
				if (vx < 0) vx -=0.5;
				else if (vx > 0) vx += 0.5;
		}

		public void paddleBounce() {
			this.bounceVert();
			this.setLocation(ball.getX(), paddle.getY() - (BALL_RADIUS * 2) - 2);
		}

		private void initVelocity() {
			vy = +5.0;
			vx = rgen.nextDouble(1.0, 4.0);
			if (rgen.nextBoolean(0.5)) vx = -vx;			
		}
		
		/*
		 * moves ball one step forward in time based on x and y velocity (vx and vy)
		 */
		public void stepAnimation() {
			double newX = this.getX() + vx;
			double newY = this.getY() + vy;
			this.setLocation(newX, newY);
		}
		
		//point is offset 1 pixel outside of ball, so that getElementAt(point) doesn't return the ball itself
		public GPoint getTop() {
			GPoint top = new GPoint(this.getX() + BALL_RADIUS, this.getY() - 1);
			return top;
		}
		
		public GPoint getBottom() {
			GPoint bottom = new GPoint(this.getX() + BALL_RADIUS, this.getY() + (2 * BALL_RADIUS) + 1);
			return bottom;
		}
		
		public GPoint getLeft() {
			GPoint left = new GPoint(this.getX() - 1, this.getY() + BALL_RADIUS);
			return left;
		}
		
		public GPoint getRight() {
			GPoint right = new GPoint(this.getX() + (BALL_RADIUS * 2) + 1, this.getY() + BALL_RADIUS);
			return right;
		}
		
		public void bounceHoriz() {
			vx = -vx;
		}
		
		public void bounceVert() {
			vy = -vy;
		}
		
		private double vx;
		private double vy;
	}
}
