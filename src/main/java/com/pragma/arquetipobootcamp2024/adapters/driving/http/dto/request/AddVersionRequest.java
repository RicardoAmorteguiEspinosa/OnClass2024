package com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public record AddVersionRequest(
        @NotNull(message = "Start date cannot be null")
        @Future(message = "Start date must be in the future")
        LocalDate startDate,
        @NotNull(message = "Start date must be in the future")
        @Future(message = "End date must be in the future")
        LocalDate endDate,
        @Min(value = 1, message = "The quota must be greater than or equal to one")
        int quota,
        @NotNull(message = "Bootcamp ID cannot be null")
        @Min(value = 1, message = "Bootcamp ID must be greater than zero")
        long idBootCamp) {

}
