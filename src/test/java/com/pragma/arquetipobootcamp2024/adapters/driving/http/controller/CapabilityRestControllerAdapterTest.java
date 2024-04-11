package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ICapabilityRequestMapper;
import com.pragma.arquetipobootcamp2024.domain.api.ICapabilityServicePort;
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
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CapabilityRestControllerAdapterTest {

    @Mock
    private ICapabilityServicePort capabilityServicePort;

    @Mock
    private ICapabilityRequestMapper capabilityRequestMapper;

    @InjectMocks
    private CapabilityRestControllerAdapter capabilityRestControllerAdapter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(capabilityRestControllerAdapter).build();
    }

    @ParameterizedTest
    @DisplayName("Test adding a capability with different request cases")
    @MethodSource("provideCapabilityRequests")
    void testAddCapability(RequestCase  testCase) throws Exception {
        // Given
        String requestBody = testCase.getRequestBody();
        HttpStatus expectedStatus = testCase.getExpectedStatus();

        // When & Then
        MvcResult mvcResult = mockMvc.perform(post("/capability/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();

        if (expectedStatus == HttpStatus.CREATED) {
            verify(capabilityServicePort).saveCapability(any());
        }
        else if (expectedStatus == HttpStatus.BAD_REQUEST) {
            Exception resolvedException = mvcResult.getResolvedException();
            assertTrue(resolvedException instanceof MethodArgumentNotValidException);
        }
    }


    private static Stream<Arguments> provideCapabilityRequests() {
        return Stream.of(
                Arguments.of(generateRequest("{\"name\":\"Java\",\"description\":\"Java programming language\",\"technologiesList\":[{\"id\":1},{\"id\":2},{\"id\":3}]}", HttpStatus.CREATED)),

                Arguments.of(generateRequest("{\"name\":\"Programación con Juaness\",\"description\":\"Capacidad para crear aplicaciones web interactivas\"," +
                        "\"technologiesList\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4},{\"id\":5},{\"id\":20},{\"id\":14},{\"id\":13},{\"id\":12},{\"id\":21},{\"id\":15}" +
                        ",{\"id\":16},{\"id\":17},{\"id\":18},{\"id\":19},{\"id\":20},{\"id\":14},{\"id\":13},{\"id\":12},{\"id\":21},{\"id\":15},{\"id\":16},{\"id\":17},{\"id\":18}" +
                        ",{\"id\":19},{\"id\":20},{\"id\":14},{\"id\":13},{\"id\":12},{\"id\":21}]}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"\",\"description\":\"Java programming language\",\"technologiesList\":[]}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Java\",\"description\":\"\",\"technologiesList\":[{\"id\":1},{\"id\":2},{\"id\":3}]}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"description\":\"\",\"technologiesList\":[]}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Java\",\"technologiesList\":[{\"id\":1},{\"id\":2},{\"id\":3}]}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Java\",\"description\":\"Java programming language\",\"technologiesList\":null}", HttpStatus.BAD_REQUEST))
        );
    }

    private static RequestCase generateRequest(String requestBody, HttpStatus expectedStatus) {
        return new RequestCase(requestBody, expectedStatus);
    }
}