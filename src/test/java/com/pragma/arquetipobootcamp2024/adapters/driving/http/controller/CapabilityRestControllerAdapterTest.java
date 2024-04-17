package com.pragma.arquetipobootcamp2024.adapters.driving.http.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.CapabilityResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.dto.response.TechnologyByCapabilityResponse;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ICapabilityRequestMapper;
import com.pragma.arquetipobootcamp2024.adapters.driving.http.mapper.ICapabilityResponseMapper;
import com.pragma.arquetipobootcamp2024.domain.api.ICapabilityServicePort;
import com.pragma.arquetipobootcamp2024.domain.model.Capability;
import com.pragma.arquetipobootcamp2024.testData.CapabilityFactory;
import com.pragma.arquetipobootcamp2024.testData.RequestCase;

import org.json.JSONArray;
import org.json.JSONObject;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(MockitoExtension.class)
class CapabilityRestControllerAdapterTest {

    @Mock
    private ICapabilityServicePort capabilityServicePort;

    @Mock
    private ICapabilityRequestMapper capabilityRequestMapper;

    @InjectMocks
    private CapabilityRestControllerAdapter capabilityRestControllerAdapter;
    @Mock
    private ICapabilityResponseMapper capabilityResponseMapper;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(capabilityRestControllerAdapter).build();
    }

    @ParameterizedTest
    @DisplayName("Test adding a capability with different request cases")
    @MethodSource("provideCapabilityRequests")
    void testAddCapability(RequestCase testCase) throws Exception {
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
        } else if (expectedStatus == HttpStatus.BAD_REQUEST) {
            Exception resolvedException = mvcResult.getResolvedException();
            assertTrue(resolvedException instanceof MethodArgumentNotValidException);
        }
    }

    @Test
    @DisplayName("Test retrieving all capabilities from the controller")
    void testGetAllCapability() throws Exception {
        // Given
        List<Capability> capabilities = Arrays.asList(
                CapabilityFactory.createCapability(1),
                CapabilityFactory.createCapability(2)
        );
        List<CapabilityResponse> capabilityResponses = CapabilityFactory.toCapabilityResponseList(capabilities);

        when(capabilityServicePort.getAllCapabilities(0, 10, "ASC")).thenReturn(capabilities);
        when(capabilityResponseMapper.toCapabilityResponseList(capabilities)).thenReturn(capabilityResponses);

        // When & Then
        MvcResult result = mockMvc.perform(get("/capability/")
                        .param("page", "0")
                        .param("size", "10")
                        .param("order", "ASC")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()").value(capabilities.size()))
                .andReturn();

        String contentAsString = result.getResponse().getContentAsString();
        JSONArray jsonArray = new JSONArray(contentAsString);

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            CapabilityResponse response = capabilityResponses.get(i);

            // Comparison of capacity properties
            assertEquals(response.getId(), jsonObject.getLong("id"));
            assertEquals(response.getName(), jsonObject.getString("name"));
            assertEquals(response.getDescription(), jsonObject.getString("description"));

            // Comparison of capacity technologies
            JSONArray technologiesArray = jsonObject.getJSONArray("technologiesList");
            List<TechnologyByCapabilityResponse> technologyResponses = response.getTechnologiesList();
            assertEquals(technologyResponses.size(), technologiesArray.length());

            for (int j = 0; j < technologyResponses.size(); j++) {
                JSONObject technologyObject = technologiesArray.getJSONObject(j);
                TechnologyByCapabilityResponse technologyResponse = technologyResponses.get(j);
                assertEquals(technologyResponse.getId(), technologyObject.getLong("id"));
                assertEquals(technologyResponse.getName(), technologyObject.getString("name"));
            }
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