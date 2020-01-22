package gameClient;
import java.util.Comparator;

import dataStructure.node_data;

class scor_cmp implements Comparator<Double> {


	@Override
	public int compare(Double o1, Double o2) {
		// TODO Auto-generated method stub
			return (int)(o1-o2);
	}
	

}
