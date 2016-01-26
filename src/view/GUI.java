package view;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Command;
import presenter.Properties;

/**
 * GUI Class - manage the graphic Line Interface for the client
 * @author Eldar , Ofek
 */
public class GUI extends BasicWindow implements UserChoice{
	View view;
	HashMap <String,Command> hc;
	Maze3dDisplayer mazeDisplay;
	/**
	 * constructor for GUI
	 * @param title
	 * @param width
	 * @param height
	 */
	public GUI(String title, int width, int height) {
		super(title, width, height);
		
	}


	@Override
	public void setView(View view) {
		this.view = view;
	}

	@Override
	public void start() {
		run();
		
	}

	public void notifyMe(String[] str) {
		view.notifyMe(str);
		
	}

	public void notifyMe(String str) {
		view.notifyMe(str);
		
	}

	@Override
	public void setMessage(Maze3d maze) {
		mazeDisplay.setMaze(maze);
	}
	@Override
	public void setMessage(String message) {
		String temp = new String(message);
		String[] newMessage = temp.split(" ",2);
		if(newMessage[0].equals("Maze"))
		{
			DialogMessage dm = new DialogMessage(shell, message+"\nClick one of the arrows to start!");
			dm.open();
			return;
		}
		else if(newMessage[0].equals("Loaded"))
		{
			DialogMessage dm = new DialogMessage(shell, message+"\nTo play on this maze follow this:\n1.Choose \"Generate Maze\" in the tool bar\n2.Choose \"Custom generate\"\n3.Write this name in the \"Maze name\" with arbitary numbers");
			dm.open();
			return;
		}
		DialogMessage dm = new DialogMessage(shell, message);
		dm.open();
		
	}
	public void redraw() {
		mazeDisplay.redraw();	
	}
	@Override
	public void setHashCommand(HashMap<String, Command> hc) {
		this.hc = hc;
		
	}

	@Override
	void initWidgets() {

		shell.setLayout(new GridLayout(3, false));

		Menu menuBar, fileMenu, gameMenu,infoMenu;
		MenuItem fileMenuHeader, gameMenuHeader,saveMazeItem,loadMazeItem, generateItem,hintItem,infoMenuHeader,floorNumberItem,solveItem, stopSolveItem, openPropertiesItem, exitItem,instructionsItem,aboutItem, newConnection;
		
		menuBar = new Menu(shell,SWT.BAR);
		
		fileMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		fileMenuHeader.setText("&Menu");
		
		fileMenu = new Menu(shell, SWT.DROP_DOWN);
		fileMenuHeader.setMenu(fileMenu);
		
		openPropertiesItem = new MenuItem(fileMenu, SWT.PUSH);
		openPropertiesItem.setText("&Open Properties");
		
		newConnection = new MenuItem(fileMenu, SWT.PUSH);
		newConnection.setText("&New Connection");
		
		instructionsItem = new MenuItem(fileMenu,SWT.PUSH);
		instructionsItem.setText("&Instructions");
		
		aboutItem = new MenuItem(fileMenu,SWT.PUSH);
		aboutItem.setText("&About");
		
		exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText("&Exit");
		
		gameMenuHeader = new MenuItem(menuBar, SWT.CASCADE);
		gameMenuHeader.setText("&Game Options");
		
		gameMenu = new Menu(shell, SWT.DROP_DOWN);
		gameMenuHeader.setMenu(gameMenu);
		
		
		generateItem = new MenuItem(gameMenu, SWT.PUSH);
		generateItem.setText("&Generate maze");
			
		solveItem = new MenuItem(gameMenu, SWT.PUSH);
		solveItem.setText("&Solve maze");
		
		stopSolveItem = new MenuItem(gameMenu, SWT.PUSH);
		stopSolveItem.setText("&Stop solve");
		
		
		saveMazeItem = new MenuItem(gameMenu, SWT.PUSH);
		saveMazeItem.setText("&Save Maze");
		
		loadMazeItem = new MenuItem(gameMenu, SWT.PUSH);
		loadMazeItem.setText("&Load Maze");
		
		infoMenuHeader = new MenuItem(menuBar,SWT.CASCADE);
		infoMenuHeader.setText("&Information");
		
		infoMenu = new Menu(shell,SWT.DROP_DOWN);
		infoMenuHeader.setMenu(infoMenu);
		
		floorNumberItem = new MenuItem(infoMenu,SWT.READ_ONLY);
		floorNumberItem.setText("&Floor Number : 0");
		
		hintItem = new MenuItem(infoMenu,SWT.PUSH);
		hintItem.setText("&Hint");
		
		shell.setMenuBar(menuBar);
				
		mazeDisplay = new Maze3dDisplayer(shell, SWT.BORDER,floorNumberItem);
		mazeDisplay.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 7));
		view.notifyMe("generate3dMaze");
		mazeDisplay.draw();
		GUI g=this;
		Listener generateListener = new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				mazeDisplay.newSolve(false);
				GenerateWindow gw = new GenerateWindow(g,shell);
				gw.open();
			}
			
		};
		
		Listener exitListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				mazeDisplay.setRunning(false);
				view.notifyMe("exit");
				shell.dispose();
			}
		};
		
		Listener propertiesListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				FileDialog fd = new FileDialog(shell, SWT.OPEN);
		        fd.setText("Open Properties");
		        try {
					fd.setFilterPath(new java.io.File( "." ).getCanonicalPath());
				} catch (IOException e) {
					e.printStackTrace();
				}
		        String[] filterExt = { "*.xml" };
		        fd.setFilterExtensions(filterExt);
		        String selected = fd.open();
		        if(selected != null){
		        	XMLDecoder d;
		    		Properties properties = new Properties();
		    		try {
		    			d = new XMLDecoder(new BufferedInputStream(new FileInputStream(selected)));
		    			properties = (Properties) d.readObject();
		    			d.close();
		    		} catch (FileNotFoundException e) {
		    			e.printStackTrace();
		    		}
		    		view.notifyMe(properties);
		        }
		        	
			}
		};

		Listener solveListener =new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				view.notifyMe("solve");			
			}
		};
		
		Listener stopSolveListener = new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				mazeDisplay.newSolve(false);			
			}
		};
		
		Listener instructionsListener = new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				DialogMessage dm = new DialogMessage(shell, "The instructions are:\nUse the key arrows to go up , down, right , left\nUse pgup and pgdown to go up and down.\nHelp bugs reach the carrot!");
				dm.open();
			}
		};
		
		Listener aboutListener = new Listener(){
			@Override
			public void handleEvent(Event arg0) {
				DialogMessage dm = new DialogMessage(shell,"3D Maze game developed by: \nEldar Gabay && Ofek Bar Ilan");
				dm.open();
			};
		
		};
		Listener hintItemListener = new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				DialogMessage dm = new DialogMessage(shell,"The carrot is at floor number :"+mazeDisplay.getExitY());
				dm.open();
			}
			
		};
		Listener saveMazeItemListener = new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				SaveLoadMazeDialog slm = new SaveLoadMazeDialog(g,shell,"Save Maze","Enter the maze name here:","Enter the file name here:");
				slm.open();
			}
			
		};
		Listener loadMazeItemListener = new Listener() {
			@Override
			public void handleEvent(Event arg0) {
				SaveLoadMazeDialog slm = new SaveLoadMazeDialog(g,shell,"Load Maze","Enter the file name here:","Enter the maze name here:");
				slm.open();
			}
			
		};
		
		Listener newConnectionListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				view.notifyMe("newConnection");
			}
		};
		
		newConnection.addListener(SWT.Selection, newConnectionListener);
		loadMazeItem.addListener(SWT.Selection,loadMazeItemListener);
		saveMazeItem.addListener(SWT.Selection,saveMazeItemListener);
		hintItem.addListener(SWT.Selection, hintItemListener);
		aboutItem.addListener(SWT.Selection, aboutListener);
		instructionsItem.addListener(SWT.Selection, instructionsListener);
		shell.addListener(SWT.Close, exitListener);
		exitItem.addListener(SWT.Selection, exitListener);
		generateItem.addListener(SWT.Selection, generateListener);
		openPropertiesItem.addListener(SWT.Selection, propertiesListener);
		solveItem.addListener(SWT.Selection, solveListener);
		stopSolveItem.addListener(SWT.Selection, stopSolveListener);
		shell.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {}
			
			@Override
			public void keyPressed(KeyEvent key) {
				if(!mazeDisplay.isSolving())
					switch(key.keyCode)
					{
					case SWT.ARROW_DOWN:
						mazeDisplay.moveBack();
						break;
					case SWT.ARROW_UP:
						mazeDisplay.moveForward();
						break;
					case SWT.ARROW_LEFT:
						mazeDisplay.moveLeft();
						break;
					case SWT.ARROW_RIGHT:
						mazeDisplay.moveRight();
						break;
					case SWT.PAGE_UP:
						mazeDisplay.moveUp();
						break;
					case SWT.PAGE_DOWN:
						mazeDisplay.moveDown();
						break;
					}	
			}
		});
		
	}


	@Override
	public void setMessage(Solution<Position> sol) {
		mazeDisplay.displaySolution(sol);
		
	}

}
