package gui;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

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
import gameClient.Bots;
import gameClient.Fruit;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import utils.Point3D;

public final class GRAPH_GUI  extends JFrame implements ActionListener, MouseListener, MouseMotionListener, KeyListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private double xMin=Double.MIN_VALUE;
	private double xMax=Double.MAX_VALUE;;
	private double yMin=Double.MIN_VALUE;
	private double yMax=Double.MAX_VALUE;
	private double BORDES=20;
	ArrayList <Fruit> _fruit ;
	HashMap <Integer,Bots> Robots ;
	graph Gui_Graph;
	Thread help;

	public GRAPH_GUI(graph g)
	{
		this.Gui_Graph=g;
		set(Gui_Graph);
		initGUI();
	}

	public GRAPH_GUI()
	{
		initGUI();
	}

	public void initGUI(graph g) 
	{
		this.Gui_Graph=g;
		set(Gui_Graph);
		initGUI();
	}

	private void initGUI() 
	{
		this.setSize(900, 900);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");
		Menu Graph_Menu = new Menu("Graph_Menu");
		Menu game = new Menu("game");
		menuBar.add(file);
		menuBar.add(Graph_Menu);
		menuBar.add(game);
		this.setMenuBar(menuBar);
		MenuItem save = new MenuItem("save");
		save.addActionListener(this);
		MenuItem load = new MenuItem("load");
		load.addActionListener(this);
		MenuItem isconnect = new MenuItem("isConnect");
		isconnect.addActionListener(this);
		MenuItem SP = new MenuItem("SP");
		SP.addActionListener(this);
		MenuItem SPD = new MenuItem("SPD");
		SPD.addActionListener(this);
		MenuItem TSP = new MenuItem("TSP");
		TSP.addActionListener(this);
		MenuItem scenario = new MenuItem("scenario");
		scenario .addActionListener(this);

		file.add(save);
		file.add(load);
		Graph_Menu.add(isconnect);
		Graph_Menu.add(SP);
		Graph_Menu.add(SPD);
		Graph_Menu.add(TSP);
		game.add(scenario);
		this.addMouseListener(this);
	}

	public void paint(Graphics g)
	{	
		super.paint(g);
		if (Gui_Graph==null) return;
		Collection<node_data> s =Gui_Graph.getV();

		for (node_data node : s) 
		{
			double x=scale(node.getLocation().x(),xMin,xMax,BORDES,getWidth()-BORDES);
			double y=scale(node.getLocation().y(),yMin,yMax,50,getHeight()-BORDES);
			Point3D p=new Point3D(x,y);
			g.setColor(Color.RED);
			g.fillOval(p.ix(),p.iy(),12,12);
			g.drawString(""+node.getKey(), p.ix()+1, p.iy()+1);
			Collection<edge_data> e =Gui_Graph.getE(node.getKey());
			for(edge_data edge : e)
			{
				if(edge.getTag() ==Double.MIN_EXPONENT)
				{
					g.setColor(Color.GREEN);
					edge.setTag(0);
				}
				else
				{
					g.setColor(Color.BLUE);
				}
				x=scale(Gui_Graph.getNode(edge.getDest()).getLocation().x(),xMin,xMax,BORDES,getWidth()-BORDES);
				y=scale((Gui_Graph.getNode(edge.getDest()).getLocation().y()),yMin,yMax,50,getHeight()-BORDES);
				Point3D pE=new Point3D(x,y);
				g.drawLine(p.ix(), p.iy(), pE.ix(), pE.iy());
				double w=Math.floor(edge.getWeight() * 100) / 100;
				g.drawString(""+w,(int)(((p.x()*3+pE.x())/4)),(int)((p.y()*3+pE.y())/4));
				g.setColor(Color.YELLOW);
				g.fillOval((int)(((p.x()*3+pE.x())/4)),(int)((p.y()*3+pE.y())/4),10,10);

			}
			if (!_fruit.isEmpty())
			{
				Iterator <Fruit> it=_fruit.iterator();
				while (it.hasNext())
				{
					Fruit f=it.next();
					x=scale(f.getlocaiton().x(),xMin,xMax,BORDES,getWidth()-BORDES);
					y=scale(f.getlocaiton().y(),yMin,yMax,50,getHeight()-BORDES);
					Point3D pf=new Point3D(x,y);
					if(f.getType()==1)
						g.setColor(Color.PINK);
					else g.setColor(Color.ORANGE);
					g.fillOval(pf.ix(),pf.iy(),12,12);
				}
			}
			Collection<Bots> bb = Robots.values();
			for(Bots b:bb)
			{
				x=scale(b.getLocaiton().x(),xMin,xMax,BORDES,getWidth()-BORDES);
				y=scale(b.getLocaiton().y(),yMin,yMax,50,getHeight()-BORDES);
				Point3D pb=new Point3D(x,y);
				g.setColor(Color.black);
				g.fillOval(pb.ix(),pb.iy(),12,12);
			}

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

	/**
	 * 
	 * @param data denote some data to be scaled
	 * @param r_min the minimum of the range of your data
	 * @param r_max the maximum of the range of your data
	 * @param t_min the minimum of the range of your desired target scaling
	 * @param t_max the maximum of the range of your desired target scaling
	 * @return
	 */
	private double scale(double data, double r_min, double r_max,double t_min, double t_max)
	{	
		double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
		return res;
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

	public void scenario() {

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
		while(game.isRunning()) {
			//initGUI();
			moveRobots(game);
		}
		String results = game.toString();
		System.out.println("Game Over: "+results);
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
					dest = nextNode(src);
					game.chooseNextEdge(rid, dest);

				}
				Bots b =Robots.get(rid);

				_fruit=new ArrayList <Fruit>();
				_fruit.clear();
				Iterator<String> f_iter = game.getFruits().iterator();
				while(f_iter.hasNext())
				{

					Fruit f=new Fruit(Gui_Graph);
					f.initFruit(f_iter.next());
					_fruit.add(f);	 

				}
				b.setLocaiton(Gui_Graph.getNode(src).getLocation());
				System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
				System.out.println(ttt);
			}
			catch (JSONException e) {e.printStackTrace();}
		}
	}


}


private int nextNode( int src) {
	Iterator<Fruit> it =_fruit.iterator();
	edge_data e =null;
	if (it.hasNext())
	{
		e=it.next().getEdge();
		if(e.getSrc()==src)
			return e.getDest();
	}
	if(e != null)
		return e.getSrc();
	return 0;

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
	// TODO Auto-generated method stub

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

	GRAPH_GUI app = new GRAPH_GUI();
	app.setVisible(true);
}
}