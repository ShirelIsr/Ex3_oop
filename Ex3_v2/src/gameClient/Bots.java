package gameClient;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

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
	
	@Override
	public void setLocation(Point3D l)
	{
		this.location=l;
	}
	
	@Override
	public Point3D getLocation()
	{
		return this.location;
	}
	
	@Override
	public  void setSrc(int src)
	{
		this.src=src;
	}
	
	@Override
	public int getSrc()
	{
		return this.src;
	}
	
	@Override
	public  void setDest(int dest)
	{
		this.dest=dest;
	}
	
	@Override
	public int getDest()
	{
		return this.dest;
	}
	
	@Override
	public void addV(double value)
	{
		this.value+=value;
	}
	
	@Override
	public double getV()
	{
		return this.value;
	}
	
	@Override
	public int getId()
	{
		return this.id;
	}
	
	@Override
	public void setPath(List<node_data> Path)
	{
		this.Path=Path;
	}
	
	@Override
	public List<node_data> getPath()
	{
		return this.Path;
	}

}

	


