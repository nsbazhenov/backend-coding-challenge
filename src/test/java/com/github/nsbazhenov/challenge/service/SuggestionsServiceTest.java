package com.github.nsbazhenov.challenge.service;

import com.github.nsbazhenov.challenge.calculator.SuggestionScoreCalculator;
import com.github.nsbazhenov.challenge.model.Suggestions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SuggestionsServiceTest {

    private static final String SEARCH_QUERY = "London";
    private static final double LATITUDE = 43.70011;
    private static final double LONGITUDE = -79.4163;

    @InjectMocks
    private SuggestionsService suggestionsService;

    @Mock
    private SuggestionScoreCalculator suggestionScoreCalculator;

    @Test
    public void whenGetSuggestions_givenIdenticalParams_thenShouldReturnAllSuggestions() {
        when(suggestionScoreCalculator.calculate(any(), anyString(), anyDouble(), anyDouble())).thenReturn(1.0);

        Suggestions actualSuggestions = suggestionsService.findSuggestions(SEARCH_QUERY, LATITUDE, LONGITUDE);

        Assert.assertEquals(7237, actualSuggestions.suggestions().size());
    }

    @Test
    public void whenGetSuggestions_givenDifferentParams_thenShouldReturnZeroSuggestions() {
        when(suggestionScoreCalculator.calculate(any(), anyString(), anyDouble(), anyDouble())).thenReturn(0.0);

        Suggestions actualSuggestions = suggestionsService.findSuggestions(SEARCH_QUERY, LATITUDE, LONGITUDE);

        Assert.assertEquals(0, actualSuggestions.suggestions().size());
    }
}
