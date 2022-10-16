package com.github.nsbazhenov.challenge.calculator;

import com.github.nsbazhenov.challenge.model.City;
import com.github.nsbazhenov.challenge.model.Point;
import com.github.nsbazhenov.challenge.service.SuggestionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.apache.commons.math3.util.Precision.round;

/**
 *
 * Class for calculating total comparison points.
 *
 * @author Bazhenov Nikita
 *
 */
@Component
public class SuggestionScoreCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionScoreCalculator.class);

    private final NameScoreCalculator nameScoreCalculator;
    private final DistanceScoreCalculator distanceScoreCalculator;

    public SuggestionScoreCalculator(NameScoreCalculator nameScoreCalculator, DistanceScoreCalculator distanceScoreCalculator) {
        this.nameScoreCalculator = nameScoreCalculator;
        this.distanceScoreCalculator = distanceScoreCalculator;
    }

    /**
     * The method counts comparison points.
     *
     * @return number of total points.
     */
    public double calculate(City city, String searchQuery, double searchLatitude, double searchLongitude) {
        LOGGER.debug("Obtained object for calculating comparison points, object - {}, searchQuery - {}, " +
                "latitude - {}, longitude - {}", city, searchQuery, searchLatitude, searchLongitude);
        double totalScore = nameScoreCalculator.calculate(searchQuery, city.name());

        if(totalScore > 0.0) {
            totalScore = totalScoreWithDistance(new Point(searchLatitude, searchLongitude), city.point(), totalScore);
        }
        double round = round(totalScore, 2);
        LOGGER.debug("Number of comparative total points - {}", round);
        return round;
    }

    private double totalScoreWithDistance(Point searchPoint, Point point, double score) {
        double distanceScore = distanceScoreCalculator.calculate(searchPoint, point);
        return (score + distanceScore) / 2;
    }
}
