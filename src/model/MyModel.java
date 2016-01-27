package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import io.MyCompressorOutputStream;
import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import algorithms.search.State;
import io.MyDecompressorInputStream;
import presenter.Properties;


/**
 * MyModel class extends CommonModel
 * manage the size of the model
 */


public class MyModel extends CommonModel {
	HashMap<Maze3d,String> mazeFile;
	HashMap<String,Maze3d> loadedMaze;
	int x;
	int y;
	int z;
	String name;
	Properties properties;
	static int number = 0;
	
	/**
	 * Constructor of MyModel
	 */
	public MyModel(String ip, int port, Properties properties) {
		super(ip, port);
		this.properties = properties;
		setProperties(properties);
		mazeFile = new HashMap<Maze3d,String>();
		loadedMaze = new HashMap<String,Maze3d>();
		load();
	}	
	
	/**
	 * Constructor that get the properties and initialize the variables
	 * @param properties
	 */
	public MyModel(Properties properties) {
		super();
		this.properties = properties;
		setProperties(properties);
		mazeFile = new HashMap<Maze3d,String>();
		load();
	}

	@Override
	public void generate3dMaze(String name,int y, int z, int x) {
		this.name = name;
		
		if(loadedMaze.containsKey(name))
		{
			Maze3d loaded = loadedMaze.get(name);
			setChanged();
			notifyObservers(loaded);
			setChanged();
			notifyObservers("Maze '" + name + "' is ready");
			return; 
		}
		byte[] bb = new byte[9 + x*y*z];
		byte b;
		String pid = null;
		String hostName = null;
		try{
			pid = ((ManagementFactory.getRuntimeMXBean().getName()).split("@")[0]);
			hostName = InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e){
			setChanged();
			notifyObservers(e.getMessage());
		}		
		
		outToServer.println("generate 3d maze " + name + ++number + "@" + pid + "@" + hostName +
							" " + y + " " + z + " " + x);
		outToServer.flush();
		try {
			while(!inFromServer.readLine().equals("doneMaze!"));
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Lost");
			return;
		}

		outToServer.println("GetMaze");
		outToServer.flush();
		
		try {
			for(int i = 0; i < 9 + x*y*z; i++){
				b = (byte)inFromServer.read();
				bb[i] = b;
			}
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Lost");
			return;
		}
		Maze3d maze = new Maze3d(bb);
		
		hm.put(name, maze);
		setChanged();
		notifyObservers(maze);
		setChanged();
		notifyObservers("Maze '" + name + "' is ready");
	}
	
	@Override
	public void generate3dMaze() {
		byte[] bb = new byte[9 + x*y*z];
		byte b;
		String pid = null;
		String hostName = null;
		try{
			pid = ((ManagementFactory.getRuntimeMXBean().getName()).split("@")[0]);
			hostName = InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e){
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		outToServer.println("generate 3d maze " + this.name + ++number +"@" + pid + "@" + hostName
				+ " " + y + " " + z + " " + x);
		outToServer.flush();
		
		try {
			while(!inFromServer.readLine().equals("doneMaze!"));
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Lost");
			return;
		}
		
		outToServer.println("GetMaze");
		outToServer.flush();
		
		try {
			for(int i = 0; i < 9 + x*y*z; i++){
				
				b = (byte)inFromServer.read();
				bb[i] = b;
			}
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Lost");
			return;
		}
		
		Maze3d maze = new Maze3d(bb);
		
		hm.put(name, maze);
		setChanged();
		notifyObservers(maze);
		setChanged();
		notifyObservers("Maze '" + name + "' is ready");

	}
	
	@Override
	public void display(String name){
		Maze3d maze = null;
		if(name.length() == 0)
			maze = hm.get(this.name);
		else
			maze = hm.get(name);
		if(maze == null){
			setChanged();
			notifyObservers("Not exist maze by this name: '" + name+"'");
		}
		else{
			setChanged();
			notifyObservers(maze.toString()); 
		}
	}


	@Override
	public void displayCrossSectionBy(String by, int index, String name) {
		Maze3d maze = null;
		if(name.length() == 0)
			maze = hm.get(this.name);
		else
			maze = hm.get(name);
		
		String strMaze ="";
		int[][] maze2d = null;
		if(maze == null){
			setChanged();
			notifyObservers("The maze isn't exist");
			return;
		}
		
		
		
		try{
			switch(by){
			case "X":
				maze2d = maze.getCrossSectionByX(index);
				break;
			case "x":
				maze2d = maze.getCrossSectionByX(index);
				break;
			case "Y":
				maze2d = maze.getCrossSectionByY(index);
				break;
			case "y":
				maze2d = maze.getCrossSectionByY(index);
				break;
			case "Z":
				maze2d = maze.getCrossSectionByZ(index);
				break;
			case "z":
				maze2d = maze.getCrossSectionByZ(index);
				break;
			default:
				setChanged();
				notifyObservers("Invalid cross section");	
				return;
			}
		}
		catch(IndexOutOfBoundsException e){
			setChanged();
			notifyObservers("Invalid index");	
			return;
		}
		
		
		
		for(int i = 0; i < maze2d.length; i++){
			for(int j = 0; j < maze2d[i].length; j++)
				strMaze += String.valueOf(maze2d[i][j]) + " ";
			strMaze += '\n';
		}
		setChanged();
		notifyObservers(strMaze);	
	}

	@Override
	public void saveMaze(String name,String fileName) {
		Maze3d maze = hm.get(name);
		if(maze == null){
			setChanged();
			notifyObservers("The maze '" + name + "' not exist");
			return;
		}
		
		OutputStream out = null;
		try {
			out = new MyCompressorOutputStream(new FileOutputStream(fileName + ".maz"));
			out.write(maze.toByteArray());	
			mazeFile.put(maze, fileName + ".maz");
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers(e.getMessage());
			return;
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
			return;
		}
		finally{
			try {
				out.flush();
			} catch (IOException e) {
				setChanged();
				notifyObservers(e.getMessage());
			}
			try {
				out.close();
			} catch (IOException e) {
				setChanged();
				notifyObservers(e.getMessage());
			}
		}
		
		setChanged();
		notifyObservers("The maze '"+name+"' was saved successfully in the file '" + fileName + "'");
	}

	@Override
	public void loadMaze(String fileName,String name) {
		Maze3d loaded = null;
		boolean isOpen = false;
		
		try{
			@SuppressWarnings("unused")
			File file = new File(fileName + ".maz");
		}
		catch(NullPointerException e){
			setChanged();
			notifyObservers("File not exist");
			return;
		}
			
		InputStream in=null;
		InputStream inSize = null;
		try {
			in = new MyDecompressorInputStream(new FileInputStream(fileName + ".maz"));
			inSize = new MyDecompressorInputStream(new FileInputStream(fileName + ".maz"));
			isOpen = true;
			
			byte bSize[] = new byte[3];
			inSize.read(bSize);
			
			byte b[] = new byte[bSize[0]*bSize[1]*bSize[2] + 9];
			in.read(b);
			loaded = new Maze3d(b);
		}
		catch (FileNotFoundException e) {
			setChanged();
			notifyObservers(e.getMessage());
			return;
		}
		catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
			return;
		}
		catch(NullPointerException e)
		{
			setChanged();
			notifyObservers(e.getMessage());
			return;
		}
		finally
		{
			try {
				if(isOpen)
				{
					inSize.close();
					in.close();
				}
			} catch (IOException e) 
			{
				setChanged();
				notifyObservers("The maze '"+ name+"' was unsuccessfully");
			}
		}
			
		hm.put(name, loaded);
		loadedMaze.put(name, loaded);
		mazeFile.put(loaded, fileName + ".maz");
		setChanged();
		notifyObservers("Loaded the maze '" + name + "' successfully");
	}
	
	@Override
	public void mazeSize(String name) {
		Maze3d maze = hm.get(name);
		if(maze == null){
			setChanged();
			notifyObservers("The maze " + name + " not exist");
			return;
		}
		
		int size = 4*(maze.getMaze().length * maze.getMaze()[0].length *maze.getMaze()[0][0].length + 3 + 3);
		setChanged();
		notifyObservers("The maze size of '" + name + "' in memory: " + size +" bytes");		
	}


	@Override
	public void fileSize(String name) {
		try{
			String filePath = mazeFile.get(hm.get(name));
			if(filePath == null){
				setChanged();
				notifyObservers("The maze '" + name + "' not exist in any file");
				return;
			}
			File maze = new File(filePath);
			setChanged();
			notifyObservers("The file size of maze '" + name + "' is: " + maze.length()+" bytes");
		}
		catch (NullPointerException e){
			setChanged();
			notifyObservers("Not exist '" + name + "' file");
		}
	}

	@Override
	public void exit(){
		outToServer.println("exit");
		outToServer.flush();
		try {
			theServer.close();
		} catch (IOException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		save();
	}
	/**
	 * save the hash maps to the zip
	 */
	public void save() {
		ObjectOutputStream out = null;
		try{
			out = new ObjectOutputStream(new GZIPOutputStream(new FileOutputStream("ClientMap.zip")));
			out.writeObject(hm);
			out.writeObject(mazeFile);
			out.flush();
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		catch(IOException e){
			setChanged();
			notifyObservers(e.getMessage());
		} finally{
			try{
				out.close();
			}catch(IOException e)
			{
				setChanged();
				notifyObservers(e.getMessage());
			}
		}
	}

	/**
	 * load the hash maps to the zip
	 */
	@SuppressWarnings("unchecked")
	public void load() {
		ObjectInputStream in = null;
		try{
			in = new ObjectInputStream(new GZIPInputStream(new FileInputStream("ClientMap.zip")));
			hm = (HashMap<String, Maze3d>) in.readObject();
			mazeFile = (HashMap<Maze3d,String>) in.readObject();
		} catch (FileNotFoundException e) {
			setChanged();
			notifyObservers(e.getMessage());
		}
		catch(IOException e){
			setChanged();
			notifyObservers(e.getMessage());
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
			finally{
		
			try{
				if(in!= null)
					in.close();
			}catch(IOException e)
			{
				setChanged();
				notifyObservers(e.getMessage());
			}
		}

	}
	/**
	 * set the x from by the properties
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * set the y from by the properties
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * set the z from by the properties
	 * @param z
	 */
	public void setZ(int z) {
		this.z = z;
	}
	/**
	 * get the name of the maze from the properties
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * set the name from the properties
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * set the variables from the properties
	 * @param properties
	 */
	@Override
	public void setProperties(Properties properties) {
		this.properties = properties;
		setChanged();
		setName(properties.getName());		
		setX(properties.getX());
		setY(properties.getY());
		setZ(properties.getZ());
		notifyObservers("The properties is uploaded successfully");
	}
	@Override
	public void solve(String name, String algorithm) {
		String pid = null;
		String hostName = null;
		
		try{
			pid = ((ManagementFactory.getRuntimeMXBean().getName()).split("@")[0]);
			hostName = InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e){
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		this.name = name;
		outToServer.println("solve " + name + number + "@" + pid + "@" + hostName+" " + algorithm);
		outToServer.flush();
		
		String line = null;
		try {
			while(!(line = inFromServer.readLine()).equals("doneSolve!") &&
					!line.equals("Error to create solution"));
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Error");
			return;
		}
		if(line.equals("Error to create solution")){
			setChanged();
			notifyObservers("Error to solve");
			return;
		}
		outToServer.println("GetSolution");
		outToServer.flush();
		String stringSolution = null;

		try {
			while(inFromServer.readLine()!= null)
			{
				stringSolution += inFromServer.readLine();
				stringSolution += "\n";
			}
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Error");
			return;
		}
		
		String[] positionsSolution = stringSolution.split("\n");
		Solution<Position> solution = new Solution<Position>();
		
		for(int i = 0; i < positionsSolution.length; i++){
			String[] positions = positionsSolution[i].split(" ");
			int y=Integer.parseInt(positions[0]);
			int z=Integer.parseInt(positions[1]);
			int x=Integer.parseInt(positions[2]);
			solution.addSolution(new State<Position>(new Position(y, z, x)));
		}

		hashSolution.put(hm.get(name), solution);	
		setChanged();
		notifyObservers(solution);
		setChanged();
		notifyObservers("Solution for '" + name + "' is ready");

	}
	@Override
	public void dir(String path)
	{
		try {
			File f = new File(path);	
			String[] string = f.list(); 
			String listPath = "";
			
			for(int i = 0; i <string.length; i++)
				listPath += string[i] + '\n';
			setChanged();
			notifyObservers(listPath);
		}
		catch (NullPointerException e){
			setChanged();
			notifyObservers("Invalid path");
		}
		
	}
	@Override
	public void solve() {
		String pid = null;
		String hostName = null;
		
		try{
			pid = ((ManagementFactory.getRuntimeMXBean().getName()).split("@")[0]);
			hostName = InetAddress.getLocalHost().getHostName();
		}
		catch(Exception e){
			setChanged();
			notifyObservers(e.getMessage());
		}
		
		String name = this.name;
		outToServer.println("solve " + name + number + "@" + pid + "@" + hostName);
		outToServer.flush();
		
		String line = null;
		try {
			while(!(line = inFromServer.readLine()).equals("doneSolve!") &&
					!line.equals("Error to create solution"));
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Error");
			return;
		}
		if(line.equals("Error to create solution")){
			setChanged();
			notifyObservers("Error to solve");
			return;
		}
		outToServer.println("GetSolution");
		outToServer.flush();
		String stringSolution = "";
		String current = "";
		try {
			String sizeStr = inFromServer.readLine();
			int size = Integer.parseInt(sizeStr);
			for (int i = 0; i < size ; i++) {
				current = inFromServer.readLine();
				stringSolution += current;
				if(i> 0)
					stringSolution += ",";
			}
		} catch (IOException e) {
			setChanged();
			notifyObservers("Connection Error");
			return;
		}
		
		String[] positionsSolution = stringSolution.split(",");
		Solution<Position> solution = new Solution<Position>();
		
		for(int i = 0; i < positionsSolution.length; i++){
			String[] positions = positionsSolution[i].split(" ");
			int y=Integer.parseInt(positions[0]);
			int z=Integer.parseInt(positions[1]);
			int x=Integer.parseInt(positions[2]);
			solution.addSolution(new State<Position>(new Position(y, z, x)));
		}

		hashSolution.put(hm.get(name), solution);	
		setChanged();
		notifyObservers(solution);
		setChanged();
		notifyObservers("Solution for '" + name + "' is ready");

	}
		
}
