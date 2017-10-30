package backend;

import backend.error_handling.SLogoException;
import utilities.PeekingIterator;

import java.util.Arrays;

public class Debugger {

    /*public void executeCommand(String command) throws SLogoException {
        String formattedCommand = command.replaceAll(DELIMITER_REGEX, STANDARD_DELIMITER).trim();
        if (!syntaxTrees.containsKey(formattedCommand)) { // in case method is called without validation
            syntaxTrees.put(formattedCommand, constructSyntaxTree(
                    new PeekingIterator<String>(Arrays.asList(formattedCommand.split(DELIMITER_REGEX)).iterator())));
        }
        SyntaxNode tree = syntaxTrees.get(formattedCommand);
        tree.execute();
    }*/
}
