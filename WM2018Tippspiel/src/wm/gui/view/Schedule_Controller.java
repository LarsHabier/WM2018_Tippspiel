package wm.gui.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import wm.controller.Hilfsfunktionen;
import wm.controller.WMHandler;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */
public class Schedule_Controller {
	
	private static WMHandler handler;
	public WM2018MainController main_ctrl;
	@FXML public TextArea schedule_display;
	
	@FXML
	private void initialize ()
	{
		
		handler = new WMHandler();
	    main_ctrl = new WM2018MainController();
	    schedule_display.appendText("======================================================================================================================================\n");
	    schedule_display.appendText("Gruppe\t\tDatum\t\tAnstoss\t\tHeimmannschaft\t\t-\tGastmannschaft\t\tSpielort\n");
	    schedule_display.appendText("======================================================================================================================================\n");
	    display_match_schedule();
	
	  
	}
	/**
	 * Zeige den aktuellen Spielplan in einer TextArea.
	 */
	public void display_match_schedule() {
		handler.display_match_schedules(this);
	}
	
	/**
	 * Füge eine neue Zeile zur TextArea hinzu.
	 * @param match_desc - Spielbezeichnung
	 * @param date - Datum
	 * @param home - Heimmannschaft
	 * @param guest - Gastmannschaft
	 * @param venue - Spielort
	 */
	public void add_line_on_display(String match_desc,String date,String home,String guest,String venue) {
		String newDate = Hilfsfunktionen.datumWandeln(date);
		String[] newDateFormatted = newDate.split(" ");
		schedule_display.appendText(match_desc+"\t"+newDateFormatted[0]+"\t"+newDateFormatted[1]+"\t\t"+home+" \t\t"+"-\t"+guest+" \t\t"+venue+"\n");
		
	}
	

}
