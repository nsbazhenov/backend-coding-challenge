package com.github.nsbazhenov.challenge.controller;

import com.github.nsbazhenov.challenge.model.Suggestions;
import com.github.nsbazhenov.challenge.service.SuggestionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

/**
 * Controller for processing queries to find a list of suitable cities
 *
 * @author Bazhenov Nikita
 *
 */
@RestController
@Validated
public class SuggestionsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionsService.class);

    private final SuggestionsService suggestionsService;

    public SuggestionsController(SuggestionsService suggestionsService) {
        this.suggestionsService = suggestionsService;
    }

    @GetMapping(value = "/suggestions")
    public ResponseEntity<Suggestions> getSuggestions(@RequestParam(value = "q") String searchQuery,
                                                     @RequestParam(value = "latitude", required = false) @Min(-90L) @Max(90L) double latitude,
                                                     @RequestParam(value = "longitude", required =  false) @Min(-180L) @Max(180L) double longitude) {
        LOGGER.info("Received request to search for suggestions. SearchQuery - {}, latitude - {}, longitude - {}",
                searchQuery, latitude, longitude);
        ResponseEntity<Suggestions> response =
                ResponseEntity.ok(suggestionsService.findSuggestions(searchQuery, latitude, longitude));
        LOGGER.info("Response for search suggestions: {}", response);
        return response;
    }
}