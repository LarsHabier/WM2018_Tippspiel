package wm.gui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import wm.controller.WMHandler;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */
public class Match_Results_Table_Controller {
	private static WMHandler handler;
	public WM2018MainController main_ctrl;
	public ObservableList<Match_Results> match_results = FXCollections.observableArrayList();
	@FXML public TableView<Match_Results> table;
	@FXML public TableColumn<Match_Results,String> tc_group;
	@FXML public TableColumn<Match_Results,String> tc_homeTeam;
	@FXML public TableColumn<Match_Results,String> tc_split;
	@FXML public TableColumn<Match_Results,String> tc_guestTeam;
	@FXML public TableColumn<Match_Results,String> tc_halfTime;
	@FXML public TableColumn<Match_Results,String> tc_finalTime;
	@FXML public TableColumn<Match_Results,String> tc_extraTime;
	@FXML public TableColumn<Match_Results,String> tc_penalty;
	@FXML public TableColumn<Match_Results,String> tc_yellow;
	@FXML public TableColumn<Match_Results,String> tc_red;

	@FXML
	private void initialize ()
	{
		handler = new WMHandler();
	    main_ctrl = new WM2018MainController();
	    //Setze die Spalten mit den Attributen der Klasse Match_Results gleich
	    tc_group.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("group"));
	    tc_homeTeam.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("homeTeam"));
	    tc_split.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("splitter"));
	    tc_guestTeam.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("guestTeam"));
	    tc_halfTime.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("halfTimeScore"));
	    tc_finalTime.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("finalScore"));
	    tc_extraTime.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("extraTimeScore"));
	    tc_penalty.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("penaltyScore"));
	    tc_yellow.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("yellowCards"));
	    tc_red.setCellValueFactory(new PropertyValueFactory<Match_Results,String>("redCards"));
	    display_match_results();
	    table.setItems(match_results);
	}
	
	/**
	 * Zeige beendete Spiele in einer TableView
	 */
	private void display_match_results() {
		handler.show_match_results_table(this);
		WMHandler.writeOnConsole("Die aktuellen Spielergebnisse wurden ausgegeben.", true);
	}
}
