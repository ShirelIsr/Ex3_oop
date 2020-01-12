package gameClient;

import org.json.JSONException;
import org.json.JSONObject;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import utils.Point3D;

public class Fruit {
	 int type;
	 double value;
	 Point3D locaiton;
	 
	 public Fruit ( )
	 {
		 this.type=0;
		 this.locaiton = null;
		 this.value=0;
	 }
	 
	 public Fruit ( int type,Point3D locaiton, double value)
	 {
		 this.type=type;
		 this.locaiton = locaiton;
		 this.value=value;
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
			this.locaiton=p;
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
		 this.locaiton=l;
	 }
	 
	 public  Point3D getlocaiton()
	 {
		 return this.locaiton; 
	 }
	 
	 public void setvalue(double value)
	 {
		 this.value=value;
	 }
	 
	 public double getValue()
	 {
		 return this.value;
	 }
	

}
