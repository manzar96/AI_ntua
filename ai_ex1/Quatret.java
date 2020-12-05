import java.util.Objects;
import java.util.ArrayList;

public class Quatret implements Comparable<Quatret> {


    	public final Double first;
    	public final Double second;
    	public  Double third;
    	public final ArrayList<Pair<Double,Double>> fourth;

    	public Quatret(final Double first, final Double second, final Double third,final ArrayList<Pair<Double,Double>> fourth) {

        	this.first = first;
        	this.second = second;
        	this.third = third;
        	this.fourth= fourth;

    	}

    	public static  Quatret valueOf(Double a,Double b,Double c,ArrayList<Pair<Double,Double>> d) {

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

	public void setThird(Double third) {
		this.third = third;
	}
    	public ArrayList<Pair<Double,Double>> getForth() {

       		return this.fourth;

    	}

    	@Override
    	public int hashCode() {

        	int hash = 3;
        	hash = 23 * hash + Objects.hashCode(this.first);
        	hash = 23 * hash + Objects.hashCode(this.second);
        	hash = 23 * hash + Objects.hashCode(this.third);
        	hash = 23 * hash + Objects.hashCode(this.fourth);
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

        	if (!Objects.equals(this.fourth, other.fourth)) {

            		return false;
       		}

        	return true;    
    } 

}
