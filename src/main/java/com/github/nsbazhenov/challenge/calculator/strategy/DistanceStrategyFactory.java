package com.github.nsbazhenov.challenge.calculator.strategy;

import org.springframework.stereotype.Component;

/**
 *
 * Class for selecting a strategy for calculating the distance between cities.
 *
 * @author Bazhenov Nikita
 *
 */
@Component
public class DistanceStrategyFactory {

    public static final String HAVERSINE = "HAV";

    public DistanceStrategy getStrategy(String strategyType) {
        if(HAVERSINE.equals(strategyType)) {
            return new HaversineDistanceStrategy();
        }
        throw new IllegalArgumentException("Invalid type of strategy " + strategyType);
    }
}