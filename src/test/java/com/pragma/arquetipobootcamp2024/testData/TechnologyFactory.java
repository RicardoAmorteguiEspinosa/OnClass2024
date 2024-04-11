package com.pragma.arquetipobootcamp2024.testData;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;

import java.util.Random;

public class TechnologyFactory {
    private static final Random random = new Random();

    public static Technology createTechnology() {
        String name = getRandomName();
        String description = "Description for " + name;
        return new Technology(null, name, description);
    }

    public static Technology toModel(Long id, String name, String description) {
        return new Technology(id, name, description);
    }
    static String getRandomName() {
        String[] names = new String[] { "Java", "Python", "JavaScript", "C#", "Ruby", "Go", "Kotlin", "Swift", "Rust", "Scala" };
        return names[random.nextInt(names.length)];
    }

}
