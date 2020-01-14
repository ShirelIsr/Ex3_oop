package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
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
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import algorithms.Graph_Algo;
import algorithms.graph_algorithms;
import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
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
	
	graph Gui_Graph;
	
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
		this.setSize(1000, 1000);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		MenuBar menuBar = new MenuBar();
		Menu file = new Menu("File");
		Menu Graph_Menu = new Menu("Graph_Menu");
		Menu Rnd_Graph = new Menu("Rnd_Graph");
		menuBar.add(file);
		menuBar.add(Graph_Menu);
		menuBar.add(Rnd_Graph);
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
		MenuItem AddEdge = new MenuItem("AddEdge");
		AddEdge.addActionListener(this);
		MenuItem RemoveEdge = new MenuItem("RemoveEdge");
		RemoveEdge.addActionListener(this);
		MenuItem Rnd_Graph1 = new MenuItem("Rnd Graph");
		Rnd_Graph1.addActionListener(this);
		MenuItem Rnd_Graph2 = new MenuItem("Rnd Graph Connect");
		Rnd_Graph2 .addActionListener(this);

		file.add(save);
		file.add(load);
		Graph_Menu.add(isconnect);
		Graph_Menu.add(SP);
		Graph_Menu.add(SPD);
		Graph_Menu.add(TSP);
		Graph_Menu.add(AddEdge);
		Graph_Menu.add(RemoveEdge);
		Rnd_Graph.add(Rnd_Graph1);
		Rnd_Graph.add(Rnd_Graph2);
		this.addMouseListener(this);
	}

	public void paint(Graphics g)
	{
		super.paint(g);
		if (Gui_Graph==null) return;
		Collection<node_data> s =Gui_Graph.getV();

		for (node_data node : s) 
		{
			double x=scale(node.getLocation().x(),xMin,xMax,10,getWidth()-20);
			double y=scale(node.getLocation().y(),yMin,yMax,70,getHeight()-20);
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
				 x=scale(Gui_Graph.getNode(edge.getDest()).getLocation().x(),xMin,xMax,10,getWidth()-20);
				 y=scale((Gui_Graph.getNode(edge.getDest()).getLocation().y()),yMin,yMax,70,getHeight()-20);
				Point3D pE=new Point3D(x,y);
				g.drawLine(p.ix(), p.iy(), pE.ix(), pE.iy());
				double w=Math.floor(edge.getWeight() * 100) / 100;
				g.drawString(""+w,(int)(((p.x()*3+pE.x())/4)),(int)((p.y()*3+pE.y())/4));
				g.setColor(Color.YELLOW);
				g.fillOval((int)(((p.x()*3+pE.x())/4)),(int)((p.y()*3+pE.y())/4),10,10);

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



	public void addEdge()
	{
		String src=  JOptionPane.showInputDialog("Please input the src");
		String dst=  JOptionPane.showInputDialog("Please input the dest");
		String w=  JOptionPane.showInputDialog("Please input the wahit");

		try {
			if(Integer.parseInt(w)<0)
			{
				JOptionPane.showMessageDialog(null,"ERR, weight could not be negative ", "graph: ", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			Gui_Graph.connect(Integer.parseInt(src), Integer.parseInt(dst), Integer.parseInt(w));
		}
		catch(Exception ex)
		{
			JOptionPane.showMessageDialog(null,"Err,ther is src/dest do not exist", "graph: ", JOptionPane.INFORMATION_MESSAGE);
			ex.printStackTrace();
			return;
		}
		JOptionPane.showMessageDialog(null,"the new edge number:"+Gui_Graph.edgeSize(), "graph: ", JOptionPane.INFORMATION_MESSAGE);
		repaint();
	}
	public void RemoveEdge()
	{
		String src=  JOptionPane.showInputDialog("Please input the src");
		String dst=  JOptionPane.showInputDialog("Please input the dest");
		edge_data ans=null;
		try {
			ans=Gui_Graph.removeEdge(Integer.parseInt(src), Integer.parseInt(dst));
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		if(ans!=null)
			JOptionPane.showMessageDialog(null,"the new edge number:"+Gui_Graph.edgeSize(), "graph: ", JOptionPane.INFORMATION_MESSAGE);
		repaint();
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
	public void Rnd_Graph1() 
	{
		graph g=new DGraph();

		for (int i=1;i<51;i++)
		{
			int ix=(int)(Math.random()*700)+100;
			int iy=(int)(Math.random()*700)+100;
			node_data v=new NodeData(i,new Point3D(ix,iy));
			g.addNode(v);
		}
		for (int i=0;i<80;i++)
		{
			int src=(int)(Math.random()*50+1);
			int dst=1;
			do {
				dst=(int)(Math.random()*50+1);
			}while(dst==src);	
			double w=Math.random()*100;
			g.connect(src, dst, w);

		}
		this.Gui_Graph=g;
		set(Gui_Graph);
		repaint();
	}

	public void Rnd_Graph2() 
	{
		graph g=new DGraph();

		for (int i=0;i<10;i++)
		{
			int ix=(int)(Math.random()*700)+100;
			int iy=(int)(Math.random()*700)+100;
			node_data v=new NodeData(i,new Point3D(ix,iy));
			g.addNode(v);
		}
		Collection<node_data> s = g.getV();
		for (node_data node1 : s) 
		{
			for (node_data node2 : s)
			{
				if(node1.getKey()!=node2.getKey())
				{
					double w=Math.random()*100;
					g.connect(node1.getKey(), node2.getKey(), w);
				}
			}
		}
		this.Gui_Graph=g;
		set(Gui_Graph);
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
		case "AddEdge"      :addEdge();
		break;
		case "RemoveEdge"   :RemoveEdge();
		break;
		case "Rnd Graph"   :Rnd_Graph1();
		break;
		case "Rnd Graph Connect"   :Rnd_Graph2();
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