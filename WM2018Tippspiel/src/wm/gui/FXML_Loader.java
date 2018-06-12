package wm.gui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;

import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */

// Aus Gründen der Programmstrukturierung gibt es eine eigene Klasse für die FXML_Loader
// Hier sind jetzt wieder Sie am Zug

public class FXML_Loader
{
	private static FXMLLoader loader;
	public void initRootLayout (WM2018 mainapp)
	{
		try
		{
			loader=new FXMLLoader();
			loader.setLocation(WM2018.class.getResource("view/RootLayout.fxml"));
			mainapp.setRootLayout((AnchorPane)loader.load());
			
			Scene scene=new Scene(mainapp.getRootLayout());
			mainapp.getPrimaryStage().setScene(scene);
			mainapp.getPrimaryStage().show();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Diese Methode öffnet ein neues Fenster
	 * @param mainapp - Start Mainapp
	 * @param fxml - Pfad zum .fxml Dokument
	 */
	
	public void openNewWindow(WM2018 mainapp, String fxml) {
		loader=new FXMLLoader();
		loader.setLocation(WM2018.class.getResource(fxml));
		try {
			mainapp.setRootLayout((AnchorPane)loader.load());
			Scene scene = new Scene(mainapp.getRootLayout());
			mainapp.getPrimaryStage().setScene(scene);
			mainapp.getPrimaryStage().show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
	

	
	public void showWM2018 (WM2018 mainapp)
	{
		initRootLayout(mainapp);
	}
	
}
