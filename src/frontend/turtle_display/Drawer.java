package frontend.turtle_display;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Drawer {
	private Paint drawColor;
	private double strokeWidth;
	private boolean isPenDown;
	
	public Drawer() { 
		drawColor = Color.BLACK;
		strokeWidth = 10.0;
		isPenDown = true;
	}
	
	public Drawer(Paint color, boolean down) {
		drawColor = color;
		isPenDown = down;
	}

	public void setDrawColor(Paint color) { drawColor = color; }

	public void setStrokeWidth(double width) { strokeWidth = width; }
	
	public void drawLine(double startX, double startY, double endX, double endY, Pane layout) {
		if(isPenDown) {
			Line lineToDraw = new Line(startX, startY, endX, endY);
			//lineToDraw.setStrokeWidth(strokeWidth);
			//TODO - line above doesn't work, no idea why
			lineToDraw.setStroke(drawColor);
			layout.getChildren().add(lineToDraw);
			System.out.println(drawColor);
		}
	}
}
