package algorithms;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;

import dataStructure.DGraph;
import dataStructure.NodeData;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author 
 *
 */
public class Graph_Algo implements graph_algorithms,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	graph _graph;
	
	public Graph_Algo(graph g)
	{
		this._graph=g;
	}

	public Graph_Algo ()
	{
		this._graph=new DGraph();
	}
	
	@Override
	public void init(graph g) {
		this._graph=g;
	}

	@Override
	public void init(String file_name) {
		try
		{ 
			FileInputStream file = new FileInputStream(file_name); 
			ObjectInputStream in = new ObjectInputStream(file); 
			Graph_Algo input =(Graph_Algo)in.readObject(); 
			_graph = input._graph;
			in.close(); 
			file.close(); 
			System.out.println("Object has been deserialized"); 
		} 
		catch(IOException ex) 
		{ 
			System.out.println("IOException is caught?"); 
		} 
		catch(ClassNotFoundException ex) 
		{ 
			System.out.println("ClassNotFoundException is caught"); 
		} 

	}

	@Override
	public void save(String file_name) {

		try
		{    
			FileOutputStream file = new FileOutputStream(file_name); 
			ObjectOutputStream out = new ObjectOutputStream(file); 
			out.writeObject(this); 
			out.close(); 
			file.close(); 
			System.out.println("Object has been serialized"); 
		}   
		catch(IOException ex) 
		{ 
			System.out.println("IOException is caught"); 
		} 

	}

	@Override
	public boolean isConnected() 
	{
		Collection<node_data> s = _graph.getV();
		
		for (node_data node : s) 
		{
			clearNodeData();
			int c = numOfConected(node);
			if(_graph.nodeSize()>c)
				return false;	
		}
		return true;
	}

	private void clearNodeData()
	{
		Collection<node_data> s = _graph.getV();
		for (node_data node : s) 
		{
			node.setTag(0);
			node.setWeight(Double.MAX_VALUE);
		}
	}
	private int numOfConected(node_data v)
	{
		if(v.getTag()==1) return 0;
		v.setTag(1);
		Collection<edge_data> e = _graph.getE(v.getKey());
		int count =1;
		for (edge_data edge : e)
		{
			count += numOfConected(_graph.getNode(edge.getDest()));
		}
		return count;	
	}

	@Override
	public double shortestPathDist(int src, int dest) {
		dijkstra(src);
		return _graph.getNode(dest).getWeight();
	}

	private void dijkstra(int src)
	{
		clearNodeData();
		PriorityQueue<node_data> queue = new PriorityQueue<>(new graph_cmp());
		_graph.getNode(src).setWeight(0);
		queue.add(_graph.getNode(src));
		while(!queue.isEmpty()){
			node_data u = queue.poll();
			Collection<edge_data> e = _graph.getE(u.getKey());
			for(edge_data edge: e)
			{
				double weightNode=u.getWeight();
				node_data v = _graph.getNode(edge.getDest());
				double weightEdge=edge.getWeight();
				if(_graph.getNode(edge.getDest()).getTag()!=1)
					if(weightEdge+weightNode<_graph.getNode(edge.getDest()).getWeight()) 
					{
						queue.remove(v);
						v.setWeight(weightEdge+weightNode);
						/*remove v from queue for updating 
							the shortestDistance value*/
						v.setInfo(""+u.getKey());
						queue.add(v);
					}
			}
		}
	}


	@Override
	public List<node_data> shortestPath(int src, int dest) {
		List <node_data> path =new ArrayList <node_data>();
		dijkstra(src);
		node_data node = _graph.getNode(dest);
		path.add(node);
		while( node.getKey()!=src){
			if(node.getWeight()==Double.MAX_VALUE)
				return null;
			node=_graph.getNode(Integer.parseInt(node.getInfo()));
			path.add(node);
		}
		//reverse the order such that it will be from source to target
		Collections.reverse(path);
		return path;
	}

	@Override
	public List<node_data> TSP(List<Integer> targets) {

		List <node_data> path =shortestPath(targets.get(0),targets.get(1));
		List<Integer> visit=new ArrayList<Integer>();
		visit.add(targets.get(0));
		visit.add(targets.get(1));
		int last_path=1;
		for(int i=2;i<targets.size();i++)
		{
			if((visit.contains(targets.get(i)))||!path.contains(_graph.getNode(targets.get(i))))
			{
				List <node_data> path_temp =shortestPath(targets.get(last_path),targets.get(i));
				last_path=i;
				if(path_temp==null)
					return null;
				path_temp.remove(0);
				for(node_data node:path_temp)
					path.add(node);
			}
			visit.add(targets.get(i));
		}
		return path;
	}

	@Override
	public graph copy() {
		graph g = new DGraph();
		Collection <node_data> nodes = _graph.getV();
		for(node_data node : nodes)
		{
			g.addNode(new NodeData(node));
		}
		for(node_data node : nodes)
		{
			Collection<edge_data> edges = _graph.getE(node.getKey());
			for(edge_data edge : edges)
			{
				g.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
			}
		}
		return g;
	}
}
