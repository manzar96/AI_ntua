
import java.io.*;

public class Client {

    Tuple<Double,Double> coordinates;
    Tuple<Double,Double> dest_coordinates;

    public void data(String clientcoor) {

        try {

		BufferedReader reader = new BufferedReader( new FileReader(clientcoor) );

          	String in;
	  	double x,y,destx,desty;

          	in=reader.readLine();
          	in=reader.readLine();

          	String[] parts;

		// input is in form x,y
          	parts = in.split(",");  

		// let's cast string input to doubles

	  	x=Double.parseDouble(parts[0]);
	  	y=Double.parseDouble(parts[1]);
	  	destx=Double.parseDouble(parts[2]);
	  	desty=Double.parseDouble(parts[3]);

		//System.out.println(x);
		//System.out.println(y);

          	coordinates=Tuple.valueOf(x,y);
		dest_coordinates=Tuple.valueOf(destx,desty);

        }

        catch(IOException ex) {

		System.out.println("Error while reading client's coordinates ! ");

        }

    }

    public Tuple<Double,Double> getcoordinates() {

        	return coordinates;

    }

    public Tuple<Double,Double> getdest_coordinates() {

        	return dest_coordinates;

    }


/*

	//This main function is used only for debugging

	public static void main(String[] args) { 
	
		data(args[0]);
	}

*/



}
