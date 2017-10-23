package frontend.turtle_display;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;

public class Drawer {
	private Paint drawColor;
	private boolean penDown;
	
	public Drawer() { 
		drawColor = Color.BLACK;
		penDown = true;
	}
	
	public void pickUpPen() {
		penDown = false;
	}
	
	public void putDownPen() {
		penDown = true;
	}
	
	public void changeDrawColor(Paint color) {
		drawColor = color;
	}
	
	public void drawLine(double startX, double startY, double endX, double endY, Pane layout) {
		if(penDown) {
			Line lineToDraw = new Line(startX, startY, endX, endY);
			lineToDraw.setFill(drawColor);
			layout.getChildren().add(lineToDraw);
		}
	}
}
