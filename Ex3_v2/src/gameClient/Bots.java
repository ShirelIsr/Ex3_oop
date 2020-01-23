package gameClient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
/**
 * this class is  implements IBots interface
 *the interface represents the robots in the game,
The robots are the players in the game and their goal is to collect as much fruit as possible.
Each robot is represented by its unique identity number,
 location, speed, and the number of points gained.
 * @author shire
 *
 */
public class Bots implements IBots{
	int src ,dest ,id,speed;
	Point3D location ;
	double value;
	List<node_data> Path=new ArrayList<node_data>();

	public Bots()
	{
		src=dest=id=speed=-1;
		this.location=null;
		this.value=0;
		Path =null;
	}
	/**
	 *  Initializes and realizes a robot from a json file.
	 */
	@Override
	public void initBot(String json) throws JSONException
	{
		JSONObject obj = new JSONObject(json);
		JSONObject Robots = obj.getJSONObject("Robot");
		this.id=Robots.getInt("id");
		this.speed=Robots.getInt("speed");
		this.src=Robots.getInt("src");
		this.dest=Robots.getInt("dest");
		String pos= Robots.getString("pos");
		String str[]=pos.split(",");
		this.location=new Point3D(Double.parseDouble(str[0]),Double.parseDouble(str[1]),Double.parseDouble(str[2]));
	}
	/**
	 * Receives the location of the robot.
	 */
	@Override
	public void setLocation(Point3D l)
	{
		this.location=l;
	}
	/**
	 * Returns the location of the robot.
	 */
	@Override
	public Point3D getLocation()
	{
		return this.location;
	}
	/**
	 * Gets the robot vertex vertically on the graph.
	 */
	@Override
	public  void setSrc(int src)
	{
		this.src=src;
	}
	/**
	 * Returns the current vertex of the robot on the graph.
	 */
	@Override
	public int getSrc()
	{
		return this.src;
	}
	/**
	 * Gets the target vertex of the robot.
	 */
	@Override
	public  void setDest(int dest)
	{
		this.dest=dest;
	}
	/**
	 * Returns the target vertex of the robot.
	 */
	@Override
	public int getDest()
	{
		return this.dest;
	}
	/**
	 * Updates the amount of points earned by the robot.
	 */
	@Override
	public void addV(double value)
	{
		this.value+=value;
	}
	/**
	 *  Returns the amount of points the robot has accumulated.
	 */
	@Override
	public double getV()
	{
		return this.value;
	}
	/**
	 * Returns the robot ID.
	 */
	
	@Override
	public int getId()
	{
		return this.id;
	}
	/**
	 *  Updates the list of vertices to the robot's path.
	 */
	@Override
	public void setPath(List<node_data> Path)
	{
		this.Path=Path;
	}
	/**
	 * Returns the list of vertices to the shortest path of a robot.
	 */
	@Override
	public List<node_data> getPath()
	{
		return this.Path;
	}
	@Override
	public int getSpeed() {
		return this.speed;
	}

}

	


