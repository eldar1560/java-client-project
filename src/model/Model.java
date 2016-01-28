package model;

import presenter.Properties;

/**
 * the model Interface that set the functions of the model side
 * @author Eldar , Ofek
 */


public interface Model {
	/**
	 * <strong>exit</strong>
	 * <p>
	 * <code>public void exit()</code>
	 * <p>
	 * Exit method, that closes the run method, all the threads and saves the cache. 
	 */
	void exit();
	/**
	 * prints all the folders and files that in that path 
	 * @param path - the path in the computer
	 */
	void dir(String path);
	/**
	 * create the solution of the maze and send him to the controller
	 * @param name - the name of the maze
	 */
	void solve(String name,String algorithm);
	/**
	 * display the solution of the maze with specific name
	 * @param name - the name of the specific maze
	 * @return - return the solution of the maze
	 */
	void displaySolution(String name);
	
	/**
	 * generate new maze with specific name, and size of x, y and z
	 * @param name - the name of the new maze
	 * @param x - the size of x
	 * @param y - the size of y
	 * @param z - the size of z
	 */
	void generate3dMaze(String name,int y, int z, int x);
	
	/**
	 * display the maze by his name
	 * @param name - get name of maze
	 * @return
	 */
	void display(String name);
	
	/**
	 * display the specific cross maze in one of the selection, in some index
	 * @param by - the selection x,y or z
	 * @param index - index in the selection
	 * @param name - name of the maze
	 */
	void displayCrossSectionBy(String by, int index, String name);
	
	/**
	 * save maze that in the memory into a file
	 * @param arg - get the parameters of the command
	 */
	void saveMaze(String name,String fileName);
	
	/**
	 * load maze from file to the memory
	 * @param arg - get the parameters of the command
	 */
	void loadMaze(String fileName,String name);
	
	/**
	 * check the size of maze in the memory and send to the controller the size
	 * of the specific maze
	 * @param name - get name of maze
	 */
	void mazeSize(String name);
	
	/**
	 * check the size of file and send to the controller the size
	 * of the file that include the specific maze
	 * @param name - get name of maze
	 */
	void fileSize(String name);
	/**
	 * generate new maze with the properties values
	 */
	void generate3dMaze();
	/**
	 * solve the maze with the algorithm we got from the properties
	 */
	void solve();
	
	/**
	 * <strong>setProperties</strong>
	 * <p>
	 * <code>public void setProperties(Properties properties)</code>
	 * <p>
	 * Setting the properties of the program from the XML properties file to the MyModel variables
	 * @return properties The properties object that contains the properties
	 * @see XMLDecoder
	 */
	void setProperties(Properties properties);
	
	/**
	 * Create new connection
	 */
	void newConnection();


	
}
