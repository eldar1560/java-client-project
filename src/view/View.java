package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;
/**
 * The view Interface , set the view functionals to the machine from the cli and starts the program.
 * @author Eldar , Ofek
 *
 */
public interface View {
	/**
	 * starts the program
	 */
	void start();
	/**
	 * display the solution to the out file.
	 * @param sol the solution we want to display
	 */
	void displayMessage(Solution<Position> sol);
	/**
	 * display the maze to the out file.
	 * @param maze the maze we want to display
	 */
	void displayMessage(Maze3d maze);
	/**
	 * display the message to the out file.
	 * @param message the message you want to display
	 */
	void displayMessage(String message);
	/**
	 * set the hash commands that we will send to the cli 
	 * @param hc the full hash command
	 */
	void setHashCommand(HashMap<String,Command> hc);
	/**
	 * send notification to the presenter with the string[] that we got
	 * @param str
	 */
	void notifyMe(String[] str);
	/**
	 * send notification to the presenter with the string that we got
	 * @param str
	 */
	void notifyMe(String str);
	/**
	 * send notification message by properties 
	 * @param properties
	 */
	void notifyMe(Properties properties);
}
