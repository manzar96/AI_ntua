import java.io.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.System;
import java.util.Random;

public class KML {


    public void make (ArrayList<Tuple<Double,ArrayList<Tuple<Double,Double>>>> listofpaths, ArrayList<Tuple<Double,Double>>  minimumpath, Long minID, ArrayList<Long> listoftaxis) {

        try {

            		PrintStream out = new PrintStream(new FileOutputStream("taxis2.kml"));
            		System.setOut(out);
			String newcolor ;

	    		// The above is considered as standard output

            		System.out.println( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
            		System.out.println( "<kml xmlns=\"http://earth.google.com/kml/2.1\">");
            		System.out.println( "<Document>");
            		System.out.println( "<name>Taxi Routes</name>");

			// We will print firstly the green route 
			// Green route is considered as route for the closest taxi

		    	System.out.println( "<Style id=\"green\">");
		    	System.out.println( "<LineStyle>");
		    	System.out.println( "<color>ff009900</color>");
			System.out.println( "<width>4</width>");
			System.out.println( "</LineStyle>");
			System.out.println( "</Style>");

		    	Iterator<Tuple<Double,ArrayList<Tuple<Double,Double>>>> iterc= listofpaths.iterator();
		    	ArrayList<String> colors = new ArrayList<String>();
		    	int N=0;

		    	while(iterc.hasNext()) {

				Tuple<Double,ArrayList<Tuple<Double,Double>>> ant=iterc.next();

				if (ant.getA()==-1.0) continue;

				newcolor = getcolour();

               		 	while (colors.lastIndexOf(newcolor)!=-1) {
                    			newcolor = getcolour();
                		}

				colors.add(newcolor);
				System.out.println( "<Style id=\"" + newcolor + "\">");
				System.out.println( "<LineStyle>");
				System.out.println( "<color>" + newcolor + "</color>");
				System.out.println( "<width>4</width>");
				System.out.println( "</LineStyle>");
				System.out.println( "</Style>");
				N++;

			}

			System.out.println(  "<Placemark>");
			System.out.println(  "<name>Taxi 1, "+ minID + "</name>");
			System.out.println(  "<styleUrl>#green</styleUrl>");
			System.out.println(  "<LineString>");
			System.out.println(  "<altitudeMode>relative</altitudeMode>");
			System.out.println(  "<coordinates>");

			Iterator<Tuple<Double,Double>> iter = minimumpath.iterator();


		        while(iter.hasNext()) {

		            Tuple<Double,Double> pair = iter.next();

		            System.out.print(pair.getA());
		            System.out.print(",");
		            System.out.print(pair.getB());
		            System.out.println(",0");

		        }

		        System.out.println(  "</coordinates>");
		        System.out.println(  "</LineString>");
		        System.out.println(  "</Placemark>");


		        Iterator<Tuple<Double,ArrayList<Tuple<Double,Double>>>> iterb = listofpaths.iterator();
		        Iterator<Long> itert = listoftaxis.iterator();

		        int i=0;

		        while(iterb.hasNext()) {
		                Tuple<Double,ArrayList<Tuple<Double,Double>>> mypair = iterb.next();
		                Long mytaxi_id = itert.next();
		                if (mypair.getA()==-1) continue;

		                if (mytaxi_id==minID) continue;

		                ArrayList<Tuple<Double,Double>> path=mypair.getB();
		                String tcolor = colors.get(i);

		                System.out.println(  "<Placemark>");
		                int i2=i+2;
		                System.out.println(  "<name> Taxi "+ i2 + ", " +mytaxi_id + "</name>");
		                System.out.println(  "<styleUrl>#"+ tcolor + "</styleUrl>");
		                System.out.println(  "<LineString>");
		                System.out.println(  "<altitudeMode>relative</altitudeMode>");
		                System.out.println(  "<coordinates>");

		                Iterator<Tuple<Double,Double>> it=path.iterator();

		                while(it.hasNext()) {

		                    Tuple<Double,Double> coor = it.next();

		                    System.out.print(coor.getA());
		                    System.out.print(",");
		                    System.out.print(coor.getB());
		                    System.out.println(",0");

		                }

		                System.out.println(  "</coordinates>");
		                System.out.println(  "</LineString>");
		                System.out.println(  "</Placemark>");

		                i++;
                	}
		    System.out.println(  "</Document>");
		    System.out.println(  "</kml>");

        	}

        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public void forclient(ArrayList<Tuple<Double,Double>>   path,Long taxi_id,Double distance) {

        try {

		    PrintStream out = new PrintStream(new FileOutputStream("client.kml"));
		    System.setOut(out);

		    System.out.println( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		    System.out.println( "<kml xmlns=\"http://earth.google.com/kml/2.1\">");
		    System.out.println( "<Document>");
		    System.out.println( "<name>Taxi Routes</name>");


		    System.out.println( "<Style id=\"green\">");
		    System.out.println( "<LineStyle>");
		    System.out.println( "<color>ff009900</color>");
		    System.out.println( "<width>4</width>");
		    System.out.println( "</LineStyle>");
		    System.out.println( "</Style>");

		    if(distance!=-1.0){
		        System.out.println(  "<Placemark>");
		        System.out.println(  "<name>Taxi 1, "+ taxi_id + "</name>");
		        System.out.println(  "<styleUrl>#green</styleUrl>");
		        System.out.println(  "<LineString>");
		        System.out.println(  "<altitudeMode>relative</altitudeMode>");
		        System.out.println(  "<coordinates>");

		        Iterator<Tuple<Double,Double>> iter = path.iterator();
		        while(iter.hasNext()) {
		            Tuple<Double,Double> pair = iter.next();

		            System.out.print(pair.getA());
		            System.out.print(",");
		            System.out.print(pair.getB());
		            System.out.println(",0");
		        }

		        System.out.println(  "</coordinates>");
		        System.out.println(  "</LineString>");
		        System.out.println(  "</Placemark>");

		    }
		    System.out.println(  "</Document>");
		    System.out.println(  "</kml>");
		}
        catch(Exception ex){
            System.out.println(ex);
        }
    }

    public String getcolour() {

	
	// Declarations 

       	Random randomGenerator = new Random();
	int R , G , B ;
	String colourfinale ;
	Color randomColour ;
	float saturation = 0.6f;				//1.0 for brilliant, 0.0 for dull
	float luminance = 0.6f; 				//1.0 for brighter, 0.0 for black
	float hue = randomGenerator.nextFloat();

	// Generate random R,G,B values

	R = randomGenerator.nextInt(256);
	G = randomGenerator.nextInt(256);
	B = randomGenerator.nextInt(256);

	// Generate random pastel colour

	randomColour = new Color(R,G,B);

	//randomColour = randomColour.getHSBColor(hue, saturation, luminance);

	colourfinale = Integer.toHexString(randomColour.getRGB()).substring(2) ;
	colourfinale = colourfinale.concat("ff"); 

        return colourfinale;  
      
    }
}
