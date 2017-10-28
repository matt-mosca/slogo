package backend.control;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import backend.error_handling.WorkspaceFileNotFoundException;

public class WorkspaceManager {
	// Avoid properties file for this since these files are not user-facing?
	public static final String ROOT_TAG_NAME = "workspace";
	public static final String FUNCTION = "function";
	public static final String FUNCTION_NAME = "functionName";
	public static final String FUNCTION_PARAM = "functionParam";
	public static final String VARIABLE = "variable";
	public static final String VARIABLE_NAME = "variableName";
	public static final String VARIABLE_VALUE = "variableValue";

	public WorkspaceManager() {
		// TODO Auto-generated constructor stub
	}

	public void saveWorkspaceToFile(ScopedStorage storage, String fileName) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			// root element
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement(ROOT_TAG_NAME);
			doc.appendChild(rootElement);
			// functions
			Map<String, Map<String, Double>> functionsInfo = storage.getFunctionInfo();
			for (String funcName : functionsInfo.keySet()) {
				List<String> funcParams = storage.getFunctionParameters(funcName);
				Map<String, Double> funcVars = functionsInfo.get(funcName);
				rootElement.appendChild(appendFunctionElementToRootTag(funcName, funcParams, funcVars, doc));
			}
			writeDocumentToXMLFile(fileName, doc);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}
	}
	
	public void loadWorkspaceFromFile(ScopedStorage storage, String fileName) throws WorkspaceFileNotFoundException {
		File workspaceFile = new File(fileName);
		if (!workspaceFile.exists()) {
			throw new WorkspaceFileNotFoundException();
		}
	}

	private void writeDocumentToXMLFile(String fileName, Document doc) {
		// write the content into xml file
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(fileName));
			// Output to console for testing
			// StreamResult result = new StreamResult(System.out);
			transformer.transform(source, result);
			System.out.println("File saved!");
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}
	}

	private Element appendFunctionElementToRootTag(String funcName, List<String> funcParams,
			Map<String, Double> funcVariables, Document doc) {
		Element funcElement = doc.createElement(FUNCTION);
		// rootElement.appendChild(function);
		// set name attribute of function
		funcElement.setAttribute(FUNCTION_NAME, funcName);
		appendParamTagsToFunctionTag(funcElement, funcParams, doc);
		// variables
		appendVarTagsToFunctionTag(funcElement, funcVariables, doc);
		return funcElement;
	}

	private void appendParamTagsToFunctionTag(Element functionElement, List<String> funcParams, Document doc) {
		if (funcParams != null) {
			for (String funcParam : funcParams) {
				Element paramTag = doc.createElement(FUNCTION_PARAM);
				paramTag.appendChild(doc.createTextNode(funcParam));
				functionElement.appendChild(paramTag);
			}
		}
	}

	private void appendVarTagsToFunctionTag(Element functionElement, Map<String, Double> funcVars, Document doc) {
		if (funcVars != null) {
			for (String funcVariable : funcVars.keySet()) {
				Element varTag = doc.createElement(VARIABLE);
				varTag.setAttribute(VARIABLE_NAME, funcVariable);
				varTag.setAttribute(VARIABLE_VALUE, Double.toString(funcVars.get(funcVariable)));
				functionElement.appendChild(varTag);
			}
		}
	}

	public static void main(String argv[]) {
		ScopedStorage testStorage = new ScopedStorage();
		WorkspaceManager testManager = new WorkspaceManager();
		String[] funcParamNames = new String[] { "x", "y", "z" };
		List<String> funcParamNamesList = (List<String>) Arrays.asList(funcParamNames);
		testStorage.addFunctionParameterNames("booya", funcParamNamesList);
		testStorage.enterScope("booya");
		testStorage.setVariable("a", 7.0);
		testStorage.setVariable("x", 3.0);
		testStorage.setVariable("y", 5.0);
		testManager.saveWorkspaceToFile(testStorage, "test.xml");
	}

}
