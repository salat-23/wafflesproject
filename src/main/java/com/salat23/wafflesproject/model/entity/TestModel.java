package com.salat23.wafflesproject.model.entity;

import lombok.Data;

@Data
public class TestModel {

    public TestModel(String name) {
        this.name = name;
    }

    private String name;
}
