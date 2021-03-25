package com.paypal.bfs.test.employeeserv.entity.service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeMapper;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.entity.TestData.getEmployee;
import static com.paypal.bfs.test.employeeserv.entity.TestData.getEmployeeEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    @Test
    public void retrieveByIdTest_found_success() {

        when(employeeRepository.findById(any())).thenReturn(Optional.of(getEmployeeEntity()));
        when(employeeMapper.mapToPojo(any())).thenReturn(getEmployee());

        Optional<Employee> employee = employeeService.retrieveById(1);

        assertEquals("fname", employee.get().getFirstName());
        assertEquals("lname", employee.get().getLastName());

        verify(employeeRepository).findById(any());
        verify(employeeMapper).mapToPojo(any());
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

    @Test
    public void retrieveByIdTest_notFound() {

        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        when(employeeMapper.mapToPojo(any())).thenReturn(Optional.empty());

        Optional<Employee> employee = employeeService.retrieveById(1);

        Assertions.assertFalse(employee.isPresent());

        verify(employeeRepository).findById(any());
        verify(employeeMapper).mapToPojo(any());
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

    @Test
    public void createTest_success() {
        EmployeeEntity employeeEntity = getEmployeeEntity();

        when(employeeRepository.save(any())).thenReturn(employeeEntity);
        when(employeeMapper.mapToEntity(any())).thenReturn(employeeEntity);

        Employee employee = employeeService.create(getEmployee().get());

        assertEquals(54, employee.getId().intValue());

        verify(employeeRepository).save(any());
        verify(employeeMapper).mapToEntity(any());
        verifyNoMoreInteractions(employeeRepository, employeeMapper);
    }

}
