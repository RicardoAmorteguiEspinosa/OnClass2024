package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.request.AddBootCampRequest;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.BootCampResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.IBootCampRequestMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.IBootCampResponseMapper;
import com.pragma.arquetipobootcamp2024.domain.api.IBootCampServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.BootCamp;
import com.pragma.arquetipobootcamp2024.testData.BootCampFactory;
import com.pragma.arquetipobootcamp2024.testData.RequestCase;
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
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class BootCampRestControllerAdapterTest {

    @Mock
    private IBootCampServicePort bootCampServicePort;

    @Mock
    private IBootCampRequestMapper bootCampRequestMapper;

    @Mock
    private IBootCampResponseMapper bootCampResponseMapper;

    @InjectMocks
    private BootCampRestControllerAdapter bootCampRestControllerAdapter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bootCampRestControllerAdapter).build();
    }

    @ParameterizedTest
    @DisplayName("Test adding a boot camp")
    @MethodSource("provideBootCampRequests")
    void testSaveBootCamp(RequestCase testCase) throws Exception {
        // Given
        String requestBody = testCase.getRequestBody();
        HttpStatus expectedStatus = testCase.getExpectedStatus();

        // When & Then
        MvcResult mvcResult = mockMvc.perform(post("/bootcamp/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();

        if (expectedStatus == HttpStatus.CREATED) {
            verify(bootCampServicePort).saveBootCamp(any());
        }
        else if (expectedStatus == HttpStatus.BAD_REQUEST) {
            Exception resolvedException = mvcResult.getResolvedException();
            assertTrue(resolvedException instanceof MethodArgumentNotValidException);
        }
    }

    @Test
    @DisplayName("Test retrieving all boot camps")
    void testGetAllBootCamps() throws Exception {
        // Given
        List<BootCamp> bootCamps = Arrays.asList(
                BootCampFactory.createBootCamp(1),
                BootCampFactory.createBootCamp(2)
        );
        List<BootCampResponse> bootCampResponses = Arrays.asList(
                BootCampFactory.toBootCampResponse(bootCamps.get(0)),
                BootCampFactory.toBootCampResponse(bootCamps.get(1))
        );

        when(bootCampServicePort.getAllBootCamps(0, 10, "ASC")).thenReturn(bootCamps);
        when(bootCampResponseMapper.toBootCampResponseList(bootCamps)).thenReturn(bootCampResponses);

        // When & Then
        mockMvc.perform(get("/bootcamp/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("order", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    private static Stream<Arguments> provideBootCampRequests() {
        return Stream.of(
                // Test case with valid data
                Arguments.of(generateRequest("{\"name\":\"Valid Bootcamp Name\",\"description\":\"Valid Description\"," +
                        "\"capabilitiesList\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4}]}", HttpStatus.CREATED)),

                // Test case with null name
                Arguments.of(generateRequest("{\"name\":null,\"description\":\"Valid Description\"," +
                        "\"capabilitiesList\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4}]}", HttpStatus.BAD_REQUEST)),

                // Test case with blank name
                Arguments.of(generateRequest("{\"name\":\"\",\"description\":\"Valid Description\"," +
                        "\"capabilitiesList\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4}]}", HttpStatus.BAD_REQUEST)),

                // Test case with null description
                Arguments.of(generateRequest("{\"name\":\"Valid Name\",\"description\":null," +
                        "\"capabilitiesList\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4}]}", HttpStatus.BAD_REQUEST)),

                // Test case with blank description
                Arguments.of(generateRequest("{\"name\":\"Valid Name\",\"description\":\"\"," +
                        "\"capabilitiesList\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4}]}", HttpStatus.BAD_REQUEST)),

                // Test case with null capabilities list
                Arguments.of(generateRequest("{\"name\":\"Valid Name\",\"description\":\"Valid Description\"," +
                        "\"capabilitiesList\":null}", HttpStatus.BAD_REQUEST)),

                // Test case with empty capabilities list
                Arguments.of(generateRequest("{\"name\":\"Valid Name\",\"description\":\"Valid Description\"," +
                        "\"capabilitiesList\":[]}", HttpStatus.BAD_REQUEST)),

                // Test case with capabilities list containing more elements than the maximum
                Arguments.of(generateRequest("{\"name\":\"Valid Name\",\"description\":\"Valid Description\"," +
                        "\"capabilitiesList\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4},{\"id\":5}," +
                        "{\"id\":6},{\"id\":7},{\"id\":8},{\"id\":9},{\"id\":10}," +
                        "{\"id\":11},{\"id\":12},{\"id\":13},{\"id\":14},{\"id\":15}," +
                        "{\"id\":16},{\"id\":17},{\"id\":18},{\"id\":19},{\"id\":20}," +
                        "{\"id\":21}]}", HttpStatus.BAD_REQUEST))
        );
    }

    private static RequestCase generateRequest(String requestBody, HttpStatus expectedStatus) {
        return new RequestCase(requestBody, expectedStatus);
    }

}
