package frontend.window_setup;

import frontend.turtle_display.TurtleView;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class IDEWindow {
	public static final Paint STANDARD_AREA_COLOR = Color.WHITE;
	public static final double TURTLEFIELD_WIDTH = 400;
	public static final double TURTLEFIELD_HEIGHT = 400;
	public static final double LEFT_WIDTH = 150;
	public static final double LEFT_HEIGHT = TURTLEFIELD_HEIGHT;
	public static final double RIGHT_WIDTH = 150;
	public static final double RIGHT_HEIGHT = TURTLEFIELD_HEIGHT;
	public static final double TOP_WIDTH = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	public static final double TOP_HEIGHT = 100;
	public static final double BOTTOM_WIDTH = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	public static final double BOTTOM_HEIGHT = 200;
	
//	private Stage primaryStage;
	private Scene primaryScene;
	private BorderPane borderLayout;
	private Rectangle turtleField;
	private GridPane turtleGrid;
	private VBox left;
	private VBox right;
	private HBox top;
	private TextField bottom;
	private double totalWidth = LEFT_WIDTH + TURTLEFIELD_WIDTH + RIGHT_WIDTH;
	private double totalHeight = TOP_HEIGHT + TURTLEFIELD_HEIGHT + BOTTOM_HEIGHT;
	
	public IDEWindow() {
		borderLayout = new BorderPane();
		primaryScene = new Scene(borderLayout, totalWidth, totalHeight, STANDARD_AREA_COLOR);
		turtleField = new Rectangle(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT, STANDARD_AREA_COLOR);
//		turtleGrid = new GridPane();
//		turtleGrid.setPrefSize(TURTLEFIELD_WIDTH, TURTLEFIELD_HEIGHT);
		left = new VBox();
//		left.setPrefSize(LEFT_WIDTH, LEFT_HEIGHT);
		right = new VBox();
//		right.setPrefSize(RIGHT_WIDTH, RIGHT_HEIGHT);
		top = new HBox();
		bottom = new TextField();
		bottom.setPrefSize(BOTTOM_WIDTH, BOTTOM_HEIGHT);
		borderLayout.setCenter(turtleField);
		borderLayout.setLeft(left);
		borderLayout.setRight(right);
		borderLayout.setTop(top);
		borderLayout.setBottom(bottom);
	}
	
	public void setUpWindow(Stage primary) {
		primary.setScene(primaryScene);
		setUpTurtleField();
	}
	
	private void setUpTurtleField() {
		TurtleView field = new TurtleView();
		field.displayInitialTurtle();
	}
	
	public Rectangle getTurtleField() {
		return turtleField;
	}
	
	public Pane getPane() {
		return borderLayout;
	}
}
