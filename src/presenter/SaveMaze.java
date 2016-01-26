package presenter;
/**
 * this class extends CommonCommand
 * save maze in the file you want
 * @author Eldar, Ofek
 *
 */
public class SaveMaze extends CommonCommand{
	/**
	 * constructor
	 * call super with the Controller that it get
	 * @param controller
	 */
	public SaveMaze(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String str) {
		String[] parm = str.split(" ");
		if(parm.length != 2)
			presenter.setMessage("Invalid Command");
		else
			presenter.getModel().saveMaze(parm[0], parm[1]);
		
		
	}
	
}
