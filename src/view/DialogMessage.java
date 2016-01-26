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
/**
 * DialogMessage - extends Dialog
 * @author Eldar , Ofek
 */
public class DialogMessage extends Dialog{
	Object result;
	String messege;
    /**
     * DialogMessage constructor
     * @param parent
     * @param style
     * @param messege
     */
    public DialogMessage (Shell parent, int style, String messege) {
    	super (parent, style);
        this.messege = messege;
    }
    /**
     * DialogMessage constructor
     * @param parent
     * @param style
     * @param messege
     */
    public DialogMessage (Shell parent, String messege) {
    	super (parent, 0);
        this.messege = messege;
    }
    /**
     * open the dialog
     */
    public Object open () {
        Shell parent = getParent();
        Shell shell = new Shell(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setText("Messege box");
        shell.setSize(350, 150);
        shell.setLayout(new GridLayout(1, false));
        
        Label label = new Label(shell, SWT.NONE);
		label.setText(messege);
		label.setBounds(25, 5, 300, 60);
		label.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		Button continueButton = new Button(shell, SWT.PUSH);
		continueButton.setBounds(100, 70, 60, 25);
		continueButton.setText("Continue");
		continueButton.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, true, true, 1, 1));
		
		continueButton.addListener(SWT.Selection, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				shell.close();
			}
		});
		
        shell.open();
        /*Display display = parent.getDisplay();
        while (!shell.isDisposed()) {
        	if (!display.readAndDispatch()) display.sleep();
        }*/
        return result;
    }
}
