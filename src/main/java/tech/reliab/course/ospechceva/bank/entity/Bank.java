package tech.reliab.course.ospechceva.bank.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "banks")
@ToString
public class Bank {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private double totalMoney;

    @Column(nullable = false)
    private double interestRate;

    @OneToMany(mappedBy = "bank")
    private List<BankOffice> offices;

    @OneToMany(mappedBy = "bank")
    private List<BankAtm> atms;

    @OneToMany(mappedBy = "bank")
    private List<Employee> employees;

    @OneToMany(mappedBy = "bank")
    private List<CreditAccount> creditAccounts;

    @OneToMany(mappedBy = "bank")
    private List<PaymentAccount> paymentAccounts;

    public Bank(String name) {
        this.name = name;
    }
}