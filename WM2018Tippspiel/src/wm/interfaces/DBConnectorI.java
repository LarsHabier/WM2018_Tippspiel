package wm.interfaces;

import java.util.List;

import wm.objekte.Configuration;
import wm.objekte.WM2018Benutzer;
import wm.objekte.WM2018Mannschaft;

public interface DBConnectorI
{
	public String connect (Configuration config);
	
	public String tabellenAnlegen ();
	
	public String datenEinlesen (boolean testdaten);
	
	public List<String[]> spielplanAusgeben ();
	
	public List<String[]> spieleSammeln ();
	
	public String ergebnisseEintragen (String[] rsInhalte);
	
	public List<String[]> ergebnisseAusgeben ();
	
	public List<String[]> benutzerSammeln ();
	
	public List<String[]> spieleFuerRankingSammeln ();
	
	public List<String[]> tippsFuerRankingSammeln ();
	
	public String rankingEintragen (String datum, List<WM2018Benutzer> benutzer);
	
	public List<String[]> rankingAusgeben ();
	
	public List<String[]> mannschaftenSammeln ();
	
	public List<String[]> abgeschlosseneGruppenSpiele ();

	public List<String[]> direkterVergleich (WM2018Mannschaft heim, WM2018Mannschaft gast);

	public List<String[]> koSpieleSammeln ();
	
	public String koSpieleEintragen (String[] koSpiele);
	
	public String close ();	
}
