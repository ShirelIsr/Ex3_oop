package gameClient;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;
/**
 * this class is implements of IFruit interface
 * An interface representing the fruits of the game,
When collecting fruit is the goal of the game.
Each fruit is represented by: location, value and type,
 and it is also possible to get the rib on the graph on which the fruit is located.
 * @author shire
 *
 */
public class Fruit implements IFruit{
	int type;
	double value;
	Point3D location;
	edge_data edge ;
	graph g;
	int Tag;
	public final double EPSILON = 0.0000001;



	public Fruit ( int type,Point3D locaiton, double value,graph g)
	{
		this.type=type;
		this.location = locaiton;
		this.value=value;
		this.edge=null;
		this.Tag=0;
	}


	public Fruit(graph gui_Graph) {
		this.g=gui_Graph;
		this.edge=null;
		this.location=null;
		this.type=0;
		this.value=-1;
		this.Tag=0;
	}
	/**
	 *  Initializes the fruit from the resulting json file data.
	 * @param json
	 * @throws JSONException
	 */
	public void initFruit (String json) throws JSONException
	{
		JSONObject obj = new JSONObject(json);
		JSONObject Fruits = obj.getJSONObject("Fruit");
		String pos= Fruits.getString("pos");
		String str[]=pos.split(",");
		this.type=Fruits.getInt("type");
		this.value=Fruits.getDouble("value");
		Point3D p=new Point3D(Double.parseDouble(str[0]),Double.parseDouble(str[1]),Double.parseDouble(str[2]));
		this.location=p;
		setEdge();
	}
	/**
	 * Updates the fruit type.
	 * @param type
	 */
	public void setType(int type)
	{
		this.type=type;
	}
	/**
	 * Returns the type of fruit.
	 * @return
	 */
	public int getType()
	{
		return this.type;
	}
	/**
	 * Updates the fruit location.
	 * @param l
	 */
	public void setLocaiton(Point3D l)
	{
		this.location=l;
	}
	/**
	 *  Returns the location of the fruit.
	 * @return
	 */
	public Point3D getLocation()
	{
		return this.location; 
	}
	/**
	 *  Updates the fruit score value.
	 * @param value
	 */
	public void setvalue(double value)
	{
		this.value=value;
	}
	/**
	 * Returns the fruit score value.
	 * @return
	 */
	public double getValue()
	{
		return this.value;
	}
	/**
	 * 
	 * Mounts the edge on which the fruit is located.
	 */
	public void setEdge()
	{
		Collection<node_data> s =g.getV();
		for (node_data node : s) 
		{
			Collection<edge_data> edges =g.getE(node.getKey());
			for(edge_data e : edges)
			{
				int src =e.getSrc();
				int dest=e.getDest();
				if(pointOn(g.getNode(src).getLocation(),g.getNode(dest).getLocation(),this.location))
				{
					if(this.type==-1)
					{
						if(src>dest)
							this.edge=e;
					}
					else if(this.type==1)
					{
						if(src<dest)
							this.edge=e;
					}
				}
			}
		}
		if (this.edge==null) throw new RuntimeException("ERR, the point is'nt on the graph");
	}
	/**
	 *  Returns the edge on which the fruit is located
	 * @return
	 */
	public edge_data getEdge()
	{
		return this.edge;
	}
	/**
	 * Auxiliary function for the fruit position on a rib, 
	 * the function calculates whether a point is on a straight line and returns true and false respectively.
	 * @param a
	 * @param b
	 * @param c
	 * @return
	 */
	public void setTag(int t)
	{
		this.Tag=t;
	}
	/**
	 * 
	 * @return
	 */
	public int getTag()
	{
		return this.Tag;
	}
	public boolean pointOn(Point3D a , Point3D b , Point3D c) {
		if (Math.abs((a.distance2D(c) + b.distance2D(c)-a.distance2D(b)))<=EPSILON)
			return true; // C is on the line.
		return false; 
	}



}
