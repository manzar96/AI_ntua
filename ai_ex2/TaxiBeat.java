import java.io.*;
import java.lang.System;
import java.util.ArrayList;
import java.util.Collections;
import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;

public class TaxiBeat {

    public  static void main(String[] args) {

        Mapping mymap = new Mapping();

        try{

            Integer sizePQ=1000;
            PrintStream stdout = System.out;

            PrintStream lines = new PrintStream(new FileOutputStream("maplines.pl"));
            PrintStream traffic = new PrintStream(new FileOutputStream("maptraffic.pl"));
            PrintStream nodes = new PrintStream(new FileOutputStream("mapnodes.pl"));
            PrintStream belongsTo = new PrintStream(new FileOutputStream("found.pl"));
            PrintStream goals = new PrintStream(new FileOutputStream("target.txt"));
            System.setOut(lines);

            mymap.lines(args[1]);
            mymap.makeMap2(args[0]);
            mymap.matchCoordinates(args[2],args[3]);

            System.setOut(nodes);
            mymap.nodes_facts(args[0],nodes,belongsTo);
            System.setOut(nodes);
            mymap.taxi_facts(args[3]);
            mymap.client_fact(args[2]);

            System.setOut(traffic);
            mymap.traffic_facts(args[4]);

            System.setOut(stdout);
            ArrayList<Long> listOfAvailableTaxis=new ArrayList<Long>();
            int availableTaxis=0;

            JIPEngine jip_nodes = new JIPEngine();
            jip_nodes.consultFile("mapnodes.pl");

            JIPEngine jip_belongs = new JIPEngine();
            jip_belongs.consultFile("found.pl");

            JIPEngine jip_lines = new JIPEngine();
            jip_lines.consultFile("maplines.pl");

            JIPEngine jip_traffic = new JIPEngine();
            jip_traffic.consultFile("maptraffic.pl");

            JIPTermParser parser= jip_nodes.getTermParser();

            JIPQuery jipQuery;
            JIPTerm term;

    	    jipQuery = jip_nodes.openSynchronousQuery(parser.parseTerm("availableTaxi(Y)."));
            term = jipQuery.nextSolution();
	    System.out.println("----------------------------------------------------------------------");
	    System.out.println("");
            System.out.println("Available Taxis.");

    	    while (term != null) {

                Long taxiid=Long.parseLong(term.getVariablesTable().get("Y").toString());

    		System.out.println("Taxi with id : " + taxiid +" is available."   );
                listOfAvailableTaxis.add(taxiid);
                availableTaxis++;
    		term = jipQuery.nextSolution();
    	    }

	    System.out.println("");
	    System.out.println("----------------------------------------------------------------------");
	    System.out.println("");

            Astar astar=new Astar();
            Tuple<Double,ArrayList<Tuple<Double,Double>>> returnValue;

            ArrayList<Tuple<Double,ArrayList<Tuple<Double,Double>>>> listofPathsforAvailableTaxis=new ArrayList<Tuple<Double,ArrayList<Tuple<Double,Double>>>>();
            Long min_taxi_id=Long.valueOf(1234567891);
            Double minimumDist=0.0;
            ArrayList<Tuple<Double,Double>> minimumpath=new ArrayList<Tuple<Double,Double>>();

            int flag=0;

            for(int i=0;i<availableTaxis;i++) {

        	jipQuery = jip_nodes.openSynchronousQuery(parser.parseTerm("taxi(TaxiX,TaxiY,"+listOfAvailableTaxis.get(i)+",_,_,_)."));
        	term = jipQuery.nextSolution();

                if(term!=null) {

                    Double taxi_x= Double.parseDouble(term.getVariablesTable().get("TaxiX").toString());
                    Double taxi_y= Double.parseDouble(term.getVariablesTable().get("TaxiY").toString());
                    returnValue = astar.findpath(mymap,taxi_x,taxi_y,listOfAvailableTaxis.get(i),mymap.client.coordinates.getA(),mymap.client.coordinates.getB(),sizePQ,jip_nodes,jip_belongs,jip_lines,jip_traffic,goals,stdout);

                    listofPathsforAvailableTaxis.add(returnValue);

                    if(returnValue.getA()!=-1) {

                        if(flag==0) {
                            minimumDist=returnValue.getA();
                            minimumpath=returnValue.getB();
                            min_taxi_id=listOfAvailableTaxis.get(i);
                            flag=1;
                        }

                        else {

                            if(returnValue.getA()<minimumDist) {
                                minimumDist=returnValue.getA();
                                minimumpath=returnValue.getB();
                                min_taxi_id=listOfAvailableTaxis.get(i);
                            }
                        }
                    }
                }
            }

            Double startx;
            Double starty;
            Double desx;
            Double desy;
            Double client_dist=0.0;
            ArrayList<Tuple<Double,Double>> client_path=null;

            jipQuery = jip_nodes.openSynchronousQuery(parser.parseTerm("client(Startx,Starty,Desx,Desy,_,_,_,_)."));
            term = jipQuery.nextSolution();

            if(term!=null) {

                startx=Double.parseDouble(term.getVariablesTable().get("Startx").toString());
                starty=Double.parseDouble(term.getVariablesTable().get("Starty").toString());
                desx=Double.parseDouble(term.getVariablesTable().get("Desx").toString());
                desy=Double.parseDouble(term.getVariablesTable().get("Desy").toString());
                returnValue = astar.findpath(mymap,mymap.client.dest_coordinates.getA(),mymap.client.dest_coordinates.getB(),min_taxi_id,mymap.client.coordinates.getA(),mymap.client.coordinates.getB(),sizePQ,jip_nodes,jip_belongs,jip_lines,jip_traffic,goals,stdout);
                client_dist=returnValue.getA();
                client_path=returnValue.getB();

            }

            ArrayList<Triple>   first_list=new ArrayList<Triple>();
            for(int i=0;i<availableTaxis;i++) {

                Triple mytriple=Triple.valueOf(listOfAvailableTaxis.get(i),(listofPathsforAvailableTaxis.get(i)).getA(),(listofPathsforAvailableTaxis.get(i)).getB());
                first_list.add(mytriple);

            }

            Collections.sort(first_list);
            System.out.println("Available taxis based on best distance to target :");
            for(int i=0;i<availableTaxis;i++) {


                System.out.println("Taxi with id "+first_list.get(i).getFirst()+" and distance "+first_list.get(i).getSecond());

            }

            ArrayList<Triplets>   second_list=new ArrayList<Triplets>();
            Double rating=0.0;

            for(int i=0;i<availableTaxis;i++) {

                jipQuery = jip_nodes.openSynchronousQuery(parser.parseTerm("taxi(_,_,"+listOfAvailableTaxis.get(i)+",_,Rating,_)."));
                term = jipQuery.nextSolution();

                if(term!=null) {
                    rating=Double.parseDouble(term.getVariablesTable().get("Rating").toString());
                }

                Triplets mytriple=Triplets.valueOf(listOfAvailableTaxis.get(i),(listofPathsforAvailableTaxis.get(i)).getA(),rating);
                second_list.add(mytriple);
            }

            Collections.sort(second_list);

	    System.out.println("");
	    System.out.println("----------------------------------------------------------------------");
	    System.out.println("");
	
            System.out.println("Available taxis based on ratings:");

            for(int i=0;i<availableTaxis;i++) {

                System.out.println("Taxi with id "+second_list.get(i).getFirst()+" rating "+second_list.get(i).getThird()+ " and distance "+second_list.get(i).getSecond());

            }

            KML mykml= new KML();
            mykml.make(listofPathsforAvailableTaxis, minimumpath,min_taxi_id, listOfAvailableTaxis);
            mykml.forclient(client_path,min_taxi_id,client_dist);
            System.setOut(stdout);
	    
            System.out.println("");
	    System.out.println("----------------------------------------------------------------------");

        }

        catch(Exception ex){
            System.out.println(ex);
        }
    }
}
