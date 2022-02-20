package jp.co.axa.apidemo.service;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.model.EmployeeModel;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.impl.EmployeeServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EmployeeServiceTest {

    @InjectMocks
    EmployeeServiceImpl employeeService;

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    RedisTemplate redisTemplate;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRetrieveEmployees(){
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(null, "samir jha", new BigDecimal(23356), "geography"));
        employees.add(new Employee(null, "samir k jha", new BigDecimal(23357), "chemistry"));

        when(employeeRepository.findAll()).thenReturn(employees);

        List<EmployeeModel> empList = employeeService.retrieveEmployees();

        assertEquals(2, empList.size());
        verify(employeeRepository, times(1)).findAll();
    }

    @Test
    public void testGetEmployees(){
        Employee emp = new Employee(null, "samir jha", new BigDecimal(23356), "geography");
        when(employeeRepository.findById(anyLong())).thenReturn(java.util.Optional.of(emp));
        when(redisTemplate.opsForValue()).thenReturn(null);

        EmployeeModel result = employeeService.getEmployee(1L);

        assertEquals("geography", result.getDepartment());
        verify(employeeRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testSaveEmployees(){
        Employee emp = new Employee(10L, "samir jha", new BigDecimal(23356), "geography");
        when(employeeRepository.save(any(Employee.class))).thenReturn(emp);
        when(redisTemplate.opsForValue()).thenReturn(null);

        EmployeeModel empmdl = new EmployeeModel(10L, "samir jha", new BigDecimal(23356), "geography");
        EmployeeModel result = employeeService.saveEmployee(empmdl);

        assertEquals("geography", result.getDepartment());
        verify(employeeRepository, times(1)).save(any(Employee.class));

    }

    @Test
    public void testUpdateEmployees(){
        Employee emp = new Employee(10L, "samir jha", new BigDecimal(23356), "geography");
        when(employeeRepository.save(any(Employee.class))).thenReturn(emp);
        when(redisTemplate.opsForValue()).thenReturn(null);

        EmployeeModel empmdl = new EmployeeModel(10L, "samir jha", new BigDecimal(23356), "geography");
        EmployeeModel result = employeeService.updateEmployee(empmdl);

        assertEquals("geography", result.getDepartment());
        verify(employeeRepository, times(1)).save(any(Employee.class));

    }

    @Test
    public void testDeleteEmployees(){
        employeeService.deleteEmployee(10L);
        verify(redisTemplate, times(1)).delete(anyLong());
        verify(employeeRepository, times(1)).deleteById(anyLong());

    }
}
