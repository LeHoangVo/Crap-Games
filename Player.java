/**
This class represents a Player, which includes a name, bank balance, shooter status, bet amount, and pass completion status.
The player's name is provided as a parameter when a new Player object is created, and the initial bank balance is set to 100.
The shooter status is initially set to false, the bet amount is initially set to 0, and the pass completion status is initially set to false.
The class contains methods to get and set the player's name, bank balance, shooter status, bet amount, and pass completion status.
Additionally, the class contains a method to check if the player has been eliminated from the game, which is determined by having a bank balance of 0.

Name: Le Hoang Vo, Sam Nasser
Date: April 14th, 2022
*/
public class Player {
    private String name;
    private int bankBalance;
    private boolean isShooter;
    private int betAmount;
    private boolean passCompleted;

    public Player(String name) {
        this.name = name;
        this.bankBalance = 100;
        this.isShooter = false;
        this.betAmount = 0;
        this.passCompleted = false;
    }

    public String getName() {
        return name;
    }

    public int getBankBalance() {
        return bankBalance;
    }

    public void setBankBalance(int newBalance) {
        this.bankBalance = newBalance;
    }

    public boolean getIsShooter() {
        return isShooter;
    }

    public void setIsShooter(boolean isShooter) {
        this.isShooter = isShooter;
    }

    public int getAmountBet() {
        return betAmount;
    }

    public void setAmountBet(int betAmount) {
        this.betAmount = betAmount;
    }

    public boolean getPassCompleted() {
        return passCompleted;
    }

    public void setPassCompleted(boolean setPassCompleted) {
        this.passCompleted = setPassCompleted;
    }
    
    
    public boolean IsEliminated()
    {
        if(this.bankBalance == 0)
         	return true;
         return false;
    }
    
}
