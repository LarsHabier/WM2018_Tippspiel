package wm.gui.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import wm.controller.WMHandler;

/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */
public class Data_Input_Controller {
	
	private static WMHandler handler;
	public WM2018MainController main_ctrl;
	
	@FXML Button btn_readData;
	@FXML public RadioButton radio_test;
	@FXML public RadioButton radio_echt;
	private ToggleGroup group;
	
	
	@FXML
	private void initialize ()
	{
		
		handler = new WMHandler();
	    main_ctrl = new WM2018MainController();
	    group = new ToggleGroup();
	    radio_test.setToggleGroup(group);
	    radio_echt.setToggleGroup(group);
	    radio_echt.setSelected(true);
	  
	}
	
	/**
	 * Auswahl, ob Echtdaten oder Testdaten ausgew‰hlt werden sollen.
	 */
	@FXML
	public void inputData() {
		String ergebnis  = handler.inputData(this);
		//print On Console
		WMHandler.writeOnConsole(ergebnis,false);
		closeWindow();
		
	}
	
	/**
	 * Optionale Methode um das Fenster wieder zu schlieﬂen.
	 */
	private void closeWindow() {
		Stage stage = (Stage) btn_readData.getScene().getWindow();
		stage.close();
	}
	

}
