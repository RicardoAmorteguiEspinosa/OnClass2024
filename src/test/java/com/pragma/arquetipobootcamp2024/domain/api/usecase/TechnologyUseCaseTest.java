package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.domain.api.usecase.TechnologyUseCase;
import com.pragma.arquetipobootcamp2024.testData.TechnologyFactory;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;
import com.pragma.arquetipobootcamp2024.domain.spi.ITechnologyPersistencePort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TechnologyUseCaseTest {
    @Mock
    private ITechnologyPersistencePort technologyPersistencePort;
    @InjectMocks
    private TechnologyUseCase technologyUseCase;

    @Test
    @DisplayName("Given a technology, it must be inserted into the database.")
    void saveTechnology() {
        //GIVEN (DADO)
        Technology technology = TechnologyFactory.createTechnology();
        doNothing().when(technologyPersistencePort).saveTechnology(technology);

        //WHEN (CUANDO)
        technologyUseCase.saveTechnology(technology);

        //THEN (ENTONCES)
        verify(technologyPersistencePort, times(1)).saveTechnology(technology);
    }














}