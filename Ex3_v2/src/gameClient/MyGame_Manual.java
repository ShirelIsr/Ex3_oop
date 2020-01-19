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
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import utils.Point3D;

public  class MyGame_Manual implements MyGame {
	game_service game;
	private boolean flag=false;
	private int botToMove;
	ArrayList <Fruit> _fruit ;
	HashMap <Integer,Bots> Robots ;
	graph _graph;
	private double x,y;
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
			while((i<rs))
			{
				if(it.hasNext())
					game.addRobot(it.next().getSrc());
				else
				{
					int rnd =(int)(Math.random()*_graph.nodeSize());
					game.addRobot(rnd);
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
		catch (Exception e) {
			e.printStackTrace();
		}
	}
/**
 * Returns a list of the fruit locations, allowing the bots to be strategically placed.
 */

	@Override
	public List<edge_data> setBots() {
		Iterator <Fruit> it=_fruit.iterator();
		ArrayList <edge_data> nodes=new ArrayList<edge_data>();
		while(it.hasNext())
		{
			nodes.add(it.next().getEdge());
			//System.out.println(s);
		}
		return  nodes;
	}
/**
 * Allows the robots to move by the desired algorithm.
 */
	@Override
	public void moveRobot() {
		List<String> log = game.move();
		if(log!=null) {
			try {
				int dest = setPath();
				if(dest!=-1) {	
					Bots rb=Robots.get(botToMove);
					if(rb!=null)
					{
						System.out.println("you choose to move robot :"+rb.getId()+"move to "+dest);
						game.chooseNextEdge(rb.getId(), dest);
						game.move();
					}
				}
				_fruit.clear();
				_fruit=new ArrayList <Fruit>();
				Iterator<String> f_iter = game.getFruits().iterator();
				while(f_iter.hasNext())
				{
					Fruit f=new Fruit(_graph);
					f.initFruit(f_iter.next());
					_fruit.add(f);	 
				}
				Robots.clear();
				List<String> botsStr = game.getRobots();
				for (String string : botsStr) {
					Bots ber = new Bots();
					//System.out.println(string);
					ber.initBot(string);
					//	System.out.println(string);
					Robots.put(ber.getId(), ber);
				}

			}
			catch (JSONException e) {e.printStackTrace();}
		}


	}
/**
 *  Updates the location of user-clicked points,
 *   when a robot is clicked Updates the robottomove field and if a relevant vertex is moved it returns the code number.
 */
	@Override
	public int setPath() {
		if (!flag)
		{
			Collection<Bots> robots = Robots.values();
			for (Bots b : robots) 
			{
				Point3D p=b.getLocation();
				double dist=p.distance2D(new Point3D(x,y));
				if(dist<=0.0005)
				{
					System.out.println(b.getId());
					//System.out.println(x+""+y);
					x=y=0;
					flag=true;

					botToMove= b.getId();
					return -1;
				}
			}
			return -1;
		} else {
			int b=Robots.get(botToMove).getSrc();
			//	System.out.println(b);
			Collection<edge_data> edges =_graph.getE(b);
			for (edge_data n : edges) 
			{
				Point3D p=_graph.getNode(n.getDest()).getLocation();
				double dist=p.distance2D(new Point3D(x,y));
				x=y=0;
				if(dist<=0.0007)
				{
					//x=y=0;
					flag=false;
					System.out.println(x+""+y);
					return n.getDest();
				}
			}
		}
		return -1;

	}

	public void setXY(double x,double y)
	{
		this.x=x;
		this.y=y;
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
}
