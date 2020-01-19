package gameClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

public interface MyGame {
	/**
	 * 
	 * @param scenario_num
	 */
	public  void initGame(int scenario_num) ;

	/**
	 * 
	 * @return
	 */
	public  List<edge_data> setBots();
	
	/**
	 * 
	 * @param game
	 */
	public  void moveRobot();

/**
 * 
 * @return
 */
	public int setPath();

	/**
	 * 
	 * @return
	 */
	public graph getGraph();
	/**
	 * 
	 * @return
	 */
	public ArrayList<Fruit> getFruits();
	/**
	 * 
	 */
	public Collection<Bots> getRobotes();
	/**
	 * 
	 * @return
	 */
	public game_service getGame();
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setXY(double x, double y);
	
	
	
	

}

