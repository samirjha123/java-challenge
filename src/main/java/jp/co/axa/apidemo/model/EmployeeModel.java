package jp.co.axa.apidemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class EmployeeModel implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal salary;

    private String department;
}
