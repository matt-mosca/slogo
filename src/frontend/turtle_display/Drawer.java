package frontend.turtle_display;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Drawer {

	private static final double DEFAULT_LINE_WIDTH = 1.0;

	private Paint drawColor;
	private double strokeWidth;
	private boolean isPenDown;
	
	public Drawer() { 
		drawColor = Color.BLACK;
		strokeWidth = DEFAULT_LINE_WIDTH;
		isPenDown = true;
	}
	
	public Drawer(Paint color, double width, boolean down) {
		drawColor = color;
		strokeWidth = width;
		isPenDown = down;
	}
	
	public void drawLine(double startX, double startY, double endX, double endY, Pane layout) {
		if(isPenDown) {
			Line lineToDraw = new Line(startX, startY, endX, endY);
			lineToDraw.setStroke(drawColor);
			lineToDraw.setStrokeWidth(strokeWidth);
			layout.getChildren().add(lineToDraw);
		}
	}
}
