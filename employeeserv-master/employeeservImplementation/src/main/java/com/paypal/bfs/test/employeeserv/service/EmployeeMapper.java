package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeMapper {

    public Optional<Employee> mapToPojo(Optional<EmployeeEntity> employeeEntity) {

        if (!employeeEntity.isPresent()) {
            return Optional.empty();
        }
        Employee employee = new Employee();
        employee.setId(employeeEntity.get().getId());
        employee.setFirstName(employeeEntity.get().getFirstName());
        employee.setLastName(employeeEntity.get().getLastName());
        employee.setDateOfBirth(employeeEntity.get().getDateOfBirth());
        employee.setAddress(getAddressPojo(employeeEntity.get().getAddress()));
        return Optional.of(employee);
    }

    private Address getAddressPojo(AddressEntity addressEntity) {
        Address address = new Address();
        if (addressEntity != null) {
            address.setLine1(addressEntity.getLine1());
            address.setLine2(addressEntity.getLine2());
            address.setCity(addressEntity.getCity());
            address.setState(addressEntity.getState());
            address.setCountry(addressEntity.getCountry());
            address.setZipCode(addressEntity.getZipCode());
        }
        return address;
    }

    public EmployeeEntity mapToEntity(Employee employee) {

        return EmployeeEntity.builder().id(employee.getId())
                .firstName(employee.getFirstName()).lastName(employee.getLastName())
                .dateOfBirth(employee.getDateOfBirth())
                .address(employee.getAddress() != null ? getAddressEntity(employee.getAddress()) : null)
                .build();
    }

    private AddressEntity getAddressEntity(Address address) {
        return AddressEntity.builder().line1(address.getLine1()).line2(address.getLine2())
                .city(address.getCity()).country(address.getCountry()).state(address.getState())
                .zipCode(address.getZipCode()).build();
    }
}
