package com.github.nsbazhenov.challenge.calculator.strategy;

import com.github.nsbazhenov.challenge.model.Point;

import static org.apache.commons.math3.util.FastMath.*;

/**
 *
 * Class for calculating haversine distance.
 * Class for calculating the distance between cities.
 *
 * @author Bazhenov Nikita
 *
 */
public class HaversineDistanceStrategy implements DistanceStrategy {

    private static final double EARTH_RADIUS_IN_KM = 6371.0;

    @Override
    public double distance(Point start, Point end) {
        return compute(start, end);
    }

    /**
     * The method calculating Haversine formula.
     * <a href="http://www.movable-type.co.uk/scripts/latlong.html">...</a>
     *
     * @return number of distance points.
     */
    private double compute(Point start, Point end) {
        double startLatToRad = toRadians(start.latitude());
        double startLongToRad = toRadians(start.longitude());
        double endLatToRad = toRadians(end.latitude());
        double endLongToRad = toRadians(end.longitude());

        double a = haversine(startLatToRad, endLatToRad)
                + cos(startLatToRad)
                * cos(endLatToRad)
                * haversine(startLongToRad, endLongToRad);
        double c = 2 * atan2(sqrt(a), sqrt(1 - a));

        return EARTH_RADIUS_IN_KM * c;
    }

    private double haversine(double start, double end) {
        return pow(sin((end - start) / 2), 2);
    }
}
