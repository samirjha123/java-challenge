package jp.co.axa.apidemo.services.impl;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.mappers.ObjectMapper;
import jp.co.axa.apidemo.model.EmployeeModel;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService<EmployeeModel> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * get list of employee details
     * @return
     */
    public List<EmployeeModel> retrieveEmployees() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream().map(employee -> ObjectMapper.OBJECT_MAPPER.entityToModel(employee)).collect(Collectors
                .toCollection(ArrayList::new));

    }

    /**
     * get employee details by employee id
     * @param employeeId
     * @return
     */
    public EmployeeModel getEmployee(Long employeeId) {
        //get from cache
        EmployeeModel result = (EmployeeModel) redisTemplate.opsForValue().get(employeeId);
        if (result == null) {
            Optional<Employee> optEmp = employeeRepository.findById(employeeId);
            if (optEmp.isPresent()) {
                result = ObjectMapper.OBJECT_MAPPER.entityToModel(optEmp.get());
                //store into cache
                redisTemplate.opsForValue().set(result.getId(), result, 1, TimeUnit.MINUTES);
            }
        }
        return result;
    }

    /**
     * save employee details
     * @param employeeModel
     */
    public EmployeeModel saveEmployee(EmployeeModel employeeModel) {
        Employee employee = ObjectMapper.OBJECT_MAPPER.modelToEntity(employeeModel);
        EmployeeModel result = ObjectMapper.OBJECT_MAPPER.entityToModel(employeeRepository.save(employee));
        //store into cache
        redisTemplate.opsForValue().set(result.getId(), result, 1, TimeUnit.MINUTES);
        return result;
    }

    /**
     * delete employee details
     * @param employeeId
     */
    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
        //delete from cache
        redisTemplate.delete(employeeId);
    }

    /**
     * update employee details
     * @param employeeModel
     */
    public EmployeeModel updateEmployee(EmployeeModel employeeModel) {
        Employee employee = ObjectMapper.OBJECT_MAPPER.modelToEntity(employeeModel);
        EmployeeModel result = ObjectMapper.OBJECT_MAPPER.entityToModel(employeeRepository.save(employee));
        //store into cache
        redisTemplate.opsForValue().set(result.getId(), result, 1, TimeUnit.MINUTES);
        return result;
    }
}