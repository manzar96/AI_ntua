import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

public class Mapping {

	// Declaration of public variables

    	public HashMap<Pair<Double,Double>,Pair<Double,ArrayList<NeighborNode>>> hmap = new HashMap< Pair<Double,Double>,Pair<Double,ArrayList<NeighborNode>>>();
    	public ArrayList<Taxis> taxilist = new ArrayList<Taxis>();
	public Client client = new Client();

    	public void createMap(String nodes) {

        	try {
              		BufferedReader reader = new BufferedReader(new FileReader(nodes));

			Random randomGenerator = new Random();
			String current = null;
              		String next = null;
			String[] parts1;
			String[] parts2;
			String[] parts3;
			String[] temp;
			int flag = 0 ;
			double x1,y1,x2,y2;
			Double heurestic ;

			// Line 1 is in form X,Y,id,name 

              		current = reader.readLine();
			
			// From this line and below input start's

			current = reader.readLine();
			next = reader.readLine();
		
			parts1 = current.split(",");
			parts2 = next.split(",");

			// checking if the id of the lines is the same

			if( parts1[2].equals(parts2[2]) ) {

				Pair<Double,Double> key;

				x1 = Double.parseDouble(parts1[0]);
				y1 = Double.parseDouble(parts1[1]);
				x2 = Double.parseDouble(parts2[0]);
				y2 = Double.parseDouble(parts2[1]);

				key = Pair.valueOf(x1,y1);
				heurestic = randomGenerator.nextDouble();
		
				NeighborNode neighbor = new NeighborNode() ;
				
				neighbor.setcoordinates( x2 , y2 );
				neighbor.setdistance( x2 , y2 , x1 , y1 );
				neighbor.setroadid(Integer.parseInt(parts2[2]));

				ArrayList <NeighborNode> close = new ArrayList<NeighborNode>();

				close.add(neighbor);

				Pair<Double,ArrayList<NeighborNode>> sth ;
		
				sth = Pair.valueOf(heurestic,close);

				hmap.put(key,sth);
			
			}
			
			temp = parts1;
			parts3 = parts1;	
			parts1 = parts2;

			next = reader.readLine();
			parts2 = next.split(","); 

			// while there is a new line in the file given 
			// link current line with next line and previous line (if possible)

             		while( flag!= 4 ) {

				// if current line and next/previous line share the same id then

				if( parts1[2].equals(parts2[2]) ) {

					Pair<Double,Double> key;

					x1 = Double.parseDouble(parts1[0]);
					y1 = Double.parseDouble(parts1[1]);
					x2 = Double.parseDouble(parts2[0]);
					y2 = Double.parseDouble(parts2[1]);

                     			key = Pair.valueOf( x1 , y1 );

                     			Pair<Double,ArrayList<NeighborNode>> sth3 = hmap.get(key);

					NeighborNode neighbor = new NeighborNode() ;
				
					neighbor.setcoordinates( x2 , y2 );
					neighbor.setdistance( x2 , y2 , x1 , y1 );
					neighbor.setroadid(Integer.parseInt(parts2[2]));

					ArrayList <NeighborNode> close = new ArrayList<NeighborNode>();

					// if key is found in hash map

					if( sth3!=null ) {

						close = sth3.getB();

						close.add(neighbor);

						Pair<Double,ArrayList<NeighborNode>> sth2 ;
		
						sth2 = Pair.valueOf(sth3.getA(),close);

						hmap.put(key,sth2);
					}

					else {

						heurestic = randomGenerator.nextDouble();

						close.add(neighbor);

						Pair<Double,ArrayList<NeighborNode>> sth ;
		
						sth3 = Pair.valueOf(heurestic,close);

						hmap.put(key,sth3);
					}

				}

			      /* flag 0 means that we linked current node with next node
				 so now let's link current node with previous node 
				 flag 1 means that we linked current node with previous AND next node
				 so now let's read a new line
				 new line is not null continue linking
				 else link last node with the node before and break ( flag = 3) */

				if ( flag == 0 ) {

					temp = parts2;
					parts2 = parts3;
					flag = 1;
				}

				else if ( flag == 1 ) {

					parts3 = parts1;
					parts1 = temp;
					next = reader.readLine();

					if( next!=null ) { 

						parts2 = next.split(",");
						flag = 0;
					}

					else {
						parts2 = parts3;
						flag = 3 ;
					}
 
				}

				else flag = 4;
			


			}
                             
        	}

        catch(IOException ex) {

            System.out.println("Error while reading nodes file !");

        }

    }

}
