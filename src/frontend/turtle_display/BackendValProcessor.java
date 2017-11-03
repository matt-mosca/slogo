package frontend.turtle_display;

/**
 * BackendValProcessor.java
 * @author Matthew Mosca
 * Processes the values communicated from the backend so that they can be more easily incorporated on the
 * frontend.
 * @version 11.03.17
 */
public class BackendValProcessor {
	/**
     * Translates the x-coordinate relative to the center (input from backend) into the x-coordinate
     * on the scene
     * @param coordFromCenter
     * @return x-coordinate along the field
     */
    public static double translateXCoord(double turtleFieldCenterX, double xCoordFromCenter) {
    	return turtleFieldCenterX + xCoordFromCenter;
    }
    
    /**
     * Translates the y-coordinate relative to the center (input from backend) into the y-coordinate
     * on the scene
     * @param coordFromCenter
     * @return x-coordinate along the field
     */
    public static double translateYCoord(double turtleFieldCenterY, double yCoordFromCenter) {
    	return turtleFieldCenterY - yCoordFromCenter;
    }
    
    /**
     * Translates the angle as interpreted in the backend into terms appropriate for the conventions of 
     * the front end. Specifically, in the backend, the angle is measured counterclokwise from the x-axis,
     * while in the frontend, due to the design of the ImageView, the angle is measured clockwise from the
     * positive y-axis.
     * @param originalAngle - the angle sent to the frontend from the backend
     * @return the processed angle that can be used in the frontend
     */
    public static double processAngle(double originalAngle) {
    	return (90 - originalAngle) % 360;
    }
}
