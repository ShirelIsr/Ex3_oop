package gameClient;

import java.util.Collection;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class Fruit {
	int type;
	double value;
	Point3D location;
	edge_data edge ;
	graph g;
	public final double EPSILON = 0.0000001;



	public Fruit ( int type,Point3D locaiton, double value,graph g)
	{
		this.type=type;
		this.location = locaiton;
		this.value=value;
		this.edge=null;
	}


	public Fruit(graph gui_Graph) {
		this.g=gui_Graph;
		this.edge=null;
		this.location=null;
		this.type=0;
		this.value=-1;
	}

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

	public void setType(int type)
	{
		this.type=type;
	}

	public int getType()
	{
		return this.type;
	}

	public void setLocaiton(Point3D l)
	{
		this.location=l;
	}

	public Point3D getLocation()
	{
		return this.location; 
	}

	public void setvalue(double value)
	{
		this.value=value;
	}

	public double getValue()
	{
		return this.value;
	}
	public void setEdge()
	{
		Collection<node_data> s =g.getV();
		for (node_data node : s) 
		{
			Collection<edge_data> edges =g.getE(node.getKey());
			for(edge_data e : edges)
			{
				if(pointOn(g.getNode(e.getSrc()).getLocation(),g.getNode(e.getDest()).getLocation(),this.location))
					this.edge=e;
			}
		}
		if (this.edge==null) throw new RuntimeException("ERR, the point is'nt on the graph");
	}
	public edge_data getEdge()
	{
		return this.edge;
	}

	public boolean pointOn(Point3D a , Point3D b , Point3D c) {
		if (Math.abs((a.distance2D(c) + b.distance2D(c)-a.distance2D(b)))<=EPSILON)
			return true; // C is on the line.
		return false; 
	}

}
