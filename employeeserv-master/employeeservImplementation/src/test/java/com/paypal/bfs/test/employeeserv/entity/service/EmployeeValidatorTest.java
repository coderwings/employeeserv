package com.paypal.bfs.test.employeeserv.entity.service;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.paypal.bfs.test.employeeserv.entity.TestData.getEmployee;
import static com.paypal.bfs.test.employeeserv.entity.TestData.getEmployeeEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeValidatorTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeValidator employeeValidator;

    @Test
    public void validateTest_success() {

        when(employeeRepository.existsById(any())).thenReturn(false);

        Optional<List<String>> errors = employeeValidator.validate(getEmployee().get());

        Assertions.assertFalse(errors.isPresent());

        verify(employeeRepository).existsById(any());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    public void validateTest_alreadyExists_error() {

        when(employeeRepository.existsById(any())).thenReturn(true);

        Optional<List<String>> errors = employeeValidator.validate(getEmployee().get());

        assertTrue(errors.isPresent());
        assertEquals("Employee already exists", errors.get().get(0));

        verify(employeeRepository).existsById(any());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    public void validateTest_employeeMandatoryMissing_error() {

        when(employeeRepository.existsById(any())).thenReturn(false);

        Employee employee = getEmployee().get();
        employee.setLastName(null);
        employee.setDateOfBirth(null);

        Optional<List<String>> errors = employeeValidator.validate(employee);

        assertTrue(errors.isPresent());
        assertEquals("#: required key [last_name] not found", errors.get().get(0));
        assertEquals("#: required key [date_of_birth] not found", errors.get().get(1));

        verify(employeeRepository).existsById(any());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    public void validateTest_employeeFirstNameGreaterThan255_error() {

        when(employeeRepository.existsById(any())).thenReturn(false);

        Employee employee = getEmployee().get();
        employee.setFirstName("employee.json has only name, and id elements. " +
                "Please add date of birth and address elements to the Employee resource. " +
                "Address will have line1, line2, city, state, country and zip_code elements. " +
                "line2 is an optional element." +
                "Add one more operation in EmployeeResource to create an employee");

        Optional<List<String>> errors = employeeValidator.validate(employee);

        assertTrue(errors.isPresent());
        assertEquals("#/first_name: expected maxLength: 255, actual: 287", errors.get().get(0));

        verify(employeeRepository).existsById(any());
        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    public void validateTest_addressMandatoryMissing_error() {

        when(employeeRepository.existsById(any())).thenReturn(false);

        Employee employee = getEmployee().get();
        employee.setAddress(new Address());

        Optional<List<String>> errors = employeeValidator.validate(employee);

        assertTrue(errors.isPresent());
        assertEquals("#: required key [line1] not found", errors.get().get(0));
        assertEquals("#: required key [city] not found", errors.get().get(1));
        assertEquals("#: required key [state] not found", errors.get().get(2));
        assertEquals("#: required key [country] not found", errors.get().get(3));
        assertEquals("#: required key [zip_code] not found", errors.get().get(4));

        verify(employeeRepository).existsById(any());
        verifyNoMoreInteractions(employeeRepository);
    }


}
