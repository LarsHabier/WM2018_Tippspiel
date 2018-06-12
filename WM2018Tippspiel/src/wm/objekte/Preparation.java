package wm.objekte;

import wm.controller.Hilfsfunktionen;

// Diese Klasse erstellt ein Objekt Preparation, das die Konfiguration und den DBConnector umfasst.
// Auf die Attribute dieses Objekt kann mit Hilfe von Gettern und Settern von außen zugegriffen werden

public class Preparation
{
	private Configuration config;
	private DBConnector dbConnect;	

	public Preparation ( )
	{
		this.config=erstelleKonfiguration();
		this.dbConnect=new DBConnector ();
	}

	public Configuration erstelleKonfiguration()
	{		
		String[] configDaten=Hilfsfunktionen.leseConfig("config.txt");
				
		if (!configDaten[0].startsWith("Fehler"))
		{
			// Die Konfigurationsdaten konnten gelesen werden, wir können das Configurations-Objekt erstellen
					
			return new Configuration(configDaten);
		}
		else return null;
	}

	public final Configuration getConfig ()
	{
		return config;
	}

	public final void setConfig (Configuration config)
	{
		this.config=config;
	}

	public final DBConnector getDbConnect ()
	{
		return dbConnect;
	}

	public final void setDbConnect (DBConnector dbConnect)
	{
		this.dbConnect=dbConnect;
	}
}
