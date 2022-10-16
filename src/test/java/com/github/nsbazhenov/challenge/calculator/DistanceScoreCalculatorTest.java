package com.github.nsbazhenov.challenge.calculator;

import com.github.nsbazhenov.challenge.calculator.strategy.DistanceStrategy;
import com.github.nsbazhenov.challenge.calculator.strategy.DistanceStrategyFactory;
import com.github.nsbazhenov.challenge.calculator.strategy.HaversineDistanceStrategy;
import com.github.nsbazhenov.challenge.model.Point;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DistanceScoreCalculatorTest {

    private static final Point START_POINT = new Point(43.70011, -79.4163);
    private static final Point END_POINT = new Point(43.70011, -79.4163);
    private static final Point MIDDLE_POINT = new Point(41.70011, -73.4163);

    @InjectMocks
    private DistanceScoreCalculator distanceScoreCalculator;

    @Mock
    private DistanceStrategyFactory distanceStrategyFactory;

    private DistanceStrategy distanceStrategy;

    @Before
    public void setup() {
        distanceStrategy = new HaversineDistanceStrategy();
    }

    @Test
    public void whenGetDistanceScore_givenIdenticalPoints_thenShouldReturnExpectedScore() {
        when(distanceStrategyFactory.getStrategy(DistanceStrategyFactory.HAVERSINE)).thenReturn(distanceStrategy);
        double actualDistance = distanceScoreCalculator.calculate(START_POINT, END_POINT);
        Assert.assertEquals(1.0, actualDistance, 0);
    }

    @Test
    public void whenGetDistanceScore_givenDifferentPoints_thenShouldReturnExpectedScore() {
        when(distanceStrategyFactory.getStrategy(DistanceStrategyFactory.HAVERSINE)).thenReturn(distanceStrategy);
        double actualDistance = distanceScoreCalculator.calculate(START_POINT, MIDDLE_POINT);
        Assert.assertEquals(0.0, actualDistance, 0);
    }

    @Test(expected = NullPointerException.class)
    public void whenGetDistanceScore_givenNulls_thenShouldThrowException() {
        distanceScoreCalculator.calculate(null, null);
    }
}
