package gameClient;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.JOptionPane;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	private static final Graphics Graphics = null;
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
		StdDraw.textLeft(this.xMax + 0.00006 , this.yMax + 0.00006 , "time: " + time);
	}

	public void Play_manual(String scenario_num)
	{
		try
		{
			int num = Integer.parseInt(scenario_num);
			if(num>=0 && num<=23)
			{
				//	int id=312354210;
				//System.out.println(Game_Server.login(id)+"shirel id :"+id);
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
		m.MoveThread();
		while(game.isRunning()) {
			m.moveRobot();
			paint();
			time = game.timeToEnd() / 1000;
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
				int id=312354210;
				System.out.println("ozoo");

				System.out.println(Game_Server.login(id)+"shirel id :"+id);
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
		KMLthread(game);
		m.MoveThread();
		while(game.isRunning())
		{
			time = game.timeToEnd() / 1000;
			m.moveRobot();
			paint();
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


	public void BestScore()
	{
		HashMap <Integer,Double> minScor =new HashMap<Integer,Double>();
		minScor.put(0, 145.0);
		minScor.put(1, 450.0);
		minScor.put(3, 720.0);
		minScor.put(5, 570.0);
		minScor.put(9, 510.0);
		minScor.put(11, 1050.0);
		minScor.put(13, 310.0);
		minScor.put(16,235.0);
		minScor.put(19, 250.0);
		minScor.put(20, 200.0);
		minScor.put(23, 1000.0);
		HashMap <Integer,Integer> minMove =new HashMap<Integer,Integer>();
		minMove.put(0, 290);
		minMove.put(1, 580);
		minMove.put(3, 580);
		minMove.put(5, 580);
		minMove.put(9, 500);
		minMove.put(11, 580);
		minMove.put(13, 580);
		minMove.put(16,290);
		minMove.put(19, 580);
		minMove.put(20, 290);
		minMove.put(23, 1140);
		HashMap <Integer,Double> IDScor =new HashMap<Integer,Double>();
		int id =312354210;
		int count =0;

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = 
					DriverManager.getConnection(SimpleDB.jdbcUrl, SimpleDB.jdbcUser, SimpleDB.jdbcUserPassword);
			Statement statement = connection.createStatement();
			String allCustomersQuery = "SELECT * FROM Logs;";
			ResultSet resultSet = statement.executeQuery(allCustomersQuery);

			while(resultSet.next())
			{
				if(resultSet.getInt("UserID")==id)
				{
					int levelID=resultSet.getInt("levelID");
					if(!minMove.containsKey(levelID))
						minMove.put(levelID,Integer.MAX_VALUE);
					if(!minScor.containsKey(levelID))
						minScor.put(levelID, 0.0);
					if(IDScor.get(levelID)==null)
						IDScor.put(levelID,0.0);
					Double scorID=resultSet.getDouble("score");
					if(scorID>=minScor.get(levelID) &&(resultSet.getInt("moves")<=minMove.get(levelID)))
						if(IDScor.get(levelID)>scorID)
							IDScor.put(levelID,scorID);
				}
			}
			resultSet.close();
			statement.close();		
			connection.close();	
			count++;
		}
		catch (SQLException sqle) {
			System.out.println("SQLException: " + sqle.getMessage());
			System.out.println("Vendor Error: " + sqle.getErrorCode());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Collection<Integer> s=IDScor.keySet();

		JFrame jinput = new JFrame();
		jinput.setSize(500,500);
		jinput.setTitle("BEST SCORE!");
		jinput.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jinput.setVisible(true); 
		Graphics g = jinput.getGraphics();
		g.setColor(Color.BLACK);
		g.fillOval(60,60,12,12);
	//	g.setFont("Times new Roman.");
		g.drawString("The number of games you playd"+count,100,100);
		int j=2;
		for(Integer i:s)
		{
			g.drawString("The level "+i+"   your score "+IDScor.get(i),100-j,100-j);
		}
		g.dispose();

	}



	////////////////////////////////////////////////////////
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