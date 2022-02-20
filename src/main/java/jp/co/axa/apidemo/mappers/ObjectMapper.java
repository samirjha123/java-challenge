package jp.co.axa.apidemo.mappers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.model.EmployeeModel;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

import java.io.Serializable;

@Mapper
public interface ObjectMapper extends Serializable {

    ObjectMapper OBJECT_MAPPER = Mappers.getMapper(ObjectMapper.class);

    Employee modelToEntity(EmployeeModel employeeModel);

    EmployeeModel entityToModel(Employee employee);
}
