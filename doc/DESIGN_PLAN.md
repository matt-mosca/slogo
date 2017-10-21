# SLogo Design Plan 

### Introduction

The primary design goal of our SLogo project is to make a flexible integrated development environment (IDE) for the simplified Logo language. We want our project to be sufficiently flexible to accommodate: 

 * New IDE configuration settings, for example changing color scheme 
 * New IDE features, for example syntax highlighting 
 * New view panes, similar to the one hosting the currently available variables
 * New ways of entering commands, such as writing an entire script 
 * New SLogo commands, including new control structures, for example BREAK or CONTINUE
 * New SLogo functionality, such as recursion 
 * New ways for the user to control the turtle, such as Undo and Redo buttons (or Rewind *x* steps)
 * Addition of new (multiple) turtle objects, possibly with different image representations 

The core code that deals with processing of basic Logo command syntax will be closed, since this should not be altered. However, this aspect of the program should be designed flexibly enough so that additional features can be added onto this core of syntax processing. The way in which the turtle’s properties change in response to particular commands will also be closed, since the effects of basic commands on the turtle will not change. The graphical design and interactive aspects of the IDE on the front end will be very open, so that new UI features can be added with ease.

### Design Overview

##### Front end classes:

* Main.java: launches the program 
* Drawer.java: draws lines based on the turtle’s movement
* Line.java: line made by the drawer
* GUIFactory.java: [interface (separate GUI items), front end internal]
  * Creates buttons, text, and text fields, among other UI features of the IDE
* TurtleDisplay.java: pane for displaying the turtle(s) and the path it moves along
* CommandDisplay.java: the dialog pane for entering commands
* Display.java [interface, front end external]
  * Hosts the main panes and side panes (such as the defined variables area), toolbar, buttons, and all other GUI elements

##### Controllers / Listeners: 

* TurtleListener.java: listens for updates to the turtle’s coordinates and tells the frontend to display these updates 
* ErrorListener.java: listens for command parsing errors and tells the frontend to display an error dialog popup (or other conveying mechanism)
* Historian.java: controls undos, redos, and rewinds by obtaining past turtle locations and commands from the HistoryStore

##### Back end classes: 

* Turtle.java [interface, back end internal]
  * holds the turtle’s coordinates and other backend properties (not its image)
* Parser.java [interface, back end external]
  * Implementing classes validate and parse SLogo commands, generate abstract syntax trees with SyntaxNodes
  * BaseSyntaxParser.java / MainParser.java: the parser where raw text is sent 
  * MathParser.java: parses math commands and builds math subtrees (sum, cos, etc.)
  * LogicParser.java: parses control structures (for, if, etc.)
* VariableStore.java: holds stored variables (using a HashMap)
* SyntaxNode.java: node in the abstract syntax tree; stores commands, has left/right children
  * traversed post-order since commands are written in prefix notation
* Command.java [interface, back end internal]
  * Implementing classes hold enums for specific command types and associated logic
  * MathCommand.java: sum, cos, etc.
  * TurtleCommand.java: forward, back, setheading, etc.
  * LogicCommand.java: greater?, equal?, etc.
* ErrorMessage.java: for making command parsing errors
* HistoryStore.java: stores a history of commands and the turtle’s location before the commands, both stored in stacks 
 

![UML Diagram](doc/UML_diagram.png)

##### APIs 

* GUIFactory.java [interface, front end internal]
  * Used to create buttons, text, text fields, etc. and will have methods for each of these
* Display.java [interface, front end external]
 * Has methods that receive information from the back end and incorporate it into the front end representation of the program. These methods will include ones that direct the turtle ImageView to move to a new location or rotate to a new angle, and ones that add created variables and commands to a display pane on the front end.
* Turtle.java [interface, back end internal]
  * Changes the position and angle of the turtle represented on the back end. Changes in these values on the back end will be monitored by a listener, which will communicate to the front end that the position of the turtle should change.
* Parser.java [interface, back end external]
  * Processes input text. First validates the text, to see if valid commands have been input. If the input text does not correspond to a valid command, then it throws an error, which is communicated to the front end and represented to the user. If the text includes valid syntax and commands, these classes and methods process the command and communicate the change that should result either to the representation of the turtle on the back end, and from there to the front end, or directly to the front end in the case of a new variable or command being added.

The GUIFactory interface deals with front end elements that are not changed by code input by the user. These elements remain constant in their appearance and function, and include buttons, text, and text fields, among other UI features of the IDE. The Display interface deals with front end elements that are changed by code input by the user, such as the movement of the turtle, the representation of currently available methods and variables, and the display of error messages. Due to the purpose of the classes and methods in this interface, this component of the program must interact with the external classes and methods of the back end.

The Turtle interface deals with back end internal modifications of the turtle’s properties. The front end does not access this interface directly, instead accessing its indirectly through listeners. The Parser interface deals with much of the syntax reading and processing. This interface decodes information from text input by the user and is external because it must then communicate this information to the Turtle interface and Display interface.

### User Interface

The user will enter commands in a command box (a text field) along the left side; the ability to spread commands across multiple lines will be added by having the user add a ‘\’ at the end of lines as a continuation character (or something similar). In a display box at the center of the window, the Turtle will be displayed as well as its path. The right side of the window will contain a box for displaying variables and user commands and a box for displaying previous commands and errors. The top part of the window will have buttons to change the background color, change the pen color, and change the Turtle’s image. The bottom of the window will have buttons to call up the help window and change the language. The main error that is reported to the user is bad input command.

![User Interface Drawing](doc/User_Interface.JPG)

### API Details 

##### Display.java interface (front end external)

The front end external API supports 3 use-cases:
1. Move the image of the Turtle
2. Rotating the image of the Turtle
3. Adding a variable or command

The TurtleDisplay class handles  use-cases 1 and 2 above, displaying the changes in the Turtle’s orientation and position prompted by the commands entered by the user. Our team decided that a Turtle’s movement should be separate from its rotation because the movement requires the Turtle’s position whereas the rotation requires its orientation.

The CommandDisplay class handles the use-case 3 about, displaying any new commands or variables the user wants to use or create.

As evident from the description above, these two classes are separated because they will be in charge of displaying distinct aspects of project.

This API can be easily extended for support of additional displays without requiring any modifications to the existing API.

TurtleDisplay interface:

```java
/**
* The pane of the display that holds the turtles and the paths they trace. Implementing class(es) will hold a
* collection of ImageViews corresponding to the turtle objects in the backend.
*/
public interface TurtleDisplay {

   /**
    * Move a turtle's image to a new location within the pane.
    *
    * @param turtleIndex - identifies which turtle within the turtle collection to move
    * @param xCoordinate - the new x-coordinate of the turtle
    * @param yCoordinate - the new y-coordinate of the turtle
    */
   void move(int turtleIndex, double xCoordinate, double yCoordinate);

   /**
    * Change the direction to which the turtle's image points. Since coordinates are handled in the backend, this
    * direction only affects visualization (that is, the turtle only move in the direction it points to because of the
    * angle as stored in the backend).
    *
    * @param turtleIndex - identifies which turtle within the turtle collection to rotate
    * @param angle - the direction the turtle's image should point toward
    */
   void rotate(int turtleIndex, double angle);
}
```

CommandDisplay interface:


```java
/**
* The part of the display that shows previously entered commands (including variable-setting commmands).
*/
public interface CommandDisplay {

   /**
    * Add a line to the pane representing a validated command (non variable-setting).
    *
    * @param command - the command as entered by the user
    */
   void addCommand(String command);

   /**
    * Add a line to the pane representing a validated variable-setting command.
    *
    * @param variableName - the name of the variable
    * @param value - the variable's value as a string
    */
   void addVariable(String variableName, String value);
}
```

##### Frontend Internal

The front end internal API supports 4 use-cases:
1. Draw the path the Turtle has taken
2. Create GUI items to be assigned properties and interacted with on the display windows
3. Print command to console
4. Print error to console

The Drawer class handles use-case 1, creating the Turtle’s path to be displayed. 

The GUIFactory class handles use-case 2. The GUIFactory will create the GUI Item needed in the display class. The interface will extendable to specific GUI items such as Buttons, Text fields, etc. as needed.

The Console class handles use-cases 3 and 4.  The console contain the user’s history of commands enacted or if an error occurred. Our team decided to keep the commands separate from the errors because if a command is incorrect then the error will be printed in lieu.

This API can be easily extended for support of additional GUI items without requiring any modifications to the existing API. This API also can support any additional output that may need to be displayed on the console.

Drawer interface:

```java

/**
 * Draws based on data received on back end to be
 * displayed in front end
 */
public interface Drawer {
	
	/**
	 * Draws Turtle's path based on old position and new position
	 * 
	 * @param newXCoordinate - This is the X coordinate where the Turtle will travel
	 * @param newYCoordinate - This is the Y coordinate where the Turtle will travel
	 * @param oldXCoordinate - This is the X coordinate where the Turtle was
	 * @param oldYCoordinate - This is the Y coordinate where the Turtle was
	 */
	void drawLine(double newXCoordinate, double newYCoordinate, double oldXCoordinate, double oldYCoordinate);

}
```

GUIFactory interface: 

```java
import javafx.event.ActionEvent;

/**
 * Creates GUI items to be used in display class. Extending 
 * the interface will be used to create specific items
 * such as buttons, text fields, menu items, and etc
 */

public interface GUIFactory{
	
	/**
	 * This method will create a GUI item that will assign it 
	 * an action event (handle) and a label (name). This item will
	 * be stored in a Group (root).
	 * @param handle - This is the action event that the GUI item will enact when interacting
	 * @param root - This is the group that the GUI item will be stored in
	 * @param name - This is the string that will be the GUI item’s label
	 */
	void makeGUIItem (ActionEvent handle, Group root, String name);
}
```


Console interface:

```java
/**
 * This interface will dictate what is viewed in the console
 */
public interface Console {
	
	/**
	 * This method will print to console what command the
	 * user entered
	 * @param command - This is the string name for the command that the user entered
	 */
	void appendCommandToConsole(String command);
	
	/**
	 * This method will print to console what error the
	 * user has caused as given by the back end
	 * @param errorMsg - This is the string message of what error the user encountered
	 */
	void appendErrorToConsole(String errorMsg);
}
```

##### Backend External

The backend external API supports 5 main use-cases: 
1. Validation of commands
2. Execution of valid commands, throwing exceptions upon invalid input
3. Retrieval of previously saved variables and commands
4. Querying of all valid commands
5. Querying session's command history (not mere strings, but parsed commands)

The BaseParser class handles use-cases 1) and 2) above, providing the front-end with an easy API for both querying the validity of a given string input from the user, and for execution of the command once validated. As explained above, the contract between the caller of the execution method, 2), and the API, is that the passed input has already been validated. Thus this method throws an exception when the input cannot be resolved into a (sequence of) command(s).

There are two justifications for splitting the validation of commands, 1), from the execution of commands, 2) :

1. The use-case of saving commands for future execution requires validation upon entry without immediate execution. Thus the API has to expose the functionality of validation without execution.
 
2. From a general modularity perspective, the validation of an input string as a command is distinct from the execution of the command. Error-handling upon flagging of invalid commands (i.e. when the validate method returns false) can then be handled by the front end as it deems fit, and this is abstracted away from the back end.

The VariableStore class handles use-cases 3) above, providing the front end with an API to fetch of all variable/command-value key-value pairs,for display of user-defined variables and commands in a separate panel.

The Command class handles use-cases 4) and 5), returning the set of all valid commands natively (for instance, through the CommandType enum class's getEnumConstants() method) and storing a log of commands in sequence.

As is evident from the above description, these classes are divided along the distinct use-cases of command validation / processing (BaseParser), holding of user-defined variables / commands and flagging of non-existent variables / commands (VariableStore), and retrieval of all valid (non-user defined) commands. This enforces clear abstraction boundaries and facilitates modularity as well as testability.

This API can be easily extended for support of additional functionality such as querying of valid commands matching a prefix substring. For instance, methods such as 
public List<String> getMatchingCommands(String prefix)
can be added. These will not require any modifications to the existing API.

However, the most important additional functionality, such as validation / execution of new commands, is supported through extension of the internal, rather than external, API. This is a benefit of our design, as the overhead on users of our API is minimized - they are unlikely to have to re-read the API to understand new commands. Encapsulation is thus maximized.

Parser interface: 

```java
/**
* Validates and parses the commands the user enters in the frontend, building a syntax tree from the parsed commands.
*/
public interface Parser {

   /**
    * Ensures that an entered command is valid in the SLogo language.
    *
    * @param commandString - the raw command as entered by the user in the frontend
    * @return true if the command is valid and false otherwise
    */
   boolean validate(String commandString);

   /**
    * Builds an abstract syntax tree from a validated command, to be traversed in post-order since SLogo commands are
    * in prefix order.
    *
    * @param commandString - the raw, validated command entered by the user
    * @throws IllegalArgumentException - thrown if an unvalidated command is passed
    */
   void parseCommand(String commandString) throws IllegalArgumentException;
}
```

VariableStore interface (external part):

```java
import java.util.Map.Entry;
import java.util.List;

/**
* Stores the variables declared by the user in the frontend using a HashMap.
*/
public interface VariableStore {

   /**
    * Get the currently stored variables.
    *
    * @return a list of the valid variables the user has stored up to this point
    */
   List<Entry> getVariables();
}
```

##### Backend Internal API

The backend internal API supports 4 main use-cases:

1. Decompose and delegate the parsing & execution logic of commands by type
2. Manipulate Turtle Model based on commands
3. Allow parser to save validated user-defined commands and variables
4. Allow parser to fetch the value of a user-defined command or variable if it exists

The MathParser, TurtleCommandParser and LogicParser handle use-case 1), decomposing the complex logic of parsing and execution into parts based on the types of the sub-commands within the input string. While it is expected that these class's functions may require calls to each other's functions (due to the recursive nature of parsing), the logic of each function is encapsulated in the respective classes.

The Turtle class handles use-case 2), allowing the back end to update the Turtle Model based on the deciphered command. This model has bindings to the front end's turtle representation through changeListeners on the x, y coordinates and angles which are basically the front end's external API methods. For instance, the parser could call Turtle.moveVertically(50.0), which is handled by the Turtle class by incrementing the Turtle model's y-coordinate by 50.0. Since the turtle class has registered the external front end API method move(0.0, 50.0) as a changeListerner on its doubleProperty y, this will result in the turtle image moving vertically. This set-up allows the back end to be isolated and abstracted from the means of representation of the turtle (e.g. whether it is an ImageView, Shape, etc) while still allowing it to be the source of truth for data and calculations.

The VariableStore class handles use-cases 3) and 4) above, providing the parser with an API to save variables and commands (after validation). Valid commands and variables will be saved within this class. It also allows querying whether a supposed user-defined variable / command exists. Finally, it also supports fetching of an individual variable / command's value, for evaluation by the parser. While the parser is expected to only call getVariableValue(String varName) after checking that it exists, by calling variableOrCommandExists(String varName) in validateCommand(String varName), inadvertently attempting to fetch the value of a non-existent variable is handled by throwing an exception. This exception, thrown by the VariableStore upon attempting to fetch a non-existent variable, will be propagated to the front end through executeCommand, and subsequently handled however the front end deems fit (most likely by printing an error message to the console using the ErrorHandler class).

On the whole, the benefit of the back end internal API is the preservation of the MVC architecture. It allows changes in state to flow from the back end to the front end by allowing the deciphered commands to manipulate back end state without having to directly manipulate front end representations. The bindings, through the front end external API, serve as the glue between the back end and the front end.


Turtle interface: 

```java
/**
* Contains the backend representation of the displayed turtles. Handles updates of turtle's positions and heading.
*/
public interface Turtle {

   /**
    * Move in the direction the turtle currently points towards.
    *
    * @param amount - the magnitude by which the turtle moves
    */
   void move(double amount); // package private

   /**
    * Rotate the direction the turtle points  clockwise or counterclockwise.
    *
    * @param angle - positive (clockwise) or negative (counterclockwise) magnitude by which the turtle's heading is to
    *              be changed
    */
   void rotate(double angle); // package private

   /**
    * Rotate the turtle to an absolute heading (that is, regardless of its current heading).
    *
    * @param heading - the new direction to point the turtle towards
    */
   void setHeading(double heading); // package private

   /**
    * Points the turtle to face the point (x,y) where (0, 0) is the center of the screen
    *
    * @param x - the x-coordinate towards which the turtle should face
    * @param y - the y-coordinate towards which the turtle should face
    * @return the number of degrees turtle turned
    */
   double faceTowards(double x, double y); // package private

   /**
    * Move the turtle to a new absolute postion
    * @param x - the new x-coordinate for the turtle
    * @param y - the new x-coordinate for the turtle
    * @return the distance the turtle moved
    */
   double goTo(double x, double y); // package private
}
```

Command interface: 

```java
import java.util.Collection;

/**
* Object used to represent various SLogo commands. Implementing classes will have the valid commands of their types
* stored as enums or collections and will represent one of these commands at construction time.
*/
public interface Command {

   /**
    * Retrieve all the valid commands of a category of command.
    *
    * @return a collection of all the valid commands within a command category
    */
   Collection getAllValidCommands();

   /**
    * Execute the command the object represents, as passed at construction time.
    */
   void execute();
}
```

VariableStore interface (internal part):

```java
/**
* Stores the variables declared by the user in the frontend using a HashMap.
*/
public interface VariableStore {

   /**
    * Get the currently stored variables.
    *
    * @return a list of the valid variables the user has stored up to this point
    */
   List<Entry> getVariables();

   /**
    * Add a new validated variable.
    *
    * @param variableMapEntry - the new variable to add to the map of all currently available variables
    */
   void saveVariable(Entry variableMapEntry);

   /**
    * Check if a variable exists in the current environment.
    *
    * @param variable - the name of the variable referenced by the user in the command area
    * @return true if the variable is a key in the variables map
    */
   boolean existsVariable(String variable);

   /**
    * Get the value of a variable referenced by the user.
    *
    * @param variable - the name of the referenced variable
    * @return the value of the variable in the variable map
    * @throws IllegalArgumentException - thrown if the variable is not in the variable map
    */
   double getVariableValue(String variable) throws IllegalArgumentException;
}
```

HistoryStore interface: 

```java
/**
* Stores stacks of entered commands as well as the turtles' position and heading prior to those commands.
*/
public interface HistoryStore {

   /**
    * Add a new command to the command stack. Should be added at the same time as the turtle's position before the
    * command is executed.
    *
    * @param lastCommand - the last entered command after parsing
    */
   void addCommand(String lastCommand); // package private

   /**
    * Add the last position and heading of the turtle before the last executed command.
    *
    * @param lastPosition - the last (x,y) position of the turtle
    * @param lastHeading - the last heading (direction pointed to) of the turtle
    */
   void addTurtlePosition(Point2D lastPosition, double lastHeading); // package private

   /**
    * Get the last executed command from the commands stack.
    *
    * @return the raw string reprentation of the last command
    */
   String getLastCommand() throws EmptyStackException; // package private

   /**
    * Get the last turtle position before the last executed command from the position stack.
    *
    * @return the last (x,y) position of the turtle
    */
   Point2D getLastTurtlePosition() throws EmptyStackException; // package private

   /**
    * Get the last turtle heading before the last executed command from the heading stack.
    *
    * @return the last heading of the turtle
    */
   double getLastTurtleHeading() throws EmptyStackException; // package private
}
```

ErrorMessage interface:

```java
/**
* Stores the last generated error message. There will be an event listener on setMessage().
*/
public interface ErrorMessage {

   /**
    * Set the current error message when bad commands are provided.
    *
    * @param message - an error message generated on account of a syntax error
    */
   void setMessage(String message); // package private

   /**
    * Get the current error message.
    *
    * @return the last generated error message (empty string if none was ever generated)
    */
  String getMessage();
}
```

#### API Example Code

* Processing of 'fd 50'

Display.processInput('fd 50') 
-> BaseParser.validateCommand('fd 50') => (Returns true)
-> BaseParser.executeCommand('fd 50')  
-> TurtleCommandParser.executeCommand('fd 50') ->
-> Turtle.moveVertically(50) 
-> Display.move(0, 50)
		-> Command.addCommandToHistory(new TurtleMovementCommand(CommandType.FD, 50))
	-> Console.appendCommandToConsole('fd 50')

(Note: TurtleMovementCommand is likely a sub-class of ExecutableCommand)

##### Adithya

* Saving of new user-defined variable <User enters 'SET x 100'>

Display.processInput('SET x 100')
	-> BaseParser.validateCommand('SET x 100') => (Returns true)
	-> BaseParser.parseCommand('SET x 100')
		-> new SyntaxNode( // each arg ) …
	-> BaseParser.executeCommand()
		-> VariableStore.saveVariable('x', 100)
		-> Command.addCommandToHistory(new UserDefinedVariableCommand(CommandType.SET, 'x', 100))
	-> Console.appendCommandToConsole('SET x 100')

(Note: UserDefinedVariableCommand is likely a sub-class of ExecutableCommand)



* Retrieval of previous user-defined variable <User enters 'SUM x 50'>

Display.processInput('SUM x 50')
	-> BaseParser.validateCommand('SUM x 50')
-> VariableStore.variableOrCommandExists('x') => (Returns true)
-> (Returns true)
-> // sees sum, passes along to math parser
		-> MathParser.parseCommand('SUM x 50') // builds mathRoot of syntax tree
			-> MathParser.executeMath(SyntaxNode mathRoot)
				-> VariableStore.getVariableValue('x') => (Returns 100)
				-> Returns 150
		-> Command.addCommandToHistory(new ArithmeticCommand(CommandType.SUM, 100, 50))
	-> Console.appendCommandToConsole('SUM x 50')

(Note: ArithmeticCommand is likely a sub-class of ExecutableCommand)

##### Ben 

* Handling errors <User enters ‘SUM SUM’>

Display.processInput(‘SUM SUM’) 
	-> BaseParser.validateCommand(‘SUM SUM’) 
		-> returns false (determines there are not enough arguments)
			-> ErrorMessage.setMessage(...) // message implementation TBD
-> ErrorListener.passToFrontEnd() // called by the listener on setMessage()
-> Display.popUp(message)

* Building simple math syntax tree <User enters 'SUM x 50'>

Display.processInput(‘SUM SUM’) 
	-> BaseParser.validateCommand(‘SUM 10 10’) 
		-> returns true
		-> MathParser.parseCommand(‘SUM 10 10’)  // builds mathRoot of syntax tree
		-> SyntaxNode.executeCommand(SyntaxNode left, SyntaxNode right)
			-> MathCommand.execute(double... args)
		-> // result passed back to main parser as whole tree is executed
		
### Design Considerations 

* Single Source of Truth - There should be exactly 1 authoritative source of data from which the information flows. Our design seeks to establish the Model as this single source of truth, even for front end or display related data. To this end, the requirement of showing and updating the turtle's position and orientation is decomposed into the 1) Display and 2) Data of the turtle, with the Turtle class representing the abstraction of the turtle's data without regard for the physical means of display of the turtle. Similarly, the other parts of the Model (loosely defined as the back end data containers) such as VariableStore and Command represent the source of truth for the state of the workspace, providing them for display in the front end through their public API methods.

* Atomicity (of changes to backing data and display) - The above consideration leads to a challenge in preserving atomicity - if the data is decoupled from the display (in separate components), how does one ensure that updates to the Model are transmitted to the front end? This is solved through the use of bindings, or changeListeners, registered onto the properties of the Model. This informed the choice of certain front end external API methods, such as move(double xVal, double yVal), since they had to called from the changeListeners on the back end. This way, modularity is maintained while still maintaining a single source of truth and atomicity.

* Isolation (private data) & Interfaces (public APIs) / Abstraction Boundaries

* To maintain abstraction boundaries between components and minimize 'back-channels' for data-sharing, each component will provide a public interface / API that provides carefully calibrated access to its private data. This interface will abstract away from each component's private data storage so as to facilitate concurrent development by team members. This is evident from the UML diagram displayed in the Overview section. 

Testability / Ease of debugging

* The design seeks to encapsulate as much information as possible in function signatures (through parameters), as opposed to referencing global variables or instance properties. This way, testability (especially unit-testing) and debugging are facilitated.

* Each team-member is expected to unit-test every method of his / her public API before releasing it to the team. This way, end-to-end testing is simplified as faults can be isolated quickly.

Naming - intuitiveness and readability

* Even at a design level, naming of components and public interface methods is a significant consideration. Accurate and descriptive names will support quick look-up of code by functionality and easy addition of new code to the right place. This has spill-over benefits in terms of other design factors such as testability / ease of debugging.

### Team Responsibilities

Matt and Tyler will work on the front end team, and Adithya and Ben will work on the back end team. Matt will work specifically on drawing and manipulating the Turtle image. Tyler will work specifically on GUI inputs and organizing the stage. Both members will work on communications with the back end. Adithya and Ben will both work on abstract syntax parsing. Ben will focus more on mathematical aspects of the backend (e.g. the math commands) while Adithya will focus on the boolean logic and turtle queries/commands.


