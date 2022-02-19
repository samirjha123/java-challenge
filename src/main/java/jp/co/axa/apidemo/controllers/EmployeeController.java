package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.model.EmployeeModel;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

    @Autowired
    private EmployeeService<EmployeeModel> employeeService;

    /**
     * Get list of employee
     * @return
     */
    @GetMapping("/employees")
    public ResponseEntity<List<EmployeeModel>> getEmployees() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(employeeService.retrieveEmployees());
    }

    /**
     * Get employee
     * @param employeeId
     * @return
     */
    @GetMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeModel> getEmployee(@PathVariable(name="employeeId")Long employeeId) {
        EmployeeModel emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(emp);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Save Employee
     * @param employeeModel
     */
    @PostMapping("/employees")
    public ResponseEntity<EmployeeModel> saveEmployee(EmployeeModel employeeModel) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(employeeService.saveEmployee(employeeModel));
    }

    /**
     * Delete employee
     * @param employeeId
     */
    @DeleteMapping("/employees/{employeeId}")
    public ResponseEntity deleteEmployee(@PathVariable(name="employeeId")Long employeeId) {
       employeeService.deleteEmployee(employeeId);
       return  ResponseEntity.accepted().build();
    }

    /**
     * Update employee
     * @param employeeModel
     * @param employeeId
     */
    @PutMapping("/employees/{employeeId}")
    public ResponseEntity<EmployeeModel> updateEmployee(@RequestBody EmployeeModel employeeModel,
                               @PathVariable(name="employeeId")Long employeeId) {
        EmployeeModel emp = employeeService.getEmployee(employeeId);
        if(emp != null){
            return ResponseEntity.status(HttpStatus.ACCEPTED)
                    .body(employeeService.updateEmployee(employeeModel));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
