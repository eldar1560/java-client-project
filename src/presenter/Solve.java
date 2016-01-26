package presenter;
/**
 * this class extends CommonCommand
 * give us a solve of the maze
 * @author Eldar, Ofek
 *
 */
public class Solve extends CommonCommand{
	/**
	 * constructor
	 * call super with the Controller that it get
	 * @param controller
	 */
	public Solve(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String str) {
		String[] parm = str.split(" ");
		
		if(parm.length != 2)
		{
			if(parm.length == 1)
				presenter.getModel().solve();
			else
				presenter.setMessage("Invalid Command");
		}
		else
			presenter.getModel().solve(parm[0],parm[1]);
	}

}
