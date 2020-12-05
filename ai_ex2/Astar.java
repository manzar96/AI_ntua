import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Collections;
import java.io.*;
import java.util.TreeSet;
import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;

public class Astar {

    Quatretcompare mycomparator = new Quatretcompare();

    public Tuple<Double,ArrayList<Tuple<Double,Double>>> findpath(Mapping mymap,Double taxi_x,Double taxi_y,Long taxi_id,Double client_x,Double client_y,int sizePQ,JIPEngine jip_nodes,JIPEngine jip_belongs,JIPEngine jip_lines,JIPEngine jip_traffic,PrintStream goals,PrintStream stdout){


        TreeSet<Quatret> myTS = new TreeSet<Quatret>(mycomparator);

        //Checks if node has been visited
        HashSet<Tuple<Double,Double>> visited=new HashSet<Tuple<Double,Double>>();

        HashMap <Tuple<Double,Double>, Double> bestdist=new HashMap <Tuple<Double,Double>,Double>();

        //shortestDistance will be return with the shortestpath
        Double distance=-1.0;
        ArrayList<Tuple<Double,Double>> path=new ArrayList<Tuple<Double,Double>>();
        HashMap<Tuple<Double,Double>,Tuple<Double,Double>> prev=new HashMap<Tuple<Double,Double>,Tuple<Double,Double>>();


        Tuple<Double,Double> taxi_coordinates=Tuple.valueOf(taxi_x,taxi_y);
        Tuple<Double,ArrayList<Neighbor>> heurvalANDneighbors=mymap.hmap.get(taxi_coordinates);
        Double heuristic_dist=heurvalANDneighbors.getA();

        heuristic_dist=update_heur(mymap,taxi_x,taxi_y,taxi_id,heuristic_dist,jip_nodes,jip_belongs,jip_lines,jip_traffic);
        prev.put(taxi_coordinates,Tuple.valueOf(-1.0,-1.0));
        Quatret start=Quatret.valueOf(taxi_coordinates.getA(),taxi_coordinates.getB(),heuristic_dist,0.0);

        //add start in pQ
        myTS.add(start);
        bestdist.put(taxi_coordinates,0.0);



        while(!myTS.isEmpty()) {

            //pop first node from PQ
            Quatret current=myTS.pollFirst();

            Tuple<Double,Double> coordinates=Tuple.valueOf(current.getFirst(),current.getSecond());

	    // if client is found then...
            if((current.getFirst().equals(client_x)) && (current.getSecond().equals(client_y))) {
                System.setOut(goals);
                System.out.println("GOAL FOUND! Taxi:" +taxi_id);
                distance= current.getForth();
                System.out.print("Distance to goal: ");
                System.out.println(distance);
            	System.out.println("");
                System.setOut(stdout);
                path=mypath(coordinates,prev);
                break;
            }

            else {

                //if visited continue
                if(visited.contains(coordinates)) {
                    continue;
                }

                else{
                    visited.add(coordinates);
                    Double dist_already=current.getForth();
                    Long current_id=mymap.hmapn.get(coordinates);
                    String neighbor_id_s=null;
                    Long neighbor_id=null;

                    JIPTermParser parser = jip_nodes.getTermParser();
                    JIPQuery jipQuery;
                    JIPTerm term;
                    jipQuery = jip_nodes.openSynchronousQuery(parser.parseTerm("nextnode("+current_id+",Neighbor_id)."));
            	    term = jipQuery.nextSolution();

                    while(term!=null) {

                        neighbor_id_s=String.format("%.0f",Double.parseDouble(term.getVariablesTable().get("Neighbor_id").toString()));
                        neighbor_id=Long.parseLong(neighbor_id_s);

                        Tuple<Double,Double> neighbor_coordinates=mymap.hmapnInverse.get(neighbor_id);
                        Double neighbor_heur=mymap.hmap.get(neighbor_coordinates).getA();
                        neighbor_heur=update_heur(mymap,neighbor_coordinates.getA(),neighbor_coordinates.getB(),neighbor_id,neighbor_heur,jip_nodes,jip_belongs,jip_lines,jip_traffic);
                        Double real_dist=mymap.euclidianDist(neighbor_coordinates.getA(),neighbor_coordinates.getB(),coordinates.getA(),coordinates.getB());
                        Quatret info=Quatret.valueOf(neighbor_coordinates.getA(),neighbor_coordinates.getB(),neighbor_heur,real_dist+dist_already);
                        Double exists=bestdist.get(neighbor_coordinates);

			// we accept minimum distances that are ceiling % close to the minimum distance

			//if( exists!=null ) {

			//	if ( ( exists*1.001 >= info.getForth() ) && ( exists < info.getForth()) )  info.setForth(exists);
			//}

                        if((exists==null)   ||  exists>info.getForth()) {

                            bestdist.put(neighbor_coordinates,info.getForth());
                            prev.put(neighbor_coordinates,coordinates);

                        }

                        myTS.add(info);
                        term = jipQuery.nextSolution();

                    }
                }
            }
        }

        //fix return Value
        Tuple<Double,ArrayList<Tuple<Double,Double>>> returnValue=Tuple.valueOf(distance,path);
        return returnValue;

    }

    public Double update_heur(Mapping mymap,Double taxi_x,Double taxi_y,Long taxi_id,Double heuristic_dist,JIPEngine jip_nodes,JIPEngine jip_belongs,JIPEngine jip_lines,JIPEngine jip_traffic){
        Tuple<Double,Double> taxi_coordinates=Tuple.valueOf(taxi_x,taxi_y);

        JIPTermParser parser = jip_nodes.getTermParser();
        JIPQuery jipQuery;
        JIPTerm term;
        String hour=null;
        String[] hour_split;
        jipQuery = jip_nodes.openSynchronousQuery(parser.parseTerm("client(_,_,_,_,Hour,_,_,_)."));
        term = jipQuery.nextSolution();

        if(term!=null) { 

            hour=term.getVariablesTable().get("Hour").toString();
            hour_split=hour.split("");
            hour="";

            for(int i=0;i<hour_split.length;i++) {
                if(hour_split[i].equals(",")) break;
                if(!hour_split[i].equals("(")  && !hour_split[i].equals(":") ){
                    hour=hour+hour_split[i];
                }
            }

        }


        String line=null;
        Long start_node_id=mymap.hmapn.get(taxi_coordinates);

        jipQuery = jip_belongs.openSynchronousQuery(parser.parseTerm("belongsTo("+start_node_id+",Line)."));
        term = jipQuery.nextSolution();

        if(term!=null) {

            line=term.getVariablesTable().get("Line").toString();

        }


        String lit=null;
        String lanes=null;
        String toll=null;
        jipQuery = jip_lines.openSynchronousQuery(parser.parseTerm("line("+line+",Highway,_,Lit,Lanes,_,_,_,_,_,_,_,_,_,_,Toll)."));
        term = jipQuery.nextSolution();

        if(term!=null) {

            lit=term.getVariablesTable().get("Lit").toString();
            lanes=term.getVariablesTable().get("Lanes").toString();
            toll=term.getVariablesTable().get("Toll").toString();

        }


        // traffic check

        String traffic="low";   
        Double starthour;
        Double endhour;
        String level=null;
        jipQuery = jip_traffic.openSynchronousQuery(parser.parseTerm("traffic("+line+",Starthour,EndHour,Level)."));
        term = jipQuery.nextSolution();

        while(term!=null) {

            starthour=Double.parseDouble(term.getVariablesTable().get("Starthour").toString());
            endhour=Double.parseDouble(term.getVariablesTable().get("EndHour").toString());
            level=term.getVariablesTable().get("Level").toString();

            if( Double.parseDouble(hour)>=starthour && Double.parseDouble(hour)<=endhour) {

                traffic=level;

            }

            term = jipQuery.nextSolution();
        }



        if( traffic.equals("medium"))   heuristic_dist=heuristic_dist+0.1*heuristic_dist;
        if(traffic.equals("high"))    heuristic_dist=heuristic_dist+0.2*heuristic_dist;
        if(lit.equals("yes") && Double.parseDouble(hour)>18)  heuristic_dist=heuristic_dist-heuristic_dist*0.05;
        if(lit.equals("no") && Double.parseDouble(hour)>18)  heuristic_dist=heuristic_dist+heuristic_dist*0.05;
        if(lit.equals("unknown") && Double.parseDouble(hour)>18)  heuristic_dist=heuristic_dist+heuristic_dist*0.05;
        if(toll.equals("yes"))   heuristic_dist=heuristic_dist+0.2*heuristic_dist;


        if(!lanes.equals("unknown")) {

            if( Double.parseDouble(lanes)>=3) {

                heuristic_dist=heuristic_dist-0.05*heuristic_dist;

            }

        }

        return heuristic_dist;
    }

    public ArrayList<Tuple<Double,Double>> mypath(Tuple<Double,Double> coordinates,HashMap<Tuple<Double,Double>,Tuple<Double,Double>> prev){
        ArrayList<Tuple<Double,Double>> path=new ArrayList<Tuple<Double,Double>>();
        path.add(coordinates);
        Tuple<Double,Double> previous=prev.get(coordinates);
        while(  (previous.getA()!=-1.0) && (previous.getB()!=-1.0)) {

            path.add(previous);
            previous=prev.get(previous);

        }

        return path;
    }
}
