package com.github.nsbazhenov.challenge.calculator;

import com.github.nsbazhenov.challenge.model.City;
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
public class SuggestionScoreCalculatorTest {

    private static final String CORRECT_SEARCH_QUERY = "London";
    private static final double CORRECT_LATITUDE  = 41.70011;
    private static final double CORRECT_LONGITUDE = -73.4163;
    private static final String INCORRECT_SEARCH_QUERY = "Russia";
    private static final double INCORRECT_LATITUDE = 21.213123 ;
    private static final double INCORRECT_LONGITUDE = -43.1123;

    @InjectMocks
    SuggestionScoreCalculator suggestionScoreCalculator;

    @Mock
    NameScoreCalculator nameScoreCalculator;

    @Mock
    DistanceScoreCalculator distanceScoreCalculator;

    private City city ;

    @Before
    public void setup() {
        city = new City("London", "CA", null, new Point(CORRECT_LATITUDE, CORRECT_LONGITUDE),
                "adminCode", "London, CA", 0.0);
    }

    @Test
    public void whenGetSuggestionScore_givenIdenticalCity_thenShouldReturnExpectedScore() {
        when(nameScoreCalculator.calculate(CORRECT_SEARCH_QUERY, city.name())).thenReturn(1.0);
        when(distanceScoreCalculator.calculate(city.point(), new Point(CORRECT_LATITUDE, CORRECT_LONGITUDE))).thenReturn(1.0);
        double actualDistance = suggestionScoreCalculator.calculate(city, CORRECT_SEARCH_QUERY,
                CORRECT_LATITUDE, CORRECT_LONGITUDE);
        Assert.assertEquals(1.0, actualDistance, 0);
    }

    @Test
    public void whenGetSuggestionScore_givenDifferentCityName_thenShouldReturnExpectedScore() {
        when(nameScoreCalculator.calculate(INCORRECT_SEARCH_QUERY, city.name())).thenReturn(0.0);
        double actualDistance = suggestionScoreCalculator.calculate(city, INCORRECT_SEARCH_QUERY,
                INCORRECT_LATITUDE, INCORRECT_LONGITUDE);
        Assert.assertEquals(0.0, actualDistance, 0);
    }

    @Test(expected = NullPointerException.class)
    public void whenGetSuggestionScore_givenNulls_thenShouldReturnExpectedScore() {
        suggestionScoreCalculator.calculate(null, null, 0.0, 0.0);
    }
}
