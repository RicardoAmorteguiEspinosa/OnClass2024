package com.pragma.arquetipobootcamp2024.domain.model;

import java.time.LocalDate;

public class Version {
    private final long id;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private final int quota;
    private final long idBootCamp;

    public Version(long id, LocalDate startDate, LocalDate endDate, int quota, long idBootCamp) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.quota = quota;
        this.idBootCamp = idBootCamp;
    }

    public long getId() {
        return id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public int getQuota() {
        return quota;
    }

    public long getIdBootCamp() {
        return idBootCamp;
    }
}
