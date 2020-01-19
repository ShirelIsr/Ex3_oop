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
	private int botToMove;
	ArrayList <Fruit> _fruit ;
	List <edge_data> targets ;
	HashMap <Integer,Bots> Robots ;
	graph _graph;
	private double x,y;
	private long timeToMov;
	int i;

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
			this.timeToMov=game.timeToEnd();

		}
		catch (Exception e) {	e.printStackTrace();}
	}


	@Override
	public List<edge_data> setBots() {
		
		Iterator <Fruit> it=_fruit.iterator();
		ArrayList <edge_data> Edges=new ArrayList<edge_data>();
		while(it.hasNext())
		{
			Edges.add(it.next().getEdge());
		}
		return  Edges;
	}

	@Override
	public void moveRobot() {
		try {
			_fruit=new ArrayList <Fruit>();
			_fruit.clear();
			Iterator<String> f_iter = game.getFruits().iterator();
			while(f_iter.hasNext())
			{
				Fruit f=new Fruit(_graph);
				f.initFruit(f_iter.next());
				_fruit.add(f);	 
			}
			if(targets != null) targets.clear();
			targets=new ArrayList<edge_data>();			
			targets=setBots();
			Collection<Bots> robots =Robots.values();
			for (Bots b : robots) 
			{
				if(b.dest==-1)
				{
					botToMove=b.getId();
					i+=setPath();
					Iterator <node_data> it= b.getPath().iterator();
					while(it.hasNext())
					{
						int dest=it.next().getKey();
						game.chooseNextEdge(b.getId(), dest);
					}
					b.setPath(null);
					b.setDest(-1);
				}
			}
			if(timeToMov-game.timeToEnd()<100)
			{
				timeToMov=game.timeToEnd();
				game.move();
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


	@Override
	public int setPath() {
		edge_data l=null;
		edge_data remove=null;
		graph_algorithms gg=new Graph_Algo(_graph);
		double min=Double.MAX_VALUE;
		Iterator<edge_data> it =targets.iterator();
		IBots b=Robots.get(botToMove);
		while(it.hasNext())
		{
			l=it.next();
			if(l.getSrc()==b.getSrc())
			{
				double temp=l.getWeight();
				if(temp<min)
				{
					min=temp;
					remove=l;
					ArrayList<node_data> tempPath=new ArrayList<node_data> ();
					tempPath.add(_graph.getNode(l.getSrc()));
					tempPath.add(_graph.getNode(l.getDest()));
					b.setPath(tempPath);
				}
			}
			else
			{
				double temp=gg.shortestPathDist(b.getSrc(),l.getSrc());
				temp+=l.getWeight();
				if(temp<min)
				{
					min=temp;
					remove=l;
					b.setPath(gg.shortestPath(b.getSrc(),l.getSrc()));
					b.getPath().add(_graph.getNode(l.getDest()));
				}	
			}
		}
		targets.remove(remove);
		return b.getPath().size();
	}

	@Override
	public graph getGraph() {
		return this._graph;
	}

	@Override
	public ArrayList<Fruit> getFruits() {
		return this._fruit;
	}

	@Override
	public Collection<Bots> getRobotes() {
		return Robots.values();
	}

	@Override
	public game_service getGame() {
		return this.game;
	}

	@Override
	public void setXY(double x, double y) {
		this.x=x;
		this. y=y;

	}

}
