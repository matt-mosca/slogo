package frontend.turtle_display;

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
    
    public static double processAngle(double originalAngle) {
    	//In backend, angle is measured counterclockwise from x-axis
    	return (90 - originalAngle) % 360;
    }
}
