package com.github.nsbazhenov.challenge.io;

import com.github.nsbazhenov.challenge.model.City;
import com.github.nsbazhenov.challenge.model.Country;
import com.github.nsbazhenov.challenge.model.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;

/**
 *
 * A class for parsing an .tsv file into a list of objects.
 * The output is a list of objects.
 *
 * @author Bazhenov Nikita
 *
 */
@Component
public class ParseTsvFile {

    private static final Logger LOGGER = LoggerFactory.getLogger(ParseTsvFile.class);

    private static final List<City> cityList = parsingTsvFile();

    private static final String COLUMN_SEPARATOR = "\t";
    private static final String LIST_SEPARATOR = ",";

    private static final int NAME_COLUMN = 1;
    private static final int ALTERNATIVE_NAMES_COLUMN = 3;
    private static final int LATITUDE_COLUMN = 4;
    private static final int LONGITUDE_COLUMN = 5;
    private static final int COUNTRY_COLUMN = 8;
    private static final int ADMIN_CODE_COLUMN = 10;

    private static final String absolutPathInTsvFile = "C:\\Users\\nikel\\IdeaProjects\\backend-coding-challenge\\data\\cities_canada-usa.tsv";


    /**
     * The method returns the list of unpacked objects.
     *
     * @return list consisting of objects City.
     */
    public static List<City> getCityList() {
        return cityList;
    }

    /**
     * The method finds the file and takes the necessary metadata from it.
     *
     * @return list consisting of objects City.
     */
    private static List<City> parsingTsvFile() {
        LOGGER.info("Start parsing an .tsv file");
        List<City> cities = new ArrayList<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(absolutPathInTsvFile))) {
            bufferedReader.readLine();

            String line = bufferedReader.readLine();

            while (line != null) {
                String[] attributes = line.split(COLUMN_SEPARATOR, -1);
                City city = createCity(attributes);
                cities.add(city);

                line = bufferedReader.readLine();
            }
        } catch (IOException exception) {
            LOGGER.error("An error occurred while parsing the .tsv file: {}", exception.getMessage());
        }
        LOGGER.info("A list of objects is obtained from the .tsv file");
        return cities;
    }

    /**
     * The method assigns the necessary values to the object fields from the resolved file.
     *
     * @return City object.
     */
    private static City createCity(String[] metadata) {
        String name = metadata[NAME_COLUMN];
        String[] alternativeName = split(trimToEmpty(metadata[ALTERNATIVE_NAMES_COLUMN]), LIST_SEPARATOR);
        double latitude = Double.parseDouble(metadata[LATITUDE_COLUMN]);
        double longitude = Double.parseDouble(metadata[LONGITUDE_COLUMN]);
        String country = Country.valueOf(metadata[COUNTRY_COLUMN]).getFullName();
        String adminCode = metadata[ADMIN_CODE_COLUMN];
        String fullName = name + ", " + adminCode + ", " + country;

        return new City(name, country, alternativeName, new Point(latitude, longitude), adminCode, fullName, 0.0);
    }
}
