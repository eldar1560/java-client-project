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

/**
 * GeneratWindow class - extends Dialog
 * @author Eldar , Ofek
 */
public class GenerateWindow extends Dialog{
	Shell win;
	GUI gui;
	/**
	 * constructor of GeneratWindow 
	 */
	public GenerateWindow(GUI gui, Shell shell) {
		super(shell);
		win = new Shell();
		win.setText("Generate 3d Maze");
		this.gui = gui;
	}
	/**
	 * open the window 
	 */
	public void open() {
		win.setLayout(new GridLayout(2, false));
		win.setSize(200, 150);
		
		Button defualt = new Button(win,SWT.RADIO);
		Button custom = new Button(win,SWT.RADIO);
		
		defualt.setText("Defualt generate");
		defualt.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		custom.setText("Custom generate");
		custom.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, false, 2, 1));
		
		Label name = new Label(win,SWT.NULL);
		name.setText("Maze name:");
		name.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true, 1, 1));
		
		Text nameText = new Text(win, SWT.SINGLE | SWT.BORDER);
		nameText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		
		Label y = new Label(win,SWT.NULL);
		y.setText("Y Size:");
		y.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true, 1, 1));
		
		Text yText = new Text(win, SWT.SINGLE | SWT.BORDER);
		yText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		yText.setText("floors");
		
		Label z = new Label(win,SWT.NULL);
		z.setText("Z Size:");
		z.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true, 1, 1));
		
		Text zText = new Text(win, SWT.SINGLE | SWT.BORDER);
		zText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		zText.setText("rows");
		
		Label x = new Label(win,SWT.NULL);
		x.setText("X Size:");
		x.setLayoutData(new GridData(SWT.LEFT, SWT.LEFT, true, true, 1, 1));
		
		Text xText = new Text(win, SWT.SINGLE | SWT.BORDER);
		xText.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		xText.setText("columns");

		
		Button okButton = new Button(win, SWT.PUSH);
		okButton.setText("OK");
		okButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 2));
		
		Button cancelButton = new Button(win, SWT.PUSH);
		cancelButton.setText("Cancel");
		cancelButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 2));
		
		Listener editYListener = new Listener(){
			
			@Override 
			public void handleEvent(Event arg0){
				yText.setText("");
			}
		};
		yText.addListener(SWT.MouseEnter | SWT.TAB, editYListener);
		
		Listener editZListener = new Listener(){
			
			@Override 
			public void handleEvent(Event arg0){
				zText.setText("");
			}
		};
		zText.addListener(SWT.MouseEnter | SWT.TAB, editZListener);
		
		Listener editXListener = new Listener(){
			
			@Override 
			public void handleEvent(Event arg0){
				xText.setText("");
			}
		};
		xText.addListener(SWT.MouseEnter | SWT.TAB, editXListener);
		
		Listener defualtListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				nameText.setEnabled(false);
				xText.setEnabled(false);
				yText.setEnabled(false);
				zText.setEnabled(false);	
			}
		};
		Listener okListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				if(defualt.getSelection()){
					gui.notifyMe("generate3dMaze");
					gui.redraw();
					win.dispose();
				}
				else if(custom.getSelection()){
					boolean isOK = true;
					boolean sizeIsOK = true;
					boolean isNumber = true;
					try{
						Integer.parseInt(yText.getText());
						Integer.parseInt(zText.getText());
						Integer.parseInt(xText.getText());
					}
					catch (NumberFormatException e){
						isNumber = false;
						DialogMessage dm = new DialogMessage(win, "X/Y/Z only numbers");
						dm.open();	
					}
					
					if(isNumber){
						if(nameText.getText().equals(""))
							isOK = false;
						if(xText.getText().equals(""))
							isOK = false;
						else if (Integer.parseInt(xText.getText()) < 3)
							sizeIsOK = false;
							
						if(yText.getText().equals(""))
							isOK = false;
						else if (Integer.parseInt(yText.getText()) < 3)
							sizeIsOK = false;
						
						if(zText.getText().equals(""))
							isOK = false;
						else if (Integer.parseInt(zText.getText()) < 3)
							sizeIsOK = false;
						
						if(sizeIsOK && isOK){
							gui.notifyMe(("generate3dMaze " + 
									nameText.getText() + " " + 
									yText.getText() + " " +
									zText.getText()+ " " +
									xText.getText()).split(" ",2));
							gui.redraw();
							win.dispose();
						}
						else if(!sizeIsOK){
							DialogMessage dm = new DialogMessage(win, "X/Y/Z < 3!");
							dm.open();
						}	
						else if(!isOK){
							DialogMessage dm = new DialogMessage(win, "Enter all values");
							dm.open();
						}
					}					
				}
			}
		};
		
		Listener cancelListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				win.dispose();
			}
		};
		
		Listener customListener = new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				nameText.setEnabled(true);
				xText.setEnabled(true);
				yText.setEnabled(true);
				zText.setEnabled(true);	
			}
		};
		

		defualt.addListener(SWT.Selection, defualtListener);
		custom.addListener(SWT.Selection, customListener);
		okButton.addListener(SWT.Selection, okListener);
		cancelButton.addListener(SWT.Selection, cancelListener);
		
		nameText.setEnabled(false);
		xText.setEnabled(false);
		yText.setEnabled(false);
		zText.setEnabled(false);
		
		defualt.setSelection(true);
		
		win.pack();
		win.open();
	}
}
