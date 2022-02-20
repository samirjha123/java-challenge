package jp.co.axa.apidemo.services;

import java.util.List;

public interface EmployeeService <T> {

    List<T> retrieveEmployees();

    T getEmployee(Long employeeId);

    T saveEmployee(T employee);

    void deleteEmployee(Long employeeId);

    T updateEmployee(T employee);
}