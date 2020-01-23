package gameClient;

import org.json.JSONException;

import dataStructure.edge_data;
import utils.Point3D;
/**
 * An interface representing the fruits of the game,
When collecting fruit is the goal of the game.
Each fruit is represented by: location, value and type, and it is also possible 
to get the rib on the graph on which the fruit is located.
 * @author shire
 *
 */
public interface IFruit {
	/**
	 *  Initializes the fruit from the resulting json file data.
	 * @param json
	 * @throws JSONException
	 */
	public void initFruit (String json) throws JSONException;
	
	/**
	 * Updates the fruit type.
	 * @param type
	 */
	public void setType(int type);
	
	/**
	 * Returns the type of fruit.
	 * @return
	 */
	public int getType();
	
	/**
	 * Updates the fruit location.
	 * @param l
	 */
	public void setLocaiton(Point3D l);
	
	/**
	 *  Returns the location of the fruit.
	 * @return
	 */
	public Point3D getLocation();
	
	/**
	 *  Updates the fruit score value.
	 * @param value
	 */
	public void setvalue(double value);
	
	/**
	 * Returns the fruit score value.
	 * @return
	 */
	public double getValue();
	
	/**
	 * Mounts the edge on which the fruit is located.
	 */
	public void setEdge();
	
	/**
	 *  Returns the edge on which the fruit is located
	 * @return
	 */
	public edge_data getEdge();
	/**
	 * 
	 * @param t
	 */
	public void setTag(int t);
	/**
	 * 
	 * @return
	 */
	public int getTag();
	
}
