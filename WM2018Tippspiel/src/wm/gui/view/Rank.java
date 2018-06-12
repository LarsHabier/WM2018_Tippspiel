package wm.gui.view;

import javafx.beans.property.SimpleStringProperty;
/**
 * 
 * @author Lars Habier, Gerrit Schulz, Felix Tenbruck
 *
 */
public class Rank {
	//Attribute für die TableView
	private SimpleStringProperty rank;
	private SimpleStringProperty user;
	private SimpleStringProperty points;
	private SimpleStringProperty group;
	
	public Rank(String rank, String user, String points, String group) {
		this.rank = new SimpleStringProperty(rank);
		this.user = new SimpleStringProperty(user);
		this.points = new SimpleStringProperty(points);
		this.group = new SimpleStringProperty(group);
	}
	
	public String getRank() {
        return rank.get();
    }
    public void setRank(String rank) {
        this.rank.set(rank);
    }
    public String getUser() {
        return user.get();
    }
    public void setUser(String user) {
        this.user.set(user);
    }
    public String getPoints() {
        return points.get();
    }
    public void setPoints(String points) {
        this.points.set(points);
    }
    public String getGroup() {
        return group.get();
    }
    public void setGroup(String group) {
        this.group.set(group);
    }

}
