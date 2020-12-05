import java.util.Comparator;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeSet;

public class Astar {

    	public Pair<Double,ArrayList<ArrayList<Pair<Double,Double>>>> findpath(HashMap<Pair<Double,Double>,Pair<Double,ArrayList<NeighborNode>>> hmap , Client client , Taxis taxi ) {

        	// Declarations

		Quatretcompare comp = new Quatretcompare();

		// we will use a priority queue for the main excecution of A* ( !~ metopo anazitisis ~! )
		TreeSet<Quatret> pq = new TreeSet<Quatret>(comp);

		// we want to keep track of the nodes that have been already visited so that we avoid visiting them in the future
        	HashSet<Pair<Double,Double>> visited = new HashSet<Pair<Double,Double>>();

		// we store the minimum distance that has been achieven till know in order to reach a node
        	HashMap <Pair<Double,Double>, Double> besttillnow = new HashMap <Pair<Double,Double>,Double>();

		// path is an array in which we store the path from start to current node
		ArrayList<Pair<Double,Double>> path = new ArrayList<Pair<Double,Double>>();

		// total paths is an array in which we keep track of ALL the minimum paths
		ArrayList<ArrayList<Pair<Double,Double>>> totalpaths = new ArrayList<ArrayList<Pair<Double,Double>>>();

		// discardertotal paths is an array in which we keep track of discarded minimum subpaths
		ArrayList<ArrayList<Pair<Double,Double>>> discardedpaths = new ArrayList<ArrayList<Pair<Double,Double>>>();
        	
		// at first we declare final distance as -1.0
		// if distance's value is -1.0 after function returns then path was not found

        	Double distance = -1.0;
		
		// we want to keep track of the number of minimum paths

		int counter = 0;

		// if flag is 0 goal was not found , if flag is 1 goal was found

		int flag = 0 ;
		
		// mindst has to be infinite at first so that in first goal found minimum distance is updated
        	Double mindst = Double.MAX_VALUE ;
		
		// our return value is a pair of the minimum distance found and an array of ALL the minimum paths found

		Pair<Double,ArrayList<ArrayList<Pair<Double,Double>>>> ret ;

		// exists will be used to check if current node is in priority queue

		Double exists,exists2 ;

		Quatret check ;

		Double disthere ;
	
		Double dst ;
		Double ceiling = 1.001;
		//Double ceiling = 1.0;
	
		ArrayList<Pair<Double,Double>> prev = new ArrayList<Pair<Double,Double>>();
		prev.add(taxi.coordinates);

		Pair<Double,ArrayList<NeighborNode>> heuristic = hmap.get(taxi.coordinates);
		Double heurtotal = heuristic.getA();    

		Double startx = taxi.coordinates.getA();
		Double starty = taxi.coordinates.getB();    	

		// our start is of course the selected taxi's location

        	Quatret start = Quatret.valueOf(startx,starty,heurtotal,prev);

        	// add start in the priority queue

        	pq.add(start);

        	besttillnow.put(taxi.coordinates,heurtotal);

        	while( !pq.isEmpty() ) {


			// extract node with minimum heuristic from priority queue

            		Quatret current=pq.pollFirst();

            		Pair<Double,Double> coordinates = Pair.valueOf(current.getFirst(),current.getSecond());

            		// if current node is client then

            		if( (client.coordinates.getA().equals(current.getFirst())) &&(client.coordinates.getB().equals(current.getSecond())) ) { 


				heuristic = hmap.get(coordinates);
                		heurtotal = heuristic.getA();
                		distance = current.getThird()-heurtotal;

				// if distance is lower than current min distance update

				if( distance < mindst ) {
	
					counter = 1 ;
					totalpaths.clear();
					path = current.getForth();
					totalpaths.add(path);
					mindst = distance ;
					flag = 1; 

				}

				// else number of minimum paths ++ and store path in total paths

				else if ( distance.equals(mindst) ) {
	
					counter+=1;
					path = current.getForth();
					totalpaths.add(path);

				}

            		}

			else {

                    		heuristic = hmap.get(coordinates);
                    		heurtotal = heuristic.getA();

				exists2 = besttillnow.get(coordinates);

				// if current node has been already visited ignore

                		if(visited.contains(coordinates)) {

					// before we continue to next node 
					// if current's node distance is in range of best distance*ceiling thenk keep path in discarted paths

					if(exists2*ceiling >= current.getThird() ) {

                        			ArrayList<Pair<Double,Double>> temp = new ArrayList<Pair<Double,Double>>();
                        			temp = current.getForth();

						if( !temp.get(temp.size()-1).equals(temp.get(temp.size()-3)) ) {
							if( temp.get(0).getA().equals(startx) && temp.get(0).getB().equals(starty) ) {
								discardedpaths.add(temp);
							}
						}
							
					}

				 	continue ;
				}

                    		visited.add(coordinates);
				
				// if goal is found and distance here is greater than minimum goal distance*ceiling then break

				if( flag==1 && ( mindst*ceiling < heurtotal ) ) break ;

                    		ArrayList<NeighborNode> array = heuristic.getB();
                    		Iterator<NeighborNode> itr = array.iterator();

                    		while( itr.hasNext() ) {

                        		NeighborNode neighbor = itr.next();

					// distance till this node

                        		disthere = current.getThird()-heurtotal;

                        		Pair<Double,ArrayList<NeighborNode>> hashvalue = hmap.get(neighbor.coordinates);

					// total distance is distance before + distance to neighbor + heuristic

                        		dst = hashvalue.getA() + neighbor.distance + disthere ;

					// temp is path till here

                        		ArrayList<Pair<Double,Double>> temp = new ArrayList<Pair<Double,Double>>();
                        		temp = current.getForth();

					// now add current node to path 

                        		ArrayList<Pair<Double,Double>> preva = new ArrayList<Pair<Double,Double>>(temp);
                        		preva.add(neighbor.coordinates);

                        		check = Quatret.valueOf(neighbor.coordinates.getA(),neighbor.coordinates.getB(),dst,preva);

                        		exists = besttillnow.get(neighbor.coordinates);

					// we accept minimum distances that are ceiling % close to the minimum distance

					if( exists!=null ) {

						if ( ( exists*ceiling >= check.getThird() ) && ( exists < check.getThird()) ) check.setThird(exists);
					}
					
					// if current node does not exist in priority queue
					// or 
					// if current node exists in priority queue but it's new distance is lower or equal
					// we add equal to in order to track ALL the minimum paths

                        		if( ( exists == null ) || ( exists > check.getThird() ) || ( check.getThird().equals(exists) ) ) {

                                		pq.add(check);
                                		besttillnow.put(neighbor.coordinates,check.getThird());

                        		}

                    		}

			}
            	
        	}

	// if client was found console log some statistics !!

	if ( flag == 1) {
	
		// if discarted paths are minimum paths from taxi to target add them to total paths
		// in order to do so we check if each discarted paths last node exists in minimum path found above
		// if so then we take the discarted path plus the minimum from node of interest to client node and add it to totalpaths

		for(int i=0 ; i<discardedpaths.size() ; i++) {

			if(totalpaths.get(0).contains(discardedpaths.get(i).get(discardedpaths.get(i).size()-1))) {

				for(int j=1; j<totalpaths.get(0).size(); j++) {
	
					if(totalpaths.get(0).get(j).equals(discardedpaths.get(i).get(discardedpaths.get(i).size()-1))) {

						ArrayList<Pair<Double,Double>> temp = new ArrayList<Pair<Double,Double>>();
						temp = discardedpaths.get(i);
						for(int k=j+1; k<totalpaths.get(0).size() ; k++) {
							temp.add(totalpaths.get(0).get(k));
						}	
												
						totalpaths.add(temp);
						counter +=1;
						break;

					}
				}
			}
						
		}

		System.out.println("");
		System.out.println("Taxi's id : " + taxi.getid());
                System.out.println("Client was found !");
                System.out.println("Number of min paths found : " + counter);
                System.out.println("Distance to goal in kilometers : "+ mindst);
            	System.out.println("");
		System.out.println("-------------------------------------------------------");

				
	}
        
        ret = Pair.valueOf(distance,totalpaths);

        return ret;
    }
}
