package com.github.nsbazhenov.challenge.model;

public record City(String name, String country, String[] alternativeName, Point point, String adminCode, String fullName,
    double score) {
}