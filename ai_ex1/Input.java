import java.util.ArrayList;
import java.io.*;

    
public class Input {


   public ArrayList<Taxis> taxilist = new ArrayList<Taxis>();
   public Client client = new Client();


   public void readinput(String clients,String taxis) {

        try {

		String current = null;
		String[] parts;

		// At first we will files : client.csv and taxis.csv

            	client.data(clients);

            	BufferedReader reader = new BufferedReader(new FileReader(taxis));
            	
            	current = reader.readLine();

            	current = reader.readLine();

		// while there are more taxis 

            	while((current!=null && !current.isEmpty() )) {

                	parts = current.split(",");

			// create new taxi

                	Taxis taxi=new Taxis();

                	taxi.data(parts);
                	taxilist.add(taxi);

                	current = reader.readLine();

		}

         } 

        catch(Exception ex) {

            System.out.println("Error while reading files client.csv and taxis.csv !");

        }


    }

}
