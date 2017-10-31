package backend.control;

import backend.Parser;
import backend.error_handling.InvalidSessionLoadedException;
import backend.error_handling.SLogoException;
import backend.error_handling.WorkspaceFileNotFoundException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Set;

public class WorkspaceManager {
	
	public static final String ENCODING = "UTF-8";
	public static final String EOF_REGEX = "\\Z";
	public static final String UNDESIRED_PREFIX = "file:";
	
	public WorkspaceManager() {
	}

	
	public void saveWorkspaceToFile(Parser parser, ScopedStorage storage, String fileName) {
		Set<String> sessionCommands = parser.getSessionCommands();
		if (fileName.startsWith(UNDESIRED_PREFIX)) {
			fileName = fileName.replace(UNDESIRED_PREFIX, "");
		}
		try {
			PrintWriter printWriter = new PrintWriter(fileName, ENCODING);			
			for (String sessionCommand : sessionCommands) {
				printWriter.println(sessionCommand);
			}
			printWriter.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// Should not happen
			e.printStackTrace();
		}
	}

	public void loadWorkspaceFromFile(Parser parser, ScopedStorage storage, String fileName) throws SLogoException {
		try {
			if (fileName.startsWith(UNDESIRED_PREFIX)) {
				fileName = fileName.replace(UNDESIRED_PREFIX, "");
			}
			File workspaceFile = new File(fileName);
			Scanner workspaceFileScanner = new Scanner(workspaceFile);	
			workspaceFileScanner.useDelimiter(EOF_REGEX);
			if (!workspaceFileScanner.hasNext()) {
				// Warn user?
				return;
			}
			String sessionText = workspaceFileScanner.next();
			if (!parser.validateCommand(sessionText)) {
				workspaceFileScanner.close();
				throw new InvalidSessionLoadedException();
			}
			// Execute upon loading? Will lead to side-effects but necessary to resolve 
			// variable definitions with side-effects, e.g. make :x fd 50
			parser.executeCommand(sessionText);
			workspaceFileScanner.close();
		} catch (FileNotFoundException e) {
			throw new WorkspaceFileNotFoundException();
		}
	}

}
