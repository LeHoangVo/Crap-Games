import java.util.ArrayList;

/**
This class represents a Game, which includes a total pot amount and a list of players.
The total pot amount is initially set to 0, and the player list is initialized as an empty ArrayList.
The class contains methods to get the total pot amount and player list, as well as to check for a game winner or loser.
If a player's bank balance matches the total pot amount, they are considered the winner and their name is returned.
If a player's bank balance reaches 0, they are considered the loser and their name is returned.

Name: Le Hoang Vo, Sam Nasser
Date: April 14th, 2022
*/

public class Game{
	private int totalPotAmount;
	private  ArrayList<Player> playerList;
	
	public Game()
	{
		this.totalPotAmount = 0;
		this.playerList = new ArrayList<Player>();	
	}

	public int getTotalPotAmount() {
		totalPotAmount = playerList.size()*100;
		return totalPotAmount;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public String checkForGameWinner()
	{
		for(Player player: playerList)
		{
			if(player.getBankBalance() == getTotalPotAmount()){
				//Game over, winner found
				//Display winner's name on GUI
				return player.getName();
			}
		}
		return null;		
		
	}
	
	public String checkForGameLoser()
	{
		for (Player player: playerList)
		{
			if(player.getBankBalance() == 0)
			{
				return player.getName();
			}
		}
		return null;
	}
		
	
}
