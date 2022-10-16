package com.github.nsbazhenov.challenge.controller;

import com.github.nsbazhenov.challenge.model.Suggestions;
import com.github.nsbazhenov.challenge.service.SuggestionsService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SuggestionsControllerTest {

    private static final String SEARCH_QUERY = "Londo";
    private static final double LATITUDE = 43.70011;
    private static final double LONGITUDE = -79.4163;

    @InjectMocks
    private SuggestionsController suggestionsController;

    @Mock
    private SuggestionsService suggestionsService;

    @Test
    public void whenGetSuggestions_givenValidParams_thenShouldReturnSuggestions() {
        Suggestions expectedSuggestions = new Suggestions(new ArrayList<>());
        when(suggestionsService.findSuggestions(SEARCH_QUERY, LATITUDE, LONGITUDE)).thenReturn(expectedSuggestions);

        ResponseEntity<Suggestions> actualResponse = suggestionsController.getSuggestions(SEARCH_QUERY, LATITUDE, LONGITUDE);

        verify(suggestionsService).findSuggestions(SEARCH_QUERY, LATITUDE, LONGITUDE);
        Assert.assertEquals(actualResponse.getBody(), expectedSuggestions);
    }

    @Test(expected = MockitoException.class)
    public void whenGetSuggestions_givenException_thenShouldThrowException() {
        when(suggestionsService.findSuggestions(SEARCH_QUERY, LATITUDE, LONGITUDE)).thenThrow(MockitoException.class);
        suggestionsController.getSuggestions(SEARCH_QUERY, LATITUDE, LONGITUDE);
    }
}
