package presenter;
/**
 * FileSize class - extends the CommonComand
 * manage the calculate size of file that include maze3d,
 * ask from the model to calculate the file size
 * @author Eldar , Ofek
 */
public class FileSize extends CommonCommand{

	/**
	 * FileSize constructor
	 * @param controller - get the Controller to work with him
	 */
	public FileSize(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String str) {
		presenter.getModel().fileSize(str);
	}

}
