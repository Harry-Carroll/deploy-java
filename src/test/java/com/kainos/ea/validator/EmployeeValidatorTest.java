package com.kainos.ea.validator;

import com.kainos.ea.exception.BankNumberLengthException;
import com.kainos.ea.exception.NinLengthException;
import com.kainos.ea.exception.SalaryTooLowException;
import com.kainos.ea.model.EmployeeRequest;
import com.kainos.ea.service.EmployeeService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeValidatorTest {


    EmployeeValidator employeeValidator = new EmployeeValidator();
    EmployeeRequest employeeRequest;
    @BeforeEach
    public void getValidEmployee() {
        employeeRequest = new EmployeeRequest(
                30000,
                "Tim",
                "Bloggs",
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
    public void isValidEmployee_shouldReturnTrue_whenValidEmployee() throws SalaryTooLowException, BankNumberLengthException, NinLengthException {
        EmployeeRequest employeeRequest = new EmployeeRequest(
                30000,
                "Tim",
                "Bloggs",
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

        assertTrue(employeeValidator.isValidEmployee(employeeRequest));
    }

    @Test
    public void isValidEmployee_shouldThrowSalaryTooLowException_whenSalaryTooLow() {
        employeeRequest.setSalary(10000);
        assertThrows(SalaryTooLowException.class,
                () -> employeeValidator.isValidEmployee(employeeRequest));
    }

    /*
    Unit Test Exercise 1

    Write a unit test for the isValidEmployee method

    When the bank number is less than 8 characters

    Expect BankNumberLengthException to be thrown

    This should pass without code changes
     */
    @Test
    public void isValidEmployee_shouldReturnTrue_whenValidBankNumber() throws BankNumberLengthException, SalaryTooLowException, NinLengthException {
        assertTrue(employeeValidator.isValidEmployee(employeeRequest));
    }
    /*
    Unit Test Exercise 2

    Write a unit test for the isValidEmployee method

    When the bank number is more than 8 characters

    Expect BankNumberLengthException to be thrown

    This should pass without code changes
     */
    @Test
    public void isValidEmployee_shouldThrowBankNumberLengthException_whenTooManyDigitsInBank() {

        employeeRequest.setBankNo("123456789");

        assertThrows(BankNumberLengthException.class,
                () -> employeeValidator.isValidEmployee(employeeRequest));
    }
    /*
    Unit Test Exercise 3

    Write a unit test for the isValidEmployee method

    When the first name more than 50 characters

    Expect false to be returned

    This should fail, make code changes to make this test pass
     */
    @Test
    public void isValidEmployee_shouldReturnFalse_whenFirstnameIsTooLong() throws SalaryTooLowException, BankNumberLengthException, NinLengthException{

        employeeRequest.setFname("TimTimTimTimTimTimTimTimTimTim" +
                "TimTimTimTimTimTimTimTimTimTimTimTimTimTimTimTimTimTimTimTim");

        assertFalse(employeeValidator.isValidEmployee(employeeRequest));
    }
    /*
    Unit Test Exercise 4

    Write a unit test for the isValidEmployee method

    When the last name than 50 characters

    Expect false to be returned

    This should fail, make code changes to make this test pass
     */
    @Test
    public void isValidEmployee_shouldReturnFalse_whenLastnameIsTooLong() throws SalaryTooLowException, BankNumberLengthException, NinLengthException{
        employeeRequest.setLname("BloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggsBloggs");
        boolean isAssertValid = employeeValidator.isValidEmployee(employeeRequest);
        assertFalse(isAssertValid);
    }
    /*
    Unit Test Exercise 5

    Write a unit test for the isValidEmployee method

    When the nin is more than 8 characters

    Expect NinLengthException to be thrown

    This should fail, make code changes to make this test pass
     */
    @Test
    public void isValidEmployee_shouldThrowNinLengthException_whenNinIsTooLong() {
        employeeRequest.setNin("AA1A11AAA");

        assertThrows(NinLengthException.class,
                () -> employeeValidator.isValidEmployee(employeeRequest));
    }
    /*
    Unit Test Exercise 6

    Write a unit test for the isValidEmployee method

    When the nin is less than 8 characters

    Expect NinLengthException to be thrown

    This should fail, make code changes to make this test pass
     */
    @Test
    public void isValidEmployee_shouldThrowNinLengthException_whenNinIsTooShort() {
        employeeRequest.setNin("AA1A11A");
        assertThrows(NinLengthException.class,
                () -> employeeValidator.isValidEmployee(employeeRequest));
    }
}