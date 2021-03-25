package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import com.paypal.bfs.test.employeeserv.service.EmployeeService;
import com.paypal.bfs.test.employeeserv.service.EmployeeValidator;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.NumberUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang.math.NumberUtils.isNumber;

/**
 * Implementation class for employee resource.
 */
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeValidator employeeValidator;

    @Override
    public ResponseEntity<Employee> employeeGetById(String id) {

        if (!isNumber(id)) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<Employee> employee = employeeService.retrieveById(Integer.valueOf(id));
        return employee.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(()
                -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Override
    public ResponseEntity createEmployee(Employee employee) {
        Optional<List<String>> errors = employeeValidator.validate(employee);
        if (errors.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors.get());
        }

        try {
            employee = employeeService.create(employee);
        } catch (Exception exception) {
            System.out.println(exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Error :" + exception);
        }
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

}
