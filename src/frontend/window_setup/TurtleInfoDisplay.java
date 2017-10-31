package frontend.window_setup;

import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class TurtleInfoDisplay {
	GridPane displayPane;
	public TurtleInfoDisplay() {
		displayPane = new GridPane();
		Text title = new Text("Turtle Information");
		Text idHeader = new Text("ID");
		Text posHeader = new Text("Position");
		Text headingHeader = new Text("Heading");
		Text penStateHeader = new Text("Pen State");
		Text penColorHeader = new Text("Pen Color");
		Text penThicknessHeader = new Text("Pen Thickness");
		displayPane.add(title, 2, 0);
		displayPane.add(idHeader, 0, 1);
		displayPane.add(posHeader, 1, 1);
		displayPane.add(headingHeader, 2, 1);
		displayPane.add(penStateHeader, 3, 1);
		displayPane.add(penColorHeader, 4, 1);
		displayPane.add(penThicknessHeader, 5, 1);
	}
	
	public GridPane getDisplay() {
		return displayPane;
	}
}
