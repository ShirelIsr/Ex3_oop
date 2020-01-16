package gameClient;

import org.json.JSONException;

import dataStructure.edge_data;
import utils.Point3D;

public interface IFruit {
	/**
	 * 
	 * @param json
	 * @throws JSONException
	 */
	public void initFruit (String json) throws JSONException;
	
	/**
	 * 
	 * @param type
	 */
	public void setType(int type);
	
	/**
	 * 
	 * @return
	 */
	public int getType();
	
	/**
	 * 
	 * @param l
	 */
	public void setLocaiton(Point3D l);
	
	/**
	 * 
	 * @return
	 */
	public Point3D getlocaiton();
	
	/**
	 * 
	 * @param value
	 */
	public void setvalue(double value);
	
	/**
	 * 
	 * @return
	 */
	public double getValue();
	
	/**
	 * 
	 */
	public void setEdge();
	
	/**
	 * 
	 * @return
	 */
	public edge_data getEdge();
}
