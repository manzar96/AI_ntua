import java.io.*;

public class Client {

    Pair<Double,Double> coordinates;

    public void data(String clientcoor) {

        try {

		BufferedReader reader = new BufferedReader( new FileReader(clientcoor) );

          	String in;
	  	double x,y;

          	in=reader.readLine();
          	in=reader.readLine();

          	String[] parts;

		// input is in form x,y
          	parts = in.split(",");  

		// let's cast string input to doubles

	  	x=Double.parseDouble(parts[0]);
	  	y=Double.parseDouble(parts[1]);

		//System.out.println(x);
		//System.out.println(y);

          	coordinates=Pair.valueOf(x,y);

        }

        catch(IOException ex) {

		System.out.println("Error while reading client's coordinates ! ");

        }

    }

    public Pair<Double,Double> getcoordinates() {

        	return coordinates;

    }


/*

	//This main function is used only for debugging

	public static void main(String[] args) { 
	
		data(args[0]);
	}

*/



}


