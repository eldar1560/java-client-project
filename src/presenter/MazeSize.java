package presenter;
/**
 * this class extends CommonCommand
 * Get the maze size (byte)
 * @author Eldar, Ofek
 *
 */
public class MazeSize extends CommonCommand {
	/**
	 * MazeSize constructor
	 * @param controller - get the Controller to work with him
	 */
	public MazeSize(Presenter presenter) {
		super(presenter);
	}

	@Override
	public void doCommand(String str) {
		presenter.getModel().mazeSize(str);
	}

}
