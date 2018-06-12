package wm.objekte;

// Diese Klasse erstellt ein Objekt Configuration, das die benötigten Daten zur Anmeldung an die Datenbank hält

public class Configuration
{
	private String	ip;
	private String	db;
	private String	port;
	private String	user;
	private String	pass;
	
	public Configuration ( String[] configDaten )
	{
		
		this.ip=configDaten[0];
		this.db=configDaten[1];
		this.port=configDaten[2];
		this.user=configDaten[3];
		this.pass=configDaten[4];
	}
	
	public String getIp ()
	{
		return ip;
	}
	
	public void setIp (String ip)
	{
		this.ip=ip;
	}
	
	public String getDb ()
	{
		return db;
	}
	
	public void setDb (String db)
	{
		this.db=db;
	}
	
	public String getPort ()
	{
		return port;
	}
	
	public void setPort (String port)
	{
		this.port=port;
	}
	
	public String getUser ()
	{
		return user;
	}
	
	public void setUser (String user)
	{
		this.user=user;
	}
	
	public String getPass ()
	{
		return pass;
	}
	
	public void setPass (String pass)
	{
		this.pass=pass;
	}
}
