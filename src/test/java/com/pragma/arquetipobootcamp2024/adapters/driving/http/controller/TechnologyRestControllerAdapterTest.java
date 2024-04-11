package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.controller.TechnologyRestControllerAdapter;
import com.pragma.arquetipobootcamp2024.testData.RequestCase;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ITechnologyRequestMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ITechnologyResponseMapper;
import com.pragma.arquetipobootcamp2024.domain.api.ITechnologyServicePort;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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