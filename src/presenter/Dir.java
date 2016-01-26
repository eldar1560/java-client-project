package presenter;
/**
 * extends the CommonComand
 * manage the dir command that display the list and the files in folder.
 * @author Eldar , Ofek
 */
public class Dir extends CommonCommand{
	/**
	 * Dir constructor
	 * @param controller - get the controller to work with him
	 */
	public Dir(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String str) {
		this.presenter.getModel().dir(str);
	}

}
