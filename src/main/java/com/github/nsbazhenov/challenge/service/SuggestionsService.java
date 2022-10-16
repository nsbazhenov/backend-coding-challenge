package com.github.nsbazhenov.challenge.service;

import com.github.nsbazhenov.challenge.calculator.SuggestionScoreCalculator;
import com.github.nsbazhenov.challenge.model.City;
import com.github.nsbazhenov.challenge.model.Suggestion;
import com.github.nsbazhenov.challenge.model.Suggestions;
import com.github.nsbazhenov.challenge.io.ParseTsvFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import static java.util.stream.Collectors.toList;

/**
 *
 * Service for working with objects Suggestion.
 *
 * @author Bazhenov Nikita
 *
 */
@Service
public class SuggestionsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SuggestionsService.class);

    private final SuggestionScoreCalculator suggestionScoreCalculator;

    public SuggestionsService(SuggestionScoreCalculator suggestionScoreCalculator) {
        this.suggestionScoreCalculator = suggestionScoreCalculator;
    }

    /**
     * A method for finding matching cities.
     *
     * @return object Suggestions.
     */
    public Suggestions findSuggestions(String searchQuery, double latitude, double longitude) {
        LOGGER.info("Received request to search for suggestions. SearchQuery - {}, latitude - {}, longitude - {}",
                searchQuery, latitude, longitude);
        Suggestions suggestions = new Suggestions(getSuggestionList(searchQuery, latitude, longitude));
        LOGGER.info("Response for search suggestions: {}", suggestions);
        return suggestions;
    }

    /**
     * Method for forming the correct answer.
     *
     * @return list consisting of objects Suggestion.
     */
    public List<Suggestion> getSuggestionList(String searchQuery, double latitude, double longitude) {
        return  ParseTsvFile.getCityList().stream()
                .map(city -> getSuggestion(city, searchQuery, latitude, longitude))
                .filter(scoreGreaterThanZero())
                .sorted(sortedByScoreDesc())
                .collect(toList());
    }

    /**
     * Method for mapping from city to object Suggestion.
     *
     * Calculate city similarity scores
     *
     * @return object Suggestions.
     */
    public Suggestion getSuggestion(City city, String searchQuery, double latitude, double longitude) {
        LOGGER.debug("Obtained object for mapping and calculating comparison points, object - {}, searchQuery - {}, " +
                        "latitude - {}, longitude - {}", city, searchQuery, latitude, longitude);
        double totalScore = suggestionScoreCalculator.calculate(city, searchQuery, latitude, longitude);
        Suggestion suggestion =
                new Suggestion(city.fullName(), city.point().latitude(), city.point().longitude(), totalScore);
        LOGGER.debug("Output object with the calculated number of comparison points - {}", suggestion);
        return suggestion;
    }

    /**
     * Method for returning objects with more than 0.0 comparison points.
     *
     * @return object Suggestions.
     */
    private Predicate<Suggestion> scoreGreaterThanZero() {
        return suggestion -> suggestion.score() > 0.0;
    }

    /**
     * Method for correctly sorting the output list.
     *
     * @return comparator.
     */
    private Comparator<Suggestion> sortedByScoreDesc() {
        return Comparator.comparing(Suggestion::score, Comparator.reverseOrder());
    }
}
