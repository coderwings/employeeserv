package com.paypal.bfs.test.employeeserv.entity.impl;

import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.entity.TestData.getEmployee;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeResourceImplTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    EmployeeService employeeService;

    @Test
    @SneakyThrows
    public void employeeGetById_isOk() {

        when(employeeService.retrieveById(any())).thenReturn(getEmployee());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/bfs/employees/54"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(54))
                .andExpect(jsonPath("first_name").value("fname"))
                .andExpect(jsonPath("last_name").value("lname"));

        verify(employeeService).retrieveById(any());
        verifyNoMoreInteractions(employeeService);
    }

    @Test
    @SneakyThrows
    public void employeeGetById_BadRequest() {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/bfs/employees/abc"))
                .andExpect(status().isBadRequest());

        verifyNoMoreInteractions(employeeService);
    }

    @Test
    @SneakyThrows
    public void employeeGetById_NotFound() {

        when(employeeService.retrieveById(any())).thenReturn(Optional.empty());

        mockMvc.perform(
                MockMvcRequestBuilders.get("/v1/bfs/employees/22"))
                .andExpect(status().isNotFound());

        verify(employeeService).retrieveById(any());
        verifyNoMoreInteractions(employeeService);
    }
}
