package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Observable;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Presenter;
/**
 * CommonModel implements Model interface
 * abstract class of the model
 * @author Eldar , Ofek
 */

public abstract class CommonModel extends Observable implements Model{
	String ip;
	int port;
	PrintWriter outToServer;
	BufferedReader inFromServer;
	Socket theServer;
	Presenter presenter;
	HashMap<String, Maze3d> hm;
	HashMap<Maze3d, Solution<Position>> hashSolution;
	
	/**
	 * constructor for common model
	 */
		public CommonModel(String ip, int port) {
		this.ip = ip;
		this.port = port;
		try {
			theServer = new Socket(ip,port);
		} catch (UnknownHostException e) {
			setChanged();
			notifyObservers(e.getMessage());
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		try {
			outToServer = new PrintWriter(theServer.getOutputStream());
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		try {
			inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		hashSolution = new HashMap<Maze3d,Solution<Position>>();
		hm = new HashMap<String,Maze3d>();
	}
	
	/**
	 * Default constructor 
	 */
	public CommonModel()
	{
		hm = new HashMap<String,Maze3d>();
		hashSolution = new HashMap<Maze3d, Solution<Position>>();
	}
	
	@Override
	public void newConnection(){
		try {
			theServer = new Socket(ip,port);
		} catch (UnknownHostException e) {
			setChanged();
			notifyObservers("Error create connection");
			return;
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error create connection");
			return;
		}
		
		try {
			outToServer = new PrintWriter(theServer.getOutputStream());
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error create connection");
			return;
		}
		try {
			inFromServer = new BufferedReader(new InputStreamReader(theServer.getInputStream()));
		} catch (IOException e) {
			setChanged();
			notifyObservers("Error create connection");
			return;
		}
		
		setChanged();
		notifyObservers("Connection up");
	}
	
	@Override	
	public void displaySolution(String name){
		Solution<Position>	solution = hashSolution.get(hm.get(name));
		if(solution == null)
		{
			setChanged();
			notifyObservers("Not exist solution for '" + name + "' maze");
		}
		else
		{
			setChanged();
			notifyObservers(solution.toString());
		}
	}

	public abstract void dir(String path);
	@Override
	public abstract void generate3dMaze(String name,int y, int z, int x);
	
	@Override
	public abstract void generate3dMaze();
	@Override
	public abstract void display(String name);
	
	@Override
	public abstract void solve(String name,String algorithm);
	
	@Override
	public abstract void solve();
	
	@Override
	public abstract void displayCrossSectionBy(String by, int index, String name);
	
	@Override
	public abstract void saveMaze(String name,String fileName);
	
	@Override
	public abstract void loadMaze(String fileName,String name);
	
	@Override
	public abstract void mazeSize(String name);
	
	@Override
	public abstract void fileSize(String name);
	
	@Override
	public abstract void exit();
	

}
