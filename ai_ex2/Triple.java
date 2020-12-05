import java.util.Objects;
import java.util.ArrayList;

public class Triple implements Comparable<Triple> {

    	private final Long first;
    	private final Double second;
    	private final ArrayList<Tuple<Double,Double>> third;


    	public Triple(final Long first, final Double second, final ArrayList<Tuple<Double,Double>> third) {

        	this.first = first;
        	this.second = second;
        	this.third = third;

    	}

    	public static Triple valueOf(Long a,Double b,ArrayList<Tuple<Double,Double>> c) {

   		return new Triple(a,b,c);
    	}

    	public Long getFirst() {

        	return this.first;
    	}

    	public Double getSecond() {

        	return this.second;
    	}

    	public ArrayList<Tuple<Double,Double>> getThird() {

        	return this.third;
    	}

    	@Override
    	public int hashCode() {

        	int hash = 3;
        	hash = 23 * hash + Objects.hashCode(this.first);
        	hash = 23 * hash + Objects.hashCode(this.second);
        	hash = 23 * hash + Objects.hashCode(this.third);;
        	return hash;

   	}


	@Override
	public int compareTo(Triple another) {

    		return another.getSecond().compareTo(this.second);

	}

    	@Override
    	public boolean equals(Object some) {

        	if (some== null) {

            		return false;
        	}

        	if (getClass() != some.getClass()) {

            		return false;
       		 }

        	final Triple other = (Triple) some;

        	if (!Objects.equals(this.first, other.first)) {

            		return false;
        	}

        	if (!Objects.equals(this.second, other.second)) {

            		return false;
        	}

        	if (!Objects.equals(this.third, other.third)) {

            		return false;
       		}

        	return true;    
    } 

}
