package view;


import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;
/**
 * the implementation of view
 * @author Eldar , ofek
 *
 */
public class MyView extends Observable implements View{
	UserChoice uc;
	HashMap<String,Command> hc;

	/**
	 * Constructor of MyView , set the view of the user choice
	 */
	public MyView(UserChoice uc)
	{
		this.uc = uc;
		uc.setView(this);
	}
	@Override
	public void start() {
		uc.start();
	}

	@Override
	public void displayMessage(String message) {
		uc.setMessage(message);
		
	}

	@Override
	public void setHashCommand(HashMap<String, Command> hc) {
		uc.setHashCommand(hc);
		this.hc = hc;
	}
	@Override
	public void notifyMe(String[] str)
	{
		setChanged();
		notifyObservers(str);
	}
	@Override
	public void notifyMe(String str) {
		setChanged();
		notifyObservers(str);
	}
	
	@Override
	public void notifyMe(Properties properties)  {
		setChanged();
		notifyObservers(properties);
	}
	@Override
	public void displayMessage(Maze3d maze) {
		uc.setMessage(maze);
		
	}
	@Override
	public void displayMessage(Solution<Position> sol) {
		uc.setMessage(sol);
		
	}
}
