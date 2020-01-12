package algorithms;
import java.util.Comparator;

import dataStructure.node_data;

class graph_cmp implements Comparator<node_data> {

	@Override
	public int compare(node_data o1, node_data o2) {
		return (int)(o1.getWeight()-o2.getWeight());
	}
	

}
