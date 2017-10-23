package frontend.turtle_display;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Drawer {
	private Paint drawColor;
	
	public Drawer() { 
		drawColor = Color.BLACK;
	}
	
	public void changeDrawColor(Paint color) {
		drawColor = color;
	}
	
	public void drawLine(double startX, double startY, double endX, double endY, Pane layout) {
		Line lineToDraw = new Line(startX, startY, endX, endY);
		layout.getChildren().add(lineToDraw);
	}
}
