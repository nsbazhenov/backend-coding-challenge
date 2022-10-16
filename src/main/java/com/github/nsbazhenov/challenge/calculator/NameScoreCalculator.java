package com.github.nsbazhenov.challenge.calculator;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.*;
import static org.apache.commons.math3.util.Precision.round;
import static org.apache.logging.log4j.util.Strings.isNotBlank;

/**
 *
 * Class for calculating name comparison points.
 *
 * @author Bazhenov Nikita
 *
 */
@Component
public class NameScoreCalculator {

    private static final Logger LOGGER = LoggerFactory.getLogger(NameScoreCalculator.class);

    /**
     * The method counts name comparison points.
     *
     * @return number of name points.
     */
    public double calculate(String searchQuery, String cityName) {
        LOGGER.debug("Calculating name points, searchQuery - {}, cityName - {}", searchQuery, cityName);
        if (StringUtils.equals(searchQuery, cityName)) {
            LOGGER.debug("Calculating name points, searchQuery - {}, cityName - {}, score - {}",
                    searchQuery, cityName, 1.0);
            return 1.0;
        }

        if (startsWith(cityName, searchQuery)) {
            String commonPrefix = getCommonPrefix(searchQuery, cityName);
            if (isNotBlank(commonPrefix)) {
                double score = length(commonPrefix) / longestLength(searchQuery, cityName);
                double round = round(score, 2);
                LOGGER.debug("Calculating name points, searchQuery - {}, cityName - {}, score - {}",
                        searchQuery, cityName, round);
                return round;
            }
        }

        LOGGER.debug("Calculating name points, searchQuery - {}, cityName - {}, score - {}",
                searchQuery, cityName, 0.0);
        return 0.0;
    }

    public static double longestLength(String str1, String str2) {
        int length1 = length(str1);
        int length2 = length(str2);
        return Math.max(length1, length2);
    }
}
