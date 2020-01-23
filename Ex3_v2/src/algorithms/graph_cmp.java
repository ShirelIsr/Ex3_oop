package algorithms;
import java.util.Comparator;

import dataStructure.node_data;
/**
 * This class is comparator that we are using for sorting the nodes so we can know which is the
 *  node with the least weight for dijkstra algorithm 
 * @author теж
 *
 */
class graph_cmp implements Comparator<node_data> {
	

	@Override
	public int compare(node_data o1, node_data o2) {
		return (int)(o1.getWeight()-o2.getWeight());
	}
	

}
