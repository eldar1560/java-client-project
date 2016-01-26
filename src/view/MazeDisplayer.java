package view;

import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

/**
 * MazeDisplayer - extends Canvas
 * @author Eldar , Ofek
 */
public abstract class MazeDisplayer extends Canvas{
	/**
	 * Default Constructor
	 * @param parent
	 * @param style
	 */
	public MazeDisplayer(Composite parent, int style) {
		super(parent, style);
	}
	/**
	 * move up
	 */
	public abstract void moveUp();
	/**
	 * move down
	 */
	public abstract  void moveDown();
	/**
	 * move left
	 */
	public abstract  void moveLeft();
	/**
	 * move right
	 */
	public  abstract void moveRight();
	/**
	 * move forward
	 */
	public abstract  void moveForward();
	/**
	 * move back
	 */
	public  abstract void moveBack();
}
