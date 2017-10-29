package frontend.turtle_display;

import backend.Controller;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

public class TurtleKeyControls {
	private Scene primaryScene;
	private TurtleGraphicalControls graphicalControl;
	private Controller turtleControl;
	public TurtleKeyControls(Scene scene, Controller control) {
		primaryScene = scene;
		turtleControl = control;
		graphicalControl = new TurtleGraphicalControls(control);
	}
	public void connectKeysToScene() {
		primaryScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	}
	public void handleKeyInput (KeyCode code) {
		if(code == KeyCode.W) {
			graphicalControl.moveForward();
        }
		if(code == KeyCode.S) {
			graphicalControl.moveBackward();
        }
		if(code == KeyCode.A) {
			graphicalControl.rotateLeft();
        }
		if(code == KeyCode.D) {
			graphicalControl.rotateRight();
        }
		if(code == KeyCode.P) {
			if(turtleControl.isPenDown(1) == 1)
				turtleControl.setPenUp(1);
			else
				turtleControl.setPenDown(1);
        }
	}
}
