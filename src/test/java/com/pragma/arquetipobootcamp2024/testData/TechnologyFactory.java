package com.pragma.arquetipobootcamp2024.testData;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;

import java.util.ArrayList;
import java.util.List;
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

    public static TechnologyResponse technologyToTechnologyResponse(Technology technology)
    {
        return new TechnologyResponse(technology.getId(), technology.getName(), technology.getDescription());
    }

    public static List<Technology> createTechnologyList(int count) {
        List<Technology> technologies = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            technologies.add(new Technology((long)i, getRandomName(), null));
        }
        return technologies;
    }}

