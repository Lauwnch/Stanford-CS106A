/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * Draws a red and white target three layers deep in the center of the window.
 * Total radius is controlled by the BASE_RADIUS constant.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {	
	private static final int BASE_RADIUS = 72;
	
	public void run() {
		double outerRadius = BASE_RADIUS;
		double middleRadius = BASE_RADIUS * 0.72;
		double innerRadius = BASE_RADIUS * 0.3;
		
		drawCircle(outerRadius, Color.red);
		drawCircle(middleRadius, Color.white);
		drawCircle(innerRadius, Color.red);
	}

	/*
	 * adds a circle of given radius and color to the canvas
	 * circle will be centered on canvas
	 */
	private void drawCircle(double radius, Color circleColor) {
		double x = findCircleX(radius);
		double y = findCircleY(radius);
		GOval circle = new GOval(x, y, (2*radius), (2*radius));
		circle.setFilled(true);
		circle.setFillColor(circleColor);
		add(circle);
		
	}

	/*
	 * finds x coor for circle such that its center is the center of the window
	 */
	private double findCircleX(double radius) {
		double centerX = getWidth()/2;
		double circleX = centerX - radius;
		return circleX;
	}

	/*
	 * finds y coor for circle such that its center is the center of the window
	 */
	private double findCircleY(double radius) {
		double centerY = getHeight()/2;
		double circleY = centerY - radius;
		return circleY;
	}

}
