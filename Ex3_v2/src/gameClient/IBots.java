package gameClient;

import java.util.List;

import org.json.JSONException;

import dataStructure.node_data;
import utils.Point3D;

public interface IBots {
	/**
	 *  Initializes and realizes a robot from a json file.
	 * @param json
	 * @throws JSONException
	 */
	public void initBot (String json) throws JSONException;
	/**
	 * Receives the location of the robot.
	 * @param l
	 */
	public void setLocation(Point3D l);
	/**
	 * Returns the location of the robot.
	 * @return
	 */
	public Point3D getLocation();
	/**
	 * Gets the robot vertex vertically on the graph.
	 * @param src
	 */
	public  void setSrc(int src);
	/**
	 * Returns the current vertex of the robot on the graph.
	 * @return
	 */
	public int getSrc();
	/**
	 * Gets the target vertex of the robot.
	 * @param dest
	 */
	public  void setDest(int dest);
	/**
	 * Returns the target vertex of the robot.
	 * @return
	 */
	public int getDest();
	/**
	 * 
	 * @param value
	 */
	public void addV(double value);
	/**
	 * 
	 * @return
	 */
	public double getV();
	/**
	 * 
	 * @return
	 */
	public int getId();
	/**
	 * 
	 * @param Path
	 */
	public void setPath(List<node_data> Path);
	/**
	 * Returns the list of vertices to the shortest path of a robot.
	 * @return
	 */
	public List<node_data> getPath();
}
