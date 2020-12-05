
public class Taxis {

	// coordinates and taxid are global variables

    	Tuple<Double,Double> coordinates;

    	int taxid;

    	public void data( String[] parts ) {

		double x,y;

		// taxis input is in form x,y,taxi's id

		x = Double.parseDouble(parts[0]);
		y = Double.parseDouble(parts[1]);

        	taxid = Integer.parseInt(parts[2]);

        	coordinates = Tuple.valueOf(x,y);


    	}

    	public Tuple<Double,Double> getcoordinates() {

        	return coordinates;

    	}

    	public int getid() {

        	return taxid;

    	}

}
