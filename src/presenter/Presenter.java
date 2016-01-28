package presenter;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Dir;
import presenter.Display;
import presenter.DisplayCrossSectionBy;
import presenter.DisplaySolution;
import presenter.Exit;
import presenter.FileSize;
import presenter.Generate3dMaze;
import presenter.LoadMaze;
import presenter.MazeSize;
import presenter.SaveMaze;
import presenter.Solve;
import model.Model;
import view.View;

/**
 * Class of the presenter that connects between the model and the view
 * responsible to notify and update.
 * @author Eldar ,Ofek
 *
 */
public class Presenter implements Observer {
	View view;
	Model model;
	HashMap<String,Command> hash;
	/**
	 * <strong>Presenter</strong>
	 * <p>
	 * <code>public Presenter(Model model, View view)</code>
	 * <p>
	 * Presenter's constructor - get Model and View
	 * initialize the model and view
	 * create the HashMap from String to Command
	 * @param model - get object from type Model
	 * @param view - get object from type View
	 */
	
	public Presenter(Model model, View view) {
		this.model = model;
		this.view = view;
		
		this.hash = new HashMap<String,Command>();
		hash.put("dir", new Dir(this));
		hash.put("display", new Display(this));
		hash.put("displayCrossSectionBy", new DisplayCrossSectionBy(this));
		hash.put("displaySolution", new DisplaySolution(this));
		hash.put("generate3dMaze", new Generate3dMaze(this));
		hash.put("solve", new Solve(this));
		hash.put("saveMaze", new SaveMaze(this));
		hash.put("loadMaze", new LoadMaze(this));
		hash.put("mazeSize", new MazeSize(this));
		hash.put("fileSize", new FileSize(this));
		hash.put("exit", new Exit(this));
		
		view.setHashCommand(hash);
	}
	/**
	 * set message and sent it to the view
	 * @param -String message
	 */	
	public void setMessage(String message) {
		this.view.displayMessage(message);

	}
	/**
	 * get the model
	 * @return -Model model
	 */	
	public Model getModel(){ return model; }
	/**
	 * get the view
	 * @return -View view
	 */	
	public View getView(){ return view; }
	//update - observer java doc
	@Override
	public void update(Observable o, Object arg) {
		if(o == view)
		{
			if(((arg.getClass()).getName()).equals("[Ljava.lang.String;")){
				String[] command = (String[]) arg;
				Command com = hash.get(command[0]);	
				if(com != null)
					if(command.length == 1)
						com.doCommand("");
					else
						com.doCommand(command[1]);
				else
					view.displayMessage("Error! Command not exist"); 
			}
			else if (((arg.getClass()).getName()).equals("java.lang.String")){
				String command = (String) arg;
				Command com = hash.get(command);
				if (command.equals("newConnection"))
					model.newConnection();
				else if(com != null)
					com.doCommand("");
				else	
					view.displayMessage("Error! Command not exist");	
			}
			else if (((arg.getClass()).getName()).equals("presenter.Properties")){
				Properties properties = (Properties) arg;
				model.setProperties(properties);
			}
			else
				view.displayMessage("Error! Object not recognized");
			
		}
		if(o == model)
		{
			if(((arg.getClass()).getName()).equals("algorithms.mazeGenerators.Maze3d"))
			{
				Maze3d maze = (Maze3d) arg;
				view.displayMessage(maze);
			}
			else if(((arg.getClass()).getName()).equals("algorithms.search.Solution")){
				@SuppressWarnings("unchecked")
				Solution<Position> sol = (Solution<Position>) arg;
				view.displayMessage(sol);
			}
			else
			{
				String s = (String) arg;
				view.displayMessage(s);
			}
		}
	}

}
