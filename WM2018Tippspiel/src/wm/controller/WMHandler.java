package wm.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import wm.gui.*;
import wm.gui.view.*;
import wm.objekte.Configuration;
import wm.objekte.DBConnector;
import wm.objekte.Preparation;
import wm.objekte.WM2018Benutzer;
import wm.objekte.WM2018Mannschaft;
import wm.controller.Hilfsfunktionen;

/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */

// In dieser Klasse können die Funktionen ausprogrammiert werden, die beim Auslösen von Events
// (z.B. Drücken eines Buttons) ausgeführt werden sollen

public class WMHandler {
	private static Configuration config;
	private static DBConnector connector;
	private static Preparation prep;
	private static WM2018MainController main_controller;
	public static List<WM2018Mannschaft>  a;
	public static List<WM2018Mannschaft>  b;
	public static List<WM2018Mannschaft>  c;
	public static List<WM2018Mannschaft>  d;
	public static List<WM2018Mannschaft>  e;
	public static List<WM2018Mannschaft>  f;
	public static List<WM2018Mannschaft>  g;
	public static List<WM2018Mannschaft>  h;
	/**
	 * Die Methode wertet den Wert aus, der von dem ausgewählten Item in der ChoiceBox zurückgegeben wird.
	 * @param controller - WM2018MainController
	 * @param mainapp - WM2018
	 */

	public void select_cb_item(WM2018MainController controller, WM2018 mainapp) {

		if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 0) {
			//Öffne neues Fenster 
			mainapp.openNewWindow(mainapp, "view/local_connect_Layout.fxml","Manuelle Verbindung mit Datenbank herstellen");

		} else if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 1) {
			String ergebnis = connect_live_db(controller);
			writeOnConsole(ergebnis, false);
			if(ergebnis.equals("Verbindung zur Datenbank wm_2018_team_h hergestellt!\n")) {
				controller.enable_choice_box();
			}
		} else if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 3) {
			String ergebnis = create_tables();
			writeOnConsole(ergebnis, false);
		} else if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 4) {
			mainapp.openNewWindow(mainapp, "view/data_input_Layout.fxml", "Daten einlesen");
		} else if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 6) {
			mainapp.openNewWindow(mainapp, "view/schedule_table_Layout.fxml", "Spielplan ausgeben");
		} else if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 7) {
			mainapp.openNewWindow(mainapp, "view/record_results_Layout.fxml", "Ergebnisse eintragen");
		} else if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 8) {
			mainapp.openNewWindow(mainapp, "view/match_results_table_Layout.fxml", "Aktuelle Ergebnisse");
		} else if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 10) {
			String ergebnis = create_ranking();
			writeOnConsole(ergebnis, true);
		} else if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 11) {
			mainapp.openNewWindow(mainapp, "view/ranking_Layout.fxml", "Aktuelles Ranking");
		} else if (controller.cb_functions.getSelectionModel().getSelectedIndex() == 13) {
			mainapp.openNewWindow(mainapp, "view/update_finals_Layout.fxml", "Spielpaarungen bearbeiten (KO-Phase)");
		}
		

	}
	
	/**
	 * Mit Hilfe dieser Methode wird eine lokale Datenbankverbindung, mit der in den TextFelder von local_connect_Layout.fxml befindenen
	 * Werte, aufgebaut.
	 * @param controller - Local_Connect_Controller
	 * @return - Ein String, der das Ergebnis des Verbindungsaufbaus zurückgibt.
	 */

	public String connect_local_db(Local_Connect_Controller controller) {
		boolean isEmpty = false;
		String ergebnis = "Verbindung konnte nicht hergestellt werden.";
		String[] configDaten = new String[5];
		configDaten[0] = controller.tf_ip.getText();
		configDaten[1] = controller.tf_db.getText();
		configDaten[2] = controller.tf_port.getText();
		configDaten[3] = controller.tf_user.getText();
		configDaten[4] = controller.tf_psw.getText();
		for (int i = 0; i < configDaten.length; i++) {
			if (configDaten[i].equals("")) {
				isEmpty = true;
			}
		}
		if (isEmpty) {
			// Dialog-Fenster wird erstellt mit der Meldung, dass die TextFelder leer sind.
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Die Textfelder enthalten keine Daten");
			alert.setHeaderText("Eins oder mehrere Textfelder sind leer!");
			alert.setContentText("Um eine Verbindung aufzubauen müssen Sie alle Felder ausfüllen.");
			alert.showAndWait();
		} else {
			config = new Configuration(configDaten);
			connector = new DBConnector();
			ergebnis = connector.connect(config);
			setConnector(connector);
		}

		return ergebnis;
	}

	/**
	 * Mit dieser Methode wird eine Verbindung zur Live-Datenbank aufgebaut, mit den Werten die sich in der config.txt befinden.
	 * @param main_ctrl - WM2018MainController
	 * @return - Ergebnis des Verbindungsaufbaus
	 */
	private String connect_live_db(WM2018MainController main_ctrl) {
		prep = new Preparation();
		Configuration config = prep.erstelleKonfiguration();
		connector = new DBConnector();
		String ergebnis = connector.connect(config);
		setConnector(connector);
		return ergebnis;
	}

	/**
	 * Die Methode erstellt die notwendigen Tabellen spiele,ranking,tipps und benutzer.
	 * @return - Ergebnis der Erstellung der Tabellen
	 */
	private String create_tables() {
		connector = getConnector();
		String ergebnis = connector.tabellenAnlegen();
		return ergebnis;
	}

	/**
	 * Mit dieser Methode werden die RadioButtons des FXML-Dokuments data_input_Layout.fxml ausgewertet.
	 * @param data_ctrl - Data_Input_Controller
	 * @return - Ergebnis der Füllung der Tabellen
	 */
	public String inputData(Data_Input_Controller data_ctrl) {
		boolean testData = false;
		if (data_ctrl.radio_test.isSelected()) {

			testData = true;
		} else if (data_ctrl.radio_echt.isSelected()) {

			testData = false;
		}
		String ergebnis;
		connector = getConnector();
		ergebnis = connector.datenEinlesen(testData);
		return ergebnis;

	}
	
	/**
	 * Ausgabe der Spiele mit Hilfe einer TextArea. Diese Methode wird nicht mehr verwendet.
	 * @param controller - Schedule_Controller
	 */

	//Ausgabe mit TextArea
	public void display_match_schedules(Schedule_Controller controller) {
		connector = getConnector();
		List<String[]> matches = connector.spielplanAusgeben();
		for (int i = 0; i < matches.size(); i++) {
			String[] match = matches.get(i);
			controller.add_line_on_display(match[0], match[1], match[2], match[3], match[4]);
		}
		writeOnConsole("Spielplan wurde erfolgreich erstellt.", false);

	}
	
	/**
	 * Diese Methode sorgt dafür, dass die TableView im Dokument schedule_table_Layout.fxml gefüllt wird (mit Spielen).
	 * @param controller - Schedule_Table_Controller
	 */

	public void display_match_schedules_table(Schedule_Table_Controller controller) {
		connector = getConnector();
		List<String[]> matches = connector.spielplanAusgeben();
		for (int i = 0; i < matches.size(); i++) {
			String[] match = matches.get(i);
			String newDate = Hilfsfunktionen.datumWandeln(match[1]);
			String[] newDateFormatted = newDate.split(" ");
			//System.out.println(match[0]+" "+newDateFormatted[0]+" "+newDateFormatted[1]+" "+match[2]+"-"+match[3]+" "+match[4]);
			controller.schedules.add(new Schedule(match[0],newDateFormatted[0],newDateFormatted[1],match[2],"-",match[3],match[4]));
		}
		writeOnConsole("Spielplan wurde erfolgreich erstellt.", false);
	}

	/**
	 * Mit dieser Methode werden die TextFelder im Dokument record_results_Layout.fxml mit den jeweiigen Mannschaften gefüllt.
	 * @param controller - Record_Results_Controller
	 * @param position - Index für die Liste matches
	 */
	public void get_score_data(Record_Results_Controller controller, int position) {
		connector = getConnector();
		List<String[]> matches = connector.spieleSammeln();
		if (position < matches.size()) {
			String[] match = matches.get(position);
			switch (match[1]) {
			case "Achtelfinale":
				controller.enable_extra_Textfields();
				controller.fill_extra_Textfields(match[11], match[12], match[14], match[15]);
				break;
			case "Viertelfinale":
				controller.enable_extra_Textfields();
				controller.fill_extra_Textfields(match[11], match[12], match[14], match[15]);
				break;
			case "Halbfinale":
				controller.enable_extra_Textfields();
				controller.fill_extra_Textfields(match[11], match[12], match[14], match[15]);
				break;
			case "Finale":
				controller.enable_extra_Textfields();
				controller.fill_extra_Textfields(match[11], match[12], match[14], match[15]);
				break;
			case "3. Platz":
				controller.enable_extra_Textfields();
				controller.fill_extra_Textfields(match[11], match[12], match[14], match[15]);
				break;

			default:
				controller.disable_extra_Textfields();
			}
			controller.setCurrent_gameId(match[0]);
			controller.tf_match_desc.setText(match[1]);
			controller.tf_date.setText(Hilfsfunktionen.datumWandeln(match[3]));
			controller.tf_team_home.setText(match[4]);
			controller.tf_team_guest.setText(match[5]);
			controller.tf_score_half_home.setText(match[6]);
			controller.tf_score_half_guest.setText(match[7]);
			controller.tf_score_final_home.setText(match[8]);
			controller.tf_score_final_guest.setText(match[9]);
			controller.tf_yellow_cards_home.setText(match[16]);
			controller.tf_yellow_cards_guest.setText(match[17]);
			controller.tf_yellow_red_cards_home.setText(match[18]);
			controller.tf_yellow_red_cards_guest.setText(match[19]);
			controller.tf_red_cards_home.setText(match[20]);
			controller.tf_red_cards_guest.setText(match[21]);
		} else {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Keine weiteren Spiele vorhanden");
			alert.setHeaderText("Alle Spiele wurden mit Daten gefüllt");
			alert.setContentText("Es gibt in der Datenbank keine Spiele mehr, die mit Daten gefüllt werden können.");
			Optional<ButtonType> result = alert.showAndWait();
			if (!result.isPresent()) {

			} else if (result.get() == ButtonType.OK) {
				controller.closeWindow();
			} else if (result.get() == ButtonType.CANCEL) {
				controller.closeWindow();
			}

		}

	}

	/**
	 * Die Daten aus den TextFelder im Dokument record_results_Layout.fxml werden in der Datenbank gespeichert.
	 * @param controller - Record_Results_Controller
	 * @return - Ergebnis der Speicherung
	 */
	public String save_score_data(Record_Results_Controller controller) {
		String[] results = new String[22];
		if (Hilfsfunktionen.nullTest(controller.tf_score_extra_time_home.getText()).equals("null")
				|| Hilfsfunktionen.nullTest(controller.tf_score_extra_time_guest.getText()).equals("null")) {
			results[10] = "0";
		} else {
			results[10] = "1";
			results[11] = controller.tf_score_extra_time_home.getText();
			results[12] = controller.tf_score_extra_time_guest.getText();
		}
		if (Hilfsfunktionen.nullTest(controller.tf_score_penalty_home.getText()).equals("null")
				|| Hilfsfunktionen.nullTest(controller.tf_score_penalty_guest.getText()).equals("null")) {
			results[13] = "0";
		} else {
			results[13] = "1";
			results[14] = controller.tf_score_penalty_home.getText();
			results[15] = controller.tf_score_penalty_guest.getText();
		}

		results[0] = controller.getCurrent_gameId();
		results[6] = controller.tf_score_half_home.getText();
		results[7] = controller.tf_score_half_guest.getText();
		results[8] = controller.tf_score_final_home.getText();
		results[9] = controller.tf_score_final_guest.getText();
		results[16] = controller.tf_yellow_cards_home.getText();
		results[17] = controller.tf_yellow_cards_guest.getText();
		results[18] = controller.tf_yellow_red_cards_home.getText();
		results[19] = controller.tf_yellow_red_cards_guest.getText();
		results[20] = controller.tf_red_cards_home.getText();
		results[21] = controller.tf_red_cards_guest.getText();
		connector = getConnector();
		String ergebnis = connector.ergebnisseEintragen(results);

		return ergebnis;

	}

	/**
	 * Diese Methode zeigt alle fertigen Spiele in einer TextArea an. (Veraltet)
	 * @param controller - Match_Results_Controller
	 */
	public void show_match_results(Match_Results_Controller controller) {
		connector = getConnector();
		List<String[]> matches = connector.ergebnisseAusgeben();
		for (int i = 0; i < matches.size(); i++) {
			String[] match = matches.get(i);
			controller.add_line_on_display(match[1], match[4], match[5], match[6], match[7], match[8], match[9],
					match[11], match[12], match[14], match[15], match[16], match[17], match[20], match[21]);
		}

	}
	
	/**
	 * Diese Methode zeigt alle fertigen Spiele in einem TableView an.
	 * @param controller - Match_Results_Table_Controller
	 */
	public void show_match_results_table(Match_Results_Table_Controller controller) {
		connector = getConnector();
		List<String[]> matches = connector.ergebnisseAusgeben();
		String extraTimeScore;
		String penaltyScore;
		String halfTimeScore;
		String finalScore;
		String yellowCards;
		String redCards;
		for (int i = 0; i < matches.size(); i++) {
			String[] match = matches.get(i);
			if(match[10].equals("0")) {
				extraTimeScore = "Nicht gespielt";
			}
			else {
				extraTimeScore = match[11]+":"+match[12];
			}
			if(match[13].equals("0")) {
				penaltyScore = "Nicht gespielt";
			}
			else {
				penaltyScore = match[14]+":"+match[15];
			}
			halfTimeScore = match[6]+":"+match[7];
			finalScore = match[8]+":"+match[9];
			yellowCards = match[16]+" - "+match[17];
			redCards = match[20]+" - "+match[21];
			controller.match_results.add(new Match_Results(match[1],match[4],"-",match[5],halfTimeScore,finalScore,extraTimeScore,penaltyScore,yellowCards,redCards));
			
			
		}
	}
	
	/**
	 * Das Ranking wird erstellt, in dem die Tipps der Benutzer mit den Spielergebnissen verglichen werden.
	 * @return - Ergebnis der Erstellung des Rankings
	 */
    public String  create_ranking() {
    	String ergebnis = "Ranking konnte nicht erstellt werden";
    	connector = getConnector();
		List<String[]> user_list = connector.benutzerSammeln();
		List<String[]> bets_list = connector.tippsFuerRankingSammeln();
		List<String[]> matches_list = connector.spieleFuerRankingSammeln();
		List<WM2018Benutzer> ranking_user = new ArrayList<WM2018Benutzer>();
		int points = 0;
		for(int i = 0;i <user_list.size();i++) {
			String[] user_array = user_list.get(i); 
			for(int j = 0;j<matches_list.size();j++) {
				String[] matches_array = matches_list.get(j);
				for(int k = 0;k<bets_list.size();k++) {
					String[] bets_array = bets_list.get(k);
					if(user_array[0].equals(bets_array[1]) && bets_array[2].equals(matches_array[0])) {
						points = evaluate_bet(user_array[1],bets_array[4], bets_array[5], matches_array[6], matches_array[7], points, "HalfTime");
						points = evaluate_bet(user_array[1],bets_array[6], bets_array[7], matches_array[8], matches_array[9], points, "FinalTime");
						if(matches_array[10].equals("1")) {
						points = evaluate_bet(user_array[1],bets_array[8], bets_array[9], matches_array[11], matches_array[12], points, "ExtraTime");
						}
						if(matches_array[13].equals("1")) {
							points = evaluate_bet(user_array[1],bets_array[10], bets_array[11], matches_array[14], matches_array[15], points, "Penalty");
							}
						points = evaluate_bet(user_array[1],bets_array[12], bets_array[13], matches_array[16], matches_array[17], points, "YellowCards");
						points = evaluate_bet(user_array[1],bets_array[14], bets_array[15], matches_array[18], matches_array[19], points, "YellowRedCards");
						points = evaluate_bet(user_array[1],bets_array[16], bets_array[17], matches_array[20], matches_array[21], points, "RedCards");
					}
					
				}
				//Ausgabe für Testzwecke
				//System.out.println(points + "Punkte nach Spiel "+matches_array[0] + " für "+user_array[1]);
				
			}
			
			// Benutzer erstellen und in ranking_user übergeben
			// Punkte wieder auf 0 setzen
			WM2018Benutzer user = new WM2018Benutzer(user_array[0],user_array[1],user_array[2]);
			user.setPunkte(points);
			ranking_user.add(user);
			points = 0;
		}
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentDate = new Date();
		String date = formatter.format(currentDate);
		Collections.sort(ranking_user);
		ergebnis = connector.rankingEintragen(date, ranking_user);
		return ergebnis;
    	
    }
    
    /**
     * Diese Methode wertet die einzelnen Tipps (tatsächliche Tipps) aus, die mit Hilfe der Parameter übergeben werden und gibt eine neue Punktzahl zurück,
     * falls ein Tipp korrekt ist.
     * @param user - Aktuelle Tipper
     * @param bet_home - Tipp für die Heimmannschaft
     * @param bet_guest - Tipp für die Gastmannschaft
     * @param home - Tatsächliches Ergebnis Heimmannschaft
     * @param guest - Tatsächliches Ergebnis Gastmannschaft
     * @param points - Aktuelle Punktzahl des Tippers
     * @param bet_type - Art des Tipps
     * @return - neue Punktzahl nach allen überprüften Tipps
     */
    private int evaluate_bet(String user,String bet_home,String bet_guest,String home, String guest, int points,String bet_type) {
    	if(bet_type.equals("HalfTime")) {
    	if(bet_home.equals(home) && bet_guest.equals(guest)) {
    		points = points +6;
    		//Ausgabe für Testzwecke
    		//System.out.println(user+" bekommt +6 für korrektes ergebnis(halbzeit)");
    	}
    	else {
    		//Tendenziell richtig getippt +3 
    		if(evaluate_bet_tendency(bet_home, bet_guest, home, guest)) {
    			points = points +3 ;
    			
    			//
    			//System.out.println(user+" bekommt +3 für tendenziell korrektes ergebnis(halbzeit)");
    		}
    	}
    	}
    	else if(bet_type.equals("FinalTime")) {
    		if(bet_home.equals(home) && bet_guest.equals(guest)) {
        		points = points +11;
        		
        		//Ausgabe für Testzwecke
        		//System.out.println(user+" bekommt +11 für korrektes ergebnis(ende)");
        	}
        	else {
        		//Tendenziell richtig getippt +5
        		if(evaluate_bet_tendency(bet_home, bet_guest, home, guest)) {
        			points = points +5 ;
        			//Ausgabe für Testzwecke
        			//System.out.println(user+" bekommt +5 für tendenziell korrektes ergebnis(ende)");
        		}
        	}
    		
    	}
    	else if(bet_type.equals("ExtraTime")) {
    		if(bet_home.equals(home) && bet_guest.equals(guest)) {
        		points = points +11;
        		//Ausgabe für Testzwecke
        		//System.out.println(user+" bekommt +11 für korrektes ergebnis(verlängerung)");
        	}
        	else {
        		//Tendenziell richtig getippt +5
        		if(evaluate_bet_tendency(bet_home, bet_guest, home, guest)) {
        			points = points +5 ;
        			//Ausgabe für Testzwecke
        			//System.out.println(user+" bekommt +5 für tendenziell korrektes ergebnis(verlängerung)");
        		}
        	}
    		
    	}
    	else if(bet_type.equals("Penalty")) {
    		if(bet_home.equals(home) && bet_guest.equals(guest)) {
        		points = points +11;
        		//Ausgabe für Testzwecke
        		//System.out.println(user+" bekommt +11 für korrektes ergebnis(elfmeter)");
        	}
        	else {
        		//Tendenziell richtig getippt +5
        		if(evaluate_bet_tendency(bet_home, bet_guest, home, guest)) {
        			points = points +5 ;
        			//Ausgabe für Testzwecke
        			//System.out.println(user+" bekommt +5 für tendenziell korrektes ergebnis(elfmeter)");
        		}
        	}
    	}
    	else if(bet_type.equals("YellowCards")) {
    		if(bet_home.equals(home)) {
    			points = points + 3;
    			//Ausgabe für Testzwecke
    			//System.out.println(user+" bekommt +3 für korrektes ergebnis(gelb)");
    		}
    		if(bet_guest.equals(guest)) {
    			points = points + 3;
    			//Ausgabe für Testzwecke
    			//System.out.println(user+" bekommt +3 für korrektes ergebnis(gelb)");
    		}
    	}
    	else if(bet_type.equals("YellowRedCards")) {
    		if(bet_home.equals(home)) {
    			points = points + 4;
    			//Ausgabe für Testzwecke
    			//System.out.println(user+" bekommt +4 für korrektes ergebnis(gelbrot)");
    		}
    		if(bet_guest.equals(guest)) {
    			points = points + 4;
    			//Ausgabe für Testzwecke
    			//System.out.println(user+" bekommt +4 für korrektes ergebnis(gelbrot)");
    		}
    	}
    	else if(bet_type.equals("RedCards")) {
    		if(bet_home.equals(home)) {
    			points = points + 5;
    			//Ausgabe für Testzwecke
    			//System.out.println(user+" bekommt +5 für korrektes ergebnis(rot)");
    		}
    		if(bet_guest.equals(guest)) {
    			points = points + 5;
    			//Ausgabe für Testzwecke
    			//System.out.println(user+" bekommt +5 für korrektes ergebnis (rot)");
    		}
    	}
    	return points;

    }
    
    /**
     * Diese Methode wertet die Tipps aus, die tendenziell korrekt sein könnten.
     * @param bet_home - Tipp Heimmannschaft
     * @param bet_guest - Tipp Gastmannschaft
     * @param home - Tatsächliches Ergebnis Heim
     * @param guest - Tatsächliches Ergebnis Gast
     * @return - boolean ob eine Tendenz vorhanden ist oder nicht
     */
    private boolean evaluate_bet_tendency(String bet_home,String bet_guest,String home,String guest) {
    	boolean ergebnis = false;
    	if(bet_home.equals(bet_guest) && home.equals(guest)) {
    		ergebnis = true;
    	}
    	else if(Integer.parseInt(bet_home) > Integer.parseInt(bet_guest) && Integer.parseInt(home) > Integer.parseInt(guest)) {
    		ergebnis = true;
    	}
    	else if(Integer.parseInt(bet_guest)>Integer.parseInt(bet_home) && Integer.parseInt(guest) > Integer.parseInt(home)) {
    		ergebnis = true;
    	}
    	else if(Integer.parseInt(bet_home) < Integer.parseInt(bet_guest) && Integer.parseInt(home) < Integer.parseInt(guest)) {
    		ergebnis = true;
    	}
    	else if(Integer.parseInt(bet_guest) < Integer.parseInt(bet_home) && Integer.parseInt(guest) < Integer.parseInt(home)) {
    		ergebnis = true;
    	}
    	
    	
    	return ergebnis;
    }
    /**
     * Das Ranking wird in einem TableView dargestellt.
     * @param controller - Ranking_Controller
     */
    public void show_ranking(Ranking_Controller controller) {
    	connector = getConnector();
    	List<String[]> ranking = connector.rankingAusgeben();
    	for(int i = 0;i<ranking.size();i++) {
    		String[] ranks = ranking.get(i);
    		controller.l_date.setText("Das aktuelle Ranking ist vom Stand: "+Hilfsfunktionen.datumWandeln(ranks[0]));
    		controller.ranking.add(new Rank(ranks[1],ranks[2],ranks[3],ranks[4]));
    	}
    }
    
    /**
     * Bei dem manuellen Korrigieren der KO-Spiele werden alle TextFelder mit der jeweiligen passenden Mannschaft gefüllt.
     * @param controller - Update_Finals_Controller
     */
    public void fill_finals(Update_Finals_Controller controller) {
    	connector = getConnector();
    	List<String[]> matches = connector.koSpieleSammeln();
    	String[] match = matches.get(0);
    	controller.tf_achtel1_home.setText(match[0]);
    	controller.tf_achtel1_guest.setText(match[1]);
    	match = matches.get(1);
    	controller.tf_achtel2_home.setText(match[0]);
    	controller.tf_achtel2_guest.setText(match[1]);
    	match = matches.get(2);
    	controller.tf_achtel3_home.setText(match[0]);
    	controller.tf_achtel3_guest.setText(match[1]);
    	match = matches.get(3);
    	controller.tf_achtel4_home.setText(match[0]);
    	controller.tf_achtel4_guest.setText(match[1]);
    	match = matches.get(4);
    	controller.tf_achtel5_home.setText(match[0]);
    	controller.tf_achtel5_guest.setText(match[1]);
    	match = matches.get(5);
    	controller.tf_achtel6_home.setText(match[0]);
    	controller.tf_achtel6_guest.setText(match[1]);
    	match = matches.get(6);
    	controller.tf_achtel7_home.setText(match[0]);
    	controller.tf_achtel7_guest.setText(match[1]);
    	match = matches.get(7);
    	controller.tf_achtel8_home.setText(match[0]);
    	controller.tf_achtel8_guest.setText(match[1]);
    	match = matches.get(8);
    	controller.tf_viertel1_home.setText(match[0]);
    	controller.tf_viertel1_guest.setText(match[1]);
    	match = matches.get(9);
    	controller.tf_viertel2_home.setText(match[0]);
    	controller.tf_viertel2_guest.setText(match[1]);
    	match = matches.get(10);
    	controller.tf_viertel3_home.setText(match[0]);
    	controller.tf_viertel3_guest.setText(match[1]);
    	match = matches.get(11);
    	controller.tf_viertel4_home.setText(match[0]);
    	controller.tf_viertel4_guest.setText(match[1]);
    	match = matches.get(12);
    	controller.tf_halb1_home.setText(match[0]);
    	controller.tf_halb1_guest.setText(match[1]);
    	match = matches.get(13);
    	controller.tf_halb2_home.setText(match[0]);
    	controller.tf_halb2_guest.setText(match[1]);
    	match = matches.get(14);
    	controller.tf_3rd_home.setText(match[0]);
    	controller.tf_3rd_guest.setText(match[1]);
    	match = matches.get(15);
    	controller.tf_final_home.setText(match[0]);
    	controller.tf_final_guest.setText(match[1]);
    }
    
    /**
     * Die Daten für die manuelle Erfassung der KO-Spiele werden in der Datenbank gespeichert.
     * @param controller - Update_Finals_Controller
     * @return - Ergebnis der Speicherung
     */
    public String save_finals_data(Update_Finals_Controller controller) {
    	String[] match = new String[32];
    	match[0] = controller.tf_achtel1_home.getText();
    	match[1] = controller.tf_achtel1_guest.getText();
    	match[2] = controller.tf_achtel2_home.getText();
    	match[3] = controller.tf_achtel2_guest.getText();
    	match[4] = controller.tf_achtel3_home.getText();
    	match[5] = controller.tf_achtel3_guest.getText();
    	match[6] = controller.tf_achtel4_home.getText();
    	match[7] = controller.tf_achtel4_guest.getText();
    	match[8] = controller.tf_achtel5_home.getText();
    	match[9] = controller.tf_achtel5_guest.getText();
    	match[10] = controller.tf_achtel6_home.getText();
    	match[11] = controller.tf_achtel6_guest.getText();
    	match[12] = controller.tf_achtel7_home.getText();
    	match[13] = controller.tf_achtel7_guest.getText();
    	match[14] = controller.tf_achtel8_home.getText();
    	match[15] = controller.tf_achtel8_guest.getText();
    	match[16] = controller.tf_viertel1_home.getText();
    	match[17] = controller.tf_viertel1_guest.getText();
    	match[18] = controller.tf_viertel2_home.getText();
    	match[19] = controller.tf_viertel2_guest.getText();
    	match[20] = controller.tf_viertel3_home.getText();
    	match[21] = controller.tf_viertel3_guest.getText();
    	match[22] = controller.tf_viertel4_home.getText();
    	match[23] = controller.tf_viertel4_guest.getText();
    	match[24] = controller.tf_halb1_home.getText();
    	match[25] = controller.tf_halb1_guest.getText();
    	match[26] = controller.tf_halb2_home.getText();
    	match[27] = controller.tf_halb2_guest.getText();
    	match[28] = controller.tf_3rd_home.getText();
    	match[29] = controller.tf_3rd_guest.getText();
    	match[30] = controller.tf_final_home.getText();
    	match[31] = controller.tf_final_guest.getText();
    	String ergebnis = connector.koSpieleEintragen(match);
    	return ergebnis;
    }
    

    /**
     * Diese Methode schreibt einen beliebigen Text in die Konsole des Dokuments RootLayout.fxml.
     * @param text - Text der angezeigt werden soll.
     * @param newLine - boolean, ob ein Zeilenumbruch am Amfang vorhanden sein soll
     */
	public static void writeOnConsole(String text, boolean newLine) {
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
		Date currentTime = new Date();
		if (newLine) {
			getMain_controller().console.appendText("\n" + formatter.format(currentTime) + " - " + text);
		} else {
			getMain_controller().console.appendText(formatter.format(currentTime) + " - " + text);
		}
	}


	// Einfache Setter und Getter
	public static DBConnector getConnector() {
		return connector;
	}

	public static void setConnector(DBConnector connector) {
		WMHandler.connector = connector;
	}

	public static WM2018MainController getMain_controller() {
		return main_controller;
	}

	public static void setMain_controller(WM2018MainController main_controller) {
		WMHandler.main_controller = main_controller;
	}

}
