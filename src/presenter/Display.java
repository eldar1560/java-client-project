package presenter;
/**
 * extends the CommonComand
 * manage the display of the maze 
 * @author Eldar , Ofek
 */
public class Display extends CommonCommand{
	/**
	 * Display constructor
	 * @param controller - set the controller to work with him
	 */
	public Display(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String str) {
		presenter.getModel().display(str);
	}

}
