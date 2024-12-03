package tech.reliab.course.ospechceva.bank.entity;

import java.time.LocalDate;

import lombok.*;

@Getter
@Setter
public class CreditAccount {

    private int id;
    private User user;
    private Bank bank;
    private LocalDate startDate;
    private LocalDate endDate;
    private int loanTermMonths;
    private double loanAmount;
    private double monthlyPayment;
    private double interestRate;
    private Employee employee;
    private PaymentAccount paymentAccount;

    public CreditAccount(User user, Bank bank, LocalDate startDate, int loanTermMonths, double interestRate,
            Employee employee, PaymentAccount paymentAccount) {
        this.user = user;
        this.bank = bank;
        this.startDate = startDate;
        this.loanTermMonths = loanTermMonths;
        this.interestRate = interestRate;
        this.employee = employee;
        this.paymentAccount = paymentAccount;
    }

    @Override
    public String toString() {
        return "CreditAccount{" +
                "id=" + id +
                ", user=" + user.getFullName() +
                ", bank=" + bank.getName() +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", loanTermMonths=" + loanTermMonths +
                ", loanAmount=" + loanAmount +
                ", monthlyPayment=" + monthlyPayment +
                ", interestRate=" + interestRate +
                ", employee=" + (employee != null ? employee.getFullName() : "None") +
                ", paymentAccountId=" + (paymentAccount != null ? paymentAccount.getId() : "None") +
                '}';
    }
}