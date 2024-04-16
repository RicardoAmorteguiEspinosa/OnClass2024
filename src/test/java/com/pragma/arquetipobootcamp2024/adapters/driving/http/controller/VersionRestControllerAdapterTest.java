package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.IVersionRequestMapper;
import com.pragma.arquetipobootcamp2024.domain.api.IVersionServicePort;
import com.pragma.arquetipobootcamp2024.testData.RequestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class VersionRestControllerAdapterTest {
    @Mock
    private IVersionServicePort versionServicePort;

    @Mock
    private IVersionRequestMapper versionRequestMapper;

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
    void testAddVersion(RequestCase  testCase) throws Exception {
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
        }
        else if (expectedStatus == HttpStatus.BAD_REQUEST) {
            Exception resolvedException = mvcResult.getResolvedException();
            assertTrue(resolvedException instanceof MethodArgumentNotValidException);
        }
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