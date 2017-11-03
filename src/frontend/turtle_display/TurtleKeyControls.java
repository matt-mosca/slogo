package frontend.turtle_display;

import backend.Controller;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.List;

/**
 * TurtleKeyControls.java
 * @author Matthew Mosca
 * Defines the actions executed when different keys are pressed during the program.
 * @version 11.03.17
 */
public class TurtleKeyControls {
	private Scene primaryScene;
	private TurtleGraphicalControls graphicalControl;
	private Controller turtleControl;
	
	/**
	 * Constructor for class TurtleKeyControls.
	 * @param scene - the scene on which the key controls should be in effect
	 * @param control - the controller for the program
	 */
	public TurtleKeyControls(Scene scene, Controller control) {
		primaryScene = scene;
		turtleControl = control;
		graphicalControl = new TurtleGraphicalControls(control);
	}
	
	/**
	 * Makes it so that the specified actions occur when the correct keys are pressed when the main scene 
	 * of the program is in place.
	 */
	public void connectKeysToScene() {
		primaryScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
	}
	
	/**
	 * Specifies the actions that should be executed when the key indicated by KeyCode code is pressed.
	 * @param code - value indicating a particular key on the keyboard
	 */
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
			List<Integer> toldTurtleIds = turtleControl.getToldTurtleIds();
			for(int i = 0; i < toldTurtleIds.size(); i++) {
				if(turtleControl.isPenDown(toldTurtleIds.get(i)) == 1)
					turtleControl.setPenUp(toldTurtleIds.get(i));
				else
					turtleControl.setPenDown(toldTurtleIds.get(i));
			}
        }
	}
}
