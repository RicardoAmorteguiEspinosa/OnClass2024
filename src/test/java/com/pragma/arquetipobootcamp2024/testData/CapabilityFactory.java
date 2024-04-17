package com.pragma.arquetipobootcamp2024.testData;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.CapabilityResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.TechnologyByCapabilityResponse;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CapabilityFactory {
    private static final Random random = new Random();

    public static Capability createCapability() {
        String name = getRandomName();
        String description = "Description for " + name;
        List<Technology> technologies = TechnologyFactory.createTechnologyList(random.nextInt(20 - 3 + 1) + 3);
        return new Capability(null, name, description, technologies);
    }

    public static Capability createCapability(long id) {
        String name = getRandomName();
        String description = "Description for " + name;
        List<Technology> technologies = TechnologyFactory.createTechnologyList(random.nextInt(20 - 3 + 1) + 3);
        return new Capability(id, name, description, technologies);
    }

    public static Capability createCapability(List<Technology> technologies) {
        String name = getRandomName();
        String description = "Description for " + name;
        return new Capability(null, name, description, technologies);
    }

    private static String getRandomName() {
        String[] names = new String[]{"Web Development", "Database Management", "Backend Programming", "Data Science", "Cybersecurity", "Mobile App Development"};
        return names[random.nextInt(names.length)];
    }

    public static List<Capability> createCapabilityList(int count) {
        List<Capability> capabilities = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            capabilities.add(createCapability(i));
        }
        return capabilities;
    }

    public static List<CapabilityResponse> toCapabilityResponseList(List<Capability> capabilities) {
        return capabilities.stream()
                .map(capability -> {
                    List<TechnologyByCapabilityResponse> technologiesList = capability.getTechnologiesList().stream()
                            .map(technology -> new TechnologyByCapabilityResponse(technology.getId(), technology.getName()))
                            .collect(Collectors.toList());
                    return new CapabilityResponse(capability.getId(), capability.getName(), capability.getDescription(), technologiesList);
                })
                .collect(Collectors.toList());
    }
}
