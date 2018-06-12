package wm.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Hilfsfunktionen
{
	public static String[] leseConfig (String datei)
	{
		File file=new File(datei);
		String[] configDaten=new String[5];
		int index=0;
		
		if (!file.canRead()||!file.isFile())
		{
			configDaten[0]="Fehler - Datei nicht lesbar!";
		}
		
		BufferedReader in=null;
		try
		{
			in=new BufferedReader(new FileReader(datei));
			
			String zeile=null;
			while ((zeile=in.readLine())!=null)
			{
				configDaten[index++]=(zeile.substring(zeile.indexOf(":")+1));
			}
		}
		catch (IOException e)
		{
			configDaten[0]="Fehler beim Einlesen der Datei!";
		}
		finally
		{
			if (in!=null)
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					configDaten[0]="Fehler beim Schlieﬂen des Readers!";
				}
		}
		return configDaten;
	}
	
	public static String leerzeichen (int anzahl, String text)
	{
		String ergebnis="";
		for (int space=0; space<(anzahl-text.length()); space++)
			ergebnis+=" ";
		return ergebnis;
	}
	
	public static String datumWandeln (String datum)
	{
		SimpleDateFormat simpleDateFormatParse=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try
		{
			Date zeitstempel=simpleDateFormatParse.parse(datum);
			SimpleDateFormat simpleDateFormatAusgabe=new SimpleDateFormat("dd.MM.yyyy HH:mm");
			return simpleDateFormatAusgabe.format(zeitstempel)+" Uhr";
		}
		catch (ParseException e)
		{
			return datum;
		}
	}
	
	public static String nullTest (String pruefwert)
	{
		if (pruefwert!=null)
		{
			if (pruefwert.equals(""))
				return "null";
			else
				return pruefwert;
		}
		return "null";
	}
}
