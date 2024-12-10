package tech.reliab.course.ospechceva.bank.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.reliab.course.ospechceva.bank.entity.Employee;
import tech.reliab.course.ospechceva.bank.model.EmployeeRequest;
import tech.reliab.course.ospechceva.bank.service.EmployeeService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<Employee> createEmployee(EmployeeRequest employeeRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeService.createEmployee(employeeRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(int id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(int id, String name) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, name));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(int id) {
        return ResponseEntity.ok(employeeService.getEmployeeDtoById(id));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }
}
