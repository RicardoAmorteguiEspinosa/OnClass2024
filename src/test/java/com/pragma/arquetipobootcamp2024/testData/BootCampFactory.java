package com.pragma.arquetipobootcamp2024.testData;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.BootCampResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.CapabilityByBootCampResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.CapabilityResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.TechnologyByCapabilityResponse;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class BootCampFactory {

    private static final Random random = new Random();

    public static BootCamp createBootCamp() {
        String name = getRandomName();
        String description = "Description for " + name;
        List<Capability> capabilities = CapabilityFactory.createCapabilityList(random.nextInt(4) + 1);
        return new BootCamp(null, name, description, capabilities);
    }

    public static BootCamp createBootCamp(long id) {
        String name = getRandomName();
        String description = "Description for " + name;
        List<Capability> capabilities = CapabilityFactory.createCapabilityList(random.nextInt(4) + 1);
        return new BootCamp(id, name, description, capabilities);
    }

    static String getRandomName() {
        String[] names = new String[] { "Coding Bootcamp", "Data Science Bootcamp", "Web Development Bootcamp", "Cybersecurity Bootcamp", "Mobile App Development Bootcamp" };
        return names[random.nextInt(names.length)];
    }

    public static BootCampResponse toBootCampResponse(BootCamp bootCamp) {
        List<CapabilityByBootCampResponse> capabilitiesResponses = bootCamp.getCapabilitiesList().stream()
                .map(BootCampFactory::toCapabilityByBootCampResponse)
                .collect(Collectors.toList());

        return new BootCampResponse(bootCamp.getId(), bootCamp.getName(), bootCamp.getDescription(), capabilitiesResponses);
    }

    private static CapabilityByBootCampResponse toCapabilityByBootCampResponse(Capability capability) {
        List<TechnologyByCapabilityResponse> technologiesList = capability.getTechnologiesList().stream()
                .map(technology -> new TechnologyByCapabilityResponse(technology.getId(), technology.getName()))
                .collect(Collectors.toList());
        return new CapabilityByBootCampResponse(capability.getId(), capability.getName(), technologiesList);
    }
}
