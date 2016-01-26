package view;

import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
/**
 * set the functional of user interface
 * @author Eldar , Ofek
 */
public interface UserChoice {
	/**
	 * set the view
	 * @param view
	 */
	void setView(View view);
	/**
	 * start the thread
	 */
	void start();
	/**
	 * display the message
	 * @param message
	 */
	void setMessage(String message);
	/**
	 * set commands into hash map
	 * @param hashCommand
	 */
	void setHashCommand(HashMap<String, Command> hc);
	/**
	 * display the maze
	 * @param maze
	 */
	void setMessage(Maze3d maze);
	/**
	 * display the	sol
	 * @param sol
	 */
	void setMessage(Solution<Position> sol);
}
