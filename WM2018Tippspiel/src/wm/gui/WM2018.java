package wm.gui;



// Startklasse, hier sind jetzt Sie gefragt

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import wm.controller.WMHandler;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */

public class WM2018 extends Application
{	
	private Stage		primaryStage;
	private AnchorPane	rootLayout;
	private FXML_Loader loader;
	
	@Override
	public void start (Stage primaryStage)
	{
	    loader = new FXML_Loader();
	    primaryStage.getIcons().add(new Image("file:bilder/icon.png"));
		primaryStage.setTitle("WM2018 Russland Tippspiel");
		primaryStage.setResizable(false);
		setPrimaryStage(primaryStage);
		setRootLayout(rootLayout);
		loader.initRootLayout(this);
		loader.showWM2018(this);
	}
	
	/**
	 * ÷ffne ein neues Fenster mit Hilfe von FXML_Loader.openNewWindow().
	 * @param mainapp - Start Mainapp
	 * @param fxml - Pfad zum .fxml Dokument
	 * @param title - Titel des neuen Fensters
	 */
	public void openNewWindow(WM2018 mainapp, String fxml,String title) {
		loader = new FXML_Loader();
		Stage stage = new Stage();
		stage.getIcons().add(new Image("file:bilder/icon.png"));
		stage.setTitle(title);
		stage.setResizable(false);
		setPrimaryStage(stage);
		loader.openNewWindow(this, fxml);	
	}
	
	/**
	 * Schlieﬂt die Datenbankverbindung, wenn das Hauptfenster geschlossen wird.
	 */
	@Override
    public void stop() {
		if(WMHandler.getConnector() != null) {
			WMHandler.getConnector().close();
		}
       
    }
	
	
	
	public AnchorPane getRootLayout ()
	{
		return rootLayout;
	}
	
	public void setRootLayout (AnchorPane rootLayout)
	{
		this.rootLayout=rootLayout;
	}
	
	public Stage getPrimaryStage ()
	{
		return primaryStage;
	}
	
	public void setPrimaryStage (Stage primaryStage)
	{
		this.primaryStage=primaryStage;
	}	
	
	public static void losGehts ()
	{
		launch();
	}
}
