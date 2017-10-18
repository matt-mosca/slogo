package test;

import java.util.Scanner;

import backend.Parser;

public class ParserTesting {

	
	public static void main(String[] args) {
		Parser parser = new Parser();
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
