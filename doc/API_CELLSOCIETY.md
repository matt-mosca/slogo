# cellsociety API Critique

package backend;
public class Cell { 
  	public Cell(int state, Color color, List<Cell>neighborCells, int rowNumber, int columnNumber) 
	public int getState() 
	public void changeState(int state) 
	public Paint getColor() 
	public void setColor(Color color) 
	public List<Cell> getNeighborCells() 
	public void setNeighborCells(List<Cell> neighborCells) 
	public int getRowNumber() 
	public void setRowNumber(int rowNumber) 
	public int getColumnNumber() 
	public void setColumnNumber(int columnNumber) 
}
 
package backend;
public class CellFire extends Cell{ 
  	public CellFire(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) 
	public void catchFire() 
	public void burnDownToEmpty() 
}
 
package backend;
public class CellGameOfLife extends Cell { 
  	public CellGameOfLife(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) 
	public void live() 
	public void die() 
}
 
package backend;
public class CellRPS extends Cell{ 
  	public CellRPS(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber, int gradientLevel) 
	public int getGradientLevel() 
	public void setGradientLevel(int gradient) 
}
 
package backend;
public class CellSegregation extends Cell{ 
  	public CellSegregation(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) 
}
 
package backend;
public class CellWaTor extends Cell{ 
  	public CellWaTor(int state, Color color, List<Cell> neighborCells, int rowNumber, int columnNumber) 
	public int getStarveDays() 
	public void setStarveDays(
	public int getBreedDays() 
	public void setBreedDays(
}
 
package backend;
public abstract class Simulation{ 
  	public Simulation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double redToBlueRatio) 
	public Simulation(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation) 
	public abstract void update();
	public Cell[][] getArray() 
	public double getEmptyPercentage() 
	public double getRedToBlueRatio() 
	public int getCellNumberHorizontal() 
	public int getCellNumberVertical() 
	public int getInitialSetting() 
	public void setInitialSetting(
	public int[]getCellProportion()
	public void count(int first, int second, int third) 
}
 
package backend;
public class SimulationFire extends Simulation{ 
  	public SimulationFire(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
	public SimulationFire(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation,double probCatch) 
	public void specificSetUp(double probCatch) 
	public void update() 
	public double getProbCatch() 
	public void setProbCatch(double prob) 
}
 
package backend;
public class SimulationGameOfLife extends Simulation { 
  	public SimulationGameOfLife(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, double redToBlueRatio) 
	public SimulationGameOfLife(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation) 
	public void specificSetUp() 
	public void update() 
}
 
package backend;
public class SimulationRPS extends Simulation { 
  	public SimulationRPS(int cellNumberHorizontal, int  cellNumberVertical, double emptyPercentage, 
	public SimulationRPS(int cellNumberHorizontal, int cellNumberVertical, int[][] specificLocation) 
	public void specificSetUp() 
	public void update() 
}
 
package backend;
public class SimulationSegregation extends Simulation { 
  	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage, 
	public SimulationSegregation(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation,double SatisfactionPercentage) 
	public void specificSetUp(double satisfactionPercentage) 
	public void update() 
	public double getSatisfactionPercentage() 
}
 
package backend;
public class SimulationWaTor extends Simulation { 
  	public SimulationWaTor( int cellNumberHorizontal, int cellNumberVertical, double emptyPercentage,double redToBlueRatio,
	public SimulationWaTor(int cellNumberHorizontal, int cellNumberVertical, int[][]specificLocation, int maxStarveDaysForSharks,
	public void specificSetUp(
	public void update() 
	public CellWaTor findRandomNeighbor(List<Cell> allNeighbor) 
	public void sharkMove(CellWaTor shark, CellWaTor randomSlot, String code) 
	public int getStarveDays() 
	public int getSharkBreed() 
	public int getFishBreed() 
}
 
package frontend;
public class ErrorMessageDisplay { 
  }
 
package frontend;
public  class Graph { 
      public Graph(Simulation s, ResourceBundle rb, String name) 
    public LineChart<Number, Number> createGraph() 
	public void update() 
	public LineChart<Number, Number> getGraph()
}
 
package frontend;
public class GridDisplay { 
  	public GridDisplay(Simulation s) 
	public void update()
	public Pane getGrid() 
	public void handleClick(double x, double y) 
}
 
package frontend;
public class NewSimulation { 
  	public NewSimulation(Stage s)
}
 
package frontend;
public class SimDisplay { 
  	public SimDisplay(int x, int y, Stage s, String filename) 
	public String getSimName() 
	public Scene getStartScene() 
	public void playSim() 
	public Timeline getAnimation() 
	public Simulation getSimulation() 
	public void setSimulation(Simulation s) 
	public Scene getScene() 
}
 
package frontend;
public class SimSlider { 
  	public SimSlider(SimDisplay s) 
	public GridPane getSliders() 
}
 
package frontend;
public class StyleUI { 
  	public boolean gridVisibility() 
	public String gridShape() 
	public boolean getGridEdge() 
	public Color emptyColor() 
	public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException 
}
 
package frontend;
public class UserInput { 
  	  public UserInput() 
	  public double[] getArray(String s) 
	public void segregationSetUpWithoutType() 
	public void waTorSetUpWithoutType() 
	public void getFire() 
	public void fireSetUpWithoutType() 
	  public void getGameOfLife() 
	  public void getRPS() 
	public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException 
	public void getInitialSetUp(Document doc, double[] second) 
	public int getType() 
	public void setType(int type) 
	public void showError (String message) 
}
 
package frontend;
public class UserSaveSimulation{ 
  	public int[][] getBack()
    public Document getFile(String fileName) throws ParserConfigurationException, SAXException, IOException 
	public void save(Cell[][]needSave,String type) 
}
 
public class Main extends Application{ 
  	public void start(Stage primaryStage) throws Exception 
}
 
package util;
public class EightNeighborFinder extends NeighborFinder { 
  	public EightNeighborFinder(Cell[][] cells, int xPos, int yPos, boolean toroidal) 
	public List<Cell> findNeighbors() 
}
 
package util;
public class FourNeighborFinder extends NeighborFinder { 
  	public FourNeighborFinder(Cell[][] cells, int xPos, int yPos, boolean toroidal) 
	public List<Cell> findNeighbors() 
}
 
package util;
public abstract class NeighborFinder { 
  	public NeighborFinder(Cell[][] cells, int xPos, int yPos, boolean tor) 
	public void setMyXPosition(int x) 
	public void setMyYPosition(int y) 
	public List<Cell> getMyNeighbors() 
	public abstract List<Cell> findNeighbors();
}
 
package util;
public class TriangleNeighborFinder extends NeighborFinder { 
  	public TriangleNeighborFinder(Cell[][] cells, int xPos, int yPos, boolean toroidal) 
	public List<Cell> findNeighbors() 
}
 
