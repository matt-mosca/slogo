package test;

import backend.Parser;
import deprecated_commands.turtle.TurtleListener;

import java.util.Scanner;

public class ParserTesting {

	
	public static void main(String[] args) {
		Parser parser = new Parser();
		// Simulate call by the front end
		TurtleListener.initializeSingleton(null);
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String command = in.nextLine();
			if (parser.validateCommand(command)) {
				parser.executeCommand(command);	
			} else {
				System.out.println("Failed to parse command!");
			}
		}
	}
	
}
