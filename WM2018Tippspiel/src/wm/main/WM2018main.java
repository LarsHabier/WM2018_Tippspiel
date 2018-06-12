package wm.main;

import wm.controller.Hilfsfunktionen;
import wm.objekte.Configuration;
import wm.objekte.DBConnector;

// Diese Klasse dient nur Testzwecken, die hier dargestellte Funktionalität soll
// über eine graphische Benutzungsoberfläche realisiert werden
// Gestartet wird das Programm dann über die Klasse "WM2018mainGUI.java"

public class WM2018main
{
	public static void main (String[] args)
	{
		System.out.println("Konfigurationsdaten aus Datei lesen...\n");
		
		String[] configDaten=Hilfsfunktionen.leseConfig("config.txt");
		
		System.out.println("Server-IP\t|Datenbank\t|Port\t|Benutzer\t\t|Passwort\t|");
		
		for (String wert: configDaten)
			System.out.print(wert+"\t|");
		System.out.println("\n");
		
		if (!configDaten[0].startsWith("Fehler"))
		{
			// Die Konfigurationsdaten konnten gelesen werden, wir können das
			// Configurations-Objekt erstellen
			Configuration config=new Configuration(configDaten);
			
			System.out.println("Konfiguration erstellt...");
			
			System.out.println("Verbinde mich mit der Datenbank...");
			
			// Verbinden mit Datenbank
			DBConnector datenbank=new DBConnector();
			System.out.println(datenbank.connect(config));
			
			// Anlegen der benötigten Tabellen
			System.out.println(datenbank.tabellenAnlegen());
			
			// Einlesen der Echt- oder Testdaten
			boolean testdaten=false;
			System.out.println(datenbank.datenEinlesen(testdaten));
			
			// Verbindung zur Datenbank trennen
			System.out.println(datenbank.close());
		}
	}
}
