package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.RepeatedItemsInTheListException;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import com.pragma.arquetipobootcamp2024.domain.spi.IBootCampPersistencePort;
import com.pragma.arquetipobootcamp2024.testData.BootCampFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BootCampUseCaseTest {

    @Mock
    private IBootCampPersistencePort bootCampPersistencePort;

    @InjectMocks
    private BootCampUseCase bootCampUseCase;

    @Test
    @DisplayName("Given a boot camp with duplicated capability IDs, when saving boot camp, then validate and throw exception")
    void testSaveBootCampWithDuplicateIdsInTheList() {
        // GIVEN
        BootCamp bootCamp = BootCampFactory.createBootCamp();
        // Add the same capability twice to the list
        bootCamp.getCapabilitiesList().add(bootCamp.getCapabilitiesList().get(0));

        // WHEN, THEN
        assertThrows(RepeatedItemsInTheListException.class, () -> bootCampUseCase.saveBootCamp(bootCamp));
        verify(bootCampPersistencePort, never()).saveBootCamp(bootCamp);
    }

    @Test
    @DisplayName("Given a boot camp with unique capability IDs, when saving boot camp, then validate and save")
    void testSaveBootCamp() {
        // GIVEN
        BootCamp bootCamp = BootCampFactory.createBootCamp();
        doNothing().when(bootCampPersistencePort).saveBootCamp(bootCamp);

        // WHEN
        bootCampUseCase.saveBootCamp(bootCamp);

        // THEN
        verify(bootCampPersistencePort, times(1)).saveBootCamp(bootCamp);
    }


    @Test
    void testGetAllBootCamps() {
        // Given
        List<BootCamp> bootCamps = Arrays.asList(
                BootCampFactory.createBootCamp(),
                BootCampFactory.createBootCamp()
        );
        when(bootCampPersistencePort.getAllBootCamps(0, 10, "ASC")).thenReturn(bootCamps);

        // When
        List<BootCamp> result = bootCampUseCase.getAllBootCamps(0, 10, "ASC");

        // Then
        assertEquals(bootCamps.size(), result.size());
        for (int i = 0; i < bootCamps.size(); i++) {
            BootCamp expected = bootCamps.get(i);
            BootCamp actual = result.get(i);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
            assertEquals(expected.getDescription(), actual.getDescription());
        }
    }
}