
/**

This program creates a graphical user interface (GUI) for the game of craps.
The GUI is built using Java Swing components, and allows the user to interact
with the game through a graphical interface.
The game of craps is a dice game where players make bets on the outcome of rolls
of two six-sided dice. The game has a set of rules for determining whether a player
wins or loses on each roll, and the objective is to win all the money in the pot.
This program uses Java classes and methods to implement the game logic, and
incorporates event listeners to respond to user input and update the GUI
accordingly. The GUI includes buttons for the player to place bets and roll
the dice, as well as displays for the current game status and the player's
balance and bet amount.

Name: Le Hoang Vo, Sam Nasser
Date: April 14th, 2022 
*/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


/*
 * This class is the opening GUI for players to enter how many
 * are going to play and the names of each player
 */
public class StartGUI extends JFrame {

	private JButton confirm;
	private JTextField numPlayers;
	private JLabel msg, img;
	private boolean isEnterPlayerNum = true;
	private boolean isEnterPlayerName = false;
	private JPanel p1;
	private ImageIcon imageIcon;
	private static int actionAmount = 0;
	private static int acAmount = 0;
	private static Game g1 = new Game();
	private static GameGUI gamegui;
	private static Pass pass;
	private static int playerTurn = 0;
	private static int playerIndex = 0;
	private static int shooterIndex = 0;

	public StartGUI() {
		super("Welcome to Craps");
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE); // exits program when x is clicked
		this.setLocationRelativeTo(null); // shows up on center of screen
		this.setLayout(new BorderLayout());
		p1 = new JPanel(new BorderLayout());
		img = new JLabel();
		imageIcon = new ImageIcon("src/images/craps-casino-game-new.jpg");
		msg = new JLabel("<html><div style='text-align: center;'>Hello, welcome!<br>Lets start the game!"
				+ "<br>Please enter number of players (Min: 2, Max: 6)</div></html>");
		msg.setHorizontalAlignment(SwingConstants.CENTER);
		confirm = new JButton("Confirm");
		numPlayers = new JTextField();
		img.setIcon(imageIcon);
		this.add(img, BorderLayout.CENTER);
		this.add(p1, BorderLayout.SOUTH);
		// this.add(confirm,BorderLayout.SOUTH);
		p1.add(confirm, BorderLayout.SOUTH);
		p1.add(numPlayers, BorderLayout.NORTH);
		this.add(msg, BorderLayout.NORTH);
		// numPlayers.setPreferredSize(new Dimension(150, 30));
		// this.add(numPlayers,BorderLayout.SOUTH);

		confirm.addActionListener(new ActionListener() {
			public int num;
			// Game g1 = new Game();

			public void actionPerformed(ActionEvent e) {
				try {

					if (isEnterPlayerNum) {
						if (numPlayers.getText().isEmpty()) {
							throw new EmptyPlayerCountException();
						}
						num = Integer.parseInt(numPlayers.getText());

						if (num < 2 || num > 6) {
							JOptionPane.showMessageDialog(new JFrame(), "Please enter number between 2 and 6.", "ERROR",
									JOptionPane.ERROR_MESSAGE);
							numPlayers.setText("");
						} else {
							isEnterPlayerNum = false;
							numPlayers.setText("");
							isEnterPlayerName = true;

						}
					}

				} catch (NumberFormatException err) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter numbers only!", "ERROR",
							JOptionPane.ERROR_MESSAGE);
					numPlayers.setText("");

				} catch (Exception err) {
					JOptionPane.showMessageDialog(new JFrame(), "Please enter a number", "ERROR",
							JOptionPane.ERROR_MESSAGE);
				}

				if (isEnterPlayerName) {

					for (int i = 1; i <= num; i++) {
						String name = JOptionPane.showInputDialog(new JFrame(),
								"Please enter name of player " + i + ".");
						try {
							if (name.isEmpty())
								;
							Player player = new Player(name);
							g1.getPlayerList().add(player);
							if (i == num) {
								gamegui = new GameGUI(g1);
								setVisible(false);
							}
						} catch (NullPointerException err) {
							JOptionPane.showMessageDialog(new JFrame(), "Please enter a name", "ERROR",
									JOptionPane.ERROR_MESSAGE);
							--i;
						}

					}

				}
			}
		});

		this.setVisible(true);
	}

	public class GameGUI extends JFrame {
		private JMenuBar bar;
		private JMenu menuOption;
		private JMenuItem instruction, about;
		private ArrayList<JPanel> playerRep;
		private JPanel p1, p2;
		private JButton rollBut, passBut, shootBut, placebetBut;
		private JLabel pStatus, betStatus, dieStatus;
		private JTextField betStatusNo, die1, die2;
		private JLabel rollSum1, rollSum2;
		private ArrayList<JTextField> amounts;
		private ArrayList<JTextField> bankBalances;
		private ArrayList<JTextField> shooters;
		private ArrayList<JLabel> eliminated;
		private JLabel elim;
		private static int firstRoll = 0;

		public GameGUI(Game g) {
			super("CRAPS");
			this.setSize(700, 700);
			this.setDefaultCloseOperation(EXIT_ON_CLOSE); // exits program when x is clicked
			this.setLocationRelativeTo(null); // shows up on center of screen
			this.setLayout(new GridLayout(0, 1, 0, 0));
			pStatus = new JLabel("<html><p style='font-size: 200%; " + "font-family: georgia; "
					+ "color: red '><b>Welcome Players!</b></p><html>");

			buildJMenuBar();
			this.setJMenuBar(bar);

			buildPlayerPanels(g);

			for (JPanel p : playerRep) {
				this.add(p);
			}
			pStatus.setHorizontalAlignment(SwingConstants.CENTER);
			this.add(pStatus);

			buildActionBox();
			this.add(p2);

			buildButtonPanel(g);
			this.add(p1);

			this.setVisible(true);
		}
		/**
			buildActionBox()
			+)This method builds an action box GUI component containing various elements like bet status, die rolls, and their sum.
			+)It creates a JPanel object, adds a JLabel and a JTextField for bet status, and adds two JTextFields and some JLabels for
			displaying the dice rolls and their sum. The bet status JTextField is initialized with "0" and set as uneditable. 
			+)The dice JTextFields are also set as uneditable and initialized with the result of the Die class's getRollValue() method.
			+)The various components are added to the JPanel object.
		*/ 
		public void buildActionBox() {
			p2 = new JPanel();
			betStatus = new JLabel("<html><p style='font-size: 120%; " + "font-family: georgia; "
					+ "color: blue '><b>Action Amount: </b></p><html>");
			betStatus.setHorizontalAlignment(SwingConstants.CENTER);
			p2.add(betStatus);
			betStatusNo = new JTextField("0");
			betStatusNo.setEditable(false);
			betStatusNo.setColumns(4);
			p2.add(betStatusNo);

			Die start = new Die();
			dieStatus = new JLabel("Roll Numbers:");
			die1 = new JTextField();
			die2 = new JTextField();
			die1.setEditable(false);
			die2.setEditable(false);
			rollSum1 = new JLabel("=");
			rollSum2 = new JLabel("");

			die1.setText("" + start.getRollValue());
			die2.setText("" + start.getRollValue());
			p2.add(dieStatus);
			p2.add(die1);
			p2.add(die2);
			p2.add(rollSum1);
			p2.add(rollSum2);
		}
		
		
		/**
			buildJMenuBar()
			+)This method builds a JMenuBar GUI component with a "Options" JMenu containing "Instruction" and "About" JMenuItems.
			+)It creates a JMenuBar object and a JMenu object named "Options". It also creates two JMenuItem objects, "Instruction" and 
				"About". An ActionListener is added to both JMenuItem objects. Then, the "Instruction" and "About" JMenuItems are 
				added to the "Options" JMenu. Finally, the "Options" JMenu is added to the JMenuBar object.
		*/
		public void buildJMenuBar() {
			bar = new JMenuBar();
			menuOption = new JMenu("Options");

			instruction = new JMenuItem("Instruction");
			about = new JMenuItem("About");

			instruction.addActionListener(new MenuListener());
			about.addActionListener(new MenuListener());

			menuOption.add(instruction);
			menuOption.add(about);

			bar.add(menuOption);
		}
		
		/**
			buildPlayerPanels(Game g)
			+)Builds player panels for the game GUI, displaying information about each player including their name,
			bank balance, shooter status, bet amount, and whether they have been eliminated from the game. 
			+)The method takes a Game object as a parameter to access the player list. 
			+)Player information is displayed in a JPanel for each player,which are stored in an ArrayList for later access.
		*/
		public void buildPlayerPanels(Game g) {
			JLabel playersTitle = new JLabel("Players in Game");
			playerRep = new ArrayList<JPanel>();
			amounts = new ArrayList<JTextField>();
			bankBalances = new ArrayList<JTextField>();
			shooters = new ArrayList<JTextField>();
			eliminated = new ArrayList<JLabel>();
			int shooterIndex = (int) (Math.random() * g.getPlayerList().size());

			int pCount = 0;
			for (Player p : g.getPlayerList()) {
				pCount++;
				JPanel data = new JPanel();

				// Add name label and player's name
				data.add(new JLabel("Player " + pCount + ": "));
				JTextField name1 = new JTextField();
				name1.setText(p.getName());
				name1.setEditable(false);
				data.add(name1);
				// Add bank balance label and player's bank balance
				data.add(new JLabel("Balance: "));

				JTextField bankBalance1 = new JTextField(Integer.toString(p.getBankBalance()));
				bankBalance1.setEditable(false);
				bankBalances.add(bankBalance1);
				data.add(bankBalance1);

				// Add shooter label and player's shooter info
				data.add(new JLabel("Shooter: "));

				if (shooterIndex == 0) {
					p.setIsShooter(true);
				}
				JTextField isShooter1;
				if (p.getIsShooter()) {
					isShooter1 = new JTextField("Yes");
					isShooter1.setEditable(false);
				} else {
					isShooter1 = new JTextField("No");
					isShooter1.setEditable(false);
				}
				shooters.add(isShooter1);
				data.add(isShooter1);

				// Add bet amount label and player's bet amount
				data.add(new JLabel("Bet Amount: "));
				JTextField betAmount1 = new JTextField(Integer.toString(p.getAmountBet()));
				betAmount1.setColumns(8);
				if (!p.getIsShooter()) {
					betAmount1.setEditable(false);
				}
				amounts.add(betAmount1);
				data.add(betAmount1);
				
				// Add eliminated label
				elim = new JLabel("");
				eliminated.add(elim);
				data.add(elim);
				
				shooterIndex--;
				playerRep.add(data);
			}

		}
		/**
			The code is a part of a game and the function is creating a panel that contains four buttons to interact with the game: 
			Roll, Place Bet, Shoot, and Pass. The function also sets the buttons to be disabled by default, except for the Place Bet button.
			The actionPerformed method for the Roll button checks if it is the first roll, sets the firstRoll variable accordingly, 
			and then enables the Shoot and Pass buttons. It also updates the dice and bank balances based on the game rules, 
			and displays a message to the player indicating whether the shooter won or lost the round.
		*/
		
		public void buildButtonPanel(Game g) {
			p1 = new JPanel();
			p1.setLayout(new GridLayout(2, 2, 5, 10));
			p1.add(rollBut = new JButton("Roll"));
			p1.add(placebetBut = new JButton("Place Bet"));
			p1.add(shootBut = new JButton("Shoot"));
			p1.add(passBut = new JButton("Pass"));
			passBut.setEnabled(false);
			shootBut.setEnabled(false);
			rollBut.setEnabled(false);

			rollBut.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getActionCommand() == "Roll") {
						Die die = new Die();
						int sum = 0;
						int roll1 = die.rollDie();
						die1.setText("" + roll1);
						int roll2 = die.rollDie();
						die2.setText("" + roll2);
						if (firstRoll == 0)
							firstRoll = roll1 + roll2;
						else{
							sum = roll1 + roll2;
						}
						if (firstRoll == 7 || firstRoll == 11) {
							rollSum1.setText("First roll: " + firstRoll);
							for (int i = 0; i < g.getPlayerList().size(); i++) {
								if (g.getPlayerList().get(i).getIsShooter()) {
									shooterIndex = i;
								}
							}

							int playerBet = 0;
							int playerBankAmount = 0;
							int shooterBank = 0;
							for (int i = 0; i < g.getPlayerList().size(); i++) {
								if(g.getPlayerList().get(i).IsEliminated())
										continue;
								if (shooterIndex != i) {
									playerBet = g.getPlayerList().get(i).getAmountBet();
									playerBankAmount = g.getPlayerList().get(i).getBankBalance();
									shooterBank = g.getPlayerList().get(shooterIndex).getBankBalance();
									g.getPlayerList().get(i).setBankBalance(playerBankAmount - playerBet);
									g.getPlayerList().get(shooterIndex).setBankBalance(shooterBank + playerBet);
								}
							}

							for (int i = 0; i < g.getPlayerList().size(); i++) {
								bankBalances.get(i).setText("" + g.getPlayerList().get(i).getBankBalance());
							}
							firstRoll=0;
							rollBut.setEnabled(false);
							shootBut.setEnabled(true);
							passBut.setEnabled(true);
							pStatus.setText("<html><p style='font-size: 200%; " + "font-family: georgia; "
									+ "color: red '><b>Pass or Shoot!</b></p><html>");
							JOptionPane.showMessageDialog(new JFrame(), "Shooter Won",
								"Result End of Round", JOptionPane.INFORMATION_MESSAGE);
							
						} else if (firstRoll == 2 || firstRoll == 3 || firstRoll == 12) {
							rollSum1.setText("First roll: " + firstRoll);
							for (int i = 0; i < g.getPlayerList().size(); i++) {
								if (g.getPlayerList().get(i).getIsShooter()) {
									shooterIndex = i;
								}
							}

							int shooterBank=0;
							for (int i = 0; i < g.getPlayerList().size(); i++) {
								if(g.getPlayerList().get(i).IsEliminated())
										continue;
								if (shooterIndex != i) {
									int playerBet = g.getPlayerList().get(i).getAmountBet();
									int playerBankAmount = g.getPlayerList().get(i).getBankBalance();
									shooterBank = g.getPlayerList().get(shooterIndex).getBankBalance();
									g.getPlayerList().get(i).setBankBalance(playerBankAmount + playerBet);
								}
							}
							g.getPlayerList().get(shooterIndex).setBankBalance(shooterBank - acAmount);
							for (int i = 0; i < g.getPlayerList().size(); i++) 
								bankBalances.get(i).setText("" + g.getPlayerList().get(i).getBankBalance());
								
							firstRoll=0;
							rollBut.setEnabled(false);
							shootBut.setEnabled(true);
							passBut.setEnabled(true);
							pStatus.setText("<html><p style='font-size: 200%; " + "font-family: georgia; "
									+ "color: red '><b>Pass or Shoot!</b></p><html>");
							JOptionPane.showMessageDialog(new JFrame(), "Shooter Lost",
									"Result End of Round", JOptionPane.INFORMATION_MESSAGE);

						} else {
							rollSum1.setText("First roll: " + firstRoll);
							if (sum == firstRoll) {
								rollSum2.setText("Next roll: " + sum);
								for (int i = 0; i < g.getPlayerList().size(); i++) {
									if (g.getPlayerList().get(i).getIsShooter()) {
										shooterIndex = i;
									}
								}
								int shooterBank = 0;
								for (int i = 0; i < g.getPlayerList().size(); i++) {
									if(g.getPlayerList().get(i).IsEliminated())
										continue;
									if (shooterIndex != i) {
										int bet = g.getPlayerList().get(i).getAmountBet();
										int bankAmount = g.getPlayerList().get(i).getBankBalance();
										shooterBank = g.getPlayerList().get(shooterIndex).getBankBalance();
										g.getPlayerList().get(i).setBankBalance(bankAmount - bet);
										g.getPlayerList().get(shooterIndex).setBankBalance(shooterBank + bet);
									}
								}
								

								for (int i = 0; i < g.getPlayerList().size(); i++) {
									bankBalances.get(i).setText("" + g.getPlayerList().get(i).getBankBalance());
								}
								firstRoll=0;
								rollBut.setEnabled(false);
								shootBut.setEnabled(true);
								passBut.setEnabled(true);
								pStatus.setText("<html><p style='font-size: 200%; " + "font-family: georgia; "
										+ "color: red '><b>Pass or Shoot!</b></p><html>");
								JOptionPane.showMessageDialog(new JFrame(), "Shooter Won",
										"Result End of Round", JOptionPane.INFORMATION_MESSAGE);
								
							} else if (sum == 7) {
								rollSum2.setText("Next roll: " + sum);
								for (int i = 0; i < g.getPlayerList().size(); i++) {
									if (g.getPlayerList().get(i).getIsShooter()) {
										shooterIndex = i;
									}
								}
								int shooterBank = 0;
								for (int i = 0; i < g.getPlayerList().size(); i++) {
									if(g.getPlayerList().get(i).IsEliminated())
										continue;
									if (shooterIndex != i) {
										int playerBet = g.getPlayerList().get(i).getAmountBet();
										int playerBankAmount = g.getPlayerList().get(i).getBankBalance();
										shooterBank = g.getPlayerList().get(shooterIndex).getBankBalance();
										g.getPlayerList().get(i).setBankBalance(playerBankAmount + playerBet);
									}
								}
								g.getPlayerList().get(shooterIndex).setBankBalance(shooterBank - acAmount);

								for (int i = 0; i < g.getPlayerList().size(); i++) {
									bankBalances.get(i).setText("" + g.getPlayerList().get(i).getBankBalance());
								}
								firstRoll=0;
								rollBut.setEnabled(false);
								shootBut.setEnabled(true);
								passBut.setEnabled(true);
								pStatus.setText("<html><p style='font-size: 200%; " + "font-family: georgia; "
										+ "color: red '><b>Pass or Shoot!</b></p><html>");
								JOptionPane.showMessageDialog(new JFrame(), "Shooter Lost",
										"Result End of Round", JOptionPane.INFORMATION_MESSAGE);
							}
							else if(sum != 0){
								rollSum2.setText("Next roll: " + sum);
							}

						}
						g.getTotalPotAmount();
						String winner = g.checkForGameWinner();
						if(winner != null)
						{
							JOptionPane.showMessageDialog(new JFrame(), "The winner is " + winner + ".Game is now"
									+ " over.",	"WINNER!", JOptionPane.INFORMATION_MESSAGE);
							setVisible(false);
							System.exit(0);
						}
						
						int j = 0; 
						for(Player player: g.getPlayerList())
						{
							if(player.IsEliminated())
							{
								JOptionPane.showMessageDialog(new JFrame(), "Player " + player.getName() + " is now ELIMINATED!",
										"ELIMINATED!", JOptionPane.INFORMATION_MESSAGE);
								eliminated.get(j).setText("ELIMINATED!");
								if(player.getIsShooter()){
									shootBut.setEnabled(false);
								}
							}
							j++;
						}
						
					}

				}
			});	

			placebetBut.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if (playerTurn < g.getPlayerList().size()) {
						if (actionAmount == 0) {
							int i = 0;
							for (Player p : g.getPlayerList()) {
								if (p.getIsShooter()) {
									playerIndex = i;
									p.setAmountBet(Integer.parseInt(amounts.get(i).getText()));
									String bet = "";
									while (p.getAmountBet() > p.getBankBalance()) {
										JOptionPane.showMessageDialog(new JFrame(), "Bet amount exceeds bank balance!",
												"ERROR", JOptionPane.ERROR_MESSAGE);
										bet = JOptionPane.showInputDialog(new JFrame(), "Please enter new bet.");
										p.setAmountBet(Integer.parseInt(bet));
									}

									amounts.get(i).setText("" + p.getAmountBet());
									actionAmount = p.getAmountBet();
									acAmount = actionAmount;

									betStatusNo.setText("" + actionAmount);
									amounts.get(i).setEditable(false);
									playerIndex = (i + 1) % (g.getPlayerList().size());
									amounts.get(playerIndex).setEditable(true);
								}

								pStatus.setText("<html><p style='font-size: 200%; " + "font-family: georgia; "
										+ "color: red '><b>Next Player Enter Bet!</b></p><html>");
								i++;
							}
						}

						else {
							if (g.getPlayerList().get(playerIndex).getBankBalance() == 0) {
								while (g.getPlayerList().get(playerIndex).getBankBalance() == 0) {
									playerTurn++;
									amounts.get(playerIndex).setEditable(false);
									JOptionPane.showMessageDialog(new JFrame(), "Player " + playerIndex + " is ELIMINATED!",
											"ELIMINATED!", JOptionPane.INFORMATION_MESSAGE);
									eliminated.get(playerIndex).setText("ELIMINATED!");
									playerIndex = (playerIndex + 1) % (g.getPlayerList().size());
									if (playerTurn != g.getPlayerList().size() - 1)
										amounts.get(playerIndex).setEditable(true);
								}
								playerTurn--;
							} else {
								Player p = g.getPlayerList().get(playerIndex);
								String bet = "";
								p.setAmountBet(Integer.parseInt(amounts.get(playerIndex).getText()));
								while (p.getAmountBet() > p.getBankBalance() || p.getAmountBet() > actionAmount) {
									JOptionPane.showMessageDialog(new JFrame(),
											"Bet amount exceeds bank balance/action amount!", "ERROR",
											JOptionPane.ERROR_MESSAGE);
									bet = JOptionPane.showInputDialog(new JFrame(), "Please enter new bet.");
									p.setAmountBet(Integer.parseInt(bet));
									amounts.get(playerIndex).setText(bet);
								}
								actionAmount -= p.getAmountBet();
								betStatusNo.setText("" + actionAmount);

								amounts.get(playerIndex).setEditable(false);
								playerIndex = (playerIndex + 1) % (g.getPlayerList().size());

								if (playerTurn != g.getPlayerList().size() - 1) {
									amounts.get(playerIndex).setEditable(true);
								}
							}

						}

					}

					playerTurn++;
					if (actionAmount == 0&& playerTurn>0) {
						playerTurn = g.getPlayerList().size();
						for(JTextField amount: amounts)
						{
							amount.setEditable(false);
						}
					}
					if (playerTurn == g.getPlayerList().size()) {
						pStatus.setText("<html><p style='font-size: 200%; " + "font-family: georgia; "
								+ "color: red '><b>Shooter, please roll!</b></p><html>");
						rollBut.setEnabled(true);
						placebetBut.setEnabled(false);
						playerIndex = (playerIndex + 1) % (g.getPlayerList().size());
						for(JTextField amount: amounts)
						{
							amount.setEditable(false);
						}
					}
				}
			});

			shootBut.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					g.getPlayerList().get(playerIndex).setIsShooter(true);
					shootBut.setEnabled(false);
					passBut.setEnabled(false);
					placebetBut.setEnabled(true);
					playerTurn = 0;
					for(JTextField amount: amounts)
					{
						amount.setEditable(false);
						amount.setText("0");
						rollSum2.setText("");
						rollSum1.setText("=");
						die1.setText("");
						die2.setText("");
					}
					amounts.get(shooterIndex).setEditable(true);
					pStatus.setText("<html><p style='font-size: 200%; " + "font-family: georgia; "
							+ "color: red '><b>Shooter, please place bet Amount!</b></p><html>");
				}
			});

			passBut.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					g.getPlayerList().get(shooterIndex).setIsShooter(false);
					for(JTextField amount: amounts)
					{
						amount.setText("0");
					}
					playerIndex = (playerIndex + 1) % (g.getPlayerList().size());
					shooterIndex = (shooterIndex + 1) % (g.getPlayerList().size());
					while (g.getPlayerList().get(playerIndex).IsEliminated()) {
						shooterIndex = (shooterIndex + 1) % (g.getPlayerList().size());
						playerIndex = (playerIndex + 1) % (g.getPlayerList().size());
					}
					g.getPlayerList().get(shooterIndex).setIsShooter(true);
					for(JTextField s:shooters)
					{
						s.setText("No");
					}
					shooters.get(shooterIndex).setText("Yes");
					for(JTextField amount: amounts)
					{
						amount.setEditable(false);
						rollSum2.setText("");
						rollSum1.setText("=");
						die1.setText("");
						die2.setText("");
					}
					playerTurn = 0;
					amounts.get(shooterIndex).setEditable(true);
					shootBut.setEnabled(false);
					passBut.setEnabled(false);
					placebetBut.setEnabled(true);
					pStatus.setText("<html><p style='font-size: 200%; " + "font-family: georgia; "
							+ "color: red '><b>Shooter chose to pass the die!</b></p><html>");
				}
			});

		}
		/*
		 *These are the menu buttons that display the instructions as well as  
		 * the authors of the program.
		 */
		private class MenuListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "Instruction") {
					JOptionPane.showMessageDialog(new JFrame(), "<html><div>Instruction:"
							+ "<p>1.The game begins with a come-out roll, which is the first roll of the dice in a betting round.</p>"
							+ "<p>2.If the come-out roll is a 7 or an 11, this is called a natural, and the pass line bet wins. If the come-out roll is a 2, 3, or 12, this is called craps, and the pass line bet loses.</p>"
							+ "<p>3.If the come-out roll is any other number (4, 5, 6, 8, 9, or 10), this number becomes the point, and the game continues with additional rolls until either the point is rolled again<br>(which is called making the point and the pass line bet wins) or a 7 is rolled (which is called sevening out and the pass line bet loses).</p>"
							+ "<p>4.Players can make additional bets on various outcomes, such as whether the shooter will roll a certain number before a 7 or whether the total of the two dice will be an odd or even number.</p>"
							+ "<p>5.There are also rules for handling the dice, such as the requirement to hit the back wall of the table when rolling and to use only one hand to throw the dice.</p></div></html>",
							"Instruction", JOptionPane.NO_OPTION);
				} else if (e.getActionCommand() == "About") {
					JOptionPane.showMessageDialog(new JFrame(),
							"This program is created by Sam Nasser and Le Hoang Vo!", "About", JOptionPane.NO_OPTION);
				}
			}
		}

	}

	public static void main(String[] args) {

		StartGUI main = new StartGUI();

	}

}
