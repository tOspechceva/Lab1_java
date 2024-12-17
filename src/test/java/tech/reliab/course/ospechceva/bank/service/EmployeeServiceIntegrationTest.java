package tech.reliab.course.ospechceva.bank.service;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;


import tech.reliab.course.ospechceva.bank.OspechcevaLabApplication;
import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.BankOffice;
import tech.reliab.course.ospechceva.bank.entity.Employee;
import tech.reliab.course.ospechceva.bank.model.EmployeeRequest;
import tech.reliab.course.ospechceva.bank.repository.BankRepository;
import tech.reliab.course.ospechceva.bank.repository.BankOfficeRepository;
import tech.reliab.course.ospechceva.bank.service.EmployeeService;
import tech.reliab.course.ospechceva.bank.container.TestContainerConfig;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(classes = OspechcevaLabApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ContextConfiguration(classes = {TestContainerConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeServiceIntegrationTest {

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private BankRepository bankRepository;

    @Autowired
    private BankOfficeRepository bankOfficeRepository;

    private Bank testBank;
    private BankOffice testBankOffice;

    @BeforeEach
    void setUp() {
        testBank = bankRepository.save(new Bank("Test Bank"));
        testBankOffice = bankOfficeRepository.save(new BankOffice("Main Office", "Main Street, 10", testBank, true, 1000, true, true));
    }

    @Test
    void testCreateEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("John Doe");
        request.setBirthDate(LocalDate.of(1990, 1, 1));
        request.setPosition("Manager");
        request.setBankId(testBank.getId());
        request.setRemoteWork(false);
        request.setBankOfficeId(testBankOffice.getId());
        request.setCanIssueLoans(true);
        request.setSalary(5000);

        Employee createdEmployee = employeeService.createEmployee(request);

        Assertions.assertNotNull(createdEmployee.getId(), "ID сотрудника не должен быть null после создания");
        Assertions.assertEquals("John Doe", createdEmployee.getFullName(), "Имя должно совпадать");
        Assertions.assertEquals("Manager", createdEmployee.getPosition(), "Должность должна совпадать");
        Assertions.assertEquals(testBank.getId(), createdEmployee.getBank().getId(), "Банк должен совпадать");
        Assertions.assertEquals(testBankOffice.getId(), createdEmployee.getBankOffice().getId(), "Офис должен совпадать");
        Assertions.assertTrue(createdEmployee.isCanIssueLoans(), "Сотрудник должен иметь право выдавать кредиты");
        Assertions.assertEquals(5000, createdEmployee.getSalary(), "Зарплата должна совпадать");
    }

    @Test
    void testUpdateEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("Jane Smith");
        request.setBirthDate(LocalDate.of(1985, 5, 10));
        request.setPosition("Consultant");
        request.setBankId(testBank.getId());
        request.setRemoteWork(true);
        request.setBankOfficeId(testBankOffice.getId());
        request.setCanIssueLoans(false);
        request.setSalary(3000);

        Employee createdEmployee = employeeService.createEmployee(request);
        Employee updatedEmployee = employeeService.updateEmployee(createdEmployee.getId(), "Jane Johnson");

        Assertions.assertEquals("Jane Johnson", updatedEmployee.getFullName(), "Имя сотрудника должно обновиться");
    }

    @Test
    void testGetEmployeeById() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("Mark Doe");
        request.setBirthDate(LocalDate.of(1988, 2, 2));
        request.setPosition("Clerk");
        request.setBankId(testBank.getId());
        request.setRemoteWork(false);
        request.setBankOfficeId(testBankOffice.getId());
        request.setCanIssueLoans(false);
        request.setSalary(2500);

        Employee createdEmployee = employeeService.createEmployee(request);
        Employee foundEmployee = employeeService.getEmployeeDtoById(createdEmployee.getId());
        Assertions.assertNotNull(foundEmployee, "Сотрудник должен быть найден по ID");
        Assertions.assertEquals("Mark Doe", foundEmployee.getFullName(), "Имя найденного сотрудника должно совпадать");
    }

    @Test
    void testGetAllEmployees() {
        EmployeeRequest request1 = new EmployeeRequest();
        request1.setFullName("Employee A");
        request1.setBirthDate(LocalDate.of(1992, 3, 3));
        request1.setPosition("Analyst");
        request1.setBankId(testBank.getId());
        request1.setRemoteWork(false);
        request1.setBankOfficeId(testBankOffice.getId());
        request1.setCanIssueLoans(true);
        request1.setSalary(3500);
        employeeService.createEmployee(request1);

        EmployeeRequest request2 = new EmployeeRequest();
        request2.setFullName("Employee B");
        request2.setBirthDate(LocalDate.of(1989, 4, 4));
        request2.setPosition("Cashier");
        request2.setBankId(testBank.getId());
        request2.setRemoteWork(true);
        request2.setBankOfficeId(testBankOffice.getId());
        request2.setCanIssueLoans(false);
        request2.setSalary(2000);
        employeeService.createEmployee(request2);

        List<Employee> employees = employeeService.getAllEmployees();
        Assertions.assertEquals(2, employees.size(), "Должно вернуться 2 сотрудника");
    }

    @Test
    void testDeleteEmployee() {
        EmployeeRequest request = new EmployeeRequest();
        request.setFullName("Employee to Delete");
        request.setBirthDate(LocalDate.of(1993, 6, 6));
        request.setPosition("Teller");
        request.setBankId(testBank.getId());
        request.setRemoteWork(false);
        request.setBankOfficeId(testBankOffice.getId());
        request.setCanIssueLoans(false);
        request.setSalary(1800);

        Employee createdEmployee = employeeService.createEmployee(request);

        employeeService.deleteEmployee(createdEmployee.getId());

        Assertions.assertThrows(
                RuntimeException.class,
                () -> employeeService.getEmployeeDtoById(createdEmployee.getId()),
                "После удаления попытка получить сотрудника по ID должна вызывать исключение"
        );
    }
}
