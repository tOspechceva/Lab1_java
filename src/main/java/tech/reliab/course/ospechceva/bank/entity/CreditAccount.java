package tech.reliab.course.ospechceva.bank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "credit_accounts")
public class CreditAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Bank bank;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private int loanTermMonths;

    @Column(nullable = false)
    private double loanAmount;

    @Column(nullable = false)
    private double monthlyPayment;

    @Column(nullable = false)
    private double interestRate;

    @ManyToOne
    private Employee employee;

    @ManyToOne
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