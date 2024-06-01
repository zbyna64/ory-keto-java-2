package org.example.ory.models;

public enum KetoRelation {

    PARENTS("parents"),
    OWNERS("owners"),
    VIEWERS("viewers"),
    EDITORS("editors");

    String value;

    KetoRelation(String value) {
        this.value = value;
    }
}
