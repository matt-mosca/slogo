# cellsociety API Critique (Team 05)

### Cell superclass and subclasses

All public methods/constructors in Cell super class and subclasses are justifiably public.

package backend;

public class Cell { 

public Cell(int state, Color color, List<Cell>neighborCells, int rowNumber, int columnNumber) - external

public int getState() - external

public void changeState(int state) - external

public Paint getColor() - external

public void setColor(Color color) - external

public List<Cell> getNeighborCells() - external

public void setNeighborCells(List<Cell> neighborCells) - internal

public int getRowNumber() - internal

public void setRowNumber(int rowNumber) - internal

public int getColumnNumber() - internal

public void setColumnNumber(int columnNumber) - internal

}
 
package backend;

public class CellFire extends Cell{ 

public CellFire(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) - 
external

public void catchFire() - internal

public void burnDownToEmpty() - internal

}
 
package backend;

public class CellGameOfLife extends Cell { 

public CellGameOfLife(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) - external 

public void live() - internal

public void die() - internal

}
 
package backend;

public class CellRPS extends Cell{ 

public CellRPS(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber, int 
gradientLevel) 

public int getGradientLevel() 

public void setGradientLevel(int gradient) 

}
 
package backend;

public class CellSegregation extends Cell{ 

public CellSegregation(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) - external

}
 
package backend;

public class CellWaTor extends Cell{ 

public CellWaTor(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) - external

public int getStarveDays() - internal

public void setStarveDays() - internal

public int getBreedDays() - internal

public void setBreedDays() - internal

}
 
### Simulation super class and subclasses
 
All public methods/constructors in Simulatin super class and subclasses are justifiably public.
 
package backend;

public abstract class Simulation{ 

public Simulation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double 
redToBlueRatio) - external

public Simulation(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation) - external

public abstract void update(); - external

public Cell[][] getArray() - external

public double getEmptyPercentage() - external

public double getRedToBlueRatio() - external

public int getCellNumberHorizontal() - external

public int getCellNumberVertical() - external

public int getInitialSetting() - external

public void setInitialSetting() - external

public int[]getCellProportion() - external

public void count(int first, int second, int third) - external

}
 
package backend;

public class SimulationFire extends Simulation{ 

public SimulationFire(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, - external

public SimulationFire(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation,double probCatch) - external

public void specificSetUp(double probCatch) - external

public void update() - external

public double getProbCatch() - internal

public void setProbCatch(double prob) - external

}
 
package backend;

public class SimulationGameOfLife extends Simulation { 

public SimulationGameOfLife(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double redToBlueRatio) - external

public SimulationGameOfLife(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation) - external

public void specificSetUp() - external

public void update() - external

}
 
package backend;

public class SimulationRPS extends Simulation { 

public SimulationRPS(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, - external

public SimulationRPS(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation) - external

public void specificSetUp() - external

public void update() - external

}
 
package backend;

public class SimulationSegregation extends Simulation { 

public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, - external

public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation,double SatisfactionPercentage) external

public void specificSetUp(double satisfactionPercentage) - external

public void update() - external

public double getSatisfactionPercentage() - internal

}
 
package backend;

public class SimulationWaTor extends Simulation { 

public SimulationWaTor( int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage,double redToBlueRatio, - external

public SimulationWaTor(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation, int maxStarveDaysForSharks, - external

public void specificSetUp() - external

public void update() - external

public CellWaTor findRandomNeighbor(List<Cell> allNeighbor) - internal

public void sharkMove(CellWaTor shark, CellWaTor randomSlot, String code) - internal

public int getStarveDays() - internal

public int getSharkBreed() - internal

public int getFishBreed() - internal

}
 
package frontend;

public class ErrorMessageDisplay { 

}
 
package frontend;

public  class Graph { 

public Graph(Simulation s, ResourceBundle rb, String name) - internal

public LineChart<Number, Number> createGraph() - internal

public void update() - internal

public LineChart<Number, Number> getGraph() - internal

}
 
package frontend;

public class GridDisplay { 

public GridDisplay(Simulation s) - internal

public void update() - internal

public Pane getGrid() - internal

public void handleClick(double x, double y) - internal

}
 
package frontend;

public class NewSimulation { 

public NewSimulation(Stage s) - internal

}
 
package frontend;

public class SimDisplay { 

public SimDisplay(int x, int y, Stage s, String filename) - external

public String getSimName() - external

public Scene getStartScene() - external

public void playSim() - external

public Timeline getAnimation() - internal

public Simulation getSimulation() - external

public void setSimulation(Simulation s) - external

public Scene getScene() - internal

}
 
package frontend;

public class SimSlider { 

public SimSlider(SimDisplay s) - internal

public GridPane getSliders() - internal

}
 
package frontend;

public class StyleUI { 

public boolean gridVisibility() - external

public String gridShape() - external

public boolean getGridEdge() - external

public Color emptyColor() - external

public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException - external 

}
 
package frontend;

public class UserInput { 

public UserInput() - external

public double[] getArray(String s) - external

public void segregationSetUpWithoutType() - internal

public void waTorSetUpWithoutType() - internal

public void getFire() - internal

public void fireSetUpWithoutType() - internal

public void getGameOfLife() - internal

public void getRPS() - internal

public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException - internal 

public void getInitialSetUp(Document doc, double[] second) - internal

public int getType() - internal

public void setType(int type) - internal

public void showError (String message) - internal

}
 
package frontend;

public class UserSaveSimulation{ 

public int[][] getBack() - external

public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException - external 

public void save(Cell[][]needSave,String type) - external

}
 
public class Main extends Application{ 

public void start(Stage primaryStage) throws Exception - external

}
 
package util;

public class EightNeighborFinder extends NeighborFinder { 

public EightNeighborFinder(Cell[][] cells, int xPos, int yPos, boolean toroidal) - external

public List<Cell> findNeighbors() - external

}
 
package util;

public class FourNeighborFinder extends NeighborFinder { 

public FourNeighborFinder(Cell[][] cells, int xPos, int yPos, boolean toroidal) - external

public List<Cell> findNeighbors() - external

}
 
package util;

public abstract class NeighborFinder { 

public NeighborFinder(Cell[][] cells, int xPos, int yPos, boolean tor) - external

public void setMyXPosition(int x) - external

public void setMyYPosition(int y) - external

public List<Cell> getMyNeighbors() - external

public abstract List<Cell> findNeighbors(); - external

}
 
package util;

public class TriangleNeighborFinder extends NeighborFinder { 

public TriangleNeighborFinder(Cell[][] cells, int xPos, int yPos, boolean toroidal) - external

public List<Cell> findNeighbors() - external

} 
