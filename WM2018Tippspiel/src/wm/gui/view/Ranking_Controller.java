package wm.gui.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import wm.controller.WMHandler;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */
public class Ranking_Controller {

	private static WMHandler handler;
	public WM2018MainController main_ctrl;
	public ObservableList<Rank> ranking = FXCollections.observableArrayList();
	@FXML public Label l_date;
	@FXML public TableView<Rank> table;
	@FXML public TableColumn<Rank,String> tc_rank;
	@FXML public TableColumn<Rank,String> tc_bet;
	@FXML public TableColumn<Rank,String> tc_points;
	@FXML public TableColumn<Rank,String> tc_group;
	
	@FXML
	private void initialize ()
	{
		handler = new WMHandler();
	    main_ctrl = new WM2018MainController();
	    //Setze Attribute der Ranks mit der TableView gleich
	    tc_rank.setCellValueFactory(new PropertyValueFactory<Rank,String>("rank"));
	    tc_bet.setCellValueFactory(new PropertyValueFactory<Rank,String>("user"));
	    tc_points.setCellValueFactory(new PropertyValueFactory<Rank,String>("points"));
	    tc_group.setCellValueFactory(new PropertyValueFactory<Rank,String>("group"));
	    show_ranking();
	    table.setItems(ranking);
	    
	}
	
	/**
	 * Erstelle das Ranking in der TableView.
	 */
	private void show_ranking() {
		handler.show_ranking(this);
		WMHandler.writeOnConsole("Ranking wurde ausgegeben.", false);
	}
	
}
