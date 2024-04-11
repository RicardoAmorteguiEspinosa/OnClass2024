package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.RepeatedItemsInTheListException;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import com.pragma.arquetipobootcamp2024.domain.spi.ICapabilityPersistencePort;
import com.pragma.arquetipobootcamp2024.testData.CapabilityFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CapabilityUseCaseTest {

    @Mock
    private ICapabilityPersistencePort capabilityPersistencePort;

    @InjectMocks
    private CapabilityUseCase capabilityUseCase;

    @Test
    @DisplayName("Given a capability with duplicated technology IDs, when saving capability, then validate and throw exception")
    void givenCapabilityWithDuplicatedIds_whenSaveCapability_thenValidateAndThrowException() {
        // GIVEN
        Capability capability = CapabilityFactory.createCapability();
        //we add the same technology from position 0 to the list of technologies
        capability.getTechnologiesList().add(capability.getTechnologiesList().get(0));

        // WHEN, THEN
        assertThrows(RepeatedItemsInTheListException.class, () -> capabilityUseCase.saveCapability(capability));
        verify(capabilityPersistencePort, never()).saveCapability(capability);
    }

    @Test
    @DisplayName("Given a capability with unique technology IDs, when saving capability, then validate and save")
    void givenCapabilityWithUniqueIds_whenSaveCapability_thenValidateAndSave() {
        // GIVEN
        Capability capability = CapabilityFactory.createCapability();
        doNothing().when(capabilityPersistencePort).saveCapability(capability);

        // WHEN
        capabilityUseCase.saveCapability(capability);

        // THEN
        verify(capabilityPersistencePort, times(1)).saveCapability(capability);
    }

    @Test
    void testGetAllCapabilities() {
        // Given
        List<Capability> expectedCapabilities = Arrays.asList(
                CapabilityFactory.createCapability(1),
                CapabilityFactory.createCapability(2)
        );

        when(capabilityPersistencePort.getAllCapabilities(anyInt(), anyInt(), anyString())).thenReturn(expectedCapabilities);

        // When
        List<Capability> actualCapabilities = capabilityUseCase.getAllCapabilities(0, 10, "ASC");

        // Then
        assertEquals(expectedCapabilities, actualCapabilities);
    }
}