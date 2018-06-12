package wm.gui.view;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import wm.controller.WMHandler;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */
public class Match_Results_Controller {
	
	private static WMHandler handler;
	public WM2018MainController main_ctrl;
	@FXML public TextArea display_match_results;
	
	@FXML
	private void initialize ()
	{
		handler = new WMHandler();
	    main_ctrl = new WM2018MainController();
	    WMHandler.writeOnConsole("Ergebnisse der beendeten Spiele wurde aufgerufen.",false);
	    display_match_results.appendText("=========================================================================================\n");
	    display_match_results.appendText("Spielmodus\tHeimmannschaft\t-\tGastmannschaft\t\tHalbzeit\tRegul. Spielz.\tVerlängerung\tElfmeter\tGelbe Karten\tRote Karten\n");
	    display_match_results.appendText("=========================================================================================\n");
	    show_match_results();
	}
	
	/**
	 * Zeige aktuell beendete Spiele
	 */
	private void show_match_results() {
		handler.show_match_results(this);
	}
	
	/**
	 * Füge eine neue Zeile zur TextArea hinzu.
	 * @param match_desc - Spielbezeichnung
	 * @param home - Heimmannschaft
	 * @param guest - Gastmannschaft
	 * @param half_time_home - Ergebnis Halbzeit Heim
	 * @param half_time_guest - Ergebnis Halbzeit Gast
	 * @param final_time_home - Endergebnis Heim
	 * @param final_time_guest - Endergebnis Gast
	 * @param extra_time_home - Ergebnis Verlängerung Heim
	 * @param extra_time_guest - Ergebnis Verlängerung Gast
	 * @param penalty_home - Elfmeterergebnis Heim
	 * @param penalty_guest - Elfmeterergebnis Gast
	 * @param yellow_home - Gelbe Karten Heim
	 * @param yellow_guest - Gelbe Karten Gast
	 * @param red_home - Rote Karten Heim
	 * @param red_guest - Rote Karten Gast
	 */
	public void add_line_on_display(String match_desc,String home,String guest,String half_time_home,String half_time_guest,String final_time_home,String final_time_guest,String extra_time_home,String extra_time_guest,String penalty_home,String penalty_guest,String yellow_home,String yellow_guest,String red_home,String red_guest) {
		display_match_results.appendText(match_desc+"\t"+home+"\t"+" - \t"+guest+" \t"+half_time_home+":"+half_time_guest+"\t"+final_time_home+":"+final_time_guest+"\t"+extra_time_home+":"+extra_time_guest+"\t"+penalty_home+":"+penalty_guest+"\t"+yellow_home+" - "+yellow_guest+"\t"+red_home+" - "+red_guest+"\n");
		
	}

}
