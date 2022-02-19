package jp.co.axa.apidemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class EmployeeModel implements Serializable {

    private Long id;

    private String name;

    private Integer salary;

    private String department;
}
