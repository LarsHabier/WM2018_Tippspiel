package wm.gui.view;



import javafx.fxml.FXML;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import javafx.stage.Stage;
import wm.controller.WMHandler;

/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */

public class Local_Connect_Controller {
	
	private static WMHandler handler;
	public WM2018MainController main_ctrl;
	
	@FXML public TextField tf_ip;
	@FXML public TextField tf_db;
	@FXML public TextField tf_port;
	@FXML public TextField tf_user;
	@FXML public TextField tf_psw;
	@FXML public Button btn_connect;
	@FXML public Button btn_connect_local;

	@FXML
	private void initialize ()
	{
		
		handler = new WMHandler();
	    main_ctrl = new WM2018MainController();

	    

	}
	

	/**
	 * Es wird eine manuelle Datenbankverbindung mit Hilfe der Werte aus den TextFelder aufgebaut.
	 */
	@FXML
	private void connect_manuall_db() {
		String ergebnis = handler.connect_local_db(this);
		//print on console
		WMHandler.writeOnConsole(ergebnis,false);
		if(ergebnis.equals("Verbindung zur Datenbank "+tf_db.getText()+" hergestellt!\n")) {
		WMHandler.getMain_controller().enable_choice_box();
		}
	    closeWindow();
		
		
	}
	/**
	 * Methode, um eine schnelle Verbindung mit meiner persönlichen lokalen Datenbank aufzubauen (Unwichtig).
	 */
	@FXML private void connect_local_db() {
		tf_ip.setText("localhost");
		tf_db.setText("WM2018");
		tf_port.setText("3306");
		tf_user.setText("root");
		tf_psw.setText("test");
		connect_manuall_db();
		
	}

	/**
	 * Methode um das aktuelle Fenster zu schließen.
	 */
	private void closeWindow() {
		Stage stage = (Stage) btn_connect_local.getScene().getWindow();
		stage.close();
	}
	
	
}


