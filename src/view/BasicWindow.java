package view;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
/**
 * BasicWindow class - implements Runnable
 * create the basic window
 * @author Eldar , Ofek
 */
public abstract class BasicWindow implements Runnable{
	Display display;
	Shell shell;
	/**
	 * constructor for Basic Window
	 * @param title
	 * @param width
	 * @param height
	 */
 	public BasicWindow(String title, int width,int height) {
 		display=new Display();
 		shell  = new Shell(display);
 		shell.setSize(width,height);
 		shell.setText(title);
	}
	/**
	 * Configure the window widgets
	 */
 	abstract void initWidgets();
	/**
	 * start the thread of the window
	 */
	@Override
	public void run() {
		initWidgets();
		shell.open();
		// main event loop
		 while(!shell.isDisposed()){ // while window isn't closed

		    // 1. read events, put then in a queue.
		    // 2. dispatch the assigned listener
		    if(!display.readAndDispatch()){ 	// if the queue is empty
		       display.sleep(); 			// sleep until an event occurs 
		    }

		 } // shell is disposed

		 display.dispose(); // dispose OS components
	}
}
