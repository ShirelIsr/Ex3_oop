package dataStructure;

import java.io.Serializable;

public class Edge implements edge_data,Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	node_data src;
	node_data dest;
	double weight;
	int tag;
	String info;

	public Edge(node_data src,node_data dest)
	{
		this.src=src;
		this.dest=dest;
		this.weight=Double.MAX_VALUE;
		tag=0;
	}
	public Edge(node_data src,node_data dest,double weight)
	{
		this.src=src;
		this.dest=dest;
		if(weight>=0)
			this.weight=weight;
		else
			throw new RuntimeException("ERR, weught could not be negative ");
		tag=0;
	}

	@Override
	public int getSrc() {
		return this.src.getKey();
	}

	@Override
	public int getDest() {
		return this.dest.getKey();
	}

	@Override
	public double getWeight() {
		return this.weight;
	}

	@Override
	public String getInfo() {
		return this.info;
	}

	@Override
	public void setInfo(String s) {
		this.info = s;
	}

	@Override
	public int getTag() {
		return this.tag;
	}

	@Override
	public void setTag(int t) {
		this.tag = t;
	}

	public void setweight(double w) {
		this.weight=w;
	}


}
