package com.pragma.arquetipobootcamp2024.testData;

import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;

import java.util.List;
import java.util.Random;


public class BootCampFactory {

    private static final Random random = new Random();

    public static BootCamp createBootCamp() {
        String name = getRandomName();
        String description = "Description for " + name;
        List<Capability> capabilities = CapabilityFactory.createCapabilityList(random.nextInt(4) + 1);
        return new BootCamp(null, name, description, capabilities);
    }

    static String getRandomName() {
        String[] names = new String[] { "Coding Bootcamp", "Data Science Bootcamp", "Web Development Bootcamp", "Cybersecurity Bootcamp", "Mobile App Development Bootcamp" };
        return names[random.nextInt(names.length)];
    }
}
