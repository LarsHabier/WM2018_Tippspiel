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
public class Update_Finals_Controller {
	
	private static WMHandler handler;
	public WM2018MainController main_ctrl;
	
	@FXML public Button btn_save_data;
	@FXML public TextField tf_achtel1_home;
	@FXML public TextField tf_achtel1_guest;
	@FXML public TextField tf_achtel2_home;
	@FXML public TextField tf_achtel2_guest;
	@FXML public TextField tf_achtel3_home;
	@FXML public TextField tf_achtel3_guest;
	@FXML public TextField tf_achtel4_home;
	@FXML public TextField tf_achtel4_guest;
	@FXML public TextField tf_achtel5_home;
	@FXML public TextField tf_achtel5_guest;
	@FXML public TextField tf_achtel6_home;
	@FXML public TextField tf_achtel6_guest;
	@FXML public TextField tf_achtel7_home;
	@FXML public TextField tf_achtel7_guest;
	@FXML public TextField tf_achtel8_home;
	@FXML public TextField tf_achtel8_guest;
	@FXML public TextField tf_viertel1_home;
	@FXML public TextField tf_viertel1_guest;
	@FXML public TextField tf_viertel2_home;
	@FXML public TextField tf_viertel2_guest;
	@FXML public TextField tf_viertel3_home;
	@FXML public TextField tf_viertel3_guest;
	@FXML public TextField tf_viertel4_home;
	@FXML public TextField tf_viertel4_guest;
	@FXML public TextField tf_halb1_home;
	@FXML public TextField tf_halb1_guest;
	@FXML public TextField tf_halb2_home;
	@FXML public TextField tf_halb2_guest;
	@FXML public TextField tf_3rd_home;
	@FXML public TextField tf_3rd_guest;
	@FXML public TextField tf_final_home;
	@FXML public TextField tf_final_guest;
	
	
	
	@FXML
	private void initialize ()
	{
		handler = new WMHandler();
	    main_ctrl = new WM2018MainController();
	    handler.fill_finals(this);
	}
	/**
	 * Speichere die manuelle Erfassung der KO-Spiele.
	 */
	@FXML
	private void save_final_data() {
		String ergebnis = handler.save_finals_data(this);
		WMHandler.writeOnConsole(ergebnis, true);
		closeWindow();
	}
	
	/**
	 * Methode, um das aktuelle Fenster zu schlieﬂen.
	 */
	private void closeWindow() {
		Stage stage = (Stage) btn_save_data.getScene().getWindow();
		stage.close();
	}

}
