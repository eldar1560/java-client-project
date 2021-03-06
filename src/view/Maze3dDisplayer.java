package view;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MenuItem;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
/**
 * Maze3dDisplayer class - extends DisplayMaze
 *@author Eldar,Ofek
 */
public class Maze3dDisplayer extends MazeDisplayer{

	boolean directions[];
	Maze3d maze;
	int characterX;
	int characterY;
	int characterZ;
	int exitX;
	int exitY;
	int exitZ;
	ExecutorService threadSolve;
	Thread solve;
	Thread run;
	boolean running;
	MenuItem mi;
	double scale;
	int counter;
	 /**
     * DisplayMaze3D constructor
     */
	public Maze3dDisplayer(Composite parent, int style,MenuItem mi) {
		super(parent, style);
		threadSolve = Executors.newFixedThreadPool(1);
		this.mi = mi;
		counter = 0;
		directions = new boolean[6];
		directions[0] = true;
		directions[1] = false;
		directions[2] = false;
		directions[3] = false;
		directions[4] = true;
		directions[5] = false;
	}	
	/**
     * get the maze
     * @return maze-the maze
     */
	public Maze3d getMaze() {
		return maze;
	}
	/**
     * set the maze
     * @param maze-the maze
     */
	public void setMaze(Maze3d maze) {
		this.maze = maze;
		characterX = maze.getStartPosition().getX();
		characterY = maze.getStartPosition().getY();
		characterZ = maze.getStartPosition().getZ();
		
		exitX = maze.getGoalPosition().getX();
		exitY = maze.getGoalPosition().getY();
		exitZ = maze.getGoalPosition().getZ();
	}

	/**
	 * get the scale(the number that responsible for the zoom in/out)
	 * @return
	 */
     public double getScale() {
		return scale;
	}
    /**
     * set the scale 
     * @param scale the number that responsible for the zoom in/out
     */
	public void setScale(double scale) {
		this.scale = scale;
	}
     /**
      * draw the maze 3d and the character
      */
	public void draw(){
		Image image = new Image(getDisplay(), "resources/walls.jpg");
		Image charachter = new Image(getDisplay(), "resources/runne.png");
		Image charachter2 = new Image(getDisplay(), "resources/runne3.png");
		Image charachter3 = new Image(getDisplay(), "resources/runne21.png");
		Image charachter4 = new Image(getDisplay(), "resources/runne22.png");
		Image charachter5 = new Image(getDisplay(), "resources/runne41.png");
		Image charachter6 = new Image(getDisplay(), "resources/runne42.png");
		Image endGame = new Image(getDisplay(), "resources/EndGame.jpg");
		Image theEnd1 = new Image(getDisplay(), "resources/carrot1.jpg");
		Image theEnd2 = new Image(getDisplay(), "resources/carrot2.jpg");
		Image theEnd3 = new Image(getDisplay(), "resources/carrot3.jpg");
		Image theEnd4 = new Image(getDisplay(), "resources/carrot4.jpg");
		Image up = new Image(getDisplay(), "resources/up.png");
		Image down = new Image(getDisplay(), "resources/down.jpg");
		Image upAndDown = new Image(getDisplay(), "resources/upanddown.png");
		setBackground(new Color(null, 255, 255, 255));
		scale = 0;
		
    	addPaintListener(new PaintListener() {
			
			@Override
			public void paintControl(PaintEvent e) {
				   e.gc.setForeground(new Color(null,255,255,255));
				   e.gc.setBackground(new Color(null,255,255,255));

				   if (scale < 0) {
					   scale = 0;
				   }
				   
				   int width=getSize().x;
				   int height=getSize().y;

				   int w = (int) (((width)/maze.getMaze()[0][0].length)*(1+scale));
				   int h = (int) (((height)/maze.getMaze()[0].length)*(1+scale));
				   
				   
				   if(characterX == exitX && characterZ == exitZ && characterY == exitY)
					   e.gc.drawImage(endGame, 0, 0, 263, 192, 0, 0, getSize().x, getSize().y);

				   else
				   {
					   counter++;
					   for(int i=0;i<maze.getMaze()[0].length;i++)
						      for(int j=0;j<maze.getMaze()[0][0].length;j++){
						    	  mi.setText("&Floor Number :"+characterY);
						          int x=j*w;
						          int y=i*h;
						          if(maze.getMaze()[characterY][i][j] == 1)
						        	  e.gc.drawImage(image,0,0,1200,1200, x, y,w,h);
						          if(maze.getMaze()[characterY][i][j] == 0)
						              e.gc.fillRectangle(x,y,w,h);  
						          if(characterY+1 < maze.getMaze().length)
						        	  if(maze.getMaze()[characterY][i][j] == 0 && maze.getMaze()[characterY+1][i][j] == 0)
						        		  e.gc.drawImage(up, 0, 0, 159,318 , x, y, w, h);
						          if(characterY-1>= 0)
						        	  	if(maze.getMaze()[characterY][i][j] == 0 && maze.getMaze()[characterY-1][i][j] == 0)
						        	  		e.gc.drawImage(down, 0, 0, 535,386 , x, y, w, h);
						          if(characterY +1< maze.getMaze().length && characterY-1 >= 0)
						        	  if(maze.getMaze()[characterY][i][j] == 0 && maze.getMaze()[characterY+1][i][j] == 0 && maze.getMaze()[characterY-1][i][j] == 0)
						        		  e.gc.drawImage(upAndDown, 0, 0, 225,225 , x, y, w, h);
						          if(j == characterX && i == characterZ){
						        	  if(directions[0] == true)
						        		  e.gc.drawImage(charachter, 0, 0, 320,320 , x, y, w, h);
						        	  if(directions[1] == true)
						        		  e.gc.drawImage(charachter2, 0, 0, 320,320 , x, y, w, h);
						        	  if(directions[2] == true)
						        	  {
						        		  if(directions[4] == true)
						        			  e.gc.drawImage(charachter3, 0, 0, 320,320 , x, y, w, h);
						        		  if(directions[5] == true)
						        			  e.gc.drawImage(charachter4, 0, 0, 320,320 , x, y, w, h);
						        	  }
						        	  if(directions[3] == true)
						        	  {
						        		  if(directions[4] == true)
						        		  {
						        			  e.gc.drawImage(charachter5, 0, 0, 320,320 , x, y, w, h);
						        		  }
						        		  if(directions[5] == true)
						        		  {
						        			  e.gc.drawImage(charachter6, 0, 0, 320,320 , x, y, w, h);
						        		  }
						        	  }
						          }
						          if(characterY == exitY)
						          {
						        	  if(counter == 0)
						        		  e.gc.drawImage(theEnd1, 0, 0, 600,600 , exitX*w, exitZ*h, w, h);
						        	  if(counter == 1)
						        		  e.gc.drawImage(theEnd2, 0, 0, 600,600 , exitX*w, exitZ*h, w, h);
						        	  if(counter == 2)
						        		  e.gc.drawImage(theEnd3, 0, 0, 600,600 , exitX*w, exitZ*h, w, h);
						        	  if(counter == 3)
						        		  e.gc.drawImage(theEnd4, 0, 0, 600,600 , exitX*w, exitZ*h, w, h);
						        	  
						        	  if(counter >3)
						        		  counter = 0;
						          }
						      }
				   }
			}
				  
		});
	}
	/**
	 * move the character
	 * @param x
	 * @param y
	 * @param z
	 */
	private void moveCharacter(int y,int z, int x){
		if(characterX == exitX && characterZ == exitZ && characterY == exitY)
      	  return;
		
		if(y>=0 && y < maze.getMaze().length && z>=0 && z<maze.getMaze()[0].length && x>=0 && x<maze.getMaze()[0][0].length && maze.getMaze()[y][z][x] == 0){
			characterX=x;
			characterY=y;
			characterZ=z;

			run=new Thread(new Runnable() {
				
				@Override
				public void run() {
					redraw();
				}
			});
			getDisplay().syncExec(run);
		
		}
	}

	@Override
	public void moveUp() {
		int x = characterX;
		int y = characterY+1;
		int z = characterZ;
		
		moveCharacter(y, z, x);
	}

	@Override
	public void moveDown() {
		int x = characterX;
		int y = characterY-1;
		int z = characterZ;
		
		moveCharacter(y, z, x);
	}

	@Override
	public void moveLeft() {
		int x = characterX-1;
		int y = characterY;
		int z = characterZ;
		
		directions[0] = false;
		directions[1] = true;
		directions[2] = false;
		directions[3] = false;
		directions[4] = false;
		directions[5] = true;
		moveCharacter(y, z, x);
	}

	@Override
	public void moveRight() {
		int x = characterX+1;
		int y = characterY;
		int z = characterZ;
		
		directions[0] = true;
		directions[1] = false;
		directions[2] = false;
		directions[3] = false;
		directions[4] = true;
		directions[5] = false;
		
		moveCharacter(y, z, x);
	}

	@Override
	public void moveForward() {
		int x = characterX;
		int y = characterY;
		int z = characterZ-1;
		
		directions[0] = false;
		directions[1] = false;
		directions[2] = false;
		directions[3] = true;
		
		moveCharacter(y, z, x);
	}

	@Override
	public void moveBack() {
		int x = characterX;
		int y = characterY;
		int z = characterZ+1;
		
		directions[0] = false;
		directions[1] = false;
		directions[2] = true;
		directions[3] = false;
		
		moveCharacter(y, z, x);
	}
	/**
	 * display the solution
	 * @param sol-the Solution for display
	 */
	public void displaySolution(Solution<Position> sol)
	{
		this.running = true;
		solve=new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(running)
				{
				String[] position=sol.toString().split("\n");
				int x,y,z;
				for(int i=position.length-1; running && i>=1; i--)
				{
					String[] numbers = position[i].split(" ");
					y=Integer.parseInt(numbers[0]);
					z=Integer.parseInt(numbers[1]);
					x=Integer.parseInt(numbers[2]);
					  moveCharacter(y,z,x);
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {}
				}
				}
			}
		});
		threadSolve.execute(solve);
	}
	
	/**
	 * set the running
	 * @param running-the running
	 */
	public void setRunning(boolean running){ 
		
		if(!running)
		{
			this.running=running;
			if(run != null)
				while(run.isAlive());
			threadSolve.shutdown();
		}
	}
	/**
	 * set that new solve is occur
	 * @param running
	 */
	public void newSolve(boolean running){ 
		
		if(!running)
			this.running=running;
	}
	/**
	 * get the x of character
	 * @return characterX-the x
	 */
	public int getCharacterX() {
		return characterX;
	}
	/**
	 * get the y of character
	 * @return characterY-the y
	 */
	public int getCharacterY() {
		return characterY;
	}
	/**
	 * get the z of character
	 * @return characterZ-the z
	 */
	public int getCharacterZ() {
		return characterZ;
	}
	/**
	 * get the y of the exit
	 * @return exitY the y
	 */
	public int getExitY(){
		return exitY;
	}
	/**
	 * return if thread for solve is in progress
	 * @return boolean running
	 */
	public boolean isSolving() { return running; }
	


}
