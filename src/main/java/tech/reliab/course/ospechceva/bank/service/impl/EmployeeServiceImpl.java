package tech.reliab.course.ospechceva.bank.service.impl;

import tech.reliab.course.ospechceva.bank.entity.Bank;
import tech.reliab.course.ospechceva.bank.entity.BankOffice;
import tech.reliab.course.ospechceva.bank.entity.Employee;
import tech.reliab.course.ospechceva.bank.service.BankService;
import tech.reliab.course.ospechceva.bank.service.EmployeeService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private static int employeesCount = 0;

    private final BankService bankService;

    private List<Employee> employees = new ArrayList<>();

    public EmployeeServiceImpl(BankService bankService) {
        this.bankService = bankService;
    }

    /**
     * Создание нового сотрудника банка.
     *
     * @param fullName     Полное имя сотрудника.
     * @param birthDate    Дата рождения сотрудника.
     * @param position     Должность сотрудника.
     * @param bank         Банк, в котором работает сотрудник.
     * @param remoteWork   Работает ли сотрудник удаленно.
     * @param bankOffice   Офис, в котором работает сотрудник.
     * @param canIssueLoans Может ли сотрудник выдавать кредиты.
     * @param salary       Зарплата сотрудника.
     * @return Созданный сотрудник банка.
     */
    public Employee createEmployee(String fullName, LocalDate birthDate, String position, Bank bank, boolean remoteWork,
                                   BankOffice bankOffice, boolean canIssueLoans, double salary) {
        Employee employee = new Employee(fullName, birthDate, position, bank, remoteWork,
                bankOffice, canIssueLoans, salary);
        employee.setId(employeesCount++);
        employees.add(employee);
        bankService.addEmployee(bank);
        return employee;
    }

    /**
     * Чтение сотрудника по его идентификатору.
     *
     * @param id Идентификатор сотрудника.
     * @return Сотрудник, если он найден, иначе - пустой Optional.
     */
    public Optional<Employee> getEmployeeById(int id) {
        return employees.stream()
                .filter(employee -> employee.getId() == id)
                .findFirst();
    }

    /**
     * Чтение всех сотрудников.
     *
     * @return Список всех сотрудников.
     */
    public List<Employee> getAllEmployees() {
        return new ArrayList<>(employees);
    }

    /**
     * Обновление информации о сотруднике по его идентификатору.
     *
     * @param id   Идентификатор сотрудника.
     * @param name Новое имя сотрудника.
     */
    public void updateEmployee(int id, String name) {
        Employee employee = getEmployeeIfExists(id);
        employee.setFullName(name);
    }

    /**
     * Удаление сотрудника по его идентификатору.
     *
     * @param id Идентификатор сотрудника.
     */
    public void deleteEmployee(int id) {
        employees.remove(getEmployeeIfExists(id));
    }

    /**
     * Получение сотрудника по его идентификатору, если он существует.
     *
     * @param id Идентификатор сотрудника.
     * @return Сотрудник, если он найден.
     * @throws NoSuchElementException Если сотрудник не найден.
     */
    public Employee getEmployeeIfExists(int id) {
        return getEmployeeById(id).orElseThrow(() -> new NoSuchElementException("Employee was not found"));
    }
}