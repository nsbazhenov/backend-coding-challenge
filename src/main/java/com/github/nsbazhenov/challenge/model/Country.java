package com.github.nsbazhenov.challenge.model;

public enum Country {

    US("US", "USA"),
    CA("CA", "Canada");

    private final String code;
    private final String fullName;

    Country(String code, String fullName) {
        this.code = code;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}