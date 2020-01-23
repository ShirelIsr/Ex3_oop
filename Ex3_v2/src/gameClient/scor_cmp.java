package gameClient;
import java.util.Comparator;
/**
 * this class is implements of comparator 
 * the compactor is in  used to sort the fruit list by the value of each fruit.
 * @author shire
 *
 */
class scor_cmp implements Comparator<Fruit> {



	@Override
	public int compare(Fruit o1, Fruit o2) {

		return (int)(o1.getValue()-o2.getValue());
	}
	

}
