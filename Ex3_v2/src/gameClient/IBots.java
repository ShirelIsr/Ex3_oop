package gameClient;

import java.util.List;

import org.json.JSONException;

import dataStructure.node_data;
import utils.Point3D;

public interface IBots {
	/**
	 * 
	 * @param json
	 * @throws JSONException
	 */
	public void initBot (String json) throws JSONException;
	/**
	 * 
	 * @param l
	 */
	public void setLocaiton(Point3D l);
	/**
	 * 
	 * @return
	 */
	public Point3D getLocaiton();
	/**
	 * 
	 * @param src
	 */
	public  void setSrc(int src);
	/**
	 * 
	 * @return
	 */
	public int getSrc();
	/**
	 * 
	 * @param dest
	 */
	public  void setDest(int dest);
	/**
	 * 
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
	 * 
	 * @return
	 */
	public List<node_data> getPath();
}
