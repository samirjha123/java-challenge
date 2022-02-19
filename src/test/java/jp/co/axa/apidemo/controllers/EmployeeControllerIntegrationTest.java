package jp.co.axa.apidemo.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.caryyu.spring.embedded.redisserver.RedisServerConfiguration;
import jp.co.axa.apidemo.model.EmployeeModel;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private static ObjectMapper mapper = new ObjectMapper();

    @TestConfiguration
    static class RedisTestConfiguration {

        @Bean
        public RedisServerConfiguration redisServerConfiguration() {
            return new RedisServerConfiguration();
        }
    }

    @Before
    public void before() throws Exception {

        EmployeeModel employeeModel1 = new EmployeeModel(null, "samir jha", 23356, "geography");
        mockMvc.perform(post("/api/v1/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(mapper.writeValueAsString(employeeModel1))
                .accept(MediaType.APPLICATION_JSON));
    }

    @Test
    @WithMockUser(username="spring")
    public void testSaveEmployee() throws Exception {

        EmployeeModel employeeModel = new EmployeeModel(null, "samir kumar", 23358, "physics");
        mockMvc.perform(post("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(employeeModel))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser(username="spring")
    public void testUpdateEmployee() throws Exception {

        EmployeeModel employeeModel = new EmployeeModel(null, "ddd", 23359, "geography");

        mockMvc.perform(put("/api/v1/employees/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(employeeModel))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

    @Test
    @WithMockUser(username="spring")
    public void testUpdateNotExistingEmployee() throws Exception {

        EmployeeModel employeeModel = new EmployeeModel(null, "ddd", 23359, "geography");

        mockMvc.perform(put("/api/v1/employees/100")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(mapper.writeValueAsString(employeeModel))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="spring")
    public void testGetEmployee() throws Exception {

        mockMvc.perform(get("/api/v1/employees/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="spring")
    public void testGetNotExistingEmployee() throws Exception {

        mockMvc.perform(get("/api/v1/employees/200")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username="spring")
    public void testRetrieveEmployees() throws Exception {

        mockMvc.perform(get("/api/v1/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username="spring")
    public void testDeleteEmployee() throws Exception {

        mockMvc.perform(delete("/api/v1/employees/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted());
    }

}
