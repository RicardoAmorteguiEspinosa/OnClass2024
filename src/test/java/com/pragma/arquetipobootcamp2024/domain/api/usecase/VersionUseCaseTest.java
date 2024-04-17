package com.pragma.arquetipobootcamp2024.domain.api.usecase;

import com.pragma.arquetipobootcamp2024.adapters.driven.jpa.mysql.exception.IllegalArgException;
import com.pragma.arquetipobootcamp2024.domain.model.Version;
import com.pragma.arquetipobootcamp2024.domain.spi.IVersionPersistencePort;
import com.pragma.arquetipobootcamp2024.testData.VersionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VersionUseCaseTest {
    @Mock
    private IVersionPersistencePort versionPersistencePort;
    @InjectMocks
    private VersionUseCase versionUseCase;

    @Test
    void testSaveVersion_ValidDates() {
        // Given
        Version version = VersionFactory.createVersion(1l, 20);

        // When
        versionUseCase.saveVersion(version);

        // Then
        verify(versionPersistencePort, times(1)).saveVersion(version);
    }

    @Test
    void testSaveVersion_InvalidDates() {
        // Given
        Version version = VersionFactory.createVersionWithInvalidDates(1l, 20);

        // When & Then
        Assertions.assertThrows(IllegalArgException.class, () -> {
            versionUseCase.saveVersion(version);
        });
        verify(versionPersistencePort, never()).saveVersion(any());
    }

    @Test
    void testGetAllVersions() {
        // Given
        List<Version> versions = Arrays.asList(
                VersionFactory.createVersion(1l, 20),
                VersionFactory.createVersion(2l, 19)
        );


        when(versionPersistencePort.getAllVersions(anyInt(), anyInt(), anyString())).thenReturn(versions);

        // When
        List<Version> result = versionUseCase.getAllVersions(0, 10, "startDate");

        // Then
        Assertions.assertEquals(versions.size(), result.size());

        for (Version version : result) {
            Assertions.assertTrue(versions.contains(version));
        }
    }

    @Test
    void testGetAllVersionsByBootCamp() {
        // Given
        long bootCampId = 20;
        List<Version> versions = Arrays.asList(
                VersionFactory.createVersion(1l, bootCampId),
                VersionFactory.createVersion(2l, bootCampId),
                VersionFactory.createVersion(3l, bootCampId)

        );

        when(versionPersistencePort.getAllVersionsByBootCamp(anyInt(), anyInt(), anyString(), eq(bootCampId))).thenReturn(versions);

        // When
        List<Version> result = versionUseCase.getAllVersionsByBootCamp(0, 10, "startDate", bootCampId);

        // Then
        Assertions.assertEquals(versions.size(), result.size());

        for (Version version : result) {
            Assertions.assertEquals(bootCampId, version.getIdBootCamp());
        }
    }
}
