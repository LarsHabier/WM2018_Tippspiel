package wm.gui.view;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */
import javafx.collections.FXCollections;

// Jedes Formular benötigt einen Controller für die in ihm enthaltenen Elemente

import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;

import wm.controller.*;
import wm.gui.WM2018;
import wm.objekte.Configuration;

public class WM2018MainController
{
	private WM2018 mainApp;
	private static WMHandler handler;
	@FXML public ChoiceBox<Object> cb_functions ;
	@FXML public Button select_btn;
	@FXML public TextArea console;
	
	
	@FXML
	private void initialize ()
	{
		
		cb_functions.setItems(FXCollections.observableArrayList("Verbindung zur Datenbank (manuell)","Verbindung zur Datenbank (Live)"));
	    cb_functions.getSelectionModel().selectFirst();
	    handler = new WMHandler();
	    mainApp = new WM2018();
	    WMHandler.setMain_controller(this);
	    console.appendText("WM 2018 Russland Tippspiel \n-----------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
	}
	/**
	 * Diese Methode wertet das ausgewählte Item der ChoiceBox aus.
	 */
    @FXML
    private void select_cb_item() {
    
    	handler.select_cb_item(this,mainApp);
    	
    }
    
    /**
     * Methode, um die config.txt zu lesen. (Zu spät gesehen, dass in Hilfsfunktionen eine Methode bereitgestellt wurde.)
     * @return - Konfiguration für die Live-Verbindung
     */
    public Configuration read_config() {
    	String newData[] = new String[5];
    	try {
			FileReader fr = new FileReader("config.txt");
			BufferedReader br = new BufferedReader(fr);
			String data[] = new String[5];
			for(int i = 0;i<data.length;i++) {
				try {
					data[i] = br.readLine();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			for(int i = 0;i<data.length;i++) {
				String splitter[] = data[i].split(":");
				newData[i] = splitter[1];
			}
			
			
			for(int i = 0;i<newData.length;i++) {
				System.out.println(newData[i]);
			}
	
					
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    	Configuration config = new Configuration(newData);
    	
		return config;
    	
    }
    
    /**
     * Am Anfang kann man nur die Verbindungsarten in der ChoiceBox auswählen. Ist die Verbindugn gelungen, werden die 
     * üblichen Optionen freigeschalten.
     */
    public void enable_choice_box() {
    	cb_functions.setItems(FXCollections.observableArrayList("Verbindung zur Datenbank (manuell)","Verbindung zur Datenbank (Live)",new Separator(),"Tabellen anlegen","Daten einlesen",new Separator(),"Spielplan ausgeben","Ergebnisse eingeben","Ergebnisse ausgeben",new Separator(),"Ranking erstellen","Ranking ausgeben",new Separator(),"Spielpaarungen bearbeiten (KO-Phase)"));
    	cb_functions.getSelectionModel().select(3);;
    }

    
	
	public void setMainApp (WM2018 mainApp)
	{
		this.mainApp=mainApp;
	}
	
	public WM2018 getMainApp ()
	{
		return mainApp;
	}
	
}