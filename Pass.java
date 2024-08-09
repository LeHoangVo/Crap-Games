/**
The Pass class represents a pass bet in a game of craps. It stores information
about the shooter ID, the amount bet on the pass, the amount of the pass that has
been covered, and whether or not the shooter has won. The class also includes
methods to get and set the values of these variables, as well as a method to settle
the bets for all players based on whether the shooter wins or loses.

Name: Le Hoang Vo, Sam Nasser
Date: April 14th, 2022
*/
import java.util.ArrayList;


public class Pass
{
	private int shooterID;
	private int actionAmount;
	private int actionAmountCovered;
	private boolean shooterWin;
	
	public Pass(int shooterId, int actionAmount,int actionAmountCovered)
	{
		this.shooterID = shooterId;
		this.actionAmount = actionAmount;
		this.actionAmountCovered = actionAmountCovered;
		this.shooterWin = false;
	}

	public int getShooterID()
	{
		return shooterID;
	}

	public void setShooterID(int index)
	{
		this.shooterID = index;
	}

	public int getActionAmount()
	{
		return actionAmount;
	}

	public void setActionAmount(int amount)
	{
		actionAmount = amount;
	}

	public int getActionAmountCovered()
	{
		return actionAmountCovered;
	}

	public void setActionAmountCovered(int amount)
	{
		actionAmountCovered = amount;
	}

	public boolean isShooterWinOrLose()
	{
		return shooterWin;
	}

	public void setShooterWinOrLose(boolean winOrLose)
	{
		shooterWin = winOrLose;
	}
	
	public void settleBets(boolean shooterWin, ArrayList<Player> playerList)
	{
		this.shooterWin = shooterWin;
		for(Player p: playerList)
		{
			if(p.getIsShooter())
			{
				if(shooterWin)
				{
					p.setBankBalance(p.getBankBalance() + this.getActionAmountCovered());
				}
				else
				{
					p.setBankBalance(p.getBankBalance() - this.getActionAmountCovered());
				}
			}
			else
			{
				if(shooterWin)
				{
					p.setBankBalance(p.getBankBalance() - p.getAmountBet());
				}
				
				else{
					p.setBankBalance(p.getBankBalance() + p.getAmountBet());
				}
			}
		}
		
	}
}
