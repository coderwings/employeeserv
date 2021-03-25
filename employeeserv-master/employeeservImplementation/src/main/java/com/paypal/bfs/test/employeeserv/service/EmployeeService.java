package com.paypal.bfs.test.employeeserv.service;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeMapper employeeMapper;

    public Optional<Employee> retrieveById(Integer id) {
        log.info("Retrieving employee with id {}", id);
        return employeeMapper.mapToPojo(employeeRepository.findById(id));
    }

    public Employee create(Employee employee) {

        log.info("Creating employee : {}", employee);

        EmployeeEntity employeeEntity = employeeMapper.mapToEntity(employee);
        employeeEntity = employeeRepository.save(employeeEntity);

        employee.setId(employeeEntity.getId());

        return employee;
    }
}
