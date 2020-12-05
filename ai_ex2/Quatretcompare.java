import java.util.Comparator;

public class Quatretcompare implements Comparator<Quatret> {

    @Override
    public int compare(Quatret a ,Quatret b) {

        if(a.getThird() + a.getForth() > b.getThird() + b.getForth()) 		return 1;

        if(a.getThird() +a.getForth() < b.getThird() + b.getForth()) 		return -1;

        return 0;

    }
}
