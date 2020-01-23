package gameClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Server.game_service;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
/**
 * This interface represents a simple game interface, where each game is represented by, 
 * the type of game taken from the game server and the graph (game board), 
 * in each game there are robots (represent players), and fruits where the main game purpose is the fruit collection.
 * @author shire
 *
 */
public interface MyGame {
	/**
	 * Gets the game number and creates the game based on server data, everything is called from the json file.
	 * @param scenario_num
	 */
	public  void initGame(int scenario_num) ;

	/**
	 * Returns a list of the fruit locations, allowing the bots to be strategically placed.
	 * @return
	 */
	public  List<edge_data> setBots();

	/**
	 * Allows the robots to move by the desired algorithm.
	 * @param game
	 */
	public  void moveRobot();

	/**
	 * 
	 * @return
	 */
	public int setPath();

	/**
	 * Returns the graph on which the game is held.
	 * @return
	 */
	public graph getGraph();
	/**
	 * Returns the list of "fruits".
	 * @return
	 */
	public ArrayList<Fruit> getFruits();
	/**
	 *  Returns a collection of bots that play the game.
	 */
	public Collection<Bots> getRobotes();
	/**
	 * Returns the game
	 * @return
	 */
	public game_service getGame();
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void setXY(double x, double y);
	/**
	 * this thread is using reduce the cost of moving the robot.

	 */
	public void MoveThread();





}

