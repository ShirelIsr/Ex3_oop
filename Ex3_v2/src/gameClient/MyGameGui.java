package gameClient;

import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


import javax.swing.JOptionPane;


import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.filechooser.FileSystemView;

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


public class MyGameGui  
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final double EPSILON = 0.0001;
	//	private final double EPSILON2 = 0.01;
	//	private static DecimalFormat df2 = new DecimalFormat("#.###");
	private double xMin=Double.MIN_VALUE;
	private double xMax=Double.MAX_VALUE;;
	private double yMin=Double.MIN_VALUE;
	private double yMax=Double.MAX_VALUE;
	private double x=0;
	private double y=0;
	static long time=0;
	game_service game;
	graph Gui_Graph;
	Thread help;
	Thread help2;
	MyGame m;
	KML_Logger k;

	public MyGameGui(graph g)
	{
		this.Gui_Graph=g;
		m=null;
		set(Gui_Graph);
		initGUI();
	}

	public void setPoint(double xpos,double ypos)
	{
		this.x= xpos;
		this.y = ypos;
		m.setXY(x, y);
	}

	public MyGameGui()
	{
		this.Gui_Graph=null;
		m=null;
		initGUI();
	}

	private void initGUI() 
	{
		if(!StdDraw.getIsPaint()) {
			StdDraw.setCanvasSize(800, 600);
			StdDraw.enableDoubleBuffering();
			StdDraw.setIsPaint();
		}
		if(m!=null)
		{
			this.Gui_Graph=m.getGraph();

		} 
		if (Gui_Graph!=null) 
		{
			set(this.Gui_Graph);
		}
		StdDraw.setXscale(xMin, xMax);
		StdDraw.setYscale(yMin, yMax);
		StdDraw.setG_GUI(this);
		StdDraw.show();
		paint();
	}

	public void paint()
	{	
		StdDraw.clear();
		if (Gui_Graph==null) return;
		Collection<node_data> s =Gui_Graph.getV();
		for (node_data node : s) 
		{
			StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 12));
			Point3D p=node.getLocation();
			StdDraw.setPenColor(Color.RED);
			StdDraw.circle(p.x(),p.y(),0.0001);
			StdDraw.text( p.x()+0.0001, p.y()+0.0001, ""+node.getKey());
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
				StdDraw.text((p.x()*3+pE.x())/4+0.0000015,(p.y()*3+pE.y())/4+0.0000015, ""+w);

			}
		}
		ArrayList <Fruit> _fruit =m.getFruits();
		if (!_fruit.isEmpty())
		{
			Iterator <Fruit> it=_fruit.iterator();
			while (it.hasNext())
			{
				Fruit f=it.next();
				Point3D pf=f.getLocation();
				if(f.getType()==1)
					StdDraw.setPenColor(Color.DARK_GRAY);
				else StdDraw.setPenColor(Color.ORANGE);
				StdDraw.circle(pf.x(),pf.y(),0.0001);
			}
		}
		Collection<Bots> bb = m.getRobotes();
		if(!bb.isEmpty())
		{
			for(Bots b:bb)
			{
				Point3D pb=b.getLocation();
				StdDraw.setPenColor(Color.BLACK);

				StdDraw.circle(pb.x(),pb.y(),0.0002);
			}
			StdDraw.show();
		}
		StdDraw.setPenColor(Color.black);
		StdDraw.setFont(new Font("TimesRoman", Font.PLAIN, 50));
		System.out.println(time);
		StdDraw.textLeft(this.xMax- 0.0004,this.yMax- 0.0004,"time: " + time);
	}

	public void Play_manual(String scenario_num)
	{
		try
		{
			int num = Integer.parseInt(scenario_num);
			if(num>=0 && num<=23)
			{
				m=new MyGame_Manual();
				m.initGame(num);
				this.game=m.getGame();
				initGUI();
				playManual();
			}
			else
			{
				JFrame jinput = new JFrame();
				JOptionPane.showMessageDialog(jinput,"Err,The input is not expected");
				jinput.dispose();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void playManual()
	{
		game.startGame();
		while(game.isRunning()) {
			m.moveRobot();
			paint();
			time = game.timeToEnd() / 1000;
			if(time<=10)
				break;
		}
		String results = game.toString();
		System.out.println("Game Over: "+results);
	}


	public  void Play_Automaticly(String scenario_num)
	{
		try
		{
			int num = Integer.parseInt(scenario_num);
			if(num>=0 && num<=23)
			{
				m=new MyGame_Automaticly();
				m.initGame(num);
				this.game=m.getGame();
				initGUI();
				playAuto();
			}
			else
			{
				JFrame jinput = new JFrame();
				JOptionPane.showMessageDialog(jinput,"Err,The input is not expected ");
				jinput.dispose();
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	private void  playAuto() {
		game.startGame();
		
		k=new KML_Logger(m);
		k.createENKML();

//		k.createRFKML();
		KMLthread(game);

		while(game.isRunning())
		{
			if(game.timeToEnd()<=15)
				break;
			m.moveRobot();
			paint();
			time = game.timeToEnd() / 1000;
			
		}
		k.save();
		System.out.println("Game Over :" +game.toString());
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
				paint();

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
		paint();


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
		paint();
	}

	////////////////////////////////////////////////////////
	//KML_Logger kml = new KML_Logger();
	//
	Thread KMLt;
	public void KMLthread(game_service game)
	{
	//	System.out.println("123");
		KMLt = new Thread(new Runnable() {

			@Override
			public void run() {
				while(game.isRunning())
				{
					long timeToSleep = 100;
					try {
						Thread.sleep(timeToSleep);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					//System.err.println("123123");
					k.createRFKML();

				}
			}
		});
		KMLt.start();
	}
	////////////////////////////////////////////////////////
	public static void main(String[] args) {

		MyGameGui app = new MyGameGui();

	}

}