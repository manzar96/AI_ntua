import java.util.HashMap;
import java.util.ArrayList;
import java.io.*;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;
import java.util.HashSet;
import java.lang.System;

import java.io.IOException;

import com.ugos.jiprolog.engine.JIPEngine;
import com.ugos.jiprolog.engine.JIPQuery;
import com.ugos.jiprolog.engine.JIPSyntaxErrorException;
import com.ugos.jiprolog.engine.JIPTerm;
import com.ugos.jiprolog.engine.JIPTermParser;

public class Mapping {

    HashMap< Tuple<Double,Double> , Tuple<Double,ArrayList<Neighbor> > > hmap= new HashMap< Tuple<Double,Double> , Tuple<Double,ArrayList<Neighbor> > >();
    HashMap< Tuple<Double,Double> , Long   > hmapn= new HashMap< Tuple<Double,Double> , Long   >();

    HashMap<Long,Tuple<Double,Double>> hmapnInverse=new HashMap<Long,Tuple<Double,Double>>();
    Client client=new Client();

    ArrayList<Taxis> listOfTaxis=new ArrayList<Taxis>();

    public void makeMap2(String nodesFile){
        int crosspaths=0;
        try{
              BufferedReader reader = new BufferedReader(new FileReader(nodesFile));
              String line=null;
              String line_next=null;
              line=reader.readLine();
              line=reader.readLine();
              line_next=reader.readLine();

              int time=1;
              String[] line_split;
              String[] line_next_split;
              String[] line_prev_split;

              line_prev_split=line.split(",");
              line_split=line.split(",");
              line_next_split=line_next.split(",");

             while((line_next!=null)) {

                if(time==1) {

                    if(line_split[2].equals(line_next_split[2])) {

                         Tuple<Double,Double> key;
                         key=Tuple.valueOf(Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));

                         Double heurValue=euclidianDist(23.7838475,37.9808714,key.getA(),key.getB());

                         Neighbor neighbor=new Neighbor();
                         neighbor.setcoordinates(Double.parseDouble(line_next_split[0]),Double.parseDouble(line_next_split[1]));
                         neighbor.setdistance(Double.parseDouble(line_next_split[0]),Double.parseDouble(line_next_split[1]),Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));
                         neighbor.setroadid(Integer.parseInt(line_next_split[2]));

                         ArrayList<Neighbor> array=new ArrayList<Neighbor>();
                         array.add(neighbor);

                         Tuple<Double,ArrayList<Neighbor> > valofKey;
                         valofKey=Tuple.valueOf(heurValue,array);

                         hmap.put(key,valofKey);
                     }
                 }

            else {

                 line_prev_split=line_split;
                 line_split=line_next_split;
                 line_next_split=line_next.split(",");

                 if(line_split[2].equals(line_prev_split[2])) {

                     Tuple<Double,Double> key;
                     key=Tuple.valueOf(Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));

                     Tuple<Double,ArrayList<Neighbor>> valofKey = hmap.get(key);

                     if(valofKey!=null) {

                         ArrayList<Neighbor> array=valofKey.getB();

                         Neighbor neighbor=new Neighbor();
                         neighbor.setcoordinates(Double.parseDouble(line_prev_split[0]),Double.parseDouble(line_prev_split[1]));

                         neighbor.setdistance(Double.parseDouble(line_prev_split[0]),Double.parseDouble(line_prev_split[1]),Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));
                         neighbor.setroadid(Integer.parseInt(line_prev_split[2]));

                         array.add(neighbor);

                         Tuple<Double,ArrayList<Neighbor> > newValue;
                         newValue=Tuple.valueOf(valofKey.getA(),array);

                         hmap.put(key,newValue);
                     }

                     else {

                         Double heurValue=euclidianDist(23.7838475,37.9808714,key.getA(),key.getB());

                         Neighbor neighbor=new Neighbor();
                         neighbor.setcoordinates(Double.parseDouble(line_prev_split[0]),Double.parseDouble(line_prev_split[1])); 

                         neighbor.setdistance(Double.parseDouble(line_prev_split[0]),Double.parseDouble(line_prev_split[1]),Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));
                         neighbor.setroadid(Integer.parseInt(line_prev_split[2]));

                         ArrayList<Neighbor> array=new ArrayList<Neighbor>();
                         array.add(neighbor);

                         valofKey=Tuple.valueOf(heurValue,array);

                         hmap.put(key,valofKey);
                     }
                 }

                 if(line_split[2].equals(line_next_split[2])) {

                     Tuple<Double,Double> key;
                     key=Tuple.valueOf(Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));

                     Tuple<Double,ArrayList<Neighbor>> valofKey = hmap.get(key);

                     if(valofKey!=null){

                         ArrayList<Neighbor> array=valofKey.getB();

                         Neighbor neighbor=new Neighbor();
                         neighbor.setcoordinates(Double.parseDouble(line_next_split[0]),Double.parseDouble(line_next_split[1]));

                         neighbor.setdistance(Double.parseDouble(line_next_split[0]),Double.parseDouble(line_next_split[1]),Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));
                         neighbor.setroadid(Integer.parseInt(line_next_split[2]));

                         array.add(neighbor);

                         Tuple<Double,ArrayList<Neighbor> > newValue;
                         newValue=Tuple.valueOf(valofKey.getA(),array);

                         hmap.put(key,newValue);
                     }

                     else {

                         Double heurValue=euclidianDist(23.7838475,37.9808714,key.getA(),key.getB());

                         Neighbor neighbor=new Neighbor();
                         neighbor.setcoordinates(Double.parseDouble(line_next_split[0]),Double.parseDouble(line_next_split[1]));

                         neighbor.setdistance(Double.parseDouble(line_next_split[0]),Double.parseDouble(line_next_split[1]),Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));
                         neighbor.setroadid(Integer.parseInt(line_next_split[2]));

                         ArrayList<Neighbor> array=new ArrayList<Neighbor>();
                         array.add(neighbor);

                         valofKey=Tuple.valueOf(heurValue,array);

                         hmap.put(key,valofKey);
                     }
                 }
             }

             time=0;
             line_next=reader.readLine();

            }

            line_prev_split=line_split;
            line_split=line_next_split;

            if(line_split[2].equals(line_prev_split[2])) {

                Tuple<Double,Double> key;
                key=Tuple.valueOf(Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));

                Tuple<Double,ArrayList<Neighbor>> valofKey = hmap.get(key);

                if(valofKey!=null) {

                    ArrayList<Neighbor> array=valofKey.getB();

                    Neighbor neighbor=new Neighbor();
                    neighbor.setcoordinates(Double.parseDouble(line_prev_split[0]),Double.parseDouble(line_prev_split[1]));

                    neighbor.setdistance(Double.parseDouble(line_prev_split[0]),Double.parseDouble(line_prev_split[1]),Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));
                    neighbor.setroadid(Integer.parseInt(line_prev_split[2]));

                    array.add(neighbor);

                    Tuple<Double,ArrayList<Neighbor> > newValue;
                    newValue=Tuple.valueOf(valofKey.getA(),array);

                    hmap.put(key,newValue);
                }


                else {


                    Double heurValue=euclidianDist(23.7838475, 37.9808714,key.getA(),key.getB());

                    Neighbor neighbor=new Neighbor();
                    neighbor.setcoordinates(Double.parseDouble(line_prev_split[0]),Double.parseDouble(line_prev_split[1]));

                    neighbor.setdistance(Double.parseDouble(line_prev_split[0]),Double.parseDouble(line_prev_split[1]),Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));
                    neighbor.setroadid(Integer.parseInt(line_prev_split[2]));

                    ArrayList<Neighbor> array=new ArrayList<Neighbor>();
                    array.add(neighbor);

                    valofKey=Tuple.valueOf(heurValue,array);

                    hmap.put(key,valofKey);
                }
            }
        }

        catch(IOException ex){
            System.out.println(ex);
            System.out.println("Error Reading File: nodesFile not found");
        }
    }

    public void lines(String linesFile) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(linesFile));
            String line=null;
            line=reader.readLine();

            line=reader.readLine();
            String[] line_split;

            while(line!=null) {

                line_split=line.split("");
                String[] exception_split=line.split(",");

                if(exception_split[0].equals("169658542")) {
                    System.out.println("line(169658542,unknown,unknown,unknown,unknown,unknown,unknown,unknown,unknown,unknown,fence,unknown,unknown,unknown,unknown,unknown).");
                }

                else {

                    int length=line_split.length;
                    int commas=0;
                    int counter=0;
                    System.out.print("line("+line_split[counter]);

                    counter=1;
                    while(counter<length) {

                        if(!line_split[counter].equals(",")) {

                            if((commas!=2)&&(commas!=14)) {

                                System.out.print(line_split[counter]);

                            }

                        }

                        else if((line_split[counter].equals(",")) && (!line_split[counter-1].equals(","))) {

                            if((commas!=2)&&(commas!=14)) {

                                System.out.print(",");

                            }

                            commas++;

                        }

                        else if((line_split[counter].equals(",")) && (line_split[counter-1].equals(","))) {

                            if((commas!=2)&&(commas!=14)) {

                                System.out.print("unknown,");

                            }

                            commas++;

                        }

                        counter++;
                    }

                    if(line_split[length-1].equals(",")&&(commas==17)) {

                        System.out.print("unknown");

                    }

                    System.out.println(").");
                }

            line=reader.readLine();

            }

            System.out.println("availableLine(X):-  line(X,primary,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println("availableLine(X):-  line(X,secondary,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println("availableLine(X):-  line(X,secondary_link,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println("availableLine(X):-  line(X,residential,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println("availableLine(X):-  line(X,tertiary,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println("availableLine(X):-  line(X,tertiary_link,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println("availableLine(X):-  line(X,motorway_link,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println("availableLine(X):-  line(X,primary_link,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println("availableLine(X):-  line(X,track,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println("availableLine(X):-  line(X,service,_,_,_,_,_,_,_,_,_,_,_,_,_,_).");
            System.out.println( "oneway(X):-    availableLine(X)    ,   line(X,_,yes,_,_,_,_,_,_,_,_,_,_,_,_,_).  "); 
            System.out.println( "inverseway(X):-    availableLine(X)    ,   line(X,_,-1,_,_,_,_,_,_,_,_,_,_,_,_,_).  ");
            System.out.println( "twoway(X):-    availableLine(X)    ,   line(X,_,no,_,_,_,_,_,_,_,_,_,_,_,_,_).  ");
            System.out.println( "twoway(X):-    availableLine(X)    ,   line(X,_,unknown,_,_,_,_,_,_,_,_,_,_,_,_,_).  ");

        }

        catch(IOException ex) {
            System.out.println(ex);
            System.out.println("Error Reading File: linesFile not found");
        }
    }


    public void matchCoordinates(String clientFile,String taxisFile) {

        try {

            client.data(clientFile);

            BufferedReader reader = new BufferedReader(new FileReader(taxisFile));
            String line=null;

            line=reader.readLine();

            line=reader.readLine();
            String[] line_split;

            while(line!=null) {

                line_split=line.split(",");
                Taxis taxi=new Taxis();
                taxi.data(line_split);
                listOfTaxis.add(taxi);
                line=reader.readLine();
            }


            int counter=1;

            Double client_minDist=Double.MAX_VALUE;
            Double client_dest_minDist=Double.MAX_VALUE;
            Tuple<Double,Double> client_pos=Tuple.valueOf(5.0,7.0);     
            Tuple<Double,Double> client_dest_pos=Tuple.valueOf(5.0,7.0);

            int size=listOfTaxis.size();
            Double[] taxis_minDist=new Double[size];
            Double[] taxis_long_pos=new Double[size];
            Double[] taxis_lat_pos=new Double[size];
            int index_taxis=0;


            for( Tuple<Double,Double> key: hmap.keySet()) {

                if(counter==1) {

                    Tuple<Double,Double> client_coordinates=client.getcoordinates();
                    client_minDist=euclidianDist(client_coordinates.getA(),client_coordinates.getB(),key.getA(),key.getB());
                    client_pos=Tuple.valueOf(key.getA(),key.getB());

                    Tuple<Double,Double> client_dest_coordinates=client.getdest_coordinates();
                    client_dest_minDist=euclidianDist(client_dest_coordinates.getA(),client_dest_coordinates.getB(),key.getA(),key.getB());
                    client_dest_pos=Tuple.valueOf(key.getA(),key.getB());

                    Iterator<Taxis> itr = listOfTaxis.iterator();

                    while(itr.hasNext()) {
                        Taxis taxi = itr.next();
                        Tuple<Double,Double> taxi_coordinates=taxi.getcoordinates();
                        taxis_minDist[index_taxis]=euclidianDist(taxi_coordinates.getA(),taxi_coordinates.getB(),key.getA(),key.getB());
                        taxis_long_pos[index_taxis]=key.getA();
                        taxis_lat_pos[index_taxis]=key.getB();
                        index_taxis+=1;
                    }
                }
                else {

                    Tuple<Double,Double> client_coordinates=client.getcoordinates();
                    Double dist=euclidianDist(client_coordinates.getA(),client_coordinates.getB(),key.getA(),key.getB());
                    if(dist<client_minDist) {
                        client_minDist=dist;
                        client_pos=Tuple.valueOf(key.getA(),key.getB());

                    }

                    Tuple<Double,Double> client_dest_coordinates=client.getdest_coordinates();
                    dist=euclidianDist(client_dest_coordinates.getA(),client_dest_coordinates.getB(),key.getA(),key.getB());

                    if(dist<client_dest_minDist) {
                        client_dest_minDist=dist;
                        client_dest_pos=Tuple.valueOf(key.getA(),key.getB());

                    }

                    Iterator<Taxis> itr = listOfTaxis.iterator();
                    while(itr.hasNext()) {
                        Taxis taxi = itr.next();
                        Tuple<Double,Double> taxi_coordinates=taxi.getcoordinates();
                        dist=euclidianDist(taxi_coordinates.getA(),taxi_coordinates.getB(),key.getA(),key.getB());
                        if(dist<taxis_minDist[index_taxis]){
                            taxis_minDist[index_taxis]=dist;
                            taxis_long_pos[index_taxis]=key.getA();
                            taxis_lat_pos[index_taxis]=key.getB();
                        }
                        index_taxis+=1;
                    }
                }
                index_taxis=0;
                counter+=1;
            }

            client.coordinates=client_pos;
            client.dest_coordinates=client_dest_pos;

            int index=0;
            Iterator<Taxis> itr = listOfTaxis.iterator();
            while(itr.hasNext()) {
                Taxis taxi = itr.next();
                taxi.coordinates=Tuple.valueOf(taxis_long_pos[index],taxis_lat_pos[index]);
                index+=1;
            }

            for(Tuple<Double,Double> key: hmap.keySet()) {

                Tuple<Double,ArrayList<Neighbor> > valueOfKey=hmap.get(key);
                Double heurValue=euclidianDist(client.coordinates.getA(),client.coordinates.getB(),key.getA(),key.getB());
                hmap.put(key,Tuple.valueOf(heurValue,valueOfKey.getB()));

            }


        }
        catch(Exception ex) {
            System.out.println(ex);
            System.out.println("Error Reading File:clientFile or taxisFile not found");
        }


    }

    public void nodes_facts(String nodesFile,PrintStream nodes,PrintStream belongsTo) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(nodesFile));
            String line=null;
            String line_next=null;

            line=reader.readLine();

            line=reader.readLine();
            line_next=reader.readLine();

            String[] line_split=line.split(",");
            String[] line_next_split=line_next.split(",");

            JIPEngine jip = new JIPEngine();
            jip.consultFile("maplines.pl");

            JIPTermParser parser = jip.getTermParser();


            JIPQuery jipQuery;
            JIPTerm term;
            System.setOut(belongsTo);

            System.out.println("belongsTo("+line_split[3]+","+line_split[2]+").");
            Tuple<Double,Double> key;
            key=Tuple.valueOf(Double.parseDouble(line_split[0]),Double.parseDouble(line_split[1]));
            hmapn.put(key,Long.parseLong(line_split[3]));
            hmapnInverse.put(Long.parseLong(line_split[3]),key);

            while(line_next!=null) {

                line_next_split=line_next.split(",");
                key=Tuple.valueOf(Double.parseDouble(line_next_split[0]),Double.parseDouble(line_next_split[1]));
                hmapn.put(key,Long.parseLong(line_next_split[3]));
                hmapnInverse.put(Long.parseLong(line_next_split[3]),key);
                System.setOut(belongsTo);

                System.out.println("belongsTo("+line_split[3]+","+line_split[2]+").");

                System.setOut(nodes);

                if(line_split[2].equals(line_next_split[2])) {

                    jipQuery = jip.openSynchronousQuery(parser.parseTerm("oneway("+line_split[2]+")."));
                    if (jipQuery.nextSolution() != null) {
                        System.out.println("nextnode("+line_split[3]+","+line_next_split[3] +").");
                    }

                    jipQuery = jip.openSynchronousQuery(parser.parseTerm("inverseway("+line_split[2]+")."));
                    if (jipQuery.nextSolution() != null) {
                        System.out.println("nextnode("+line_next_split[3]+","+line_split[3] +").");
                    }

                    jipQuery = jip.openSynchronousQuery(parser.parseTerm("twoway("+line_split[2]+")."));
                    if (jipQuery.nextSolution() != null) {
                        System.out.println("nextnode("+line_split[3]+","+line_next_split[3] +").");
                        System.out.println("nextnode("+line_next_split[3]+","+line_split[3] +").");
                    }

                }

                line=line_next;
                line_split=line_next_split;
                line_next=reader.readLine();
            }

        }

        catch(Exception ex){
            System.out.println("Error with nodesFile");
            System.out.println(ex);
        }
    }

    public void taxi_facts(String taxisFile) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(taxisFile));
            String line=null;

            line=reader.readLine();
            line=reader.readLine();

            String[] line_split=line.split(",");

            Iterator<Taxis> itr = listOfTaxis.iterator();

            while(itr.hasNext()) {
                line_split=line.split(",");
                Taxis taxi = itr.next();

                String[] peopleInTaxi=line_split[4].split("-");
                System.out.println("peopleInTaxi("+line_split[2]+","+peopleInTaxi[0]+","+peopleInTaxi[1]+").");

                String[] languages=line_split[5].split("|");
                int len=languages.length;
                System.out.print("speakslan("+line_split[2] +",");

                for(int i=0;i<len;i++) {

                    if(languages[i].equals("|")) {
                        System.out.println( ")."  );
                        System.out.print("speakslan("+line_split[2] +",");
                        continue;
                    }

                    System.out.print(languages[i]);
                }

                System.out.println( ")."  );

                System.out.println("taxi("+taxi.coordinates.getA().toString()+","+taxi.coordinates.getB().toString()+","+line_split[2]+","+line_split[3]+","+line_split[6]+","+line_split[7]+ ")." );
                line=reader.readLine();
            }

            System.out.println("availableTaxi(X):-  taxi(_,_,X,yes,_,yes) ,  capacity(X), samelang(X).");
            System.out.println("availableTaxi(X):-  taxi(TaxiX,TaxiY,X,yes,_,no) , capacity(X), samelang(X), client(CX,CY,DX,DY,_,D,_,_),distance(TaxiX,TaxiY,CX,CY,Res1)  , 40>=Res1 ,   distance(TaxiX,TaxiY,DX,DY,Res2) , 40>=Res2 .");


            System.out.println("distance(Startx,Starty,Finx,Finy,Res):-    Theta is Startx-Finx ,");
            System.out.println("Ar1 is sin(Starty*pi/180), ");
            System.out.println("Ar2 is sin(Finy*pi/180),Ar3 is cos(Starty*pi/180),Ar4 is cos(Finy*pi/180),");
            System.out.println(" Ar5 is cos(Theta*pi/180),Dist is Ar1*Ar2+Ar3*Ar4*Ar5, Dist1 is acos(Dist) , Dist2 is Dist1*180/pi,Res is Dist2*60*1.1515*1.609344 .     ");

            System.out.println("capacity(X):-  client(_,_,_,_,_,D,_,_)  ,   peopleInTaxi(X,A,B)   ,   B>=D , D>=A .");
            System.out.println("samelang(X):-  client(_,_,_,_,_,_,Lan,_)    ,   speakslan(X,Lan).");

        }
        catch(Exception ex){
            System.out.println("Error with taxisFile");
            System.out.println(ex);
        }
    }


    public void client_fact(String clientFile) {

        try {

            BufferedReader reader = new BufferedReader(new FileReader(clientFile));
            String line=null;

            line=reader.readLine();

            line=reader.readLine();

            String[] line_split=line.split(",");

            System.out.println("client("+ client.coordinates.getA().toString() +","+client.coordinates.getB().toString()+","+
            client.dest_coordinates.getA().toString() +","+client.dest_coordinates.getB().toString()+","+
            line_split[4]+","+line_split[5]+","+line_split[6]+","+line_split[7]+").");

        }

        catch(Exception ex) {
            System.out.println("Error with taxisFile");
            System.out.println(ex);
        }
    }

    public void traffic_facts(String trafficFile){
        try{
            BufferedReader reader = new BufferedReader(new FileReader(trafficFile));
            String line=null;

            line=reader.readLine();

            line=reader.readLine();

            String[] line_split=line.split("");
	    int liners = 0;
	    int flag2 = 0;
            while(line!=null) {
		//if(flag2==1) break;
		liners++;
		//System.out.println("1");
		//if(liners==1) flag2=1;
                line_split=line.split("");
		//if(flag2==1) System.out.println(line);
                String line_split_id=(line.split(","))[0];
                int length=line_split.length;
                int counter=0;
                int commas=0;
                int yes=0;
                int print=0;
                int flag=0;
                counter=1;

                while(counter<length) {
                    if(line_split[counter].equals(",")) {
                        commas++;
                    }
                    if(!line_split[counter].equals(",")&&(commas==2)){
                        flag=1;
                        if((print==1)  && !line_split[counter].equals("|")) {
                            System.out.print(line_split[counter]);
                        }
                        if(yes==0) {

			    //liners++;
			    //if(liners==736)  { 
			    //System.out.println(line);
			    //flag2=1;
			    //break;
			    //}

			    if(line_split_id.equals("169658542")) {
				 flag = 0;
				 break;
		            }
                            System.out.print("traffic("+line_split_id+",");
                            yes=1;
                        }
                        if(line_split[counter].equals("|")) {
                            System.out.println(").");
                            yes=0;
                            print=0;
                        }
                        if(line_split[counter].equals(":")) {
                            System.out.print( line_split[counter-2]+line_split[counter-1]+","  );
                        }
                        if(line_split[counter].equals("=")) {
                            print=1;
                        }
                    }
                    counter++;
                }

                if(flag==1) {
                    System.out.println(").");
                }
                line=reader.readLine();
            }
        }
        catch(Exception ex) {
            System.out.println("Error with taxisFile");
            System.out.println(ex);
        }
    }

    public void fix_heur(){
        try{
            //System.out.println("mpika");
            for( Tuple<Double,Double> key: hmap.keySet()) {

                Tuple<Double,ArrayList<Neighbor>> valofKey = hmap.get(key);
                Double gDist=valofKey.getA();
                Long node_id=hmapn.get(key);


                JIPEngine jip = new JIPEngine();
                jip.consultFile("found.pl");
                JIPEngine jip2 = new JIPEngine();
                jip2.consultFile("maplines.pl");

                JIPTermParser parser = jip.getTermParser();
                JIPTermParser parser2 = jip2.getTermParser();

                JIPQuery jipQuery;
                JIPTerm term;
                JIPQuery jipQuery2;
                JIPTerm term2;

                jipQuery = jip.openSynchronousQuery(parser.parseTerm("belongsTo("+node_id+","+"Line_id)."));
                term = jipQuery.nextSolution();

                if (term != null) {
                    System.out.println("line and node id found");
                    jipQuery2 = jip2.openSynchronousQuery(parser2.parseTerm("line("+term.getVariablesTable().get("Line_id").toString()+","+"Highway,_,Lit,Lanes,_,_,_,_,_,_,_,_,_,_,Toll)."));
                    term2 = jipQuery2.nextSolution();
                    if(term2!=null) {
                        System.out.println(  term2.getVariablesTable().get("Toll").toString());
                    }
                }

                else {
                    System.out.println("not found");
                }

            }
        }
        catch(Exception ex){
            System.out.println("Error with Heuristic update function!");
            System.out.println(ex);
        }
    }



    // let's calculate the euclidean distance
    // the above function has been copied from the link below :
    // https://www.geodatasource.com/developers/java

    public Double euclidianDist(Double lon1,Double lat1,Double lon2,Double lat2) {

		Double dst = 0.0;

		if ( (lon1-lon2 == 0 ) && ( lat1-lat2 == 0) ) return(dst);

		else {

			Double theta = lon1 - lon2;
			Double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
			dist = Math.acos(dist);
			dist = Math.toDegrees(dist);
			dist = dist * 60 * 1.1515;
			dist = dist * 1.609344;
        		return(dist);
		}
/*
			Double lon = Math.toRadians(lon1-lon2);
			Double lat = Math.toRadians(lat1-lat2);
			Double dst = Math.sin(lat/2)*Math.sin(lat/2) + Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(lon/2)*Math.sin(lon/2);
			Double c = 2*Math.atan2(Math.sqrt(dst),Math.sqrt(1-dst));
			this.distance = c*6371 ;
*/
    	}

}
