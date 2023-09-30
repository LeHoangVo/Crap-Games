/**
This class represents a Player, which includes a name, bank balance, shooter status, bet amount, and pass completion status.
The player's name is provided as a parameter when a new Player object is created, and the initial bank balance is set to 100.
The shooter status is initially set to false, the bet amount is initially set to 0, and the pass completion status is initially set to false.
The class contains methods to get and set the player's name, bank balance, shooter status, bet amount, and pass completion status.
Additionally, the class contains a method to check if the player has been eliminated from the game, which is determined by having a bank balance of 0.

Name: Le Hoang Vo, Sam Nasser
Date: April 14th, 2022
*/
public class Die
{
	private int rollValue;
	
	public Die()
	{
		rollValue = 0;
	}
	
	public int getRollValue()
	{
		return rollValue;
	}
	
	public int rollDie()
	{
		rollValue = (int)(Math.random()*6+1);
		return rollValue;
	}
}