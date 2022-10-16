package com.github.nsbazhenov.challenge.calculator.strategy;

import org.junit.Assert;
import org.junit.Test;
public class DistanceStrategyFactoryTest {

    private final DistanceStrategyFactory distanceStrategyFactory = new DistanceStrategyFactory();

    @Test
    public void whenGetStrategy_givenValidStrategy_thenShouldReturnExpectedStrategy() {
        DistanceStrategy actual = distanceStrategyFactory.getStrategy(DistanceStrategyFactory.HAVERSINE);
        Assert.assertNotNull(actual);
        Assert.assertEquals(actual.getClass(), HaversineDistanceStrategy.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenGetStrategy_givenInvalidStrategy_thenShouldThrowException() {
        distanceStrategyFactory.getStrategy("Invalid strategy");
    }
}
