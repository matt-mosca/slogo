package backend.control;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

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

	// TODO - Refactor into helper methods
	public void loadWorkspaceFromFile(ScopedStorage storage, String fileName) throws WorkspaceFileNotFoundException {
		File workspaceFile = new File(fileName);
		if (!workspaceFile.exists()) {
			throw new WorkspaceFileNotFoundException();
		}
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(workspaceFile);
			doc.getDocumentElement().normalize();
			loadFunctionsFromDocument(storage, doc);
		} catch (ParserConfigurationException | IOException | SAXException e) {
			e.printStackTrace();
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

	private void loadFunctionsFromDocument(ScopedStorage storage, Document doc) {
		NodeList functionNodesList = doc.getElementsByTagName(FUNCTION);
		for (int functionNum = 0; functionNum < functionNodesList.getLength(); functionNum++) {
			Node functionNode = functionNodesList.item(functionNum);
			if (functionNode.getNodeType() == Node.ELEMENT_NODE) {
				Element functionElement = (Element) functionNode;
				String functionName = functionElement.getAttribute(FUNCTION_NAME);
				loadParamsForFunction(storage, functionName, functionElement);
				loadVariablesForFunction(storage, functionName, functionElement);
			}
		}
	}

	private void loadParamsForFunction(ScopedStorage storage, String functionName, Element functionElement) {
		// Load params for function
		NodeList paramNodesList = functionElement.getElementsByTagName(FUNCTION_PARAM);
		List<String> paramsList = new ArrayList<>();
		for (int paramNum = 0; paramNum < paramNodesList.getLength(); paramNum++) {
			Node paramNode = paramNodesList.item(paramNum);
			if (paramNode.getNodeType() == Node.ELEMENT_NODE) {
				Element paramElement = (Element) paramNode;
				String paramName = paramElement.getTextContent();
				paramsList.add(paramName);
			}
		}
		storage.addFunctionParameterNames(functionName, paramsList);
	}

	private void loadVariablesForFunction(ScopedStorage storage, String functionName, Element functionElement) {
		// Load variables for function
		NodeList variablesList = functionElement.getElementsByTagName(VARIABLE);
		storage.enterScope(functionName);
		for (int varNum = 0; varNum < variablesList.getLength(); varNum++) {
			Node varNode = variablesList.item(varNum);
			if (varNode.getNodeType() == Node.ELEMENT_NODE) {
				Element varElement = (Element) varNode;
				String varName = varElement.getAttribute(VARIABLE_NAME);
				double varValue = Double.parseDouble(varElement.getAttribute(VARIABLE_VALUE));
				storage.setVariable(varName, varValue);
			}
		}
		storage.exitScope();
	}

	public static void main(String argv[]) {
		ScopedStorage testSave = new ScopedStorage();
		WorkspaceManager testManager = new WorkspaceManager();
		String[] funcParamNames = new String[] { "x", "y", "z" };
		List<String> funcParamNamesList = (List<String>) Arrays.asList(funcParamNames);
		testSave.addFunctionParameterNames("booya", funcParamNamesList);
		testSave.enterScope("booya");
		testSave.setVariable("a", 7.0);
		testSave.setVariable("x", 3.0);
		testSave.setVariable("y", 5.0);
		testManager.saveWorkspaceToFile(testSave, "test.xml");
		ScopedStorage testLoad = new ScopedStorage();
		try {
			testManager.loadWorkspaceFromFile(testLoad, "test.xml");
			Map<String, Map<String, Double>> functionsInfo = testLoad.getFunctionInfo();
			System.out.println("Functions loaded: ");
			for (String funcName : functionsInfo.keySet()) {
				System.out.print(funcName + "; ");
			}
			System.out.println();
			for (String funcName : functionsInfo.keySet()) {
				List<String> functionParams = testLoad.getFunctionParameters(funcName);
				if (functionParams != null) {
					System.out.println("Parameters for function");
					for (String funcParam : functionParams) {
						System.out.print(funcParam + ";");
					}
					System.out.println();
				}
				Map<String, Double> funcVariables = functionsInfo.get(funcName);
				if (funcVariables != null) {
					System.out.println("Variables for function");
					for (String varName : funcVariables.keySet()) {
						System.out.print(varName + " : " + funcVariables.get(varName));
					}
					System.out.println();
				}
			}
		} catch (WorkspaceFileNotFoundException e) {
			System.out.println("File not found");
		}
	}

}
