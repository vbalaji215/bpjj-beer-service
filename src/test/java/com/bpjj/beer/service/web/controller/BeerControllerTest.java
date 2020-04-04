package com.bpjj.beer.service.web.controller;

import com.bpjj.beer.service.services.BeerService;
import com.bpjj.beer.service.web.model.BeerDto;
import com.bpjj.beer.service.web.model.BeerStyle;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = "${bpjj.uri.scheme}", uriHost = "${bpjj.uri.host}", uriPort = 8000)
@WebMvcTest(BeerController.class)
class BeerControllerTest {

    @MockBean
    BeerService beerService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(get(
                "/api/v1/beer/{beerId}", UUID.randomUUID().toString())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("v1/beer-get",
                        pathParameters(
                            parameterWithName("beerId").description("UUID of desired beer to get.")
                        ),
                        responseFields(
                            fieldWithPath("id").description("Id of Beer").type(UUID.class),
                                fieldWithPath("version").description("Version number").type("String"),
                                fieldWithPath("createdDate").description("Date Created").type(OffsetDateTime.class),
                                fieldWithPath("lastModifiedDate").description("Date Updated").type(OffsetDateTime.class),
                                fieldWithPath("beerName").description("Beer Name").type("String"),
                                fieldWithPath("beerStyle").description("Beer Style").type("String"),
                                fieldWithPath("upc").description("UPC of Beer").type("Number"),
                                fieldWithPath("price").description("Price of Beer").type(BigDecimal.class),
                                fieldWithPath("quantityOnHand").description("Quantity On Hand").type("Number")
                        )
                ));
    }

    @Test
    void saveNewBeer() throws Exception{
        BeerDto beerDto = BeerDto.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.ALE).upc("00234254423")
                .price(new BigDecimal(11.95)).build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        ConstrainedFields fields = new ConstrainedFields((BeerDto.class));

        mockMvc.perform(post(
                "/api/v1/beer/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated())
        .andDo(document("v1/beer-save",
                requestFields(
                        fields.withPath("id").ignored(),
                        fields.withPath("version").ignored(),
                        fields.withPath("createdDate").ignored(),
                        fields.withPath("lastModifiedDate").ignored(),
                        fields.withPath("beerStyle").description("Style of the beer"),
                        fields.withPath("upc").description("Beer UPC").attributes(),
                        fields.withPath("beerName").description("Name of the Beer"),
                        fields.withPath("price").description("Price of the beer"),
                        fields.withPath("quantityOnHand").ignored()
                )));
    }

    @Test
    void updateBeer() throws  Exception {
        BeerDto beerDto = BeerDto.builder()
                .beerName("New Beer")
                .beerStyle(BeerStyle.ALE).upc("0023425442")
                .price(new BigDecimal(11.95)).build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);
        mockMvc.perform(put(
                "/api/v1/beer/"+ UUID.randomUUID().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());
    }

    private static class ConstrainedFields {
        private final ConstraintDescriptions constraintDescriptions;

        ConstrainedFields(Class<?> input){
            this.constraintDescriptions = new ConstraintDescriptions(input);
        }

        private FieldDescriptor withPath(String path){
            return fieldWithPath(path)
                    .attributes(key("constraints")
                            .value(StringUtils.collectionToDelimitedString(
                                    this.constraintDescriptions.descriptionsForProperty(path), ".")));
        }
    }
}