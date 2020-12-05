import java.io.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.System;
import java.util.Random;

public class KML {


	public void make ( ArrayList<Pair<Double,ArrayList<ArrayList<Pair<Double,Double>>>>> listofpaths , ArrayList<ArrayList<Pair<Double,Double>>>  minimumpath , ArrayList<Taxis> listoftaxis , int minid , String output ) {

  		try {
	
			String newcolor , pcolor;
			int taxno = 1;
			int taxid = 0;
			int counter = 0;
			int i = 0;

			Pair<Double,ArrayList<ArrayList<Pair<Double,Double>>>> mypair,ant ;
			ArrayList<ArrayList<Pair<Double,Double>>> paths ;
			Iterator<ArrayList<Pair<Double,Double>>> iter ;
			Iterator<Taxis> itert ;
			ArrayList<String> colors ;
			Iterator<Pair<Double,ArrayList<ArrayList<Pair<Double,Double>>>>> iterp ;

        		PrintStream out = new PrintStream(new FileOutputStream(output+".kml"));
        		System.setOut(out);

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

			iterp = listofpaths.iterator();

			// we will keep track of the colours generated

			colors = new ArrayList<String>();

			// Let's print section 1

			while(iterp.hasNext()) {

                		ant = iterp.next();

                		if (ant.getA() == -1.0) continue ;

                		newcolor = getcolour();

				// Makes colour more vivid

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
          		}
            

                	iterp = listofpaths.iterator();
                	itert = listoftaxis.iterator();

               		while(iterp.hasNext()) {

				if ( taxno == 1 ) {

					pcolor = "green";
					taxid = minid;
					iter = minimumpath.iterator();
				}
	
				else {
				
                        		mypair = iterp.next();
                        		Taxis taxi = itert.next();
					taxid = taxi.getid();

                        		if ( ( mypair.getA() == -1 ) || ( taxid == minid ) ) continue ;

                        		paths = mypair.getB();
                        		pcolor = colors.get(taxno-2);
					iter = paths.iterator();

				}

                        	System.out.println(  "<Placemark>");
                       	 	System.out.println(  "<name> Taxi "+ taxno + ", " + taxid + "</name>");
                        	System.out.println(  "<styleUrl>#"+ pcolor + "</styleUrl>");
                        	System.out.println(  "<LineString>");
                        	System.out.println(  "<altitudeMode>relative</altitudeMode>");
                        	System.out.println(  "<coordinates>");
				counter = 0;
                        	while(iter.hasNext()) {

                            		 ArrayList<Pair<Double,Double>> almostdone = iter.next();
					 
					 // if counter is 0 print from taxi to client

                        		 if( counter == 0 ) {

						for ( i =0 ; i<almostdone.size() ; i++) {

							Pair<Double,Double> coor = almostdone.get(i);
                           		 		System.out.print(coor.a);
                            		 		System.out.print(",");
                            		 		System.out.print(coor.b);
                            		 		System.out.println(",0");
							counter +=1;
						}
					}

					else {
			
					// if counter is 1 print from client to taxi

						for ( i =almostdone.size()-1 ; i>=0 ; i--) {

							Pair<Double,Double> coor = almostdone.get(i);
                           		 		System.out.print(coor.a);
                            		 		System.out.print(",");
                            		 		System.out.print(coor.b);
                            		 		System.out.println(",0");
							counter = 0;
						}
					}

                        	}
                        	System.out.println(  "</coordinates>");
                        	System.out.println(  "</LineString>");
                        	System.out.println(  "</Placemark>");
                
                        	taxno++;
               		}

            		System.out.println(  "</Document>");
            		System.out.println(  "</kml>");
    
        }

        catch(Exception ex) {

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
