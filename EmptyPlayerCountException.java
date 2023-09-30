/**

An exception class to handle situations where the count of players is empty or invalid.

This class extends the built-in Exception class in Java.

The class provides two constructors:

EmptyPlayerCountException() - to handle empty player count
EmptyPlayerCountException(Object name) - to handle empty player name
Both constructors call the parent constructor with a custom error message.

Name: Le Hoang Vo, Sam Nasser
Date: April 14th, 2022
*/
public class EmptyPlayerCountException extends Exception {
	
	EmptyPlayerCountException()
	{
		super("Enter number of players");
	}
	
	
	EmptyPlayerCountException(Object name)
	{
		super("Enter player's name");
	}

}
