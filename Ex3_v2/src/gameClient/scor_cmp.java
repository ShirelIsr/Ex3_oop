package gameClient;
import java.util.Comparator;

class scor_cmp implements Comparator<Fruit> {



	@Override
	public int compare(Fruit o1, Fruit o2) {

		return (int)(o1.getValue()-o2.getValue());
	}
	

}
