package wm.objekte;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import wm.controller.Hilfsfunktionen;
import wm.interfaces.DBConnectorI;

public class DBConnector implements DBConnectorI
{
	private Statement		statement;
	private Connection	connection;
	private String			sql;
	private ResultSet		rs;
	
	public DBConnector ()
	{
		this.statement=null;
		this.connection=null;
		this.sql=null;
		this.rs=null;
	}
	
	@Override
	public String connect (Configuration config)
	{
		try
		{
			connection=DriverManager.getConnection(
			    "jdbc:mariadb://"+config.getIp()+":"+config.getPort()+"/"+config.getDb()+"?useSSL=false",
			    config.getUser(), config.getPass());
		}
		catch (Exception e)
		{
			return "Verbindung zur Datenbank "+config.getDb()+" konnte mit \nBenutzername: "
			    +config.getUser()+" und\nPasswort: "+config.getPass()+"\nnicht hergestellt werden!\n";
		}
		return "Verbindung zur Datenbank "+config.getDb()+" hergestellt!\n";
	}
	
	@Override
	public String tabellenAnlegen ()
	{
		if (this.connection!=null)
		{
			try
			{
				this.statement=connection.createStatement();
				
				this.statement.addBatch("DROP TABLE IF EXISTS ranking");
				this.statement.addBatch("DROP TABLE IF EXISTS tipps");
				this.statement.addBatch("DROP TABLE IF EXISTS benutzer");
				this.statement.addBatch("DROP TABLE IF EXISTS spiele");
				this.statement.executeBatch();
				
				sql="CREATE TABLE benutzer"+"(benutzerid INT AUTO_INCREMENT NOT NULL,"
				    +"benutzerName VARCHAR(20),"+"autologin VARCHAR(32),"+"IP VARCHAR(15) NOT NULL,"
				    +"sessionID VARCHAR(32) NOT NULL,"+"nickname VARCHAR(30) NOT NULL,"
				    +"passwort VARCHAR(32) NOT NULL,"+"gruppenname VARCHAR(32),"
				    +"email VARCHAR(70) NOT NULL,"+"show_Email BIT(1),"+"registrierungsdatum DATE,"
				    +"PRIMARY KEY (benutzerid),"+"UNIQUE (nickname, email))";
				
				this.statement.addBatch(sql);
				
				sql="CREATE TABLE spiele "+"(spieleid INT (10) AUTO_INCREMENT NOT NULL,"
				    +"spielbezeichnung VARCHAR(20),"+"spielort VARCHAR(20),"+"datumuhrzeit DATETIME(0),"
				    +"heimmannschaft VARCHAR(20),"+"gastmannschaft VARCHAR(20),"
				    +"heimmannschafthz INT(2) DEFAULT NULL,"+"gastmannschafthz INT(2) DEFAULT NULL,"
				    +"heimmannschaftende INT(2) DEFAULT NULL,"+"gastmannschaftende INT(2) DEFAULT NULL,"
				    +"verlaengerung BIT(1) DEFAULT 0,"+"heimmannschaftverl INT(2) DEFAULT NULL,"
				    +"gastmannschaftverl INT(2) DEFAULT NULL,"+"elfmeter BIT(1) DEFAULT 0,"
				    +"heimmannschaftelf INT(2) DEFAULT NULL,"+"gastmannschaftelf INT(2) DEFAULT NULL,"
				    +"gelbekartenheim INT(2) DEFAULT NULL,"+"gelbekartengast INT(2) DEFAULT NULL,"
				    +"gelbrotekartenheim INT(2) DEFAULT NULL,"+"gelbrotekartengast INT(2) DEFAULT NULL,"
				    +"rotekartenheim INT(2) DEFAULT NULL,"+"rotekartengast INT(2) DEFAULT NULL,"
				    +"PRIMARY KEY (spieleid))";
				
				this.statement.addBatch(sql);
				
				sql="CREATE TABLE tipps "+"(tippid INT(10) NOT NULL AUTO_INCREMENT,"+"benutzerid INT(10),"
				    +"spieleid INT(10),"+"tippdatum DATETIME(0),"+"tippheimhz INT(4),"+"tippgasthz INT(4),"
				    +"tippheimende INT(4),"+"tippgastende INT(4),"+"tippheimverl INT(4),"
				    +"tippgastverl INT(4),"+"tippheimelf INT(4),"+"tippgastelf INT(4),"
				    +"tippgelbeheim INT(4),"+"tippgelbegast INT(4), "+"tippgelbroteheim INT(4),"
				    +"tippgelbrotegast INT(4),"+"tipproteheim INT(4),"+"tipprotegast INT(4),"
				    +"PRIMARY KEY (tippid),"+"INDEX FK_tipps_benutzer (benutzerid),"
				    +"INDEX FK_tipps_spiele (spieleid),"
				    +"CONSTRAINT FK_tipps_benutzer FOREIGN KEY (benutzerid) REFERENCES benutzer (benutzerid),"
				    +"CONSTRAINT FK_tipps_spiele FOREIGN KEY (spieleid) REFERENCES spiele (spieleid))";
				
				this.statement.addBatch(sql);
				
				sql="CREATE TABLE ranking "+"(datum DATETIME(0),"+"benutzerid INT(11),"+"punkte INT(10),"
				    +"platz INT(10),"+"INDEX FK_ranking_benutzer (benutzerid),"
				    +"CONSTRAINT FK_ranking_benutzer FOREIGN KEY (benutzerid) REFERENCES benutzer (benutzerid))";
				
				this.statement.addBatch(sql);
				
				this.statement.executeBatch();
				
			}
			catch (SQLException e)
			{
				return "Vorgang fehlgeschlagen!\n";
			}
			return "Tabellen gelöscht.\n"+"Tabellen benutzer, spiele, tipps und ranking neu erstellt!\n";
		}
		else
			return "Bitte erst eine Verbindung herstellen!\n";
	}
	
	@Override
	public String datenEinlesen (boolean testdaten)
	{
		tabellenAnlegen();
		
		String datei=testdaten==true? "spiele_wm2018_test.txt": "spiele_wm2018.txt";
		String meldung="Spieldaten eingelesen!\n";
		
		if (this.connection!=null)
		{
			try
			{
				if (this.statement==null)
					this.statement=connection.createStatement();
				
				sql="LOAD DATA LOCAL INFILE './tabellen/"+datei
				    +"' INTO TABLE spiele FIELDS TERMINATED BY ';' LINES TERMINATED BY 'endOfLine'";
				this.statement.execute(sql);
				
				if (testdaten)
				{
					sql="LOAD DATA LOCAL INFILE './tabellen/benutzer.txt' INTO TABLE benutzer FIELDS TERMINATED BY ';'";
					this.statement.execute(sql);
					
					sql="LOAD DATA LOCAL INFILE './tabellen/tipps.txt' INTO TABLE tipps FIELDS TERMINATED BY ';'";
					this.statement.execute(sql);
					meldung="Benutzer-, Testspiel- und Testtippdaten eingelesen!\n";
				}
			}
			catch (SQLException e)
			{
				return "Fehler beim Einlesen der Daten!\n";
			}
			
			return meldung;
		}
		else
			return "Bitte erst eine Verbindung herstellen!\n";
	}
	
	@Override
	public List<String[]> spielplanAusgeben ()
	{
		sql="select spielbezeichnung, datumuhrzeit, heimmannschaft, gastmannschaft, spielort "
		    +"from spiele "+"order by datumuhrzeit";
		
		return datenAusDBholen(sql);
	}
	
	@Override
	public List<String[]> spieleSammeln ()
	{
		sql="select * "+"from spiele "+"where datumuhrzeit < date_add(now(),interval -45 MINUTE) and "
		    +"((heimmannschafthz is null or gastmannschafthz is null or "
		    +"heimmannschaftende is null or gastmannschaftende is null or "
		    +"gelbekartenheim is null or gelbekartengast is null or "
		    +"gelbrotekartenheim is null or gelbrotekartengast is null or "
		    +"rotekartenheim is null or rotekartengast is null) or "
		    +"(spielbezeichnung IN ('Achtelfinale','Viertelfinale','Halbfinale','Finale') and "
		    +"(((heimmannschaftverl is null or gastmannschaftverl is null) and "
		    +"(heimmannschaftende-gastmannschaftende = 0))	or "
		    +"((heimmannschaftelf is null or gastmannschaftelf is null) and "
		    +"(heimmannschaftverl-gastmannschaftverl = 0))))) "+"order by datumuhrzeit desc";
		
		return datenAusDBholen(sql);
	}
	
	@Override
	public String ergebnisseEintragen (String[] rsInhalte)
	{
		if (connection!=null)
		{
			try
			{
				if (statement==null)
					statement=connection.createStatement();
				
				sql="UPDATE spiele SET heimmannschafthz="+Hilfsfunktionen.nullTest(rsInhalte[6])+","
				    +"gastmannschafthz="+Hilfsfunktionen.nullTest(rsInhalte[7])+","+"heimmannschaftende="
				    +Hilfsfunktionen.nullTest(rsInhalte[8])+","+"gastmannschaftende="
				    +Hilfsfunktionen.nullTest(rsInhalte[9])+","+"verlaengerung="
				    +Hilfsfunktionen.nullTest(rsInhalte[10])+","+"heimmannschaftverl="
				    +Hilfsfunktionen.nullTest(rsInhalte[11])+","+"gastmannschaftverl="
				    +Hilfsfunktionen.nullTest(rsInhalte[12])+","+"elfmeter="
				    +Hilfsfunktionen.nullTest(rsInhalte[13])+","+"heimmannschaftelf="
				    +Hilfsfunktionen.nullTest(rsInhalte[14])+","+"gastmannschaftelf="
				    +Hilfsfunktionen.nullTest(rsInhalte[15])+","+"gelbekartenheim="
				    +Hilfsfunktionen.nullTest(rsInhalte[16])+","+"gelbekartengast="
				    +Hilfsfunktionen.nullTest(rsInhalte[17])+","+"gelbrotekartenheim="
				    +Hilfsfunktionen.nullTest(rsInhalte[18])+","+"gelbrotekartengast="
				    +Hilfsfunktionen.nullTest(rsInhalte[19])+","+"rotekartenheim="
				    +Hilfsfunktionen.nullTest(rsInhalte[20])+","+"rotekartengast="
				    +Hilfsfunktionen.nullTest(rsInhalte[21])+" "+"WHERE spieleid="
				    +Hilfsfunktionen.nullTest(rsInhalte[0]);
				
				statement.executeUpdate(sql);
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			return ("Daten gespeichert!\n");
		}
		else
			return ("Keine Verbindung!\n");
	}
	
	@Override
	public List<String[]> ergebnisseAusgeben ()
	{
		sql="select * from spiele "+"where datumuhrzeit < date_add(now(),interval -90 MINUTE) "
		    +"and (heimmannschafthz is not null or gastmannschafthz is not null "
		    +"or heimmannschaftende is not null or gastmannschaftende is not null "
		    +"or heimmannschaftverl is not null or gastmannschaftverl is not null "
		    +"or heimmannschaftelf is not null or gastmannschaftelf is not null "
		    +"or gelbekartenheim is not null or gelbekartengast is not null "
		    +"or gelbrotekartenheim is not null or gelbrotekartengast is not null "
		    +"or rotekartenheim is not null or rotekartengast is not null)";
		return datenAusDBholen(sql);
	}
	
	@Override
	public List<String[]> benutzerSammeln ()
	{
		sql="select benutzerid, nickname, gruppenname from benutzer order by benutzerid";
		
		return datenAusDBholen(sql);
	}
	
	@Override
	public List<String[]> spieleFuerRankingSammeln ()
	{
		sql="select * "+"from spiele "+"where datumuhrzeit < date_add(now(),interval -45 MINUTE) "
		    +"order by datumuhrzeit";
		
		return datenAusDBholen(sql);
	}
	
	@Override
	public List<String[]> tippsFuerRankingSammeln ()
	{
		sql="select * "+"from tipps "+"where spieleid IN ( "+"select spieleid "+"from spiele "
		    +"where datumuhrzeit < date_add(now(),interval -45 MINUTE) "+") "
		    +"order by benutzerid, spieleid";
		
		return datenAusDBholen(sql);
	}
	
	@Override
	public String rankingEintragen (String datum, List<WM2018Benutzer> benutzer)
	{
		if (connection!=null)
		{
			try
			{
				if (statement==null)
					statement=connection.createStatement();
				
				int platz=0;
				int vergleich=0;
				
				for (WM2018Benutzer tipper: benutzer)
				{
					if (vergleich!=tipper.getPunkte())
						platz++;
					
					vergleich=tipper.getPunkte();
					
					sql="INSERT INTO ranking (datum, benutzerid, punkte, platz) "+"VALUES ('"+datum+"',"
					    +tipper.getBenutzerID()+","+tipper.getPunkte()+","+platz+")";
					
					statement.addBatch(sql);
				}
				statement.executeBatch();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			return ("Ranking in die Datenbank eingetragen!\n");
		}
		else
			return ("Keine Verbindung!\n");
	}
	
	@Override
	public List<String[]> rankingAusgeben ()
	{
		sql="select r.datum, r.platz, b.nickname, r.punkte, b.gruppenname "
		    +"from ranking as r, benutzer as b	"
		    +"where datum = (select Max(datum) from ranking) and b.benutzerid = r.benutzerid "
		    +"order by r.platz, b.nickname";
		return datenAusDBholen(sql);
	}
	
	@Override
	public List<String[]> mannschaftenSammeln ()
	{
		sql="select distinct spielbezeichnung,heimmannschaft "
		    +"from spiele where spielbezeichnung like 'Gruppe%'";
		return datenAusDBholen(sql);
	}
	
	@Override
	public List<String[]> abgeschlosseneGruppenSpiele ()
	{
		sql="SELECT heimmannschaft, gastmannschaft, heimmannschaftende, gastmannschaftende, "
		    +"gelbekartenheim, gelbekartengast, gelbrotekartenheim, gelbrotekartengast, rotekartenheim, rotekartengast "
		    +"FROM spiele WHERE spielbezeichnung like 'Gruppe%' "+"AND datumuhrzeit < NOW() "
		    +"AND heimmannschaftende IS NOT NULL AND gastmannschaftende IS NOT NULL "
		    +"AND gelbekartenheim IS NOT NULL AND gelbekartengast IS NOT NULL "
		    +"AND gelbrotekartenheim IS NOT NULL AND gelbrotekartengast IS NOT NULL "
		    +"AND rotekartenheim IS NOT NULL AND rotekartengast IS NOT NULL ";
		return datenAusDBholen(sql);
	}
	
	@Override
	public List<String[]> direkterVergleich (WM2018Mannschaft heim, WM2018Mannschaft gast)
	{
		sql="SELECT heimmannschaft, heimmannschaftende, gastmannschaft, gastmannschaftende FROM spiele "
		    +"WHERE (heimmannschaftende IS NOT NULL AND gastmannschaftende IS NOT NULL) "
		    +"AND ((heimmannschaft = '"+heim.getNation()+"' AND gastmannschaft = '"+gast.getNation()
		    +"') "+"OR (heimmannschaft = '"+gast.getNation()+"' AND gastmannschaft = '"+heim.getNation()
		    +"'))";
		return datenAusDBholen(sql);
	}
	
	@Override
	public List<String[]> koSpieleSammeln ()
	{
		sql="select s.heimmannschaft, s.gastmannschaft "+"from spiele as s "+"where s.spieleid > 48";
		
		return datenAusDBholen(sql);
	}
	
	@Override
	public String koSpieleEintragen (String[] koSpiele)
	{
		if (connection!=null)
		{
			try
			{
				if (statement==null)
					statement=connection.createStatement();
				
				for (int index=0; index<koSpiele.length; index+=2)
				{
					sql="UPDATE spiele SET heimmannschaft='"+Hilfsfunktionen.nullTest(koSpiele[index])+"',"
					    +"gastmannschaft='"+Hilfsfunktionen.nullTest(koSpiele[index+1])+"' WHERE spieleid="
					    +(49+index/2);
					statement.addBatch(sql);
				}
				statement.executeBatch();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
			return ("Daten gespeichert!\n");
		}
		else
			return ("Keine Verbindung!\n");
	}
	
	@Override
	public String close ()
	{
		if (connection!=null)
		{
			try
			{
				connection.close();
				if (statement!=null)
					statement.close();
			}
			catch (SQLException e)
			{
				System.out.println("Fehler beim Beenden der Verbindung!\n");
			}
			return "Verbindung getrennt!\n";
		}
		else
			return "Es ist keine Verbindung vorhanden!\n";
	}
	
	private List<String[]> datenAusDBholen (String sql)
	{
		List<String[]> resultSet=new ArrayList<String[]>();
		if (connection!=null)
		{
			try
			{
				if (statement==null)
					statement=connection.createStatement();
				
				rs=statement.executeQuery(sql);
				
				int feldgroesse=rs.getMetaData().getColumnCount();
				
				while (rs.next())
				{
					String[] rsInhalte=new String[feldgroesse];
					for (int col=0; col<feldgroesse; col++)
					{
						rsInhalte[col]=rs.getString(col+1);
					}
					resultSet.add(rsInhalte);
				}
				rs.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		return resultSet;
	}
	
	public Statement getStatement ()
	{
		return statement;
	}
	
	public void setStatement (Statement statement)
	{
		this.statement=statement;
	}
	
	public Connection getConnection ()
	{
		return connection;
	}
	
	public void setConnection (Connection connection)
	{
		this.connection=connection;
	}
	
	public String getSql ()
	{
		return sql;
	}
	
	public void setSql (String sql)
	{
		this.sql=sql;
	}
	
	public ResultSet getRs ()
	{
		return rs;
	}
	
	public void setRs (ResultSet rs)
	{
		this.rs=rs;
	}
}
