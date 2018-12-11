package parser;

public class Game{
	Integer appID;
	String gameName;

	public Game(Integer appID, String gameName){
		this.appID = appID;
		this.gameName = gameName;
	}

	public String toString(){
		return appID + ": " + gameName;
	}
}