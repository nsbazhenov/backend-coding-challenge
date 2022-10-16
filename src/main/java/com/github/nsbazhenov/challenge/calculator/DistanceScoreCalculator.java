package com.github.nsbazhenov.challenge.calculator;

import com.github.nsbazhenov.challenge.calculator.strategy.DistanceStrategy;
import com.github.nsbazhenov.challenge.calculator.strategy.DistanceStrategyFactory;
import com.github.nsbazhenov.challenge.model.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.math3.util.Precision.round;

/**
 *
 * Class for calculating distance comparison points.
 *
 * @author Bazhenov Nikita
 *
 */
@Component
public class DistanceScoreCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(DistanceScoreCalculator.class);

    private static final double DISTANCE_THRESHOLD = 200.0;

    private final DistanceStrategyFactory distanceStrategyFactory;

    @Autowired
    public DistanceScoreCalculator(DistanceStrategyFactory distanceStrategyFactory) {
        this.distanceStrategyFactory = distanceStrategyFactory;
    }

    /**
     * The method counts distance comparison points.
     *
     * @return number of distance points.
     */
    public double calculate(Point start, Point end) {
        LOGGER.debug("Calculating distance points, startPoint - {}, endPoint - {}", start, end);
        DistanceStrategy distanceStrategy = distanceStrategyFactory.getStrategy(DistanceStrategyFactory.HAVERSINE);
        double distance = distanceStrategy.distance(start, end);
        double rescale = rescale(distance);
        LOGGER.debug("Calculating distance points, startPoint - {}, endPoint - {}, score - {}", start, end, rescale);
        return rescale;
    }

    /**
     * Method for scaling distance points.
     * <a href="https://en.wikipedia.org/wiki/Feature_scaling#Rescaling_(min-max_normalization)">...</a>
     *
     * @return number of scaling distance points.
     */
    private double rescale(double distance) {
        if(distance <= DISTANCE_THRESHOLD) {
            return round(1 - (distance / DISTANCE_THRESHOLD), 2);
        }
        return 0.0;
    }
}
