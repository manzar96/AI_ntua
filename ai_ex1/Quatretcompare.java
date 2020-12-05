import java.util.Comparator;

public class Quatretcompare implements Comparator<Quatret> {

    @Override
    public int compare(Quatret a ,Quatret b) {


        	if ( a.getThird() > b.getThird() ) return 1;

       		if( a.getThird() < b.getThird() ) return -1;

		return 0;



    }

}
