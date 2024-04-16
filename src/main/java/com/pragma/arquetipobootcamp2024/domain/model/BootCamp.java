package com.pragma.arquetipobootcamp2024.domain.model;

import java.util.List;

public class BootCamp {
    private final Long id;
    private final String name;
    private final String description;
    private List<Capability> capabilitiesList;

    public BootCamp(Long id, String name, String description, List<Capability> capabilitiesList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.capabilitiesList = capabilitiesList;
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

    public List<Capability> getCapabilitiesList() {
        return capabilitiesList;
    }

    public void setCapabilitiesList(List<Capability> capabilitiesList) {
        this.capabilitiesList = capabilitiesList;
    }


}
