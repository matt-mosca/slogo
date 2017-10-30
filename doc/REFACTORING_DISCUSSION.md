# SLOGO Discussion Refactoring

### Refactoring

* IDEWindow
    * The main changes to IDEWindow was refactoring duplicate code into helper methods. 
    We created two methods to format the vboxes and hboxes. We also created a method to format
    the ScrollPanes. Additionally, we refactored the code to change how the variables are stores and displayed.
    This aided the functionality for the variables.
* BackEndValProcessor
    * It processes angle values and position values in the backend to help with the frontend display functionality.
* TurtleFactory
    * Rename to turtle controller: more accurately describes the purpose of the class since it manipulates the turtle's properties in addition to generating them
    * Refactor out turtle movement behavior into separate class: delegate this specific purpose, which is generally outside the scope of "management," to another class 
    * Refactor out turtle rotation behavior into separate class: delegate this specific purpose, which is generally outside the scope of "management," to another class 
    * Refactor wrapping methods (and fix a bug in the process) and move to turtle movement class: make the code that handles wrapping more clear