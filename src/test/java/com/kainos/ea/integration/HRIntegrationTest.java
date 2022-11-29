package com.kainos.ea.integration;

import com.kainos.ea.WebServiceApplication;
import com.kainos.ea.WebServiceConfiguration;
import com.kainos.ea.model.Employee;
import com.kainos.ea.model.EmployeeRequest;
import io.dropwizard.configuration.ResourceConfigurationSourceProvider;
import io.dropwizard.testing.junit5.DropwizardAppExtension;
import io.dropwizard.testing.junit5.DropwizardExtensionsSupport;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@ExtendWith(DropwizardExtensionsSupport.class)
public class HRIntegrationTest {

    static final DropwizardAppExtension<WebServiceConfiguration> APP = new DropwizardAppExtension<>(
            WebServiceApplication.class, null,
            new ResourceConfigurationSourceProvider()
    );


    EmployeeRequest employeeRequest;

    @BeforeEach
    public void beforeEach() {
        employeeRequest = new EmployeeRequest(
                30000,
                "Integration",
                "Test",
                "tbloggs@email.com",
                "1 Main Street",
                "Main Road",
                "Belfast",
                "Antrim",
                "BT99BT",
                "Northern Ireland",
                "12345678901",
                "12345678",
                "AA1A11AA"
        );
    }
    @Test
    void getEmployees_shouldReturnListOfEmployees() {
        List<Employee> response = APP.client().target("http://localhost:8080/hr/employee")
                .request()
                .get(List.class);

        Assertions.assertTrue(response.size() > 0);
    }

    @Test
    void postEmployee_shouldReturnIdOfEmployee() {

        int response = APP.client().target("http://localhost:8080/hr/employee")
                .request()
                .post(Entity.entity(employeeRequest, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Integer.class);

        Assertions.assertNotNull(response);
    }

    /*
    Integration Test Exercise 1

    Write an integration test for the GET /hr/employee/{id} endpoint

    Create an employee by calling the POST /hr/employee endpoint

    Get the ID from the response of the above call

    Call the GET /hr/employee/{id} endpoint

    Expect the response values to be the same and the employee created above

    This should pass without code changes
     */

    @Test
    void getEmployees_shouldReturnEmployeeJustCreated() {
        int postRequestEmployeeId = APP.client().target("http://localhost:8080/hr/employee")
                .request()
                .post(Entity.entity(employeeRequest, MediaType.APPLICATION_JSON_TYPE))
                .readEntity(Integer.class);
        Employee employeeFromPost = APP.client().target("http://localhost:8080/hr/employee/" + postRequestEmployeeId)
                .request()
                .get(Employee.class);

        Assertions.assertEquals(postRequestEmployeeId,employeeFromPost.getEmployeeId() );
    }
    /*
    Integration Test Exercise 2

    Write an integration test for the POST /hr/employee method

    Call the POST /hr/employee endpoint with an employee with salary of 10000

    Expect a response with error code 400 to be returned

    This should fail, make code changes to make this test pass
     */
    @Test
    void postEmployee_withResponse400ToBeReturned_whenSalaryTooLow() {
        employeeRequest.setSalary(10000);
        Response response = APP.client().target("http://localhost:8080/hr/employee")
                .request()
                .post(Entity.entity(employeeRequest, MediaType.APPLICATION_JSON_TYPE));

        Assertions.assertEquals(400, response.getStatus());
    }
    /*
    Integration Test Exercise 3

    Write an integration test for the POST /hr/employee method

    Call the POST /hr/employee endpoint with an employee with bank number of 123

    Expect a response with error code 400 to be returned

    This should fail, make code changes to make this test pass
     */

    @Test
    void postEmployee_withResponse400ToBeReturned_whenBankNumberTooShort() {
        employeeRequest.setBankNo("123");
        Response response = APP.client().target("http://localhost:8080/hr/employee")
                .request()
                .post(Entity.entity(employeeRequest, MediaType.APPLICATION_JSON_TYPE));

        Assertions.assertEquals(400, response.getStatus());
    }
    /*
    Integration Test Exercise 4

    Write an integration test for the GET /hr/employee/{id} endpoint

    Call the GET /hr/employee/{id} endpoint will an id of 123456

    Expect a response with error code 400 to be returned

    This should fail, make code changes to make this test pass
     */
    @Test
    void getEmployees_shouldReturnResponseCode400IfEmployeeIsNull() {
        int testEndpoint = 123456;
        Response employeeFromPostRes = APP.client().target("http://localhost:8080/hr/employee/" + testEndpoint)
                .request()
                .get(Response.class);

        Assertions.assertEquals(400,employeeFromPostRes.getStatus() );
    }
}
