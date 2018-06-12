package wm.gui.view;

import javafx.beans.property.SimpleStringProperty;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */
public class Match_Results {
	//Attribute für die TableView
	private SimpleStringProperty group;
	private SimpleStringProperty homeTeam;
	private SimpleStringProperty splitter;
	private SimpleStringProperty guestTeam;
	private SimpleStringProperty halfTimeScore;
	private SimpleStringProperty finalScore;
	private SimpleStringProperty extraTimeScore;
	private SimpleStringProperty penaltyScore;
	private SimpleStringProperty yellowCards;
	private SimpleStringProperty redCards;
	
	
	public Match_Results(String group, String homeTeam,String splitter, String guestTeam, String halfTimeScore, String finalScore, String extraTimeScore, String penaltyScore, String yellowCards, String redCards) {
		this.group = new SimpleStringProperty(group);
		this.homeTeam = new SimpleStringProperty(homeTeam);
		this.splitter = new SimpleStringProperty(splitter);
		this.guestTeam = new SimpleStringProperty(guestTeam);
		this.halfTimeScore = new SimpleStringProperty(halfTimeScore);
		this.finalScore = new SimpleStringProperty(finalScore);
		this.extraTimeScore = new SimpleStringProperty(extraTimeScore);
		this.penaltyScore = new SimpleStringProperty(penaltyScore);
		this.yellowCards = new SimpleStringProperty(yellowCards);
		this.redCards = new SimpleStringProperty(redCards);
	}
	
	public String getGroup() {
        return group.get();
    }
    public void setGroup(String group) {
        this.group.set(group);
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
    public String getHalfTimeScore() {
        return halfTimeScore.get();
    }
    public void setHalfTimeScore(String halfTimeScore) {
        this.halfTimeScore.set(halfTimeScore);
    }
    public String getFinalScore() {
        return finalScore.get();
    }
    public void setFinalScore(String finalScore) {
        this.finalScore.set(finalScore);
    }
    public String getExtraTimeScore() {
        return extraTimeScore.get();
    }
    public void setExtraTimeScore(String extraTimeScore) {
        this.extraTimeScore.set(extraTimeScore);
    }
    public String getPenaltyScore() {
        return penaltyScore.get();
    }
    public void setPenaltyScore(String penaltyScore) {
        this.penaltyScore.set(penaltyScore);
    }
    public String getYellowCards() {
        return yellowCards.get();
    }
    public void setYellowCards(String yellowCards) {
        this.yellowCards.set(yellowCards);
    }
    public String getRedCards() {
        return redCards.get();
    }
    public void setRedCards(String redCards) {
        this.redCards.set(redCards);
    }
	

}
