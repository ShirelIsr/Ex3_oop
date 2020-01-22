package gameClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;

public class MyGame_Automaticly implements MyGame{

	game_service game;
	ArrayList <Fruit> _fruit ;
	List <edge_data> targets ;
	HashMap <Integer,Bots> Robots ;
	graph _graph;
	private int botToMove;
	private double x,y;
	private int maxSpeed=1;
	private long sleep;
	int i;
	graph_algorithms algo_graph;

	/**
	 * Gets the game number and creates the game based on server data, everything is called from the json file.
	 */
	@Override
	public void initGame(int scenario_num) {
		game = Game_Server.getServer(scenario_num); // you have [0,23] games
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		this._graph=gg;
		algo_graph=new Graph_Algo(_graph);
		Iterator<String> f_iter = game.getFruits().iterator();
		if(_fruit==null)
		{
			_fruit=new ArrayList <Fruit>();
		}
		else _fruit.clear();
		while(f_iter.hasNext())
		{
			try
			{
				Fruit f=new Fruit(_graph);
				f.initFruit(f_iter.next());
				_fruit.add(f);	 
			}
			catch (JSONException e) {e.printStackTrace();}
		}
		String info = game.toString();
		JSONObject line;
		try {
			line = new JSONObject(info);
			JSONObject ttt = line.getJSONObject("GameServer");
			int rs = ttt.getInt("robots");
			if(Robots ==null)
				Robots=new HashMap<Integer,Bots>();
			else Robots.clear();
			int i=0;
			Iterator <edge_data> it =setBots().iterator();
			while(i<rs)
			{
				if(it.hasNext())
				{
					game.addRobot(it.next().getSrc());
				}
				else
				{
					int src=(int)(Math.random()*_graph.getV().size());
					game.addRobot(src);
				}
				i++;
			}
			List<String> Bots = game.getRobots();
			for (String str : Bots)
			{
				System.out.println(str);
				Bots b= new Bots ();
				b.initBot(str);
				Robots.put(b.getId(), b);
			}

		}
		catch (Exception e) {	e.printStackTrace();}

		if(scenario_num<=11)
			sleep =122;
		else if(scenario_num<=20)
			sleep =120;
		else
			sleep=55;

	}
	/**
	 * Returns a list of the fruit locations, allowing the bots to be strategically placed.
	 */

	@Override
	public List<edge_data> setBots() {
		ArrayList <edge_data> Edges=new ArrayList<edge_data>();
		Edges.clear();
		for(Fruit f :_fruit)
		{
			if(f.getTag()==0)
				Edges.add(f.getEdge());

		}
		return  Edges;
	}
	/**
	 * Allows the robots to move by the desired algorithm.
	 */
	@Override
	public void moveRobot() {
		try {
			Collection<Bots> robots =Robots.values();
			for (Bots b : robots) 
			{
				if(b.getDest()==-1)
				{
					botToMove=b.getId();
					if(setPath()>0)
					{
						Iterator <node_data> it= b.getPath().iterator();
						int src=0;
						if(it.hasNext())
						{
							src=it.next().getKey();
							game.chooseNextEdge(b.getId(),src);

						}
						while(it.hasNext())
						{
							int dest=it.next().getKey();
							game.chooseNextEdge(b.getId(), dest);
							for(Fruit f :_fruit)
							{
								if((f.getEdge().getSrc()==src) &&(f.getEdge().getDest()==dest))
									f.setTag(100);
							}
							src=dest;
						}
						b.setPath(null);
					}
				}
				if(b.getSpeed()>maxSpeed)
				{
					this.maxSpeed=b.getSpeed();
					sleep--;
				}
			}
			_fruit=new ArrayList <Fruit>();
			_fruit.clear();
			Iterator<String> f_iter = game.getFruits().iterator();
			while(f_iter.hasNext())
			{
				Fruit f=new Fruit(_graph);
				f.initFruit(f_iter.next());
				_fruit.add(f);	 
			}
			Robots.clear();
			List<String> botsStr = game.getRobots();
			for (String string : botsStr)
			{
				Bots ber = new Bots();
				ber.initBot(string);
				Robots.put(ber.getId(), ber);
			}
		}
		catch (JSONException e) {e.printStackTrace();}

	}
	/**
	 * Applies the shortest path to "fruit", inserting into a field in the desired robot
	 */

	@Override
	public int setPath() {
		IBots b=Robots.get(botToMove);
		if(b.getPath()!=null) return -1;
		targets=new ArrayList<edge_data>();
		targets.clear();
		targets=setBots();
		edge_data tmpE=null;
		double min=Double.MAX_VALUE;
		for (edge_data l:targets)
		{
			double temp=algo_graph.shortestPathDist(b.getSrc(),l.getSrc())+l.getWeight();
			if(temp<min)
			{
				min=temp;
				tmpE=l;
			}	
		}
		List<node_data> tmpP=new ArrayList<node_data>();
		tmpP.clear();
		tmpP=algo_graph.shortestPath(b.getSrc(),tmpE.getSrc());
		tmpP.add(_graph.getNode(tmpE.getDest()));
		b.setPath(tmpP);
		return tmpP.size();
	}
	/**
	 * Returns the graph on which the game is held.
	 */
	@Override
	public graph getGraph() {
		return this._graph;
	}
	/**
	 * Returns the list of "fruits".
	 */
	@Override
	public ArrayList<Fruit> getFruits() {
		return this._fruit;
	}
	/**
	 *  Returns a collection of bots that play the game.
	 */
	@Override
	public Collection<Bots> getRobotes() {
		return Robots.values();
	}
	/**
	 * Returns the game
	 */

	@Override
	public game_service getGame() {
		return this.game;
	}


	@Override
	public void setXY(double x, double y) {
		this.x=x;
		this. y=y;

	}
	Thread MoveT;
	@Override
	public void MoveThread()
	{
		//	System.out.println("123");
		MoveT = new Thread(new Runnable() {

			@Override
			public void run() {
				while(game!=null)
				{
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					game.move();
				}
			}
		});
		MoveT.start();
	}


}
