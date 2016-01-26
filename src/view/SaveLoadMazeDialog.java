package view;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SaveLoadMazeDialog extends Dialog{
	String message1;
	String message2;
	String type;
	GUI gui;
	Shell win;
    /**
     * SaveLoadMazeDialog constructor
     * @param parent
     * @param style
     * @param message1
     * @param message2
     */
    public SaveLoadMazeDialog (GUI g,Shell parent, int style,String type, String message1,String message2) {
    	super(parent, style);
    	this.gui = g;
    	win = new Shell();
        this.message1 = message1;
        this.message2 = message2;
    }
    /**
     * SaveLoadMazeDialog constructor
     * @param parent
     * @param style
     * @param message1
     * @param message2
     */
    public SaveLoadMazeDialog (GUI g,Shell parent,String type, String message1,String message2) {
    	super (parent, 0);
    	this.type = type;
    	win = new Shell();
    	this.gui = g;
        this.message1 = message1;
        this.message2 = message2;
    }
    /**
     * open the dialog
     */
    public void open () {
        //win = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        win.setText(type);
        win.setSize(400, 200);
        
        win.setLayout(new GridLayout(2, false));
        
        Label label1 = new Label(win, SWT.NONE);
		label1.setText(message1);
		label1.setBounds(25, 5, 250, 60);
		
		Text text1 = new Text(win, SWT.SINGLE | SWT.BORDER);
		text1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		
        Label label2 = new Label(win, SWT.NONE);
		label2.setText(message2);
		label2.setBounds(25, 5, 250, 60);
		
		Text text2 = new Text(win, SWT.SINGLE | SWT.BORDER);
		text2.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Button continueButton = new Button(win, SWT.PUSH);
		continueButton.setBounds(100, 70, 60, 25);
		continueButton.setText("Continue");
		continueButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true,1, 1));
		
		continueButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				if(type.equals("Save Maze"))
				{
					String [] str = new String[3];
					str = ("saveMaze " + text1.getText() + " " + text2.getText()).split(" ",2);
					gui.notifyMe(str);
				}
				else
				{
					String [] str = new String[3];
					str = ("loadMaze " + text1.getText() + " " + text2.getText()).split(" ",2);
					gui.notifyMe(str);
				}
				win.dispose();
				
				
			}
		});
		
        win.open();
    }
}
