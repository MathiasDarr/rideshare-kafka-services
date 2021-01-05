package org.mddarr.producer;

import org.mddarr.producer.models.Location;
import org.mddarr.producer.models.RideCoordinate;
import org.mddarr.rides.event.dto.AvroRideCoordinate;

import java.util.ArrayList;
import java.util.List;

public class CoordinatesProducer {

    public static double[] linspace(double min, double max, int points) {
        double[] d = new double[points];
        for (int i = 0; i < points; i++){
            d[i] = min + i * (max - min) / (points - 1);
        }
        return d;
    }

    public static List<AvroRideCoordinate>  generateCoordinateArray(String rideid, Location location, Location destination){

        int npoints = 120;

        double[] latitudes = linspace(location.getLat(), location.getLng(), npoints);
        double[] longitudes = linspace(location.getLng(), destination.getLng(), npoints);
        List<AvroRideCoordinate> rideCoordinates = new ArrayList<>();

        for(int i =0; i< latitudes.length; i++){
            rideCoordinates.add(new AvroRideCoordinate((long) i, latitudes[i], longitudes[i]));
        }
        return rideCoordinates;
    }


}
