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
public class Record_Results_Controller {
	
	private static WMHandler handler;
	public WM2018MainController main_ctrl;
	private static int counter;
	private String current_gameId;

	@FXML public Button btn_save_data;
	@FXML public Button btn_next_match;
	@FXML public TextField tf_match_desc;
	@FXML public TextField tf_date;
	@FXML public TextField tf_team_home;
	@FXML public TextField tf_team_guest;
	@FXML public TextField tf_score_half_home;
	@FXML public TextField tf_score_half_guest;
	@FXML public TextField tf_score_final_home;
	@FXML public TextField tf_score_final_guest;
	@FXML public TextField tf_score_extra_time_home;
	@FXML public TextField tf_score_extra_time_guest;
	@FXML public TextField tf_score_penalty_home;
	@FXML public TextField tf_score_penalty_guest;
	@FXML public TextField tf_yellow_cards_home;
	@FXML public TextField tf_yellow_cards_guest;
	@FXML public TextField tf_yellow_red_cards_home;
	@FXML public TextField tf_yellow_red_cards_guest;
	@FXML public TextField tf_red_cards_home;
	@FXML public TextField tf_red_cards_guest;
	
	
	@FXML
	private void initialize ()
	{
		handler = new WMHandler();
	    main_ctrl = new WM2018MainController();
	    //Setze counter gleich 0,so dass das erste Match ausgewählt wird, welches noch mit Daten gefüllt werden kann.
	    counter = 0;
	    get_finished_Game();
	}
	
	/**
	 * Speichere die Daten der TextFelder in der Datenbank.
	 */
	@FXML
	private void save_data() {
		String ergebnis = handler.save_score_data(this);
		WMHandler.writeOnConsole(ergebnis,true);
	}
	/**
	 * Beim drücken auf den Nächstes Spiel Button wird der aktuelle Index von counter übergeben und dann um eins erhöht.
	 */
	@FXML
	private void get_finished_Game() {
		handler.get_score_data(this, counter);
		counter++;
	}
	/**
	 * Aktiviere die Verlängerung/Elfmeter TextFelder
	 */
	public void enable_extra_Textfields() {
		tf_score_extra_time_home.setDisable(false);
		tf_score_extra_time_guest.setDisable(false);
		tf_score_penalty_home.setDisable(false);
		tf_score_penalty_guest.setDisable(false);
		
	}
	/**
	 * Deaktiviere die Verlängerung/Elfmeter TextFelder
	 */
	public void disable_extra_Textfields() {
		tf_score_extra_time_home.setText("");
		tf_score_extra_time_guest.setText("");
		tf_score_penalty_home.setText("");
		tf_score_penalty_guest.setText("");
		tf_score_extra_time_home.setDisable(true);
		tf_score_extra_time_guest.setDisable(true);
		tf_score_penalty_home.setDisable(true);
		tf_score_penalty_guest.setDisable(true);
		
	}
	
	/**
	 * Falls es ein KO-Spiel ist, fülle die Verlängerung/Elfmeter TextFelder.
	 * @param extra_time_home
	 * @param extra_time_guest
	 * @param penalty_home
	 * @param penalty_guest
	 */
	public void fill_extra_Textfields(String extra_time_home,String extra_time_guest,String penalty_home,String penalty_guest) {
		tf_score_extra_time_home.setText(extra_time_home);
		tf_score_extra_time_guest.setText(extra_time_guest);
		tf_score_penalty_home.setText(penalty_home);
		tf_score_penalty_guest.setText(penalty_guest);
		
		
	}
	/**
	 * Methode um das aktuelle Fenster zu schließen.
	 */
	public void closeWindow() {
		Stage stage = (Stage) btn_save_data.getScene().getWindow();
		stage.close();
	}
	
	public String getCurrent_gameId() {
		return current_gameId;
	}

	public void setCurrent_gameId(String current_gameId) {
		this.current_gameId = current_gameId;
	}

}
