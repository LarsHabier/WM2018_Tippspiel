package wm.gui.view;

import javafx.beans.property.SimpleStringProperty;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */
public class Schedule {
	//Attribute für die TableView.
	private SimpleStringProperty group;
	private SimpleStringProperty date;
	private SimpleStringProperty startTime;
	private SimpleStringProperty homeTeam;
	private SimpleStringProperty splitter;
	private SimpleStringProperty guestTeam;
	private SimpleStringProperty venue;
	
	public Schedule(String group, String date, String startTime,String homeTeam,String splitter, String guestTeam, String venue) {
		this.group = new SimpleStringProperty(group);
		this.date = new SimpleStringProperty(date);
		this.startTime = new SimpleStringProperty(startTime);
		this.homeTeam = new SimpleStringProperty(homeTeam);
		this.splitter = new SimpleStringProperty(splitter);
		this.guestTeam = new SimpleStringProperty(guestTeam);
		this.venue = new SimpleStringProperty(venue);
	}
	
	public String getGroup() {
        return group.get();
    }
    public void setGroup(String group) {
        this.group.set(group);
    }

	public String getDate() {
        return date.get();
    }
    public void setDate(String date) {
        this.date.set(date);
    }
    
    public String getStartTime() {
        return startTime.get();
    }
    public void setStartTime(String startTime) {
        this.startTime.set(startTime);
    }
    public String getHomeTeam() {
        return homeTeam.get();
    }
    public void setHomeTeam(String homeTeam) {
        this.homeTeam.set(homeTeam);
    }
    public String getSplitter() {
        return splitter.get();
    }
    public void setSplitter(String splitter) {
        this.splitter.set(splitter);
    }
    public String getGuestTeam() {
        return guestTeam.get();
    }
    public void setGuestTeam(String guestTeam) {
        this.guestTeam.set(guestTeam);
    }
    public String getVenue() {
        return venue.get();
    }
    public void setVenue(String venue) {
        this.venue.set(venue);
    }

}
