package com.pragma.arquetipobootcamp2024.testData;

import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


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

    public static Capability toModel(Long id, String name, String description, List<Technology> technologiesList) {
        Capability capability = new Capability(id, name, description, technologiesList);
        return capability;
    }

    static String getRandomName() {
        String[] names = new String[] { "Web Development", "Database Management", "Backend Programming", "Data Science", "Cybersecurity", "Mobile App Development" };
        return names[random.nextInt(names.length)];
    }

    public static List<Capability> createCapabilityList(int count) {
        List<Capability> capabilities = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            capabilities.add(new Capability((long)i, null, null, null));
        }
        return capabilities;
    }


}
