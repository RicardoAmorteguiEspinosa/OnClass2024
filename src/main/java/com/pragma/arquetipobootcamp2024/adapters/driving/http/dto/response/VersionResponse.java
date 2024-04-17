package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class VersionResponse {
    private final long id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int quota;
    private final long idBootCamp;
}
