package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class Bots {
	int src ,dest ,id,speed;
	Point3D locaiton ;
	double value;

	public Bots()
	{
		src=dest=id=speed=-1;
		this.locaiton=null;
		this.value=0;
	}

	public void initBot (String json ) throws JSONException
	{
		JSONObject obj = new JSONObject(json);
		JSONObject Robots = obj.getJSONObject("Robot");
		this.id=Robots.getInt("id");
		this.speed=Robots.getInt("speed");
		this.src=Robots.getInt("src");
		this.dest=Robots.getInt("dest");
		String pos= Robots.getString("pos");
		String str[]=pos.split(",");
		this.locaiton=new Point3D(Double.parseDouble(str[0]),Double.parseDouble(str[1]),Double.parseDouble(str[2]));
	}

	public void setLocaiton(Point3D l)
	{
		this.locaiton=l;
	}

	public Point3D getLocaiton()
	{
		return this.locaiton;
	}

	public  void setSrc(int src)
	{
		this.src=src;
	}

	public int getSrc()
	{
		return this.src;
	}

	public  void setDest(int dest)
	{
		this.dest=dest;
	}

	public int getDest()
	{
		return this.dest;
	}

	public void addV(double value)
	{
		this.value+=value;
	}

	public double getV()
	{
		return this.value;
	}
	public int getId()
	{
		return this.id;
	}

}
