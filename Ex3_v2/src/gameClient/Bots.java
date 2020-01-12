package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class Bots {
	int id;
	Point3D locaiton ;
	double speed;
	node_data src;
	node_data dest;
	graph g ;


	public Bots(graph g)
	{
		this.id=-1;
		this.locaiton = null;
		this.speed =-1;
		this.src=null;
		this.dest=null;
		this.g=g;
	}

	public Bots(int id,Point3D locaiton,graph g,double speed )
	{
		this.id=id;
		this.locaiton = locaiton;
		this.speed =speed;
		this.src=null;
		this.dest=null;
		this.g=g;
	}
	//{"Robot":{"id":0,"value":0.0,"src":0,"dest":-1,"speed":1.0
	//,"pos":"35.18753053591606,32.10378225882353,0.0"}}
	public void initRobot(String json) throws JSONException
	{
		JSONObject obj = new JSONObject(json);
		JSONObject Robots = obj.getJSONObject("Robot");
		this.id=Robots.getInt("id");
		this.speed=Robots.getInt("speed");
		int src1=Robots.getInt("src");
		if(g.getNode(src1)!= null)
			this.src=g.getNode(src1);
		int dst =Robots.getInt("dest");
		if(dst>-1)
		{
			if(g.getNode(dst)!= null)
				this.src=g.getNode(dst);
		}
		 String pos= Robots.getString("pos");
		 String str[]=pos.split(",");
		 this.locaiton=new Point3D(Double.parseDouble(str[0]),Double.parseDouble(str[1]),Double.parseDouble(str[2]));
	
	}

	public int getId()
	{
		return this.id;
	}

	public  Point3D getlocaiton()
	{
		return this.locaiton; 
	}

	public  void setlocaiton(Point3D l)
	{
		this.locaiton=l; 
	}

	public  void setCurNode()
	{

	}






}
