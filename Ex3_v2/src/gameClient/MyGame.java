//package gameClient;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import Server.Game_Server;
//import Server.game_service;
//import algorithms.Graph_Algo;
//import algorithms.graph_algorithms;
//import dataStructure.DGraph;
//import dataStructure.edge_data;
//import dataStructure.node_data;
//import utils.Point3D;
//
//public class MyGame {
//
//	public void initGame(int scenario_num) {
//		game = Game_Server.getServer(scenario_num); // you have [0,23] games
//		String g = game.getGraph();
//		DGraph gg = new DGraph();
//		gg.init(g);
//		xMin=Double.MIN_VALUE;
//		xMax=Double.MAX_VALUE;
//		yMin=Double.MIN_VALUE;
//		yMax=Double.MAX_VALUE;
//		this.Gui_Graph=gg;
//		Iterator<String> f_iter = game.getFruits().iterator();
//		if(_fruit==null)
//		{
//			_fruit=new ArrayList <Fruit>();
//		}
//		else _fruit.clear();
//		while(f_iter.hasNext())
//		{
//			try
//			{
//				Fruit f=new Fruit(Gui_Graph);
//				f.initFruit(f_iter.next());
//				_fruit.add(f);	 
//			}
//			catch (JSONException e) {e.printStackTrace();}
//		}
//		String info = game.toString();
//		JSONObject line;
//		try {
//			line = new JSONObject(info);
//			JSONObject ttt = line.getJSONObject("GameServer");
//			int rs = ttt.getInt("robots");
//			if(Robots ==null)
//				Robots=new HashMap<Integer,Bots>();
//			else Robots.clear();
//			int i=0;
//			Iterator <Integer> it =setBots().iterator();
//			while((i<rs)&&(it.hasNext()))
//			{
//				game.addRobot(it.next());
//				i++;
//			}
//			while(i<rs)
//			{
//				int rnd =(int)Math.random()*Gui_Graph.nodeSize();
//				game.addRobot(rnd);
//				i++;
//			}
//			List<String> Bots = game.getRobots();
//			for (String str : Bots)
//			{
//				System.out.println(str);
//				Bots b= new Bots ();
//				b.initBot(str);
//				Robots.put(b.getId(), b);	 
//			}
//			initGUI();
//			paint();
//			
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//
//	}
//	
//	private  void moveRobotsManual(game_service game)
//	{
//		List<String> log = game.move();
//		if(log!=null) {
//			try {
//				long t = game.timeToEnd();
//				int dest = nextNode(game);
//				if(dest!=-1) {	
//					Bots rb=Robots.get(botToMove);
//					if(rb!=null)
//					{
//						System.out.println("you choose to move robot :"+rb.getId()+"move to "+dest);
//						game.chooseNextEdge(rb.getId(), dest);
//						game.move();
//
//					}
//					_fruit.clear();
//					_fruit=new ArrayList <Fruit>();
//					Iterator<String> f_iter = game.getFruits().iterator();
//					while(f_iter.hasNext())
//					{
//						Fruit f=new Fruit(Gui_Graph);
//						f.initFruit(f_iter.next());
//						_fruit.add(f);	 
//					}
//					Robots.clear();
//					List<String> botsStr = game.getRobots();
//					for (String string : botsStr) {
//						Bots ber = new Bots();
//						//		System.out.println(string);
//						ber.initBot(string);
//						Robots.put(ber.getId(), ber);
//					}
//				}
//
//			}
//			catch (JSONException e) {e.printStackTrace();}
//		}
//	}
//
//	private  void moveRobotAouto(game_service game) {
//		List<String> log = game.move();
//		if(log!=null) {
//			try {
//				_fruit=new ArrayList <Fruit>();
//				_fruit.clear();
//				Iterator<String> f_iter = game.getFruits().iterator();
//				while(f_iter.hasNext())
//				{
//
//					Fruit f=new Fruit(Gui_Graph);
//					f.initFruit(f_iter.next());
//					_fruit.add(f);	 
//
//				}
//				Collection<Bots> robots =Robots.values();
//				for (Bots b : robots) 
//				{
//					if(b.getDest()==-1)
//					{
//						setPath(Robots.get(b.getId()));
//						Iterator <node_data> it= b.getPath().iterator();
//						while(it.hasNext())
//						{
//							node_data n=it.next();
//							//System.out.println("Turn to node: "+n.getKey()+"  time to end:"+(t/1000));
//							game.chooseNextEdge(b.getId(), n.getKey());
//						}
//						b.setPath(null);
//					}
//				}
//				Robots.clear();
//				List<String> botsStr = game.getRobots();
//				for (String string : botsStr) {
//					Bots ber = new Bots();
//					//System.out.println(string);
//					ber.initBot(string);
//					Robots.put(ber.getId(), ber);
//				}
//			}
//			catch (JSONException e) {e.printStackTrace();}
//		}
//
//	}
//	private int nextNodeM(game_service game) {
//		if (!flage)
//		{
//			Collection<Bots> robots =Robots.values();
//			for (Bots b : robots) 
//			{
//				Point3D p=b.getLocaiton();
//				double dist=p.distance2D(new Point3D(x,y));
//
//				if(dist<=(xMax-xMin)*0.01)
//				{
//					System.out.println(b.getId());
//					x=y=0;
//					flage=true;
//					botToMove= b.getId();
//					//		System.out.println("the boot is :"+botToMove+"in node "+b.getSrc());
//					return -1;
//				}
//			}
//			int b=Robots.get(botToMove).getSrc();
//			//	System.out.println(b);
//			Collection<edge_data> edges =Gui_Graph.getE(b);
//			for (edge_data n : edges) 
//			{
//				Point3D p=Gui_Graph.getNode(n.getDest()).getLocation();
//				double dist=p.distance2D(new Point3D(x,y));
//				if(dist<=(xMax-xMin)*0.01)
//				{
//					x=y=0;
//					flage=true;
//					//	System.out.println("the dest is :"+n.getDest());
//					return n.getDest();
//				}
//
//			}
//		}
//		return -1;
//	}
//	private void setPath(Bots b) {
//		Fruit l=null;
//		Fruit toremove=null;
//		graph_algorithms gg=new Graph_Algo();
//		gg.init(Gui_Graph);
//		double min=Double.MAX_VALUE;
//		Iterator<Fruit> it =_fruit.iterator();
//		if(it.hasNext())
//		{
//			l=it.next();
//			double temp=gg.shortestPathDist(b.getSrc(), l.getEdge().getSrc());
//			if(temp==0)
//			{
//				b.setPath(gg.shortestPath(b.getSrc(),l.getEdge().getDest()));
//			}
//			if(temp<=min)
//			{
//				min=temp;
//				b.setPath(gg.shortestPath(b.getSrc(), l.getEdge().getSrc()));
//				b.getPath().add(Gui_Graph.getNode(l.getEdge().getDest()));
//				toremove=l;
//			}
//		}
//		_fruit.remove(toremove);
//	}
//
//}
