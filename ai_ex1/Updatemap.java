import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.Arrays;
import java.util.HashSet;

public class Updatemap {

    HashMap<Pair<Double,Double>,Pair<Double,ArrayList<NeighborNode>>> mymap = new HashMap< Pair<Double,Double>,Pair<Double,ArrayList<NeighborNode>>>();	    
    ArrayList<Taxis> taxilist = new ArrayList<Taxis>();
    Client client = new Client();

    public void doupdate( Mapping mymapobj ) {

        try {

		taxilist = mymapobj.taxilist ;
		client = mymapobj.client ;
		mymap = mymapobj.hmap ;

            	int size = taxilist.size();
		int index = 0;

		Double dist , clientx , clienty ;
		Pair<Double,Double> clientin;
		Pair<Double,Double> taxicoor;

            	Double clientmindst = Double.MAX_VALUE;

            	Pair<Double,Double> clientpos;     

            	Double[] taximindst = new Double[size];
            	Double[] taxilong = new Double[size];
            	Double[] taxilat = new Double[size];
            	
		Arrays.fill(taximindst , Double.MAX_VALUE) ;

		clientin = client.getcoordinates();
		clientx = clientin.getA();
		clienty = clientin.getB();
		clientpos = clientin;

		// let's find the exact place of taxis and client on the map
		// in order to do so we fing the minimum distance between the current position and current nodes position

            	for( Pair<Double,Double> key: mymap.keySet()) {

                   
                    dist = dst(clientx , clienty , key.getA() , key.getB() );

                    if( dist<clientmindst ) {

                        clientmindst = dist;
                        clientpos = Pair.valueOf(key.getA(),key.getB());

                    }

                    Iterator<Taxis> itr = taxilist.iterator();

                    while(itr.hasNext()) {

                        Taxis taxi = itr.next();

                        taxicoor = taxi.getcoordinates();
                        dist = dst(taxicoor.getA() , taxicoor.getB() , key.getA() , key.getB() );

                        if(dist < taximindst[index] ) {

                            	taximindst[index] = dist;
                            	taxilong[index] = key.getA();
                            	taxilat[index] = key.getB();

                        }

                        index += 1;

                   }

                index = 0;
            }

	    // At this point we will update clients and taxis x and y values as found above

            client.coordinates = clientpos;

	    clientin = client.getcoordinates();
	    clientx = clientin.getA();
	    clienty = clientin.getB();

            Iterator<Taxis> itr = taxilist.iterator();

	    index = 0 ;

            while(itr.hasNext()) {

                Taxis taxi = itr.next();
                taxi.coordinates = Pair.valueOf(taxilong[index] , taxilat[index]);
                index+=1;

            }

	    // finally it's time to update all the euclidean distances on the current map 

            for( Pair<Double,Double> key: mymap.keySet()) {

                Pair<Double,ArrayList<NeighborNode> > sth = mymap.get(key);

                Double heurestic = dst( clientx , clienty , key.getA() , key.getB() );

                mymap.put(key,Pair.valueOf( heurestic , sth.getB() ));

            }

	    mymapobj.taxilist = taxilist ;
	    mymapobj.client = client ;
	    mymapobj.hmap = mymap ;

        }

        catch(Exception ex) {

            System.out.println("Error while importing client and taxis on the map !");

        }


    }

    	// let's calculate the euclidean distance
    	// the above function has been copied from the link below :
    	// https://www.geodatasource.com/developers/java

    public double dst(Double lon1,Double lat1,Double lon2,Double lat2) {

		if ( (lon1-lon2 == 0 ) && ( lat1-lat2 == 0) ) return 0.0 ;

		else {

				Double theta = lon1 - lon2;
				Double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
				dist = Math.acos(dist);
				dist = Math.toDegrees(dist);
				dist = dist * 60 * 1.1515;
				dist = dist * 1.609344;
        			return dist;
		}

    }

}
