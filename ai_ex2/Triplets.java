import java.util.Objects;
import java.util.ArrayList;

public class Triplets implements Comparable<Triplets>{

    	private final Long first;
    	private final Double second;
    	private final Double third;

    	public Triplets(final Long first, final Double second, final Double third) {

        	this.first = first;
        	this.second = second;
        	this.third = third;
    	}

    	public static  Triplets valueOf(Long a,Double b,Double c) {

  	 	return new Triplets(a,b,c);

    	}
	
    	public Long getFirst() {

        	return this.first;

    	}

    	public Double getSecond() {

        	return this.second;

    	}

    	public Double getThird() {

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
	public int compareTo(Triplets another) {

    		return another.getThird().compareTo(this.third);

	}

    	@Override
    	public boolean equals(Object some) {

        	if (some== null) {

            		return false;
        	}

        	if (getClass() != some.getClass()) {

            		return false;
       		 }

        	final Triplets other = (Triplets) some;

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
