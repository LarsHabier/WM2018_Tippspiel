package wm.objekte;

public class WM2018Benutzer implements Comparable<WM2018Benutzer>
{
	String	benutzerID;
	String	nickname;
	String	gruppe;
	int			punkte;
	
	public WM2018Benutzer ( String benutzerID, String nickname, String gruppe )
	{
		this.benutzerID=benutzerID;
		this.nickname=nickname;
		this.gruppe=gruppe;
		this.punkte=0;
	}
	
	public int compareTo (WM2018Benutzer benutzer)
	{
		return benutzer.punkte-this.punkte;
	}
	
	public String getBenutzerID ()
	{
		return benutzerID;
	}
	
	public void setBenutzerID (String benutzerID)
	{
		this.benutzerID=benutzerID;
	}
	
	public String getNickname ()
	{
		return nickname;
	}
	
	public void setNickname (String nickname)
	{
		this.nickname=nickname;
	}
	
	public String getGruppe ()
	{
		return gruppe;
	}
	
	public void setGruppe (String gruppe)
	{
		this.gruppe=gruppe;
	}
	
	public int getPunkte ()
	{
		return punkte;
	}
	
	public void setPunkte (int punkte)
	{
		this.punkte=punkte;
	}
}
