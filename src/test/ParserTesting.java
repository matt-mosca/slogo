package test;

import backend.Parser;

public class ParserTesting {

	
	public static void main(String[] args) {
		Parser parser = new Parser();
		String command = "SUM 2 SUM 3   SUM SIN PI 4";
		parser.validateCommand(command);		
		parser.executeCommand(command);
	}
	
}
