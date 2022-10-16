package com.github.nsbazhenov.challenge.calculator;

import org.junit.Assert;
import org.junit.Test;

public class NameScoreCalculatorTest {

    private static final String FULL_SEARCH_QUERY = "London";

    private static final String DISSIMILAR_SEARCH_QUERY = "Russia";
    private static final String PARTIAL_SEARCH_QUERY = "Londo";
    private static final String FULL_CITY_NAME = "London";

    private final NameScoreCalculator nameScoreCalculator = new NameScoreCalculator();

    @Test
    public void whenGetNameScore_givenIdenticalNames_thenShouldReturnExpectedScore() {
        double actualDistance = nameScoreCalculator.calculate(FULL_SEARCH_QUERY, FULL_CITY_NAME);
        Assert.assertEquals(1.0, actualDistance, 0);
    }

    @Test
    public void whenGetNameScore_givenSimilarNames_thenShouldReturnExpectedScore() {
        double actualDistance = nameScoreCalculator.calculate(PARTIAL_SEARCH_QUERY, FULL_CITY_NAME);
        Assert.assertEquals(0.83, actualDistance, 0);
    }

    @Test
    public void whenGetNameScore_givenDissimilarNames_thenShouldReturnExpectedScore() {
        double actualDistance = nameScoreCalculator.calculate(DISSIMILAR_SEARCH_QUERY, FULL_CITY_NAME);
        Assert.assertEquals(0.0, actualDistance, 0);
    }

    @Test
    public void whenGetNameScore_givenNulls_thenShouldReturnExpectedScore() {
        double actualDistance = nameScoreCalculator.calculate(null, null);
        Assert.assertEquals(1.0, actualDistance, 0);
    }
}
