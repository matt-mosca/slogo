package frontend.turtle_display;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

/**
 * Drawer.java
 * @author Matthew Mosca
 * Draws the line made by a turtle.
 * @version 11.03.17
 */
public class Drawer {

	private static final double DEFAULT_LINE_WIDTH = 1.0;

	private Paint drawColor;
	private double strokeWidth;
	private boolean isPenDown;
	
	/**
	 * Default constructor for class Drawer.
	 */
	public Drawer() { 
		drawColor = Color.BLACK;
		strokeWidth = DEFAULT_LINE_WIDTH;
		isPenDown = true;
	}
	
	/**
	 * Parameterized constructor for class Drawer. Allows caller of method to specify color, width, and
	 * whether the pen is down (whether a line should be drawn at all).
	 * @param color - the color of the line to be drawn
	 * @param width - the width of the line to be drawn
	 * @param down - boolean indicating whether the pen is down and the line should be drawn
	 */
	public Drawer(Paint color, double width, boolean down) {
		drawColor = color;
		strokeWidth = width;
		isPenDown = down;
	}
	
	/**
	 * Draws the line based on the initialized instance variables and specified parameters.
	 * @param startX - the x-coordinate where the line should start
	 * @param startY - the y-coordinate where the line should start
	 * @param endX - the x-coordinate where the line should end
	 * @param endY - the y-coordinate where the line should end
	 * @param layout - the Pane on which the lines are to be displayed, in the case of this program, the 
	 * Pane on which most of the elements are arranged
	 */
	public void drawLine(double startX, double startY, double endX, double endY, Pane layout) {
		if(isPenDown) {
			Line lineToDraw = new Line(startX, startY, endX, endY);
			lineToDraw.setStroke(drawColor);
			lineToDraw.setStrokeWidth(strokeWidth);
			layout.getChildren().add(lineToDraw);
		}
	}
}
