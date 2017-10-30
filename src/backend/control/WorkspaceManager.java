package backend.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import backend.Parser;
import backend.error_handling.InvalidSessionLoadedException;
import backend.error_handling.SLogoException;
import backend.error_handling.WorkspaceFileNotFoundException;

import java.io.FileNotFoundException;

public class WorkspaceManager {
	
	public static final String ENCODING = "UTF-8";
	public static final String EOF_REGEX = "\\Z";
	
	public WorkspaceManager() {
	}

	
	public void saveWorkspaceToFile(Parser parser, ScopedStorage storage, String fileName) {
		Set<String> sessionCommands = parser.getSessionCommands();
		try {
			PrintWriter printWriter = new PrintWriter(fileName, ENCODING);			
			for (String sessionCommand : sessionCommands) {
				printWriter.println(sessionCommand);
			}
			printWriter.close();
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			// Should not happen
		}
	}

	public void loadWorkspaceFromFile(Parser parser, ScopedStorage storage, String fileName) throws SLogoException {
		try {
			File workspaceFile = new File(fileName);
			Scanner workspaceFileScanner = new Scanner(workspaceFile);	
			workspaceFileScanner.useDelimiter(EOF_REGEX);
			String sessionText = workspaceFileScanner.next();
			if (!parser.validateCommand(sessionText)) {
				workspaceFileScanner.close();
				throw new InvalidSessionLoadedException();
			}
			workspaceFileScanner.close();
		} catch (FileNotFoundException e) {
			throw new WorkspaceFileNotFoundException();
		}
	}

}
