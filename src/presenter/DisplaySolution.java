package presenter;

/**
 * extends the CommonComand
 * manage the display of the solution 
 * @author Eldar , Ofek
 */
public class DisplaySolution extends CommonCommand{
	/**
	 * DisplaySolution constructor
	 * @param controller - set the controller to work with him
	 */
	public DisplaySolution(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String str) {
		presenter.getModel().displaySolution(str);
	}

}
