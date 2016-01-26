package presenter;
/**
 * interface of all the commands
 * @author Eldar , Ofek
 *
 */
public interface Command {
	/**
	 * the command function
	 * @param str the parameter to the Model function
	 */
	void doCommand(String str);
}
