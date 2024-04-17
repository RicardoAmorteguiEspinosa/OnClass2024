package com.pragma.arquetipobootcamp2024.domain.model;

import java.util.List;

public class Capability {
    private final Long id;
    private final String name;
    private final String description;

    private List<Technology> technologiesList;

    public Capability(Long id, String name, String description, List<Technology> technologiesList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.technologiesList = technologiesList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }


    public List<Technology> getTechnologiesList() {
        return technologiesList;
    }

    public void setTechnologiesList(List<Technology> technologiesList) {
        this.technologiesList = technologiesList;
    }
}
