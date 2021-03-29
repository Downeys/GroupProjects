package com.company.U1M6GroupProject.controllers;

import com.company.U1M6GroupProject.U1M6GroupProjectApplication;
import com.company.U1M6GroupProject.models.Customer;
import com.company.U1M6GroupProject.models.Invoice;
import com.company.U1M6GroupProject.service.ServiceLayer;
import com.company.U1M6GroupProject.viewModels.CustomerViewModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    //private List<Customer> customerList;

    @MockBean
    private ServiceLayer service;

    @Before
    public void setUp(){
        setupServiceLayerMock();
    }

    @Test
    public void shouldAddCustomerAndReturnStatusCreated() throws Exception {
        //arrange
        //The controller should receive a customerViewModel from the user. That customerViewModel will not
        //yet have an id because it has not been added to the database yet. It will also not have a list of
        //invoices because this is a new customer who has not yet been invoiced.
        CustomerViewModel inputForTest = new CustomerViewModel();
        inputForTest.setFirstName("Jerry");
        inputForTest.setLastName("Garcia");
        inputForTest.setEmail("jgarcia@gmail.com");
        inputForTest.setCompany("Grateful Dead");
        inputForTest.setPhone("2813846385");

        CustomerViewModel expectedOutputForTest = new CustomerViewModel();
        expectedOutputForTest.setCustomerId(1);
        expectedOutputForTest.setFirstName("Jerry");
        expectedOutputForTest.setLastName("Garcia");
        expectedOutputForTest.setEmail("jgarcia@gmail.com");
        expectedOutputForTest.setCompany("Grateful Dead");
        expectedOutputForTest.setPhone("2813846385");

        String inputJsonString = mapper.writeValueAsString(inputForTest);
        String expectedOutputJsonString = mapper.writeValueAsString(expectedOutputForTest);

        mockMvc.perform(
                post("/customer")
                .content(inputJsonString)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedOutputJsonString));
    }

    @Test
    public void shouldGetCustomerByIdAndReturnStatusOK() throws Exception{
        String uriForTest1 = "/customer/1";

        CustomerViewModel expectedOutputForTest = new CustomerViewModel();
        expectedOutputForTest.setCustomerId(1);
        expectedOutputForTest.setFirstName("Jerry");
        expectedOutputForTest.setLastName("Garcia");
        expectedOutputForTest.setEmail("jgarcia@gmail.com");
        expectedOutputForTest.setCompany("Grateful Dead");
        expectedOutputForTest.setPhone("2813846385");

        String expectedOutputJsonString = mapper.writeValueAsString(expectedOutputForTest);

        mockMvc.perform(
                get(uriForTest1)
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJsonString));
    }

    @Test
    public void shouldGetAllCustomersAndStatusOk() throws Exception{
        CustomerViewModel outputCustomer1 = new CustomerViewModel();
        outputCustomer1.setCustomerId(1);
        outputCustomer1.setFirstName("Jerry");
        outputCustomer1.setLastName("Garcia");
        outputCustomer1.setEmail("jgarcia@gmail.com");
        outputCustomer1.setCompany("Grateful Dead");
        outputCustomer1.setPhone("2813846385");

        CustomerViewModel outputCustomer2 = new CustomerViewModel();
        outputCustomer2.setCustomerId(2);
        outputCustomer2.setFirstName("John");
        outputCustomer2.setLastName("Cena");
        outputCustomer2.setEmail("jcena@gmail.com");
        outputCustomer2.setCompany("WWE");
        outputCustomer2.setPhone("8196543546");

        List<CustomerViewModel> outputCustomerList = new ArrayList<>();
        outputCustomerList.add(outputCustomer1);
        outputCustomerList.add(outputCustomer2);

        String expectedOutputJsonString = mapper.writeValueAsString(outputCustomerList);

        mockMvc.perform(
                get("/customer")
        )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(expectedOutputJsonString));
    }

    @Test
    public void shouldReturnStatusOkWhenPassedAValidCustomerViewModel() throws Exception{

        CustomerViewModel inputCustomer1 = new CustomerViewModel();
        inputCustomer1.setCustomerId(1);
        inputCustomer1.setFirstName("Jerry");
        inputCustomer1.setLastName("Garcia");
        inputCustomer1.setEmail("jgarcia@gmail.com");
        inputCustomer1.setCompany("Grateful Dead");
        inputCustomer1.setPhone("2813846385");

        String testInputJsonString = mapper.writeValueAsString(inputCustomer1);

        mockMvc.perform(
                put("/customer")
                .content(testInputJsonString)
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void shouldReturnStatusNoContentWhenDeleteEndpointIsCalled() throws Exception{
        String testUri = "/customer/1";

        mockMvc.perform(
                delete(testUri)
        )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnStatusUnprocessableEntityWhenPassedAValidCustomerViewModel() throws Exception{

        //When a request body fails the validation performed by the @Valid annotation in the Customer Controller
        //That will throw a status 422 or Unprocessable Entity exception.

        CustomerViewModel inputCustomer1 = new CustomerViewModel();
        inputCustomer1.setCustomerId(1);
        inputCustomer1.setFirstName("Jerry");
        inputCustomer1.setEmail("jgarcia@gmail.com");
        inputCustomer1.setCompany("Grateful Dead");
        inputCustomer1.setPhone("2813846385");

        String testInputJsonString = mapper.writeValueAsString(inputCustomer1);

        mockMvc.perform(
                put("/customer")
                        .content(testInputJsonString)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());

    }

    private void setupServiceLayerMock() {
        //arrange
        CustomerViewModel inputAddTestCustomer = new CustomerViewModel();
        inputAddTestCustomer.setFirstName("Jerry");
        inputAddTestCustomer.setLastName("Garcia");
        inputAddTestCustomer.setEmail("jgarcia@gmail.com");
        inputAddTestCustomer.setCompany("Grateful Dead");
        inputAddTestCustomer.setPhone("2813846385");

        CustomerViewModel outputCustomer1 = new CustomerViewModel();
        outputCustomer1.setCustomerId(1);
        outputCustomer1.setFirstName("Jerry");
        outputCustomer1.setLastName("Garcia");
        outputCustomer1.setEmail("jgarcia@gmail.com");
        outputCustomer1.setCompany("Grateful Dead");
        outputCustomer1.setPhone("2813846385");

        CustomerViewModel outputCustomer2 = new CustomerViewModel();
        outputCustomer2.setCustomerId(2);
        outputCustomer2.setFirstName("John");
        outputCustomer2.setLastName("Cena");
        outputCustomer2.setEmail("jcena@gmail.com");
        outputCustomer2.setCompany("WWE");
        outputCustomer2.setPhone("8196543546");

        List<CustomerViewModel> outputCustomerList = new ArrayList<>();
        outputCustomerList.add(outputCustomer1);
        outputCustomerList.add(outputCustomer2);

        doReturn(outputCustomer1).when(service).addCustomer(inputAddTestCustomer);
        doReturn(outputCustomer1).when(service).getCustomerById(1);
        doReturn(outputCustomerList).when(service).getAllCustomers();
    }


}