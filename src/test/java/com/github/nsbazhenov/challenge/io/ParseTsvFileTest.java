package com.github.nsbazhenov.challenge.io;

import com.github.nsbazhenov.challenge.model.City;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class ParseTsvFileTest {

    @Test
    public void test() {
        List<City> cityList = ParseTsvFile.getCityList();
        Assert.assertEquals(7237, cityList.size());
    }
}
