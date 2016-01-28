package presenter;
/**
 * interface of all the commands
 * @author Eldar , Ofek
 *
 */
public interface Command {
	/**
	 * <strong>doCommand</strong>
	 * <p>
	 * <code>public void doCommand(String str)</code>
	 * <p>
	 * The method doCommand is using different algorithms 
	 * @param param - The content of the parameters that the command will
	 * need to make his mission
	 */
	void doCommand(String str);
}
