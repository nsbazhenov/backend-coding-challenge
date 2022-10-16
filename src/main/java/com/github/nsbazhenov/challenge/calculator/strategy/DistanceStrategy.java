package com.github.nsbazhenov.challenge.calculator.strategy;

import com.github.nsbazhenov.challenge.model.Point;

public interface DistanceStrategy {
    double distance(Point start, Point end);
}
