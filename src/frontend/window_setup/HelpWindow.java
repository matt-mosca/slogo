package frontend.window_setup;

import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class HelpWindow {
	public static final int STANDARD_TEXT_SIZE = 12;
	public static final int LARGE_TEXT_SIZE = 20;
	public static final int WRAPPING_WIDTH = 450;
	public static final int SCENE_WIDTH = 500;
	public static final int SCENE_HEIGHT = 200;
	
	private Stage helpStage;
	public HelpWindow() {
		helpStage = new Stage();
	}
	public void help() {
		VBox vbox = new VBox();
		ScrollPane scroller = new ScrollPane();
		scroller.setContent(vbox);
		Scene scene = new Scene(scroller, SCENE_WIDTH, SCENE_HEIGHT, Color.WHITE);
		helpStage.setTitle("Help");
		helpStage.setScene(scene);
		helpStage.show();
		
		Text description = new Text("Help/Commands");
		description.setFont(new Font(LARGE_TEXT_SIZE));
		Text t = assembleDisplayedText();
		t.setFont(new Font(STANDARD_TEXT_SIZE));
		t.setWrappingWidth(WRAPPING_WIDTH);
		t.setTextAlignment(TextAlignment.JUSTIFY);
		t.setX(30);
		
		vbox.getChildren().add(description);
		vbox.getChildren().add(t);
	}
	private Text assembleDisplayedText() {
		Text textToDisplay = new Text();
		String text = new String();
		StringBuilder textBuilder = new StringBuilder();
		textBuilder.append("\nHelp : \n");
		textBuilder.append("The user can control the turtle and pen in several ways. \n");
		textBuilder.append("1. The user can enter commands in Logo. The commands will be displayed in the"
				+ " \'Command History\' area on the right side of the window. \n");
		textBuilder.append("2. The user can use buttons provided in the display to control the turtle and pen. "
				+ "The user can click these buttons or move between them with the arrow keys and press "
				+ "them with \'Space.\' \n");
		textBuilder.append("3. When a text box is not selected, the user can use WASD to control the "
				+ "movement of the turtle, and \'Space\' to pick the pen up or put it down. \n \n");
		textBuilder.append("Commands: \n");
		textBuilder.append("FORWARD pixels (FD pixels) - move forward pixels distance \n"
				+ "BACK pixels (BK pixels) - move back pixels distance \n"
				+ "LEFT degrees- (LT degrees) - rotate clockwise by degrees angle\n"
				+ "RIGHT degrees (RT degrees) - rotate counterclockwise by degrees angle \n");
		textBuilder.append("SETHEADING degrees (SETH degrees) \nTOWARDS x y \nSETXY x y (GOTO x y) \n"
				+ "PENDOWN (PD) \nPENUP (PU)\nSHOWTURTLE (ST)\nHIDETURTLE (HT)\nHOME\nCLEARSCREEN\n");
		textBuilder.append("XCOR\nYCOR\nPENDOWN? (PENDOWNP)\nSHOWING? (SHOWINGP)\n");
		textBuilder.append("SUM\nDIFFERENCE\nPRODUCT\nQUOTIENT\nREMAINDER\nMINUS\nRANDOM\n"
				+ "SIN\nCOS\nTAN\nATAN\nLOG\nPOW\nPI\n");
		textBuilder.append("LESS? (LESSP)\nGREATER? (GREATERP)\nEQUAL? (EQUALP)\n"
				+ "NOTEQUAL? (NOTEQUALP)\nAND\nOR\nNOT\n");
		textBuilder.append("MAKE (SET)\nREPEAT\nDOTIMES\nFOR\nIF\nIFELSE\nTO\n");
		textBuilder.append("SETBACKGROUND index (SETBG index)\nSETPENCOLOR index (SETPC index)\n"
				+ "SETPENSIZE pixels (SETPS pixels)\nSETSHAPE index (SETSH index)\n"
				+ "SETPALETTE index r b g\nPENCOLOR (PC)\nSHAPE (SH)\n");
		textBuilder.append("ID\nTURTLES\nTELL [ turtle(s) ]\nASK [ turtle(s) ]\n"
				+ "ASKWITH [ condition ] [ command ]\n");
		text = new String(textBuilder);
		textToDisplay.setText(text);
		return textToDisplay;
	}
}
