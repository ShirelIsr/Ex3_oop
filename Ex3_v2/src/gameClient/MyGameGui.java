package gameClient;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiFunction;

import javax.swing.JOptionPane;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import utils.Point3D;
import utils.StdDraw;

public final class MyGameGui  extends JFrame implements ActionListener, MouseListener, MouseMotionListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double EPSILON = 0.0001;
	//	private final double EPSILON2 = 0.01;
	private static DecimalFormat df2 = new DecimalFormat("#.###");
	private double xMin=Double.MIN_VALUE;
	private double xMax=Double.MAX_VALUE;;
	private double yMin=Double.MIN_VALUE;
	private double yMax=Double.MAX_VALUE;
	private double BORDES=20;
	ArrayList <Fruit> _fruit ;
	HashMap <Integer,Bots> Robots ;
	graph Gui_Graph;
	Thread help;

	public MyGameGui(graph g)
	{
		this.Gui_Graph=g;

		set(Gui_Graph);
		initGUI();
	}

	public MyGameGui()
	{
		initGUI();
	}

	public void initGUI(graph g) 
	{
		this.Gui_Graph=g;
		set(Gui_Graph);

		this.getBufferStrategy();
		initGUI();
	}

	private void initGUI() 
	{
		StdDraw.enableDoubleBuffering();
		StdDraw.setCanvasSize(800, 600);
		if (Gui_Graph!=null) 
		{
			set(this.Gui_Graph);
		}
		StdDraw.setXscale(xMin , xMax);
		StdDraw.setYscale(yMin, yMax);
		StdDraw.show();
	}
	public void ThreadPaint(game_service game)
	{
		help = new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while(game.isRunning())
				{
					try {
						Thread.sleep(1000);
						Iterator<String> f_iter = game.getFruits().iterator();
						_fruit.clear();
						if(f_iter.hasNext())
						{
							while(f_iter.hasNext())
							{
								String json = f_iter.next();
								Fruit n = new Fruit(Gui_Graph);
								n.initFruit(json);
								_fruit.add(n);
							}

						}
						//						bots.clear();
						List<String> botsStr = game.getRobots();
						for (String string : botsStr) {
							Bots ber = new Bots();
							ber.initBot(string);
						}
						repaint();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				help.interrupt();
			}
		});
		help.start();
	}

	public void paint(Graphics g)
	{	
		StdDraw.clear();
		if (Gui_Graph==null) return;
		Collection<node_data> s =Gui_Graph.getV();
		for (node_data node : s) 
		{
			Point3D p=node.getLocation();
			StdDraw.setPenColor(Color.RED);
			StdDraw.circle(p.x(),p.y(),6);
			StdDraw.text( p.x()+1, p.y()+1, ""+node.getKey());
			Collection<edge_data> e =Gui_Graph.getE(node.getKey());
			for(edge_data edge : e)
			{
				if(edge.getTag() ==Double.MIN_EXPONENT)
				{
					StdDraw.setPenColor(Color.GREEN);
					edge.setTag(0);
				}
				else
				{
					StdDraw.setPenColor(Color.BLUE);
				}

				Point3D pE=Gui_Graph.getNode(edge.getDest()).getLocation();
				StdDraw.line(p.x(), p.y(), pE.x(), pE.y());
				double w=Math.floor(edge.getWeight() * 100) / 100;
				StdDraw.text((p.x()*3+pE.x())/4+0.07,(p.y()*3+pE.y())/4+0.07, ""+w);
				StdDraw.setPenColor(Color.YELLOW);
				StdDraw.circle((((p.x()*3+pE.x())/4)),(int)((p.y()*3+pE.y())/4),5);

			}
			if (!_fruit.isEmpty())
			{
				Iterator <Fruit> it=_fruit.iterator();
				while (it.hasNext())
				{
					Fruit f=it.next();
					Point3D pf=f.getlocaiton();
					if(f.getType()==1)
						g.setColor(Color.PINK);
					else g.setColor(Color.ORANGE);
					StdDraw.circle(pf.x(),pf.y(),6);
				}
			}
			Collection<Bots> bb = Robots.values();
			for(Bots b:bb)
			{
				Point3D pb=b.getLocaiton();
				g.setColor(Color.black);
				StdDraw.circle(pb.x(),pb.y(),6);
			}
			StdDraw.show();

		}

	}
	public void save() 
	{
		graph_algorithms g = new Graph_Algo();
		g.init(Gui_Graph);
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
		{
			try
			{
				g.save(chooser.getSelectedFile()+".txt");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}

	}
	public void load() 
	{
		Graph_Algo g = new Graph_Algo();
		JFileChooser chooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		if(chooser.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File SelectedFile=chooser.getSelectedFile();
				g.init(SelectedFile.getAbsolutePath());
				Gui_Graph=g.copy();
				repaint();

			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}


	private void set(graph g)
	{
		Collection<node_data> s = g.getV();
		double x[]=new double[s.size()];
		double y[]=new double[s.size()];
		int i=0;
		for (node_data node : s) 
		{
			x[i]=node.getLocation().x();
			y[i]=node.getLocation().y();
			i++;
		}
		Arrays.sort(x);
		Arrays.sort(y);
		this.xMin=x[0];
		this.xMax=x[s.size()-1];
		this.yMin=y[0];
		this.yMax=y[s.size()-1];
	}


	public void isConnect() {
		graph_algorithms g = new Graph_Algo();
		g.init(Gui_Graph);
		boolean ans = g.isConnected();
		if(ans)
		{
			JOptionPane.showMessageDialog(null,"The graph is connected", "isConnected", JOptionPane.QUESTION_MESSAGE);
		}
		else
		{
			JOptionPane.showMessageDialog(null, "The graph is not connected", "isConnected", JOptionPane.INFORMATION_MESSAGE);
		}
	}



	public void SP() 
	{
		String src=  JOptionPane.showInputDialog("Please input the src ");
		String dst=  JOptionPane.showInputDialog("Please input the dest");
		graph_algorithms g = new Graph_Algo();
		g.init(Gui_Graph);
		List <node_data> ans=g.shortestPath(Integer.parseInt(src),Integer.parseInt(dst));
		if (ans ==null)
		{
			JOptionPane.showMessageDialog(null,"Err, There is no path between the points :", "shortest path points \"+src+\"-\"+dst", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int s=0;
		for (int d=1;d<ans.size();d++,s++)
		{
			Gui_Graph.getEdge(ans.get(s).getKey(),ans.get(d).getKey()).setTag(Double.MIN_EXPONENT);
		}
		repaint();

	}
	public void SPD() 
	{
		String src=  JOptionPane.showInputDialog("Please input a starting point");
		String dst=  JOptionPane.showInputDialog("Please input a ending point");
		graph_algorithms g = new Graph_Algo();
		g.init(Gui_Graph);
		double ans =g.shortestPathDist(Integer.parseInt(src),Integer.parseInt(dst));
		if(ans !=Double.MAX_VALUE)
		{
			JOptionPane.showMessageDialog(null,"The shortest path dist is:\n "+ans,"shortest path", JOptionPane.INFORMATION_MESSAGE);
		}
		else 
		{
			JOptionPane.showMessageDialog(null,"The shortest path dist is:\n "+Double.POSITIVE_INFINITY, "shortest path:", JOptionPane.INFORMATION_MESSAGE);	
		}

	}
	public void TSP() 
	{
		List <Integer> targets =new ArrayList<Integer>();
		int repeat;
		String input="";
		do {
			input=JOptionPane.showInputDialog("Please input the points \n ");
			try {
				targets.add(Integer.parseInt(input));
			}
			catch(Exception ex)
			{
				JOptionPane.showMessageDialog(null,"","ERR", JOptionPane.INFORMATION_MESSAGE);
				ex.printStackTrace();
				return;
			}
			repeat = JOptionPane.showConfirmDialog(null, "Press Yes to repeat, No to quit ", "please confirmm", JOptionPane.YES_NO_OPTION);
		}while(repeat == JOptionPane.YES_OPTION);
		graph_algorithms g = new Graph_Algo();
		g.init(Gui_Graph);
		List <node_data> ans =g.TSP(targets);
		if (ans ==null)
		{
			JOptionPane.showMessageDialog(null,"Err, There is no path between the points ", "shortest path", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		int src=0;
		String pathAns=""+ans.get(src).getKey();
		for (int dst=1;dst<ans.size();dst++,src++)
		{
			Gui_Graph.getEdge(ans.get(src).getKey(),ans.get(dst).getKey()).setTag(Double.MIN_EXPONENT);
			pathAns+="->"+ans.get(dst).getKey();
		}
		JOptionPane.showMessageDialog(null,pathAns,"the path is:", JOptionPane.INFORMATION_MESSAGE);
		repaint();
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	public void scenario() throws InterruptedException {

		String num=  JOptionPane.showInputDialog("Please input the num");
		int scenario_num = Integer.parseInt(num);
		game_service game = Game_Server.getServer(scenario_num); // you have [0,23] games
		String g = game.getGraph();
		DGraph gg = new DGraph();
		gg.init(g);
		this.Gui_Graph=gg;
		set(Gui_Graph);
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
				Fruit f=new Fruit(Gui_Graph);
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
			while(i<rs)
			{
				int rnd =(int)Math.random()*Gui_Graph.nodeSize();
				game.addRobot(rnd);
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
		catch (JSONException e) {e.printStackTrace();}
		ArrayList <edge_data> target=targets();
		setBots(target);
		Thread.sleep(200);
		playSolo(game);
		repaint();

	}

	private ArrayList <edge_data> targets()
	{
		ArrayList <edge_data> t=new ArrayList <edge_data>();
		if (!_fruit.isEmpty())
		{
			Iterator <Fruit> it=_fruit.iterator();
			while (it.hasNext())
			{
				Fruit f=it.next();
				t.add(f.getEdge());
			}
		}
		return t;
	}



	private void setBots(ArrayList <edge_data> targets)
	{
		Collection<Bots> b = Robots.values();
		Iterator <edge_data> it=targets.iterator();
		for(Bots bb:b)
			if(it.hasNext())
				bb.setLocaiton(Gui_Graph.getNode(it.next().getSrc()).getLocation());
	}


	private void playSolo(game_service game)
	{
		game.startGame();
		ThreadPaint(game);
		//ThreadMouse(game);
		while(game.isRunning()) {
			//initGUI();
			moveRobots(game);
		}
		String results = game.toString();
		System.out.println("Game Over: "+results);
	}


	/** 
	 * Moves each of the robots along the edge, 
	 * in case the robot is on a node the next destination (next edge) is chosen (randomly).
	 * @param game
	 * @param gg
	 * @param log
	 */
	private  void moveRobots(game_service game) {
		List<String> log = game.move();
		if(log!=null) {
			long t = game.timeToEnd();
			for(int i=0;i<log.size();i++) {
				String robot_json = log.get(i);
				try {
					JSONObject line = new JSONObject(robot_json);
					JSONObject ttt = line.getJSONObject("Robot");
					int rid = ttt.getInt("id");
					int src = ttt.getInt("src");
					int dest = ttt.getInt("dest");

					if(dest==-1) {	
						setPath(Robots.get(rid));
					}
					_fruit=new ArrayList <Fruit>();
					_fruit.clear();
					Iterator<String> f_iter = game.getFruits().iterator();
					while(f_iter.hasNext())
					{

						Fruit f=new Fruit(Gui_Graph);
						f.initFruit(f_iter.next());
						_fruit.add(f);	 

					}
					Collection<Bots> robots =Robots.values();

					for (Bots b : robots) 
					{
						Iterator <node_data> it= b.getPath().iterator();
						while(it.hasNext())
						{
							node_data n=it.next();
							b.setLocaiton(n.getLocation());
							System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
							System.out.println(ttt);
							game.move();
						}

					}
				}
				catch (JSONException e) {e.printStackTrace();}
			}
		}


	}

	private void setPath(Bots b) {
		Collection<edge_data> e =Gui_Graph.getE(b.getSrc());
		edge_data l=null;
		graph_algorithms gg=new Graph_Algo();
		gg.init(Gui_Graph);
		for(edge_data edge : e)
		{
			Iterator<Fruit> it =_fruit.iterator();
			while (it.hasNext())
			{
				l=it.next().getEdge();
				if(edge.getSrc()==l.getSrc())
					b.setPath(gg.shortestPath(l.getSrc(), l.getDest()));
			}
		}
		Iterator<Fruit> it =_fruit.iterator();
		if(it.hasNext())
		{
			l=it.next().getEdge();
			b.setPath(gg.shortestPath(b.getSrc(), l.getDest()));

		}
	}



	@Override
	public void actionPerformed(ActionEvent e) {
		String str = e.getActionCommand();
		switch (str)
		{
		case "save"     :save();
		break;
		case "load"     :load();
		break;
		case "isConnect":isConnect();
		break;
		case "SP"       :SP();
		break;
		case "SPD"      :SPD();
		break;
		case "TSP"      :TSP();
		break;
		case "scenario"   :

			;	help =new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					try {
						scenario();
						help.interrupt();
					}
					catch (Exception e) {e.printStackTrace();}
				}
			});
			help.start();
			break;
		}

	}


	@Override
	public void mouseClicked(MouseEvent e) {



	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}


	public static void main(String[] args) {

		MyGameGui app = new MyGameGui();
		app.setVisible(true);
	}
}