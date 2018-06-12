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
public class Schedule_Table_Controller {
	
	private static WMHandler handler;
	public WM2018MainController main_ctrl;
	public ObservableList<Schedule> schedules = FXCollections.observableArrayList();
	@FXML public TableView<Schedule> table;
	@FXML public TableColumn<Schedule,String> tc_group;
	@FXML public TableColumn<Schedule,String> tc_date;
	@FXML public TableColumn<Schedule,String> tc_startTime;
	@FXML public TableColumn<Schedule,String> tc_homeTeam;
	@FXML public TableColumn<Schedule,String> tc_guestTeam;
	@FXML public TableColumn<Schedule,String> tc_split;
	@FXML public TableColumn<Schedule,String> tc_venue;
	
	@FXML
	private void initialize ()
	{
		handler = new WMHandler();
	    main_ctrl = new WM2018MainController();
	    //Setze die Attribute mit den TableColumns gleich.
	    tc_group.setCellValueFactory(new PropertyValueFactory<Schedule,String>("group"));
	    tc_date.setCellValueFactory(new PropertyValueFactory<Schedule,String>("date"));
	    tc_startTime.setCellValueFactory(new PropertyValueFactory<Schedule,String>("startTime"));
	    tc_homeTeam.setCellValueFactory(new PropertyValueFactory<Schedule,String>("homeTeam"));
	    tc_guestTeam.setCellValueFactory(new PropertyValueFactory<Schedule,String>("guestTeam"));
	    tc_split.setCellValueFactory(new PropertyValueFactory<Schedule,String>("splitter"));
	    tc_venue.setCellValueFactory(new PropertyValueFactory<Schedule,String>("venue"));
	    display_match_schedules();
	    table.setItems(schedules);
	}
	/**
	 * Zeige die Spielpläne in einem TableView
	 */
	private void display_match_schedules(){
		handler.display_match_schedules_table(this);
		
	}

}
