package frontend.turtle_display;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

public class Drawer {
	public void drawLine(double startX, double startY, double endX, double endY, Pane layout) {
		Line lineToDraw = new Line(startX, startY, endX, endY);
		layout.getChildren().add(lineToDraw);
	}
}
