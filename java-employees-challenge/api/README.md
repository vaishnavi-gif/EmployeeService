**Overview**
This API allows you to manage employee data such as retrieving employee information, creating new employees, and performing searches based on employee names. 


#### In this assessment i have tasked with filling out the functionality of different methods that will be listed further down.

These methods will require some level of API interactions with Mock Employee API at [http://localhost:8112/api/v1/employee.]()

Please keep the following in mind when doing this assessment:
* clean coding practices
* test driven development
* logging
* scalability

### Endpoints to implement
The API exposes the following endpoints:

_GET /employee - Get all employees._
getAllEmployees()
    output - list of employees
    description - this should return all employees

_GET /employee/{id} - Get an employee by their ID._
getEmployeeById(...)

    path input - employee ID
    output - employee
    description - this should return a single employee

_POST /employee - Create a new employee._
createEmployee(...)

    body input - attributes necessary to create an employee
    output - employee
    description - this should return a single employee, if created, otherwise error

_GET /employee/search/{searchString} - Search employees by name._
getEmployeesByNameSearch(...)

    path input - name fragment
    output - list of employees
    description - this should return all employees whose name contains or matches the string input provided


_DELETE /employee/{id} - Delete an employee by ID._
deleteEmployeeById(...)

    path input - employee ID
    output - name of the employee
    description - this should delete the employee with specified id given, otherwise error


_GET /employee/highestSalary - Get the highest salary of employees._
getHighestSalaryOfEmployees()

    output - integer of the highest salary
    description - this should return a single integer indicating the highest salary of amongst all employees

_GET /employee/topTenHighestEarningEmployeeNames - Get the top 10 highest earning employee names._
getTop10HighestEarningEmployeeNames()

    output - list of employees
    description - this should return a list of the top 10 employees based off of their salaries


Base URL:  http://localhost:8111/api/v1

Before you can run the API, make sure you have the following installed:

**Java 17+:** This project uses Java version 17 or above. It is highly recommended to use the appropriate JDK version that matches the project requirements.
Maven: For building the project.
IDE: (Optional) You can use an IDE like IntelliJ IDEA, Eclipse, or Visual Studio Code with Java support to work with this project.

If you're using an IDE like IntelliJ IDEA or Eclipse, you can also run the unit tests directly from the IDE:

IntelliJ IDEA:

Open the project in IntelliJ.
Navigate to the src/test/java folder and right-click on the test class or test method.
Select Run 'TestClassName' to run the test(s).
Eclipse:

Open the project in Eclipse.
Right-click on the test class or method in the src/test/java folder.
Select Run As > JUnit Test.

_See `com.reliaquest.api.controller.IEmployeeController` for details._

**Swagger** Documentation attached in specs folder.













### Testing
Please include proper integration and/or unit tests.



### External endpoints from base url
#### This section will outline all available endpoints and their request and response models from http://localhost:8112/api/v1/employee

