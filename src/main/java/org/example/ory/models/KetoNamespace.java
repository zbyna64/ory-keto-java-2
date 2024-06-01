package org.example.ory.models;

import lombok.Getter;

@Getter
public enum KetoNamespace {

    ORGANIZATION("Organization"),
    USER("User");

    final String value;

    KetoNamespace(String value) {
        this.value = value;
    }
}
