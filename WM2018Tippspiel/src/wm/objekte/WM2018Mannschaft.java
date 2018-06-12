package wm.objekte;


public class WM2018Mannschaft implements Comparable<WM2018Mannschaft> {
	
	
	
	private String nation;
	private String matches;
	private String points;
	private int own_goals;
	private int conceded_goals;
	private int goals_difference;
	private String fairplay;
	
	WM2018Mannschaft(String nation){
		this.nation = nation;
	}

	public String getNation() {
		
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@Override
	public int compareTo(WM2018Mannschaft guest) {
		int score = 0;
		int points_g = Integer.parseInt(guest.getPoints());
		int points_this = Integer.parseInt(this.getPoints());
		int fairplay_g = Integer.parseInt(guest.getFairplay());
		int fairplay_this = Integer.parseInt(this.getFairplay());
		
		if(points_g == points_this) {
			if(fairplay_g < fairplay_this) {
				score = fairplay_g;
			}
			else {
				score = fairplay_this;
			}
		}
		else {
			score = points_g - points_this;
		}
		return score;
	}

	public String getMatches() {
		return matches;
	}

	public void setMatches(String matches) {
		this.matches = matches;
	}

	public String getPoints() {
		return points;
	}

	public void setPoints(String points) {
		this.points = points;
	}



	public int getGoals_difference() {
		int score = own_goals - conceded_goals;
		setGoals_difference(score);
		return goals_difference;
	}

	public void setGoals_difference(int goals_difference) {
		this.goals_difference = goals_difference;
	}

	public String getFairplay() {
		return fairplay;
	}

	public void setFairplay(String fairplay) {
		this.fairplay = fairplay;
	}

	public int getOwn_goals() {
		return own_goals;
	}

	public void setOwn_goals(int own_goals) {
		this.own_goals = own_goals;
	}

	public int getConceded_goals() {
		return conceded_goals;
	}

	public void setConceded_goals(int conceded_goals) {
		this.conceded_goals = conceded_goals;
	}

}
