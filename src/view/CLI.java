package view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
/**
 * CLI Class - manage the Command Line Interface for the client
 * @author Eldar , Ofek
 */
public class CLI implements UserChoice{
	private BufferedReader in;
	private PrintWriter out;
	private HashMap<String,Command> hm;
	private View view;
	/**
	 * CLI Constructor - initialize the CLI object
	 * @param out - get PrintWriter
	 * @param in - get BufferedReader
	 */
	public CLI(BufferedReader in,PrintWriter out)
	{
		this.out = out;
		this.in = in;
	}

	@Override
	public void start() {
		new Thread(new Runnable() {
		String s = null;
			@Override
			public void run() {
				try {
					while ((s = in.readLine()).equals("exit") != true) {
						String[] command = s.split(" ",2);		
						if(hm.containsKey(command[0]) == true)
							view.notifyMe(command);
						else
						{
							out.println("Error");
							out.flush();
						}
					}
					}catch (IOException e) {
					e.printStackTrace();
				}
				view.notifyMe(s);
			}
		}).start();
	}

	@Override
	public void setView(View view){ 
		this.view = view;
	}

	@Override
	public void setHashCommand(HashMap<String, Command> hc) {
		this.hm = hc;
	}
	@Override
	public void setMessage(String str){
		out.println(str);
		out.flush();
	}
	@Override
	public void setMessage(Maze3d maze){
	}

	@Override
	public void setMessage(Solution<Position> sol) {	
	}
}
