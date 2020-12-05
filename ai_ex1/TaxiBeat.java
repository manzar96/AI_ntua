import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.System;


public class TaxiBeat {

	public static void main(String[] args) {

		// Declarations

        	int taxidmin ;
        	Double mindst = Double.MAX_VALUE ;
		Pair<Double,ArrayList<ArrayList<Pair<Double,Double>>>> returnval ;
        	ArrayList<Pair<Double,ArrayList<ArrayList<Pair<Double,Double>>>>> listofpaths ;
		ArrayList<ArrayList<Pair<Double,Double>>> minpaths ; 
		Iterator<Taxis> ittaxis ;
		Taxis taxi ;
		String output ;
		Mapping mymap = new Mapping() ;
		Input in = new Input();
		Updatemap up = new Updatemap();

		taxidmin = 0;

        	// list of paths contains the shortest distance and the shortest path of each route

		listofpaths = new ArrayList<Pair<Double,ArrayList<ArrayList<Pair<Double,Double>>>>>();

		// minimum path contains the min path for the taxi chosen

		minpaths = new ArrayList<ArrayList<Pair<Double,Double>>>();

        	try {

			if( args.length != 4 ) {
			
				System.out.println("Wrong argument's given as input !");
				System.out.println("Usage is : nodes.csv client.csv taxis.csv outputfile ! ");
			}
		
			else {

				// Let's create our map based on nodes.csv file

				// args[0] = nodes.csv

				mymap.createMap(args[0]);

				// Let's read files client.csv and taxis.csv

				// args[1] = client.csv
				// args[2] = taxis.csv

            			in.readinput(args[1],args[2]);

				mymap.client = in.client;
				mymap.taxilist = in.taxilist;

				// Now we have to update client's and taxi's coordinates in order to be matched to current map nodes

				up.doupdate(mymap);

				// Name of the .kml file which will be given as output

				output = args[3] ;

            			Astar astar = new Astar();

            			ittaxis = mymap.taxilist.iterator();

				// For each and every taxi find the minimum path 

            			while(ittaxis.hasNext()) {

                			taxi = ittaxis.next();

                			returnval = astar.findpath( mymap.hmap , mymap.client , taxi );

                			// Add path and distance

                			listofpaths.add(returnval);

					// If path was found

                			if( returnval.getA()!=-1 ) { 
 
						// If the path found has a smaller distance 

                        			if( returnval.getA() < mindst ) {

                            				mindst = returnval.getA();
                            				minpaths = returnval.getB();
                            				taxidmin = taxi.taxid;

                        			}
                			}
            			}


            			KML mykml= new KML();

            			mykml.make( listofpaths , minpaths , mymap.taxilist , taxidmin , output );
			}

        	}

        	catch(Exception ex) {

            		System.out.println("Error in main function --> something went completely wrong !");

        	}
    	}
}
