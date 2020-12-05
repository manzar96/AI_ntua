import java.util.Objects;
import java.util.ArrayList;

public class Quatret implements Comparable<Quatret> {

    	private final Double first;
    	private final Double second;
    	private final Double third;
    	public Double forth;

    	public Quatret(final Double first, final Double second, final Double third,final Double forth) {

        	this.first = first;
        	this.second = second;
        	this.third = third;
        	this.forth= forth;
    	}

    	public static  Quatret valueOf(Double a,Double b,Double c,Double d) {

  	 	return new Quatret(a,b,c,d);

    	}

    	public Double getFirst() {

        	return this.first;

    	}

    	public Double getSecond() {

        	return this.second;

    	}

    	public Double getThird() {

        	return this.third;

    	}

    	public Double getForth() {

       		return this.forth;

    	}

    	public void setForth(Double forth) {

		this.forth = forth;

    	}

    	@Override
    	public int hashCode() {

        	int hash = 3;
        	hash = 23 * hash + Objects.hashCode(this.first);
        	hash = 23 * hash + Objects.hashCode(this.second);
        	hash = 23 * hash + Objects.hashCode(this.third);
        	hash = 23 * hash + Objects.hashCode(this.forth);
        	return hash;

   	}

	@Override
	public int compareTo(Quatret another) {

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

        	final Quatret other = (Quatret) some;

        	if (!Objects.equals(this.first, other.first)) {

            		return false;
        	}

        	if (!Objects.equals(this.second, other.second)) {

            		return false;
        	}

        	if (!Objects.equals(this.third, other.third)) {

            		return false;
       		}

        	if (!Objects.equals(this.forth, other.forth)) {

            		return false;
       		}

        	return true;    
    } 

}
