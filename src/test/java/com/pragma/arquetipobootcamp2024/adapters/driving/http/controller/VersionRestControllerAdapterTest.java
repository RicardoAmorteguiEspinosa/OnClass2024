package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.VersionResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.IVersionRequestMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.IVersionResponseMapper;
import com.pragma.arquetipobootcamp2024.domain.api.IVersionServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.Version;
import com.pragma.arquetipobootcamp2024.testData.RequestCase;
import com.pragma.arquetipobootcamp2024.testData.VersionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VersionRestControllerAdapterTest {
    @Mock
    private IVersionServicePort versionServicePort;

    @Mock
    private IVersionRequestMapper versionRequestMapper;

    @Mock
    private IVersionResponseMapper versionResponseMapper;
    @InjectMocks
    private VersionRestControllerAdapter versionRestControllerAdapter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(versionRestControllerAdapter).build();
    }

    @ParameterizedTest
    @DisplayName("Test adding a version")
    @MethodSource("provideCapabilityRequests")
    void testAddVersion(RequestCase testCase) throws Exception {
        // Given
        String requestBody = testCase.getRequestBody();
        HttpStatus expectedStatus = testCase.getExpectedStatus();

        // When & Then
        MvcResult mvcResult = mockMvc.perform(post("/version/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();

        if (expectedStatus == HttpStatus.CREATED) {
            verify(versionServicePort).saveVersion(any());
        } else if (expectedStatus == HttpStatus.BAD_REQUEST) {
            Exception resolvedException = mvcResult.getResolvedException();
            assertTrue(resolvedException instanceof MethodArgumentNotValidException);
        }
    }

    @Test
    @DisplayName("Test retrieving all versions")
    void testGetVersions() throws Exception {
        // Given
        List<Version> versions = Arrays.asList(
                VersionFactory.createVersion(1l, 20),
                VersionFactory.createVersion(2l, 5),
                VersionFactory.createVersion(3l, 10)

        );
        List<VersionResponse> versionResponses = Arrays.asList(
                VersionFactory.toVersionResponse(versions.get(0)),
                VersionFactory.toVersionResponse(versions.get(1)),
                VersionFactory.toVersionResponse(versions.get(2))
        );
        when(versionServicePort.getAllVersions(anyInt(), anyInt(), anyString())).thenReturn(versions);
        when(versionResponseMapper.toVersionResponseList(any())).thenReturn(versionResponses);

        // When & Then
        mockMvc.perform(get("/version/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("order", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(versions.size()));
    }

    @Test
    @DisplayName("Test retrieving versions by boot camp")
    void testGetVersionsByBootCamp() throws Exception {
        // Given
        int idBootCamp = 20;
        List<Version> versions = Arrays.asList(
                VersionFactory.createVersion(1l, idBootCamp),
                VersionFactory.createVersion(2l, idBootCamp),
                VersionFactory.createVersion(3l, idBootCamp)

        );
        List<VersionResponse> versionResponses = Arrays.asList(
                VersionFactory.toVersionResponse(versions.get(0)),
                VersionFactory.toVersionResponse(versions.get(1)),
                VersionFactory.toVersionResponse(versions.get(2))
        );
        when(versionServicePort.getAllVersionsByBootCamp(0, 10, "ASC", idBootCamp)).thenReturn(versions);
        when(versionResponseMapper.toVersionResponseList(versions)).thenReturn(versionResponses);

        // When & Then
        mockMvc.perform(get("/version/search/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("order", "ASC")
                        .param("id", "20")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(versions.size()));
    }

    private static Stream<Arguments> provideCapabilityRequests() {
        return Stream.of(
                // Test case with valid data
                Arguments.of(generateRequest("{\"startDate\":\"2025-07-15\",\"endDate\":\"2025-09-15\",\"quota\":110,\"idBootCamp\":2}", HttpStatus.CREATED)),

                // Test case with startDate in the past
                Arguments.of(generateRequest("{\"startDate\":\"2023-07-15\",\"endDate\":\"2024-09-15\",\"quota\":110,\"idBootCamp\":2}", HttpStatus.BAD_REQUEST)),

                // Test case with endDate in the past
                Arguments.of(generateRequest("{\"startDate\":\"2024-07-15\",\"endDate\":\"2023-09-15\",\"quota\":110,\"idBootCamp\":2}", HttpStatus.BAD_REQUEST)),

                // Test case with quota less than 1
                Arguments.of(generateRequest("{\"startDate\":\"2024-07-15\",\"endDate\":\"2024-09-15\",\"quota\":0,\"idBootCamp\":2}", HttpStatus.BAD_REQUEST)),

                // Test case with idBootCamp less than 1
                Arguments.of(generateRequest("{\"startDate\":\"2024-07-15\",\"endDate\":\"2024-09-15\",\"quota\":110,\"idBootCamp\":0}", HttpStatus.BAD_REQUEST)),

                // Test case with missing startDate
                Arguments.of(generateRequest("{\"endDate\":\"2024-09-15\",\"quota\":110,\"idBootCamp\":2}", HttpStatus.BAD_REQUEST)),

                // Test case with missing endDate
                Arguments.of(generateRequest("{\"startDate\":\"2024-07-15\",\"quota\":110,\"idBootCamp\":2}", HttpStatus.BAD_REQUEST)),

                // Test case with missing quota
                Arguments.of(generateRequest("{\"startDate\":\"2024-07-15\",\"endDate\":\"2024-09-15\",\"idBootCamp\":2}", HttpStatus.BAD_REQUEST)),

                // Test case with missing idBootCamp
                Arguments.of(generateRequest("{\"startDate\":\"2024-07-15\",\"endDate\":\"2024-09-15\",\"quota\":110}", HttpStatus.BAD_REQUEST)),

                // Test case with null startDate
                Arguments.of(generateRequest("{\"startDate\":null,\"endDate\":\"2024-09-15\",\"quota\":110,\"idBootCamp\":2}", HttpStatus.BAD_REQUEST)),

                // Test case with null endDate
                Arguments.of(generateRequest("{\"startDate\":\"2024-07-15\",\"endDate\":null,\"quota\":110,\"idBootCamp\":2}", HttpStatus.BAD_REQUEST)),

                // Test case with null quota
                Arguments.of(generateRequest("{\"startDate\":\"2024-07-15\",\"endDate\":\"2024-09-15\",\"quota\":null,\"idBootCamp\":2}", HttpStatus.BAD_REQUEST)),

                // Test case with null idBootCamp
                Arguments.of(generateRequest("{\"startDate\":\"2024-07-15\",\"endDate\":\"2024-09-15\",\"quota\":110,\"idBootCamp\":null}", HttpStatus.BAD_REQUEST))
        );
    }

    private static RequestCase generateRequest(String requestBody, HttpStatus expectedStatus) {
        return new RequestCase(requestBody, expectedStatus);
    }
}