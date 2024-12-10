package tech.reliab.course.ospechceva.bank.entity;
import tech.reliab.course.ospechceva.bank.enums.BankOfficeStatus;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@ToString
@Entity
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bank_offices")
public class BankOffice {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BankOfficeStatus status;

    @Column(nullable = false)
    private boolean canPlaceAtm;

    @Column(nullable = false)
    private boolean canIssueLoan;

    @Column(nullable = false)
    private boolean cashWithdrawal;

    @Column(nullable = false)
    private boolean cashDeposit;

    @Column(nullable = false)
    private double officeMoney;

    @Column(nullable = false)
    private double rentCost;

    @OneToMany(mappedBy = "location")
    private List<BankAtm> atms;

    @ManyToOne
    private Bank bank;

    @OneToMany(mappedBy = "bankOffice")
    private List<Employee> employees;

    public BankOffice(String name, String address, boolean canPlaceAtm, boolean canIssueLoan, boolean cashWithdrawal,
            boolean cashDeposit, double rentCost, Bank bank) {
        this.name = name;
        this.address = address;
        this.canPlaceAtm = canPlaceAtm;
        this.canIssueLoan = canIssueLoan;
        this.cashWithdrawal = cashWithdrawal;
        this.cashDeposit = cashDeposit;
        this.rentCost = rentCost;
        this.bank = bank;
    }
}