package presenter;
/**
 * this class extends from CommonCommand 
 * Generate the 3d maze  
 * @author Eldar , Ofek
 *
 */
public class Generate3dMaze extends CommonCommand {
/**
 * constructor
 * call super with the Controller that it get
 * @param controller
 */
	public Generate3dMaze(Presenter presenter) {
		super(presenter);
	}
	@Override
	public void doCommand(String str) {
		String[] parm = str.split(" ");
		
		if(parm.length != 4)
		{
			if(parm.length == 1)
				presenter.getModel().generate3dMaze();
			else
				presenter.setMessage("Invalid Command");
		}
		else{
			int x = 0,y = 0,z = 0;
			try{
				y = Integer.parseInt(parm[1]);
				z = Integer.parseInt(parm[2]);
				x = Integer.parseInt(parm[3]);
			}
			catch (NumberFormatException e){
				presenter.setMessage("Invalid Command");
			}
			
			presenter.getModel().generate3dMaze(parm[0],y, z, x);
		}
	}

}
