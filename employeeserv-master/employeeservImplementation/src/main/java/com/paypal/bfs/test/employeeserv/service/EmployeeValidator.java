package com.paypal.bfs.test.employeeserv.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.repository.EmployeeRepository;
import org.everit.json.schema.Schema;
import org.everit.json.schema.ValidationException;
import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeValidator {

    @Autowired
    EmployeeRepository employeeRepository;

    public Optional<List<String>> validate(Employee employee) {

        List<String> errors = new ArrayList<>();

        if (employee.getId() != null && employeeRepository.existsById(employee.getId())) {
            return Optional.of(Collections.singletonList("Employee already exists"));
        }

        validateObject(employee, "/v1/schema/employee.json", errors);

        validateObject(employee.getAddress(), "/v1/schema/address.json", errors);

        return errors.size() > 0 ? Optional.of(errors) : Optional.empty();

    }

    private <T> void validateObject(T object, String schemaPath, List<String> errors) {
        try {
            if (object != null) {
                JSONObject jsonSchema = new JSONObject(
                        new JSONTokener(EmployeeValidator.class.getResourceAsStream(schemaPath)));
                Schema schema = SchemaLoader.load(jsonSchema);

                JSONObject employeeObject = new JSONObject(
                        new JSONTokener(new ObjectMapper().writeValueAsString(object)));
                schema.validate(employeeObject);
            }
        } catch (ValidationException exception) {
            System.out.println(exception.getAllMessages());
            errors.addAll(exception.getAllMessages());
        } catch (Exception exception) {
            System.out.println(exception.getLocalizedMessage());
            errors.add(exception.getMessage());
        }
    }
}
