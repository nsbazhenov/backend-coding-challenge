package com.github.nsbazhenov.challenge.calculator.strategy;

import com.github.nsbazhenov.challenge.model.Point;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class HaversineDistanceStrategyTest {

    private static final Point START_POINT = new Point(43.70011, -79.4163);
    private static final Point END_POINT = new Point(43.70011, -79.4163);
    private static final Point MIDDLE_POINT = new Point(41.70011, -73.4163);

    @InjectMocks
    private HaversineDistanceStrategy haversineDistanceStrategy;

    @Test
    public void whenGetDistance_givenIdenticalPoints_thenShouldReturnExpectedDistance() {
        double actualDistance = haversineDistanceStrategy.distance(START_POINT, END_POINT);
        Assert.assertEquals(0.0, actualDistance, 0);
    }

    @Test
    public void whenGetDistance_givenDifferentPoints_thenShouldReturnExpectedDistance() {
        double actualDistance = haversineDistanceStrategy.distance(START_POINT, MIDDLE_POINT);
        Assert.assertEquals(538.2146722186468, actualDistance, 0);
    }

    @Test(expected = NullPointerException.class)
    public void whenGetDistance_givenNulls_thenShouldThrowException() {
        haversineDistanceStrategy.distance(null, null);
    }
}
