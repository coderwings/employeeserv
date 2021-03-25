package com.paypal.bfs.test.employeeserv.entity;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import lombok.experimental.UtilityClass;

import java.util.Optional;

@UtilityClass
public class TestData {

    public static Optional<Employee> getEmployee() {
        Employee employee = new Employee();
        employee.setId(54);
        employee.setFirstName("fname");
        employee.setLastName("lname");
        employee.setDateOfBirth("11-MAR-88");
        employee.setAddress(getAddress());
        return Optional.of(employee);
    }

    public static EmployeeEntity getEmployeeEntity() {
        return EmployeeEntity.builder().id(54)
                .firstName("fname").lastName("lname").build();
    }

    private static Address getAddress() {
        Address address = new Address();
        address.setLine1("line1");
        address.setLine2("line2");
        address.setCity("city");
        address.setState("state");
        address.setCountry("country");
        address.setZipCode(88432);
        return address;
    }
}
