import java.lang.*;

public class Neighbor {


	Tuple<Double,Double> coordinates;
	Double distance;
   	int roadId;

    	// let's calculate the euclidean distance
    	// the above function has been copied from the link below :
    	// https://www.geodatasource.com/developers/java

    	public void setdistance(Double lon1,Double lat1,Double lon2,Double lat2) {

			if ( (lon1-lon2 == 0 ) && ( lat1-lat2 == 0) ) this.distance = 0.0 ;

			else {

					Double theta = lon1 - lon2;
					Double dist = Math.sin(Math.toRadians(lat1)) * Math.sin(Math.toRadians(lat2)) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.cos(Math.toRadians(theta));
					dist = Math.acos(dist);
					dist = Math.toDegrees(dist);
					dist = dist * 60 * 1.1515;
					dist = dist * 1.609344;
        				this.distance=dist;
			}
/*
			Double lon = Math.toRadians(lon1-lon2);
			Double lat = Math.toRadians(lat1-lat2);
			Double dst = Math.sin(lat/2)*Math.sin(lat/2) + Math.cos(Math.toRadians(lat1))*Math.cos(Math.toRadians(lat2))*Math.sin(lon/2)*Math.sin(lon/2);
			Double c = 2*Math.atan2(Math.sqrt(dst),Math.sqrt(1-dst));
			this.distance = c*6371 ;
*/
    	}

    	public void setroadid(int id) {

        	this.roadId=id;

    	}

    	public void setcoordinates(Double x , Double y ) {

        	this.coordinates=Tuple.valueOf(x,y);

    	}

    	public Tuple<Double,Double> getcoordinates() {

        	return coordinates;

    	}

    	public Double getdistance() {

        	return distance;

    	}

    	public int getroadid() {

        	return roadId;

    	}

  
}
