package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.controller.TechnologyRestControllerAdapter;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.TechnologyResponse;
import com.pragma.arquetipobootcamp2024.domain.model.Technology;
import com.pragma.arquetipobootcamp2024.testData.RequestCase;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.pragma.arquetipobootcamp2024.domain.api.ITechnologyServicePort;
import com.pragma.arquetipobootcamp2024.testData.TechnologyFactory;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TechnologyRestControllerAdapterTest {
    @Mock
    private ITechnologyServicePort technologyServicePort;
    @Mock
    private ITechnologyRequestMapper technologyRequestMapper;
    @Mock
    private ITechnologyResponseMapper technologyResponseMapper;
    @InjectMocks
    private TechnologyRestControllerAdapter technologyRestControllerAdapter;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(technologyRestControllerAdapter).build();
    }

    @ParameterizedTest
    @MethodSource("provideTechnologyRequests")
    @DisplayName("Test to add technology with different request cases")
    void testAddTechnology(RequestCase testCase) throws Exception {
        // Given
        String requestBody = testCase.getRequestBody();
        HttpStatus expectedStatus = testCase.getExpectedStatus();

        // When & Then
        MvcResult mvcResult = mockMvc.perform(post("/technology/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();

        if (expectedStatus == HttpStatus.CREATED) {
            verify(technologyServicePort).saveTechnology(any());
        } else if (expectedStatus == HttpStatus.BAD_REQUEST) {
            Exception resolvedException = mvcResult.getResolvedException();
            assertTrue(resolvedException instanceof MethodArgumentNotValidException);
        }
    }

    @Test
    @DisplayName("Test to get all technologies")
    void testGetAllTechnologies() throws Exception {
        // Given
        List<Technology> expectedTechnologies = Arrays.asList(
                TechnologyFactory.createTechnology(),
                TechnologyFactory.createTechnology(),
                TechnologyFactory.createTechnology()
        );

        when(technologyServicePort.getAllTechnologies(0, 10, true)).thenReturn(expectedTechnologies);

        List<TechnologyResponse> expectedResponses = Arrays.asList(
                TechnologyFactory.toTechnologyResponse(expectedTechnologies.get(0)),
                TechnologyFactory.toTechnologyResponse(expectedTechnologies.get(1)),
                TechnologyFactory.toTechnologyResponse(expectedTechnologies.get(2))
        );
        when(technologyResponseMapper.toTechnologyResponseList(expectedTechnologies)).thenReturn(expectedResponses);

        // When & Then

        mockMvc.perform(get("/technology/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(expectedResponses.get(0).getName()))
                .andExpect(jsonPath("$[0].description").value(expectedResponses.get(0).getDescription()))
                .andExpect(jsonPath("$[1].name").value(expectedResponses.get(1).getName()))
                .andExpect(jsonPath("$[1].description").value(expectedResponses.get(1).getDescription()))
                .andExpect(jsonPath("$[2].name").value(expectedResponses.get(2).getName()))
                .andExpect(jsonPath("$[2].description").value(expectedResponses.get(2).getDescription()));
    }


    private static Stream<Arguments> provideTechnologyRequests() {
        return Stream.of(
                Arguments.of(generateRequest("{\"name\":\"Javaa\",\"description\":\"Java programming language\"}", HttpStatus.CREATED)),

                Arguments.of(generateRequest("{\"name\":\"\",\"description\":\"Java programming language\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Java\",\"description\":\"\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"A\",\"description\":\"A\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean.\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean.\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"\",\"description\":\"Java programming language\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Java\",\"description\":\"\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"A\",\"description\":\"A\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean.\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean.\"}", HttpStatus.BAD_REQUEST))
        );
    }

    private static RequestCase generateRequest(String requestBody, HttpStatus expectedStatus) {
        return new RequestCase(requestBody, expectedStatus);
    }

}