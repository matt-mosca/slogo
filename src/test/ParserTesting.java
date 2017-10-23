package test;

import backend.Parser;
import backend.error_handling.SLogoException;
import deprecated_commands.turtle.TurtleListener;

import java.util.Scanner;

public class ParserTesting {

	
	public static void main(String[] args) {
		
		// TEMP
		Parser parser = new Parser(null);
		// Simulate call by the front end
		TurtleListener.initializeSingleton(null);
		Scanner in = new Scanner(System.in);
		while (in.hasNextLine()) {
			String command = in.nextLine();
			try {
				if (parser.validateCommand(command)) {
					parser.executeCommand(command);
				}
			} catch (SLogoException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	
}
