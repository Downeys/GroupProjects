package com.company.U1M6GroupProject.controllers;

import com.company.U1M6GroupProject.models.Item;
import com.company.U1M6GroupProject.service.ServiceLayer;
import com.company.U1M6GroupProject.viewModels.CustomerViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private ServiceLayer service;

    @Before
    public void setUp(){
        setupServiceLayerMock();
    }

    @Test
    public void shouldAddItemAndReturnStatusCreated() throws Exception {
        //arrange
        //The controller should receive a customerViewModel from the user. That customerViewModel will not
        //yet have an id because it has not been added to the database yet. It will also not have a list of
        //invoices because this is a new customer who has not yet been invoiced.
        Item inputForTest = new Item();
        inputForTest.setName("Lamp");
        inputForTest.setDescription("silver lamp");
        inputForTest.setDailyRate(new BigDecimal("1.05"));

        Item expectedOutputForTest = new Item();
        expectedOutputForTest.setItemId(1);
        expectedOutputForTest.setName("Lamp");
        expectedOutputForTest.setDescription("silver lamp");
        expectedOutputForTest.setDailyRate(new BigDecimal("1.05"));

        String inputJsonString = mapper.writeValueAsString(inputForTest);
        String expectedOutputJsonString = mapper.writeValueAsString(expectedOutputForTest);

        mockMvc.perform(
                post("/item")
                        .content(inputJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedOutputJsonString));
    }

    @Test
    public void shouldGetItemByIdAndReturnStatusOK() throws Exception{
        String uriForTest1 = "/item/1";

        Item expectedOutputForTest = new Item();
        expectedOutputForTest.setItemId(1);
        expectedOutputForTest.setName("Lamp");
        expectedOutputForTest.setDescription("silver lamp");
        expectedOutputForTest.setDailyRate(new BigDecimal("1.05"));

        String expectedOutputJsonString = mapper.writeValueAsString(expectedOutputForTest);

        mockMvc.perform(
                get(uriForTest1)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJsonString));
    }

    @Test
    public void shouldGetAllItemsAndStatusOk() throws Exception{
        Item outputItem1 = new Item();
        outputItem1.setItemId(1);
        outputItem1.setName("Lamp");
        outputItem1.setDescription("silver lamp");
        outputItem1.setDailyRate(new BigDecimal("1.05"));

        Item outputItem2 = new Item();
        outputItem2.setItemId(2);
        outputItem2.setName("Desk");
        outputItem2.setDescription("brown desk");
        outputItem2.setDailyRate(new BigDecimal("99.99"));

        List<Item> outputItemList = new ArrayList<>();
        outputItemList.add(outputItem1);
        outputItemList.add(outputItem2);

        String expectedOutputJsonString = mapper.writeValueAsString(outputItemList);

        mockMvc.perform(
                get("/item")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJsonString));
    }

    @Test
    public void shouldReturnStatusOkWhenPassedAValidItem() throws Exception{

        //arrange
        Item inputItem1 = new Item();
        inputItem1.setName("Lamp");
        inputItem1.setDescription("silver lamp");
        inputItem1.setDailyRate(new BigDecimal("1.05"));

        String testInputJsonString = mapper.writeValueAsString(inputItem1);

        mockMvc.perform(
                put("/item")
                        .content(testInputJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnStatusNoContentWhenDeleteEndpointIsCalled() throws Exception{
        String testUri = "/item/1";

        mockMvc.perform(
                delete(testUri)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatusUnprocessableEntityWhenPassedAValidItem() throws Exception{

        //When a request body fails the validation performed by the @Valid annotation in the Customer Controller
        //That will throw a status 422 or Unprocessable Entity exception.

        Item outputItem1 = new Item();
        outputItem1.setItemId(1);
        outputItem1.setName("Lamp");
        outputItem1.setDescription("silver lamp");

        String testInputJsonString = mapper.writeValueAsString(outputItem1);

        mockMvc.perform(
                put("/item")
                        .content(testInputJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    private void setupServiceLayerMock(){
        //arrange
        Item inputItemForAddTest = new Item();
        inputItemForAddTest.setName("Lamp");
        inputItemForAddTest.setDescription("silver lamp");
        inputItemForAddTest.setDailyRate(new BigDecimal("1.05"));

        Item outputItem1 = new Item();
        outputItem1.setItemId(1);
        outputItem1.setName("Lamp");
        outputItem1.setDescription("silver lamp");
        outputItem1.setDailyRate(new BigDecimal("1.05"));

        Item outputItem2 = new Item();
        outputItem2.setItemId(2);
        outputItem2.setName("Desk");
        outputItem2.setDescription("brown desk");
        outputItem2.setDailyRate(new BigDecimal("99.99"));

        List<Item> outputItemList = new ArrayList<>();
        outputItemList.add(outputItem1);
        outputItemList.add(outputItem2);

        doReturn(outputItem1).when(service).addItem(inputItemForAddTest);
        doReturn(outputItem1).when(service).getItemById(1);
        doReturn(outputItemList).when(service).getAllItems();
    }

}