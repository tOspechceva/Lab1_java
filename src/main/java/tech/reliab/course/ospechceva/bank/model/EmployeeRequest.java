package tech.reliab.course.ospechceva.bank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequest {

    private String fullName;
    private LocalDate birthDate;
    private String position;
    private int bankId;
    private boolean remoteWork;
    private int bankOfficeId;
    private boolean canIssueLoans;
    private double salary;
}
