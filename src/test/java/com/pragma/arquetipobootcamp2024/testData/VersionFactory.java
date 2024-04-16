package com.pragma.arquetipobootcamp2024.testData;

import com.pragma.arquetipobootcamp2024.domain.model.Version;

import java.time.LocalDate;
import java.util.Random;

public class VersionFactory {
    private static final Random random = new Random();

    public static Version createVersion(Long id, long idBootCamp) {
        LocalDate startDate = generateRandomFutureDate();
        LocalDate endDate = generateRandomFutureDateAfter(startDate);
        int quota = random.nextInt(100);
        return new Version(id, startDate, endDate, quota, idBootCamp);
    }

    public static Version createVersionWithInvalidDates(Long id, long idBootCamp) {
        LocalDate startDate = LocalDate.now().plusDays(2);
        LocalDate endDate = LocalDate.now().plusDays(1);
        int quota = random.nextInt(100);
        return new Version(id, startDate, endDate, quota, idBootCamp);
    }

    private static LocalDate generateRandomFutureDate() {
        LocalDate now = LocalDate.now();
        long minDay = now.toEpochDay() + 1;
        long maxDay = now.plusYears(1).toEpochDay();
        long randomDay = minDay + random.nextInt((int) (maxDay - minDay));
        return LocalDate.ofEpochDay(randomDay);
    }

    private static LocalDate generateRandomFutureDateAfter(LocalDate startDate) {
        LocalDate now = LocalDate.now();
        long minDay = startDate.toEpochDay() + 1;
        long maxDay = now.plusYears(1).toEpochDay();
        long randomDay = minDay + random.nextInt((int) (maxDay - minDay));
        return LocalDate.ofEpochDay(randomDay);
    }


}
